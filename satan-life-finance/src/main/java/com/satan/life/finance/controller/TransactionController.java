package com.satan.life.finance.controller;

import com.satan.life.finance.entity.Transaction;
import com.satan.life.finance.service.TransactionService;
import com.satan.life.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * 交易记录控制器
 */
@RestController
@RequestMapping("/api/finance/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * 添加交易记录
     */
    @PostMapping("/add")
    public R<?> addTransaction(@RequestBody Transaction transaction) {
        return transactionService.addTransaction(transaction);
    }

    /**
     * 更新交易记录
     */
    @PutMapping("/update")
    public R<?> updateTransaction(@RequestBody Transaction transaction) {
        return transactionService.updateTransaction(transaction);
    }

    /**
     * 删除交易记录
     */
    @DeleteMapping("/delete")
    public R<?> deleteTransaction(@RequestBody Map<String, Object> params) {
        Long transactionId = Long.valueOf(params.get("transactionId").toString());
        Long userId = Long.valueOf(params.get("userId").toString());
        return transactionService.deleteTransaction(transactionId, userId);
    }

    /**
     * 获取交易记录列表
     */
    @GetMapping("/list")
    public R<?> getTransactionList(@RequestParam Map<String, Object> params) {
        return transactionService.getTransactionList(params);
    }

    /**
     * 获取交易记录详情
     */
    @GetMapping("/detail")
    public R<?> getTransactionDetail(@RequestParam Long transactionId, @RequestParam Long userId) {
        return transactionService.getTransactionDetail(transactionId, userId);
    }

    /**
     * 获取月度财务统计
     */
    @GetMapping("/statistics/monthly")
    public R<?> getMonthlyStatistics(@RequestParam Long userId, @RequestParam Integer year, @RequestParam Integer month) {
        return transactionService.getMonthlyStatistics(userId, year, month);
    }

    /**
     * 获取年度财务统计
     */
    @GetMapping("/statistics/yearly")
    public R<?> getYearlyStatistics(@RequestParam Long userId, @RequestParam Integer year) {
        return transactionService.getYearlyStatistics(userId, year);
    }

    /**
     * 获取自定义时间段财务统计
     */
    @GetMapping("/statistics/custom")
    public R<?> getCustomPeriodStatistics(@RequestParam Long userId, 
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, 
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return transactionService.getCustomPeriodStatistics(userId, startDate, endDate);
    }
}