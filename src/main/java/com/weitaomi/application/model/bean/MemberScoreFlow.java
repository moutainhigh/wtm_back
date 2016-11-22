package com.weitaomi.application.model.bean;

import com.weitaomi.application.model.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "wtm_member_score_flow")
public class MemberScoreFlow extends BaseModel {
    /**
     * 用户积分ID
     */
    @Column(name = "memberScoreId")
    private Long memberScoreId;

    /**
     * 用户ID
     */
    @Column(name = "memberId")
    private Long memberId;

    /**
     * 积分变动类型
     */
    @Column(name = "typeId")
    private Long typeId;
    /**
     * 是否已经完成
     */
    @Column(name = "isFinished")
    private Integer isFinished;

    /**
     * 用户流动前积分
     */
    @Column(name = "memberScoreBefore")
    private BigDecimal memberScoreBefore;

    /**
     * 用户流动后积分
     */
    @Column(name = "memberScoreAfter")
    private BigDecimal memberScoreAfter;

    /**
     * 流动积分
     */
    @Column(name = "flowScore")
    private BigDecimal flowScore;

    /**
     * 创建日期
     */
    @Column(name = "createTime")
    private Long createTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户积分ID
     *
     * @return memberScoreId - 用户积分ID
     */
    public Long getMemberScoreId() {
        return memberScoreId;
    }

    /**
     * 设置用户积分ID
     *
     * @param memberScoreId 用户积分ID
     */
    public void setMemberScoreId(Long memberScoreId) {
        this.memberScoreId = memberScoreId;
    }

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
     * 获取积分变动类型
     *
     * @return typeId - 积分变动类型
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * 设置积分变动类型
     *
     * @param typeId 积分变动类型
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取用户流动前积分
     *
     * @return memberScoreBefore - 用户流动前积分
     */
    public BigDecimal getMemberScoreBefore() {
        return memberScoreBefore;
    }

    /**
     * 设置用户流动前积分
     *
     * @param memberScoreBefore 用户流动前积分
     */
    public void setMemberScoreBefore(BigDecimal memberScoreBefore) {
        this.memberScoreBefore = memberScoreBefore;
    }

    /**
     * 获取用户流动后积分
     *
     * @return memberScoreAfter - 用户流动后积分
     */
    public BigDecimal getMemberScoreAfter() {
        return memberScoreAfter;
    }

    /**
     * 设置用户流动后积分
     *
     * @param memberScoreAfter 用户流动后积分
     */
    public void setMemberScoreAfter(BigDecimal memberScoreAfter) {
        this.memberScoreAfter = memberScoreAfter;
    }

    /**
     * 获取流动积分
     *
     * @return flowScore - 流动积分
     */
    public BigDecimal getFlowScore() {
        return flowScore;
    }

    /**
     * 设置流动积分
     *
     * @param flowScore 流动积分
     */
    public void setFlowScore(BigDecimal flowScore) {
        this.flowScore = flowScore;
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
     * 获取积分变动类型
     * @return isFinished 积分变动类型
     */
    public Integer getIsFinished() {
        return this.isFinished;
    }

    /**
     * 设置积分变动类型
     * @param isFinished 积分变动类型
     */
    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }
}