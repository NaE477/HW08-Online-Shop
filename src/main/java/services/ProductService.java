package services;

import repositories.ProductsRepository;
import things.shopRelated.Product;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class ProductService extends Service<Product>{
    ProductsRepository pr;
    public ProductService(Connection connection) {
        super(connection);
        pr = new ProductsRepository(super.getConnection());
    }

    public Integer addProductToWareHouse(Product product,Integer quantity){
        return pr.insert(product,quantity);
    }
    @Override
    public Product find(Integer productId) {
        return pr.read(productId);
    }

    @Override
    public List<Product> findAll() {
        return pr.readAll();
    }
    public HashMap<Product,Integer> findAllByCategoryID(Integer categoryID){
        return pr.readAllByCategoryID(categoryID);
    }

    public HashMap<Product,Integer> findAllWithQuantity(){
        return pr.readAllWithQuantity();
    }

    @Override
    public Integer update(Product product) {
        return pr.update(product);
    }
    public Integer update(Product product,Integer quantity){
        return pr.update(product,quantity);
    }

    @Override
    public Integer delete(Integer productId) {
        return pr.delete(productId);
    }
}
