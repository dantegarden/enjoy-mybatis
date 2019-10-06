/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : mybatis_test

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-10-06 20:21:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_code`
-- ----------------------------
DROP TABLE IF EXISTS `sys_code`;
CREATE TABLE `sys_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code_key` varchar(255) DEFAULT NULL,
  `code_name` varchar(255) DEFAULT NULL,
  `code_type` varchar(255) DEFAULT NULL,
  `code_value` varchar(255) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10013 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_code
-- ----------------------------
INSERT INTO `sys_code` VALUES ('10000', 'S1', '待付款', 'OrderStatus', '1', '1', '1', '2019-07-03 23:49:34', '2019-07-03 23:50:12');
INSERT INTO `sys_code` VALUES ('10001', 'S2', '待发货', 'OrderStatus', '2', '1', '2', '2019-07-02 23:49:37', '2019-07-03 23:50:15');
INSERT INTO `sys_code` VALUES ('10002', 'S3', '待收货', 'OrderStatus', '3', '1', '3', '2019-07-03 23:49:42', '2019-07-03 23:50:17');
INSERT INTO `sys_code` VALUES ('10003', 'S4', '已收货', 'OrderStatus', '4', '1', '4', '2019-07-03 23:49:45', '2019-07-03 23:50:19');
INSERT INTO `sys_code` VALUES ('10004', 'S5', '退款', 'OrderStatus', '5', '1', '5', '2019-07-03 23:49:48', '2019-07-03 23:50:21');
INSERT INTO `sys_code` VALUES ('10005', 'S6', '退货', 'OrderStatus', '6', '1', '6', '2019-07-03 23:49:50', '2019-07-03 23:50:24');
INSERT INTO `sys_code` VALUES ('10006', 'S1', '男', 'CustomerSex', '1', '1', '1', '2019-07-03 23:49:53', '2019-07-03 23:50:27');
INSERT INTO `sys_code` VALUES ('10007', 'S2', '女', 'CustomerSex', '2', '1', '2', '2019-07-03 23:49:56', '2019-07-03 23:50:30');
INSERT INTO `sys_code` VALUES ('10008', 'S3', '不明', 'CustomerSex', '3', '1', '3', '2019-07-03 23:49:58', '2019-07-03 23:50:32');
INSERT INTO `sys_code` VALUES ('10009', 'S1', '是', 'DirectlyUnder', '1', '1', '1', '2019-07-03 23:50:01', '2019-07-03 23:50:34');
INSERT INTO `sys_code` VALUES ('10010', 'S2', '否', 'DirectlyUnder', '0', '1', '1', '2019-07-03 23:50:04', '2019-07-03 23:50:37');
INSERT INTO `sys_code` VALUES ('10011', 'S1', '成本', 'DemoMajor', '1', '1', '1', '2019-07-03 23:50:07', '2019-07-03 23:50:40');
INSERT INTO `sys_code` VALUES ('10012', 'S2', '收入', 'DemoMajor', '2', '1', '1', '2019-07-03 23:50:09', '2019-07-03 23:50:42');

-- ----------------------------
-- Table structure for `sys_org`
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `org_name` varchar(200) DEFAULT NULL,
  `org_code` varchar(200) DEFAULT NULL,
  `delete_status` tinyint(1) DEFAULT '1',
  `pid` bigint(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES ('1', '总集团', '00001', '1', null);
INSERT INTO `sys_org` VALUES ('2', '地产集团', '00002', '1', '1');
INSERT INTO `sys_org` VALUES ('3', '食品集团', '00003', '1', '1');
INSERT INTO `sys_org` VALUES ('4', '科技集团', '00004', '1', '1');
INSERT INTO `sys_org` VALUES ('5', '大数据研究中心', '000041', '1', '4');
INSERT INTO `sys_org` VALUES ('6', '软件开发有限公司', '000042', '1', '4');
INSERT INTO `sys_org` VALUES ('7', '山水集团', '00005', '1', '1');

-- ----------------------------
-- Table structure for `sys_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `relation_name` varchar(200) DEFAULT NULL,
  `relation_age` int(3) DEFAULT NULL,
  `delete_status` tinyint(1) DEFAULT '1',
  `user_id` bigint(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_relation
-- ----------------------------
INSERT INTO `sys_relation` VALUES ('1', '里xx', '34', '1', '10010');
INSERT INTO `sys_relation` VALUES ('2', '王xx', '45', '1', '10010');
INSERT INTO `sys_relation` VALUES ('3', '刘xx', '12', '1', '10019');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(200) DEFAULT NULL,
  `role_code` varchar(200) DEFAULT NULL,
  `delete_status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'admin', '1');
INSERT INTO `sys_role` VALUES ('2', '测试', 'test', '1');
INSERT INTO `sys_role` VALUES ('3', '测试2', 'test2', '1');
INSERT INTO `sys_role` VALUES ('4', '测试3', 'test3', '1');
INSERT INTO `sys_role` VALUES ('5', '测试4', 'test4', '1');
INSERT INTO `sys_role` VALUES ('6', '普通用户', 'user', '1');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `delete_status` tinyint(1) DEFAULT '1',
  `nickname` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `org_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10032 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('10003', '2017-10-30 11:52:38', '1', '超级用户', '123456', '2018-12-05 16:52:19', '1', 'admin', '1');
INSERT INTO `sys_user` VALUES ('10004', '2017-10-30 16:13:02', '1', '莎士比亚', '123456', '2019-01-02 14:47:51', '1', 'user', '4');
INSERT INTO `sys_user` VALUES ('10005', '2017-11-15 14:02:56', '1', 'abba', '123456', '2019-01-02 15:42:48', '1', 'aaa', '2');
INSERT INTO `sys_user` VALUES ('10007', '2017-11-22 16:29:41', '1', '就看看列表', '123456', '2019-01-02 15:42:56', '1', 'test', '1');
INSERT INTO `sys_user` VALUES ('10009', '2018-12-05 16:17:13', '2', '李xx', '123123', '2018-12-05 16:50:12', '1', 'lij', '2');
INSERT INTO `sys_user` VALUES ('10010', '2018-12-05 16:08:17', '1', '李倞', '321321', '2019-09-29 20:54:12', '1', 'lijing', '3');
INSERT INTO `sys_user` VALUES ('10011', '2018-12-05 16:50:54', '1', 'test1', '123', '2019-01-02 15:43:30', '1', 'test1', '3');
INSERT INTO `sys_user` VALUES ('10012', '2018-12-05 16:51:06', '1', 'test2', '123', '2019-01-02 15:43:47', '1', 'test2', '3');
INSERT INTO `sys_user` VALUES ('10013', '2018-12-05 16:51:22', '1', 'test3', '123', '2019-01-02 15:43:35', '1', 'test3', '2');
INSERT INTO `sys_user` VALUES ('10014', '2018-12-05 16:51:42', '1', 'test4', '123', '2019-01-02 15:43:40', '1', 'test4', '4');
INSERT INTO `sys_user` VALUES ('10015', '2018-12-05 16:51:52', '1', 'test5', '123', '2019-01-02 15:43:54', '1', 'test5', '1');
INSERT INTO `sys_user` VALUES ('10016', '2018-12-05 16:52:01', '1', 'test6', '123', '2019-01-02 15:59:49', '1', 'test6', '4');
INSERT INTO `sys_user` VALUES ('10018', '2018-12-21 16:16:37', '2', 'lijing22', '123123', '2018-12-21 16:18:07', '1', 'lijing22', '3');
INSERT INTO `sys_user` VALUES ('10019', '2018-12-25 10:23:02', '2', 'lkk', '123123', '2019-09-30 15:47:31', '2', 'lkk-orm', '2');
INSERT INTO `sys_user` VALUES ('10020', '2018-12-25 10:24:54', '2', 'lkk2', '123', '2019-01-02 16:00:15', '1', 'lkk2', '3');
INSERT INTO `sys_user` VALUES ('10021', '2019-01-02 14:42:09', '1', '新人', '123123', '2019-01-02 14:42:09', '1', '新人', '3');
INSERT INTO `sys_user` VALUES ('10022', '2019-01-02 17:16:53', '1', '测试', '123123', '2019-01-02 17:16:53', '1', '测试', '3');
INSERT INTO `sys_user` VALUES ('10026', '2019-09-28 18:30:04', '2', 'insertIfOper0', 'zxczxc', '2019-09-28 18:30:04', '1', 'insertIfOper0', '4');
INSERT INTO `sys_user` VALUES ('10027', '2019-09-28 18:30:04', '2', 'insertIfOper1', 'zxczxc', '2019-09-28 18:30:04', '1', 'insertIfOper1', '5');
INSERT INTO `sys_user` VALUES ('10028', '2019-09-28 18:30:04', '2', 'insertIfOper2', 'zxczxc', '2019-09-28 18:30:04', '1', 'insertIfOper2', '1');
INSERT INTO `sys_user` VALUES ('10029', '2019-09-29 12:07:56', '2', 'batchExecutor0', '12312321', '2019-09-29 12:07:56', '1', 'batchExecutor0', '2');
INSERT INTO `sys_user` VALUES ('10030', '2019-09-29 12:07:57', '2', 'batchExecutor1', '12312321', '2019-09-29 12:07:57', '1', 'batchExecutor1', null);
INSERT INTO `sys_user` VALUES ('10031', '2019-09-29 12:07:57', '2', 'batchExecutor2', '12312321', '2019-09-29 12:07:57', '1', 'batchExecutor2', null);

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(16) NOT NULL,
  `role_id` bigint(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '10010', '1');
INSERT INTO `sys_user_role` VALUES ('2', '10010', '2');
INSERT INTO `sys_user_role` VALUES ('3', '10019', '3');
INSERT INTO `sys_user_role` VALUES ('4', '10019', '5');

-- ----------------------------
-- Table structure for `t_health_report_man`
-- ----------------------------
DROP TABLE IF EXISTS `t_health_report_man`;
CREATE TABLE `t_health_report_man` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `heart_beats` int(11) DEFAULT NULL,
  `user_id` bigint(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_health_report_man
-- ----------------------------
INSERT INTO `t_health_report_man` VALUES ('1', '2019-09-29', '99', '10010');

-- ----------------------------
-- Table structure for `t_health_report_woman`
-- ----------------------------
DROP TABLE IF EXISTS `t_health_report_woman`;
CREATE TABLE `t_health_report_woman` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `create_time` date DEFAULT NULL,
  `has_baby` tinyint(1) DEFAULT NULL,
  `user_id` bigint(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_health_report_woman
-- ----------------------------
INSERT INTO `t_health_report_woman` VALUES ('1', '2019-09-06', '2', '10019');
