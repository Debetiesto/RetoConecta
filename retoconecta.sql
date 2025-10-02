
CREATE DATABASE RetoConecta;
USE RetoConecta;

CREATE TABLE Enunciado (
    IdE INT PRIMARY KEY AUTO_INCREMENT,
    Descripcion VARCHAR(5000),
    Nivel ENUM('Alta', 'Media', 'Baja'),
    Disponible BOOLEAN,
    Ruta VARCHAR(250)
);



CREATE TABLE UnidadDidactica (
    IdU INT PRIMARY KEY AUTO_INCREMENT,
    Acronimo VARCHAR(50),
    Titulo VARCHAR(50),
    Evaluacion VARCHAR(50),
    Descripcion VARCHAR(500)
);

CREATE TABLE Tiene (
    IdE INT,
    IdU INT,
    PRIMARY KEY (IdE, IdU),
    FOREIGN KEY (IdE) REFERENCES Enunciado(IdE) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (IdU) REFERENCES UnidadDidactica(IdU) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO Enunciado (Descripcion, Nivel, Disponible, Ruta) VALUES
('Resuelve el sistema de ecuaciones lineales usando el método de Gauss.', 'Media', TRUE, 'docs/enunciado1.gif'),
('Analiza el movimiento parabólico de un proyectil.', 'Alta', TRUE, 'docs/enunciado2.pdf'),
('Desarrolla una función recursiva en Python para calcular factoriales.', 'Media', FALSE, 'docs/enunciado3.jpg');

INSERT INTO UnidadDidactica (Acronimo, Titulo, Evaluacion, Descripcion) VALUES
('ALG', 'Álgebra Lineal', 'Formativa', 'Estudio de matrices, vectores y sistemas de ecuaciones.'),
('CIN', 'Cinemática', 'Sumativa', 'Análisis del movimiento en una y dos dimensiones.'),
('PYF', 'Python Funcional', 'Formativa', 'Introducción a funciones, recursividad y programación funcional en Python.');


INSERT INTO Tiene (IdE, IdU) VALUES
(1, 1), 
(2, 2), 
(3, 3); 

