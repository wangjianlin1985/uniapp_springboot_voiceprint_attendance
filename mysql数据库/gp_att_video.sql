/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.6
 Source Server Type    : MySQL
 Source Server Version : 50620
 Source Host           : localhost:3306
 Source Schema         : gp_att_video

 Target Server Type    : MySQL
 Target Server Version : 50620
 File Encoding         : 65001

 Date: 11/09/2022 12:25:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_att
-- ----------------------------
DROP TABLE IF EXISTS `sys_att`;
CREATE TABLE `sys_att`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_att
-- ----------------------------
INSERT INTO `sys_att` VALUES (8, 12709, '2022-09-10', '2022-09-10 20:11:10', '1');
INSERT INTO `sys_att` VALUES (9, 12710, '2022-09-10', '2022-09-10 21:27:32', '1');
INSERT INTO `sys_att` VALUES (10, 12709, '2022-09-11', '', '0');
INSERT INTO `sys_att` VALUES (11, 12710, '2022-09-11', '', '0');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `delete_flag` tinyint(4) NULL DEFAULT 0,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `delete_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-green', 'Y', 'admin', '2018-03-16 11:33:00', 'admin', '2020-02-13 21:26:30', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow', 0, NULL, NULL);
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '初始化密码 123456', 0, NULL, NULL);
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-light', 'Y', 'admin', '2018-03-16 11:33:00', 'admin', '2020-06-20 09:59:50', '深色主题theme-dark，浅色主题theme-light', 0, NULL, NULL);
INSERT INTO `sys_config` VALUES (101, '系统名称', 'sys.name', '语音考勤管理系统', 'Y', 'admin', '2019-08-07 00:04:04', 'admin', '2022-02-27 10:53:19', '由“项目配置”页面更改操作', 0, NULL, NULL);
INSERT INTO `sys_config` VALUES (102, '系统版权', 'sys.copyright', '© 2022 All Rights Reserved. MORAL TTIT', 'Y', 'admin', '2019-08-07 00:26:47', 'admin', '2022-09-09 21:41:45', '由“项目配置”页面更改操作', 0, NULL, NULL);
INSERT INTO `sys_config` VALUES (103, 'LOGO图片', 'sys.logo', '/profile/logo/2022/09/09/3b05aabcae868a9c674ee1c557a78dec.jpg', 'Y', 'admin', '2019-08-08 00:03:03', 'admin', '2022-09-09 21:39:24', '由“项目配置”页面更改操作', 0, NULL, NULL);
INSERT INTO `sys_config` VALUES (111, '系统根部门ID', 'schoole_dept_root_id', '101', 'Y', 'admin', '2019-12-04 10:00:08', 'admin', '2022-02-26 15:32:07', '', 0, NULL, NULL);
INSERT INTO `sys_config` VALUES (117, '单位名称', 'sys.school.name', 'XX单位', 'Y', '', NULL, 'admin', '2022-02-26 15:32:20', '', 0, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `delete_flag` tinyint(4) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `delete_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 204 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', 'XX公司', 0, '', '', '', '0', 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-03-17 09:46:41', NULL, NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '学校部门', 3, '', '', '', '0', 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-03-17 09:46:41', NULL, NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '学校处室', 1, '', '', '', '0', 0, 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(11) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `delete_flag` tinyint(4) NULL DEFAULT 0,
  `delete_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '性别男', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '性别女', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '性别未知', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '显示菜单', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '隐藏菜单', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '正常状态', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '2', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-08-07 22:38:22', '停用状态', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '正常状态', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (9, 3, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-08-07 22:44:00', '停用状态', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '默认分组', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统分组', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统默认是', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '系统默认否', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '通知', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '公告', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '正常状态', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '关闭状态', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '新增操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '修改操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '删除操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '授权操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '导出操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '导入操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '强退操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '生成操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '清空操作', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '正常状态', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '停用状态', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (103, 2, '是', '1', 'article_need_audit_status', '', 'default', 'N', '0', 'admin', '2019-08-15 22:53:39', 'admin', '2019-08-15 22:54:12', '需要审核', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (104, 3, '否', '0', 'article_need_audit_status', '', 'default', 'Y', '0', 'admin', '2019-08-15 22:53:55', 'admin', '2019-08-15 22:54:06', '不需要审核', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (105, 2, '否', '0', 'article_istop', NULL, NULL, 'Y', '0', 'admin', '2019-08-17 15:57:50', 'admin', NULL, '不置顶', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (106, 0, '是', '1', 'article_istop', NULL, NULL, 'N', '0', 'admin', '2019-08-17 15:58:07', 'admin', NULL, '置顶', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (107, 10, '正常', '0', 'useable_status', NULL, NULL, 'Y', '0', 'demo', '2019-08-22 10:31:15', 'admin', NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (108, 9, '停用', '1', 'useable_status', '', '', 'N', '0', 'demo', '2019-08-22 10:31:47', 'admin', '2020-03-20 10:59:48', 'test', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (109, 8, '毕业', '3', 'useable_status', '', '', 'N', '0', 'demo', '2019-08-22 10:32:12', 'admin', '2020-03-20 11:06:19', 'ts', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (111, 3, '考勤', 'ATTENDANCE', 'sys_job_group', NULL, NULL, 'Y', '0', 'demo', '2019-11-13 10:45:38', '', NULL, '考勤分组', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (112, 4, '接口', 'API', 'sys_job_group', NULL, NULL, 'Y', '0', 'demo', '2019-11-13 11:07:11', '', NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (113, 5, '清理数据', 'CLEAN', 'sys_job_group', NULL, NULL, 'Y', '0', 'demo', '2019-11-16 17:01:01', '', NULL, '定时清理一些缓存数据或者数据库', 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (114, 4, '使用中', '0', 'use_status', NULL, NULL, 'Y', '0', 'demo', '2019-11-18 18:03:02', '', NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (115, 3, '未使用', '1', 'use_status', NULL, 'default', 'Y', '0', 'demo', '2019-11-18 18:03:38', '', NULL, NULL, 0, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (116, 6, '评价', 'EVALUATE', 'sys_job_group', NULL, NULL, 'Y', '0', 'admin', '2019-11-19 11:23:34', '', NULL, '评价模块', 0, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `dict_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `delete_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_flag` tinyint(4) NULL DEFAULT 0,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 100, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '用户性别列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 90, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '菜单状态列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 80, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '系统开关列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 70, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '任务状态列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 60, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '任务分组列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 50, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '系统是否列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 40, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '通知类型列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 30, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '通知状态列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 20, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '操作类型列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 10, 'admin', '2018-03-16 11:33:00', 'demo', '2018-03-16 11:33:00', '登录状态列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (100, '文章审核状态', 'article_need_audit_status', '0', 110, 'admin', '2019-08-15 22:52:49', 'demo', '2019-08-22 10:29:34', '文章审核状态列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (101, '文章置顶', 'article_istop', '0', 120, 'admin', '2019-08-17 15:57:02', 'admin', '2020-03-17 10:06:22', '文章是否置顶', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (102, '可用状态', 'useable_status', '0', 130, 'demo', '2019-08-22 10:30:41', 'demo', '2019-08-22 10:44:51', '年级班级学生等毕业状态列表', NULL, 0, NULL);
INSERT INTO `sys_dict_type` VALUES (104, '使用状态', 'use_status', '0', 103, 'demo', '2019-11-18 18:00:04', 'demo', '2019-11-18 18:04:06', '是否使用标签', NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1590 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (1569, 'admin', '192.168.1.122', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2022-02-26 15:42:52');
INSERT INTO `sys_logininfor` VALUES (1570, 'admin', '192.168.1.122', '内网IP', 'Chrome 9', 'Windows 10', '0', '退出成功', '2022-02-26 15:43:26');
INSERT INTO `sys_logininfor` VALUES (1571, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2022-02-27 10:52:04');
INSERT INTO `sys_logininfor` VALUES (1572, 'admin', '127.0.0.1', '内网IP', 'Chrome 9', 'Windows 10', '0', '登录成功', '2022-02-27 11:09:23');
INSERT INTO `sys_logininfor` VALUES (1573, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2022-09-09 21:23:01');
INSERT INTO `sys_logininfor` VALUES (1574, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2022-09-09 21:32:42');
INSERT INTO `sys_logininfor` VALUES (1575, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-09-09 21:32:45');
INSERT INTO `sys_logininfor` VALUES (1576, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-09-10 11:16:51');
INSERT INTO `sys_logininfor` VALUES (1577, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-09-10 16:33:30');
INSERT INTO `sys_logininfor` VALUES (1578, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-09-10 16:36:30');
INSERT INTO `sys_logininfor` VALUES (1579, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-09-10 17:16:36');
INSERT INTO `sys_logininfor` VALUES (1580, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-09-10 20:12:33');
INSERT INTO `sys_logininfor` VALUES (1581, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '退出成功', '2022-09-10 20:13:54');
INSERT INTO `sys_logininfor` VALUES (1582, 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-09-10 20:20:42');
INSERT INTO `sys_logininfor` VALUES (1583, 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-09-10 20:24:25');
INSERT INTO `sys_logininfor` VALUES (1584, 'admiin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '用户不存在/密码错误', '2022-09-10 21:00:05');
INSERT INTO `sys_logininfor` VALUES (1585, 'admiin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '1', '用户不存在/密码错误', '2022-09-10 21:00:09');
INSERT INTO `sys_logininfor` VALUES (1586, 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-09-10 21:00:12');
INSERT INTO `sys_logininfor` VALUES (1587, 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-09-10 21:02:23');
INSERT INTO `sys_logininfor` VALUES (1588, 'admin', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', '0', '登录成功', '2022-09-10 21:21:48');
INSERT INTO `sys_logininfor` VALUES (1589, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-09-11 12:21:26');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '请求地址',
  `menu_top_id` bigint(20) NULL DEFAULT NULL,
  `target` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `delete_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `delete_flag` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2339 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '信息管理', 0, 40, '#', 1, 'menuItem', 'M', '0', '', 'fa fa-bars', 'admin', '2018-03-16 11:33:00', 'admin', '2022-02-26 15:41:21', '系统管理目录', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 30, '#', 1, 'menuItem', 'M', '0', '', 'fa fa-video-camera', 'admin', '2018-03-16 11:33:00', 'admin', '2019-08-11 16:21:09', '系统监控目录', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 120, '/system/user', 3, 'menuItem', 'C', '0', 'system:user:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2022-02-26 15:31:22', '用户管理菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 110, '/system/role', 1, 'menuItem', 'C', '1', 'system:role:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2022-02-26 15:41:43', '角色管理菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 100, '/system/menu', 1, 'menuItem', 'C', '1', 'system:menu:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2022-02-26 15:43:19', '菜单管理菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 90, '/system/dept', NULL, 'menuItem', 'C', '0', 'system:dept:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2019-08-11 16:22:50', '部门管理菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 80, '/system/post', 1, 'menuItem', 'C', '1', 'system:post:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2022-02-26 15:42:00', '岗位管理菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 70, '/system/dict', 1, 'menuItem', 'C', '1', 'system:dict:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2022-02-26 15:42:08', '字典管理菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 60, '/system/config', 1, 'menuItem', 'C', '1', 'system:config:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2022-02-26 15:42:14', '参数设置菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (108, '日志管理', 2, 50, '#', 1, 'menuItem', 'M', '0', '', '#', 'admin', '2018-03-16 11:33:00', 'demo', '2019-11-13 16:15:57', '日志管理菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 30, '/monitor/online', NULL, 'menuItem', 'C', '0', 'monitor:online:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2019-08-11 16:24:31', '在线用户菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 10, '/monitor/data', NULL, 'menuItem', 'C', '0', 'monitor:data:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2019-08-11 16:24:04', '数据监控菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 20, '/monitor/server', NULL, 'menuItem', 'C', '0', 'monitor:server:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2019-08-11 16:24:13', '服务监控菜单', 'admin', '2022-02-26 15:30:23', 1);
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, '/monitor/logininfor', NULL, '', 'C', '0', 'monitor:logininfor:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '登录日志菜单', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '#', NULL, '', 'F', '0', 'system:user:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '#', NULL, '', 'F', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '#', NULL, '', 'F', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '#', NULL, '', 'F', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '#', NULL, '', 'F', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '#', NULL, '', 'F', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '#', NULL, '', 'F', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '#', NULL, '', 'F', '0', 'system:role:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '#', NULL, '', 'F', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '#', NULL, '', 'F', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '#', NULL, '', 'F', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '#', NULL, '', 'F', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '#', NULL, '', 'F', '0', 'system:menu:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '#', NULL, '', 'F', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '#', NULL, '', 'F', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '#', NULL, '', 'F', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '#', NULL, '', 'F', '0', 'system:dept:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '#', NULL, '', 'F', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '#', NULL, '', 'F', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '#', NULL, '', 'F', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '#', NULL, '', 'F', '0', 'system:post:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '#', NULL, '', 'F', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '#', NULL, '', 'F', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '#', NULL, '', 'F', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '#', NULL, '', 'F', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', NULL, '', 'F', '0', 'system:dict:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', NULL, '', 'F', '0', 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', NULL, '', 'F', '0', 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', NULL, '', 'F', '0', 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', NULL, '', 'F', '0', 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', NULL, '', 'F', '0', 'system:config:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', NULL, '', 'F', '0', 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', NULL, '', 'F', '0', 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', NULL, '', 'F', '0', 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', NULL, '', 'F', '0', 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', NULL, '', 'F', '0', 'monitor:logininfor:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', NULL, '', 'F', '0', 'monitor:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', NULL, '', 'F', '0', 'monitor:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', NULL, '', 'F', '0', 'monitor:online:list', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', NULL, '', 'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', NULL, '', 'F', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2005, '项目配置', 1, 30, '/system/config/projectConfig', NULL, 'menuItem', 'C', '0', '', '#', 'admin', '2019-08-07 23:29:26', 'admin', '2019-08-11 16:23:48', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2023, '基础信息', 0, 50, '#', 3, 'menuItem', 'M', '0', NULL, 'fa fa-arrows', 'admin', '2019-08-14 23:41:16', 'admin', NULL, '', 'admin', '2022-02-26 15:31:11', 1);
INSERT INTO `sys_menu` VALUES (2076, '点位信息', 2023, 80, '#', 3, 'menuItem', 'M', '0', NULL, '#', 'admin', '2019-09-06 14:45:02', '', NULL, '', 'admin', '2022-02-26 15:31:09', 1);
INSERT INTO `sys_menu` VALUES (2287, '顶部菜单', 1, 105, '/system/menutop', 1, 'menuItem', 'C', '1', 'system:menutop:view', '#', 'admin', '2019-12-31 10:39:33', 'admin', '2022-02-26 15:41:51', '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2288, '查看', 2287, 10, '#', NULL, 'menuItem', 'F', '0', 'system:menutop:list', '#', 'admin', '2019-12-31 11:13:58', '', NULL, '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2289, '新增', 2287, 9, '#', NULL, 'menuItem', 'F', '0', 'system:menutop:add', '#', 'admin', '2019-12-31 11:14:27', '', NULL, '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2290, '修改', 2287, 8, '#', NULL, 'menuItem', 'F', '0', 'system:menutop:edit', '#', 'admin', '2019-12-31 11:14:49', '', NULL, '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2291, '删除', 2287, 7, '#', NULL, 'menuItem', 'F', '0', 'system:menutop:remove', '#', 'admin', '2019-12-31 11:15:14', '', NULL, '', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2330, '院系管理员', 2023, 15, '/baseinfo/academydean', 3, 'menuItem', 'C', '0', '', '#', 'admin', '2021-12-25 10:40:51', 'admin', '2021-12-25 10:41:46', '', 'admin', '2022-02-26 15:31:06', 1);
INSERT INTO `sys_menu` VALUES (2331, '任务管理', 0, 100, '#', 4, 'menuItem', 'M', '0', NULL, 'fa fa-asl-interpreting', 'admin', '2021-12-26 11:55:36', '', NULL, '', 'admin', '2022-02-26 15:29:42', 1);
INSERT INTO `sys_menu` VALUES (2337, '教师审核', 1, 115, '/system/user/shuser', 3, 'menuItem', 'C', '0', NULL, '#', 'admin', '2022-01-02 23:29:02', '', NULL, '', 'admin', '2022-02-26 15:31:01', 1);
INSERT INTO `sys_menu` VALUES (2338, '考勤管理', 1, 130, '/system/att', 3, 'menuItem', 'C', '0', NULL, '#', 'admin', '2022-02-26 15:36:54', '', NULL, '', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_menu_top
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_top`;
CREATE TABLE `sys_menu_top`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `target` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `delete_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `delete_flag` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '顶部菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu_top
-- ----------------------------
INSERT INTO `sys_menu_top` VALUES (1, '信息管理', 10, '', 'fa fa-bars', '', NULL, 'admin', '2022-02-26 15:41:31', '', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(11) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `operator_type` int(11) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `oper_user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人ID',
  `oper_user_type` int(11) NULL DEFAULT NULL COMMENT '用户类型',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `status` int(11) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `oper_date` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1116 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(11) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `delete_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `delete_flag` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'school', '学校领导', 1, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-11-24 16:06:05', '', NULL, NULL, 0);
INSERT INTO `sys_post` VALUES (2, 'dean', '系部领导', 2, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-11-24 16:05:54', '', NULL, NULL, 0);
INSERT INTO `sys_post` VALUES (3, 'student', '学生', 3, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-11-24 16:05:41', '', NULL, NULL, 0);
INSERT INTO `sys_post` VALUES (4, 'teacher', '普通教师', 4, '0', 'admin', '2018-03-16 11:33:00', 'admin', '2019-11-24 16:05:29', '', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `delete_flag` tinyint(4) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `delete_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', '0', 0, 'admin', '2018-03-16 11:33:00', 'admin', '2020-10-16 16:46:30', '管理员', NULL, NULL);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'common', 2, '1', '0', 0, 'admin', '2018-03-16 11:33:00', 'admin', '2022-01-03 17:23:40', '普通角色', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (112, 2333);
INSERT INTO `sys_role_menu` VALUES (112, 2335);
INSERT INTO `sys_role_menu` VALUES (112, 2336);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `user_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工号/学号',
  `idcardno` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '盐加密',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 2停用）',
  `shstatus` tinyint(4) NULL DEFAULT 0,
  `user_type` tinyint(4) NULL DEFAULT 0 COMMENT '0教师 1学生 2家长',
  `clazz_id` int(11) NULL DEFAULT NULL COMMENT '***班级ID',
  `student_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '***学生状态',
  `residence` tinyint(4) NULL DEFAULT NULL COMMENT '***住宿状态',
  `guardian_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '***家长姓名',
  `guardian_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '***家长电话',
  `bind_student_id` int(11) NULL DEFAULT NULL COMMENT '***家长绑定的学生ID',
  `openid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信公众号OPENID',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `delete_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `delete_flag` tinyint(4) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `oldpwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `aid` bigint(20) NULL DEFAULT NULL,
  `featureId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `index_longname_unique`(`login_name`, `delete_flag`) USING BTREE,
  UNIQUE INDEX `index_code_unique`(`user_code`, `delete_flag`) USING BTREE,
  UNIQUE INDEX `index_idcardno_unique`(`idcardno`, `delete_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12711 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '超级管理员', '0001', '0001', 'asaa@163.com', '13877887788', '1', '/profile/avatar/2022/09/09/13a5b6d83997c506481839e8ed9e21b1.png', 'f1bed644774d786996236e37ed5af235', '90f800', '0', 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, 'o5An40dpt-Njynxjl7nouxsMA_OU', '2019-08-06 23:43:50', '127.0.0.1', '2022-09-11 12:21:27', '管理员', 'admin', '2018-03-16 11:33:00', 'admin', '2022-09-11 12:21:26', NULL, NULL, 0, 'wU6G3AbTqzby88fFF69W1w==', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (12709, 103, 'STU001', '张三', NULL, NULL, '', '13511112223', '0', '', '70204882c487f75e97b98c768e5405bd', '11e778', '0', 0, 0, NULL, '0', NULL, '', '', NULL, NULL, '2022-09-10 16:38:04', '', NULL, '', '', '2022-09-10 16:38:04', 'admin', '2022-09-11 12:21:53', NULL, NULL, 0, 'PLql1b5bgSBH17YalL+k8wY3qrz2+HZf+8cd1kOfv1JiDrIGx0X4qkmKzrBcfuNR', NULL, NULL, 'abcefghik');
INSERT INTO `sys_user` VALUES (12710, 103, 'STU002', '李四', NULL, NULL, '', '13611113333', '0', '', '88b050cbb32e724de79ca8c9a29ede28', 'ca341a', '0', 0, 0, NULL, '0', NULL, '', '', NULL, NULL, '2022-09-10 21:26:39', '', NULL, '', '', '2022-09-10 21:26:39', 'admin', '2022-09-11 12:22:00', NULL, NULL, 0, 'FNSuEwjsI99p3/2IW0zD/PdkjHwRFdIz5P/Z0nzSvudiDrIGx0X4qkmKzrBcfuNR', NULL, NULL, 'abcefgh12');

-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online`  (
  `sessionId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录账号',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户姓名',
  `user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '学工号',
  `idcardno` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '身份证',
  `user_type` int(11) NULL DEFAULT 0,
  `phonenumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime(0) NULL DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime(0) NULL DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int(11) NULL DEFAULT 0 COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`sessionId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在线用户记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_online
-- ----------------------------
INSERT INTO `sys_user_online` VALUES ('0cafc319-94b3-4c85-8406-853da5af7c64', 'admin', '超级管理员', '0001', '0001', 0, '13877887788', '学校处室', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', 'on_line', '2022-09-11 12:21:11', '2022-09-11 12:21:27', 604800000);
INSERT INTO `sys_user_online` VALUES ('6ef8456e-574a-4ed5-b705-983f121ced41', 'admin', '超级管理员', '0001', '0001', 0, '13877887788', '学校处室', '127.0.0.1', '内网IP', 'Firefox 10', 'Windows 10', 'on_line', '2022-09-10 21:21:43', '2022-09-10 21:21:48', 604800000);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (106, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (12708, 2);

SET FOREIGN_KEY_CHECKS = 1;
