-- 个人综合管理系统数据库初始化脚本
-- 版本：V1.0
-- 日期：2023年10月

-- 创建数据库
drop database if exists satan_life;
create database satan_life default character set utf8mb4 collate utf8mb4_unicode_ci;
use satan_life;

-- 1. 用户管理模块

-- 用户表
drop table if exists `user`;
create table `user` (
  `id` bigint primary key auto_increment comment '用户ID',
  `username` varchar(50) not null unique comment '用户名',
  `password` varchar(255) not null comment '密码（加密存储）',
  `nickname` varchar(50) comment '昵称',
  `email` varchar(100) unique comment '邮箱',
  `phone` varchar(20) unique comment '手机号',
  `avatar` varchar(255) comment '头像URL',
  `gender` tinyint comment '性别（0：未知，1：男，2：女）',
  `birthday` date comment '出生日期',
  `status` tinyint default 1 comment '状态（0：禁用，1：启用）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  `last_login_time` datetime comment '最后登录时间',
  `login_ip` varchar(50) comment '登录IP'
) comment '用户表';

-- 用户角色表
drop table if exists `role`;
create table `role` (
  `id` bigint primary key auto_increment comment '角色ID',
  `role_name` varchar(50) not null unique comment '角色名称',
  `role_desc` varchar(255) comment '角色描述',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间'
) comment '角色表';

-- 用户角色关联表
drop table if exists `user_role`;
create table `user_role` (
  `user_id` bigint not null comment '用户ID',
  `role_id` bigint not null comment '角色ID',
  primary key (`user_id`, `role_id`),
  foreign key (`user_id`) references `user`(`id`) on delete cascade,
  foreign key (`role_id`) references `role`(`id`) on delete cascade
) comment '用户角色关联表';

-- 权限表
drop table if exists `permission`;
create table `permission` (
  `id` bigint primary key auto_increment comment '权限ID',
  `permission_name` varchar(50) not null comment '权限名称',
  `permission_code` varchar(50) not null unique comment '权限编码',
  `resource_type` varchar(20) comment '资源类型（menu：菜单，button：按钮）',
  `url` varchar(255) comment '资源路径',
  `parent_id` bigint default 0 comment '父权限ID',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间'
) comment '权限表';

-- 角色权限关联表
drop table if exists `role_permission`;
create table `role_permission` (
  `role_id` bigint not null comment '角色ID',
  `permission_id` bigint not null comment '权限ID',
  primary key (`role_id`, `permission_id`),
  foreign key (`role_id`) references `role`(`id`) on delete cascade,
  foreign key (`permission_id`) references `permission`(`id`) on delete cascade
) comment '角色权限关联表';

