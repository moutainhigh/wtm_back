package com.weitaomi.application.model.bean;

import com.weitaomi.application.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "wtm_payment_approve")
public class PaymentApprove  extends BaseModel {
    /**
     * 用户ID
     */
    @Column(name = "memberId")
    private Long memberId;
    /**
     * 用户ID
     */
    @Column(name = "payType")
    private Integer payType;
    /**
     * 收款账号
     */
    @Column(name = "accountNumber")
    private String accountNumber;

    /**
     * 收款人姓名
     */
    @Column(name = "accountName")
    private String accountName;

    /**
     * 提现金额
     */
    @Column(name = "amount")
    private BigDecimal amount;
    /**
     * 提现手续费
     */
    @Column(name = "extraAmount")
    private BigDecimal extraAmount;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 是否提现成功标识位 0：未成功
     */
    @Column(name = "isPaid")
    private Integer isPaid;

    /**
     * 创建日期
     */
    @Column(name = "createTime")
    private Long createTime;
    /**
     * 获取用户ID
     *
     * @return memberId - 用户ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置用户ID
     *
     * @param memberId 用户ID
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取收款账号
     *
     * @return accountNumber - 收款账号
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 设置收款账号
     *
     * @param accountNumber 收款账号
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 获取收款人姓名
     *
     * @return accountName - 收款人姓名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置收款人姓名
     *
     * @param accountName 收款人姓名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取提现金额
     *
     * @return amount - 提现金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置提现金额
     *
     * @param amount 提现金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取是否提现成功标识位 0：未成功
     *
     * @return isPaid - 是否提现成功标识位 0：未成功
     */
    public Integer getIsPaid() {
        return isPaid;
    }

    /**
     * 设置是否提现成功标识位 0：未成功
     *
     * @param isPaid 是否提现成功标识位 0：未成功
     */
    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
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
     * 获取用户ID
     * @return payType 用户ID
     */
    public Integer getPayType() {
        return this.payType;
    }

    /**
     * 设置用户ID
     * @param payType 用户ID
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取提现手续费
     * @return extraAmount 提现手续费
     */
    public BigDecimal getExtraAmount() {
        return this.extraAmount;
    }

    /**
     * 设置提现手续费
     * @param extraAmount 提现手续费
     */
    public void setExtraAmount(BigDecimal extraAmount) {
        this.extraAmount = extraAmount;
    }
}