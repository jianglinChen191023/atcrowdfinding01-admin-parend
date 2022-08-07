/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : 175.178.174.83:3306
 Source Schema         : project_crowd

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 08/08/2022 03:58:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for inner_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `inner_admin_role`;
CREATE TABLE `inner_admin_role` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `admin_id` int(11) DEFAULT NULL,
                                    `role_id` int(11) DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户和权限中间表';

-- ----------------------------
-- Records of inner_admin_role
-- ----------------------------
BEGIN;
INSERT INTO `inner_admin_role` (`id`, `admin_id`, `role_id`) VALUES (1, 2, 2);
INSERT INTO `inner_admin_role` (`id`, `admin_id`, `role_id`) VALUES (2, 2, 4);
INSERT INTO `inner_admin_role` (`id`, `admin_id`, `role_id`) VALUES (3, 1, 1);
INSERT INTO `inner_admin_role` (`id`, `admin_id`, `role_id`) VALUES (4, 1, 3);
INSERT INTO `inner_admin_role` (`id`, `admin_id`, `role_id`) VALUES (5, 7, 5);
COMMIT;

-- ----------------------------
-- Table structure for inner_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `inner_role_auth`;
CREATE TABLE `inner_role_auth` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `role_id` int(11) DEFAULT NULL,
                                   `auth_id` int(11) DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色和权限中间表';

-- ----------------------------
-- Records of inner_role_auth
-- ----------------------------
BEGIN;
INSERT INTO `inner_role_auth` (`id`, `role_id`, `auth_id`) VALUES (1, 3, 4);
INSERT INTO `inner_role_auth` (`id`, `role_id`, `auth_id`) VALUES (2, 4, 6);
INSERT INTO `inner_role_auth` (`id`, `role_id`, `auth_id`) VALUES (3, 4, 3);
COMMIT;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `login_acct` varchar(255) NOT NULL,
                           `user_pswd` varchar(255) NOT NULL,
                           `user_name` varchar(255) NOT NULL,
                           `email` varchar(255) NOT NULL,
                           `create_time` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
BEGIN;
INSERT INTO `t_admin` (`id`, `login_acct`, `user_pswd`, `user_name`, `email`, `create_time`) VALUES (1, 'adminOperator', '$2a$10$YlFApwsuOTRxvVlzIKJ1IO20wPftE57QovWuBsKoh2NEYZWKLT6jK', '经理', '1@qq.com', '2022-08-06 15:50:25');
INSERT INTO `t_admin` (`id`, `login_acct`, `user_pswd`, `user_name`, `email`, `create_time`) VALUES (2, 'roleOperator', '$2a$10$YlFApwsuOTRxvVlzIKJ1IO20wPftE57QovWuBsKoh2NEYZWKLT6jK', '部长', '2@qq.com', '2022-08-06 15:50:25');
INSERT INTO `t_admin` (`id`, `login_acct`, `user_pswd`, `user_name`, `email`, `create_time`) VALUES (3, 'admin01', '$2a$10$YlFApwsuOTRxvVlzIKJ1IO20wPftE57QovWuBsKoh2NEYZWKLT6jK', '路人甲', '3@qq.com', '2022-08-06 15:50:25');
INSERT INTO `t_admin` (`id`, `login_acct`, `user_pswd`, `user_name`, `email`, `create_time`) VALUES (4, 'admin02', '2a$10$YlFApwsuOTRxvVlzIKJ1IO20wPftE57QovWuBsKoh2NEYZWKLT6jK', '路人甲', '3@qq.com', '2022-08-06 15:50:25');
INSERT INTO `t_admin` (`id`, `login_acct`, `user_pswd`, `user_name`, `email`, `create_time`) VALUES (5, 'admin03', '$2a$10$YlFApwsuOTRxvVlzIKJ1IO20wPftE57QovWuBsKoh2NEYZWKLT6jK', '路人甲', '3@qq.com', '2022-08-06 15:50:25');
INSERT INTO `t_admin` (`id`, `login_acct`, `user_pswd`, `user_name`, `email`, `create_time`) VALUES (6, 'CS3', '$2a$10$elqat0c.KxAlnRV58VOBfuCMboRRNnKG4Qs1bcc4qJDAQTqPLDRTi', '测试3', '1912623646@qq.com', '2022-08-08 01:46:56');
INSERT INTO `t_admin` (`id`, `login_acct`, `user_pswd`, `user_name`, `email`, `create_time`) VALUES (7, 'root', '$2a$10$YlFApwsuOTRxvVlzIKJ1IO20wPftE57QovWuBsKoh2NEYZWKLT6jK', '超级管理员', '0@qq.com', '2022-08-08 03:10:44');
COMMIT;

