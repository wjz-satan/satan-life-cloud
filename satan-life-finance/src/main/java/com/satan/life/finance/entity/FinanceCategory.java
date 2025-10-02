package com.satan.life.finance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

/**
 * 财务分类实体类
 */
@TableName("finance_category")
public class FinanceCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID（null表示系统默认分类）
     */
    private Long userId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类类型（1：收入，2：支出）
     */
    private Integer categoryType;

    /**
     * 图标
     */
    private String icon;

    /**
     * 颜色
     */
    private String color;

    /**
     * 排序
     */
    private Integer sort;

    // getter and setter methods
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}