package com.weitaomi.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.weitaomi.application.model.bean.Article;
import com.weitaomi.application.model.bean.MemberScore;
import com.weitaomi.application.model.bean.OfficialAccount;
import com.weitaomi.application.model.bean.TaskPool;
import com.weitaomi.application.model.dto.*;
import com.weitaomi.application.model.mapper.*;
import com.weitaomi.application.service.interf.ICacheService;
import com.weitaomi.application.service.interf.IMemberScoreService;
import com.weitaomi.application.service.interf.IMemberTaskHistoryService;
import com.weitaomi.application.service.interf.IMemberTaskPoolService;
import com.weitaomi.systemconfig.constant.SystemConfig;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.util.*;
import org.apache.ibatis.session.RowBounds;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
@Service
public class MemberTaskPoolService extends BaseService implements IMemberTaskPoolService{
    private Logger logger= LoggerFactory.getLogger(MemberTaskPoolService.class);
    @Autowired
    private TaskPoolMapper taskPoolMapper;
    @Autowired
    private OfficalAccountMapper officalAccountMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private IMemberScoreService memberScoreService;
    @Autowired
    private IMemberTaskHistoryService memberTaskHistoryService;
    @Autowired
    private MemberScoreMapper memberScoreMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ICacheService cacheService;
    @Override
    public RequireFollowerParamsDto getRequireFollowerParamsDto(Long memberId) {
        RequireFollowerParamsDto requireFollowerParamsDto=new RequireFollowerParamsDto();
        List<OfficialAccountsDto> accountList=officalAccountMapper.getAccountsByMemberId(memberId,0L);
        if (accountList.isEmpty()){
            throw new BusinessException("公众号列表为空");
        }
        requireFollowerParamsDto.setOfficialAccountList(accountList);
        List<Address> addressList=provinceMapper.getAllAddress();
        if (addressList.isEmpty()){
            throw new BusinessException("获取地区列表失败，请稍后再试");
        }
        requireFollowerParamsDto.setAddressList(addressList);
//        logger.info(JSON.toJSONString(requireFollowerParamsDto));
        return requireFollowerParamsDto;
    }

    @Override
    public RequireFollowerParamsDto getMemberArticlePublishMsg(Long memberId) {
        RequireFollowerParamsDto requireFollowerParamsDto=new RequireFollowerParamsDto();
        List<OfficialAccountsDto> accountList=officalAccountMapper.getAccountsByMemberId(memberId,1L);
        if (accountList.isEmpty()){
            throw new BusinessException("公众号列表为空");
        }
        requireFollowerParamsDto.setOfficialAccountList(accountList);
//        logger.info(JSON.toJSONString(requireFollowerParamsDto));
        return requireFollowerParamsDto;
    }

    @Override
    public Page<TaskPoolDto> getTaskPoolDto(Long officialAccountId, Integer type, int pageSize, int pageIndex) {
        List<TaskPoolDto> taskPoolDtoList=null;
        if (type==1) {
            taskPoolDtoList= taskPoolMapper.getTaskPoolArticleDto(officialAccountId,new RowBounds(pageIndex,pageSize));
        }
        if (type==0) {
            taskPoolDtoList= taskPoolMapper.getTaskPoolAccountDto(officialAccountId,new RowBounds(pageIndex,pageSize));
        }
        PageInfo<TaskPoolDto> taskPoolDtoPageInfo=new PageInfo<TaskPoolDto>(taskPoolDtoList);
        return Page.trans(taskPoolDtoPageInfo);
    }

    @Override
    @Transactional
    public Boolean updateTaskPoolDto(Long memberId,Long taskPoolId, Integer isPublishNow,Integer needNumber,Double singScore,Integer limitDay) {
        TaskPool taskPool=taskPoolMapper.selectByPrimaryKey(taskPoolId);
        taskPool.setIsPublishNow(isPublishNow);
        taskPool.setCreateTime(DateUtils.getUnixTimestamp());
        if (taskPool==null){
            throw new InfoException("修改失败,没有此任务");
        }
        TaskPool taskPoolTemp=new TaskPool();
        taskPoolTemp.setIsPublishNow(1);
        long scoreId=0;
        long taskId=0;
        if (taskPool.getTaskType()==0){
            scoreId=4;
            taskId=8;
            taskPoolTemp.setOfficialAccountsId(taskPool.getOfficialAccountsId());
        }
        if (taskPool.getTaskType()==1){
            scoreId=5;
            taskId=9;
            taskPoolTemp.setArticleId(taskPool.getArticleId());
        }
        List<TaskPool> taskPoolList=taskPoolMapper.select(taskPoolTemp);
        if (isPublishNow==1){
            if (taskPoolList.size()>=1){
                throw new InfoException("您还有一个相同的任务正在进行中，请将该任务下架后再上架新任务，或者等任务结束");
            }
            if (singScore==null||singScore==0){
                throw new InfoException("单次任务米币必须为大于零的整数");
            }
            if (needNumber==null||needNumber==0){
                throw new InfoException("任务需求量必须为大于零的整数");
            }
            Double totalScore=singScore*needNumber-taskPool.getTotalScore();
            MemberScore  memberScore=memberScoreMapper.getMemberScoreByMemberId(memberId);
            OfficialAccount officialAccount= officalAccountMapper.selectByPrimaryKey(taskPool.getOfficialAccountsId());
            if (totalScore>memberScore.getMemberScore().doubleValue()){
                throw new InfoException("账户剩余米币大于发布任务消费米币");
            }
            taskPool.setTotalScore(singScore*needNumber);
            taskPool.setLimitDay(limitDay.longValue());
            taskPool.setNeedNumber(needNumber);
            taskPool.setSingleScore(singScore);
            memberScoreService.addMemberScore(taskPool.getMemberId(),scoreId,1,-Double.valueOf(totalScore), UUIDGenerator.generate());
            memberTaskHistoryService.addMemberTaskToHistory(taskPool.getMemberId(),taskId,-totalScore.doubleValue(),1,"重新发布公众号"+officialAccount.getUserName()+"求粉任务",null,null);
        } else if (isPublishNow==0){
            Double score=taskPool.getTotalScore();
            taskPool.setTotalScore(0D);
            memberScoreService.addMemberScore(memberId, 6L,1,score.doubleValue(), UUIDGenerator.generate());
        }
        int num = taskPoolMapper.updateByPrimaryKeySelective(taskPool);
        return num>0?true:false;
    }
}
