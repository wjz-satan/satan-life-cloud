package com.satan.life.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 用户角色关联实体类
 */
@TableName("user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 角色ID
     */
    @TableId
    private Long roleId;

    // getter and setter methods
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}