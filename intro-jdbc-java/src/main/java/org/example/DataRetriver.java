package org.example;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataRetriver {
    private Connection connection;

    public void setConnection(Connection connection){
        this.connection = connection;
    }
    List<Category> getAllCategories(){
        List<Category> allCategories = new ArrayList<>();

        String sql = "SELECT * FROM Product_category";

        try(Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                int id = rs.getInt("id_category");
                String name = rs.getString("name");
                Category category = new Category();
                category.setId(id);
                category.setName(name);
                allCategories.add(category);


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(allCategories);
        return allCategories;
    }

    List<Product> getProductList (int page, int size){
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;
        String sql = "SELECT * FROM Product LIMIT ? OFFSET ?";

        try(Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                int id = rs.getInt("id_product");
                String name = rs.getString("name");
                Float price = rs.getFloat("price");
                String creation = rs.getString("creation");
                products.

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
