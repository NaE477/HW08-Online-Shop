package services;

import repositories.ShoppingCartToProductRepository;
import repositories.ShoppingCartsRepository;
import things.userRelated.ShoppingCart;
import users.Customer;

import java.sql.Connection;
import java.util.List;

public class ShoppingCartService extends Service<ShoppingCart> {
    ShoppingCartsRepository scr;
    ShoppingCartToProductRepository sctpr;
    public ShoppingCartService(Connection connection) {
        super(connection);
        scr = new ShoppingCartsRepository(super.getConnection());
        sctpr = new ShoppingCartToProductRepository(super.getConnection());
    }

    @Override
    public ShoppingCart find(Integer shoppingCartId) {
        return scr.read(shoppingCartId);
    }
    public ShoppingCart findByCustomer(Customer customer){ return scr.readByCustomer(customer);}

    @Override
    public List<ShoppingCart> findAll() {
        return null;
    }

    @Override
    public Integer update(ShoppingCart shoppingCart) {
        return scr.update(shoppingCart);
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
