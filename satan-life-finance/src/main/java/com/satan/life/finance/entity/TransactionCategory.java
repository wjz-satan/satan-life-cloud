package com.satan.life.finance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

/**
 * 交易分类实体类
 */
@TableName("t_transaction_category")
public class TransactionCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类类型(1:收入,2:支出)
     */
    private Integer type;

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

    /**
     * 是否默认分类(0:否,1:是)
     */
    private Integer isDefault;

    // getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}