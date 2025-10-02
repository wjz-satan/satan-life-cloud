package com.satan.life.finance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易记录实体类
 */
@TableName("transaction_record")
public class Transaction extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 交易类型（1：收入，2：支出）
     */
    private Integer transactionType;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

    /**
     * 描述
     */
    private String description;

    /**
     * 地点
     */
    private String location;

    /**
     * 支付方式
     */
    private String paymentMethod;

    // getter and setter methods
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


}