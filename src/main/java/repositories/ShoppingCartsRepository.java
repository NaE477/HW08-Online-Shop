package repositories;

import things.userRelated.ShoppingCart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class ShoppingCartsRepository extends Repository<ShoppingCart>{

    public ShoppingCartsRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected ShoppingCart mapTo(ResultSet rs) {
        return null;
    }

    @Override
    protected List<ShoppingCart> mapToList(ResultSet rs) {
        return null;
    }


    @Override
    public Integer insert(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public ShoppingCart read(Integer id) {
        return null;
    }

    @Override
    public List<ShoppingCart> readAll() {
        return null;
    }

    @Override
    public Integer update(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public Integer delete(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