-- 设备管理表
drop table if exists `device`;
create table `device` (
  `id` bigint primary key auto_increment comment '设备ID',
  `user_id` bigint not null comment '用户ID',
  `device_name` varchar(50) not null comment '设备名称',
  `device_type` varchar(20) comment '设备类型（web、ios、android等）',
  `device_token` varchar(255) comment '设备token',
  `last_login_time` datetime comment '最后登录时间',
  `status` tinyint default 1 comment '状态（0：禁用，1：启用）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '设备管理表';

-- 2. 财务管理模块

-- 财务分类表
drop table if exists `finance_category`;
create table `finance_category` (
  `id` bigint primary key auto_increment comment '分类ID',
  `user_id` bigint comment '用户ID（null表示系统默认分类）',
  `category_name` varchar(50) not null comment '分类名称',
  `category_type` tinyint not null comment '分类类型（1：收入，2：支出）',
  `icon` varchar(50) comment '图标',
  `color` varchar(20) comment '颜色',
  `sort` int default 0 comment '排序',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  unique key `uk_user_id_category_name_type` (`user_id`, `category_name`, `category_type`),
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '财务分类表';

-- 交易记录表
drop table if exists `transaction_record`;
create table `transaction_record` (
  `id` bigint primary key auto_increment comment '交易记录ID',
  `user_id` bigint not null comment '用户ID',
  `category_id` bigint not null comment '分类ID',
  `amount` decimal(16,2) not null comment '金额',
  `transaction_type` tinyint not null comment '交易类型（1：收入，2：支出）',
  `transaction_time` datetime not null comment '交易时间',
  `description` varchar(255) comment '描述',
  `location` varchar(100) comment '地点',
  `payment_method` varchar(50) comment '支付方式',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade,
  foreign key (`category_id`) references `finance_category`(`id`) on delete cascade
) comment '交易记录表';

-- 预算表
drop table if exists `budget`;
create table `budget` (
  `id` bigint primary key auto_increment comment '预算ID',
  `user_id` bigint not null comment '用户ID',
  `category_id` bigint comment '分类ID（null表示总预算）',
  `budget_amount` decimal(16,2) not null comment '预算金额',
  `start_date` date not null comment '开始日期',
  `end_date` date not null comment '结束日期',
  `period_type` tinyint not null comment '周期类型（1：月度，2：季度，3：年度，4：自定义）',
  `status` tinyint default 1 comment '状态（0：禁用，1：启用）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade,
  foreign key (`category_id`) references `finance_category`(`id`) on delete cascade
) comment '预算表';

-- 财务目标表
drop table if exists `finance_goal`;
create table `finance_goal` (
  `id` bigint primary key auto_increment comment '目标ID',
  `user_id` bigint not null comment '用户ID',
  `goal_name` varchar(100) not null comment '目标名称',
  `target_amount` decimal(16,2) not null comment '目标金额',
  `current_amount` decimal(16,2) default 0 comment '当前金额',
  `start_date` date not null comment '开始日期',
  `end_date` date not null comment '结束日期',
  `status` tinyint default 1 comment '状态（0：未开始，1：进行中，2：已完成，3：已失败）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '财务目标表';

-- 3. 健康管理模块

-- 健康数据类型表
drop table if exists `health_data_type`;
create table `health_data_type` (
  `id` bigint primary key auto_increment comment '数据类型ID',
  `type_name` varchar(50) not null unique comment '类型名称（如：体重、血压、心率等）',
  `unit` varchar(20) comment '单位',
  `description` varchar(255) comment '描述',
  `normal_range` varchar(100) comment '正常范围'
) comment '健康数据类型表';

-- 健康数据表
drop table if exists `health_data`;
create table `health_data` (
  `id` bigint primary key auto_increment comment '健康数据ID',
  `user_id` bigint not null comment '用户ID',
  `data_type_id` bigint not null comment '数据类型ID',
  `data_value` varchar(50) not null comment '数据值',
  `record_time` datetime not null comment '记录时间',
  `description` varchar(255) comment '描述',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade,
  foreign key (`data_type_id`) references `health_data_type`(`id`) on delete cascade
) comment '健康数据表';

-- 运动记录表
drop table if exists `exercise_record`;
create table `exercise_record` (
  `id` bigint primary key auto_increment comment '运动记录ID',
  `user_id` bigint not null comment '用户ID',
  `exercise_type` varchar(50) not null comment '运动类型',
  `duration` int not null comment '运动时长（分钟）',
  `distance` decimal(10,2) comment '运动距离（公里）',
  `calories_burned` int comment '消耗卡路里',
  `start_time` datetime not null comment '开始时间',
  `end_time` datetime not null comment '结束时间',
  `description` varchar(255) comment '描述',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '运动记录表';

-- 4. 行程安排模块

-- 行程表
drop table if exists `trip`;
create table `trip` (
  `id` bigint primary key auto_increment comment '行程ID',
  `user_id` bigint not null comment '用户ID',
  `trip_name` varchar(100) not null comment '行程名称',
  `start_date` datetime not null comment '开始时间',
  `end_date` datetime not null comment '结束时间',
  `location` varchar(255) comment '地点',
  `description` varchar(500) comment '行程描述',
  `status` tinyint default 1 comment '状态（0：未开始，1：进行中，2：已完成，3：已取消）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '行程表';

-- 行程项目表

-- 数据统计与分析模块

-- 用户消费分析表
CREATE TABLE IF NOT EXISTS `user_spending_analysis` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `analysis_date` DATE NOT NULL COMMENT '分析日期',
  `total_spending` DECIMAL(16, 2) DEFAULT 0 COMMENT '总消费金额',
  `category_spending` JSON DEFAULT NULL COMMENT '分类消费统计',
  `monthly_comparison` DECIMAL(16, 2) DEFAULT 0 COMMENT '环比变化',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_date` (`user_id`, `analysis_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户消费分析表';

-- 用户健康分析表
CREATE TABLE IF NOT EXISTS `user_health_analysis` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `analysis_date` DATE NOT NULL COMMENT '分析日期',
  `avg_sleep_duration` DECIMAL(4, 2) DEFAULT 0 COMMENT '平均睡眠时间(小时)',
  `avg_activity_minutes` INT DEFAULT 0 COMMENT '平均活动时长(分钟)',
  `health_score` INT DEFAULT 0 COMMENT '健康评分(0-100)',
  `health_suggestions` TEXT COMMENT '健康建议',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_date` (`user_id`, `analysis_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户健康分析表';

-- 用户任务完成分析表
CREATE TABLE IF NOT EXISTS `user_task_analysis` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `analysis_date` DATE NOT NULL COMMENT '分析日期',
  `total_tasks` INT DEFAULT 0 COMMENT '总任务数',
  `completed_tasks` INT DEFAULT 0 COMMENT '完成任务数',
  `completion_rate` DECIMAL(5, 2) DEFAULT 0 COMMENT '完成率(%)',
  `task_type_stats` JSON DEFAULT NULL COMMENT '任务类型统计',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_date` (`user_id`, `analysis_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户任务完成分析表';

-- 数据统计与分析模块结束
drop table if exists `trip_item`;
create table `trip_item` (
  `id` bigint primary key auto_increment comment '行程项目ID',
  `trip_id` bigint not null comment '行程ID',
  `item_name` varchar(100) not null comment '项目名称',
  `item_type` varchar(50) comment '项目类型（如：交通、住宿、餐饮、景点等）',
  `start_time` datetime not null comment '开始时间',
  `end_time` datetime not null comment '结束时间',
  `location` varchar(255) comment '地点',
  `description` varchar(500) comment '项目描述',
  `cost` decimal(16,2) default 0 comment '费用',
  `currency` varchar(10) default 'CNY' comment '货币类型',
  `status` tinyint default 1 comment '状态（0：未开始，1：进行中，2：已完成，3：已取消）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`trip_id`) references `trip`(`id`) on delete cascade
) comment '行程项目表';

-- 饮食记录表
drop table if exists `diet_record`;
create table `diet_record` (
  `id` bigint primary key auto_increment comment '饮食记录ID',
  `user_id` bigint not null comment '用户ID',
  `food_name` varchar(100) not null comment '食物名称',
  `calories` int comment '卡路里',
  `protein` decimal(10,2) comment '蛋白质（克）',
  `carbs` decimal(10,2) comment '碳水化合物（克）',
  `fat` decimal(10,2) comment '脂肪（克）',
  `meal_type` varchar(20) comment '餐次类型（早餐、午餐、晚餐、加餐）',
  `record_time` datetime not null comment '记录时间',
  `description` varchar(255) comment '描述',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '饮食记录表';

-- 睡眠记录表
drop table if exists `sleep_record`;
create table `sleep_record` (
  `id` bigint primary key auto_increment comment '睡眠记录ID',
  `user_id` bigint not null comment '用户ID',
  `start_time` datetime not null comment '入睡时间',
  `end_time` datetime not null comment '起床时间',
  `duration` int comment '睡眠时长（分钟）',
  `quality` tinyint comment '睡眠质量（1-5分）',
  `description` varchar(255) comment '描述',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '睡眠记录表';

-- 医疗记录管理表
drop table if exists `medical_record`;
create table `medical_record` (
  `id` bigint primary key auto_increment comment '医疗记录ID',
  `user_id` bigint not null comment '用户ID',
  `record_type` varchar(50) comment '记录类型（如：体检、就诊、用药等）',
  `title` varchar(100) not null comment '标题',
  `content` text comment '内容',
  `record_date` date not null comment '记录日期',
  `doctor_name` varchar(50) comment '医生姓名',
  `hospital_name` varchar(100) comment '医院名称',
  `attachments` varchar(500) comment '附件列表（JSON格式）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '医疗记录管理表';

-- 4. 物品资产管理模块

-- 物品分类表
drop table if exists `asset_category`;
create table `asset_category` (
  `id` bigint primary key auto_increment comment '分类ID',
  `user_id` bigint comment '用户ID（null表示系统默认分类）',
  `category_name` varchar(50) not null comment '分类名称',
  `parent_id` bigint default 0 comment '父分类ID',
  `icon` varchar(50) comment '图标',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  unique key `uk_user_id_category_name` (`user_id`, `category_name`),
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '物品分类表';

-- 物品资产表
drop table if exists `asset`;
create table `asset` (
  `id` bigint primary key auto_increment comment '物品ID',
  `user_id` bigint not null comment '用户ID',
  `category_id` bigint not null comment '分类ID',
  `asset_name` varchar(100) not null comment '物品名称',
  `purchase_price` decimal(16,2) comment '购买价格',
  `current_value` decimal(16,2) comment '当前价值',
  `purchase_date` date comment '购买日期',
  `status` tinyint default 1 comment '状态（1：在用，2：闲置，3：已处置）',
  `location` varchar(100) comment '存放位置',
  `description` text comment '描述',
  `images` varchar(1000) comment '图片列表（JSON格式）',
  `warranty_period` date comment '保修截止日期',
  `next_maintenance_date` date comment '下次维护日期',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade,
  foreign key (`category_id`) references `asset_category`(`id`) on delete cascade
) comment '物品资产表';

-- 5. 备忘录模块

-- 笔记分类表
drop table if exists `note_category`;
create table `note_category` (
  `id` bigint primary key auto_increment comment '分类ID',
  `user_id` bigint not null comment '用户ID',
  `category_name` varchar(50) not null comment '分类名称',
  `parent_id` bigint default 0 comment '父分类ID',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  unique key `uk_user_id_category_name` (`user_id`, `category_name`),
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '笔记分类表';

-- 笔记标签表
drop table if exists `note_tag`;
create table `note_tag` (
  `id` bigint primary key auto_increment comment '标签ID',
  `user_id` bigint not null comment '用户ID',
  `tag_name` varchar(50) not null comment '标签名称',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  unique key `uk_user_id_tag_name` (`user_id`, `tag_name`),
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '笔记标签表';

-- 笔记表
drop table if exists `note`;
create table `note` (
  `id` bigint primary key auto_increment comment '笔记ID',
  `user_id` bigint not null comment '用户ID',
  `category_id` bigint comment '分类ID',
  `title` varchar(200) not null comment '标题',
  `content` longtext comment '内容',
  `content_type` tinyint default 1 comment '内容类型（1：文本，2：富文本，3：语音）',
  `is_important` tinyint default 0 comment '是否重要（0：否，1：是）',
  `status` tinyint default 1 comment '状态（1：正常，2：已删除）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  `voice_url` varchar(255) comment '语音文件URL',
  foreign key (`user_id`) references `user`(`id`) on delete cascade,
  foreign key (`category_id`) references `note_category`(`id`) on delete cascade
) comment '笔记表';

-- 笔记标签关联表
drop table if exists `note_tag_relation`;
create table `note_tag_relation` (
  `note_id` bigint not null comment '笔记ID',
  `tag_id` bigint not null comment '标签ID',
  primary key (`note_id`, `tag_id`),
  foreign key (`note_id`) references `note`(`id`) on delete cascade,
  foreign key (`tag_id`) references `note_tag`(`id`) on delete cascade
) comment '笔记标签关联表';

-- 6. 行程安排模块

-- 日程分类表
drop table if exists `schedule_category`;
create table `schedule_category` (
  `id` bigint primary key auto_increment comment '分类ID',
  `user_id` bigint not null comment '用户ID',
  `category_name` varchar(50) not null comment '分类名称',
  `color` varchar(20) comment '颜色',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  unique key `uk_user_id_category_name` (`user_id`, `category_name`),
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '日程分类表';

-- 日程表
drop table if exists `schedule`;
create table `schedule` (
  `id` bigint primary key auto_increment comment '日程ID',
  `user_id` bigint not null comment '用户ID',
  `category_id` bigint comment '分类ID',
  `title` varchar(200) not null comment '标题',
  `description` text comment '描述',
  `start_time` datetime not null comment '开始时间',
  `end_time` datetime not null comment '结束时间',
  `is_all_day` tinyint default 0 comment '是否全天（0：否，1：是）',
  `reminder_time` datetime comment '提醒时间',
  `repeat_type` varchar(20) comment '重复类型（不重复、每天、每周、每月、每年）',
  `status` tinyint default 1 comment '状态（1：正常，2：已完成，3：已取消）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`user_id`) references `user`(`id`) on delete cascade,
  foreign key (`category_id`) references `schedule_category`(`id`) on delete cascade
) comment '日程表';

-- 日程参与者表
drop table if exists `schedule_participant`;
create table `schedule_participant` (
  `id` bigint primary key auto_increment comment '参与者ID',
  `schedule_id` bigint not null comment '日程ID',
  `participant_user_id` bigint comment '参与者用户ID（系统内用户）',
  `participant_name` varchar(50) comment '参与者名称（系统外用户）',
  `participant_email` varchar(100) comment '参与者邮箱',
  `participant_phone` varchar(20) comment '参与者电话',
  `status` tinyint default 0 comment '参与状态（0：未响应，1：已接受，2：已拒绝，3：暂定）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  foreign key (`schedule_id`) references `schedule`(`id`) on delete cascade,
  foreign key (`participant_user_id`) references `user`(`id`) on delete cascade
) comment '日程参与者表';

-- 7. 系统设置模块

-- 用户设置表
drop table if exists `user_setting`;
create table `user_setting` (
  `id` bigint primary key auto_increment comment '设置ID',
  `user_id` bigint not null comment '用户ID',
  `setting_key` varchar(50) not null comment '设置键',
  `setting_value` text comment '设置值',
  `description` varchar(255) comment '描述',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  unique key `uk_user_id_setting_key` (`user_id`, `setting_key`),
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '用户设置表';

-- 通知设置表
drop table if exists `notification_setting`;
create table `notification_setting` (
  `id` bigint primary key auto_increment comment '通知设置ID',
  `user_id` bigint not null comment '用户ID',
  `notification_type` varchar(50) not null comment '通知类型',
  `is_enabled` tinyint default 1 comment '是否启用（0：否，1：是）',
  `notify_method` varchar(50) comment '通知方式（email、sms、app等，多个用逗号分隔）',
  `create_time` datetime default current_timestamp comment '创建时间',
  `update_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
  unique key `uk_user_id_notification_type` (`user_id`, `notification_type`),
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '通知设置表';

-- 8. 系统通用模块

-- 系统日志表
drop table if exists `system_log`;
create table `system_log` (
  `id` bigint primary key auto_increment comment '日志ID',
  `user_id` bigint comment '操作用户ID',
  `operation_type` varchar(50) comment '操作类型',
  `operation_desc` varchar(255) comment '操作描述',
  `operation_ip` varchar(50) comment '操作IP',
  `operation_time` datetime default current_timestamp comment '操作时间',
  `request_params` text comment '请求参数',
  `response_result` text comment '响应结果',
  `error_message` text comment '错误信息',
  `status` tinyint default 1 comment '状态（0：失败，1：成功）'
) comment '系统日志表';

-- 数据备份表
drop table if exists `data_backup`;
create table `data_backup` (
  `id` bigint primary key auto_increment comment '备份ID',
  `user_id` bigint not null comment '用户ID',
  `backup_name` varchar(100) not null comment '备份名称',
  `backup_path` varchar(255) not null comment '备份文件路径',
  `backup_size` bigint comment '备份文件大小（字节）',
  `backup_time` datetime default current_timestamp comment '备份时间',
  `description` varchar(255) comment '描述',
  foreign key (`user_id`) references `user`(`id`) on delete cascade
) comment '数据备份表';

-- 初始化基础数据

-- 插入默认管理员用户
insert into `user` (`username`, `password`, `nickname`, `email`, `status`) values 
('admin', 'admin123', '管理员', 'admin@example.com', 1);

-- 插入默认角色
insert into `role` (`role_name`, `role_desc`) values 
('ROLE_ADMIN', '系统管理员'),
('ROLE_USER', '普通用户');

-- 关联管理员用户和角色
insert into `user_role` (`user_id`, `role_id`) values 
(1, 1);

-- 插入默认财务分类
insert into `finance_category` (`category_name`, `category_type`, `icon`, `color`) values 
-- 收入分类
('工资', 1, 'icon-salary', '#52c41a'),
('奖金', 1, 'icon-bonus', '#1890ff'),
('投资收益', 1, 'icon-investment', '#722ed1'),
('其他收入', 1, 'icon-other-income', '#fa8c16'),
-- 支出分类
('餐饮', 2, 'icon-food', '#f5222d'),
('交通', 2, 'icon-transport', '#1890ff'),
('购物', 2, 'icon-shopping', '#722ed1'),
('娱乐', 2, 'icon-entertainment', '#fa8c16'),
('医疗', 2, 'icon-medical', '#eb2f96'),
('教育', 2, 'icon-education', '#36cfc9'),
('住房', 2, 'icon-housing', '#52c41a'),
('其他支出', 2, 'icon-other-expense', '#faad14');

-- 插入健康数据类型
drop trigger if exists update_backup_time;
create trigger update_backup_time before insert on data_backup
begin
  set new.backup_time = current_timestamp;
end;