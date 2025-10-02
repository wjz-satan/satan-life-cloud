-- 健康管理相关表结构初始化

-- 健康记录表
CREATE TABLE IF NOT EXISTS `health_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `record_type` tinyint(4) NOT NULL COMMENT '记录类型：1-体重，2-血压，3-心率，4-血糖，5-步数，6-睡眠',
  `value` double NOT NULL COMMENT '记录值',
  `unit` varchar(10) DEFAULT NULL COMMENT '单位',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `record_time` datetime NOT NULL COMMENT '记录时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `update_by` bigint(20) NOT NULL COMMENT '更新人',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记：0-正常，1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_type` (`record_type`),
  KEY `idx_record_time` (`record_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康记录表';

-- 健康目标表
CREATE TABLE IF NOT EXISTS `health_goal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `goal_type` tinyint(4) NOT NULL COMMENT '目标类型：1-体重，2-血压，3-心率，4-血糖，5-步数，6-睡眠',
  `target_value` double NOT NULL COMMENT '目标值',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `current_value` double DEFAULT NULL COMMENT '当前值',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1-进行中，2-已完成，3-已过期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `update_by` bigint(20) NOT NULL COMMENT '更新人',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记：0-正常，1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_goal_type` (`goal_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康目标表';

-- 健康提醒表
CREATE TABLE IF NOT EXISTS `health_reminder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `reminder_type` tinyint(4) NOT NULL COMMENT '提醒类型：1-喝水，2-运动，3-吃药，4-测量',
  `title` varchar(50) NOT NULL COMMENT '提醒标题',
  `content` varchar(255) DEFAULT NULL COMMENT '提醒内容',
  `remind_time` time NOT NULL COMMENT '提醒时间',
  `repeat_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '重复类型：0-不重复，1-每天，2-每周，3-每月',
  `week_days` varchar(20) DEFAULT NULL COMMENT '重复的星期几，逗号分隔',
  `month_days` varchar(100) DEFAULT NULL COMMENT '重复的日期，逗号分隔',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `update_by` bigint(20) NOT NULL COMMENT '更新人',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记：0-正常，1-删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_reminder_type` (`reminder_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康提醒表';

-- 插入模拟数据
-- 假设用户ID为1的用户有以下健康记录
INSERT INTO `health_record` (`user_id`, `record_type`, `value`, `unit`, `remark`, `record_time`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(1, 1, 70.5, 'kg', '早晨空腹', '2023-10-10 08:00:00', '2023-10-10 08:00:00', '2023-10-10 08:00:00', 1, 1),
(1, 1, 71.0, 'kg', '晚上睡前', '2023-10-10 22:00:00', '2023-10-10 22:00:00', '2023-10-10 22:00:00', 1, 1),
(1, 3, 75.0, 'bpm', '静息心率', '2023-10-10 08:30:00', '2023-10-10 08:30:00', '2023-10-10 08:30:00', 1, 1),
(1, 5, 8000.0, '步', '今日步数', '2023-10-10 21:00:00', '2023-10-10 21:00:00', '2023-10-10 21:00:00', 1, 1),
(1, 6, 7.5, '小时', '睡眠时长', '2023-10-10 07:00:00', '2023-10-10 07:00:00', '2023-10-10 07:00:00', 1, 1);

-- 插入健康目标数据
INSERT INTO `health_goal` (`user_id`, `goal_type`, `target_value`, `start_date`, `end_date`, `current_value`, `status`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(1, 1, 68.0, '2023-10-01', '2023-12-31', 70.5, 1, '三个月内减重目标', '2023-10-01 00:00:00', '2023-10-10 08:00:00', 1, 1),
(1, 5, 10000.0, '2023-10-01', NULL, 8000.0, 1, '每日步数目标', '2023-10-01 00:00:00', '2023-10-10 21:00:00', 1, 1);

-- 插入健康提醒数据
INSERT INTO `health_reminder` (`user_id`, `reminder_type`, `title`, `content`, `remind_time`, `repeat_type`, `week_days`, `month_days`, `status`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES
(1, 1, '喝水提醒', '记得多喝水，保持身体水分', '09:00:00', 1, NULL, NULL, 1, '2023-10-01 00:00:00', '2023-10-01 00:00:00', 1, 1),
(1, 1, '喝水提醒', '记得多喝水，保持身体水分', '14:00:00', 1, NULL, NULL, 1, '2023-10-01 00:00:00', '2023-10-01 00:00:00', 1, 1),
(1, 2, '运动提醒', '该进行日常运动了', '18:00:00', 1, NULL, NULL, 1, '2023-10-01 00:00:00', '2023-10-01 00:00:00', 1, 1),
(1, 4, '测量体重', '早晨空腹测量体重', '07:30:00', 1, NULL, NULL, 1, '2023-10-01 00:00:00', '2023-10-01 00:00:00', 1, 1);