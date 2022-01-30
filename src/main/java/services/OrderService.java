package services;

import things.userRelated.Order;

import java.sql.Connection;
import java.util.List;

public class OrderService extends Service<Order>{
    public OrderService(Connection connection) {
        super(connection);
    }

    @Override
    public Order find(Integer id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Integer update(Order order) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
