-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: instaclone
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(100) NOT NULL,
  `likeNum` int(11) DEFAULT '0',
  `userId` varchar(20) NOT NULL,
  `postId` varchar(20) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`commentId`),
  KEY `fk_comment_user1_idx` (`userId`),
  KEY `fk_comment_post1_idx` (`postId`),
  CONSTRAINT `fk_comment_post1` FOREIGN KEY (`postId`) REFERENCES `post` (`postId`),
  CONSTRAINT `fk_comment_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'good',0,'id01','post07','2020-05-15 09:55:32'),(16,'만세.',0,'id04','post11','2020-05-15 10:01:26'),(17,'YESS',0,'id03','post11','2020-05-15 10:04:38'),(20,'굿.',0,'id01','post11','2020-05-15 10:04:38'),(22,'finally',0,'id02','post02','2020-05-15 10:19:45'),(23,'finally',0,'id02','post02','2020-05-15 10:20:32'),(24,'주혁아 사랑해',0,'id01','post03','2020-05-15 10:22:03'),(27,'주혁아 넌 진짜 멋지다.',26,'id05','post20','2020-05-15 20:27:50'),(28,'주혁아 넌 진짜 멋지다.',0,'id05','post20','2020-05-15 20:27:50'),(29,'졸리다... 자자',0,'id05','post20','2020-05-15 20:27:50'),(30,'주혁아 넌 진짜 멋지다.',0,'id05','post20','2020-05-15 20:27:50'),(31,'주혁아 넌 진짜 멋지다.',0,'id05','post20','2020-05-15 20:27:50'),(32,'finally',0,'id05','post20','2020-05-15 20:27:50');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `followIdx` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(20) NOT NULL,
  `followingId` varchar(20) NOT NULL,
  PRIMARY KEY (`followIdx`),
  KEY `fk_user_has_user_user2_idx` (`followingId`),
  KEY `fk_user_has_user_user1_idx` (`userId`),
  CONSTRAINT `fk_user_has_user_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `fk_user_has_user_user2` FOREIGN KEY (`followingId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (1,'id01','id02'),(2,'id01','id04'),(3,'id01','id05'),(4,'id02','id01'),(5,'id03','id01'),(6,'id02','id02'),(7,'id03','id02'),(8,'id04','id02'),(9,'id05','id02'),(10,'id04','id03'),(11,'id05','id03'),(12,'id02','id03'),(13,'id05','id04'),(14,'id02','id05'),(15,'id03','id05'),(16,'id04','id05');
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashgroup`
--

DROP TABLE IF EXISTS `hashgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashgroup` (
  `HashGroupIdx` int(11) NOT NULL AUTO_INCREMENT,
  `postId` varchar(20) NOT NULL,
  `hashtagId` varchar(20) NOT NULL,
  PRIMARY KEY (`HashGroupIdx`),
  KEY `fk_post_has_hashtag_hashtag1_idx` (`hashtagId`),
  KEY `fk_post_has_hashtag_post1_idx` (`postId`),
  CONSTRAINT `fk_post_has_hashtag_hashtag1` FOREIGN KEY (`hashtagId`) REFERENCES `hashtag` (`hashtagId`),
  CONSTRAINT `fk_post_has_hashtag_post1` FOREIGN KEY (`postId`) REFERENCES `post` (`postId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashgroup`
--

LOCK TABLES `hashgroup` WRITE;
/*!40000 ALTER TABLE `hashgroup` DISABLE KEYS */;
INSERT INTO `hashgroup` VALUES (1,'post01','travel'),(2,'post01','nature'),(3,'post02','travel'),(4,'post02','nature'),(5,'post02','vacation'),(6,'post02','trip'),(7,'post03','travel'),(8,'post03','nature'),(9,'post03','trip'),(10,'post04','nature'),(11,'post04','vacation'),(12,'post05','computer'),(13,'post05','algorithm'),(14,'post05','programming'),(15,'post06','computer'),(16,'post06','programming'),(17,'post07','programming'),(18,'post08','computer'),(19,'post09','programming'),(21,'post11','algorithm');
/*!40000 ALTER TABLE `hashgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtag` (
  `hashtagId` varchar(20) NOT NULL,
  PRIMARY KEY (`hashtagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtag`
--

LOCK TABLES `hashtag` WRITE;
/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
INSERT INTO `hashtag` VALUES ('algorithm'),('computer'),('nature'),('programming'),('travel'),('trip'),('vacation');
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persontag`
--

DROP TABLE IF EXISTS `persontag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persontag` (
  `personTagIdx` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(20) NOT NULL,
  `postId` varchar(20) NOT NULL,
  `postOwner` varchar(4) NOT NULL,
  PRIMARY KEY (`personTagIdx`),
  KEY `fk_user_has_post_post1_idx` (`postId`),
  KEY `fk_user_has_post_user1_idx` (`userId`),
  CONSTRAINT `fk_user_has_post_post1` FOREIGN KEY (`postId`) REFERENCES `post` (`postId`),
  CONSTRAINT `fk_user_has_post_user1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persontag`
--

LOCK TABLES `persontag` WRITE;
/*!40000 ALTER TABLE `persontag` DISABLE KEYS */;
INSERT INTO `persontag` VALUES (1,'id01','post01','Y'),(2,'id02','post01','N'),(3,'id04','post01','N'),(4,'id01','post02','Y'),(5,'id04','post02','N'),(6,'id05','post02','N'),(7,'id02','post03','Y'),(8,'id01','post03','N'),(9,'id03','post03','N'),(10,'id04','post03','N'),(11,'id02','post04','Y'),(12,'id01','post04','N'),(13,'id03','post04','N'),(14,'id02','post05','Y'),(15,'id04','post05','N'),(16,'id03','post06','Y'),(17,'id04','post06','N'),(18,'id05','post06','N'),(19,'id03','post07','Y'),(20,'id01','post07','N'),(21,'id02','post07','N'),(22,'id05','post07','N'),(23,'id04','post08','Y'),(24,'id02','post08','N'),(25,'id03','post08','N'),(26,'id04','post09','Y'),(27,'id01','post09','N'),(28,'id02','post09','N'),(29,'id03','post09','N'),(32,'id05','post11','Y'),(33,'id02','post11','N');
/*!40000 ALTER TABLE `persontag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `postId` varchar(20) NOT NULL,
  `caption` varchar(100) NOT NULL,
  `imageSrc` varchar(45) DEFAULT NULL,
  `likeNum` int(11) DEFAULT '0',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userId` varchar(45) NOT NULL DEFAULT 'id02',
  PRIMARY KEY (`postId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES ('post01','대애애애애애ㅐ박','local:C//쓰레기통',3,'2020-05-14 13:05:39','id02'),('post02','The sun','src\\images\\sun.jpg',3,'2020-05-14 13:05:39','id02'),('post03','The star','src\\images\\star.jpg',2,'2020-05-14 13:05:39','id02'),('post04','The mt','src\\images\\mt.jpg',1,'2020-05-14 13:05:39','id02'),('post05','The computer','src\\images\\computer.jpg',27,'2020-05-14 13:05:39','id02'),('post06','The python3','src\\images\\python3.jpg',1,'2020-05-14 13:05:39','id02'),('post07','The thread','src\\images\\thread.jpg',0,'2020-05-14 13:05:39','id02'),('post08','The server','src\\images\\server.jpg',0,'2020-05-14 13:05:39','id02'),('post09','The for','src\\images\\for.jpg',2,'2020-05-14 13:05:39','id02'),('post11','The break','src\\mages\\break.jpg',2,'2020-05-14 13:05:39','id02'),('post14','The sea','src\\images\\sea.jpg',3,'2020-05-15 11:55:41','id01'),('post15','대머리','src\\images\\hair.jpg',2,'2020-05-15 11:56:03','id01'),('post16','대머리','src\\images\\hair.jpg',2,'2020-05-15 11:56:15','id04'),('post17','아정말','src\\images\\hair.jpg',2631,'2020-05-15 11:57:54','id03'),('post18','팀워크','src\\images\\hair.jpg',261,'2020-05-15 11:57:54','id03'),('post19','안녕하세요','src\\images\\hair.jpg',5612,'2020-05-15 11:57:54','id05'),('post20','테드','src\\images\\hair.jpg',23,'2020-05-15 11:57:54','id01'),('post21','칭칭','src\\images\\hair.jpg',22,'2020-05-15 11:57:54','id03'),('post22','대애박','local:C//휴지통',0,'2020-05-15 16:55:21','id03'),('post24','The sun','src\\images\\sun.jpg',0,'2020-05-15 16:47:54','id04');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` varchar(20) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `followerNum` int(11) DEFAULT '0',
  `followingNum` int(11) DEFAULT '0',
  `postNum` int(11) DEFAULT '0',
  `email` varchar(45) NOT NULL,
  `gender` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`userId`,`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('id01','Ree','1234',2,1,0,'beanskobe@gmail.com','F'),('id02','Kim','1234',4,3,0,'abc@test.com','W'),('id03','Kang','1234',3,3,0,'ave@test.com','M'),('id04','Song','1234',1,3,0,'age@test.com','W'),('id05','Park','1234',3,3,0,'ajw@test.com','M'),('id06','Kim','1234',0,0,0,'beanskobe@gmail.com','M');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'instaclone'
--

--
-- Dumping routines for database 'instaclone'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-16  9:14:34
