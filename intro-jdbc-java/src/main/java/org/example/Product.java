package org.example;

import java.time.Instant;

public class Product {
    private int id;
    private String name;
    private Instant creationDateTime;
    private Category category;

    public Category getCategory() {
        return category;
    }
}