-- ----------------------------
-- Table structure for t_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_auth`;
CREATE TABLE `t_auth` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `name` varchar(200) DEFAULT NULL,
                          `title` varchar(200) DEFAULT NULL,
                          `category_id` int(11) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_auth
-- ----------------------------
BEGIN;
INSERT INTO `t_auth` (`id`, `name`, `title`, `category_id`) VALUES (1, '', '用户模块', NULL);
INSERT INTO `t_auth` (`id`, `name`, `title`, `category_id`) VALUES (2, 'user:delete', '删除', 1);
INSERT INTO `t_auth` (`id`, `name`, `title`, `category_id`) VALUES (3, 'user:get', '查询', 1);
INSERT INTO `t_auth` (`id`, `name`, `title`, `category_id`) VALUES (4, 'user:save', '保存', 1);
INSERT INTO `t_auth` (`id`, `name`, `title`, `category_id`) VALUES (5, '', '角色模块', NULL);
INSERT INTO `t_auth` (`id`, `name`, `title`, `category_id`) VALUES (6, 'role:delete', '删除', 4);
INSERT INTO `t_auth` (`id`, `name`, `title`, `category_id`) VALUES (7, 'role:get', '查询', 4);
INSERT INTO `t_auth` (`id`, `name`, `title`, `category_id`) VALUES (8, 'role:save', '保存', 4);
COMMIT;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `pid` int(11) DEFAULT NULL,
                          `name` varchar(200) DEFAULT NULL,
                          `url` varchar(200) DEFAULT NULL,
                          `icon` varchar(200) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (1, NULL, '系统权限菜单', NULL, 'glyphicon glyphicon-th-list');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (2, 1, ' 控 制 面 板 ', 'main.htm', 'glyphicon glyphicon-dashboard');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (3, 1, '权限管理', NULL, 'glyphicon glyphicon glyphicon-tasks');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (4, 3, ' 用 户 维 护 ', 'user/index.htm', 'glyphicon glyphicon-user');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (5, 3, ' 角 色 维 护 ', 'role/index.htm', 'glyphicon glyphicon-king');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (6, 3, ' 菜 单 维 护 ', 'permission/index.htm', 'glyphicon glyphicon-lock');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (7, 1, ' 业 务 审 核 ', NULL, 'glyphicon glyphicon-ok');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (8, 7, ' 实 名 认 证 审 核 ', 'auth_cert/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (9, 7, ' 广 告 审 核 ', 'auth_adv/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (10, 7, ' 项 目 审 核 ', 'auth_project/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (11, 1, ' 业 务 管 理 ', NULL, 'glyphicon glyphicon-th-large');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (12, 11, ' 资 质 维 护 ', 'cert/index.htm', 'glyphicon glyphicon-picture');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (13, 11, ' 分 类 管 理 ', 'certtype/index.htm', 'glyphicon glyphicon-equalizer');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (14, 11, ' 流 程 管 理 ', 'process/index.htm', 'glyphicon glyphicon-random');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (15, 11, ' 广 告 管 理 ', 'advert/index.htm', 'glyphicon glyphicon-hdd');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (16, 11, ' 消 息 模 板 ', 'message/index.htm', 'glyphicon glyphicon-comment');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (17, 11, ' 项 目 分 类 ', 'projectType/index.htm', 'glyphicon glyphicon-list');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (18, 11, ' 项 目 标 签 ', 'tag/index.htm', 'glyphicon glyphicon-tags');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (19, 1, ' 参 数 管 理 ', 'param/index.htm', 'glyphicon glyphicon-list-alt');
INSERT INTO `t_menu` (`id`, `pid`, `name`, `url`, `icon`) VALUES (20, 1, 'A', 'A', 'glyphicon glyphicon-list-alt');
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色 ID',
                          `name` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` (`id`, `name`) VALUES (1, '经理');
INSERT INTO `t_role` (`id`, `name`) VALUES (2, '部长');
INSERT INTO `t_role` (`id`, `name`) VALUES (3, '经理操作者');
INSERT INTO `t_role` (`id`, `name`) VALUES (4, '部长操作者');
INSERT INTO `t_role` (`id`, `name`) VALUES (5, 'Admin');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
