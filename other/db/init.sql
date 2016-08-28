/*
 Navicat Premium Backup

 Source Server         : 本地centos
 Source Server Type    : MySQL
 Source Server Version : 50630
 Source Host           : 192.168.6.132
 Source Database       : SkDB

 Target Server Type    : MySQL
 Target Server Version : 50630
 File Encoding         : utf-8

 Date: 08/28/2016 17:10:46 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Txls`
-- ----------------------------
DROP TABLE IF EXISTS `Txls`;
CREATE TABLE `Txls` (
  `txlid` int(8) NOT NULL,
  `lxrname` varchar(20) NOT NULL,
  `lxrtel` varchar(20) NOT NULL,
  `userid` int(8) NOT NULL,
  `addtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`txlid`),
  KEY `userid` (`userid`),
  CONSTRAINT `FK_USERS_TXLS_ID` FOREIGN KEY (`userid`) REFERENCES `Users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `Users`
-- ----------------------------
DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(50) NOT NULL,
  `upwd` varchar(50) NOT NULL,
  `addtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `Users`
-- ----------------------------
BEGIN;
INSERT INTO `Users` VALUES ('1', 'zzxb', 'qaz123', '2016-07-17 10:49:01');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
