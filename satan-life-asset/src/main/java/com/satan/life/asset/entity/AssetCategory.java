package com.satan.life.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 物品分类实体
 */
@Data
@TableName("asset_category")
public class AssetCategory {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标识 0未删除 1已删除
     */
    private Integer deleted;
    
    /**
     * 子分类列表（非数据库字段，用于树形结构展示）
     */
    private List<AssetCategory> children;
}