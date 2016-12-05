-- MySQL dump 10.13  Distrib 5.5.53, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: gluu_log
-- ------------------------------------------------------
-- Server version	5.5.53-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `gluu_log`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `gluu_log` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `gluu_log`;

--
-- Table structure for table `oauth2_audit_logging_event`
--

DROP TABLE IF EXISTS `oauth2_audit_logging_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth2_audit_logging_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `mac_address` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `success` bit(1) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `oauth2_audit_logging_event_timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oxauth_server_logging_event`
--

DROP TABLE IF EXISTS `oxauth_server_logging_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oxauth_server_logging_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `formatted_message` text,
  `level` varchar(255) DEFAULT NULL,
  `logger_name` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `oxauth_server_logging_event_timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oxauth_server_logging_event_exception`
--

DROP TABLE IF EXISTS `oxauth_server_logging_event_exception`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oxauth_server_logging_event_exception` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `index` int(11) DEFAULT NULL,
  `trace_line` text,
  `oxauth_server_logging_event_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtp5p28uolrsx6vlj6annm7255` (`oxauth_server_logging_event_id`),
  CONSTRAINT `FKtp5p28uolrsx6vlj6annm7255` FOREIGN KEY (`oxauth_server_logging_event_id`) REFERENCES `oxauth_server_logging_event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-21 18:51:28
