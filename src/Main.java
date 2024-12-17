//DOR_ZHAVIAN_211337845_&_ASAF_BANANI_211961933

import Enums.Category;
import Managers.Manager;
import Models.Address;
import Models.Categories;
import Models.Factory;

import java.util.*;

public class Main {
    private static Scanner sc;
    private static Manager manager;
    private static String input;
    private static String message;
    private static Factory factory;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        manager = new Manager();
        factory = new Factory();
        menu(manager);
        sc.close();
    }

    private static void menu(Manager manager) {
        int choice;
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println(" ------------HELLO AND WELCOME TO OUR BUYER - SELLER PROGRAM-----------");
        System.out.println(" ------------(In anytime press -1 for return to main menu)-------------");
        System.out.println("------------------------------------------------------------------------");
        do {
            printMenu();
            try {
                input = sc.next();
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("\nInvalid input, please enter a digit!");
                choice = -1;
                continue;
            }
            sc.nextLine();
            switch (choice) {
                case -1:
                    break;
                case 0:
                    System.out.println("\nThanks for using our system. GoodBye!");
                    break;
                case 1:
                    case1();
                    break;
                case 2:
                    case2();
                    break;
                case 3:
                    case3();
                    break;
                case 4:
                    case4();
                    break;
                case 5:
                    case5();
                    break;
                case 6:
                    System.out.println(manager.buyersInfo());
                    break;
                case 7:
                    System.out.println(manager.sellersInfo());
                    break;
                case 8:
                    System.out.println(manager.productsByCategory());
                    break;
                case 9:
                    case9();
                    break;
                case 10:
                    case10();
                    break;
                case 99:
                    case99();
                    break;
                case 100:
                    case100();
                    break;
                case 101:
                    case101();
                    break;
                case 102:
                    case102();
                    break;
                case 103:
                    case103();
                    break;
                default:
                    System.out.println("\nPlease enter a valid choice in range 0-9!");
                    break;
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\nMenu : ");
        System.out.println("0) Exit");
        System.out.println("1) Add seller");
        System.out.println("2) Add buyer");
        System.out.println("3) Add item for seller");
        System.out.println("4) Add item for buyer");
        System.out.println("5) Payment for buyer");
        System.out.println("6) Buyer's details");
        System.out.println("7) Seller's details");
        System.out.println("8) Product's by category");
        System.out.println("9) Replace current cart with cart from history");
        System.out.println("10) Use factory for automatic shop create");
        System.out.println("99) Question 15");
        System.out.println("100) Question 16");
        System.out.println("101) Question 17 ");
        System.out.println("102) Question 18 ");
        System.out.println("103) Question 19 ");
        System.out.println("Please enter your choice: ");
    }

    public static void case1() {
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            do input = sc.nextLine();
            while (input.isEmpty());
            if (input.equals("-1")) return;
            message = manager.isExistSeller(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        System.out.println("Enter password: (Enter -1 to return main menu)");
        do input = sc.nextLine();    // password
        while (input.isEmpty());
        if (input.equals("-1")) return;
        manager.addSeller(username, input);
        System.out.println("Seller added successfully.");
    }

    public static void case2() {
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            do input = sc.nextLine();
            while (input.isEmpty());
            if (input.equals("-1")) return;
            message = manager.isExistBuyer(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        System.out.println("Enter password: (Enter -1 to return main menu)");
        do input = sc.nextLine();
        while (input.isEmpty());
        if (input.equals("-1")) return;
        String password = input;
        System.out.println("Enter your full address: ");
        System.out.println("Street: (Enter -1 to return main menu)");
        do input = sc.nextLine();
        while (input.isEmpty());
        if (input.equals("-1")) return;
        String street = input;
        System.out.println("House number: (Enter -1 to return main menu)");
        String houseNum = sc.next();
        if (houseNum.equals("-1")) return;
        System.out.println("City: (Enter -1 to return main menu)");
        do input = sc.nextLine();
        while (input.isEmpty());
        if (input.equals("-1")) return;
        String city = input;
        System.out.println("State: (Enter -1 to return main menu)");
        String state = sc.next();
        if (state.equals("-1")) return;
        Address address = new Address (street, houseNum, city, state);
        manager.addBuyer(username, password, address);
        System.out.println("Buyer added successfully.");
    }

    public static void case3 () {
        if (manager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot be proceed. return to Menu.");
            return;
        }
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        System.out.println("Enter product name to add: (Enter -1 to return main menu)");
        do input = sc.nextLine();
        while (input.isEmpty());
        if (input.equals("-1")) return;
        String productName = input;
        System.out.println("Enter product price: (Enter -1 to return main menu)");
        do {
            input = sc.next();
            if (input.equals("-1")) return;
            message = manager.validPrice(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        double productPrice = Double.parseDouble(input);
        System.out.println(Categories.categoriesByNames());
        System.out.println("Choose category: (Enter -1 to return main menu)\n");
        do {
            input = sc.next();
            if (input.equals("-1")) return;
            message = manager.validCategoryIndex(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        int categoryIndex = Integer.parseInt(input);
        System.out.println("This product have special package? YES / NO : (Enter -1 to return main menu) ");
        double specialPackagePrice = 0;
        do {
            input = sc.next();
            if (input.equals("-1")) return;
            if (input.equalsIgnoreCase("yes")) {
                System.out.println("Enter price for special package: (Enter -1 to return main menu)");
                do {
                    input = sc.next();
                    if (input.equals("-1")) return;
                    message = manager.validPrice(input);
                    if (message != null) {
                        System.out.println(message);
                    }
                } while (message != null);
                specialPackagePrice = Double.parseDouble(input);
                break;
            }
            if (!input.equalsIgnoreCase("no")) {
                System.out.println("Please enter YES / NO only !");
            }
        } while (!input.equalsIgnoreCase("no"));
        manager.addProductSeller(sellerIndex, productName, productPrice, Category.values()[categoryIndex - 1], specialPackagePrice);
        System.out.println("Product added successfully.");
    }

    public static void case4 () {
        if (manager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        if (manager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        if (manager.getSellers()[sellerIndex].getNumOfProducts() == 0) {
            System.out.println("This seller haven't products yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println(manager.getSellers()[sellerIndex].toString());
        System.out.println("Enter product's number for adding to your cart: (Enter -1 to return main menu)");
        do {
            input = sc.next();
            if (input.equals("-1")) return;
            message = manager.validProductIndex(sellerIndex, input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        int productIndex = Integer.parseInt(input);
        manager.addProductBuyer(buyerIndex,sellerIndex,productIndex - 1);
        System.out.println("Product added successfully to cart.");
    }

    public static void case5 () {
        if (manager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        System.out.println(manager.pay(buyerIndex));
    }

    public static void case9 () {
        if (manager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        if (manager.getBuyers()[buyerIndex].getHistoryCartsNum() == 0) {
            System.out.println("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
            return;
        }
        System.out.println(manager.getBuyers()[buyerIndex].toString());
        System.out.println("Please choose cart number from history carts:");
        System.out.println("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
        do {
             input = sc.next();
             if (input.equals("-1")) return;
             message = manager.isValidHistoryCartIndex(input, buyerIndex);
             if (message != null) {
                 System.out.println(message);
             }
        } while (message != null);
        int historyCartIndex = Integer.parseInt(input);
        manager.replaceCarts(historyCartIndex - 1, buyerIndex);
        System.out.println("Your current cart update successfully.");
    }

    public static void case10(){
        int sellerIndex = manager.getNumberOfSellers();
        int buyersIndex = manager.getNumberOfBuyers();
        factory.initFactory(manager, sellerIndex, buyersIndex);
    }

    public static void case99() {
        manager.printProductsName();
    }

    public static void case100(){
        if(manager.getNumberOfProducts() != 0){
          Map<String,Integer> map = manager.productsToLinkedMap();                                     // first loop for create
          map.forEach((key, value) -> System.out.println(key + ".........." + value));         // second loop for print
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");

}

    public static void case101() {
        if (manager.getNumberOfProducts() != 0) {
            Map<String, Integer> map = manager.productsToLinkedMap();
            System.out.println("Please enter a string: (Enter -1 to return main menu)");
            input = sc.nextLine().toLowerCase();
            if (input.equals("-1")) return;
            System.out.printf("the number of times that " + input + " appears in the OG ARRAY is %d\n" , map.get(input) == null ? 0 : map.get(input));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public static void case102(){
        if(manager.getNumberOfProducts() != 0) {
            Map<String, Integer> map = manager.productsToLinkedMap();
            List<String> keyList = new ArrayList<>(map.keySet());
            List<String> doubleNames = new ArrayList<>();
            ListIterator<String> iterator = keyList.listIterator();
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

    public static void case103() {
        if (manager.getNumberOfProducts() != 0) {
            Set<?> productsSet = manager.productsToTree();
            Iterator<?> productsIterator = productsSet.iterator();
            while (productsIterator.hasNext()) {
                System.out.println(productsIterator.next().toString().toUpperCase());
            }
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public static int chooseSeller () {
        System.out.println(manager.sellersNames());
        System.out.println("Please choose seller from the list above: (Enter -1 to return main menu)");
        while (true) {
            input = sc.next();
            if (input.equals("-1")) return -1;
            message = manager.chooseValidSeller(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
    }

    public static int chooseBuyer () {
        System.out.println(manager.buyersNames());
        System.out.println("Please choose buyer from the list above: (Enter -1 to return main menu)");
        while (true) {
            input = sc.next();
            if (input.equals("-1")) return -1;
            message = manager.chooseValidBuyer(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
    }
}

