package Managers;

import Comparators.CompareBuyersByName;
import Comparators.CompareSellersByProductsNumber;
import Enums.Category;
import Enums.ExceptionsMessages;
import Exceptions.*;
import Models.*;

import java.util.*;

public class ManagerFacade implements Manageable {
    private static ManagerFacade instance;
    private final int SIZE_INCREASE = 2;
    private Seller[] sellers;
    private Product[] allProducts;
    private int numberOfSellers;
    private Buyer[] buyers;
    private int numberOfBuyers;
    private int numberOfProducts;
    private final Categories categoriesArrays;
    private final Comparator<Seller> comparatorSeller;
    private final Comparator<Buyer> comparatorBuyer;
    private static String input;
    private static String message;

    public static ManagerFacade getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new ManagerFacade();
        return instance;
    }

    private ManagerFacade() {                                          // PRIVATE CONSTRUCTOR FOR SINGLETON
        buyers = new Buyer[0];
        sellers = new Seller[0];
        allProducts = new Product[0];
        categoriesArrays = new Categories();
        comparatorSeller = new CompareSellersByProductsNumber();
        comparatorBuyer = new CompareBuyersByName();
    }

    public void case1(InputHandler uI) {
        do {
            input = uI.getString("Enter username: (Enter -1 to return main menu)");
            if (input.equals("-1")) return;
            message = isExistSeller(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        input = uI.getString("Enter password: (Enter -1 to return main menu)");
        if (input.equals("-1")) return;
        addSeller(username, input);
        System.out.println("Seller added successfully.");
    }

    public void case2(InputHandler uI) {
        do {
            input = uI.getString("Enter username: (Enter -1 to return main menu)");
            if (input.equals("-1")) return;
            message = isExistBuyer(input);
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
        addBuyer(username, password, address);
        System.out.println("Buyer added successfully.");
    }

    public void case3 (InputHandler uI) {
        double productPrice;
        if (numberOfSellers == 0) {
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
            message = validPrice(productPrice);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        System.out.println(Categories.categoriesByNames());
        int categoryIndex;
        do {
            categoryIndex = uI.getInt("Choose category: (Enter -1 to return main menu)\n");
            if (categoryIndex == -1) return;
            message = validCategoryIndex(categoryIndex);
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
                    message = validPrice(specialPackagePrice);
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
        addProductSeller(sellerIndex, productName, productPrice, Category.values()[categoryIndex - 1], specialPackagePrice);
        System.out.println("Product added successfully.");
    }

    public void case4 (InputHandler uI) {
        if (numberOfBuyers == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        if (numberOfSellers == 0) {
            System.out.println("Haven't sellers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer(uI);
        if (buyerIndex == -1) return;
        int sellerIndex = chooseSeller(uI);
        if (sellerIndex == -1) return;
        if (sellers[sellerIndex].getNumOfProducts() == 0) {
            System.out.println("This seller haven't products yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println(sellers[sellerIndex].toString());
        int productIndex;
        do {
            productIndex = uI.getInt("Enter product's number for adding to your cart: (Enter -1 to return main menu)");
            if (input.equals("-1")) return;
            message = validProductIndex(sellerIndex, productIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        addProductBuyer(buyerIndex,sellerIndex,productIndex - 1);
        System.out.println("Product added successfully to cart.");
    }

    public void case5 (InputHandler uI) {
        if (numberOfBuyers == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer(uI);
        if (buyerIndex == -1) return;
        System.out.println(pay(buyerIndex));
    }

    public void case9 (InputHandler uI) {
        int choice = 0;
        if (numberOfBuyers == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer(uI);
        if (buyerIndex == -1) return;
        if (buyers[buyerIndex].getHistoryCartsNum() == 0) {
            System.out.println("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
            return;
        }
        System.out.println(buyers[buyerIndex].toString());
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
        replaceCarts(historyCartIndex - 1, buyerIndex);
        System.out.println("Your current cart update successfully.");
    }

    public void hardcoded() {

        int sellerIndex = numberOfSellers;
        int buyerIndex = numberOfBuyers;

        // Adding 5 sellers with real names and realistic passwords
        addSeller("Jack", "J@ck2024!");
        addSeller("Dor", "DorPass@123");
        addSeller("Tal", "Tal2024Secure");
        addSeller("Maya", "Maya@Home2024");
        addSeller("Avi", "AviPassword#45");

// Adding 5 buyers with usernames, realistic passwords, and addresses
        addBuyer("Jack", "J@ck2024!", Factory.createAddress("Main St", "123", "Los Angeles", "California"));
        addBuyer("Dor", "DorPass@123", Factory.createAddress("Oak Rd", "456", "San Francisco", "California"));
        addBuyer("Tal", "Tal2024Secure", Factory.createAddress("Pine Ave", "789", "New York", "New York"));
        addBuyer("Maya", "Maya@Home2024", Factory.createAddress("Maple St", "101", "Chicago", "Illinois"));
        addBuyer("Avi", "AviPassword#45", Factory.createAddress("Cedar Blvd", "202", "Houston", "Texas"));

// Adding products to sellers

// Seller 0 products
        addProductSeller(sellerIndex, "TV", 325.00, Category.ELECTRONIC, 15.00);
        addProductSeller(sellerIndex, "Shirt", 50.00, Category.CLOTHES, 0);

// Seller 1 products
        addProductSeller(sellerIndex + 1, "Laptop", 950.00, Category.ELECTRONIC, 50.00);
        addProductSeller(sellerIndex + 1, "Jacket", 75.00, Category.CLOTHES, 0);
        addProductSeller(sellerIndex + 1, "Desk Lamp", 45.00, Category.OFFICE, 5.00);

// Seller 2 products
        addProductSeller(sellerIndex + 2, "TV", 330.00, Category.ELECTRONIC, 18.00);
        addProductSeller(sellerIndex + 2, "Hat", 60.00, Category.CLOTHES, 0);

// Seller 3 products
        addProductSeller(sellerIndex + 3, "Headphones", 120.00, Category.ELECTRONIC, 0);
        addProductSeller(sellerIndex + 3, "Sweater", 80.00, Category.CLOTHES, 15.00);
        addProductSeller(sellerIndex + 3, "desk lamP", 130.00, Category.OFFICE, 0);

// Seller 4 products
        addProductSeller(sellerIndex + 4, "Smartwatch", 220.00, Category.ELECTRONIC, 30.00);
        addProductSeller(sellerIndex + 4, "hAt", 25.00, Category.CLOTHES, 5.00);
        addProductSeller(sellerIndex + 4, "Office Organizer", 40.00, Category.OFFICE, 0);
        addProductSeller(sellerIndex + 4, "Board Game", 28.00, Category.CHILDREN, 10.00);

// Adding products to carts

// Buyer 0
        addProductBuyer(buyerIndex, sellerIndex, 0);  // Buyer 0 buys TV from Seller 0
        addProductBuyer(buyerIndex, sellerIndex + 1, 1);  // Buyer 0 buys Jacket from Seller 1

// Buyer 1
        addProductBuyer(buyerIndex + 1, sellerIndex + 2, 0);  // Buyer 1 buys TV from Seller 2
        addProductBuyer(buyerIndex + 1, sellerIndex + 3, 1);  // Buyer 1 buys Sweater from Seller 3

// Buyer 2
        addProductBuyer(buyerIndex + 2, sellerIndex + 4, 0);  // Buyer 2 buys Smartwatch from Seller 4
        addProductBuyer(buyerIndex + 2, sellerIndex, 1);  // Buyer 2 buys Shirt from Seller 0

// Buyer 3
        addProductBuyer(buyerIndex + 3, sellerIndex + 1, 2);  // Buyer 3 buys Desk Lamp from Seller 1
        addProductBuyer(buyerIndex + 3, sellerIndex + 4, 1);  // Buyer 3 buys Hat from Seller 4

// Buyer 4
        addProductBuyer(buyerIndex + 4, sellerIndex + 3, 0);  // Buyer 4 buys Headphones from Seller 3
        addProductBuyer(buyerIndex + 4, sellerIndex + 2, 1);  // Buyer 4 buys Hat from Seller 2

        System.out.println("Hardcoded added successfully!");
    }

    public void case100(){
        if(numberOfProducts != 0){
            Map<String,Integer> map = productsToLinkedMap();
            map.forEach((key, value) -> System.out.println(key + ".........." + value));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case101(InputHandler uI) {
        if (numberOfProducts != 0) {
            Map<String, Integer> map = productsToLinkedMap();
            input = uI.getString("Please enter a string: (Enter -1 to return main menu)").toLowerCase();
            if (input.equals("-1")) return;
            System.out.printf("the number of times that " + input + " appears in the OG ARRAY is %d\n" , map.get(input) == null ? 0 : map.get(input));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case102(){
        if(numberOfProducts != 0) {
            List<String> setList = new ArrayList<>(productsNameToLinkedSet());
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
        if (numberOfProducts != 0) {
            Set<?> productsSet = productsToTree();
            Iterator<?> productsIterator = productsSet.iterator();
            while (productsIterator.hasNext()) {
                System.out.println(productsIterator.next().toString().toUpperCase());
            }
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public int chooseBuyer (InputHandler uI) {
        System.out.println(buyersNames());
        while (true) {
            input = uI.getString("Please choose buyer from the list above: (Enter -1 to return main menu)");
            if (input.equals("-1")) return -1;
            message = chooseValidBuyer(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
    }

    public int chooseSeller (InputHandler uI) {
        System.out.println(sellersNames());
        while (true) {
            input = uI.getString("Please choose seller from the list above: (Enter -1 to return main menu)");
            if (input.equals("-1")) return -1;
            message = chooseValidSeller(input);
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
            if (productIndexInput <= 0 || productIndexInput > sellers[sellerIndex].getNumOfProducts()) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_PRODUCT_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String validPrice(double priceInput) {
        try {
            if (priceInput <= 0) throw new InputMismatchException(ExceptionsMessages.INVALID_PRICE_VALUE.getExceptionMessage());
        } catch (NullPointerException e){
            return ExceptionsMessages.PRICE_EMPTY.getExceptionMessage();
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_PRICE_INPUT.getExceptionMessage();
        } catch (InputMismatchException e) {
            return e.getMessage();
        }
        return null;
    }

    public String validCategoryIndex(int categoryInput) {
        try {
            if (categoryInput <= 0 || categoryInput > Category.values().length) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_CATEGORY_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String isExistSeller (String name) {
        for (int i = 0; i < numberOfSellers; i++) {
            if (sellers[i].getUserName().equalsIgnoreCase(name)) return "Seller name already EXIST, please try again!";
        }
        return null;
    }

    public String isExistBuyer (String name) {
        for (int i = 0; i < numberOfBuyers; i++) {
            if (buyers[i].getUserName().equalsIgnoreCase(name)) return "Buyer name already EXIST, please try again!";
        }
        return null;
    }

    public String isValidHistoryCartIndex(int indexCartInput, int buyerIndex) {
        try {
            if (buyers[buyerIndex].getHistoryCartsNum() < indexCartInput || indexCartInput <= 0) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_HISTORY_CART_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }
    
    public String chooseValidSeller(String indexInput) {
        try {
            int index = Integer.parseInt(indexInput);
            if (index > numberOfSellers || index <= 0) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_SELLER_INDEX.getExceptionMessage());
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        }
        return null;
    }
    
    public String chooseValidBuyer(String indexInput) {
        try {
            int index = Integer.parseInt(indexInput);
            if (index > numberOfBuyers || index <= 0) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_BUYER_INDEX.getExceptionMessage());
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        }
        return null;
    }

    public void addProductName(Product p){
        if (allProducts.length == numberOfProducts) {
            if (allProducts.length == 0) {
                allProducts = Arrays.copyOf(allProducts, 1);
            }
            allProducts = Arrays.copyOf(allProducts, allProducts.length * SIZE_INCREASE);
        }
        allProducts[numberOfProducts++] = p;

    }

    public void addSeller(String username, String password) {
        Seller seller = Factory.createSeller(username, password);
        if (sellers.length == numberOfSellers) {
            if (sellers.length == 0) {
                sellers = Arrays.copyOf(sellers, 1);
            }
            sellers = Arrays.copyOf(sellers, sellers.length * SIZE_INCREASE);
        }
        sellers[numberOfSellers++] = seller;
    }

    public void addBuyer(String username, String password, Address address) {
        Buyer buyer = Factory.createBuyer(username, password, address);
        if (buyers.length == numberOfBuyers) {
            if (buyers.length == 0) {
                buyers = Arrays.copyOf(buyers, 1);
            }
            buyers = Arrays.copyOf(buyers, buyers.length * SIZE_INCREASE);
        }
        buyers[numberOfBuyers++] = buyer;
    }

    public String sellersInfo() {
        if (numberOfSellers == 0) {
            return "\nHaven't sellers yet, cannot be proceed. return to Menu.";
        }
        StringBuilder sb = new StringBuilder("\nSellers info:\n--------------\n");
        Arrays.sort(sellers, 0, numberOfSellers, comparatorSeller);
        for (int i = 0; i < numberOfSellers; i++) {
            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append(":");
            sb.append(sellers[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public String buyersInfo() {
        if (numberOfBuyers == 0) {
            return "\nHaven't buyers yet, cannot be proceed. return to Menu.";
        }
        StringBuilder sb = new StringBuilder("\nBuyers info:\n--------------\n");
        Arrays.sort(buyers, 0, numberOfBuyers, comparatorBuyer);
        for (int i = 0; i < numberOfBuyers; i++) {
            sb.append(i + 1).append(") ");
            sb.append(buyers[i].toString());
        }
        return sb.toString();
    }

    public String productsByCategory() {
        if (numberOfSellers == 0) {
            return "\nHaven't sellers yet - no products available, cannot be proceed. return to Menu.";
        }
        return categoriesArrays.toString();
    }

    public void printProductsName() {
        if (numberOfProducts != 0) {
            for (int i = 0; i < numberOfProducts; i++)
                System.out.println(allProducts[i].getProductName());
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public String sellersNames() {
        StringBuilder sb = new StringBuilder("Seller's:\n--------------\n");
        for (int i = 0; i < numberOfSellers; i++) {
            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append("\n");
        }
        return sb.toString();
    }
    
    public String buyersNames() {
        StringBuilder sb = new StringBuilder("Buyer's:\n--------------\n");
        for (int i = 0; i < numberOfBuyers; i++) {
            sb.append(i + 1).append(") ").append(buyers[i].getUserName()).append("\n");
        }
        return sb.toString();
    }

    public void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex) {
        Product p1;
        if (sellers[sellerIndex].getProducts()[productIndex] instanceof ProductSpecialPackage) {
            p1 = Factory.createProductSpecialPackageForBuyer(sellers[sellerIndex].getProducts()[productIndex], ((ProductSpecialPackage) sellers[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice());
        } else {
            p1 = Factory.createProductForBuyer(sellers[sellerIndex].getProducts()[productIndex]);
        }
        buyers[buyerIndex].getCurrentCart().addProductToCart(p1);
    }

    public void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice) {
        Product p1;
        if (specialPackagePrice == 0) {
            p1 = Factory.createProduct(productName, productPrice, c);
        } else {
            p1 = Factory.createProductSpecialPackage(productName, productPrice, c, specialPackagePrice);
        }
        sellers[sellerIndex].addProduct(p1);
        addToCategoryArray(p1);
        addProductName(p1);
    }

    public void addToCategoryArray(Product p) {
        switch (p.getCategory()) {
            case ELECTRONIC:
                categoriesArrays.addElectronic(p);
                break;
            case CHILDREN:
                categoriesArrays.addChild(p);
                break;
            case CLOTHES:
                categoriesArrays.addClothes(p);
                break;
            case OFFICE:
                categoriesArrays.addOffice(p);
                break;
            default:
                break;
        }
    }

    public String pay(int buyerIndex) {
        try {
            if (buyers[buyerIndex].getCurrentCart().getNumOfProducts() == 0) throw new EmptyCartPayException(buyers[buyerIndex].getUserName());
        } catch (EmptyCartPayException e) {
            return e.getMessage();
        }
        buyers[buyerIndex].payAndMakeHistoryCart();
        return """
                 ____   _ __   ____  __ _____ _   _ _____                              \s
                |  _ \\ / \\\\ \\ / /  \\/  | ____| \\ | |_   _|                             \s
                | |_) / _ \\\\ V /| |\\/| |  _| |  \\| | | |                               \s
                |  __/ ___ \\| | | |  | | |___| |\\  | | |                               \s
                |_| /_/   \\_\\_|_|_|_ |_|_____|_|_\\_|_|_|  _____ ____  _____ _   _ _    \s
                            / ___|| | | |/ ___/ ___/ ___|| ____/ ___||  ___| | | | |   \s
                            \\___ \\| | | | |   \\___ \\___ \\|  _| \\___ \\| |_  | | | | |   \s
                             ___) | |_| | |___ ___) |__) | |___ ___) |  _| | |_| | |___\s
                            |____/ \\___/ \\____|____/____/|_____|____/|_|    \\___/|_____|""";
    }

    public void replaceCarts(int historyCartIndex, int buyerIndex) {
        buyers[buyerIndex].setCurrentCart(buyers[buyerIndex].getHistoryCart()[historyCartIndex]);
    }

    public Map<String, Integer> productsToLinkedMap(){                   // Object Oriented Design - Assignment 1
        Map<String, Integer> map = new LinkedHashMap<>();
        for(int i = 0; i < numberOfProducts; i++){
            String key = allProducts[i].getProductName().toLowerCase();
            if (map.containsKey(key)){
                map.put(key,map.get(key) + 1);
            }
            else{
                map.put(key, 1);
            }
        }
        return map;
    }

    public Set<Product> productsToTree() {                   // Object Oriented Design - Assignment 1
        Set<Product> treeSet = new TreeSet<>((p1, p2) -> {
            int lengthCompare = Integer.compare(p1.getProductName().length(), p2.getProductName().length());
            if (lengthCompare == 0) {
                return p1.getProductName().compareToIgnoreCase(p2.getProductName());
            }
            return lengthCompare;
        });
        for(int i = 0; i < numberOfProducts; i++){
            treeSet.add(allProducts[i]);
        }
        return treeSet;
    }

    public Set<String> productsNameToLinkedSet() {                  // Object Oriented Design - Assignment 1
        Set <String> set = new LinkedHashSet<>();
        for (int i = 0; i < numberOfProducts; i++)
            set.add(allProducts[i].getProductName().toLowerCase());
        return set;
    }
}


