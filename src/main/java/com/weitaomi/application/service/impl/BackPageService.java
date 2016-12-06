package com.weitaomi.application.service.impl;

import com.github.pagehelper.PageInfo;
import com.weitaomi.application.model.bean.OfficialAccount;
import com.weitaomi.application.model.bean.TaskPool;
import com.weitaomi.application.model.dto.*;
import com.weitaomi.application.model.mapper.*;
import com.weitaomi.application.service.interf.IBackPageService;
import com.weitaomi.application.service.interf.IMemberTaskPoolService;
import com.weitaomi.systemconfig.constant.SystemConfig;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.util.Page;
import com.weitaomi.systemconfig.util.PropertiesUtil;
import com.weitaomi.systemconfig.util.SendMCUtils;
import com.weitaomi.systemconfig.util.StringUtil;
import org.apache.ibatis.cache.CacheException;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/11/3.
 */
@Service
public class BackPageService extends BaseService implements IBackPageService {
    private Logger logger= org.slf4j.LoggerFactory.getLogger(BackPageService.class);
    @Autowired
    private TaskPoolMapper taskPoolMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberScoreMapper memberScoreMapper;
    @Autowired
    private MemberScoreFlowMapper memberScoreFlowMapper;
    @Autowired
    private MemberTaskHistoryMapper memberTaskHistoryMapper;
    @Autowired
    private OfficalAccountMapper officalAccountMapper;
    @Autowired
    private MemberInvitedRecordMapper memberInvitedRecordMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Page<ArticleShowDto> getAllArticle(Integer pageIndex, Integer pageSize) {
        List<Map<String, String>> articleShowDtoList = taskPoolMapper.getAtricleList(new RowBounds(pageIndex, pageSize));
        PageInfo<Map<String, String>> showDtoPage = new PageInfo<Map<String, String>>(articleShowDtoList);
        return Page.trans(showDtoPage);
    }
    @Override
    public String sendIndentifyCode(String mobile, Integer type) {
        if (!mobile.matches("^[1][34578]\\d{9}$")) {
            throw new BusinessException("手机号码格式不正确");
        }
        String key = "member:indentifyCode:" + mobile;
        String value = this.getIndentifyCodeFromCache(key);
        if (value != null && !value.equals("00000000")) {
            throw new InfoException("验证码已发送，请120s后重试");
        }
        String identifyCode = StringUtil.numRandom(6);
        String content = null;
        content = MessageFormat.format(new String(PropertiesUtil.getValue("verifycode.msg")), identifyCode);
        SendMCUtils.sendMessage(mobile, content);
        this.setIndentifyCodeToCache(key, identifyCode, 120L);
        return identifyCode;
    }
    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    private String getIndentifyCodeFromCache(String key) {
        ValueOperations<String, String> valueOper = redisTemplate.opsForValue();
        StringBuilder sb = new StringBuilder();
        String type = null;
        String value = valueOper.get(key);
        if (value != null) {
            return value;
        }
        return "00000000";
    }
    /**
     * 设置缓存
     *
     * @param key
     * @param IdentifyCode
     * @param timeout
     * @throws CacheException
     */
    private void setIndentifyCodeToCache(String key, String IdentifyCode, Long timeout) {
        Long timeoutTemp = timeout;
        if (timeout == null) {
            timeoutTemp = 120L;
        }
        ValueOperations<String, String> valueOper = redisTemplate.opsForValue();
        valueOper.set(key, IdentifyCode, timeoutTemp, TimeUnit.SECONDS);
    }
    @Override
    public int patchCheckArticle(List<Long> poolIdList) {
        if (!poolIdList.isEmpty()){
            int number=taskPoolMapper.patchCheckArticle(poolIdList);
            return  number;
        }
        return 0;
    }
    @Override
    public String uploadUpyunFiles(String path, String files, String suffix,Integer temp) {
        boolean flag=false;
        if (temp!=null&&temp==0){
            flag=super.uploadImage(path+"."+suffix, files.substring(files.indexOf("base64")+7));
        }else if (temp!=null&&temp==1){
            flag=super.uploadImage(files.getBytes(),path+"."+suffix);
        }

        if (flag) {
            return SystemConfig.UPYUN_PREFIX +path+"."+suffix;
        }
        return null;
    }

