package repositories;

import things.shopRelated.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class CategoryRepository extends Repository<Category>{
    public CategoryRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Category category) {
        return null;
    }

    @Override
    public Category read(Integer id) {
        return null;
    }

    @Override
    public List<Category> readAll() {
        return null;
    }

    @Override
    public Integer update(Category category) {
        return null;
    }

    @Override
    public Integer delete(Category category) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    protected Category mapTo(ResultSet rs) {
        return null;
    }

    @Override
    protected List<Category> mapToList(ResultSet rs) {
        return null;
    }
}
