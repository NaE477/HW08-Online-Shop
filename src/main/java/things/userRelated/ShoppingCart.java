package things.userRelated;

import executions.Utilities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import things.shopRelated.Product;
import users.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingCart {
    private Integer id;
    private Customer customer;
    private HashMap<Product, Integer> products;

    @Override
    public String toString() {
        return Utilities.iterateThroughProducts(products);
    }
}
