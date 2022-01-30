package services;

import things.userRelated.ShoppingCart;

import java.sql.Connection;
import java.util.List;

public class ShoppingCartService extends Service<ShoppingCart> {
    public ShoppingCartService(Connection connection) {
        super(connection);
    }

    @Override
    public ShoppingCart find(Integer id) {
        return null;
    }

    @Override
    public List<ShoppingCart> findAll() {
        return null;
    }

    @Override
    public Integer update(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
