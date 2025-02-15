create database IF NOT EXISTS projetojava;
use projetojava;

create table aluno(
matricula int primary key auto_increment,
nome varchar (100),
cpf varchar (14),
dataNascimento date,
foto longblob);

create table professor(
matricula int,
nome varchar(100),
foto longblob);

INSERT INTO aluno (nome, cpf, dataNascimento, foto) VALUES
('João Silva', '123.456.789-00', '2000-05-15', NULL),
('Maria Oliveira', '987.654.321-00', '1998-09-22', NULL),
('Carlos Santos', '456.789.123-00', '2001-12-10', NULL),
('Ana Souza', '321.654.987-00', '1999-07-08', NULL),
('Fernanda Lima', '741.852.963-00', '2002-03-25', NULL);

INSERT INTO professor (matricula, nome, foto) VALUES
(101, 'Carlos Silva', NULL),
(102, 'Ana Souza', NULL),
(103, 'Marcos Pereira', NULL),
(104, 'Fernanda Lima', NULL),
(105, 'Ricardo Almeida', NULL);


