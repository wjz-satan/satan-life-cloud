package com.satan.life.analytics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.satan.life.analytics.entity.UserTaskAnalysis;
import com.satan.life.analytics.mapper.UserTaskAnalysisMapper;
import com.satan.life.analytics.service.UserTaskAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户任务完成分析Service实现类
 */
@Service
public class UserTaskAnalysisServiceImpl extends ServiceImpl<UserTaskAnalysisMapper, UserTaskAnalysis> implements UserTaskAnalysisService {
    
    private static final Logger log = LoggerFactory.getLogger(UserTaskAnalysisServiceImpl.class);
    
    @Autowired
    private UserTaskAnalysisMapper userTaskAnalysisMapper;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public UserTaskAnalysis generateUserTaskAnalysis(Long userId, LocalDate analysisDate) {
        // 这里实现任务完成分析生成逻辑
        // 1. 从任务服务获取用户的任务数据
        // 2. 计算任务完成率
        // 3. 计算平均完成时间
        // 4. 分析任务分类完成情况
        // 5. 生成任务完成建议
        
        UserTaskAnalysis analysis = new UserTaskAnalysis();
        analysis.setUserId(userId);
        analysis.setAnalysisDate(analysisDate);
        
        // 模拟数据
        analysis.setTotalTasks(20);
        analysis.setCompletedTasks(16);
        
        // 计算任务完成率
        int completionRate = calculateCompletionRate(analysis.getCompletedTasks(), analysis.getTotalTasks());
        analysis.setCompletionRate(java.math.BigDecimal.valueOf(completionRate));
        
        // 使用taskTypeStats存储分类任务完成情况
        ObjectNode taskTypeStats = objectMapper.createObjectNode();
        // 工作任务
        taskTypeStats.put("workTasks", 8);
        taskTypeStats.put("completedWorkTasks", 7);
        
        // 学习任务
        taskTypeStats.put("studyTasks", 5);
        taskTypeStats.put("completedStudyTasks", 4);
        
        // 生活任务
        taskTypeStats.put("lifeTasks", 7);
        taskTypeStats.put("completedLifeTasks", 5);
        
        // 平均完成时间
        taskTypeStats.put("avgCompletionMinutes", 30);
        
        analysis.setTaskTypeStats(taskTypeStats);
        
        // 保存分析结果
        save(analysis);
        
        return analysis;
    }
    
    private int calculateCompletionRate(Integer completedTasks, Integer totalTasks) {
        if (totalTasks == 0) {
            return 0;
        }
        return Math.round((float) completedTasks / totalTasks * 100);
    }
    
    private String generateTaskSuggestions(UserTaskAnalysis analysis) {
        StringBuilder suggestions = new StringBuilder();
        
        if (analysis.getCompletionRate().intValue() >= 90) {
            suggestions.append("您的任务完成情况非常好，继续保持！\n");
        } else if (analysis.getCompletionRate().intValue() >= 70) {
            suggestions.append("您的任务完成情况良好，但还有提升空间。\n");
        } else {
            suggestions.append("建议您优化任务管理策略，提高任务完成率。\n");
        }
        
        // 各类任务完成情况分析
        JsonNode taskTypeStats = analysis.getTaskTypeStats();
        if (taskTypeStats != null) {
            int workCompletionRate = calculateCompletionRate(
                taskTypeStats.path("completedWorkTasks").asInt(), 
                taskTypeStats.path("workTasks").asInt());
            int studyCompletionRate = calculateCompletionRate(
                taskTypeStats.path("completedStudyTasks").asInt(), 
                taskTypeStats.path("studyTasks").asInt());
            int lifeCompletionRate = calculateCompletionRate(
                taskTypeStats.path("completedLifeTasks").asInt(), 
                taskTypeStats.path("lifeTasks").asInt());
            
            if (workCompletionRate < 70) {
                suggestions.append("工作任务完成率较低，建议合理安排工作时间。\n");
            }
            
            if (studyCompletionRate < 70) {
                suggestions.append("学习任务完成率较低，建议制定更明确的学习计划。\n");
            }
            
            if (lifeCompletionRate < 70) {
                suggestions.append("生活任务完成率较低，建议关注生活品质。\n");
            }
            
            // 平均完成时间分析
            if (taskTypeStats.path("avgCompletionMinutes").asInt() > 60) {
                suggestions.append("任务平均完成时间较长，建议提高工作效率。\n");
            }
        }
        
        return suggestions.toString();
    }
    
    @Override
    public List<UserTaskAnalysis> getUserTaskAnalysisList(Long userId, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return userTaskAnalysisMapper.selectByUserIdAndDateRange(
                userId, 
                startDate.format(formatter), 
                endDate.format(formatter)
        );
    }
    
    @Override
    public UserTaskAnalysis getLatestUserTaskAnalysis(Long userId) {
        return userTaskAnalysisMapper.selectLatestByUserId(userId);
    }
    
    @Override
    public int batchGenerateUserTaskAnalysis(List<Long> userIds, LocalDate analysisDate) {
        int count = 0;
        for (Long userId : userIds) {
            try {
                generateUserTaskAnalysis(userId, analysisDate);
                count++;
            } catch (Exception e) {
                log.error("Failed to generate task analysis for user {}", userId, e);
            }
        }
        return count;
    }
}