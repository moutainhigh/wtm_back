package com.weitaomi.application.model.bean;

import com.weitaomi.application.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "wtm_task_fail_pushToWechat")
public class TaskFailPushToWechat extends BaseModel {

    /**
     * 类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 是否成功
     */
    @Column(name = "isPushToWechat")
    private Integer isPushToWechat;

    /**
     * 参数
     */
    @Column(name = "params")
    private String params;

    /**
     * 请求地址
     */
    @Column(name = "postUrl")
    private String postUrl;

    /**
     * 时间戳
     */
    @Column(name = "createTime")
    private Long createTime;

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取是否成功
     *
     * @return isPushToWechat - 是否成功
     */
    public Integer getIsPushToWechat() {
        return isPushToWechat;
    }

    /**
     * 设置是否成功
     *
     * @param isPushToWechat 是否成功
     */
    public void setIsPushToWechat(Integer isPushToWechat) {
        this.isPushToWechat = isPushToWechat;
    }

    /**
     * 获取参数
     *
     * @return params - 参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置参数
     *
     * @param params 参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取请求地址
     *
     * @return postUrl - 请求地址
     */
    public String getPostUrl() {
        return postUrl;
    }

    /**
     * 设置请求地址
     *
     * @param postUrl 请求地址
     */
    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    /**
     * 获取时间戳
     *
     * @return createTime - 时间戳
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 设置时间戳
     *
     * @param createTime 时间戳
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}