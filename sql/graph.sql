/*
Navicat MySQL Data Transfer

Source Server         : train
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : train

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2023-07-01 04:33:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for graph
-- ----------------------------
DROP TABLE IF EXISTS `graph`;
CREATE TABLE `graph` (
  `start_city` varchar(20) NOT NULL COMMENT '出发城市',
  `end_city` varchar(20) NOT NULL COMMENT '到达城市',
  `duration` bigint(20) DEFAULT NULL COMMENT '时间间隔',
  `start_date` date NOT NULL COMMENT '发车日期',
  PRIMARY KEY (`start_city`,`end_city`,`start_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市时间图';

-- ----------------------------
-- Records of graph
-- ----------------------------
INSERT INTO `graph` VALUES ('东莞', '惠州', '2', '2023-06-28');
INSERT INTO `graph` VALUES ('东莞', '深圳', '60', '2023-06-28');
INSERT INTO `graph` VALUES ('广州', '东莞', '0', '2023-06-28');
INSERT INTO `graph` VALUES ('广州', '广州', '0', '2023-06-28');
INSERT INTO `graph` VALUES ('惠州', '深圳', '2', '2023-06-28');
