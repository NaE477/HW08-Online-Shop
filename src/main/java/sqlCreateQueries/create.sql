CREATE TABLE IF NOT EXISTS categories
(
    category_id       SERIAL PRIMARY KEY,
    category_name     VARCHAR(50),
    super_category_id INTEGER DEFAULT NULL,

    FOREIGN KEY (super_category_id) REFERENCES categories (category_id)
);
CREATE TABLE IF NOT EXISTS products
(
    product_id   SERIAL PRIMARY KEY,
    product_name VARCHAR(50),
    description  TEXT,
    price        DOUBLE PRECISION,
    cat_id       INTEGER,

    FOREIGN KEY (cat_id) REFERENCES categories (category_id)
);
CREATE TABLE IF NOT EXISTS customers
(
    customer_id SERIAL PRIMARY KEY,
    first_name  VARCHAR(50),
    last_name   VARCHAR(50),
    username    VARCHAR(50) UNIQUE,
    password    VARCHAR(50),
    email       VARCHAR(50),
    address     VARCHAR(500),
    balance     DOUBLE PRECISION
);
CREATE TABLE IF NOT EXISTS managers
(
    manager_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    username   VARCHAR(50) UNIQUE,
    password   VARCHAR(50),
    email      VARCHAR(50),
    salary     DOUBLE PRECISION
);
CREATE TABLE IF NOT EXISTS shopping_carts
(
    cart_id     SERIAL PRIMARY KEY,
    customer_id INTEGER,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);
CREATE TABLE IF NOT EXISTS cart_to_products
(
    cart_id    INTEGER,
    product_id INTEGER,
    quantity   INTEGER,
    FOREIGN KEY (cart_id) REFERENCES shopping_carts (cart_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id)
);
CREATE TABLE IF NOT EXISTS orders
(
    order_id     SERIAL PRIMARY KEY,
    order_date   DATE,
    order_status VARCHAR,
    customer_id  INTEGER,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
CREATE TABLE IF NOT EXISTS order_to_product
(
    order_id        INTEGER,
    product_id      INTEGER,
    quantity        INTEGER,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);