package org.example;

import java.sql.*;
import java.time.Instant;

public class DBConnection {
    private Connection connection;

    public Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/product_management_db";
            String user = "product_manager_user";
            String password = "123456";
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection à la base de données réussi");

        }catch (SQLException e){
            System.out.println("Erreur lors de la connection à la base de données");
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }




}