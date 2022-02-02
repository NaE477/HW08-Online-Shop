package services;

import repositories.OrdersRepository;
import things.userRelated.Order;
import users.Customer;

import java.sql.Connection;
import java.util.List;

public class OrderService extends Service<Order>{
    OrdersRepository or;
    public OrderService(Connection connection) {
        super(connection);
        or = new OrdersRepository(super.getConnection());
    }
    public Integer registerOrder(Order order){
        return or.insert(order);
    }

    @Override
    public Order find(Integer orderId) {
        return or.read(orderId);
    }

    @Override
    public List<Order> findAll() {
        return or.readAll();
    }
    public List<Order> findAllForCustomer(Customer customer){
        return or.readAllByCustomer(customer);
    }
    public List<Order> findAllPendingsForCustomer(Customer customer){
        return or.readAllPendingsByCustomer(customer);
    }

    @Override
    public Integer update(Order order) {
        return or.update(order);
    }

    @Override
    public Integer delete(Integer orderId) {
        return or.delete(orderId);
    }
}
