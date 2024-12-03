CREATE DATABASE  IF NOT EXISTS `simplepos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `simplepos`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: simplepos
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (5,'Raw Food'),(6,'Beverage'),(7,'Frozen Food'),(8,'Condiment'),(9,'Dairy'),(12,'Canned Goods'),(14,'Fruits'),(17,'Pizza'),(18,'Bread'),(19,'Noodles');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(256) NOT NULL,
  `role` enum('manager','cashier') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Ger','managerger','password','manager','2024-11-28 13:46:28','2024-11-28 13:46:28'),(2,'Sher','cashiersher','cashcash','cashier','2024-11-28 13:47:27','2024-11-28 13:47:27'),(4,'Shera','cashierashera','sherashera','cashier','2024-11-28 13:50:38','2024-11-28 13:50:38'),(5,'jolly','jollibee','jollyhotdog','cashier','2024-12-02 01:29:31','2024-12-03 16:36:40'),(6,'testcashier','cashiertest',' ','cashier','2024-12-03 03:36:53','2024-12-03 03:36:53'),(7,'testmanager','managertest',' ','manager','2024-12-03 03:36:53','2024-12-03 03:36:53'),(8,'admin','admin','admin','manager','2024-12-03 16:39:28','2024-12-03 16:39:45');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `total_amount` decimal(12,2) NOT NULL,
  `issue_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `invoices_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,4,131.90,'2024-12-01 17:42:43'),(2,4,12.00,'2024-12-01 19:32:55'),(3,4,6.00,'2024-12-01 19:35:03'),(4,4,12.00,'2024-12-01 19:47:53'),(5,4,12.00,'2024-12-01 19:52:38'),(6,4,6.00,'2024-12-01 19:56:48'),(7,4,6.00,'2024-12-01 20:00:32'),(8,4,12.00,'2024-12-01 20:09:49'),(9,4,12.00,'2024-12-01 20:13:01'),(10,4,18.00,'2024-12-01 20:14:04'),(11,4,12.00,'2024-12-01 20:15:43'),(12,4,6.00,'2024-12-01 20:17:11'),(13,4,12.00,'2024-12-01 20:18:43'),(14,4,280.00,'2024-12-01 20:28:56'),(15,4,62.95,'2024-12-01 20:31:22'),(16,4,413.35,'2024-12-01 23:28:48'),(17,4,476.30,'2024-12-01 23:29:28'),(18,4,141.75,'2024-12-01 23:30:47'),(19,4,457.90,'2024-12-02 00:21:01'),(20,4,1000.00,'2024-12-02 01:25:01'),(21,4,803.25,'2024-12-02 01:25:58'),(22,4,509.00,'2024-12-02 01:36:34'),(23,4,184.65,'2024-12-02 01:37:35'),(24,4,113.00,'2024-12-02 01:44:37'),(25,4,137.40,'2024-12-03 01:06:38'),(26,6,701.10,'2024-12-03 03:37:49'),(27,6,274.80,'2024-12-03 03:38:01'),(28,4,141.75,'2024-12-03 19:08:59'),(29,4,473.75,'2024-12-03 20:32:08'),(30,4,278.65,'2024-12-03 20:39:31'),(31,4,94.50,'2024-12-03 20:41:14'),(32,4,137.40,'2024-12-03 20:42:10'),(33,4,189.00,'2024-12-03 20:42:54'),(34,4,330.75,'2024-12-03 20:44:21'),(35,4,300.60,'2024-12-03 20:44:47'),(36,4,189.00,'2024-12-03 20:45:44'),(37,4,206.10,'2024-12-03 20:47:18'),(38,4,137.40,'2024-12-03 20:47:48'),(39,4,137.40,'2024-12-03 20:48:52'),(40,4,141.75,'2024-12-03 20:50:20'),(41,4,94.50,'2024-12-03 20:52:02'),(42,4,420.40,'2024-12-03 20:55:41'),(43,4,206.10,'2024-12-03 20:57:04'),(44,4,141.75,'2024-12-03 20:59:10'),(45,4,274.80,'2024-12-03 21:01:20'),(46,4,547.30,'2024-12-03 21:02:33');
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `per_item_sales`
--

DROP TABLE IF EXISTS `per_item_sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `per_item_sales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `invoice_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `actual_unit_price` decimal(12,2) NOT NULL,
  `actual_total_price` decimal(12,2) GENERATED ALWAYS AS ((`actual_unit_price` * `quantity`)) VIRTUAL,
  PRIMARY KEY (`id`),
  KEY `invoice_id` (`invoice_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`),
  CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `per_item_sales`
