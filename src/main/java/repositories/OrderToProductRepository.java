package repositories;

import things.shopRelated.Category;
import things.shopRelated.Product;
import things.userRelated.Order;
import things.userRelated.OrderDetails;
import things.userRelated.OrderStatus;
import users.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class OrderToProductRepository {
    private final Connection connection;
    public OrderToProductRepository(Connection connection) {
        this.connection = connection;
    }

    public Integer insert(OrderDetails orderDetails) {
        String insStmt = "INSERT INTO order_to_product (order_id, product_id, quantity) " +
                "VALUES (?,?,?)";
        orderDetails.getProducts().forEach((product, quantity) ->
                {
                    try {
                        PreparedStatement ps = connection.prepareStatement(insStmt);
                        ps.setInt(1,orderDetails.getOrder().getId());
                        ps.setInt(2,product.getId());
                        ps.setInt(3,quantity);
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                );
        return orderDetails.getOrder().getId();
    }

    public OrderDetails read(Integer orderId) {
        String selectStmt = "SELECT order_to_product.*,o.*,c.*,p.*,c2.* FROM order_to_product " +
                " INNER JOIN orders o on o.order_id = order_to_product.order_id " +
                " INNER JOIN customers c on c.customer_id = o.customer_id " +
                " INNER JOIN products p on p.product_id = order_to_product.product_id " +
                " INNER JOIN categories c2 on c2.category_id = p.cat_id " +
                " WHERE order_to_product.order_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(
                    selectStmt,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1,orderId);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer delete(Integer orderId) {
        String delStmt = "DELETE FROM order_to_product WHERE order_id = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(delStmt);
            ps.setInt(1,orderId);
            ps.executeUpdate();
            return orderId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrderDetails mapTo(ResultSet rs) {
        try {
            HashMap<Product,Integer> products = new HashMap<>();
            while(rs.next()) {
                products.put(new Product(
                        rs.getInt(2),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        new Category(rs.getString("category_name"))
                ),rs.getInt("quantity"));
            }
            rs.absolute(0);
            if(rs.next()) {
                return new OrderDetails(
                        new Order(
                                rs.getInt(4),
                                rs.getDate(5),
                                new Customer(
                                        rs.getInt(7),
                                        rs.getString("first_name"),
                                        rs.getString("last_name"),
                                        rs.getString("username"),
                                        rs.getString("password"),
                                        rs.getString("email"),
                                        rs.getString("address"),
                                        rs.getDouble("balance")
                                ),
                                OrderStatus.valueOf(rs.getString("order_status"))),
                        products
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
