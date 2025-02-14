package com.example.projetojavafxjdbc.model.dao.impl;

import com.example.projetojavafxjdbc.db.DB;
import com.example.projetojavafxjdbc.model.dao.ProfessorDao;
import com.example.projetojavafxjdbc.model.entities.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDaoJDBC implements ProfessorDao {
    private final Connection conn;

    public ProfessorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Professor professor) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO professor (nome, matricula,foto) VALUES (?, ?, ?)"
            );
            st.setString(1, professor.getNome());
            st.setInt(2, professor.getMatricula());
            st.setBytes(3, professor.getFoto());
            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas == 0) {
                    throw new RuntimeException("Erro inesperado! Nenhuma linha foi afetada.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir um novo professor: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Professor professor) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE professor SET nome = ?, matricula = ?, foto = ? WHERE matricula = ?"
            );
            st.setString(1, professor.getNome());
            st.setInt(2, professor.getMatricula());
            //não sei se é necessario modificar a foto.
            st.setBytes(3, professor.getFoto());
            st.setInt(2, professor.getMatricula());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar um professor: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteByMatricula(int matricula) {

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM professor WHERE matricula = ?");
            st.setInt(1, matricula);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover um professor: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }


    @Override
    public List<Professor> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM professor");
            rs = st.executeQuery();

            List<Professor> professores = new ArrayList<>();

            while (rs.next()) {
                Professor professor = new Professor();
                professor.setMatricula(rs.getInt("matricula"));
                professor.setNome(rs.getString("nome"));
                professores.add(professor);
            }

            return professores;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os produtos: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
}

