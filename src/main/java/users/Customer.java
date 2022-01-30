package users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer extends User{
    private String address;
    private Double balance;

    public Customer(Integer id, String firstName, String lastName, String username, String password, String emailAddress,String address,Double balance) {
        super(id, firstName, lastName, username, password, emailAddress);
        this.address = address;
        this.balance = balance;
    }
}
