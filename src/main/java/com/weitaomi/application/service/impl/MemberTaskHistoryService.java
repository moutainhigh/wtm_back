package com.weitaomi.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.weitaomi.application.model.bean.*;
import com.weitaomi.application.model.dto.MemberTaskDto;
import com.weitaomi.application.model.dto.MemberTaskWithDetail;
import com.weitaomi.application.model.mapper.*;
import com.weitaomi.application.service.interf.ICacheService;
import com.weitaomi.application.service.interf.IMemberScoreService;
import com.weitaomi.application.service.interf.IMemberTaskHistoryService;
import com.weitaomi.application.service.interf.IOfficeAccountService;
import com.weitaomi.systemconfig.constant.SystemConfig;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.util.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.IntegerTypeHandler;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16.
 */
@Service
public class MemberTaskHistoryService implements IMemberTaskHistoryService {
    private Logger logger = LoggerFactory.getLogger(MemberTaskHistoryService.class);
    @Autowired
    private MemberTaskHistoryMapper memberTaskHistoryMapper;
    @Autowired
    private MemberTaskMapper memberTaskMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberTaskHistoryDetailMapper memberTaskHistoryDetailMapper;
    @Autowired
    private IMemberScoreService memberScoreService;
    @Autowired
    private OfficeMemberMapper officeMemberMapper;
    @Autowired
    private WtmOfficialMemberMapper wtmOfficialMemberMapper;
    @Autowired
    private IOfficeAccountService officeAccountService;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private OfficalAccountMapper officalAccountMapper;

    @Override
    public Page<MemberTaskWithDetail> getMemberTaskInfo(Long memberId, Integer type, Integer pageSize, Integer pageIndex) {
        List<MemberTaskWithDetail> memberTaskHistoryDtoList = memberTaskHistoryMapper.getMemberTaskHistoryList(memberId, type, new RowBounds(pageIndex, pageSize));
        PageInfo<MemberTaskWithDetail> showDtoPage = new PageInfo<MemberTaskWithDetail>(memberTaskHistoryDtoList);
        return Page.trans(showDtoPage);
    }

    @Override
    public boolean addMemberTaskToHistory(Long memberId, Long taskId, Double score, Integer flag, String detail, List<MemberTaskHistoryDetail> detailList, String taskFlag) {
        MemberTask memberTask = memberTaskMapper.selectByPrimaryKey(taskId);
        if (score == null || score == 0) {
            score = memberTask.getPointCount().doubleValue();
        }
        if (StringUtil.isEmpty(detail)) {
            detail = memberTask.getTaskDesc();
        }
        MemberTaskWithDetail memberTaskWithDetail = new MemberTaskWithDetail();
        memberTaskWithDetail.setTaskId(taskId);
        memberTaskWithDetail.setPointCount(score);
        memberTaskWithDetail.setIsFinished(flag);
        memberTaskWithDetail.setMemberId(memberId);
        memberTaskWithDetail.setTaskName(memberTask.getTaskName());
        memberTaskWithDetail.setTaskDesc(memberTask.getTaskDesc());
        memberTaskWithDetail.setCreateTime(DateUtils.getUnixTimestamp());
        if (!StringUtil.isEmpty(taskFlag)) {
            memberTaskWithDetail.setTaskFlag(taskFlag);
        }
        List<MemberTaskHistoryDetail> memberTaskHistoryDetailList = new ArrayList<MemberTaskHistoryDetail>();
        if (detailList != null) {
            //// TODO: 2016/8/25
        } else {
            MemberTaskHistoryDetail memberTaskHistoryDetail = new MemberTaskHistoryDetail();
            memberTaskHistoryDetail.setTaskName(memberTask.getTaskName());
            memberTaskHistoryDetail.setTaskDesc(detail);
            memberTaskHistoryDetail.setPointCount(score);
            memberTaskHistoryDetail.setIsFinished(1);
            memberTaskHistoryDetail.setCreateTime(DateUtils.getUnixTimestamp());
            memberTaskHistoryDetailList.add(memberTaskHistoryDetail);
        }
        memberTaskWithDetail.setMemberTaskHistoryDetailList(memberTaskHistoryDetailList);
        memberTaskHistoryMapper.insertSelective((MemberTaskHistory) memberTaskWithDetail);
        if (!memberTaskWithDetail.getMemberTaskHistoryDetailList().isEmpty()) {
            memberTaskHistoryDetailMapper.insertIntoDetail(memberTaskWithDetail.getMemberTaskHistoryDetailList(), memberTaskWithDetail.getId(), DateUtils.getUnixTimestamp());
            return true;
        }
        return false;
    }

    @Override
    public void deleteUnFinishedTask() {
        logger.info("定时任务启动");
        List<OfficeMember> officeMemberList = officeMemberMapper.getOfficeMemberUnfinishedList(SystemConfig.TASK_CACHE_TIME);
        for (OfficeMember officeMember : officeMemberList) {
            Map<String, Object> params = officeMemberMapper.getCacheKeyParams(officeMember.getMemberId());
            OfficialAccount officialAccount = officalAccountMapper.selectByPrimaryKey(officeMember.getOfficeAccountId());
            if (officialAccount!=null) {
                String originId = officialAccount.getOriginId();
                String key = null;
                try {
                    key = Base64Utils.encodeToString(params.get("nickname").toString().getBytes("UTF-8")) + ":" + params.get("sex").toString() + ":" + originId;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                officeMemberMapper.delete(officeMember);
                cacheService.delKeyFromRedis(key);
            }
        }
        int number1 = officeMemberMapper.deleteOverTimeUnfollowedAccounts(SystemConfig.TASK_CACHE_TIME);
        logger.info("删除未关注公众号" + number1 + "条");
        int number2 = memberTaskHistoryMapper.deleteUnfinishedTask(SystemConfig.TASK_CACHE_TIME);
        logger.info("删除未完成任务" + number2 + "条");
        int number3 = memberTaskHistoryMapper.deleteUnfinishedTaskDetail(SystemConfig.TASK_CACHE_TIME);
        logger.info("删除未完成任务详情" + number3 + "条");
        //推送微信失败
        officeAccountService.taskFailPushToWechat();
        //关注未推送成功
        officeAccountService.taskFailToAckAddRequest();
    }


    @Override
    public void twoOclockScheduledJob() {
        //统一处理7天加米币奖励
        Integer number1 = memberScoreService.addOfficialAccountScoreToAvaliable();
        logger.info("处理统一处理7天加米币奖励" + number1 + "条");
    }

    @Override
    public void threeOclockScheduledJob() {
        //统一处理平台的加成奖励
        Integer number = memberScoreService.updateExtraRewardTimer();
        logger.info("处理上下级米币问题" + number + "条");
    }

    @Override
    public void fourOclockScheduledJob() {
        //统一处理十天前无用的任务记录
        Integer number1 = memberTaskHistoryMapper.deleteUselessRecord(7*24*60*60L);
        Integer number2 = memberTaskHistoryMapper.deleteUselessRecordDetail(7*24*60*60L);
        logger.info("统一处理十天前无用的任务记录" + number1 + "条，任务详情" +number2+ "条");
    }
}
