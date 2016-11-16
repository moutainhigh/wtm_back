package com.weitaomi.application.model.bean;

import com.weitaomi.application.model.BaseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "wtm_office_member")
public class OfficeMember extends BaseModel implements Serializable{

    /**
     * 公众号ID
     */
    @Column(name = "officeAccountId")
    private Long officeAccountId;

    /**
     * 会员ID
     */
    @Column(name = "memberId")
    private Long memberId;

    /**
     * 当前是否还在关注  0 ：未关注， 1 ：关注
     */
    @Column(name = "isAccessNow")
    private Integer isAccessNow;
    /**
     * 公众号分配给用户的openid
     */
    @Column(name = "openId")
    private String openId;
    /**
     * 是否已经奖励
     */
    @Column(name = "isScoreAccess")
    private Integer isScoreAccess;
    /**
     *  关注奖励
     */
    @Column(name = "addRewarScore")
    private BigDecimal addRewarScore;
    /**
     * 完成时间
     */
    @Column(name = "finishedTime")
    private Long finishedTime;
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
     * 获取公众号ID
     *
     * @return officeAccountId - 公众号ID
     */
    public Long getOfficeAccountId() {
        return officeAccountId;
    }

    /**
     * 设置公众号ID
     *
     * @param officeAccountId 公众号ID
     */
    public void setOfficeAccountId(Long officeAccountId) {
        this.officeAccountId = officeAccountId;
    }

    /**
     * 获取会员ID
     *
     * @return memberId - 会员ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置会员ID
     *
     * @param memberId 会员ID
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取当前是否还在关注  0 ：未关注， 1 ：关注
     *
     * @return isAccessNow - 当前是否还在关注  0 ：未关注， 1 ：关注
     */
    public Integer getIsAccessNow() {
        return isAccessNow;
    }

    /**
     * 设置当前是否还在关注  0 ：未关注， 1 ：关注
     *
     * @param isAccessNow 当前是否还在关注  0 ：未关注， 1 ：关注
     */
    public void setIsAccessNow(Integer isAccessNow) {
        this.isAccessNow = isAccessNow;
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
     * 获取完成时间
     * @return finishedTime 完成时间
     */
    public Long getFinishedTime() {
        return this.finishedTime;
    }

    /**
     * 设置完成时间
     * @param finishedTime 完成时间
     */
    public void setFinishedTime(Long finishedTime) {
        this.finishedTime = finishedTime;
    }

    /**
     * 获取公众号分配给用户的openid
     * @return openId 公众号分配给用户的openid
     */
    public String getOpenId() {
        return this.openId;
    }

    /**
     * 设置公众号分配给用户的openid
     * @param openId 公众号分配给用户的openid
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取是否已经奖励
     * @return isScoreAccess 是否已经奖励
     */
    public Integer getIsScoreAccess() {
        return this.isScoreAccess;
    }

    /**
     * 设置是否已经奖励
     * @param isScoreAccess 是否已经奖励
     */
    public void setIsScoreAccess(Integer isScoreAccess) {
        this.isScoreAccess = isScoreAccess;
    }

    /**
     * 获取 关注奖励
     * @return addRewarScore  关注奖励
     */
    public BigDecimal getAddRewarScore() {
        return this.addRewarScore;
    }

    /**
     * 设置 关注奖励
     * @param addRewarScore  关注奖励
     */
    public void setAddRewarScore(BigDecimal addRewarScore) {
        this.addRewarScore = addRewarScore;
    }
}