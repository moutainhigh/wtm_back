package com.weitaomi.application.model.dto;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MemberSearchInformation {
    /**
     * 用户Id
     */
    private Long memberId;
    /**
     * 用户名
     */
    private String memberName;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 手机号码
     */
    private String telephone;
    /**
     * 性别 0：保密，1：男  2：女 3：性别不明（禁用中）
     */
    private Integer sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 用户来源
     */
    private String source;
    /**
     * 注册时间
     */
    private Long createTime;
    /**
     * 是否已经禁用
     */
    private Integer isForbidden;

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
     * 获取用户名
     * @return memberName 用户名
     */
    public String getMemberName() {
        return this.memberName;
    }

    /**
     * 设置用户名
     * @param memberName 用户名
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * 获取手机号码
     * @return telephone 手机号码
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * 设置手机号码
     * @param telephone 手机号码
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取性别 0：保密，1：男  2：女 3：性别不明（禁用中）
     * @return sex 性别 0：保密，1：男  2：女 3：性别不明（禁用中）
     */
    public Integer getSex() {
        return this.sex;
    }

    /**
     * 设置性别 0：保密，1：男  2：女 3：性别不明（禁用中）
     * @param sex 性别 0：保密，1：男  2：女 3：性别不明（禁用中）
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取地址
     * @return address 地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置地址
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取邀请码
     * @return inviteCode 邀请码
     */
    public String getInviteCode() {
        return this.inviteCode;
    }

    /**
     * 设置邀请码
     * @param inviteCode 邀请码
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * 获取注册时间
     * @return createTime 注册时间
     */
    public Long getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置注册时间
     * @param createTime 注册时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取是否已经禁用
     * @return isForbidden 是否已经禁用
     */
    public Integer getIsForbidden() {
        return this.isForbidden;
    }

    /**
     * 设置是否已经禁用
     * @param isForbidden 是否已经禁用
     */
    public void setIsForbidden(Integer isForbidden) {
        this.isForbidden = isForbidden;
    }

    /**
     * 获取微信昵称
     * @return nickName 微信昵称
     */
    public String getNickName() {
        return this.nickName;
    }

    /**
     * 设置微信昵称
     * @param nickName 微信昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取用户来源
     * @return source 用户来源
     */
    public String getSource() {
        return this.source;
    }

    /**
     * 设置用户来源
     * @param source 用户来源
     */
    public void setSource(String source) {
        this.source = source;
    }
}
