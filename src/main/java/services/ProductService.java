package services;

import things.shopRelated.Product;

import java.sql.Connection;
import java.util.List;

public class ProductService extends Service<Product>{
    public ProductService(Connection connection) {
        super(connection);
    }

    @Override
    public Product find(Integer id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Integer update(Product product) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
