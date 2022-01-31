package repositories;

import things.shopRelated.Category;
import things.shopRelated.Product;
import things.userRelated.ShoppingCart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartToProductRepository {
    private final Connection connection;

    public ShoppingCartToProductRepository(Connection connection) {
        this.connection = connection;
    }

    public void insert(ShoppingCart shoppingCart,Product product,Integer quantity) {
        String insStmt = "INSERT INTO cart_to_products (cart_id, product_id, quantity) VALUES (?,?,?);";
        try {
            PreparedStatement ps = connection.prepareStatement(insStmt);
            ps.setInt(1,shoppingCart.getId());
            ps.setInt(2,product.getId());
            ps.setInt(3,quantity);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Product,Integer> readAllByCart(ShoppingCart cart) {
        Map<Product,Integer> products = new HashMap<>();
        String slcStmt = "SELECT * FROM cart_to_products" +
                " INNER JOIN products p on p.product_id = cart_to_products.product_id " +
                " INNER JOIN categories c on c.category_id = p.cat_id" +
                " WHERE cart_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(slcStmt);
            ps.setInt(1,cart.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                products.put(new Product(
                    rs.getInt(2),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        new Category(rs.getInt("category_id"),rs.getString("category_name"))
                ),rs.getInt("quantity"));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(ShoppingCart shoppingCart,Product product,Integer quantity) {
        String updateStmt = "UPDATE cart_to_products SET quantity = ? WHERE cart_id = ? AND product_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(updateStmt);
            ps.setInt(1,quantity);
            ps.setInt(2,shoppingCart.getId());
            ps.setInt(3,product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(ShoppingCart shoppingCart,Product product) {
        String delStmt = "DELETE FROM cart_to_products WHERE cart_id = ? AND product_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1,shoppingCart.getId());
            ps.setInt(2,product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
