-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: myarrow
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.1

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
-- Table structure for table `rundenziel`
--

DROP TABLE IF EXISTS `rundenziel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rundenziel` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` varchar(50) NOT NULL,
  `rundengid` varchar(50) NOT NULL,
  `zielgid` varchar(50) NOT NULL,
  `rundenschuetzengid` varchar(50) NOT NULL,
  `nummer` int(11) DEFAULT NULL,
  `eins` int(11) DEFAULT NULL,
  `zwei` int(11) DEFAULT NULL,
  `drei` int(11) DEFAULT NULL,
  `kills` int(11) DEFAULT NULL,
  `killkill` int(11) DEFAULT NULL,
  `punkte` int(11) DEFAULT NULL,
  `anmerkung` varchar(256) DEFAULT NULL,
  `dateiname` varchar(256) DEFAULT NULL,
  `zeitstempel` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`_id`,`gid`),
  UNIQUE KEY `_id_UNIQUE` (`_id`),
  UNIQUE KEY `gid_UNIQUE` (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-24 18:08:41
