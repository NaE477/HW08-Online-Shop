package repositories;

import things.userRelated.OrderDetails;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class OrderDetailsRepository extends Repository<OrderDetails> {
    public OrderDetailsRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(OrderDetails orderDetails) {
        return null;
    }

    @Override
    public OrderDetails read(Integer id) {
        return null;
    }

    @Override
    public List<OrderDetails> readAll() {
        return null;
    }

    @Override
    public Integer update(OrderDetails orderDetails) {
        return null;
    }

    @Override
    public Integer delete(OrderDetails orderDetails) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    protected OrderDetails mapTo(ResultSet rs) {
        return null;
    }

    @Override
    protected List<OrderDetails> mapToList(ResultSet rs) {
        return null;
    }
}
