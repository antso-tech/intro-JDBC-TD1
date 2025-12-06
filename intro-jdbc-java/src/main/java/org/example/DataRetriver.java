package org.example;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DataRetriver {
    List<Category> getAllCategories(){
        List<Category> allCategories = new ArrayList<>();
        String sql = "SELECT * From Product_category";

        try(Connection connection = DBConnection.g)

    }
}
