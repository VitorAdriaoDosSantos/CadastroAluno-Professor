package com.example.projetojavafxjdbc.model.dao;

import com.example.projetojavafxjdbc.model.entities.Aluno;

import java.util.List;

public interface AlunoDao {
    void inserir (Aluno aluno);
    void atualizar (Aluno aluno);
    void deletarPorMatricula(int matricula);
    Aluno procurarPorMatricula(int matricula);
    List<Aluno> procurarTodos( );

}

