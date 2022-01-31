package services;

import repositories.CustomersRepository;
import users.Customer;

import java.sql.Connection;
import java.util.List;

public class CustomerService extends Service<Customer>{
    CustomersRepository cr;
    public CustomerService(Connection connection) {
        super(connection);
        cr = new CustomersRepository(super.getConnection());
    }


    public Integer signUp(Customer customer){
        return cr.insert(customer);
    }
    @Override
    public Customer find(Integer customerId) {
        return cr.read(customerId);
    }
    public Customer findByUsername(String username){
        return cr.read(username);
    }
    public Customer findByEmail(String email){
        return cr.readByEmail(email);
    }

    @Override
    public List<Customer> findAll() {
        return cr.readAll();
    }

    @Override
    public Integer update(Customer customer) {
        return cr.update(customer);
    }

    @Override
    public Integer delete(Integer customerId) {
        return cr.delete(customerId);
    }
}
