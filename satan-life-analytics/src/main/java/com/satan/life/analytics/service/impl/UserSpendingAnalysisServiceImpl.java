package com.satan.life.analytics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.satan.life.analytics.entity.UserSpendingAnalysis;
import com.satan.life.analytics.mapper.UserSpendingAnalysisMapper;
import com.satan.life.analytics.service.UserSpendingAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户消费分析Service实现类
 */
@Service
public class UserSpendingAnalysisServiceImpl extends ServiceImpl<UserSpendingAnalysisMapper, UserSpendingAnalysis> implements UserSpendingAnalysisService {
    
    private static final Logger log = LoggerFactory.getLogger(UserSpendingAnalysisServiceImpl.class);
    
    @Autowired
    private UserSpendingAnalysisMapper userSpendingAnalysisMapper;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public UserSpendingAnalysis generateUserSpendingAnalysis(Long userId, LocalDate analysisDate) {
        // 这里实现消费分析生成逻辑
        // 1. 从财务服务获取用户的消费数据
        // 2. 计算总消费金额
        // 3. 计算分类消费统计
        // 4. 计算环比变化
        
        UserSpendingAnalysis analysis = new UserSpendingAnalysis();
        analysis.setUserId(userId);
        analysis.setAnalysisDate(analysisDate);
        
        // 模拟数据
        analysis.setTotalSpending(BigDecimal.valueOf(2500.75));
        
        // 模拟分类消费统计
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("food", 1200.50);
        categoryMap.put("transport", 300.25);
        categoryMap.put("entertainment", 500.00);
        categoryMap.put("shopping", 300.00);
        categoryMap.put("other", 200.00);
        
        try {
            JsonNode categoryJson = objectMapper.valueToTree(categoryMap);
            analysis.setCategorySpending(categoryJson);
        } catch (Exception e) {
            log.error("Failed to convert category map to json", e);
        }
        
        // 模拟环比变化（假设上月消费2300.00）
        analysis.setMonthlyComparison(BigDecimal.valueOf(8.73));
        
        // 保存分析结果
        save(analysis);
        
        return analysis;
    }
    
    @Override
    public List<UserSpendingAnalysis> getUserSpendingAnalysisList(Long userId, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return userSpendingAnalysisMapper.selectByUserIdAndDateRange(
                userId, 
                startDate.format(formatter), 
                endDate.format(formatter)
        );
    }
    
    @Override
    public UserSpendingAnalysis getLatestUserSpendingAnalysis(Long userId) {
        return userSpendingAnalysisMapper.selectLatestByUserId(userId);
    }
    
    @Override
    public int batchGenerateUserSpendingAnalysis(List<Long> userIds, LocalDate analysisDate) {
        int count = 0;
        for (Long userId : userIds) {
            try {
                generateUserSpendingAnalysis(userId, analysisDate);
                count++;
            } catch (Exception e) {
                log.error("Failed to generate spending analysis for user {}", userId, e);
            }
        }
        return count;
    }
}