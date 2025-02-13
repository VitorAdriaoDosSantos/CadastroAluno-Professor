package com.example.projetojavafxjdbc.model.dao;

import com.example.projetojavafxjdbc.db.DB;
import com.example.projetojavafxjdbc.model.dao.impl.AlunoDaoJDBC;
import com.example.projetojavafxjdbc.model.dao.impl.ProfessorDaoJDBC;


public interface DaoFactory {

    public static AlunoDao createAlunoDao(){
        return new AlunoDaoJDBC(DB.getConnection());
    }

    public static ProfessorDao createProfessorDao(){
        return new ProfessorDaoJDBC(DB.getConnection());
    }
}

