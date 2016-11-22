package com.weitaomi.application.service.interf;

import com.weitaomi.application.model.bean.MemberScore;
import com.weitaomi.application.model.bean.MemberScoreFlow;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by supumall on 2016/7/8.
 */
public interface IMemberScoreService {
    /**
     * 增加的积分类型
     * @param memberId
     * @param typeId
     * @param score
     * @param sessionId
     * @return
     */
    MemberScore addMemberScore(Long memberId,Long typeId,Integer isFinished,Double score,String sessionId);

    /**
     * 定时持久化前日平台奖励
     * @return
     */
    Integer updateExtraRewardTimer();

    Integer addOfficialAccountScoreToAvaliable();

    void test(Long i);
}
