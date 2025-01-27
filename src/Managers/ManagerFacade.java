package Managers;
import Enums.Category;
import Enums.ExceptionsMessages;
import Factories.ProductFactory;
import Factories.UserFactory;
import Models.*;
import Actions.*;

import java.util.*;

public class ManagerFacade {
    private final ISellerManager sellerManager;
    private final IBuyerManager buyerManager;
    private final ProductManager productManager;
    private final ActionServer  actionServer;
    private final Action1 action1;
    private final Action2 action2;
    private static ManagerFacade instance;

    private static String message;

    public static ManagerFacade getInstance() {       // SINGLETON !!!!!!!
        if (instance == null)
            instance = new ManagerFacade();
        return instance;
    }

    private ManagerFacade() {
        sellerManager = SellerManager.getInstance();
        buyerManager = BuyerManager.getInstance();
        productManager = ProductManager.getInstance();
        actionServer = new ActionServer();
        action1 = new Action1();
        action2 = new Action2();
    }

    public void case1() {
        String username, password;
        do {
            username = UserInput.getString("Enter username:");
            if (username == null) return;
            message = sellerManager.isExistSeller(username);
            if (message != null) System.out.println(message);
        } while (message != null);
        password = UserInput.getString("Enter password:");
        if (password == null) return;
        sellerManager.addSeller(UserFactory.createSeller(username, password));
        System.out.println("Seller added successfully.");
    }

    public void case2() {
        String username, password, houseNum, city, state;
        do {
            username = UserInput.getString("Enter username:");
            if (username == null) return;
            message = buyerManager.isExistBuyer(username);
            if (message != null) System.out.println(message);
        } while (message != null);

        password = UserInput.getString("Enter password:");
        if (password == null ) return;

        System.out.println("Enter your full address:");
        String street = UserInput.getString("Street:");
        if (street == null) return;

        houseNum = UserInput.getString("House number:");
        if (houseNum == null) return;

        city = UserInput.getString("City:");
        if (city == null) return;

        state = UserInput.getString("State:");
        if (state == null) return;

        Address address = UserFactory.createAddress(street, houseNum, city, state);
        buyerManager.addBuyer(UserFactory.createBuyer(username, password, address));
        System.out.println("Buyer added successfully.");
    }

