package repositories;

import things.shopRelated.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class ProductsRepository extends Repository<Product>{
    public ProductsRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Product product) {
        return null;
    }

    @Override
    public Product read(Integer id) {
        return null;
    }

    @Override
    public List<Product> readAll() {
        return null;
    }

    @Override
    public Integer update(Product product) {
        return null;
    }

    @Override
    public Integer delete(Product product) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    protected Product mapTo(ResultSet rs) {
        return null;
    }

    @Override
    protected List<Product> mapToList(ResultSet rs) {
        return null;
    }
}
