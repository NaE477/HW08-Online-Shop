package services;

import things.userRelated.OrderDetails;

import java.sql.Connection;
import java.util.List;

public class OrderDetailService extends Service<OrderDetails> {
    public OrderDetailService(Connection connection) {
        super(connection);
    }

    @Override
    public OrderDetails find(Integer id) {
        return null;
    }

    @Override
    public List<OrderDetails> findAll() {
        return null;
    }

    @Override
    public Integer update(OrderDetails orderDetails) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
