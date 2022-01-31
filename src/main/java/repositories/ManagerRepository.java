package repositories;

import services.ManagerService;
import users.Customer;
import users.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerRepository extends Repository<Manager>{

    public ManagerRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Manager manager) {
        String insStmt = "INSERT INTO managers (first_name, last_name, username, password, email, salary) " +
                "VALUES (?,?,?,?,?,?) RETURNING manager_id;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(insStmt);
            ps.setString(1,manager.getFirstName());
            ps.setString(2,manager.getLastName());
            ps.setString(3,manager.getUsername());
            ps.setString(4,manager.getPassword());
            ps.setString(5,manager.getEmailAddress());
            ps.setDouble(6,manager.getSalary());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("manager_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Manager read(Integer id) {
        String readStmt = "SELECT * FROM managers WHERE manager_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt);
            ps.setInt(1,id);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Manager read(String username){
        String readStmt = "SELECT * FROM managers WHERE username = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt);
            ps.setString(1,username);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Manager readByEmail(String email){
        String readStmt = "SELECT * FROM managers WHERE email = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readStmt);
            ps.setString(1,email);
            return mapTo(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Manager> readAll() {
        String readAllStmt = "SELECT * FROM managers;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(readAllStmt);
            return mapToList(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(Manager manager) {
        String updateStmt = "UPDATE managers " +
                "SET first_name = ?," +
                "last_name = ?," +
                " password = ?," +
                " salary = ?" +
                " WHERE manager_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(updateStmt);
            ps.setString(1,manager.getFirstName());
            ps.setString(2,manager.getLastName());
            ps.setString(3,manager.getPassword());
            ps.setDouble(4,manager.getSalary());
            ps.setInt(5,manager.getId());
            ps.executeUpdate();
            return manager.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer delete(Manager manager) {
        String delStmt = "DELETE FROM managers WHERE manager_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1,manager.getId());
            ps.executeUpdate();
            return manager.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        String delStmt = "DELETE FROM managers WHERE manager_id = ?;";
        try {
            PreparedStatement ps = super.getConnection().prepareStatement(delStmt);
            ps.setInt(1,id);
            ps.executeUpdate();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Manager mapTo(ResultSet rs) {
        try {
            if(rs.next())
                return new Manager(
                        rs.getInt("manager_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getDouble("salary")
                );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<Manager> mapToList(ResultSet rs) {
        List<Manager> managers = new ArrayList<>();
        try {
            while (rs.next()){
                managers.add(
                        new Manager(
                                rs.getInt("manager_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getDouble("salary")
                        )
                );
            }
            return managers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
