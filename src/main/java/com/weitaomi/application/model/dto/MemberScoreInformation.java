package com.weitaomi.application.model.dto;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MemberScoreInformation {
    /**
     * 总米币
     */
    private Double totalScore;
    /**
     * 可用米币数
     */
    private Double avaliableScore;
    /**
     * 当前充值米币数
     */
    private Double currentChargeScore;
    /**
     * 充值总米币数
     */
    private Double totalChargeScore;
    /**
     * 即将可得关注米币
     */
    private Double futureAvaliableFollowScore;
    /**
     * 即将可得邀请奖励
     */
    private Double futureAvaliableInvitedScore;

    /**
     * 获取总米币
     * @return totalScore 总米币
     */
    public Double getTotalScore() {
        return this.totalScore;
    }

    /**
     * 设置总米币
     * @param totalScore 总米币
     */
    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 获取可用米币数
     * @return avaliableScore 可用米币数
     */
    public Double getAvaliableScore() {
        return this.avaliableScore;
    }

    /**
     * 设置可用米币数
     * @param avaliableScore 可用米币数
     */
    public void setAvaliableScore(Double avaliableScore) {
        this.avaliableScore = avaliableScore;
    }

    /**
     * 获取当前充值米币数
     * @return currentChargeScore 当前充值米币数
     */
    public Double getCurrentChargeScore() {
        return this.currentChargeScore;
    }

    /**
     * 设置当前充值米币数
     * @param currentChargeScore 当前充值米币数
     */
    public void setCurrentChargeScore(Double currentChargeScore) {
        this.currentChargeScore = currentChargeScore;
    }

    /**
     * 获取充值总米币数
     * @return totalChargeScore 充值总米币数
     */
    public Double getTotalChargeScore() {
        return this.totalChargeScore;
    }

    /**
     * 设置充值总米币数
     * @param totalChargeScore 充值总米币数
     */
    public void setTotalChargeScore(Double totalChargeScore) {
        this.totalChargeScore = totalChargeScore;
    }

    /**
     * 获取即将可得关注米币
     * @return futureAvaliableFollowScore 即将可得关注米币
     */
    public Double getFutureAvaliableFollowScore() {
        return this.futureAvaliableFollowScore;
    }

    /**
     * 设置即将可得关注米币
     * @param futureAvaliableFollowScore 即将可得关注米币
     */
    public void setFutureAvaliableFollowScore(Double futureAvaliableFollowScore) {
        this.futureAvaliableFollowScore = futureAvaliableFollowScore;
    }

    /**
     * 获取即将可得邀请奖励
     * @return futureAvaliableInvitedScore 即将可得邀请奖励
     */
    public Double getFutureAvaliableInvitedScore() {
        return this.futureAvaliableInvitedScore;
    }

    /**
     * 设置即将可得邀请奖励
     * @param futureAvaliableInvitedScore 即将可得邀请奖励
     */
    public void setFutureAvaliableInvitedScore(Double futureAvaliableInvitedScore) {
        this.futureAvaliableInvitedScore = futureAvaliableInvitedScore;
    }
}
