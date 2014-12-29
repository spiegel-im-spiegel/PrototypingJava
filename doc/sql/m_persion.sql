-- --------------------------------------------------------
-- ホスト:                          127.0.0.1
-- Server version:               5.5.5-10.0.15-MariaDB - mariadb.org binary distribution
-- サーバー OS:                      Win64
-- HeidiSQL バージョン:               8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for テーブル test.m_person
DROP TABLE IF EXISTS `m_person`;
CREATE TABLE IF NOT EXISTS `m_person` (
  `PERSON_ID` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ユーザID',
  `PERSON_NAME` varchar(255) NOT NULL COMMENT 'ユーザ名',
  `AGE` smallint(3) unsigned NOT NULL COMMENT '年齢',
  `UPD_TIME` datetime NOT NULL COMMENT '更新日時',
  PRIMARY KEY (`PERSON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ユーザ管理マスタ';

