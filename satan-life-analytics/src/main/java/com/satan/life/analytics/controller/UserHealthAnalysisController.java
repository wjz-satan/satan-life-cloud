package com.satan.life.analytics.controller;

import com.satan.life.analytics.entity.UserHealthAnalysis;
import com.satan.life.analytics.service.UserHealthAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户健康分析Controller
 */
@RestController
@RequestMapping("/api/analytics/health")
public class UserHealthAnalysisController {
    
    @Autowired
    private UserHealthAnalysisService userHealthAnalysisService;
    
    /**
     * 生成用户健康分析
     * @param userId 用户ID
     * @param analysisDate 分析日期
     * @return 生成的健康分析记录
     */
    @PostMapping("/generate")
    public ResponseEntity<UserHealthAnalysis> generateUserHealthAnalysis(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate analysisDate) {
        
        UserHealthAnalysis analysis = userHealthAnalysisService.generateUserHealthAnalysis(userId, analysisDate);
        return ResponseEntity.ok(analysis);
    }
    
    /**
     * 获取用户健康分析列表
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康分析列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<UserHealthAnalysis>> getUserHealthAnalysisList(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        
        List<UserHealthAnalysis> analysisList = userHealthAnalysisService.getUserHealthAnalysisList(userId, startDate, endDate);
        return ResponseEntity.ok(analysisList);
    }
    
    /**
     * 获取用户最新的健康分析
     * @param userId 用户ID
     * @return 最新的健康分析记录
     */
    @GetMapping("/latest")
    public ResponseEntity<UserHealthAnalysis> getLatestUserHealthAnalysis(
            @RequestParam Long userId) {
        
        UserHealthAnalysis analysis = userHealthAnalysisService.getLatestUserHealthAnalysis(userId);
        return ResponseEntity.ok(analysis);
    }
    
    /**
     * 批量生成用户健康分析
     * @param userIds 用户ID列表
     * @param analysisDate 分析日期
     * @return 生成的健康分析记录数量
     */
    @PostMapping("/batch/generate")
    public ResponseEntity<Integer> batchGenerateUserHealthAnalysis(
            @RequestBody List<Long> userIds,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate analysisDate) {
        
        int count = userHealthAnalysisService.batchGenerateUserHealthAnalysis(userIds, analysisDate);
        return ResponseEntity.ok(count);
    }
}