package com.example.projetojavafxjdbc.model.dao;

import com.example.projetojavafxjdbc.model.entities.Professor;

import java.util.List;
public interface ProfessorDao {
    void insert(Professor professor);
    void update(Professor professor,int matriculaAntiga);
    void deleteByMatricula(int matricula);
    List<Professor> findAll();
}
