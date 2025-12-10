import org.example.Category;
import org.example.DBConnection;
import org.example.DataRetriver;
import org.example.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionTest {
    DBConnection connection = new DBConnection();
    DataRetriver data = new DataRetriver();
    Category category1;
    Category category2;
    Category category3;
    Category category4;
    Category category5;
    Category category6;
    Category category7;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;
    Product product6;
    Product product7;

    @BeforeEach
     void prepareAllTest(){
        Connection test = connection.getConnection();
        data.setConnection(test);

        category1 = new Category();
        category1.setId(1);
        category1.setName("Informatique");

        category2 = new Category();
        category2.setId(2);
        category2.setName("Téléphone");

        category3 = new Category();
        category3.setId(3);
        category3.setName("Audio");

        category4 = new Category();
        category4.setId(4);
        category4.setName("Accessoires");

        category5 = new Category();
        category5.setId(5);
        category5.setName("Informatique");

        category6 = new Category();
        category6.setId(6);
        category6.setName("Bureau");

        category7 = new Category();
        category7.setId(7);
        category7.setName("Mobile");

        product1 = new Product();
        product1.setId(1);
        product1.setName("Laptop Dell XPS");
        product1.setCreationDateTime(Instant.parse("2025-12-05T15:48:10.923394Z"));
        product1.setCategory(category1);

        product2 = new Product();
        product2.setId(2);
        product2.setName("IPhone 13");
        product2.setCreationDateTime(Instant.parse("2025-12-05T15:52:08.330214Z"));
        product2.setCategory(category2);

        product3 = new Product();
        product3.setId(3);
        product3.setName("Casque Sony WH1000");
        product3.setCreationDateTime(Instant.parse("2025-12-05T15:52:09.876874Z"));
        product3.setCategory(category3);

        product4 = new Product();
        product4.setId(4);
        product4.setName("Clavier Logitech");
        product4.setCreationDateTime(Instant.parse("2025-12-05T15:52:15.872955Z"));
        product4.setCategory(category4);

        product5 = new Product();
        product5.setId(5);
        product5.setName("Ecran Samsung 27");
        product5.setCreationDateTime(Instant.parse("2025-12-05T15:52:17.008421Z"));
        product5.setCategory(category5);
         
        product6 = new Product();
        product6.setId(5);
        product6.setName("Ecran Samsung 27");
        product6.setCreationDateTime(Instant.parse("2025-12-05T12:52:08.330214Z"));
        product6.setCategory(category6);

        product7 = new Product();
        product7.setId(2);
        product7.setName("IPhone 13");
        product7.setCreationDateTime(Instant.parse("2025-12-05T12:52:08.330214Z"));
        product7.setCategory(category7);
    }

    @Test
    void getAllCategoriesTest(){
        List<Category> category = new ArrayList<>();
        category.add(category1);
        category.add(category2);
        category.add(category3);
        category.add(category4);
        category.add(category5);
        category.add(category6);
        category.add(category7);

        Assertions.assertEquals(category, data.getAllCategories());


    }

    @Test
    void getProductListTest (){
        List<Product> productsAnswer1 = new ArrayList<>();
        productsAnswer1.add(product1);
        productsAnswer1.add(product2);
        productsAnswer1.add(product3);
        productsAnswer1.add(product4);
        productsAnswer1.add(product5);
        Assertions.assertEquals(productsAnswer1,data.getProductList(1,5));

        List<Product> productsAnswer2 = new ArrayList<>();
        productsAnswer2.add(product1);
        productsAnswer2.add(product2);
        productsAnswer2.add(product3);
        Assertions.assertEquals(productsAnswer2,data.getProductList(1,3));

        List<Product> productsAnswer3 = new ArrayList<>();
        productsAnswer3.add(product3);
        productsAnswer3.add(product4);
        Assertions.assertEquals(productsAnswer3, data.getProductList(1,3));



    }

    void getProductsByCriteria(){
        List<Product> productsAnswer1 = new ArrayList<>();
        productsAnswer1.add(product1);
        Assertions.assertEquals(productsAnswer1,data.getProductsByCriteria("Dell",null,null,null));

        List<Product> productsAnswer2 = new ArrayList<>();
        productsAnswer2.add(product1);
        productsAnswer2.add(product5);
        Assertions.assertEquals(productsAnswer2,data.getProductsByCriteria(null, "info", null,null));

        List<Product> productsAnswer3 = new ArrayList<>();
        productsAnswer3.add(product5);
        Assertions.assertEquals(productsAnswer3,data.getProductsByCriteria("Samsung", "Mobile",null, null));

        List<Product> productsAnswer4 = new ArrayList<>();
        Assertions.assertEquals(productsAnswer4,data.getProductsByCriteria("Sony","Informatique",null,null));

        List<Product> productsAnswer5 = new ArrayList<>();
        Assertions.assertEquals(productsAnswer5,data.getProductsByCriteria(null,null,null,null));


        List<Product> productsAnswer8 = new ArrayList<>();
        Assertions.assertEquals(productsAnswer8,data.getProductsByCriteria(null,null,null,null,1,10));

        List<Product> productsAnswer9 = new ArrayList<>();
        productsAnswer9.add(product1);
        Assertions.assertEquals(productsAnswer5,data.getProductsByCriteria("Dell", null, null, null, 1,10));

        List<Product> productsAnswer10 = new ArrayList<>();
        productsAnswer10.add(product1);
        productsAnswer10.add(product5);
        Assertions.assertEquals(productsAnswer10,data.getProductsByCriteria(null,"Informatique",null,null,1,10));
    }


}
