package com.weitaomi.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weitaomi.application.model.bean.*;
import com.weitaomi.application.model.dto.*;
import com.weitaomi.application.model.mapper.*;
import com.weitaomi.application.service.interf.*;
import com.weitaomi.systemconfig.constant.SystemConfig;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by supumall on 2016/8/9.
 */
@Service
public class OfficeAccountService implements IOfficeAccountService {
    private static Logger logger = LoggerFactory.getLogger(OfficeAccountService.class);
    @Autowired
    private OfficalAccountMapper officalAccountMapper;
    @Autowired
    private MemberTaskHistoryMapper memberTaskHistoryMapper;
    @Autowired
    private WtmOfficialMemberMapper wtmOfficialMemberMapper;
    @Autowired
    private ThirdLoginMapper thirdLoginMapper;
    @Autowired
    private MemberTaskMapper memberTaskMapper;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private TaskPoolMapper taskPoolMapper;
    @Autowired
    private OfficeMemberMapper officeMemberMapper;
    @Autowired
    private IMemberScoreService memberScoreService;
    @Autowired
    private IMemberTaskHistoryService memberTaskHistoryService;
    @Autowired
    private AccountAdsMapper accountAdsMapper;
    @Autowired
    private TaskFailPushToWechatMapper taskFailPushToWechatMapper;

    @Override
    @Transactional
    public Boolean pushAddFinished(Map<String, Object> params) {
        String openid="";
        if (params.get("openid")!=null) {
            openid = (String) params.get("openid").toString();
        }
        String nickname="";
        if (params.get("nickname")!=null) {
            nickname = params.get("nickname").toString();
        }
        String sexString = "";
        if (params.get("sex")!=null) {
            sexString = params.get("sex").toString();
        }
        String originId = "";
        if (params.get("originId")!=null) {
            originId = params.get("originId").toString();
        }
        Integer flag = Integer.valueOf(params.get("flag").toString());
        logger.info("openId:{},nickname:{},sex:{},originId:{},flag:{}", openid, nickname, sexString,originId,flag);
        if (flag == 0) {
            OfficialAccountWithScore officialAccountWithScore = officalAccountMapper.getOfficialAccountWithScoreById(originId, 0);
            if (officialAccountWithScore != null) {
                OfficeMember officeMember = officeMemberMapper.getOfficeMemberByOpenId(openid);
                if (officeMember == null) {
                    logger.info("没有此用户记录");
                    return true;
                }
                //增加积分以及积分记录
                Long memberId = officeMember.getMemberId();
                int num = officeMemberMapper.deleteFollowAccountsMember(officeMember.getId());
                if (num > 0) {
                    try {
                        if (DateUtils.getUnixTimestamp() - officeMember.getCreateTime() < 7 * 24 * 60 * 60) {
                            TaskPool taskPool = taskPoolMapper.getTaskPoolByOfficialId(officialAccountWithScore.getId(), null);
                            memberScoreService.addMemberScore(memberId, 7L, 1, -(taskPool.getRate().multiply(BigDecimal.valueOf(officialAccountWithScore.getScore()))).doubleValue(), UUIDGenerator.generate());
                            memberTaskHistoryService.addMemberTaskToHistory(memberId, 11L, -(taskPool.getRate().multiply(BigDecimal.valueOf(officialAccountWithScore.getScore()))).doubleValue(), 1, "七天之内取消关注公众号" + officialAccountWithScore.getUserName(), null, null);
                            memberScoreService.addMemberScore(officialAccountWithScore.getMemberId(), 9L, 1, officialAccountWithScore.getScore(), UUIDGenerator.generate());
                            memberTaskHistoryService.addMemberTaskToHistory(officialAccountWithScore.getMemberId(), 12L, officialAccountWithScore.getScore(), 1, "用户七天之内取消关注公众号" + officialAccountWithScore.getUserName() + ",米币退还给公众号商家", null, null);
                            logger.info("普通用户ID为{}的用户米币扣除成功，米币数为{}，商户用户ID为{}的用户米币返还成功，米币数为{}", memberId, -(taskPool.getRate().multiply(BigDecimal.valueOf(officialAccountWithScore.getScore()))).doubleValue(), officialAccountWithScore.getMemberId(), officialAccountWithScore.getScore());
                            Map memberInfoDto = thirdLoginMapper.getNickNameAndSex(memberId);
                            String key = Base64Utils.encodeToString(memberInfoDto.get("nickname").toString().getBytes()) + ":" + memberInfoDto.get("sex") + ":" + officialAccountWithScore.getOriginId();
                            cacheService.delKeyFromRedis(key);
                            return true;
                        }
                    }catch (Exception e){
                        logger.info("增加记录失败");
                        return true;
                    }
                }
            }else {
                return true;
            }
        }
        if (flag == 1) {
            OfficialAccountWithScore officialAccountWithScore = officalAccountMapper.getOfficialAccountWithScoreById(originId, 1);
            if (officialAccountWithScore != null) {
                String key = nickname + ":" + sexString + ":" + originId;
                logger.info("key is {}", key);
                MemberCheck memberCheck = cacheService.getCacheByKey(key, MemberCheck.class);
                logger.info("获取到的关注数据为:{}", JSON.toJSONString(memberCheck));
                if (memberCheck != null) {
                    Long officeAccountId = Long.valueOf(memberCheck.getOfficialAccountsId());
                    TaskPool taskPool = taskPoolMapper.getTaskPoolByOfficialId(officeAccountId, 1);
                    Long memberId = memberCheck.getMemberId();
                    if (taskPool == null) {
                        JpushUtils.buildRequest("您关注的公众号任务：" + officialAccountWithScore.getUserName() + "已经结束", memberId);
                        return false;
                    }
                    Double score = taskPool.getTotalScore() - taskPool.getSingleScore();
                    if (taskPool != null && officialAccountWithScore != null && score >= 0) {
                        //添加到公众号关注表中
                        OfficeMember officeMember = officeMemberMapper.getOfficeMember(memberId, officeAccountId);
                        logger.info("关注记录表:{}", JSON.toJSONString(officeMember));
                        if (officeMember == null) {
                            JpushUtils.buildRequest("您关注的公众号任务：" + officialAccountWithScore.getUserName() + "，不存在或者已经结束", memberId);
                            return true;
                        }
                        if (officeMember.getIsAccessNow() == 1) {
                            JpushUtils.buildRequest("您已关注过公众号：" + officialAccountWithScore.getUserName() + "，该任务失效", memberId);
                            return true;
                        }
                        officeMember.setIsAccessNow(1);
                        officeMember.setOpenId(openid);
                        officeMember.setAddRewarScore((taskPool.getRate().multiply(BigDecimal.valueOf(officialAccountWithScore.getScore()))));
                        officeMember.setFinishedTime(DateUtils.getUnixTimestamp());
                        int num = officeMemberMapper.updateByPrimaryKeySelective(officeMember);
                        if (num > 0) {
                            //任务池中的任务剩余积分更改
                            if (score >= taskPool.getSingleScore()) {
                                taskPoolMapper.updateTaskPoolWithScore(score.doubleValue(), taskPool.getId());
                            } else {
                                taskPool.setTotalScore(0D);
                                taskPool.setLimitDay(0L);
                                taskPool.setIsPublishNow(0);
                                taskPoolMapper.updateByPrimaryKeySelective(taskPool);
                                memberScoreService.addMemberScore(officialAccountWithScore.getMemberId(), 6L, 1, score.doubleValue(), UUIDGenerator.generate());
                            }
                            //增加任务记录
                            logger.info("增加任务记录");
                            int number = memberTaskHistoryMapper.updateMemberTaskUnfinished(memberId, 0, officialAccountWithScore.getOriginId());
                            //增加积分以及积分记录
                            logger.info("ID为：{}用户,增加积分以及积分记录：{}", memberId, (taskPool.getRate().multiply(BigDecimal.valueOf(officialAccountWithScore.getScore()))).doubleValue());
                            memberScoreService.addMemberScore(memberId, 11L, 1, (taskPool.getRate().multiply(BigDecimal.valueOf(officialAccountWithScore.getScore()))).doubleValue(), UUIDGenerator.generate());
                            cacheService.delKeyFromRedis(key);
                            return true;
                        }
                    } else {
                        JpushUtils.buildRequest(JpushUtils.getJpushMessage(memberId, "任务不存在，或者任务已结束"));
                        throw new InfoException("任务不存在，或者任务已结束");
                    }
                }else {
                    return true;
                }
            } else {
                logger.info("任务不存在，或者任务已结束");
                String key = nickname + ":" + sexString + ":" + originId;
                cacheService.delKeyFromRedis(key);
                return true;
            }
        }
        return false;
    }

