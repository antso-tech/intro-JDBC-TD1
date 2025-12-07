package org.example;

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

    List<Product> getProductList (int page, int size){
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;
        String sql = "SELECT * FROM " +
                "Product p LEFT JOIN Product_category c on " +
                "p.id_product = c.id_product LIMIT ? OFFSET ?";
        try(PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1,size);
            st.setInt(2,offset);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id_product");
                String name = rs.getString("name");
                Timestamp creation = rs.getTimestamp("creation");
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

    List<Product> getProductsByCriteria(String productName, String categoryName,
                                        Instant creationMin, Instant creationMax){
        List<Product> products = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Product p INNER JOIN Product_category c on p.id_product = c.id_product")
                if(productName != null && !productName.trim().isEmpty()){
                    sql.append(" WHERE name ILIKE ?");
                    parameters.add("%" + productName + "%");
                }
                if (categoryName != null && categoryName.trim().isEmpty()){
                    sql.append(" WHERE name_category ILIKE ?");
                    parameters.add("%" + categoryName + "%");
                }
                if (creationMin != null){
                    sql.append("WHERE creation >= ?");
                    parameters.add(Timestamp.from(creationMin));

                }
                if(creationMax != null){
                    sql.append("WHERE creation <= ?");
                    parameters.add(Timestamp.from(creationMax));

                }

                try (PreparedStatement ps = connection.prepareStatement(sql.toString())){
                    for (int i = 0; i < parameters.size(); i++) {
                        ps.setObject(i + 1, parameters.get(i));
                    }

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        int id = rs.getInt("id_product");
                        String name = rs.getString("name");
                        Timestamp creation = rs.getTimestamp("creation");
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
                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        return products;
    }
}
