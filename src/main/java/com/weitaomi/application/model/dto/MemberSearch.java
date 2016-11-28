package com.weitaomi.application.model.dto;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MemberSearch {
    /**
     * 用户名
     */
    private String memberName;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 用户联系方式
     */
    private String telephone;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 注册查询时间开始
     */
    private Long registerStartTime;
    /**
     * 注册查询结束时间
     */
    private Long registerEndTime;
    /**
     * 页码
     */
    private Integer pageIndex=1;
    /**
     * 页码大小
     */
    private Integer pageSize=10;
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
     * 获取用户联系方式
     * @return telephone 用户联系方式
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * 设置用户联系方式
     * @param telephone 用户联系方式
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取用户ID
     * @return memberId 用户ID
     */
    public Long getMemberId() {
        return this.memberId;
    }

    /**
     * 设置用户ID
     * @param memberId 用户ID
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取注册查询时间开始
     * @return registerStartTime 注册查询时间开始
     */
    public Long getRegisterStartTime() {
        return this.registerStartTime;
    }

    /**
     * 设置注册查询时间开始
     * @param registerStartTime 注册查询时间开始
     */
    public void setRegisterStartTime(Long registerStartTime) {
        this.registerStartTime = registerStartTime;
    }

    /**
     * 获取注册查询结束时间
     * @return registerEndTime 注册查询结束时间
     */
    public Long getRegisterEndTime() {
        return this.registerEndTime;
    }

    /**
     * 设置注册查询结束时间
     * @param registerEndTime 注册查询结束时间
     */
    public void setRegisterEndTime(Long registerEndTime) {
        this.registerEndTime = registerEndTime;
    }


    /**
     * 获取页码
     * @return pageIndex 页码
     */
    public Integer getPageIndex() {
        return this.pageIndex;
    }

    /**
     * 设置页码
     * @param pageIndex 页码
     */
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 获取页码大小
     * @return pageSize 页码大小
     */
    public Integer getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置页码大小
     * @param pageSize 页码大小
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
