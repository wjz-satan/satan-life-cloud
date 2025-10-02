package com.satan.life.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.satan.life.finance.entity.Transaction;
import com.satan.life.common.result.R;

import java.time.LocalDate;
import java.util.Map;

/**
 * 交易记录服务接口
 */
public interface TransactionService extends IService<Transaction> {
    /**
     * 添加交易记录
     * @param transaction 交易记录信息
     * @return 添加结果
     */
    R<?> addTransaction(Transaction transaction);

    /**
     * 更新交易记录
     * @param transaction 交易记录信息
     * @return 更新结果
     */
    R<?> updateTransaction(Transaction transaction);

    /**
     * 删除交易记录
     * @param transactionId 交易记录ID
     * @param userId 用户ID
     * @return 删除结果
     */
    R<?> deleteTransaction(Long transactionId, Long userId);

    /**
     * 查询交易记录列表
     * @param params 查询参数
     * @return 交易记录列表
     */
    R<?> getTransactionList(Map<String, Object> params);

    /**
     * 获取交易记录详情
     * @param transactionId 交易记录ID
     * @param userId 用户ID
     * @return 交易记录详情
     */
    R<?> getTransactionDetail(Long transactionId, Long userId);

    /**
     * 获取月度财务统计
     * @param userId 用户ID
     * @param year 年份
     * @param month 月份
     * @return 月度财务统计
     */
    R<?> getMonthlyStatistics(Long userId, Integer year, Integer month);

    /**
     * 获取年度财务统计
     * @param userId 用户ID
     * @param year 年份
     * @return 年度财务统计
     */
    R<?> getYearlyStatistics(Long userId, Integer year);

    /**
     * 获取自定义时间段财务统计
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 财务统计
     */
    R<?> getCustomPeriodStatistics(Long userId, LocalDate startDate, LocalDate endDate);
}