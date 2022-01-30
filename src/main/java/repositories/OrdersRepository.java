package repositories;

import things.userRelated.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class OrdersRepository extends Repository<Order>{
    public OrdersRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Integer insert(Order order) {
        return null;
    }

    @Override
    public Order read(Integer id) {
        return null;
    }

    @Override
    public List<Order> readAll() {
        return null;
    }

    @Override
    public Integer update(Order order) {
        return null;
    }

    @Override
    public Integer delete(Order order) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    protected Order mapTo(ResultSet rs) {
        return null;
    }

    @Override
    protected List<Order> mapToList(ResultSet rs) {
        return null;
    }
}
