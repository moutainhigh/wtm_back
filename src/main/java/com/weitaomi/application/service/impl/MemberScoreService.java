package com.weitaomi.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.weitaomi.application.model.bean.*;
import com.weitaomi.application.model.dto.MemberTaskWithDetail;
import com.weitaomi.application.model.dto.OfficialAddAvaliableScore;
import com.weitaomi.application.model.dto.RewardCountDto;
import com.weitaomi.application.model.mapper.*;
import com.weitaomi.application.service.interf.ICacheService;
import com.weitaomi.application.service.interf.IKeyValueService;
import com.weitaomi.application.service.interf.IMemberScoreService;
import com.weitaomi.application.service.interf.IMemberTaskHistoryService;
import com.weitaomi.systemconfig.constant.SystemConfig;
import com.weitaomi.systemconfig.exception.BusinessException;
import com.weitaomi.systemconfig.exception.InfoException;
import com.weitaomi.systemconfig.util.DateUtils;
import com.weitaomi.systemconfig.util.PropertiesUtil;
import com.weitaomi.systemconfig.util.StringUtil;
import com.weitaomi.systemconfig.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

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
    @Autowired
    private OfficeMemberMapper officeMemberMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IKeyValueService keyValueService;
    @Override
    @Transactional
    public MemberScore addMemberScore(Long memberId, Long typeId,Integer isFinished, Double score, String sessionId){
        try{
            if (sessionId == null) {
                throw new BusinessException("幂等性操作，请生成随机数");
            }
            String key = "member:score:" + sessionId;
            String avaliableScoreKey="member:score:type:isAvaliableScore";
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
                    BigDecimal avaliableScoreBefore=BigDecimal.ZERO;
                    BigDecimal avaliableScoreFlow=BigDecimal.ZERO;
                    MemberScore memberScore = memberScoreMapper.getMemberScoreByMemberId(memberId);
                    if (score != null) {
                        BigDecimal rate = BigDecimal.ONE;
                        increaseScore = BigDecimal.valueOf(score).multiply(rate);
                    }
                    MemberScoreFlow memberScoreFlow = new MemberScoreFlow();
                    if (memberScore == null) {
                        if (increaseScore.doubleValue() < 0) {
                            throw new BusinessException("可用积分不足");
                        }
                        memberScore = new MemberScore();
                        memberScore.setMemberId(memberId);
                        memberScore.setMemberScore(increaseScore);
                        //todo
                        boolean flag= cacheService.keyExistInHashTable(avaliableScoreKey,typeId.toString());
                        if (!flag){
                            flag=keyValueService.keyIsExist(avaliableScoreKey,typeId.toString());
                        }
                        if (flag) {
                            memberScore.setAvaliableScore(increaseScore);
                            if (typeId==1){
                                BigDecimal charge=increaseScore;
                                if (charge.doubleValue()>=0){
                                    charge=BigDecimal.ZERO;
                                }
                                memberScore.setInValidScore(charge);
                                memberScore.setRechargeCurrentScore(increaseScore);
                                memberScore.setRechargeTotalScore(increaseScore);
                            }
                        }
                        memberScore.setCreateTime(DateUtils.getUnixTimestamp());
                        memberScore.setUpdateTime(DateUtils.getUnixTimestamp());
                        memberScoreMapper.insertSelective(memberScore);
                    } else {
                        scoreBefore=memberScore.getMemberScore();
                        //总米币
                        BigDecimal afterScore = scoreBefore.add(increaseScore);
                        //可用米币
                        BigDecimal avaliableScore=memberScore.getAvaliableScore();
                        avaliableScoreBefore=avaliableScore;
                        //充值米币（current）
                        BigDecimal rechargeCurrentScore=memberScore.getRechargeCurrentScore();
                        //充值米币（total）
                        BigDecimal rechargeTotalScore=memberScore.getRechargeTotalScore();
                        boolean flag= cacheService.keyExistInHashTable(avaliableScoreKey,typeId.toString());
                        if (!flag){
                            flag=keyValueService.keyIsExist(avaliableScoreKey,typeId.toString());
                        }
                        if (flag) {
                            avaliableScore = avaliableScore.add(increaseScore);
                            avaliableScoreFlow=increaseScore;
                            if (typeId==1) {
                                rechargeTotalScore = rechargeTotalScore.add(increaseScore);
                                rechargeCurrentScore = rechargeCurrentScore.add(increaseScore);
                            }else if (typeId==4||typeId==5){
                                if (increaseScore.doubleValue()<0&&increaseScore.abs().doubleValue()<=rechargeCurrentScore.doubleValue()){
                                    rechargeCurrentScore=rechargeCurrentScore.add(increaseScore);
                                }else if (increaseScore.doubleValue()<0&&increaseScore.abs().doubleValue()>rechargeCurrentScore.doubleValue()){
                                    rechargeCurrentScore=BigDecimal.ZERO;
                                }
                            }
                        }
                        if (afterScore.doubleValue() < 0) {
                            throw new BusinessException("可用积分不足");
                        }
                        String memberScoreKey="member:score:type:isMemberScore";
                        boolean memberScoreAvaliable= cacheService.keyExistInHashTable(memberScoreKey,typeId.toString());
                        if (!memberScoreAvaliable){
                            memberScoreAvaliable=keyValueService.keyIsExist(memberScoreKey,typeId.toString());
                        }
                        if (memberScoreAvaliable) {
                            memberScore.setMemberScore(afterScore);
                        }
                        memberScore.setAvaliableScore(avaliableScore);
                        memberScore.setRechargeCurrentScore(rechargeCurrentScore);
                        memberScore.setRechargeTotalScore(rechargeTotalScore);
                        memberScore.setRate(BigDecimal.ONE);
                        memberScore.setUpdateTime(DateUtils.getUnixTimestamp());
                        memberScoreMapper.updateByPrimaryKeySelective(memberScore);
                    }
                    memberScoreFlow.setMemberId(memberId);
                    memberScoreFlow.setTypeId(typeId);
                    memberScoreFlow.setIsFinished(isFinished);
                    memberScoreFlow.setFlowScore(increaseScore);
                    memberScoreFlow.setMemberScoreAfter(memberScore.getMemberScore());
                    memberScoreFlow.setMemberScoreBefore(scoreBefore);
                    memberScoreFlow.setAvaliableScoreBefore(avaliableScoreBefore);
                    memberScoreFlow.setAvaliableScoreAfter(memberScore.getAvaliableScore());
                    memberScoreFlow.setAvaliableFlowScore(avaliableScoreFlow);
                    memberScoreFlow.setMemberScoreId(memberScore.getId());
                    memberScoreFlow.setCreateTime(DateUtils.getUnixTimestamp());
                    Boolean flag = memberScoreFlowMapper.insertSelective(memberScoreFlow) > 0 ? true : false;
                    if (!flag) {
                        throw new BusinessException("积分记录失败");
                    }
                    cacheService.setCacheByKey(key, memberScore, 60);
                    String table="member:score:type:isAvaliableToSuper";
                    boolean flagTemp= cacheService.keyExistInHashTable(table,typeId.toString());
                    if (!flagTemp){
                        flagTemp=keyValueService.keyIsExist(table,typeId.toString());
                    }
                    if (flagTemp&&increaseScore.doubleValue()>0) {
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
        }catch (Exception e){
            logger.info("米币流动时出现异常");
        }
        return null;
    }
    @Override
    public Integer updateExtraRewardTimer(){
        String tableName="member:extra:reward";
        Set<String> keys=cacheService.getAllKeysFromHashTable(tableName);
        List<RewardCountDto> rewardCountDtoList=cacheService.getFromHashTable(tableName,new ArrayList<String>(keys));
        for (RewardCountDto rewardCountDto:rewardCountDtoList){
            dealUpdateExtraReward(rewardCountDto);
        }
        cacheService.delKeyFromRedis(tableName);
        return rewardCountDtoList.size();
    }

    @Override
    public Integer addOfficialAccountScoreToAvaliable(){
        //todo  时间灵活修改
        List<OfficialAddAvaliableScore> officialAddAvaliableScoreList = officeMemberMapper.getOfficialAddAvaliableScoreList(7*24*60*60L);
        List<Long> idList=new ArrayList<>();
        Integer number=0;
        if (!officialAddAvaliableScoreList.isEmpty()) {
            for (OfficialAddAvaliableScore officialAddAvaliableScore : officialAddAvaliableScoreList) {
                MemberScore memberScore = this.addMemberScore(officialAddAvaliableScore.getMemberId(), 17L, 1, officialAddAvaliableScore.getScore(), UUIDGenerator.generate());
                if (memberScore != null) {
                    idList.add(officialAddAvaliableScore.getId());
                }
            }
             number = officeMemberMapper.updateOfficialMemberForAvaliable(idList);
        }
        return number;
    }
    private void dealUpdateExtraReward(RewardCountDto rewardCountDto){
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
            memberScore1.setAvaliableScore(memberScore1.getAvaliableScore().add(BigDecimal.valueOf(rewardScore)));
            memberScore1.setRate(BigDecimal.ONE);
            memberScore1.setUpdateTime(DateUtils.getUnixTimestamp());
            memberScoreMapper.updateByPrimaryKeySelective(memberScore1);
        }else {
            memberScore1=new MemberScore();
            BigDecimal afterScore = beforeAmount.add(BigDecimal.valueOf(rewardScore));
            memberScore1.setRate(BigDecimal.ONE);
            memberScore1.setMemberId(rewardCountDto.getParentId());
            memberScore1.setMemberScore(afterScore);
            memberScore1.setAvaliableScore(afterScore);
            memberScore1.setUpdateTime(DateUtils.getUnixTimestamp());
            memberScore1.setCreateTime(DateUtils.getUnixTimestamp());
            memberScoreMapper.insertSelective(memberScore1);
        }
        MemberScoreFlowType memberScoreFlowType1 = memberScoreFlowTypeMapper.selectByPrimaryKey(3L);
        MemberScoreFlow memberScoreFlow1 = new MemberScoreFlow();
        memberScoreFlow1.setMemberId(rewardCountDto.getParentId());
        memberScoreFlow1.setTypeId(10L);
        memberScoreFlow1.setIsFinished(1);
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
@Override
    public void test(Long i){
    System.out.println("===========>");
        MemberScore account=memberScoreMapper.selectByPrimaryKey(i);
        System.out.println(account.getMemberScore()+"===="+Thread.currentThread().getName());
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
}
