package com.weitaomi.application.model.dto;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MemberInvitedRecordInformation {
    /**
     * 用户Id
     */
    private Long memberId;
    /**
     * 邀请者ID
     */
    private Long parentId;
    /**
     * 被邀请者用户名
     */
    private String invitedName;
    /**
     * 邀请时间
     */
    private Long createTime;
    /**
     * 是否奖励已经获得
     */
    private Integer isAccessForInvitor;


    /**
     * 获取用户Id
     * @return memberId 用户Id
     */
    public Long getMemberId() {
        return this.memberId;
    }

    /**
     * 设置用户Id
     * @param memberId 用户Id
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取邀请者用户名
     * @return parentId 邀请者用户名
     */
    public Long getParentId() {
        return this.parentId;
    }

    /**
     * 设置邀请者用户名
     * @param parentId 邀请者用户名
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取被邀请者用户名
     * @return invitedName 被邀请者用户名
     */
    public String getInvitedName() {
        return this.invitedName;
    }

    /**
     * 设置被邀请者用户名
     * @param invitedName 被邀请者用户名
     */
    public void setInvitedName(String invitedName) {
        this.invitedName = invitedName;
    }

    /**
     * 获取邀请时间
     * @return createTime 邀请时间
     */
    public Long getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置邀请时间
     * @param createTime 邀请时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取是否奖励已经获得
     * @return isAccessForInvitor 是否奖励已经获得
     */
    public Integer getIsAccessForInvitor() {
        return this.isAccessForInvitor;
    }

    /**
     * 设置是否奖励已经获得
     * @param isAccessForInvitor 是否奖励已经获得
     */
    public void setIsAccessForInvitor(Integer isAccessForInvitor) {
        this.isAccessForInvitor = isAccessForInvitor;
    }
}
