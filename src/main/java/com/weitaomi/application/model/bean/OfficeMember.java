package com.weitaomi.application.model.bean;

import com.weitaomi.application.model.BaseModel;

import javax.persistence.*;
import java.io.Serializable;

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
}