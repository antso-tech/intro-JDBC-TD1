package org.example;

import java.sql.*;

public class DBConnection {
    String url = "jdbc:postgresql://localhost:5432/product_management_db";
    String user = "product_manager_user";
    String password = "123456";

    public void getConnection(){
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user, password);
            System.out.println("Connection à la base de données réussi");

        }catch (SQLException e){
            System.out.println("Erreur lors de la connection à la base de données");
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        DBConnection firstConnection = new DBConnection();

    }
}