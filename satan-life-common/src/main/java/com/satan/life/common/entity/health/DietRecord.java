package com.satan.life.common.entity.health;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 饮食记录实体类
 */
@TableName("diet_record")
public class DietRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 食物名称
     */
    private String foodName;

    /**
     * 卡路里
     */
    private Integer calories;

    /**
     * 蛋白质（克）
     */
    private BigDecimal protein;

    /**
     * 碳水化合物（克）
     */
    private BigDecimal carbs;

    /**
     * 脂肪（克）
     */
    private BigDecimal fat;

    /**
     * 餐次类型（早餐、午餐、晚餐、加餐）
     */
    private String mealType;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    /**
     * 描述
     */
    private String description;

    // getter and setter methods
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getCarbs() {
        return carbs;
    }

    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    public BigDecimal getFat() {
        return fat;
    }

    public void setFat(BigDecimal fat) {
        this.fat = fat;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}