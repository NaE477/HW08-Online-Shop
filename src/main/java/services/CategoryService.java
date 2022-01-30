package services;

import things.shopRelated.Category;

import java.sql.Connection;
import java.util.List;

public class CategoryService extends Service<Category> {
    public CategoryService(Connection connection) {
        super(connection);
    }

    @Override
    public Category find(Integer id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public Integer update(Category category) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