    public void case3() {
        String productName;
        double productPrice,specialPackagePrice = 0;
        int categoryIndex;

        if (sellerManager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot proceed. Returning to Menu.");
            return;
        }

        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;

        productName = UserInput.getString("Enter product name:");
        if (productName == null) return;

        do {
            productPrice = UserInput.getDouble("Enter product price:");
            if (productPrice == -1) return;
            message = productManager.validPrice(productPrice);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);

        System.out.println(Categories.categoriesByNames());

        do {
            categoryIndex = UserInput.getInt("Choose category:");
            if (categoryIndex == -1) return;
            message = productManager.validCategoryIndex(categoryIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);

        if (UserInput.getYesNo("Does this product have a special package? (Y/y) ")) {
            do {
                specialPackagePrice = UserInput.getDouble("Enter price for special package: ");
                if (specialPackagePrice == -1) return;
                message = productManager.validPrice(specialPackagePrice);
                if (message != null) {
                    System.out.println(message);
                }
            } while (message != null);
        }
        makeProductToSeller(sellerIndex, productName, productPrice, Category.values()[categoryIndex - 1], specialPackagePrice);
        System.out.println("Product added successfully.");
    }

    public void case4() {
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot proceed. Returning to Menu.");
            return;
        }
        if (sellerManager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot proceed. Returning to Menu.");
            return;
        }

        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;

        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;

        if (sellerManager.getSellers()[sellerIndex].getNumOfProducts() == 0) {
            System.out.println("This seller has no products yet, cannot proceed. Returning to Menu.");
            return;
        }

        System.out.println(sellerManager.getSellers()[sellerIndex].toString());

        int productIndex;
        do {
            productIndex = UserInput.getInt("Enter product's number to add to your cart: ");
            if (productIndex == -1) return;
            message = validProductIndex(sellerIndex, productIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);

        makeProductToBuyer(buyerIndex, sellerIndex, productIndex - 1);
        System.out.println("Product added successfully to cart.");
    }

    public void case5 () {
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        System.out.println(buyerManager.pay(buyerIndex));
    }

    public void case6(){
        System.out.println(buyerManager.buyersInfo());
    }

    public void case7() {
        System.out.println((sellerManager.sellersInfo()));
    }

    public void case8(){
        System.out.println(productsByCategory());
    }

    public void case9 () {
        int choice;
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        if (buyerManager.getBuyers()[buyerIndex].getHistoryCartsNum() == 0) {
            System.out.println("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
            return;
        }
        System.out.println(buyerManager.getBuyers()[buyerIndex].toString());
        System.out.println("Please choose cart number from history carts:");
        do {
            choice = UserInput.getInt("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
            if (choice == -1) return;
            message = isValidHistoryCartIndex(choice, buyerIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        buyerManager.replaceCarts(choice - 1, buyerIndex);
        System.out.println("Your current cart update successfully.");
    }

    public void hardcoded() {

        int sellerIndex = sellerManager.getNumberOfSellers();
        int buyerIndex = buyerManager.getNumberOfBuyers();

        // Adding 5 sellers with real names and realistic passwords
        sellerManager.addSeller(UserFactory.createSeller("Jack", "J@ck2024!"));
        sellerManager.addSeller(UserFactory.createSeller("Dor", "DorPass@123"));
        sellerManager.addSeller(UserFactory.createSeller("Tal", "Tal2024Secure"));
        sellerManager.addSeller(UserFactory.createSeller("Maya", "Maya@Home2024"));
        sellerManager.addSeller(UserFactory.createSeller("Avi", "AviPassword#45"));

        // Adding 5 buyers with usernames, realistic passwords, and addresses
        buyerManager.addBuyer(UserFactory.createBuyer("Jack", "J@ck2024!", UserFactory.createAddress("Main St", "123", "Los Angeles", "California")));
        buyerManager.addBuyer(UserFactory.createBuyer("Dor", "DorPass@123", UserFactory.createAddress("Oak Rd", "456", "San Francisco", "California")));
        buyerManager.addBuyer(UserFactory.createBuyer("Tal", "Tal2024Secure", UserFactory.createAddress("Pine Ave", "789", "New York", "New York")));
        buyerManager.addBuyer(UserFactory.createBuyer("Maya", "Maya@Home2024", UserFactory.createAddress("Maple St", "101", "Chicago", "Illinois")));
        buyerManager.addBuyer(UserFactory.createBuyer("Avi", "AviPassword#45", UserFactory.createAddress("Cedar Blvd", "202", "Houston", "Texas")));

        // Adding products to sellers

        // Seller 0 products
        makeProductToSeller(sellerIndex, "TV", 325.00, Category.ELECTRONIC, 15.00);
        makeProductToSeller(sellerIndex, "Shirt", 50.00, Category.CLOTHES, 0);

        // Seller 1 products
        makeProductToSeller(sellerIndex + 1, "Laptop", 950.00, Category.ELECTRONIC, 50.00);
        makeProductToSeller(sellerIndex + 1, "Jacket", 75.00, Category.CLOTHES, 0);
        makeProductToSeller(sellerIndex + 1, "Desk Lamp", 45.00, Category.OFFICE, 5.00);

        // Seller 2 products
        makeProductToSeller(sellerIndex + 2, "TV", 330.00, Category.ELECTRONIC, 18.00);
        makeProductToSeller(sellerIndex + 2, "Hat", 60.00, Category.CLOTHES, 0);

        // Seller 3 products
        makeProductToSeller(sellerIndex + 3, "Headphones", 120.00, Category.ELECTRONIC, 0);
        makeProductToSeller(sellerIndex + 3, "Sweater", 80.00, Category.CLOTHES, 15.00);
        makeProductToSeller(sellerIndex + 3, "desk lamP", 130.00, Category.OFFICE, 0);

        // Seller 4 products
        makeProductToSeller(sellerIndex + 4, "Smartwatch", 220.00, Category.ELECTRONIC, 30.00);
        makeProductToSeller(sellerIndex + 4, "hAt", 25.00, Category.CLOTHES, 5.00);
        makeProductToSeller(sellerIndex + 4, "Office Organizer", 40.00, Category.OFFICE, 0);
        makeProductToSeller(sellerIndex + 4, "Board Game", 28.00, Category.CHILDREN, 10.00);

        // Adding products to carts

        // Buyer 0
        makeProductToBuyer(buyerIndex, sellerIndex, 0);  // Buyer 0 buys TV from Seller 0
        makeProductToBuyer(buyerIndex, sellerIndex + 1, 1);  // Buyer 0 buys Jacket from Seller 1

        // Buyer 1
        makeProductToBuyer(buyerIndex + 1, sellerIndex + 2, 0);  // Buyer 1 buys TV from Seller 2
        makeProductToBuyer(buyerIndex + 1, sellerIndex + 3, 1);  // Buyer 1 buys Sweater from Seller 3

        // Buyer 2
        makeProductToBuyer(buyerIndex + 2, sellerIndex + 4, 0);  // Buyer 2 buys Smartwatch from Seller 4
        makeProductToBuyer(buyerIndex + 2, sellerIndex, 1);  // Buyer 2 buys Shirt from Seller 0

        // Buyer 3
        makeProductToBuyer(buyerIndex + 3, sellerIndex + 1, 2);  // Buyer 3 buys Desk Lamp from Seller 1
        makeProductToBuyer(buyerIndex + 3, sellerIndex + 4, 1);  // Buyer 3 buys Hat from Seller 4

        // Buyer 4
        makeProductToBuyer(buyerIndex + 4, sellerIndex + 3, 0);  // Buyer 4 buys Headphones from Seller 3
        makeProductToBuyer(buyerIndex + 4, sellerIndex + 2, 1);  // Buyer 4 buys Hat from Seller 2

        System.out.println("Hardcoded added successfully!");
    }

    public void case99()
    {
        productManager.printProductsName();
    }

    public void case100(){
        if(productManager.getNumberOfProducts() != 0){
            Map<String,Integer> map = productManager.productsToLinkedMap();
            map.forEach((key, value) -> System.out.println(key + ".........." + value));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case101() {
        String input;
        if (productManager.getNumberOfProducts() != 0) {
            Map<String, Integer> map = productManager.productsToLinkedMap();
            input = UserInput.getString("Please enter a string: (Enter -1 to return main menu)");
            if (input == null) return;
            System.out.printf("the number of times that " + input.toLowerCase() + " appears in the OG ARRAY is %d\n" , map.get(input.toLowerCase()) == null ? 0 : map.get(input));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case102(){
        //TASK 1:

        if(productManager.getNumberOfProducts() != 0) {
            List<String> productNameList = new ArrayList<>(productManager.productsNameToLinkedSet());
            List<String> doubleNames = new ArrayList<>();
            ListIterator<String> iterator = productNameList.listIterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                doubleNames.add(key);
                doubleNames.add(key);
            }
            ListIterator<String> doubleIterator = doubleNames.listIterator(doubleNames.size());
            while(doubleIterator.hasPrevious()){
                System.out.println(doubleIterator.previous());
            }

            // TASK2 :

            if (!UserInput.getYesNo("Do you want to see the output of my self-implemented iterators (Y/y or any other key to skip):"))
                return;
            productManager.setSetList(productNameList);
            productManager.setDoubleNames(doubleNames);
            actionServer.attach(action1);
            actionServer.attach(action2);
            System.out.println("\nMy custom name ArrayList iterator: ");
            Iterator<String> myIterator = productManager.myIterator();
            while(myIterator.hasNext())
                System.out.println(myIterator.next());
            actionServer.setMsg("My Iterator ended!");
            actionServer.myNotify();
            System.out.println("\nMy custom name ArrayList List iterator (Start --> End): ");
            ListIterator<String> myListIterator = productManager.myListIterator();
            while(myListIterator.hasNext()){
                System.out.println(myListIterator.next());
            }
            actionServer.setMsg("My ListIterator ended!");
            actionServer.myNotify();
            System.out.println("\nMy custom name ArrayList List iterator (End --> Start): ");
            while(myListIterator.hasPrevious()){
                System.out.println(myListIterator.previous());
            }
            actionServer.setMsg("My ListIterator ended!");
            actionServer.myNotify();


        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case103() {
        if (productManager.getNumberOfProducts() != 0) {
            Set<?> productsSet = productManager.productsToTree();
            Iterator<?> productsIterator = productsSet.iterator();
            while (productsIterator.hasNext()) {
                System.out.println(productsIterator.next().toString().toUpperCase());
            }
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public int chooseSeller() {
        System.out.println(sellerManager.sellersNames());
        int sellerIndex;
        do {
            sellerIndex = UserInput.getInt("Please choose seller from the list above:");
            if (sellerIndex == -1) return -1;
            message = sellerManager.chooseValidSeller(sellerIndex);
            if (message != null) System.out.println(message);
        } while (message != null);
        return sellerIndex - 1;
    }

    public int chooseBuyer() {
        System.out.println(buyerManager.buyersNames());
        int buyerIndex;
        do {
            buyerIndex = UserInput.getInt("Please choose buyer from the list above:");
            if (buyerIndex == -1) return -1;
            message = buyerManager.chooseValidBuyer(buyerIndex);
            if (message != null) System.out.println(message);
        } while (message != null);
        return buyerIndex - 1;
    }

    public String validProductIndex(int sellerIndex, int productIndexInput) {
        try {
            if (productIndexInput <= 0 || productIndexInput > sellerManager.getSellers()[sellerIndex].getNumOfProducts()) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_PRODUCT_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String isValidHistoryCartIndex(int indexCartInput, int buyerIndex) {
        try {
            if (buyerManager.getBuyers()[buyerIndex].getHistoryCartsNum() < indexCartInput || indexCartInput <= 0) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_HISTORY_CART_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String productsByCategory() {
        if (sellerManager.getNumberOfSellers() == 0) {
            return "\nHaven't sellers yet - no products available, cannot be proceed. return to Menu.";
        }
        return productManager.getCategoriesArrays().toString();
    }

    public void makeProductToBuyer(int buyerIndex, int sellerIndex, int productIndex) {
        Product p1;
        if (productManager.isSpecialPackageProduct(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]))
        {
            p1 = ProductFactory.createProductSpecialPackageForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], ((ProductSpecialPackage) sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice());
        } else {
            p1 = ProductFactory.createProductForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]);
        }
        buyerManager.addProductToBuyer(p1, buyerIndex);
    }

    public void makeProductToSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice) {
        Product p1;
        if (specialPackagePrice == 0) {
            p1 = ProductFactory.createProduct(productName, productPrice, c);
        } else {
            p1 = ProductFactory.createProductSpecialPackage(productName, productPrice, c, specialPackagePrice);
        }
        sellerManager.addProductToSeller(p1, sellerIndex);
        productManager.addToCategoryArray(p1);
        productManager.addProductName(p1);
    }

}


