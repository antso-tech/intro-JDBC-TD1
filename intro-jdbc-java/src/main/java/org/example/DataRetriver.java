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
                "p.id_product = c.id_product LIMIT ? OFFSET ?";
        try(PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1,size);
            st.setInt(2,offset);
            ResultSet rs = st.executeQuery();

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
        List<Object> parameters = new ArrayList<>();
        boolean added = false;
        StringBuilder sql = new StringBuilder("SELECT * FROM Product p INNER JOIN Product_category " +
                "c on p.id_product = c.id_product");
                if(productName != null ){
                    sql.append(added ? " AND " : " WHERE ");
                    sql.append(" name ILIKE ?");
                    parameters.add("%" + productName + "%");
                    added = true;
                }
                if (categoryName != null){
                    sql.append(added ? " AND " : " WHERE ");
                    sql.append(" name_category ILIKE ?");
                    parameters.add("%" + categoryName + "%");
                    added= true;
                }
                if (creationMin != null){
                    sql.append(added ? " AND " : " WHERE ");
                    sql.append(" creation_date_time >= ?");
                    parameters.add(Timestamp.from(creationMin));
                    added=true;

                }
                if(creationMax != null){
                    sql.append(added ? " AND " : " WHERE ");
                    sql.append(" creation_date_time <= ?");
                    System.out.println("instant creationMax : " + creationMax);
                    System.out.println("creationMax timestamp : " + Timestamp.from(creationMax));
                    parameters.add(Timestamp.from(creationMax));
                    added=true;

                }

        System.out.println("SQL généré: " + sql.toString());
                try (PreparedStatement ps = connection.prepareStatement(sql.toString())){
                    for (int i = 0; i < parameters.size(); i++) {
                        ps.setObject(i + 1, parameters.get(i));
                    }

                    ResultSet rs = ps.executeQuery();
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
            sql.append(" name ILIKE ? ");
            parameters.add("%" + productName + "%");
            added = true;

        }
        if(categoryName != null && !categoryName.isEmpty()){
            sql.append(added ? " AND " : " WHERE ");
            sql.append(" name_category ILIKE ? ");
            parameters.add("%" + categoryName + "%");
            added = true;
        }
        if(creationMin != null){
            sql.append(added ? " AND " : " WHERE ");
            sql.append("creation_date_time >= ?");
            parameters.add(Timestamp.from(creationMin));
            added = true;

        }
        if(creationMax != null){
            sql.append(added ? " AND " : " WHERE ");
            sql.append("creation_date_time <= ?");
            parameters.add(Timestamp.from(creationMax));
            added = true;
        }

        sql.append(" LIMIT ? OFFSET ?");
        try {
            PreparedStatement st = connection.prepareStatement(String.valueOf(sql));
            int parametreIndex = 1;
            for (Object param : parameters) {
                st.setObject(parametreIndex++, param);

            }
            st.setInt(parametreIndex++,size);
            st.setInt(parametreIndex,offset);
            ResultSet rs = st.executeQuery();
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
        return products;
    }
}
