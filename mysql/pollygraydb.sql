-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: pollygraydb
-- ------------------------------------------------------
-- Server version	10.4.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `pollygraydb`
--

/*!40000 DROP DATABASE IF EXISTS `pollygraydb`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `pollygraydb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pollygraydb`;

--
-- Table structure for table `arte`
--

DROP TABLE IF EXISTS `arte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arte` (
  `idArte` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) NOT NULL,
  `unidades` int(11) NOT NULL,
  `preco` float NOT NULL,
  `imagem` varchar(60) NOT NULL,
  `descricao` longtext DEFAULT NULL,
  `dataPublicacao` date NOT NULL,
  `esgotado` tinyint(4) DEFAULT 0,
  PRIMARY KEY (`idArte`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arte`
--

LOCK TABLES `arte` WRITE;
/*!40000 ALTER TABLE `arte` DISABLE KEYS */;
INSERT INTO `arte` VALUES (1,'Arte 1',1,10.5,'CowBoy.jpg','arte teste','2022-11-01',0),(2,'Arte 1',1,10.5,'CowBoy.jpg','aa','2022-11-01',0),(3,'Arte 2',1,100.5,'Freestyle.jpg','asdasd','2022-11-01',0),(4,'Arte 3',1,1020.5,'desafio.png','asdasd','2022-11-01',0),(5,'Arte 4',1,11020.5,'joker mam.png','asdasdasdas','2022-11-01',0),(7,'Tiger',1,4004.5,'tiger.jpg','Thats not me! \nTrust.','2022-11-01',0);
/*!40000 ALTER TABLE `arte` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER unidades_arte_esgotadas_up AFTER UPDATE ON Arte 
FOR EACH ROW
BEGIN
	IF (new.unidades <= 0) THEN
		UPDATE Artes SET esgotado = TRUE;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER actualizar_artes_por_categoria_up AFTER UPDATE ON Arte 
FOR EACH ROW
BEGIN
	DECLARE dif INT;
	SET dif = new.unidades - old.unidades;
	
	IF (dif <> 0) THEN
		UPDATE Categoria SET quantidadeArtes = quantidadeArtes + dif
		WHERE idCategoria = (SELECT idCategoria FROM Arte_Categoria
			WHERE Arte_Categoria.idArte = new.idArte);
	END IF;

	IF (new.unidades = 0) THEN
		UPDATE Artes SET esgotado = TRUE WHERE Artes.idArte = new.idArte;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER actualizar_artes_por_categoria_del BEFORE DELETE ON Arte 
FOR EACH ROW
BEGIN
	UPDATE Categoria SET quantidadeArtes = quantidadeArtes - old.unidades
	WHERE idCategoria = (SELECT idCategoria FROM Arte_Categoria
			WHERE Arte_Categoria.idArte = old.idArte);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `arte_categoria`
--

DROP TABLE IF EXISTS `arte_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arte_categoria` (
  `idArte` int(11) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  PRIMARY KEY (`idArte`,`idCategoria`),
  KEY `fk_Arte_has_Categoria_Categoria1` (`idCategoria`),
  CONSTRAINT `fk_Arte_has_Categoria_Arte` FOREIGN KEY (`idArte`) REFERENCES `arte` (`idArte`) ON DELETE CASCADE,
  CONSTRAINT `fk_Arte_has_Categoria_Categoria1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arte_categoria`
--

LOCK TABLES `arte_categoria` WRITE;
/*!40000 ALTER TABLE `arte_categoria` DISABLE KEYS */;
INSERT INTO `arte_categoria` VALUES (5,1),(7,1);
/*!40000 ALTER TABLE `arte_categoria` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER actualizar_artes_por_categoria_in AFTER INSERT ON arte_categoria
FOR EACH ROW
BEGIN
	UPDATE Categoria SET quantidadeArtes = quantidadeArtes + (SELECT unidades FROM Arte WHERE Arte.idArte = new.idArte)
	WHERE idCategoria = new.idCategoria;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `artista`
--

DROP TABLE IF EXISTS `artista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artista` (
  `idArtista` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `dataNascimento` date NOT NULL,
  `contactos` mediumtext DEFAULT NULL,
  `biografia` longtext DEFAULT NULL,
  `imagem` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idArtista`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artista`
--

LOCK TABLES `artista` WRITE;
/*!40000 ALTER TABLE `artista` DISABLE KEYS */;
INSERT INTO `artista` VALUES (1,'Nuno Fonseca Florencio','2003-10-31','Pessoal: +258 84 3698 443\nEmail: nunofonsecaflorencio@gmail.com\nInstagram: @nuno2f','Sou um artista gráfico.\n\nTrabalho com:\n- Manipulação de imagens;\n- Criação de Logotipos e Identidade Visual;\n- Desenhos vectoriais.\n\nEspero que gostes do meu trabalho artístico.','toolate.jpg'),(7,'Belarmino Junior','2003-09-17','Email: belarminosimaojunior@petalmail.com\nInstagram: @wonderr____\nWhtasApp: +258 85 637 9600','Let\'s leave it empty !!!','Bela.jpg');
/*!40000 ALTER TABLE `artista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artista_arte`
--

DROP TABLE IF EXISTS `artista_arte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artista_arte` (
  `idArtista` int(11) NOT NULL,
  `idArte` int(11) NOT NULL,
  PRIMARY KEY (`idArtista`,`idArte`),
  KEY `fk_Artista_has_Arte_Arte1` (`idArte`),
  CONSTRAINT `fk_Artista_has_Arte_Arte1` FOREIGN KEY (`idArte`) REFERENCES `arte` (`idArte`) ON DELETE CASCADE,
  CONSTRAINT `fk_Artista_has_Arte_Artista1` FOREIGN KEY (`idArtista`) REFERENCES `artista` (`idArtista`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artista_arte`
--

LOCK TABLES `artista_arte` WRITE;
/*!40000 ALTER TABLE `artista_arte` DISABLE KEYS */;
INSERT INTO `artista_arte` VALUES (1,7),(7,4),(7,5);
/*!40000 ALTER TABLE `artista_arte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(65) DEFAULT NULL,
  `quantidadeArtes` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Conceptual',0),(2,'Rupestre',0);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `endereco` varchar(45) DEFAULT NULL,
  `contacto` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compra` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `idArte` int(11) NOT NULL,
  `data` date NOT NULL,
  `unidades` int(11) NOT NULL,
  `precoTotal` float NOT NULL,
  PRIMARY KEY (`idCliente`,`idArte`),
  KEY `fk_Cliente_has_Arte_Arte1` (`idArte`),
  CONSTRAINT `fk_Cliente_has_Arte_Arte1` FOREIGN KEY (`idArte`) REFERENCES `arte` (`idArte`),
  CONSTRAINT `fk_Cliente_has_Arte_Cliente1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'pollygraydb'
--
/*!50003 DROP PROCEDURE IF EXISTS `comprar_arte` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `comprar_arte`(idArtee INT, idClientee INT, unidadess INT)
BEGIN

	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
	END;
    START TRANSACTION;
    
	IF (EXISTS (SELECT * FROM Artes WHERE idArte = idArtee)) THEN
		IF ((SELECT esgotado FROM Artes WHERE idArte = idArtee) = FALSE) THEN
			UPDATE Artes SET unidades = unidades - 1;
			
			INSERT INTO Compra(idCliente, idArte, data, unidades, precoTotal)
			VALUES (idClientee, idArtee, NOW(), unidadess, unidades * (SELECT preco FROM Artes WHERE idArte = idArtee));

		ELSE
			SIGNAL SQLSTATE '45000' SET
			MESSAGE_TEXT = "Arte Esgotada";
		END IF;
	ELSE
        SIGNAL SQLSTATE '45000' SET
		MESSAGE_TEXT = "A Arte Não Existe";
	END IF;
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `publicar_arte` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `publicar_arte`(idArtista INT, idCategoria INT, titulo VARCHAR(100), unidades INT, preco FLOAT, imagem VARCHAR(100), descricao LONGTEXT)
BEGIN
	DECLARE idd INT;


	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
		ROLLBACK;
	END;
    START TRANSACTION;
    
	INSERT INTO Arte (titulo, unidades, preco, imagem, descricao, dataPublicacao) VALUES (titulo, unidades, preco, imagem, descricao, NOW());
	SET idd = (SELECT idArte FROM Arte WHERE (Arte.titulo = titulo AND Arte.imagem = imagem AND Arte.preco = preco));
	INSERT INTO Artista_Arte VALUES (idArtista, idd);
	INSERT INTO Arte_Categoria VALUES (idd, idCategoria);
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-19 13:44:21