--

LOCK TABLES `per_item_sales` WRITE;
/*!40000 ALTER TABLE `per_item_sales` DISABLE KEYS */;
INSERT INTO `per_item_sales` (`id`, `invoice_id`, `product_id`, `quantity`, `actual_unit_price`) VALUES (15,14,6,3,62.95),(16,14,3,1,22.45),(17,14,7,1,68.70),(18,15,6,1,62.95),(19,16,6,3,62.95),(20,16,3,10,22.45),(21,17,3,10,22.45),(22,17,6,4,62.95),(23,18,25,3,47.25),(24,19,6,4,62.95),(25,19,24,3,68.70),(27,21,25,17,47.25),(30,23,25,1,47.25),(31,23,24,2,68.70),(34,25,7,2,68.70),(36,26,24,3,68.70),(37,27,24,4,68.70),(38,28,25,3,47.25),(39,29,25,3,47.25),(40,29,7,3,68.70),(41,29,6,2,62.95),(42,30,6,3,62.95),(43,30,3,4,22.45),(44,32,24,2,68.70),(45,33,25,4,47.25),(46,34,25,7,47.25),(47,35,25,2,47.25),(48,35,7,3,68.70),(49,36,25,4,47.25),(50,37,24,3,68.70),(51,38,24,2,68.70),(52,41,25,2,47.25),(53,43,7,3,68.70),(54,44,25,3,47.25),(55,45,24,4,68.70),(56,46,25,6,47.25),(57,46,7,2,68.70),(58,46,6,1,62.95),(59,46,3,1,22.45),(60,46,35,2,10.00),(61,46,36,2,10.50);
/*!40000 ALTER TABLE `per_item_sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `barcode` varchar(15) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `quantity` int NOT NULL,
  `quantity_type` enum('counted','weighted (g)') NOT NULL DEFAULT 'counted',
  `category_id` int DEFAULT NULL,
  `supplier_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `barcode_UNIQUE` (`barcode`),
  KEY `category_id` (`category_id`),
  KEY `products_ibfk_2_idx` (`supplier_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (3,'NISSIN CUP NOODLES SEAFOOD 40G ','4800016552113',22.45,275,'counted',19,3,'2024-11-27 19:49:57','2024-12-03 21:02:33'),(6,'ROYAL TRU ORANGE 1.5 LITERS','4801981116171',62.95,104,'counted',6,4,'2024-11-28 14:02:32','2024-12-03 21:02:33'),(7,'MAGNOLIA CHEEZEE MILKY WHITE 160G','4805358425880',68.70,89,'counted',NULL,NULL,'2024-11-28 14:05:21','2024-12-03 21:02:33'),(24,'LADYS CHOICE REAL MAYONNAISE REGULAR 27MLX6S','4800888607126',68.70,27,'counted',9,NULL,'2024-12-01 22:40:43','2024-12-03 21:01:20'),(25,'CDO KARNE NORTE CLASSIC GUISADO 260G','4800249908657',47.25,66,'counted',12,NULL,'2024-12-01 22:41:08','2024-12-03 21:02:33'),(35,'itlog','13123',10.00,8,'counted',NULL,NULL,'2024-12-03 13:21:51','2024-12-03 21:02:33'),(36,'testItem','123123',10.50,8,'counted',NULL,NULL,'2024-12-03 13:24:20','2024-12-03 21:02:33');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (2,'Blue Sky Trading','1111111',NULL,NULL,'2024-11-28 13:57:16','2024-11-28 13:57:16'),(3,'The Salamanca Trading','2222222','test@test.test',NULL,'2024-11-28 13:57:16','2024-12-03 20:08:51'),(4,'Gustavo Trading','3333333',NULL,NULL,'2024-11-28 13:57:16','2024-11-28 13:57:16'),(5,'Walter White Trading','4444444',NULL,'221B Baker Street','2024-11-28 13:57:16','2024-12-03 20:09:09'),(7,'Spongebob',NULL,NULL,NULL,'2024-12-03 11:29:13','2024-12-03 11:29:13'),(8,'New Supplier','','','	','2024-12-03 20:09:19','2024-12-03 20:09:19');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-04  5:20:51
