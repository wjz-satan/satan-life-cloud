package com.satan.life.analytics.controller;

import com.satan.life.analytics.entity.UserTaskAnalysis;
import com.satan.life.analytics.service.UserTaskAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户任务完成分析Controller
 */
@RestController
@RequestMapping("/api/analytics/task")
public class UserTaskAnalysisController {
    
    @Autowired
    private UserTaskAnalysisService userTaskAnalysisService;
    
    /**
     * 生成用户任务完成分析
     * @param userId 用户ID
     * @param analysisDate 分析日期
     * @return 生成的任务完成分析记录
     */
    @PostMapping("/generate")
    public ResponseEntity<UserTaskAnalysis> generateUserTaskAnalysis(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate analysisDate) {
        
        UserTaskAnalysis analysis = userTaskAnalysisService.generateUserTaskAnalysis(userId, analysisDate);
        return ResponseEntity.ok(analysis);
    }
    
    /**
     * 获取用户任务完成分析列表
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 任务完成分析列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<UserTaskAnalysis>> getUserTaskAnalysisList(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        
        List<UserTaskAnalysis> analysisList = userTaskAnalysisService.getUserTaskAnalysisList(userId, startDate, endDate);
        return ResponseEntity.ok(analysisList);
    }
    
    /**
     * 获取用户最新的任务完成分析
     * @param userId 用户ID
     * @return 最新的任务完成分析记录
     */
    @GetMapping("/latest")
    public ResponseEntity<UserTaskAnalysis> getLatestUserTaskAnalysis(
            @RequestParam Long userId) {
        
        UserTaskAnalysis analysis = userTaskAnalysisService.getLatestUserTaskAnalysis(userId);
        return ResponseEntity.ok(analysis);
    }
    
    /**
     * 批量生成用户任务完成分析
     * @param userIds 用户ID列表
     * @param analysisDate 分析日期
     * @return 生成的任务完成分析记录数量
     */
    @PostMapping("/batch/generate")
    public ResponseEntity<Integer> batchGenerateUserTaskAnalysis(
            @RequestBody List<Long> userIds,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate analysisDate) {
        
        int count = userTaskAnalysisService.batchGenerateUserTaskAnalysis(userIds, analysisDate);
        return ResponseEntity.ok(count);
    }
}