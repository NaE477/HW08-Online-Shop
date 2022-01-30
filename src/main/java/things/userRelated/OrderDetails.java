package things.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import things.shopRelated.Product;

import java.util.List;

@Getter
@Setter
public class OrderDetails {
    private Order order;
    private List<Product> products;
    private List<Integer> quantities;

    public OrderDetails(Order order) {
        this.order = order;
    }
}
