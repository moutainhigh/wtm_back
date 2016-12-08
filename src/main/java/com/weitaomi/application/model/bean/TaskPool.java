package com.weitaomi.application.model.bean;

import com.weitaomi.application.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Table(name = "wtm_task_pool")
public class TaskPool extends BaseModel {

    /**
     * 任务类型 0：求粉，1：阅读
     */
    @Column(name = "taskType")
    private Integer taskType;
    /**
     * 用户ID
     */
    @Column(name = "memberId")
    private Long memberId;
    /**
     * 时间限制
     */
    @Column(name = "limitDay")
    private Long limitDay;
    /**
     * 公众号ID
     */
    @Column(name = "officialAccountsId")
    private Long officialAccountsId;
    /**
     * 文章Id
     */
    @Column(name = "articleId")
    private Long articleId;
    /**
     * 任务总支出米币
     */
    @Column(name = "totalScore")
    private Double totalScore;
    /**
     * 任务总支出米币
     */
    @Column(name = "rate")
    private BigDecimal rate;
    /**
     * 单次奖励
     */
    @Column(name = "singleScore")
    private Double singleScore;
    /**
     * 完成奖励
     */
    @Column(name = "finishScore")
    private Double finishScore;
    /**
     * 请求群体性别
     */
    @Column(name = "sex")
    private Integer sex;


    /**
     * 省级地区
     */
    @Column(name = "provinceCode")
    private String provinceCode;

    /**
     * 市级地区
     */
    @Column(name = "cityCode")
    private String cityCode;

    /**
     * 现在是否上架
     */
    @Column(name = "isPublishNow")
    private Integer isPublishNow;
    /**
     * 需求数量
     */
    @Column(name = "isAutoPublishToOthers")
    private Integer isAutoPublishToOthers;
    /**
     * 需求数量
     */
    @Column(name = "needNumber")
    private Integer needNumber;
    /**
     * 实际增长数
     */
    @Column(name = "realityNumber")
    private Integer realityNumber;

    /**
     * 创建日期
     */
    @Column(name = "createTime")
    private Long createTime;
    @Transient
    private String randomKey;
    /**
     * 获取任务类型 0：求粉，1：阅读
     *
     * @return taskType - 任务类型 0：求粉，1：阅读
     */
    public Integer getTaskType() {
        return taskType;
    }

    /**
     * 设置任务类型 0：求粉，1：阅读
     *
     * @param taskType 任务类型 0：求粉，1：阅读
     */
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    /**
     * 获取任务总支出米币
     *
     * @return totalScore - 任务总支出米币
     */
    public Double getTotalScore() {
        return totalScore;
    }

    /**
     * 设置任务总支出米币
     *
     * @param totalScore 任务总支出米币
     */
    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 获取需求数量
     *
     * @return needNumber - 需求数量
     */
    public Integer getNeedNumber() {
        return needNumber;
    }

    /**
     * 设置需求数量
     *
     * @param needNumber 需求数量
     */
    public void setNeedNumber(Integer needNumber) {
        this.needNumber = needNumber;
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
     * 获取公众号ID
     * @return officeAccountId 公众号ID
     */
    public Long getOfficialAccountsId() {
        return this.officialAccountsId;
    }

    /**
     * 设置公众号ID
     * @param officialAccountsId 公众号ID
     */
    public void setOfficialAccountsId(Long officialAccountsId) {
        this.officialAccountsId = officialAccountsId;
    }

    /**
     * 获取文章Id
     * @return articleId 文章Id
     */
    public Long getArticleId() {
        return this.articleId;
    }

    /**
     * 设置文章Id
     * @param articleId 文章Id
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * 获取单次奖励
     * @return singleScore 单次奖励
     */
    public Double getSingleScore() {
        return this.singleScore;
    }

    /**
     * 设置单次奖励
     * @param singleScore 单次奖励
     */
    public void setSingleScore(Double singleScore) {
        this.singleScore = singleScore;
    }

    /**
     * 获取请求群体性别
     * @return sex 请求群体性别
     */
    public Integer getSex() {
        return this.sex;
    }

    /**
     * 设置请求群体性别
     * @param sex 请求群体性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取省级地区
     * @return provinceCode 省级地区
     */
    public String getProvinceCode() {
        return this.provinceCode;
    }

    /**
     * 设置省级地区
     * @param provinceCode 省级地区
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    /**
     * 获取市级地区
     * @return cityCode 市级地区
     */
    public String getCityCode() {
        return this.cityCode;
    }

    /**
     * 设置市级地区
     * @param cityCode 市级地区
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 获取现在是否上架
     * @return isPublishNow 现在是否上架
     */
    public Integer getIsPublishNow() {
        return this.isPublishNow;
    }

    /**
     * 设置现在是否上架
     * @param isPublishNow 现在是否上架
     */
    public void setIsPublishNow(Integer isPublishNow) {
        this.isPublishNow = isPublishNow;
    }

    /**
     * 获取需求数量
     * @return isAutoPublishToOthers 需求数量
     */
    public Integer getIsAutoPublishToOthers() {
        return this.isAutoPublishToOthers;
    }

    /**
     * 设置需求数量
     * @param isAutoPublishToOthers 需求数量
     */
    public void setIsAutoPublishToOthers(Integer isAutoPublishToOthers) {
        this.isAutoPublishToOthers = isAutoPublishToOthers;
    }

    /**
     * 获取任务总支出米币
     * @return rate 任务总支出米币
     */
    public BigDecimal getRate() {
        return this.rate;
    }

    /**
     * 设置任务总支出米币
     * @param rate 任务总支出米币
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
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
     * 获取时间限制
     * @return limitDay 时间限制
     */
    public Long getLimitDay() {
        return this.limitDay;
    }

    /**
     * 设置时间限制
     * @param limitDay 时间限制
     */
    public void setLimitDay(Long limitDay) {
        this.limitDay = limitDay;
    }

    public String getRandomKey() {
        return this.randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    /**
     * 获取实际增长数
     * @return realityNumber 实际增长数
     */
    public Integer getRealityNumber() {
        return this.realityNumber;
    }

    /**
     * 设置实际增长数
     * @param realityNumber 实际增长数
     */
    public void setRealityNumber(Integer realityNumber) {
        this.realityNumber = realityNumber;
    }

    /**
     * 获取完成奖励
     * @return finishScore 完成奖励
     */
    public Double getFinishScore() {
        return this.finishScore;
    }

    /**
     * 设置完成奖励
     * @param finishScore 完成奖励
     */
    public void setFinishScore(Double finishScore) {
        this.finishScore = finishScore;
    }
}