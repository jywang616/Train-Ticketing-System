/*
Navicat MySQL Data Transfer

Source Server         : train
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : train

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2023-07-01 04:32:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for city_station
-- ----------------------------
DROP TABLE IF EXISTS `city_station`;
CREATE TABLE `city_station` (
  `city_name` varchar(20) NOT NULL COMMENT '城市名',
  `station_name` varchar(20) NOT NULL COMMENT '站点名',
  PRIMARY KEY (`station_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市内站点';

-- ----------------------------
-- Records of city_station
-- ----------------------------
INSERT INTO `city_station` VALUES ('上海', '上海北');
INSERT INTO `city_station` VALUES ('上海', '上海南');
INSERT INTO `city_station` VALUES ('东莞', '东莞东');
INSERT INTO `city_station` VALUES ('东莞', '东莞北');
INSERT INTO `city_station` VALUES ('广州', '广州东');
INSERT INTO `city_station` VALUES ('广州', '广州南');
INSERT INTO `city_station` VALUES ('惠州', '惠州北');
INSERT INTO `city_station` VALUES ('惠州', '惠州南');
INSERT INTO `city_station` VALUES ('深圳', '深圳西');
