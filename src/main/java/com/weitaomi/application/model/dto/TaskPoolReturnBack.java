package com.weitaomi.application.model.dto;

/**
 * Created by Administrator on 2016/11/29.
 */
public class TaskPoolReturnBack {
    /**
     * 任务ID
     */
    private Long taskPoolId;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 需求数
     */
    private Integer needNumber;
    /**
     * 公众号名称
     */
    private String username;
    /**
     * 实际剩余数
     */
    private Integer realityNumber;
    /**
     * 单次关注奖励
     */
    private Double singleScore;
    /**
     * 任务奖励rate
     */
    private Double rate;

    /**
     * 获取任务ID
     * @return taskPoolId 任务ID
     */
    public Long getTaskPoolId() {
        return this.taskPoolId;
    }

    /**
     * 设置任务ID
     * @param taskPoolId 任务ID
     */
    public void setTaskPoolId(Long taskPoolId) {
        this.taskPoolId = taskPoolId;
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
     * 获取需求数
     * @return needNumber 需求数
     */
    public Integer getNeedNumber() {
        return this.needNumber;
    }

    /**
     * 设置需求数
     * @param needNumber 需求数
     */
    public void setNeedNumber(Integer needNumber) {
        this.needNumber = needNumber;
    }

    /**
     * 获取实际剩余数
     * @return realityNumber 实际剩余数
     */
    public Integer getRealityNumber() {
        return this.realityNumber;
    }

    /**
     * 设置实际剩余数
     * @param realityNumber 实际剩余数
     */
    public void setRealityNumber(Integer realityNumber) {
        this.realityNumber = realityNumber;
    }

    /**
     * 获取单次关注奖励
     * @return singleScore 单次关注奖励
     */
    public Double getSingleScore() {
        return this.singleScore;
    }

    /**
     * 设置单次关注奖励
     * @param singleScore 单次关注奖励
     */
    public void setSingleScore(Double singleScore) {
        this.singleScore = singleScore;
    }

    /**
     * 获取任务奖励rate
     * @return rate 任务奖励rate
     */
    public Double getRate() {
        return this.rate;
    }

    /**
     * 设置任务奖励rate
     * @param rate 任务奖励rate
     */
    public void setRate(Double rate) {
        this.rate = rate;
    }

    /**
     * 获取公众号名称
     * @return username 公众号名称
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置公众号名称
     * @param username 公众号名称
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
