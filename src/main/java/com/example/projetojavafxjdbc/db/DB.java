package com.example.projetojavafxjdbc.db;


import java.sql.*;

public class DB {

    private static Connection conn=null;

    public static Connection getConnection(){
        if(conn==null){
            try {
                conn = DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/projetojava","root","vitor15458512");
            } catch (SQLException e) {
                throw new RuntimeException("Não foi possível conectar ao DB. " + e.getMessage(), e);
            }
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar a conexão com o DB. " + e.getMessage(), e);
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar o ResultSet. " + e.getMessage(), e);
            }
        }
    }
    public static void closeStatement(Statement st){
        if(st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar o Statement. " + e.getMessage(), e);
            }
        }
    }
}

