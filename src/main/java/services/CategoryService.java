package services;

import repositories.CategoryRepository;
import things.shopRelated.Category;

import java.sql.Connection;
import java.util.List;

public class CategoryService extends Service<Category> {
    CategoryRepository cr;
    public CategoryService(Connection connection) {
        super(connection);
        cr = new CategoryRepository(super.getConnection());
    }

    @Override
    public Category find(Integer id) {
        return cr.read(id);
    }

    @Override
    public List<Category> findAll() {
        return cr.readAll();
    }

    @Override
    public Integer update(Category category) {
        return cr.update(category);
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
