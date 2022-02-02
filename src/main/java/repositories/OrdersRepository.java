package repositories;

import things.userRelated.Order;
import things.userRelated.OrderStatus;
import users.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepository extends Repository<Order> {
    public OrdersRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Order order) {
        String insStmt = "INSERT INTO orders (order_date, order_status, customer_id) " +
                "VALUES (CURRENT_DATE,'PENDING',?) RETURNING order_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setInt(1,order.getCustomer().getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("order_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order read(Integer id) {
        String slcStmt = "SELECT orders.*,c.* FROM orders " +
                "INNER JOIN customers c on c.customer_id = orders.customer_id " +
                "WHERE order_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcStmt);
            ps.setInt(1,id);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> readAll() {
        String slcAllStmt = "SELECT * FROM orders;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcAllStmt);
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }
    public List<Order> readAllByCustomer(Customer customer){
        String slcAllStmt = "SELECT * FROM orders " +
                "INNER JOIN customers c on c.customer_id = orders.customer_id " +
                "WHERE orders.customer_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcAllStmt);
            ps.setInt(1,customer.getId());
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Order> readAllPendingsByCustomer(Customer customer){
        String slcAllStmt = "SELECT * FROM orders" +
                " INNER JOIN customers c on c.customer_id = orders.customer_id " +
                " WHERE orders.customer_id = ? AND order_status = 'PENDING';";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(slcAllStmt);
            ps.setInt(1,customer.getId());
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Order order) {
        String updateStmt = "UPDATE orders SET order_status = ? WHERE order_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(updateStmt);
            ps.setString(1,order.getStatus().toString());
            ps.setInt(2,order.getId());
            ps.executeUpdate();
            return order.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer delete(Order order) {
        String delStmt = "DELETE FROM orders WHERE order_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1,order.getId());
            ps.executeUpdate();
            return order.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    public Integer delete(Integer id) {
        String delStmt = "DELETE FROM orders WHERE order_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    protected Order mapTo(ResultSet rs) {
        try {
            if(rs.next()) {
                return new Order(
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        new Customer(
                                rs.getInt(4),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("address"),
                                rs.getDouble("balance")),
                        OrderStatus.valueOf(rs.getString("order_status"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<Order> mapToList(ResultSet rs) {
        List<Order> orders = new ArrayList<>();
        try {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        new Customer(
                                rs.getInt("customer_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("address"),
                                rs.getDouble("balance")),
                        OrderStatus.valueOf(rs.getString("order_status"))
                ));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
