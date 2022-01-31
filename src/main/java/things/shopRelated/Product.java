package things.shopRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private Integer id;
    private String productName;
    private String description;
    private Double price;
    private Category category;

    @Override
    public String toString() {
        return "ID: " + id +
                ", Product Name: " + productName +
                "\n Description: " + description +
                "\n Price: " + price +
                "\n Category: " + category.getCatName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
