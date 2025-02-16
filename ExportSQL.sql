CREATE DATABASE IF NOT EXISTS projetojava;
USE projetojava;

CREATE TABLE aluno (
    matricula INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    cpf VARCHAR(14),
    dataNascimento DATE,
    foto LONGBLOB
);

CREATE TABLE professor (
    matricula,
    nome VARCHAR(100),
    foto LONGBLOB
);

INSERT INTO aluno (nome, cpf, dataNascimento, foto) VALUES
('Lucas Mendes', '321.654.987-11', '2001-06-18', NULL),
('Amanda Rocha', '852.963.741-22', '1997-11-30', NULL),
('Gabriel Souza', '963.852.741-33', '2003-02-14', NULL),
('Juliana Costa', '741.369.258-44', '2000-08-27', NULL),
('Felipe Martins', '258.147.369-55', '1999-05-10', NULL);

INSERT INTO professor (matricula, nome, foto) VALUES
(201, 'Roberto Nunes', NULL),
(202, 'Beatriz Fernandes', NULL),
(203, 'Eduardo Campos', NULL),
(204, 'Tatiane Oliveira', NULL),
(205, 'Vinícius Cardoso', NULL);
