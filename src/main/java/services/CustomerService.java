package services;

import users.Customer;

import java.sql.Connection;
import java.util.List;

public class CustomerService extends Service<Customer>{
    public CustomerService(Connection connection) {
        super(connection);
    }

    @Override
    public Customer find(Integer id) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Integer update(Customer customer) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
