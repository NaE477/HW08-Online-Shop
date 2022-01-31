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
    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Online Shop.\n" +
                    "Enter L/l to Login or S/s to sign up.");
            String lOrS = scanner.nextLine().toUpperCase(Locale.ROOT);
            if (lOrS.equals("L")) {
                User user = login();
                if(user != null ){
                    if(user instanceof Manager){
                        ManagerSection managerSection = new ManagerSection((Manager) user);
                        managerSection.entry();
                    }
                    else if(user instanceof Customer){
                        CustomerSection customerSection = new CustomerSection((Customer) user);
                        customerSection.entry();
                    }
                }
            } else if (lOrS.equals("S")) {
                System.out.print("First Name: ");
                String firstName = scanner.nextLine();
                System.out.print("Last Name: ");
                String lastName = scanner.nextLine();
                System.out.println("Username: ");

            }
        }
    }

    public static User login(){
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if(managerService.findByUsername(username) != null){
            Manager manager = managerService.findByUsername(username);
            if(manager.getPassword().equals(password)){
                return manager;
            }
        } else if(customerService.findByUsername(username) != null){
            Customer customer = customerService.findByUsername(username);
            if(customer.getPassword().equals(password)){
                return customer;
            }
        }
        return null;
    }
}
