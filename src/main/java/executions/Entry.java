package executions;


import executions.Sections.CustomerSection;
import executions.Sections.ManagerSection;
import services.CustomerService;
import services.ManagerService;
import users.Customer;
import users.Manager;
import users.User;

import java.sql.Connection;
import java.util.Locale;
import java.util.Scanner;

public class Entry {
    static Connection connection = ConClass.getInstance().getConnection();
    static CustomerService customerService = new CustomerService(connection);
    static ManagerService managerService = new ManagerService(connection);
    static Scanner scanner = new Scanner(System.in);
    static Utilities utilities = new Utilities(connection);

    public static void main(String[] args) {
        label:
        while (true) {
            System.out.println("Welcome to the Online Shop.\n" +
                    "Enter L/l to Login or S/s to sign up or E/e to Exit.");
            String lOrS = scanner.nextLine().toUpperCase(Locale.ROOT);
            switch (lOrS) {
                case "L":
                    User user = login();
                    if (user != null) {
                        if (user instanceof Manager) {
                            ManagerSection managerSection = new ManagerSection((Manager) user);
                            managerSection.entry();
                        } else if (user instanceof Customer) {
                            CustomerSection customerSection = new CustomerSection((Customer) user);
                            customerSection.entry();
                        }
                    } else System.out.println("Wrong Username/Password. Try Again.");
                    break;
                case "S":
                    signUp();
                    break;
                case "E":
                    break label;
                default:
                    System.out.println("Enter L/S/E.");
                    break;
            }
        }
    }

    public static User login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (managerService.findByUsername(username) != null) {
            Manager manager = managerService.findByUsername(username);
            if (manager.getPassword().equals(password)) {
                return manager;
            }
        } else if (customerService.findByUsername(username) != null) {
            Customer customer = customerService.findByUsername(username);
            if (customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    public static void signUp() {
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        String username = utilities.usernameReceiver();
        String password = utilities.passwordReceiver();
        String email = utilities.emailReceiver();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Initial Balance: ");
        Double balance = utilities.doubleReceiver();
        Customer newCustomer = new Customer(0,firstName,lastName,username,password,email,address,balance);
        Integer newId = customerService.signUp(newCustomer);
        if(newId != null){
            utilities.printGreen("You've been signed up successfully with ID: " + newId);
        } else System.out.println("Something wrong with your sign up process.");
    }
}
