package com.weitaomi.application.model.bean;

import com.weitaomi.application.model.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "wtm_member_score")
public class MemberScore extends BaseModel {

    /**
     * 用户ID
     */
    @Column(name = "memberId")
    private Long memberId;

    /**
     * 用户总积分
     */
    @Column(name = "memberScore")
    private BigDecimal memberScore;

    /**
     * 不可用积分
     */
    @Column(name = "inValidScore")
    private BigDecimal inValidScore;
    /**
     * 可用米币数
     */
    @Column(name = "avaliableScore")
    private BigDecimal avaliableScore;
    /**
     * 充值当前剩余米币
     */
    @Column(name = "rechargeCurrentScore")
    private BigDecimal rechargeCurrentScore;
    /**
     * 充值历史总米币
     */
    @Column(name = "rechargeTotalScore")
    private BigDecimal rechargeTotalScore;
    /**
     * 可用倍率
     */
    @Column(name = "rate")
    private BigDecimal rate;
    /**
     * 更新日期
     */
    @Column(name = "updateTime")
    private Long updateTime;
    /**
     * 创建日期
     */
    @Column(name = "createTime")
    private Long createTime;

    /**
     * 获取用户ID
     *
     * @return memberId - 用户ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置用户ID
     *
     * @param memberId 用户ID
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取用户总积分
     *
     * @return memberScore - 用户总积分
     */
    public BigDecimal getMemberScore() {
        return memberScore;
    }

    /**
     * 设置用户总积分
     *
     * @param memberScore 用户总积分
     */
    public void setMemberScore(BigDecimal memberScore) {
        this.memberScore = memberScore;
    }

    /**
     * 获取可用积分
     *
     * @return validScore - 可用积分
     */
    public BigDecimal getInValidScore() {
        return inValidScore;
    }

    /**
     * 设置可用积分
     *
     * @param inValidScore 可用积分
     */
    public void setInValidScore(BigDecimal inValidScore) {
        this.inValidScore = inValidScore;
    }

    /**
     * 获取创建日期
     *
     * @return createTime - 创建日期
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新日期
     * @return updateTime 更新日期
     */
    public Long getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新日期
     * @param updateTime 更新日期
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取可用倍率
     * @return rate 可用倍率
     */
    public BigDecimal getRate() {
        return this.rate;
    }

    /**
     * 设置可用倍率
     * @param rate 可用倍率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 获取可用米币数
     * @return avaliableScore 可用米币数
     */
    public BigDecimal getAvaliableScore() {
        return this.avaliableScore;
    }

    /**
     * 设置可用米币数
     * @param avaliableScore 可用米币数
     */
    public void setAvaliableScore(BigDecimal avaliableScore) {
        this.avaliableScore = avaliableScore;
    }

    /**
     * 获取充值当前剩余米币
     * @return rechargeCurrentScore 充值当前剩余米币
     */
    public BigDecimal getRechargeCurrentScore() {
        return this.rechargeCurrentScore;
    }

    /**
     * 设置充值当前剩余米币
     * @param rechargeCurrentScore 充值当前剩余米币
     */
    public void setRechargeCurrentScore(BigDecimal rechargeCurrentScore) {
        this.rechargeCurrentScore = rechargeCurrentScore;
    }

    /**
     * 获取充值历史总米币
     * @return rechargeTotalScore 充值历史总米币
     */
    public BigDecimal getRechargeTotalScore() {
        return this.rechargeTotalScore;
    }

    /**
     * 设置充值历史总米币
     * @param rechargeTotalScore 充值历史总米币
     */
    public void setRechargeTotalScore(BigDecimal rechargeTotalScore) {
        this.rechargeTotalScore = rechargeTotalScore;
    }
}