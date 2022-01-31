package executions;


import services.CustomerService;
import users.User;

import java.sql.Connection;
import java.util.Locale;
import java.util.Scanner;

public class Entry {
    static Connection connection = ConClass.getInstance().getConnection();
    static CustomerService customerService = new CustomerService(connection);

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Online Shop.\n" +
                    "Enter L/l to Login or S/s to sign up.");
            String lOrS = scanner.nextLine().toUpperCase(Locale.ROOT);
            if (lOrS.equals("L")) {
                User user = login();

            } else if (lOrS.equals("S")) {

            }
        }
    }

    public static User login(){
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
return null;
    }
}
