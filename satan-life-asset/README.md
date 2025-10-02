# 物品资产管理服务（satan-life-asset）

## 1. 模块介绍

物品资产管理服务是个人综合管理系统中的一个核心模块，用于管理用户的各类物品资产信息，包括资产的添加、查询、更新、删除等功能，同时提供资产分类管理能力。

## 2. 技术栈

- Spring Boot 2.7.x
- Spring Cloud Alibaba
- Nacos Discovery & Config
- MyBatis Plus
- MySQL
- Redis
- Lombok
- Hutool

## 3. 目录结构

```
src/main/
├── java/com/satan/life/asset/
│   ├── AssetApplication.java     # 应用启动类
│   ├── controller/               # 控制器层
│   │   ├── AssetController.java        # 物品资产控制器
│   │   └── AssetCategoryController.java # 物品分类控制器
│   ├── entity/                   # 实体类
│   │   ├── Asset.java             # 物品资产实体
│   │   └── AssetCategory.java     # 物品分类实体
│   ├── mapper/                   # Mapper层
│   │   ├── AssetMapper.java       # 物品资产Mapper
│   │   └── AssetCategoryMapper.java # 物品分类Mapper
│   ├── service/                  # 服务层
│   │   ├── AssetService.java      # 物品资产服务接口
│   │   ├── AssetCategoryService.java # 物品分类服务接口
│   │   └── impl/                 # 服务实现
│   │       ├── AssetServiceImpl.java  # 物品资产服务实现
│   │       └── AssetCategoryServiceImpl.java # 物品分类服务实现
│   └── exception/                # 异常处理
│       └── GlobalExceptionHandler.java # 全局异常处理
└── resources/
    └── bootstrap.yml            # 配置文件
```

## 4. 核心功能

### 4.1 物品资产管理
- 添加物品资产
- 更新物品资产
- 删除物品资产
- 获取物品资产详情
- 查询物品资产列表
- 批量删除物品资产
- 更新物品资产状态

### 4.2 物品分类管理
- 添加物品分类
- 更新物品分类
- 删除物品分类
- 获取分类详情
- 获取分类树形结构
- 获取子分类列表

## 5. 配置说明

主要配置文件为 `bootstrap.yml`，包含以下关键配置：
- 服务端口：8006
- 应用名称：satan-life-asset
- 配置中心：Nacos（localhost:8848）
- 命名空间：6c828707-8362-4292-a1c7-7f1d85bdd997
- 激活环境：dev

## 6. API接口

### 6.1 物品资产接口
- POST /api/asset/asset - 添加物品资产
- PUT /api/asset/asset - 更新物品资产
- DELETE /api/asset/asset/{id} - 删除物品资产
- GET /api/asset/asset/{id} - 获取物品资产详情
- GET /api/asset/asset - 查询物品资产列表
- DELETE /api/asset/asset/batch - 批量删除物品资产
- PUT /api/asset/asset/{id}/status - 更新物品资产状态

### 6.2 物品分类接口
- POST /api/asset/category - 添加物品分类
- PUT /api/asset/category - 更新物品分类
- DELETE /api/asset/category/{id} - 删除物品分类
- GET /api/asset/category/{id} - 获取分类详情
- GET /api/asset/category/tree - 获取分类树形结构
- GET /api/asset/category/sub - 获取子分类列表

## 7. 启动说明

1. 确保Nacos服务已启动
2. 确保MySQL数据库已创建并配置正确
3. 确保Redis服务已启动
4. 运行AssetApplication.java的main方法启动服务

## 8. 注意事项
- 所有接口都需要进行用户身份验证
- 物品资产操作需要验证用户权限
- 分类删除时需确保没有子分类