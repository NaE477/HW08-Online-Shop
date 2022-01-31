package things.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import things.shopRelated.Product;
import users.Customer;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingCart {
    private Integer id;
    private Customer customer;
    private Map<Product,Integer> products;
}
