package things.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import users.Customer;

import java.sql.Date;

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
}
