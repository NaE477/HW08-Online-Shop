package services;

import repositories.ProductsRepository;
import things.shopRelated.Product;

import java.sql.Connection;
import java.util.List;

public class ProductService extends Service<Product>{
    ProductsRepository pr;
    public ProductService(Connection connection) {
        super(connection);
        pr = new ProductsRepository(super.getConnection());
    }

    @Override
    public Product find(Integer productId) {
        return pr.read(productId);
    }

    @Override
    public List<Product> findAll() {
        return pr.readAll();
    }

    @Override
    public Integer update(Product product) {
        return pr.update(product);
    }

    @Override
    public Integer delete(Integer productId) {
        return pr.delete(productId);
    }
}
