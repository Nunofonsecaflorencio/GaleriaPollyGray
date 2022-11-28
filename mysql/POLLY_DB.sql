-- -------------------------- --
-- 1. CRIAÇÃO DA BD PollyGray --
-- -------------------------- --

CREATE DATABASE IF NOT EXISTS `pollygraydb_test`;
USE `pollygraydb_test`;

-- ---------------------- --
-- 2. CRIAÇÃO DE TABELAS  --
-- ---------------------- --

	-- TABELA ARTE
    
DROP TABLE IF EXISTS `arte`;
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
);

	-- Triggers Da Tabela Arte
    

DELIMITER ;;
CREATE TRIGGER unidades_arte_esgotadas_up AFTER UPDATE ON Arte 
FOR EACH ROW

-- Actualiza o estado da arte, Certifica quando esgota.

BEGIN
	IF (new.unidades <= 0) THEN
		UPDATE Artes SET esgotado = TRUE;
	END IF;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE TRIGGER actualizar_artes_por_categoria_up AFTER UPDATE ON Arte 
FOR EACH ROW

-- Actualiza a quantidade de artes da categoria quando a arte é actualizada.

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
END ;;
DELIMITER ;

DELIMITER ;;
CREATE TRIGGER actualizar_artes_por_categoria_del BEFORE DELETE ON Arte 
FOR EACH ROW

-- Diminui a quantidades de arte na categoria correspondente a esta arte

BEGIN
	UPDATE Categoria SET quantidadeArtes = quantidadeArtes - old.unidades
	WHERE idCategoria = (SELECT idCategoria FROM Arte_Categoria
			WHERE Arte_Categoria.idArte = old.idArte);
END ;;
DELIMITER ;


	-- TABELA ARTISTA
    
DROP TABLE IF EXISTS `artista`;
CREATE TABLE `artista` (
  `idArtista` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `dataNascimento` date NOT NULL,
  `contactos` mediumtext DEFAULT NULL,
  `biografia` longtext DEFAULT NULL,
  `imagem` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idArtista`)
);

	-- TABELA CATEGORIA
    
DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(65) DEFAULT NULL,
  `quantidadeArtes` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
);

	-- TABELA CLIENTE

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `endereco` varchar(45) DEFAULT NULL,
  `contacto` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


	-- TABELA ARTE_ARTISTA
    
DROP TABLE IF EXISTS `artista_arte`;
CREATE TABLE `artista_arte` (
  `idArtista` int(11) NOT NULL,
  `idArte` int(11) NOT NULL,
  PRIMARY KEY (`idArtista`,`idArte`),
  KEY `fk_Artista_has_Arte_Arte1` (`idArte`),
  CONSTRAINT `fk_Artista_has_Arte_Arte1` FOREIGN KEY (`idArte`) REFERENCES `arte` (`idArte`) ON DELETE CASCADE,
  CONSTRAINT `fk_Artista_has_Arte_Artista1` FOREIGN KEY (`idArtista`) REFERENCES `artista` (`idArtista`) ON DELETE CASCADE
);
    
	-- TABELA ARTE_CATEGORIA
    
DROP TABLE IF EXISTS `arte_categoria`;
CREATE TABLE `arte_categoria` (
  `idArte` int(11) NOT NULL,
  `idCategoria` int(11) NOT NULL,
  PRIMARY KEY (`idArte`,`idCategoria`),
  KEY `fk_Arte_has_Categoria_Categoria1` (`idCategoria`),
  CONSTRAINT `fk_Arte_has_Categoria_Arte` FOREIGN KEY (`idArte`) REFERENCES `arte` (`idArte`) ON DELETE CASCADE,
  CONSTRAINT `fk_Arte_has_Categoria_Categoria1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`) ON DELETE CASCADE
);
    
	-- TABELA COMPRA

DROP TABLE IF EXISTS `compra`;
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
);



-- ----------------------------- --
-- 3. PROCEDIMENTOS / TRANSAÇÕES --
-- ----------------------------- --


-- PUBLICAR ARTE 

DROP PROCEDURE IF EXISTS `publicar_arte`;
DELIMITER ;;
CREATE PROCEDURE `publicar_arte`(idArtista INT, idCategoria INT, titulo VARCHAR(100), unidades INT, preco FLOAT, imagem VARCHAR(100), descricao LONGTEXT)
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


-- COMPRAR ARTE

DROP PROCEDURE IF EXISTS `comprar_arte`;
DELIMITER ;;
CREATE PROCEDURE `comprar_arte`(idArtee INT, idClientee INT, unidadess INT)
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



-- ---------- --
-- 4. ïNDICES --
-- ---------- --

-- Arte por Estado
DROP INDEX  i_Estado_Arte ON Arte; -- drop
CREATE INDEX i_Estado_Arte ON Arte (esgotado);

-- Arte por Ano
ALTER TABLE Arte DROP ano; -- drop
ALTER TABLE Arte ADD ano VARCHAR(4) AS (YEAR(dataPublicacao)) STORED;

DROP INDEX  i_DataPublicacao_Arte ON Arte; -- drop
CREATE INDEX i_DataPublicacao_Arte ON Arte (ano);

-- Artista por Ano de Nascimento
ALTER TABLE Artista DROP ano; -- drop
ALTER TABLE Artista ADD ano VARCHAR(4) AS (YEAR(dataNascimento)) STORED;

DROP INDEX  i_DataNascimento_Arte ON Artista; -- drop
CREATE INDEX i_DataNascimento_Arte ON Artista (ano);




-- -------------- --s
-- 5. DADOS TESTE --
-- -------------- --

INSERT INTO `artista` VALUES (1,'Nuno Fonseca Florencio','2003-10-31','Pessoal: +258 84 3698 443\nEmail: nunofonsecaflorencio@gmail.com\nInstagram: @nuno2f','Sou um artista gráfico.\n\nTrabalho com:\n- Manipulação de imagens;\n- Criação de Logotipos e Identidade Visual;\n- Desenhos vectoriais.\n\nEspero que gostes do meu trabalho artístico.','toolate.jpg','2003')
,(7,'Belarmino Junior','2003-09-17','Email: belarminosimaojunior@petalmail.com\nInstagram: @wonderr____\nWhtasApp: +258 85 637 9600','Let\'s leave it empty !!!','Bela.jpg','2003');

INSERT INTO `categoria` VALUES (1,'Conceptual',-1),(2,'Rupestre',0);

CALL publicar_arte(1, 1, 'Barretinho', 1, 500, 'barreto.jpg', 'Você bota fogo no circo?\nO que você faz com as criançãs lá dentro?\n~Barreto');

-- SELECTS SIMPLES
SELECT * FROM arte;
SELECT * FROM arte_categoria;
SELECT * FROM artista;
SELECT * FROM artista_arte;
SELECT * FROM categoria;
SELECT * FROM cliente;
SELECT * FROM compra;

-- SELECTS COM INFORMAÇÃO ADICIONAL
EXPLAIN SELECT * FROM arte;
EXPLAIN SELECT * FROM arte_categoria;
EXPLAIN SELECT * FROM artista;
EXPLAIN SELECT * FROM artista_arte;
EXPLAIN SELECT * FROM categoria;
EXPLAIN SELECT * FROM cliente;
EXPLAIN SELECT * FROM compra;
