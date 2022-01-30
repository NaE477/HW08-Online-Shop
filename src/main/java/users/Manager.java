package users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Manager extends User{
    private Double salary;

    public Manager(int id,String firstname,String lastname,String username,String password,String emailAddress,Double salary){
        super(id,firstname,lastname,username,password,emailAddress);
        this.salary = salary;
    }
}
