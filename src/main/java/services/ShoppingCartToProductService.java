package services;

import repositories.ShoppingCartToProductRepository;
import things.shopRelated.Product;
import things.userRelated.ShoppingCart;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartToProductService {
    ShoppingCartToProductRepository sctpr;
    final Connection connection;

    public ShoppingCartToProductService(Connection connection) {
        this.connection = connection;
        sctpr = new ShoppingCartToProductRepository(this.connection);
    }

    public void addProductToCart(ShoppingCart shoppingCart, Product product, Integer quantity){
        sctpr.insert(shoppingCart,product,quantity);
    }

    public HashMap<Product,Integer> findCartProducts(ShoppingCart cart){
        return sctpr.readAllByCart(cart);
    }

    public void changeQuantity(ShoppingCart shoppingCart,Product product,Integer quantity){
        sctpr.update(shoppingCart,product,quantity);
    }

    public void deleteFromCart(ShoppingCart shoppingCart,Product product){
        sctpr.delete(shoppingCart,product);
    }
}
