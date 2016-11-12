package com.weitaomi.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.weitaomi.application.model.bean.*;
import com.weitaomi.application.model.dto.MemberTaskWithDetail;
import com.weitaomi.application.model.dto.RewardCountDto;
import com.weitaomi.application.model.mapper.*;
import com.weitaomi.application.service.interf.ICacheService;
import com.weitaomi.application.service.interf.IMemberScoreService;
import com.weitaomi.application.service.interf.IMemberTaskHistoryService;
import com.weitaomi.systemconfig.constant.SystemConfig;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.util.DateUtils;
import com.weitaomi.systemconfig.util.PropertiesUtil;
import com.weitaomi.systemconfig.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by supumall on 2016/7/8.
 */
@Service
public class MemberScoreService implements IMemberScoreService {
    private final Logger logger = LoggerFactory.getLogger(MemberScoreService.class);
    //    private RuntimeSchema<Member> schema=RuntimeSchema.createFrom(Member.class);
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private MemberScoreMapper memberScoreMapper;
    @Autowired
    private MemberScoreFlowMapper memberScoreFlowMapper;
    @Autowired
    private MemberScoreFlowTypeMapper memberScoreFlowTypeMapper;
    @Autowired
    private MemberInvitedRecordMapper memberInvitedRecordMapper;
    @Autowired
    private MemberTaskMapper memberTaskMapper;
    @Autowired
    private IMemberTaskHistoryService memberTaskHistoryService;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    @Transactional
    public MemberScore addMemberScore(Long memberId, Long typeId,Integer isFinished, Double score, String sessionId) {
        if (sessionId == null) {
            throw new BusinessException("幂等性操作，请生成随机数");
        }
        String key = "member:score:" + sessionId;
        MemberScore memberScoreCache = cacheService.getCacheByKey(key, MemberScore.class);
        if (memberScoreCache != null) {
            throw new BusinessException("重复操作");
        } else {
            if (typeId == null) {
                throw new BusinessException("积分类型ID为空");
            } else {
                MemberScoreFlowType memberScoreFlowType = memberScoreFlowTypeMapper.selectByPrimaryKey(typeId);
                if (memberScoreFlowType == null) {
                    throw new InfoException("查无此积分流动类型");
                }
                BigDecimal increaseScore = null;
                BigDecimal scoreBefore = BigDecimal.ZERO;
                MemberScore memberScore = memberScoreMapper.getMemberScoreByMemberId(memberId);
                if (score != null) {
                    BigDecimal rate = BigDecimal.ONE;
                    if (memberScore != null) {
                        scoreBefore = memberScore.getMemberScore();
                        rate = memberScore.getRate();
                    }
                    if (typeId!=7) {
                        if (typeId != 3) {
                            rate = BigDecimal.ONE;
                        }
                    }
                    increaseScore = BigDecimal.valueOf(score).multiply(rate);
                }

                if (memberScore == null) {
                    if (increaseScore.doubleValue() < 0) {
                        throw new BusinessException("可用积分不足");
                    }
                    memberScore = new MemberScore();
                    memberScore.setMemberId(memberId);
                    memberScore.setMemberScore(increaseScore);
                    memberScore.setCreateTime(DateUtils.getUnixTimestamp());
                    memberScore.setUpdateTime(DateUtils.getUnixTimestamp());
                    memberScoreMapper.insertSelective(memberScore);
                } else {
                    BigDecimal afterScore = memberScore.getMemberScore().add(increaseScore);
                    BigDecimal avaliableScore = memberScore.getAvaliableScore().add(increaseScore);
                    if (afterScore.doubleValue() < 0) {
                        throw new BusinessException("可用积分不足");
                    }
                    memberScore.setMemberScore(afterScore);
                    memberScore.setAvaliableScore(avaliableScore);
                    if (afterScore.doubleValue() >= Double.valueOf(PropertiesUtil.getValue("score.level.one"))) {
                        memberScore.setRate(BigDecimal.valueOf(2.00));
                    } else if (afterScore.doubleValue() <= Double.valueOf(PropertiesUtil.getValue("score.level.one.one"))) {
                        memberScore.setRate(BigDecimal.valueOf(1.00));
                    } else {
                        Double levelOne = Double.valueOf(PropertiesUtil.getValue("score.level.one"));
                        Double rateBase = Double.valueOf(PropertiesUtil.getValue("score.level.one.base"));
                        BigDecimal rateIncrease = afterScore.subtract(BigDecimal.valueOf(rateBase)).divide(BigDecimal.valueOf(levelOne), 1, BigDecimal.ROUND_UP);
                        memberScore.setRate(rateIncrease.add(BigDecimal.ONE));
                    }
                    memberScore.setUpdateTime(DateUtils.getUnixTimestamp());
                    memberScoreMapper.updateByPrimaryKeySelective(memberScore);
                }
                MemberScoreFlow memberScoreFlow = new MemberScoreFlow();
                memberScoreFlow.setMemberId(memberId);
                memberScoreFlow.setTypeId(typeId);
                memberScoreFlow.setIsFinished(isFinished);
                memberScoreFlow.setDetail(memberScoreFlowType.getTypeDesc());
                memberScoreFlow.setFlowScore(increaseScore);
                memberScoreFlow.setMemberScoreAfter(memberScore.getMemberScore());
                memberScoreFlow.setMemberScoreBefore(scoreBefore);
                memberScoreFlow.setMemberScoreId(memberScore.getId());
                memberScoreFlow.setCreateTime(DateUtils.getUnixTimestamp());
                Boolean flag = memberScoreFlowMapper.insertSelective(memberScoreFlow) > 0 ? true : false;
                if (!flag) {
                    throw new BusinessException("积分记录失败");
                }
                cacheService.setCacheByKey(key, memberScore, SystemConfig.TASK_CACHE_TIME);
                if (typeId==3&&increaseScore.doubleValue()>0) {
                    //处理上级奖励问题
                    MemberInvitedRecord memberInvitedRecord = memberInvitedRecordMapper.getMemberInvitedRecordByMemberId(memberId);
                    if (memberInvitedRecord != null) {
                        Double rewardScore=increaseScore.multiply(BigDecimal.valueOf(0.1)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//                        Long expiresTime=DateUtils.getTodayEndSeconds()-DateUtils.getUnixTimestamp()+3*60*60;
                        String tableName="member:extra:reward";
                        String idKey = memberInvitedRecord.getMemberId()+":"+memberInvitedRecord.getParentId();
                        Member member = memberMapper.selectByPrimaryKey(memberInvitedRecord.getMemberId());
                        if (cacheService.keyExistInHashTable(tableName,idKey)){
                            RewardCountDto rewardCountDtoTemp=cacheService.getFromHashTable(tableName,idKey);
                            if (rewardCountDtoTemp!=null){
                                Double totalFlowScore=rewardCountDtoTemp.getTotalFlowScore()+rewardScore;
                                rewardCountDtoTemp.setTotalFlowScore(totalFlowScore);
                                cacheService.reSetToHashTable(tableName,idKey,rewardCountDtoTemp);
                            }else {
                                RewardCountDto rewardCountDto = new RewardCountDto();
                                rewardCountDto.setMemberId(memberInvitedRecord.getMemberId());
                                rewardCountDto.setParentId(memberInvitedRecord.getParentId());
                                rewardCountDto.setMemberName(member.getMemberName());
                                rewardCountDto.setTotalFlowScore(rewardScore);
                                cacheService.setToHashTable(tableName,idKey,rewardCountDto,null);
                            }
                        }else {
                            RewardCountDto rewardCountDto = new RewardCountDto();
                            rewardCountDto.setMemberId(memberInvitedRecord.getMemberId());
                            rewardCountDto.setParentId(memberInvitedRecord.getParentId());
                            rewardCountDto.setMemberName(member.getMemberName());
                            rewardCountDto.setTotalFlowScore(rewardScore);
                            cacheService.setToHashTable(tableName,idKey,rewardCountDto,0L);
                        }
                    }
                }
                return memberScore;
            }
        }
    }

    @Override
    public Integer updateExtraRewardTimer(){
        String tableName="member:extra:reward";
        Set<String> keys=cacheService.getAllKeysFromHashTable(tableName);
        List<RewardCountDto> rewardCountDtoList=cacheService.getFromHashTable(tableName,new ArrayList<String>(keys));
        for (RewardCountDto rewardCountDto:rewardCountDtoList){
            MemberScore memberScore1 = memberScoreMapper.getMemberScoreByMemberId(rewardCountDto.getParentId());
            BigDecimal beforeAmount = BigDecimal.ZERO;
            Double rewardScore=rewardCountDto.getTotalFlowScore();
            if (memberScore1 != null) {
                beforeAmount = memberScore1.getMemberScore();
                BigDecimal afterScore = beforeAmount.add(BigDecimal.valueOf(rewardScore));
                if (afterScore.doubleValue() < 0) {
                    throw new BusinessException("可用积分不足");
                }
                memberScore1.setMemberScore(afterScore);
                if (afterScore.doubleValue() >= Double.valueOf(PropertiesUtil.getValue("score.level.one"))) {
                    memberScore1.setRate(BigDecimal.valueOf(2.00));
                } else if (afterScore.doubleValue() <= Double.valueOf(PropertiesUtil.getValue("score.level.one.one"))) {
                    memberScore1.setRate(BigDecimal.valueOf(1.00));
                } else {
                    Double levelOne = Double.valueOf(PropertiesUtil.getValue("score.level.one"));
                    Double rateBase = Double.valueOf(PropertiesUtil.getValue("score.level.one.base"));
                    BigDecimal rateIncrease = afterScore.subtract(BigDecimal.valueOf(rateBase)).divide(BigDecimal.valueOf(levelOne), 1, BigDecimal.ROUND_FLOOR);
                    memberScore1.setRate(rateIncrease.add(BigDecimal.ONE));
                }
                memberScore1.setUpdateTime(DateUtils.getUnixTimestamp());
                memberScoreMapper.updateByPrimaryKeySelective(memberScore1);
            }else {
                memberScore1=new MemberScore();
                BigDecimal afterScore = beforeAmount.add(BigDecimal.valueOf(rewardScore));
                if (afterScore.doubleValue() >= Double.valueOf(PropertiesUtil.getValue("score.level.one"))) {
                    memberScore1.setRate(BigDecimal.valueOf(2.00));
                } else if (afterScore.doubleValue() <= Double.valueOf(PropertiesUtil.getValue("score.level.one.one"))) {
                    memberScore1.setRate(BigDecimal.valueOf(1.00));
                } else {
                    Double levelOne = Double.valueOf(PropertiesUtil.getValue("score.level.one"));
                    Double rateBase = Double.valueOf(PropertiesUtil.getValue("score.level.one.base"));
                    BigDecimal rateIncrease = afterScore.subtract(BigDecimal.valueOf(rateBase)).divide(BigDecimal.valueOf(levelOne), 1, BigDecimal.ROUND_FLOOR);
                    memberScore1.setRate(rateIncrease.add(BigDecimal.ONE));
                }
                memberScore1.setMemberId(rewardCountDto.getParentId());
                memberScore1.setMemberScore(afterScore);
                memberScore1.setUpdateTime(DateUtils.getUnixTimestamp());
                memberScore1.setCreateTime(DateUtils.getUnixTimestamp());
                memberScoreMapper.insertSelective(memberScore1);
            }
            MemberScoreFlowType memberScoreFlowType1 = memberScoreFlowTypeMapper.selectByPrimaryKey(3L);
            MemberScoreFlow memberScoreFlow1 = new MemberScoreFlow();
            memberScoreFlow1.setMemberId(rewardCountDto.getParentId());
            memberScoreFlow1.setTypeId(10L);
            memberScoreFlow1.setIsFinished(1);
            memberScoreFlow1.setDetail(memberScoreFlowType1.getTypeDesc());
            memberScoreFlow1.setFlowScore(BigDecimal.valueOf(rewardScore));
            memberScoreFlow1.setMemberScoreAfter(memberScore1.getMemberScore());
            memberScoreFlow1.setMemberScoreBefore(beforeAmount);
            memberScoreFlow1.setMemberScoreId(memberScore1.getId());
            memberScoreFlow1.setCreateTime(DateUtils.getUnixTimestamp());
            memberScoreFlowMapper.insertSelective(memberScoreFlow1);
            //处理任务记录问题
            String detail = "您邀请的好友"+rewardCountDto.getMemberName()+"今日完成了任务，平台额外奖励您好友收入的10%，累计"+BigDecimal.valueOf(rewardCountDto.getTotalFlowScore()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"米币";
            memberTaskHistoryService.addMemberTaskToHistory(rewardCountDto.getParentId(), 7L, rewardScore, 1, detail, null,null);
        }
        cacheService.delKeyFromRedis(tableName);
        return rewardCountDtoList.size();
    }
}
