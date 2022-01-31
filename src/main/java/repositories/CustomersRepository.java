package repositories;

import users.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersRepository extends Repository<Customer>{
    public CustomersRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Customer customer) {
        String insStmt = "INSERT INTO customers (first_name, last_name, username, password, email, address, balance) " +
                "VALUES (?,?,?,?,?,?,?) RETURNING customer_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getUsername());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getEmailAddress());
            ps.setString(6, customer.getAddress());
            ps.setDouble(7,customer.getBalance());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("customer_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer read(Integer id) {
        String readStmt = "SELECT * FROM customers WHERE customer_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt);
            ps.setInt(1,id);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Customer read(String username){
        String readStmt = "SELECT * FROM customers WHERE username = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt);
            ps.setString(1,username);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> readAll() {
        String readAllStmt = "SELECT * FROM customers";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readAllStmt);
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Customer customer) {
        String updateStmt = "UPDATE customers" +
                " SET password = ?," +
                "email = ?," +
                "address = ?," +
                "balance = ? " +
                "WHERE username = ? " +
                "RETURNING customer_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(updateStmt);
            ps.setString(1,customer.getPassword());
            ps.setString(2,customer.getEmailAddress());
            ps.setString(3,customer.getAddress());
            ps.setDouble(4,customer.getBalance());
            ps.setString(5, customer.getUsername());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("customer_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer delete(Customer customer) {
        try{
            String delStmt = "DELETE FROM customers WHERE customer_id = ? OR username = ? RETURNING customer_id;";
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1,customer.getId());
            ps.setString(2,customer.getUsername());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("customer_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        try{
        String delStmt = "DELETE FROM customers WHERE customer_id = ?;";
        PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        return id;
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return null;
    }

    @Override
    protected Customer mapTo(ResultSet rs) {
        try {
            if(rs.next()){
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<Customer> mapToList(ResultSet rs) {
        List<Customer> customers = new ArrayList<>();
        try {
            while (rs.next()) {
                customers.add(
                        new Customer(rs.getInt("customer_id"),rs.getString("first_name"),
                                rs.getString("last_naem"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("address"),
                                rs.getDouble("balance")
                        )
                );
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
