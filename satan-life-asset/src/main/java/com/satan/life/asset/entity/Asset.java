package com.satan.life.asset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 物品资产实体类
 */
@Data
@TableName("asset")
public class Asset {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 物品名称
     */
    private String name;
    
    /**
     * 物品类型ID
     */
    private Long categoryId;
    
    /**
     * 物品价值
     */
    private BigDecimal value;
    
    /**
     * 购买日期
     */
    private LocalDateTime purchaseDate;
    
    /**
     * 物品状态：1-使用中，2-闲置，3-已损坏，4-已丢失，5-已出售
     */
    private Integer status;
    
    /**
     * 物品描述
     */
    private String description;
    
    /**
     * 存放位置
     */
    private String location;
    
    /**
     * 品牌
     */
    private String brand;
    
    /**
     * 型号
     */
    private String model;
    
    /**
     * 序列号
     */
    private String serialNumber;
    
    /**
     * 保修截止日期
     */
    private LocalDateTime warrantyEndDate;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    private Long createBy;
    
    /**
     * 更新人
     */
    private Long updateBy;
    
    /**
     * 逻辑删除标记：0-正常，1-删除
     */
    private Integer deleted;
}