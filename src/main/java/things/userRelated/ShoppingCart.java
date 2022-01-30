package things.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import things.shopRelated.Product;

import java.util.List;

@Getter
@Setter
public class ShoppingCart {
    private Integer id;
    private List<Product> products;
    private List<Integer> quantities;

    public ShoppingCart(Integer id) {
        this.id = id;
    }
}
