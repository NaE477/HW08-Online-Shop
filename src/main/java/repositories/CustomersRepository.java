package repositories;

import users.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class CustomersRepository extends Repository<Customer>{
    public CustomersRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Customer customer) {
        return null;
    }

    @Override
    public Customer read(Integer id) {
        return null;
    }

    @Override
    public List<Customer> readAll() {
        return null;
    }

    @Override
    public Integer update(Customer customer) {
        return null;
    }

    @Override
    public Integer delete(Customer customer) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    protected Customer mapTo(ResultSet rs) {
        return null;
    }

    @Override
    protected List<Customer> mapToList(ResultSet rs) {
        return null;
    }
}
