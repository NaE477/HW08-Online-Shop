package repositories;

import things.shopRelated.Category;
import things.shopRelated.Product;
import things.userRelated.ShoppingCart;
import users.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartsRepository extends Repository<ShoppingCart>{

    public ShoppingCartsRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(ShoppingCart shoppingCart) {
        String insStmt = "INSERT INTO shopping_carts (customer_id) VALUES (?) RETURNING cart_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setInt(1,shoppingCart.getCustomer().getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ShoppingCart read(Integer id) {
        String readStmt = "SELECT * FROM cart_to_products " +
                " INNER JOIN products p on p.product_id = cart_to_products.product_id " +
                " INNER JOIN shopping_carts sc on sc.cart_id = cart_to_products.cart_id " +
                " INNER JOIN customers c on c.customer_id = sc.customer_id " +
                " INNER JOIN categories c on c.category_id = p.cat_id " +
                " INNER JOIN shopping_carts s on c.customer_id = s.customer_id " +
                " WHERE sc.cart_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1,id);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ShoppingCart readByCustomer(Customer customer){
        String readStmt = "SELECT * FROM cart_to_products " +
                " INNER JOIN products p on p.product_id = cart_to_products.product_id " +
                " INNER JOIN shopping_carts sc on sc.cart_id = cart_to_products.cart_id " +
                " INNER JOIN customers c on c.customer_id = sc.customer_id " +
                " INNER JOIN categories c2 on c2.category_id = p.cat_id " +
                " INNER JOIN shopping_carts s on c.customer_id = s.customer_id " +
                " WHERE c.customer_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt( 1,customer.getId());
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ShoppingCart> readAll() {
        return null;
    }

    @Override
    public Integer update(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public Integer delete(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }


    @Override
    protected ShoppingCart mapTo(ResultSet rs) {
        Map<Product,Integer> products = new HashMap<>();
        try {
            while (rs.next()){
                products.put(
                        new Product(
                                rs.getInt(4),
                                rs.getString(5),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                new Category(rs.getString("category_name")
                                )
                        ),
                        rs.getInt(3)
                );
            }
            rs.absolute(0);
            if(rs.next()) {
                return new ShoppingCart(
                        rs.getInt(1),
                        new Customer(
                                rs.getInt(11),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("address"),
                                rs.getDouble("balance")
                        ),
                        products
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<ShoppingCart> mapToList(ResultSet rs) {
        return null;
    }

}
