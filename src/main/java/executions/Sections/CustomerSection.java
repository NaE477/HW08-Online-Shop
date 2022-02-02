package executions.Sections;

import executions.ConClass;
import executions.Utilities;
import services.*;
import things.shopRelated.Category;
import things.shopRelated.Product;
import things.userRelated.Order;
import things.userRelated.OrderDetails;
import things.userRelated.OrderStatus;
import things.userRelated.ShoppingCart;
import users.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CustomerSection {
    Customer customer;
    ShoppingCart shoppingCart;
    private final Connection connection = ConClass.getInstance().getConnection();
    private final ShoppingCartToProductService shoppingCartToProductService = new ShoppingCartToProductService(connection);
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
        shoppingCart.setProducts(shoppingCartToProductService.findCartProducts(shoppingCart));
    }

    public void entry() {
        System.out.println("Welcome to Customer Section , " + customer.getFirstName() + " " + customer.getLastName());
        while (true) {
            System.out.println("""
                    1-Buy Product
                    2-Shopping Cart Management
                    3-
                    4-View Orders
                    -View Profile
                    0-Exit""");
            String option = scanner.nextLine();
            if (option.equals("1")) {
                Category category = viewCategories();
                if (category != null) {
                    buyProduct(category);
                } else utilities.printRed("No product been added yet. Apologies.");

            } else if (option.equals("2")) {
                shoppingCartManagement();
            } else if (option.equals("3")) {

            } else if (option.equals("0")) {
                break;
            } else {
                utilities.printRed("Wrong Option.");
            }
        }
    }

    private Category viewCategories() {
        List<Category> rootCategories = categoryService.findAllRootCategories();
        utilities.iterateThrough(rootCategories);
        if (rootCategories.size() > 0) {
            while (true) {
                System.out.print("Choose a Category ID to continue: ");
                Integer rootCatId = utilities.intReceiver();
                Category rootCat = categoryService.find(rootCatId);
                if (rootCat != null && rootCategories.contains(rootCat)) {
                    while (true) {
                        System.out.print("Choose T/t to view all products in This Category or C/c to continue withing categories: ");
                        String tOrC = scanner.nextLine();
                        if (tOrC.toUpperCase(Locale.ROOT).equals("T")) {
                            return rootCat;
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
                                                return firstLayerCategory;
                                            } else if (secondLayerTOrC.toUpperCase(Locale.ROOT).equals("C")) {
                                                List<Category> secondLayerCategories = categoryService.findAllBySuper(firstLayerCategory);
                                                utilities.iterateThrough(secondLayerCategories);
                                                if (secondLayerCategories.size() > 0) {
                                                    while (true) {
                                                        System.out.println("Category ID: ");
                                                        Integer secondLayerCategoryID = utilities.intReceiver();
                                                        Category secondLayerCategory = categoryService.find(secondLayerCategoryID);
                                                        if (secondLayerCategory != null && secondLayerCategories.contains(secondLayerCategory)) {
                                                            return secondLayerCategory;
                                                        } else {
                                                            utilities.printRed("Wrong input");
                                                        }
                                                    }
                                                } else {
                                                    utilities.printYellow("Category has no further sub categories,you'll be shown products with this category.");
                                                    return firstLayerCategory;
                                                }
                                            } else utilities.printRed("Wrong input");
                                        }
                                    } else {
                                        utilities.printRed("Wrong input");
                                    }
                                }
                            } else {
                                utilities.printYellow("Category has no further sub categories,you'll be shown products with this category.");
                                return rootCat;
                            }
                        } else utilities.printRed("Wrong input.");
                    }
                } else utilities.printRed("Wrong input.");
            }
        } else {
            return null;
        }
    }

    private void buyProduct(Category category) {
        List<Integer> descendantCategoriesIDs = categoryService.findAllDescendants(category);
        HashMap<Product, Integer> products = new HashMap<>();
        for (Integer descendantCategoriesID : descendantCategoriesIDs) {
            products.putAll(productService.findAllByCategoryID(descendantCategoriesID));
        }
        products.forEach((product, quantity) -> utilities.printGreen("\n" + product + "\n In Stock: " + quantity, 400));

        while (true) {
            System.out.print("Product ID you want to add to your shopping cart: ");
            Integer productID = utilities.intReceiver();
            Product product = productService.find(productID);
            HashMap<Product, Integer> productWithQuantityInWareHouse = productService.findWithQuantity(product);
            Integer productWarehouseQuantity = productWithQuantityInWareHouse.get(product);
            HashMap<Product, Integer> productsInShoppingCart = shoppingCart.getProducts();
            if (product != null && products.containsKey(product)) {
                System.out.print("Quantity(In Stock: " + productWarehouseQuantity + "): ");
                Integer quantity = utilities.intReceiver();
                if (productWarehouseQuantity >= quantity) {
                    if (quantity > 0) {

                        if (shoppingCart.getProducts().containsKey(product)) {
                            Integer oldQuantity = shoppingCart.getProducts().get(product);
                            Integer newQuantity = oldQuantity + quantity;
                            productsInShoppingCart.put(product, newQuantity);
                            shoppingCart.setProducts(productsInShoppingCart);
                            shoppingCartToProductService.changeQuantity(shoppingCart, product, newQuantity);
                            productService.changeQuantity(product, productWarehouseQuantity - quantity);
                            utilities.printGreen("New amount added to product in your shopping cart.");
                        }

                        else {
                            productsInShoppingCart.put(product, quantity);
                            shoppingCart.setProducts(productsInShoppingCart);
                            shoppingCartToProductService.addProductToCart(shoppingCart, product, quantity);
                            productService.changeQuantity(product, productWarehouseQuantity - quantity);
                            utilities.printGreen("Product added to your shopping cart.");
                        }
                    }
                    else utilities.printRed("0 quantity not permitted.");
                    break;
                }
                else utilities.printRed("Quantity more than stock.");
            } else utilities.printRed("Wrong ID.");
        }
    }

    private void shoppingCartManagement() {
        while (true){
            System.out.println("""
                    1-View Shopping Cart
                    2-Remove From Shopping Cart
                    3-Check Out
                    4-Finalize Order
                    0-Exit""");
            String option = scanner.nextLine();
            if(option.equals("1")){
                utilities.printGreen(shoppingCart.toString());
                utilities.printGreen("Total Price: " + cartTotalPrice().toString());
            }
            else if (option.equals("2")) {
                System.out.print("Choose Product ID you want to remove: ");
                Integer productID = utilities.intReceiver();
                Product productToRemove = productService.find(productID);
                if(productToRemove != null && shoppingCart.getProducts().containsKey(productToRemove)){
                    Integer shoppingCartQuantity = shoppingCart.getProducts().get(productToRemove);
                    Integer warehouseQuantity = productService.findWithQuantity(productToRemove).get(productToRemove);
                    shoppingCartToProductService.deleteFromCart(shoppingCart,productToRemove);
                    Integer changeQtyOutput = productService.changeQuantity(productToRemove,warehouseQuantity + shoppingCartQuantity);
                    if(changeQtyOutput != null) {
                        utilities.printGreen("Item Successfully deleted from your shopping cart.");
                    } else utilities.printRed("Something went wrong.");
                } else utilities.printRed("Wrong Product ID!");
            }
            else if(option.equals("3")){
                utilities.printGreen("Total Price: " + cartTotalPrice().toString());
                System.out.println("Y/y to finalize - N/n to go back to shopping: ");
                String yn = scanner.nextLine();
                if(yn.toUpperCase(Locale.ROOT).equals("Y")){
                    if(customer.getBalance() >= cartTotalPrice()){
                        Order order = new Order(0,null,customer, OrderStatus.PENDING);
                        Integer newOrderID = orderService.registerOrder(order);
                        order = orderService.find(newOrderID);
                        OrderDetails orderDetails = new OrderDetails(order,shoppingCart.getProducts());
                        Map<Product,Integer> orderProducts = shoppingCart.getProducts();
                        shoppingCart.setProducts(new HashMap<>());
                        utilities.printGreen("New Order created with ID: " + newOrderID);
                    } else utilities.printRed("Your Account balance is low. Deposit to your account or remove product to check out.");
                } else if (yn.toUpperCase(Locale.ROOT).equals("N")) {
                    break;
                } else System.out.println("Wrong option.");
            }
            else if (option.equals("4")){

            }
            else if (option.equals("0")) {
                break;
            } else utilities.printRed("Wrong Option");
        }

    }

    private Double cartTotalPrice(){
        AtomicReference<Double> totalCartPrice = new AtomicReference<>(0.0);
        shoppingCart.getProducts().forEach(((product, quantity) ->
                totalCartPrice.updateAndGet(v -> v + product.getPrice() * quantity)));
        return totalCartPrice.get();
    }
}
