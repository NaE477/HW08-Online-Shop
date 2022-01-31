package executions.Sections;

import executions.ConClass;
import executions.Utilities;
import services.CategoryService;
import services.ProductService;
import things.shopRelated.Category;
import things.shopRelated.Product;
import things.shopRelated.WrongCategoryEntry;
import users.Manager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ManagerSection {
    private Manager manager;
    private final Connection connection = ConClass.getInstance().getConnection();
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
        System.out.println("""
                1-Add Category
                2-Add Product
                3-Charge Product
                4-Create Manager
                0-Exit""");
        label:
        while (true) {
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

                    break;
                case "0":
                    break label;
                default:
                    System.out.println("Choose from options.");
                    break;
            }
        }
    }

    public void addCategory() {
        try {
            CategoryService categoryService = new CategoryService(connection);
            List<Category> firstLayerCategories = categoryService.findAllRootCategories();
            utilities.iterateThrough(firstLayerCategories);
            if (firstLayerCategories.size() > 0) {
                System.out.println("""
                        1-Add to these categories
                        2-Choose Category ID to edit""");
                String opt = scanner.nextLine();
                if (opt.equals("1")) {
                    System.out.print("New category: ");
                    String newFirstLayerCategoryName = utilities.categoryReceiver();
                    Category newFirstLayerCategory = new Category(0, newFirstLayerCategoryName, null);
                    Integer newFirstLayerCategoryID = categoryService.addSuperCategory(newFirstLayerCategory);
                    System.out.println("New Category created with ID " + newFirstLayerCategoryID);
                } else if (opt.equals("2")) {
                    Integer secondLayerCategoryID = utilities.intReceiver();
                    Category secondLayerCategory = categoryService.find(secondLayerCategoryID);
                    if (secondLayerCategory != null) {
                        List<Category> secondLayerCategories = categoryService.findAllBySuper(secondLayerCategory);
                        utilities.iterateThrough(secondLayerCategories);
                        System.out.println("""
                                1-Add to these categories
                                2-Choose Category ID to edit""");
                        String secondOpt = scanner.nextLine();
                        if (secondOpt.equals("1")) {
                            System.out.print("New Category: ");
                            String newSecondLayerCategoryName = utilities.categoryReceiver();
                            Category newSecondLayerCategory = new Category(0, newSecondLayerCategoryName, secondLayerCategory);
                            Integer newSecondLayerCategoryID = categoryService.addCommonCategory(newSecondLayerCategory);
                            utilities.printGreen("New Category Created with ID: " + newSecondLayerCategoryID);
                        } else if (secondOpt.equals("2")) {
                            Integer thirdLayerCategoryID = utilities.intReceiver();
                            Category thirdLayerCategory = categoryService.find(thirdLayerCategoryID);

                        }
                    } else throw new WrongCategoryEntry();
                } else System.out.println("Wrong Option,Bye.");
            } else {
                System.out.print("New category: ");
                String newFirstLayerCategoryName = utilities.categoryReceiver();
                Category newFirstLayerCategory = new Category(0, newFirstLayerCategoryName, null);
                Integer newFirstLayerCategoryID = categoryService.addSuperCategory(newFirstLayerCategory);
                System.out.println("New Category created with ID " + newFirstLayerCategoryID);
            }
        } catch (WrongCategoryEntry e) {
            System.out.println("Something Wrong with Category.Either wrong ID or category already exists.");
        }
    }

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

    public void chargeProduct() {
        HashMap<Product, Integer> products = ps.findAllWithQuantity();
        products.forEach((product, quantity) -> System.out.println(product.toString() + "\nQuantity: " + quantity));
        System.out.print("Product ID to Charge: ");
        Integer productID = utilities.intReceiver();
        if (ps.find(productID) != null) {
            Product productToCharge = ps.find(productID);
            int oldQuantity = products.get(productToCharge);
            while (true) {
                System.out.print("Quantity: ");
                int quantity = utilities.intReceiver();
                if (oldQuantity < quantity) {
                    System.out.println("New quantity is less than old quantity. Discharge unavailable.");
                    break;
                } else {
                    Integer update = ps.update(productToCharge, quantity);
                    if (update != null) {
                        System.out.println("Product got charged.");
                    } else System.out.println("Something went wrong with the database.");
                }
            }
        } else {
            System.out.println("Wrong product ID.");
        }
    }
}