    @Override
    public Object getMemberInformationDetail(Long memberId,Integer flag,Integer pageIndex,Integer pageSize) {
        if (memberId==null){
            throw new InfoException("用户ID不能为空");
        }
        if (flag==null||flag<=0||flag>=6){
            throw new InfoException("查询条件不正确");
        }
        Object params=null;
        switch (flag){
            case 1:params=this.getMemberScoreInformation(memberId);break;
            case 2:params=this.getMemberScoreFlowInformation(memberId, pageIndex, pageSize);break;
            case 3:params=this.getMemberTaskHistoryInformation(memberId, pageIndex, pageSize);break;
            case 4:params=this.getOfficialAccountList(memberId, pageIndex, pageSize);break;
            case 5:params=this.getMemberInvitedRecordInformation(memberId, pageIndex, pageSize);
        }
        return params;
    }
    @Override
    public Page<MemberSearchInformation> getMemberInformation(MemberSearch memberSearch){
        List<MemberSearchInformation> memberSearchInformationList=memberMapper.getMemberSearchInformation(memberSearch,new RowBounds(memberSearch.getPageIndex(),memberSearch.getPageSize()));
        PageInfo pageInfo=new PageInfo(memberSearchInformationList);
        return Page.trans(pageInfo);
    }
    private MemberScoreInformation getMemberScoreInformation(Long memberId){
        MemberScoreInformation memberScoreInformation=memberScoreMapper.getMemberScoreInformation(memberId);
        return memberScoreInformation==null?null:memberScoreInformation;
    }
    private Page<MemberScoreFlowInformation> getMemberScoreFlowInformation(Long memberId,Integer pageIndex,Integer pageSize){
        List<MemberScoreFlowInformation> memberScoreFlowInformations=memberScoreFlowMapper.getMemberScoreFlowInformation(memberId,new RowBounds(pageIndex,pageSize));
        PageInfo<MemberScoreFlowInformation> pageInfo=new PageInfo<MemberScoreFlowInformation>(memberScoreFlowInformations);
        return Page.trans(pageInfo);
    }
    private Page<MemberTaskHistoryInformation> getMemberTaskHistoryInformation(Long memberId,Integer pageIndex,Integer pageSize){
        List<MemberTaskHistoryInformation> memberTaskHistoryInformations=memberTaskHistoryMapper.getMemberTaskHistoryInformation(memberId,new RowBounds(pageIndex,pageSize));
        PageInfo<MemberTaskHistoryInformation> pageInfo=new PageInfo<MemberTaskHistoryInformation>(memberTaskHistoryInformations);
        return Page.trans(pageInfo);
    }
    private Page<OfficialAccount> getOfficialAccountList(Long memberId,Integer pageIndex,Integer pageSize){
        List<OfficialAccount> officialAccountList=officalAccountMapper.getOfficialAccountList(memberId,new RowBounds(pageIndex,pageSize));
        PageInfo<OfficialAccount> pageInfo=new PageInfo<OfficialAccount>(officialAccountList);
        return Page.trans(pageInfo);
    }
    private Page<MemberInvitedRecordInformation> getMemberInvitedRecordInformation(Long memberId,Integer pageIndex,Integer pageSize){
        List<MemberInvitedRecordInformation> memberInvitedRecordInformations=memberInvitedRecordMapper.getMemberInvitedRecordInformation(memberId,new RowBounds(pageIndex,pageSize));
        PageInfo<MemberInvitedRecordInformation> pageInfo=new PageInfo<MemberInvitedRecordInformation>(memberInvitedRecordInformations);
        return Page.trans(pageInfo);
    }
}
