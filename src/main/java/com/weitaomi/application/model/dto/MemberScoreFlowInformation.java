package com.weitaomi.application.model.dto;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MemberScoreFlowInformation {
    /**
     * 积分变动类型
     */
    private String typeName;
    /**
     * 积分变动类型详情
     */
    private String typeDesc;
    /**
     * 是否已经完成
     */
    private Integer isFinished;

    /**
     * 用户流动前积分
     */
    private BigDecimal memberScoreBefore;

    /**
     * 用户流动后积分
     */
    private BigDecimal memberScoreAfter;

    /**
     * 流动积分
     */
    private BigDecimal flowScore;

    /**
     * 创建日期
     */
    private Long createTime;



    /**
     * 获取是否已经完成
     * @return isFinished 是否已经完成
     */
    public Integer getIsFinished() {
        return this.isFinished;
    }

    /**
     * 设置是否已经完成
     * @param isFinished 是否已经完成
     */
    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * 获取用户流动前积分
     * @return memberScoreBefore 用户流动前积分
     */
    public BigDecimal getMemberScoreBefore() {
        return this.memberScoreBefore;
    }

    /**
     * 设置用户流动前积分
     * @param memberScoreBefore 用户流动前积分
     */
    public void setMemberScoreBefore(BigDecimal memberScoreBefore) {
        this.memberScoreBefore = memberScoreBefore;
    }

    /**
     * 获取用户流动后积分
     * @return memberScoreAfter 用户流动后积分
     */
    public BigDecimal getMemberScoreAfter() {
        return this.memberScoreAfter;
    }

    /**
     * 设置用户流动后积分
     * @param memberScoreAfter 用户流动后积分
     */
    public void setMemberScoreAfter(BigDecimal memberScoreAfter) {
        this.memberScoreAfter = memberScoreAfter;
    }

    /**
     * 获取流动积分
     * @return flowScore 流动积分
     */
    public BigDecimal getFlowScore() {
        return this.flowScore;
    }

    /**
     * 设置流动积分
     * @param flowScore 流动积分
     */
    public void setFlowScore(BigDecimal flowScore) {
        this.flowScore = flowScore;
    }

    /**
     * 获取创建日期
     * @return createTime 创建日期
     */
    public Long getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建日期
     * @param createTime 创建日期
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取积分变动类型
     * @return typeName 积分变动类型
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * 设置积分变动类型
     * @param typeName 积分变动类型
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取积分变动类型详情
     * @return typeDesc 积分变动类型详情
     */
    public String getTypeDesc() {
        return this.typeDesc;
    }

    /**
     * 设置积分变动类型详情
     * @param typeDesc 积分变动类型详情
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
