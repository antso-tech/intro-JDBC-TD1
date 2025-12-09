package org.example;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriver {
    private Connection connection;

    public void setConnection(Connection connection){
        this.connection = connection;
    }
    public List<Category> getAllCategories(){
        List<Category> allCategories = new ArrayList<>();

        String sql = "SELECT * FROM Product_category";

        try(Statement st = connection.createStatement()){
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                int id = rs.getInt("id_category");
                String name = rs.getString("name_category");
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

    public List<Product> getProductList (int page, int size){
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;
        String sql = "SELECT * FROM " +
                "product p LEFT JOIN product_category c on " +
                "p.id_product = c.id_product LIMIT %d OFFSET %d".formatted(size,offset);
        try(Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                int id = rs.getInt("id_product");
                String name = rs.getString("name");
                Timestamp creation = rs.getTimestamp("creation_date_time");
                int idCategory = rs.getInt("id_category");
                String categoryName = rs.getString("name_category");

                Product product = new Product();
                Category productCategory = new Category();

                product.setId(id);
                product.setName(name);
                product.setCreationDateTime(creation.toInstant());
                productCategory.setId(idCategory);
                productCategory.setName(categoryName);
                product.setCategory(productCategory);

                products.add(product);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(products);

        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName,
                                      Instant creationMin, Instant creationMax){
        List<Product> products = new ArrayList<>();
        boolean added = false;
        StringBuilder sql = new StringBuilder("SELECT * FROM Product p INNER JOIN Product_category " +
                "c on p.id_product = c.id_product");
                if(productName != null ){
                    sql.append(added ? " AND " : " WHERE ");
                    sql.append(" name ILIKE '%").append(productName).append("%'");
                    added = true;
                }
                if (categoryName != null){
                    sql.append(added ? " AND " : " WHERE ");
                    sql.append(" name_category ILIKE '%").append(categoryName).append("%'");
                    added= true;
                }
                if (creationMin != null){
                    sql.append(added ? " AND " : " WHERE ");
                    sql.append(" creation_date_time >= '%").append(creationMin).append("%'");
                    System.out.println("Parameters : " + creationMin);
                    System.out.println("result : " + creationMin);
                    added=true;

                }
                if(creationMax != null){
                    sql.append(added ? " AND " : " WHERE");
                    sql.append(" creation_date_time <= '%").append(creationMax).append("%'");
                    added=true;

                }
        System.out.println(sql.toString());
                try (Statement ps = connection.createStatement()){
                    ResultSet rs = ps.executeQuery(String.valueOf(sql));

                    while (rs.next()){
                        int id = rs.getInt("id_product");
                        String name = rs.getString("name");
                        Timestamp creation = rs.getTimestamp("creation_date_time");
                        int idCategory = rs.getInt("id_category");
                        String nameCategory = rs.getString("name_category");

                        Product product = new Product();
                        Category productCategory = new Category();

                        product.setId(id);
                        product.setName(name);
                        product.setCreationDateTime(creation.toInstant());
                        productCategory.setId(idCategory);
                        productCategory.setName(nameCategory);
                        product.setCategory(productCategory);
                        products.add(product);

                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        if (!added){
            return new ArrayList<>();
        }
        System.out.println(products);
        return products;
    }

    public List<Product>
    getProductsByCriteria(String productName, String
            categoryName, Instant creationMin, Instant creationMax, int page, int size){
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Product p INNER JOIN Product_category " +
                "c on p.id_product = c.id_product");
        List<Object> parameters = new ArrayList<>();
        int offset = (page - 1) * size;

        boolean added = false;
        if(productName != null && !productName.isEmpty()){
            sql.append(added ? " AND " : " WHERE ");
            sql.append(" name ILIKE '%").append(productName).append("%'");
            added = true;

        }
        if(categoryName != null && !categoryName.isEmpty()){
            sql.append(added ? " AND " : " WHERE ");
            sql.append(" name_category ILIKE '%").append(categoryName).append("%'");
            added = true;
        }
        if(creationMin != null){
            sql.append(added ? " AND " : " WHERE ");
            sql.append("creation_date_time >= '%").append(creationMin).append("%'");
            added = true;

        }
        if(creationMax != null){
            sql.append(added ? " AND " : " WHERE ");
            sql.append("creation_date_time <= '%").append(creationMax).append("%'");
            added = true;
        }

        sql.append(" LIMIT %d OFFSET %d".formatted(size,offset));
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql.toString());
            while (rs.next()){
                int id = rs.getInt("id_product");
                String name = rs.getString("name");
                Timestamp creation = rs.getTimestamp("creation_date_time");
                int idCategory = rs.getInt("id_category");
                String nameCategory = rs.getString("name_category");

                Product product = new Product();
                Category category = new Category();
                product.setId(id);
                product.setName(name);
                product.setCreationDateTime(creation.toInstant());
                category.setId(idCategory);
                category.setName(nameCategory);
                product.setCategory(category);

                products.add(product);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!added){
            return new ArrayList<>();
        }
        System.out.println(products);
        return products;
    }
}
