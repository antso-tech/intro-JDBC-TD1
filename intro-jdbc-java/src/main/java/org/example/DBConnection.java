package org.example;

import java.sql.*;

public class DBConnection {
    String url = "jdbc:postgresql://localhost:5432/product_management_db";
    String user = "product_manager_user";
    String password = "123456";
    String query = "SELECT * FROM Product";

    public void getConnection(){
        try(Connection connection = DriverManager.getConnection(url,user, password)) {
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);

            while(rs.next()){
                System.out.println(rs.getInt("id_product") + " and " + rs.getString("name"));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        DBConnection firstConnection = new DBConnection();
        firstConnection.getConnection();

    }
}