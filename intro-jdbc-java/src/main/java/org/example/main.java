package org.example;

import java.time.Instant;

public class main {
    public static void main(String[] args) {
        DBConnection firstConnection = new DBConnection();
        firstConnection.getConnection();
        DataRetriver data = new DataRetriver();
        data.setConnection(firstConnection.getConnection());
        data.getAllCategories();

        System.out.println("------------------------------------");
        data.getProductList(1,10);
        data.getProductList(1, 5);
        data.getProductList(2,2);

        System.out.println("------------------------------------");
        data.getProductsByCriteria("Dell",null,null,null);
        data.getProductsByCriteria(null, "info", null,null);
        data.getProductsByCriteria("Samsung", "Mobile",null, null);
        data.getProductsByCriteria("Sony","Informatique",null,null);
        data.getProductsByCriteria(null, null, Instant.parse("2025-12-05T15:52:08Z"),Instant.parse("2025-12-05T15:52:17Z"));
        data.getProductsByCriteria(null, null, Instant.parse("2025-12-05T15:52:08Z"),Instant.parse("2025-12-05T15:52:17Z"));
        data.getProductsByCriteria("Samsung", "Bureau",null, null);
        data.getProductsByCriteria("Sony","Informatique",null,null);
        data.getProductsByCriteria(null,null,null,null);

        System.out.println("------------------------------------");
        data.getProductsByCriteria(null,null,null,null,1,10);
        data.getProductsByCriteria("Dell", null, null, null, 1,10);
        data.getProductsByCriteria(null,"Informatique",null,null,1,10);
    }
}
