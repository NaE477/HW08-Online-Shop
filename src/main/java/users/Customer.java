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

    @Override
    public String toString() {
        return "ID: " + super.getId() +
                "\nFull Name: " + super.getFirstName() + " " + super.getLastName() +
                "\nEmail: " + super.getEmailAddress() + " " +
                "\nAddress: " + getAddress() +
                "\nBalance: " + getBalance().toString();
    }
}
