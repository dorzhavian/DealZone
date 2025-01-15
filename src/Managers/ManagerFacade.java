package Managers;
import Enums.Category;
import Enums.ExceptionsMessages;
import Models.*;

import java.util.*;

public class ManagerFacade {
    private final SellerManager sellerManager;
    private final BuyerManager buyerManager;
    private final ProductManager productManager;
    private static ManagerFacade instance;

    private static String input;
    private static String message;

    public static ManagerFacade getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new ManagerFacade();
        return instance;
    }

    private ManagerFacade() {
        sellerManager = SellerManager.getInstance();
        buyerManager = BuyerManager.getInstance();
        productManager = ProductManager.getInstance();
    }

    public void case1(InputHandler uI) {
        do {
            input = uI.getString("Enter username: (Enter -1 to return main menu)");
            if (input.equals("-1")) return;
            message = sellerManager.isExistSeller(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        input = uI.getString("Enter password: (Enter -1 to return main menu)");
        if (input.equals("-1")) return;
        sellerManager.addSeller(Factory.createSeller(username, input));
        System.out.println("Seller added successfully.");
    }

    public void case2(InputHandler uI) {
        do {
            input = uI.getString("Enter username: (Enter -1 to return main menu)");
            if (input.equals("-1")) return;
            message = buyerManager.isExistBuyer(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        String password = uI.getString("Enter password: (Enter -1 to return main menu)");
        if (password.equals("-1")) return;
        System.out.println("Enter your full address: ");
        String street= uI.getString("Street: (Enter -1 to return main menu)");
        if (street.equals("-1")) return;
        String houseNum = uI.getString("House number: (Enter -1 to return main menu)");
        if (houseNum.equals("-1")) return;
        String city = uI.getString("City: (Enter -1 to return main menu)");
        if (input.equals("-1")) return;
        String state = uI.getString("State: (Enter -1 to return main menu)");
        if (state.equals("-1")) return;
        Address address = Factory.createAddress(street, houseNum, city, state);
        buyerManager.addBuyer(Factory.createBuyer(username, password, address));
        System.out.println("Buyer added successfully.");
    }

    public void case3 (InputHandler uI) {
        double productPrice;
        if (sellerManager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot be proceed. return to Menu.");
            return;
        }
        int sellerIndex = chooseSeller(uI);
        if (sellerIndex == -1) return;
        do input = uI.getString("Enter product name to add: (Enter -1 to return main menu)");
        while (input.isEmpty());
        if (input.equals("-1")) return;
        String productName = input;
        do {
            productPrice = uI.getDouble("Enter product price: (Enter -1 to return main menu)");
            if (productPrice == -1) return;
            message = productManager.validPrice(productPrice);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        System.out.println(Categories.categoriesByNames());
        int categoryIndex;
        do {
            categoryIndex = uI.getInt("Choose category: (Enter -1 to return main menu)\n");
            if (categoryIndex == -1) return;
            message = productManager.validCategoryIndex(categoryIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        double specialPackagePrice = 0;
        do {
            input = uI.getString("This product have special package? YES / NO : (Enter -1 to return main menu) ");
            if (input.equals("-1")) return;
            if (input.equalsIgnoreCase("yes")) {
                do {
                    specialPackagePrice = uI.getDouble("Enter price for special package: (Enter -1 to return main menu)");
                    if (input.equals("-1")) return;
                    message = productManager.validPrice(specialPackagePrice);
                    if (message != null) {
                        System.out.println(message);
                    }
                } while (message != null);
                break;
            }
            if (!input.equalsIgnoreCase("no")) {
                System.out.println("Please enter YES / NO only !");
            }
        } while (!input.equalsIgnoreCase("no"));
        makeProductToSeller(sellerIndex, productName, productPrice, Category.values()[categoryIndex - 1], specialPackagePrice);
        System.out.println("Product added successfully.");
    }

    public void case4 (InputHandler uI) {
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        if (sellerManager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer(uI);
        if (buyerIndex == -1) return;
        int sellerIndex = chooseSeller(uI);
        if (sellerIndex == -1) return;
        if (sellerManager.getSellers()[sellerIndex].getNumOfProducts() == 0) {
            System.out.println("This seller haven't products yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println(sellerManager.getSellers()[sellerIndex].toString());
        int productIndex;
        do {
            productIndex = uI.getInt("Enter product's number for adding to your cart: (Enter -1 to return main menu)");
            if (input.equals("-1")) return;
            message = validProductIndex(sellerIndex, productIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        makeProductToBuyer(buyerIndex,sellerIndex,productIndex - 1);
        System.out.println("Product added successfully to cart.");
    }

    public void case5 (InputHandler uI) {
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer(uI);
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

    public void case9 (InputHandler uI) {
        int choice = 0;
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer(uI);
        if (buyerIndex == -1) return;
        if (buyerManager.getBuyers()[buyerIndex].getHistoryCartsNum() == 0) {
            System.out.println("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
            return;
        }
        System.out.println(buyerManager.getBuyers()[buyerIndex].toString());
        System.out.println("Please choose cart number from history carts:");
        do {
            choice = uI.getInt("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
            if (choice == -1) return;
            message = isValidHistoryCartIndex(choice, buyerIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        int historyCartIndex = Integer.parseInt(input);
        buyerManager.replaceCarts(historyCartIndex - 1, buyerIndex);
        System.out.println("Your current cart update successfully.");
    }

    public void hardcoded() {

        int sellerIndex = sellerManager.getNumberOfSellers();
        int buyerIndex = buyerManager.getNumberOfBuyers();

        // Adding 5 sellers with real names and realistic passwords
        sellerManager.addSeller(Factory.createSeller("Jack", "J@ck2024!"));
        sellerManager.addSeller(Factory.createSeller("Dor", "DorPass@123"));
        sellerManager.addSeller(Factory.createSeller("Tal", "Tal2024Secure"));
        sellerManager.addSeller(Factory.createSeller("Maya", "Maya@Home2024"));
        sellerManager.addSeller(Factory.createSeller("Avi", "AviPassword#45"));

// Adding 5 buyers with usernames, realistic passwords, and addresses
        buyerManager.addBuyer(Factory.createBuyer("Jack", "J@ck2024!", Factory.createAddress("Main St", "123", "Los Angeles", "California")));
        buyerManager.addBuyer(Factory.createBuyer("Dor", "DorPass@123", Factory.createAddress("Oak Rd", "456", "San Francisco", "California")));
        buyerManager.addBuyer(Factory.createBuyer("Tal", "Tal2024Secure", Factory.createAddress("Pine Ave", "789", "New York", "New York")));
        buyerManager.addBuyer(Factory.createBuyer("Maya", "Maya@Home2024", Factory.createAddress("Maple St", "101", "Chicago", "Illinois")));
        buyerManager.addBuyer(Factory.createBuyer("Avi", "AviPassword#45", Factory.createAddress("Cedar Blvd", "202", "Houston", "Texas")));

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

    public void case101(InputHandler uI) {
        if (productManager.getNumberOfProducts() != 0) {
            Map<String, Integer> map = productManager.productsToLinkedMap();
            input = uI.getString("Please enter a string: (Enter -1 to return main menu)").toLowerCase();
            if (input.equals("-1")) return;
            System.out.printf("the number of times that " + input + " appears in the OG ARRAY is %d\n" , map.get(input) == null ? 0 : map.get(input));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case102(){
        if(productManager.getNumberOfProducts() != 0) {
            List<String> setList = new ArrayList<>(productManager.productsNameToLinkedSet());
            List<String> doubleNames = new ArrayList<>();
            ListIterator<String> iterator = setList.listIterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                doubleNames.add(key);
                doubleNames.add(key);
            }
            ListIterator<String> doubleIterator = doubleNames.listIterator(doubleNames.size());
            while(doubleIterator.hasPrevious()){
                System.out.println(doubleIterator.previous());
            }
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

    public int chooseSeller (InputHandler uI) {
        System.out.println(sellerManager.sellersNames());
        String input;
        while (true) {
            input = uI.getString("Please choose seller from the list above: (Enter -1 to return main menu)");
            if (input.equals("-1")) return -1;
            String message = sellerManager.chooseValidSeller(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
    }

    public int chooseBuyer (InputHandler uI) {
        System.out.println(buyerManager.buyersNames());
        String input;
        while (true) {
            input = uI.getString("Please choose buyer from the list above: (Enter -1 to return main menu)");
            if (input.equals("-1")) return -1;
            String message = buyerManager.chooseValidBuyer(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
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
            p1 = Factory.createProductSpecialPackageForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], ((ProductSpecialPackage) sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice());
        } else {
            p1 = Factory.createProductForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]);
        }
        buyerManager.addProductToBuyer(p1, buyerIndex);
    }

    public void makeProductToSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice) {
        Product p1;
        if (specialPackagePrice == 0) {
            p1 = Factory.createProduct(productName, productPrice, c);
        } else {
            p1 = Factory.createProductSpecialPackage(productName, productPrice, c, specialPackagePrice);
        }
        sellerManager.addProductToSeller(p1, sellerIndex);
        productManager.addToCategoryArray(p1);
        productManager.addProductName(p1);
    }

}


