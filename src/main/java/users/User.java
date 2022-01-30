package users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class User {
    private Integer id;
    private String firstName,lastName,username,password,emailAddress;
}
