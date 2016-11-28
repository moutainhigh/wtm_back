package com.weitaomi.application.model.bean;

import com.weitaomi.application.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "wtm_member_invited_record")
public class MemberInvitedRecord extends BaseModel {

    /**
     * 被邀请用户ID
     */
    @Column(name = "memberId")
    private Long memberId;

    /**
     * 邀请用户
     */
    @Column(name = "parentId")
    private Long parentId;
    /**
     * 该用户是否为有效邀请人
     */
    @Column(name = "isAccessible")
    private Integer isAccessible;
    /**
     * 是否奖励邀请者
     */
    @Column(name = "isAccessForInvitor")
    private Integer isAccessForInvitor;
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
     * 获取被邀请用户ID
     *
     * @return memberId - 被邀请用户ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置被邀请用户ID
     *
     * @param memberId 被邀请用户ID
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取邀请用户
     *
     * @return parentId - 邀请用户
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置邀请用户
     *
     * @param parentId 邀请用户
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
     * 获取该用户是否为有效邀请人
     * @return isAccessible 该用户是否为有效邀请人
     */
    public Integer getIsAccessible() {
        return this.isAccessible;
    }

    /**
     * 设置该用户是否为有效邀请人
     * @param isAccessible 该用户是否为有效邀请人
     */
    public void setIsAccessible(Integer isAccessible) {
        this.isAccessible = isAccessible;
    }

    /**
     * 获取是否奖励邀请者
     * @return isAccessForInvitor 是否奖励邀请者
     */
    public Integer getIsAccessForInvitor() {
        return this.isAccessForInvitor;
    }

    /**
     * 设置是否奖励邀请者
     * @param isAccessForInvitor 是否奖励邀请者
     */
    public void setIsAccessForInvitor(Integer isAccessForInvitor) {
        this.isAccessForInvitor = isAccessForInvitor;
    }
}