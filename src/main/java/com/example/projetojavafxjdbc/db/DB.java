package com.example.projetojavafxjdbc.db;


import java.sql.*;

public class DB {

    private static Connection conn=null;
    private static final String url = "jdbc:mysql://localhost:3306/projetojava";
    private static final String user = "root";
    private static final String password = "vitor15458512";

    public static Connection getConnection(){
        if(conn==null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException("Não foi possível conectar ao DB. " + e.getMessage(), e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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

