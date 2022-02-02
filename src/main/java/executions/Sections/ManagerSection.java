package executions.Sections;

import executions.ConClass;
import executions.Utilities;
import services.CategoryService;
import services.ManagerService;
import services.ProductService;
import things.shopRelated.Category;
import things.shopRelated.Product;
import things.shopRelated.WrongCategoryEntry;
import users.Manager;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ManagerSection {
    private final Manager manager;
    private final Connection connection = ConClass.getInstance().getConnection();
    private final ManagerService managerService = new ManagerService(connection);
    private final CategoryService categoryService = new CategoryService(connection);
    private final CategoryService cs = new CategoryService(connection);
    private final ProductService ps = new ProductService(connection);
    private final Scanner scanner = new Scanner(System.in);
    private final Utilities utilities = new Utilities(connection);

    public ManagerSection(Manager manager) {
        this.manager = manager;
    }

    public void entry() {
        System.out.println("Welcome to Manager Section.\n" +
                "Choose a menu to enter: ");

        label:
        while (true) {
            System.out.println("""
                    1-Add Category
                    2-Add Product
                    3-Charge Product
                    4-Create Manager
                    5-View Profile
                    6-Change Password
                    0-Exit""");
            System.out.print("Option: ");
            String opt = scanner.nextLine();
            switch (opt) {
                case "1":
                    addCategory();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    chargeProduct();
                    break;
                case "4":
                    signUpManager();
                    break;
                case "5":
                    viewProfile();
                    break;
                case "6":
                    changePassword();
                    break;
                case "0":
                    break label;
                default:
                    System.out.println("Choose from options.");
                    break;
            }
        }
    }

    //OPTION #1
    public void addCategory() {
        try {
            List<Category> firstLayerCategories = categoryService.findAllRootCategories();
            utilities.iterateThrough(firstLayerCategories);
                if (firstLayerCategories.size() > 0) {
                    System.out.println("""
                            1-Add to these categories
                            2-Choose Category ID to edit hierarchy""");
                    String opt = scanner.nextLine();
                    if (opt.equals("1")) {
                        String newFirstLayerCategoryName = utilities.categoryReceiver();
                        Category newFirstLayerCategory = new Category(0, newFirstLayerCategoryName, null);
                        Integer newFirstLayerCategoryID = categoryService.addSuperCategory(newFirstLayerCategory);
                        utilities.printGreen("New Category created with ID " + newFirstLayerCategoryID);
                    } else if (opt.equals("2")) {
                        System.out.print("Category ID: ");
                        Integer firstLayerCategoryID = utilities.intReceiver();
                        Category firstLayerCategory = categoryService.find(firstLayerCategoryID);
                        if (firstLayerCategory != null && firstLayerCategories.contains(firstLayerCategory)) {
                            List<Category> secondLayerCategories = categoryService.findAllBySuper(firstLayerCategory);
                            utilities.iterateThrough(secondLayerCategories);
                            if (secondLayerCategories.size() > 0) {
                                System.out.println("""
                                        1-Add to these categories
                                        2-Choose Category ID to edit hierarchy""");
                                String secondOpt = scanner.nextLine();
                                if (secondOpt.equals("1")) {
                                    String newSecondLayerCategoryName = utilities.categoryReceiver();
                                    Category newSecondLayerCategory = new Category(0, newSecondLayerCategoryName, firstLayerCategory);
                                    Integer newSecondLayerCategoryID = categoryService.addCommonCategory(newSecondLayerCategory);
                                    utilities.printGreen("New Category Created with ID: " + newSecondLayerCategoryID);
                                } else if (secondOpt.equals("2")) {
                                    System.out.print("Category ID: ");
                                    Integer secondLayerCategoryID = utilities.intReceiver();
                                    Category secondLayerCategory = categoryService.find(secondLayerCategoryID);
                                    if (secondLayerCategory != null && secondLayerCategories.contains(secondLayerCategory)) {
                                        List<Category> thirdLayerCategories = categoryService.findAllBySuper(secondLayerCategory);
                                        utilities.iterateThrough(thirdLayerCategories);
                                        String newThirdLayerCategoryName = utilities.categoryReceiver();
                                        Category newThirdLayerCategory = new Category(0, newThirdLayerCategoryName, secondLayerCategory);
                                        Integer newThirdLayerCategoryID = categoryService.addCommonCategory(newThirdLayerCategory);
                                        utilities.printGreen("New Category created with ID " + newThirdLayerCategoryID);
                                    } else throw new WrongCategoryEntry();
                                } else {
                                    utilities.printRed("Wrong Option,Bye.");
                                }
                            } else {
                                String newSecondLayerCategoryName = utilities.categoryReceiver();
                                Category newSecondLayerCategory = new Category(0, newSecondLayerCategoryName, firstLayerCategory);
                                Integer newSecondLayerCategoryID = categoryService.addCommonCategory(newSecondLayerCategory);
                                newSecondLayerCategory.setId(newSecondLayerCategoryID);
                                utilities.printGreen("New Category created with ID " + newSecondLayerCategoryID);
                            }
                        } else throw new WrongCategoryEntry();
                    }
                    else {
                        utilities.printRed("Wrong Option,Bye.");
                    }
                } else {
                    String newFirstLayerCategoryName = utilities.categoryReceiver();
                    Category newFirstLayerCategory = new Category(0, newFirstLayerCategoryName, null);
                    Integer newFirstLayerCategoryID = categoryService.addSuperCategory(newFirstLayerCategory);
                    utilities.printGreen("New Category created with ID " + newFirstLayerCategoryID);
                }
        } catch (WrongCategoryEntry e) {
            utilities.printRed("Something Wrong with Category.Either wrong ID or category already exists.");
        }
    }

    //OPTION #2
    public void addProduct() {
        System.out.print("Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Price: ");
        Double price = utilities.doubleReceiver();
        Category pCategory;
        while (true) {
            utilities.iterateThrough(cs.findAll());
            System.out.print("Category: ");
            String categoryName = scanner.nextLine();
            Category category = cs.find(categoryName);
            if (category != null) {
                pCategory = category;
                break;
            } else System.out.println("Category does not exist.");
        }
        System.out.print("Quantity: ");
        Integer quantity = utilities.intReceiver();
        Product newProduct = new Product(0, productName, description, price, pCategory);
        Integer newProductID = ps.addProductToWareHouse(newProduct, quantity);
        System.out.println("New Product Created with ID: " + newProductID);
    }

    //OPTION #3
    public void chargeProduct() {
        HashMap<Product, Integer> products = ps.findAllWithQuantity();
        products.forEach((product, quantity) -> utilities.printGreen(product.toString() + "\nQuantity: " + quantity,400));
        System.out.print("Product ID to Charge: ");
        Integer productID = utilities.intReceiver();
        if (ps.find(productID) != null) {
            Product productToCharge = ps.find(productID);
            int oldQuantity = products.get(productToCharge);
            while (true) {
                System.out.print("Quantity: ");
                int chargeQuantity = utilities.intReceiver();
                    Integer update = ps.changeQuantity(productToCharge, oldQuantity + chargeQuantity);
                    if (update != null) {
                        utilities.printGreen("Product got charged.");
                        break;
                    } else System.out.println("Something went wrong with the database.");
            }
        } else {
            utilities.printRed("Wrong product ID.");
        }
    }

    //OPTION #4 - Creates a new manager
    public void signUpManager() {
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        String username = utilities.usernameReceiver();
        String password = utilities.passwordReceiver();
        String email = utilities.emailReceiver();
        System.out.print("Salary: ");
        Double salary = utilities.doubleReceiver();
        Manager newManager = new Manager(0, firstName, lastName, username, password, email, salary);
        Integer newManagerID = managerService.signUp(newManager);
        utilities.printGreen("New Manager Created with ID: " + newManagerID);
    }

    //OPTION #5
    public void viewProfile() {
        utilities.printGreen(managerService.find(manager.getId()).toString());
    }

    //OPTION #6
    public void changePassword() {
        System.out.print("Old Password: ");
        String oldPass = scanner.nextLine();
        String newPass = utilities.passwordReceiver();
        if (manager.getPassword().equals(oldPass)) {
            manager.setPassword(newPass);
            Integer passUpdate = managerService.update(manager);
            if (passUpdate != null) {
                utilities.printGreen("Password Changed for ID: " + passUpdate);
            } else utilities.printRed("Something went wrong with the database.");
        } else {
            utilities.printRed("Old password doesn't match.");
        }
    }
}