package services;

import repositories.OrderToProductRepository;
import repositories.OrdersRepository;
import things.userRelated.OrderDetails;

import java.sql.Connection;
import java.util.List;

public class OrderDetailService {
    Connection connection;
    OrderToProductRepository otpr;

    public OrderDetailService(Connection connection) {
        this.connection = connection;
        otpr = new OrderToProductRepository(this.connection);
    }

    public OrderDetails find(Integer orderId) {
        return otpr.read(orderId);
    }

    public Integer update(OrderDetails orderDetails) {
        return null;
    }

    public Integer delete(Integer id) {
        return null;
    }
}
