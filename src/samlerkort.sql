/*DROP DATABASE IF EXISTS Kort;
CREATE DATABASE Kort;*/

USE Kort;

DROP TABLE IF EXISTS Fotballkort;
DROP TABLE IF EXISTS Basketballkort;
DROP TABLE IF EXISTS Baseballkort;
DROP TABLE IF EXISTS SamlerkortSerie;

CREATE TABLE SamlerkortSerie (
    id INT PRIMARY KEY AUTO_INCREMENT,
    Utgiver VARCHAR(100) NOT NULL,
    Utgitt INT,
    Sport VARCHAR(100),
    Antall INT
);

CREATE TABLE Fotballkort (
    id INT PRIMARY KEY AUTO_INCREMENT,
    Serie INT,
    Tilstand VARCHAR(100),
    Spillernavn VARCHAR(100),
    Klubb VARCHAR(100),
    Sesonger INT,
    Kamper INT,
    Seriescoringer INT,
    Cupscoringer INT,
    FOREIGN KEY (Serie) REFERENCES SamlerkortSerie(id)
);

CREATE TABLE Basketballkort (
    id INT PRIMARY KEY AUTO_INCREMENT,
    Serie INT,
    Tilstand VARCHAR(100),
    Spillernavn VARCHAR(100),
    Klubb VARCHAR(100),
    Sesonger INT,
    Kamper INT,
    FGPercent INT,
    FTPercent INT,
    Poengsnitt DOUBLE,
    FOREIGN KEY (Serie) REFERENCES SamlerkortSerie(id)
);

CREATE TABLE Baseballkort (
    id INT PRIMARY KEY AUTO_INCREMENT,
    Serie INT,
    Tilstand VARCHAR(100),
    Spillernavn VARCHAR(100),
    Klubb VARCHAR(100),
    Sesonger INT,
    Kamper INT,
    Homeruns INT,
    FOREIGN KEY (Serie) REFERENCES SamlerkortSerie(id)
);