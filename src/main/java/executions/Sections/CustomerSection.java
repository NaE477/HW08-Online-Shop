package executions.Sections;

import executions.ConClass;
import executions.Utilities;
import repositories.ShoppingCartToProductRepository;
import services.*;
import things.shopRelated.Category;
import things.shopRelated.Product;
import things.userRelated.OrderDetails;
import things.userRelated.ShoppingCart;
import users.Customer;

import java.sql.Connection;
import java.util.*;

public class CustomerSection {
    Customer customer;
    ShoppingCart shoppingCart;
    private final Connection connection = ConClass.getInstance().getConnection();
    private final ShoppingCartToProductService sctps = new ShoppingCartToProductService(connection);
    private final ShoppingCartService shoppingCartService = new ShoppingCartService(connection);
    private final CustomerService customerService = new CustomerService(connection);
    private final CategoryService categoryService = new CategoryService(connection);
    private final ProductService productService = new ProductService(connection);
    private final OrderService orderService = new OrderService(connection);
    private final Utilities utilities = new Utilities(connection);
    private final Scanner scanner = new Scanner(System.in);

    public CustomerSection(Customer customer) {
        this.customer = customer;
        shoppingCart = shoppingCartService.findByCustomer(customer);
        shoppingCart.setProducts(sctps.findCartProducts(shoppingCart));
    }

    public void entry() {
        System.out.println("Welcome to Customer Section , " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println(shoppingCart.toString());
        while (true) {
            System.out.println("""
                    1-View Products to Buy
                    2-Deposit Balance
                    3-View Shopping Cart
                    4-View Orders
                    -View Profile
                    0-Exit""");
            String option = scanner.nextLine();
            if (option.equals("1")) {
                viewCategories();
            }
            else if (option.equals("3")){
                shoppingCart.toString();
            }
            else if (option.equals("0")) {
                break;
            } else {
                utilities.printRed("Wrong Option.");
            }
        }
    }

    private void viewCategories(){
        List<Category> rootCategories = categoryService.findAllRootCategories();
        utilities.iterateThrough(rootCategories);
        if (rootCategories.size() > 0) {
            label:
            while (true) {
                System.out.print("Choose a Category ID to continue: ");
                Integer rootCatId = utilities.intReceiver();
                Category rootCat = categoryService.find(rootCatId);
                if (rootCat != null && rootCategories.contains(rootCat)) {
                    while (true) {
                        System.out.print("Choose T/t to view all products in This Category or C/c to continue withing categories: ");
                        String tOrC = scanner.nextLine();
                        if (tOrC.toUpperCase(Locale.ROOT).equals("T")) {
                            buyProduct(rootCat);
                            break label;
                        } else if (tOrC.toUpperCase(Locale.ROOT).equals("C")) {
                            List<Category> firstLayerCategories = categoryService.findAllBySuper(rootCat);
                            utilities.iterateThrough(firstLayerCategories);
                            if (firstLayerCategories.size() > 0) {
                                while (true) {
                                    System.out.println("Category ID: ");
                                    Integer firstLayerCatID = utilities.intReceiver();
                                    Category firstLayerCategory = categoryService.find(firstLayerCatID);
                                    if (firstLayerCategory != null && firstLayerCategories.contains(firstLayerCategory)) {
                                        while (true) {
                                            System.out.println("Choose T/t to view all products in This Category or C/c to continue withing categories: ");
                                            String secondLayerTOrC = scanner.nextLine();
                                            if (secondLayerTOrC.toUpperCase(Locale.ROOT).equals("T")) {
                                                buyProduct(firstLayerCategory);
                                                break label;
                                            } else if (secondLayerTOrC.toUpperCase(Locale.ROOT).equals("C")) {
                                                List<Category> secondLayerCategories = categoryService.findAllBySuper(firstLayerCategory);
                                                utilities.iterateThrough(secondLayerCategories);
                                                if (secondLayerCategories.size() > 0) {
                                                    while (true) {
                                                        System.out.println("Category ID: ");
                                                        Integer secondLayerCategoryID = utilities.intReceiver();
                                                        Category secondLayerCategory = categoryService.find(secondLayerCategoryID);
                                                        if (secondLayerCategory != null && secondLayerCategories.contains(secondLayerCategory)) {
                                                            buyProduct(secondLayerCategory);
                                                            break label;
                                                        } else {
                                                            utilities.printRed("Wrong input");
                                                        }
                                                    }
                                                } else {
                                                    utilities.printYellow("Category has no further sub categories,you'll be shown products with this category.");
                                                    buyProduct(firstLayerCategory);
                                                    break label;
                                                }
                                            } else utilities.printRed("Wrong input");
                                        }
                                    } else {
                                        utilities.printRed("Wrong input");
                                    }
                                }
                            } else {
                                utilities.printYellow("Category has no further sub categories,you'll be shown products with this category.");
                                buyProduct(rootCat);
                                break label;
                            }
                        } else utilities.printRed("Wrong input.");
                    }
                } else utilities.printRed("Wrong input.");
            }
        } else System.out.println("No Category or Item been created yet. Apologies.");
    }

    private void buyProduct(Category category) {
        List<Integer> descendantCategoriesIDs = categoryService.findAllDescendants(category);
        HashMap<Product, Integer> products = new HashMap<>();
        for (Integer descendantCategoriesID : descendantCategoriesIDs) {
            products.putAll(productService.findAllByCategoryID(descendantCategoriesID));
        }
        products.forEach((product,quantity) -> utilities.printGreen(product + "\n In Stock: " + quantity,400));
        while (true) {
            System.out.print("Product ID you want to add to your shopping cart: ");
            Integer productID = utilities.intReceiver();
            Product product = productService.find(productID);
            HashMap<Product,Integer> productWithQuantityInWareHouse = productService.findWithQuantity(product);
            Integer productWarehouseQuantity = productWithQuantityInWareHouse.get(product);
            Map<Product,Integer> productsInShoppingCart = shoppingCart.getProducts();
            if (product != null && products.containsKey(product)) {
                System.out.print("Quantity(In Stock: " + productWarehouseQuantity + "): ");
                Integer quantity = utilities.intReceiver();
                if(productWarehouseQuantity >= quantity) {
                    if(quantity > 0) {
                        if (shoppingCart.getProducts().containsKey(product)) {
                            Integer oldQuantity = shoppingCart.getProducts().get(product);
                            Integer newQuantity = oldQuantity + quantity;
                            productsInShoppingCart.put(product, newQuantity);
                            shoppingCart.setProducts(productsInShoppingCart);
                            sctps.changeQuantity(shoppingCart, product, newQuantity);
                            productService.changeQuantity(product,productWarehouseQuantity - quantity);
                            utilities.printGreen("New amount added to product in your shopping cart.");
                        } else {
                            productsInShoppingCart.put(product, quantity);
                            shoppingCart.setProducts(productsInShoppingCart);
                            sctps.addProductToCart(shoppingCart, product, quantity);
                            productService.changeQuantity(product,productWarehouseQuantity - quantity);
                            utilities.printGreen("Product added to your shopping cart.");
                        }
                    } else {
                        utilities.printRed("0 quantity not permitted.");
                    }
                    break;
                } else {
                    utilities.printRed("Quantity more than stock.");
                }
            } else utilities.printRed("Wrong ID.");
        }
    }
}
