package things.userRelated;

import executions.Utilities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import things.shopRelated.Product;

import java.util.HashMap;

@Getter
@Setter
public class OrderDetails {
    private Order order;
    private HashMap<Product,Integer> products;

    public OrderDetails(Order order,HashMap<Product,Integer> products) {
        this.order = order;
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order ID: " + order.getId() +
                "\n Products: " + Utilities.iterateThroughProducts(products);
    }
}
