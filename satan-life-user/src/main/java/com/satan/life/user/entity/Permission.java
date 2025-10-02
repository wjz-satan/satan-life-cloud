package com.satan.life.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.satan.life.common.entity.BaseEntity;

/**
 * 权限实体类
 */
@TableName("permission")
public class Permission extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 资源类型（menu：菜单，button：按钮）
     */
    private String resourceType;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 父权限ID
     */
    private Long parentId;

    // getter and setter methods
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}