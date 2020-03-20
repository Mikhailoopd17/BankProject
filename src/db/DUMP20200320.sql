CREATE DATABASE  IF NOT EXISTS `bank_db` /*!40100 DEFAULT CHARACTER SET cp1251 */;
USE `bank_db`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: bank_db
-- ------------------------------------------------------
-- Server version	5.5.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Иванов Иван Иванович','614000, г.Пермь, ул.Ленина, д 1б кв.1'),(2,'Смирнов Перт Васильевич','61400, Пермь, ул.Екатерининская, '),(3,'Сидоров Иван Иванович','614090, г.Пермь, ул.Стахановская'),(4,'BANKOMAT--1077121930','-'),(5,'SHOP-Magazine-417908875','-'),(6,'BANKOMAT--973245383','-'),(7,'SHOP-Magazine--1420658580','-'),(8,'SHOP-Magazine-973776645','-');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scopes`
--

DROP TABLE IF EXISTS `scopes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scopes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `capasity` float NOT NULL,
  `clients_id` int(11) NOT NULL,
  `currency` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_scopes_clients_idx` (`clients_id`),
  CONSTRAINT `fk_scopes_clients` FOREIGN KEY (`clients_id`) REFERENCES `clients` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scopes`
--

LOCK TABLES `scopes` WRITE;
/*!40000 ALTER TABLE `scopes` DISABLE KEYS */;
INSERT INTO `scopes` VALUES (1,'Карта Visa',14900,1,'Рубль'),(2,'Карта МИР',35100,1,'Рубль'),(4,'AnotherBank',4900000,4,'Рубль'),(5,'AnotherShop',5100000,5,'Рубль'),(6,'Сбер счет',30000,1,'Рубль'),(7,'AnotherBank',4900000,6,'Рубль'),(8,'Карта Visa',643.8,2,'Рубль'),(9,'Карта МИР',0,2,'Рубль'),(10,'Сбер счет',57.2224,3,'Доллар'),(11,'AnotherShop',5000100,7,'Рубль'),(12,'AnotherShop',5015000,8,'Рубль');
/*!40000 ALTER TABLE `scopes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `scopes_id_at` int(11) NOT NULL,
  `scopes_id_to` int(11) NOT NULL,
  `sum_transaction` float NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transaction_scopes1_idx` (`scopes_id_at`),
  KEY `fk_transaction_scopes2_idx` (`scopes_id_to`),
  CONSTRAINT `fk_transaction_scopes1` FOREIGN KEY (`scopes_id_at`) REFERENCES `scopes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_scopes2` FOREIGN KEY (`scopes_id_to`) REFERENCES `scopes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=cp1251;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (3,'2020-03-20 07:40:30',7,2,100000,'Пополнение'),(4,'2020-03-20 07:40:37',2,1,15000,'Перевод между счетами клиента'),(5,'2020-03-20 07:41:00',2,6,50000,'Перевод между счетами клиента'),(6,'2020-03-20 07:44:19',6,12,15000,'Списание средст(покупка)'),(7,'2020-03-20 07:43:33',10,8,10,'Перевод на счет другому клиенту'),(8,'2020-03-20 07:43:49',8,11,100,'Списание средст(покупка)');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bank_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-20 13:45:45
