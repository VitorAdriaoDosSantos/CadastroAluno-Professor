package com.example.projetojavafxjdbc.model.dao;

import com.example.projetojavafxjdbc.db.DB;
import com.example.projetojavafxjdbc.model.dao.impl.AlunoDaoJDBC;
import com.example.projetojavafxjdbc.model.dao.impl.ProfessorDaoJDBC;


public interface DaoFactory {

     static AlunoDao createAlunoDao(){
        return new AlunoDaoJDBC(DB.getConnection());
    }

     static ProfessorDao createProfessorDao(){return new ProfessorDaoJDBC(DB.getConnection());}
}

