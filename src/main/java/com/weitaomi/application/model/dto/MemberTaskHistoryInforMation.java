package com.weitaomi.application.model.dto;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MemberTaskHistoryInformation {
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务详情
     */
    private String taskDesc;
    /**
     * 任务米币
     */
    private Double pointCount;
    /**
     * 时间
     */
    private Long createTime;

    /**
     * 获取任务名称
     * @return taskName 任务名称
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * 设置任务名称
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取任务详情
     * @return taskDesc 任务详情
     */
    public String getTaskDesc() {
        return this.taskDesc;
    }

    /**
     * 设置任务详情
     * @param taskDesc 任务详情
     */
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * 获取任务米币
     * @return pointCount 任务米币
     */
    public Double getPointCount() {
        return this.pointCount;
    }

    /**
     * 设置任务米币
     * @param pointCount 任务米币
     */
    public void setPointCount(Double pointCount) {
        this.pointCount = pointCount;
    }

    /**
     * 获取时间
     * @return createTime 时间
     */
    public Long getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置时间
     * @param createTime 时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
