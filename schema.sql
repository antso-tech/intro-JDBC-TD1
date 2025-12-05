CREATE TABLE Product(
    id_product serial primary key NOT NULL,
    name VARCHAR(50) NOT NULL,
    price DECIMAL NOT NULL,
    creation TIMESTAMP DEFAULT NOW()

);

CREATE TABLE Product_category(
    id_category serial PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    id_product int NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (id_product) REFERENCES Product(id_product)

);