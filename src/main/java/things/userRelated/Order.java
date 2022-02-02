package things.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import users.Customer;

import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Order {
    private Integer id;
    private Date orderDate;
    private Customer customer;
    private OrderStatus status;

    @Override
    public String toString() {
        return "ID: " + id +
                " , Date: " + orderDate +
                " , Status: " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
