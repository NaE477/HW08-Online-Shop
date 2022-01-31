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
    public Integer addCommonCategory(Category newCategory){
        return cr.insert(newCategory);
    }
    public Integer addSuperCategory(Category category){
        return cr.insertSuper(category);
    }

    @Override
    public Category find(Integer id) {
        return cr.read(id);
    }
    public Category find(Category category){
        return cr.read(category);
    }
    public Category find(String categoryName){
        return cr.read(categoryName);
    }

    @Override
    public List<Category> findAll() {
        return cr.readAll();
    }
    public List<Category> findAllRootCategories(){
        return cr.readAllRootCategories();
    }
    public List<Category> findAllBySuper(Category superCategory){
        return cr.readAllBySuper(superCategory);
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
