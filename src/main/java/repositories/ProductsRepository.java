package repositories;

import things.shopRelated.Category;
import things.shopRelated.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductsRepository extends Repository<Product> {
    public ProductsRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Product product) {
        return null;
    }
    public Integer insert(Product product,Integer quantity) {
        String insStmt = "INSERT INTO products (product_name, description, price, cat_id,quantity) " +
                "VALUES (?,?,?,?,?) RETURNING product_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getCategory().getId());
            ps.setInt(5,quantity);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("product_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product read(Integer id) {
        String slcStmt = "SELECT * FROM products " +
                "INNER JOIN categories c on c.category_id = products.cat_id " +
                " WHERE product_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setInt(1, id);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> readAll() {
        String slcStmt = "SELECT * FROM products" +
                " INNER JOIN categories c on c.category_id = products.cat_id";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<Product,Integer> readAllWithQuantity(){
        HashMap<Product,Integer> products = new HashMap<>();
        String slcStmt = "SELECT * FROM products" +
                " INNER JOIN categories c on c.category_id = products.cat_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                products.put(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("product_name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                new Category(
                                        rs.getInt("cat_id"),
                                        rs.getString("category_name"))
                        )
                        ,rs.getInt("quantity"));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public HashMap<Product,Integer> readAllByCategoryID(Integer categoryID){
        HashMap<Product,Integer> products = new HashMap<>();
        String selectStmt = "SELECT * FROM products " +
                "INNER JOIN categories c on c.category_id = products.cat_id " +
                "WHERE cat_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(selectStmt);
            ps.setInt(1,categoryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                products.put(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("product_name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                new Category(
                                        rs.getInt("cat_id"),
                                        rs.getString("category_name"))
                        )
                        ,rs.getInt("quantity"));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public HashMap<Product,Integer> readProductAndQuantity(Product product){
        HashMap<Product,Integer> productAndQuantity = new HashMap<>();
        String readStmt = "SELECT * FROM products " +
                "               INNER JOIN categories c on c.category_id = products.cat_id" +
                "          WHERE product_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt  );
            ps.setInt(1,product.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                productAndQuantity.put(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("product_name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                new Category(
                                        rs.getInt("cat_id"),
                                        rs.getString("category_name"))
                        ),rs.getInt("quantity")
                );
                return productAndQuantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Product product) {
        String updateStmt = "UPDATE products SET product_name = ?,description = ?,cat_id = ?,price = ? WHERE cat_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(updateStmt);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getCategory().getCatName());
            ps.setDouble(4, product.getPrice());
            ps.executeUpdate();
            return product.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Integer update(Product product,Integer quantity){
        String updateStmt = "UPDATE products SET quantity = ? WHERE product_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(updateStmt);
            ps.setInt(1,quantity);
            ps.setInt(2,product.getId());
            ps.executeUpdate();
            return product.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer delete(Product product) {
        String delStmt = "DELETE FROM products WHERE product_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1, product.getId());
            ps.executeUpdate();
            return product.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        String delStmt = "DELETE FROM products WHERE product_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1, id);
            ps.executeUpdate();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Product mapTo(ResultSet rs) {
        try {
            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        new Category(
                                rs.getInt("category_id"),
                                rs.getString("category_name"),
                                null
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<Product> mapToList(ResultSet rs) {
        List<Product> products = new ArrayList<>();
        try {
            while (rs.next()) {
                products.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("product_name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                new Category(
                                        rs.getInt("category_id"),
                                        rs.getString("category_name"),
                                        null
                                )
                        )
                );
            }
            return products;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
