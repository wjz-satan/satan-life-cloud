package com.satan.life.analytics.controller;

import com.satan.life.analytics.entity.UserSpendingAnalysis;
import com.satan.life.analytics.service.UserSpendingAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户消费分析Controller
 */
@RestController
@RequestMapping("/api/analytics/spending")
public class UserSpendingAnalysisController {
    
    @Autowired
    private UserSpendingAnalysisService userSpendingAnalysisService;
    
    /**
     * 生成用户消费分析
     * @param userId 用户ID
     * @param analysisDate 分析日期
     * @return 生成的消费分析记录
     */
    @PostMapping("/generate")
    public ResponseEntity<UserSpendingAnalysis> generateUserSpendingAnalysis(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate analysisDate) {
        
        UserSpendingAnalysis analysis = userSpendingAnalysisService.generateUserSpendingAnalysis(userId, analysisDate);
        return ResponseEntity.ok(analysis);
    }
    
    /**
     * 获取用户消费分析列表
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 消费分析列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<UserSpendingAnalysis>> getUserSpendingAnalysisList(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        
        List<UserSpendingAnalysis> analysisList = userSpendingAnalysisService.getUserSpendingAnalysisList(userId, startDate, endDate);
        return ResponseEntity.ok(analysisList);
    }
    
    /**
     * 获取用户最新的消费分析
     * @param userId 用户ID
     * @return 最新的消费分析记录
     */
    @GetMapping("/latest")
    public ResponseEntity<UserSpendingAnalysis> getLatestUserSpendingAnalysis(
            @RequestParam Long userId) {
        
        UserSpendingAnalysis analysis = userSpendingAnalysisService.getLatestUserSpendingAnalysis(userId);
        return ResponseEntity.ok(analysis);
    }
    
    /**
     * 批量生成用户消费分析
     * @param userIds 用户ID列表
     * @param analysisDate 分析日期
     * @return 生成的消费分析记录数量
     */
    @PostMapping("/batch/generate")
    public ResponseEntity<Integer> batchGenerateUserSpendingAnalysis(
            @RequestBody List<Long> userIds,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate analysisDate) {
        
        int count = userSpendingAnalysisService.batchGenerateUserSpendingAnalysis(userIds, analysisDate);
        return ResponseEntity.ok(count);
    }
}