    @Override
    public void taskFailPushToWechat() {
        List<Long> idList=new ArrayList<>();
        idList.add(0L);
        idList.add(1L);
        List<TaskFailPushToWechat> taskFailPushToWechatList = taskFailPushToWechatMapper.getAllTaskFailPushToWechat(idList);
        for (TaskFailPushToWechat taskFailPushToWechat : taskFailPushToWechatList) {
            logger.info("定时推送参数数据为：{}",JSON.toJSONString(taskFailPushToWechat));
            try {
                String result = HttpRequestUtils.postStringEntity(taskFailPushToWechat.getPostUrl(), taskFailPushToWechat.getParams());
                if (!StringUtil.isEmpty(result)) {
                    Map<String,String> params= (Map<String, String>) JSONObject.parse(result);
                    boolean flag = Boolean.valueOf(params.get("temp"));
                    if (flag){
                        taskFailPushToWechat.setIsPushToWechat(1);
                        taskFailPushToWechatMapper.updateByPrimaryKeySelective(taskFailPushToWechat);
                    }
                    logger.info("定时任务执行陈宫，执行结果为："+flag);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

            }
        }
    }

    @Override
    public void taskFailToAckAddRequest() {
        List<Long> idList=new ArrayList<>();
        idList.add(2L);
        List<TaskFailPushToWechat> taskFailPushToWechatList = taskFailPushToWechatMapper.getAllTaskFailPushToWechat(idList);
        for (TaskFailPushToWechat taskFailPushToWechat : taskFailPushToWechatList) {
            logger.info("定时推送参数数据为：{}",JSON.toJSONString(taskFailPushToWechat));
            Boolean flag = this.pushAddFinished((Map)JSON.parseObject(taskFailPushToWechat.getParams()));
            if (flag){
                taskFailPushToWechat.setIsPushToWechat(1);
                taskFailPushToWechatMapper.updateByPrimaryKeySelective(taskFailPushToWechat);
            }
            logger.info("定时任务执行陈宫，执行结果为："+flag);
        }
    }
}
