package repositories;

import things.shopRelated.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository extends Repository<Category> {
    public CategoryRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Category category) {
        try {
            if (category.getSuperCategory() == null) {
                String insStmt = "INSERT INTO categories (category_name) VALUES (?) RETURNING category_id;";
                PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
                ps.setString(1,category.getCatName());
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    return rs.getInt("category_id");
                }
            }
            else {
                String insStmt = "INSERT INTO categories (category_name, super_category_id) VALUES (?,?) RETURNING category_id";
                PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
                ps.setString(1,category.getCatName());
                ps.setInt(2,category.getSuperCategory().getId());
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    return rs.getInt("category_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category read(Integer id) {
        String selectStmt = "SELECT * FROM categories WHERE category_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(selectStmt);
            ps.setInt(1, id);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> readAll() {
        String selectStmt = "SELECT * FROM categories;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(selectStmt);
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Category category) {
        String updStmt = "UPDATE categories SET category_name = ? WHERE category_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(updStmt);
            ps.setString(1, category.getCatName());
            ps.setInt(2, category.getId());
            return category.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try {
            if (rs.getInt(3) == 0) {
                return new Category(
                        rs.getInt(1),
                        rs.getString(2),
                        null)
                        ;
            } else {
                return new Category(
                        rs.getInt(1),
                        rs.getString(2),
                        read(rs.getInt(3))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<Category> mapToList(ResultSet rs) {
        List<Category> categories = new ArrayList<>();
        try {
            while (rs.next()) {
                if (rs.getInt(3) == 0) {
                    categories.add(new Category(
                            rs.getInt(1),
                            rs.getString(2),
                            null
                    ));
                } else {
                    categories.add(
                            new Category(
                                    rs.getInt(1),
                                    rs.getString(2),
                                    read(rs.getInt(3))
                            )
                    );
                }
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
