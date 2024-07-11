// DOR_ZHAVIAN-211337845_ALON_ETOS-207431487_DANIEL_SULTAN-323883751
// LECTURER - PINI SHLOMI
import Enums.Category;
import Managers.Manager;
import Models.Categories;

import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static Manager manager;
    private static String input;
    private static String message;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        manager = new Manager();
        menu(manager);
        sc.close();
    }

    private static void menu(Manager manager) {
        String input;
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
            switch (choice) {
                case 0:
                    System.out.println("\nThanks for using our system. GoodBye!");
                    break;
                case 1:
                    case1and2(1);
                    break;
                case 2:
                    case1and2(2);
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
        System.out.println("Please enter your choice: ");
    }

    public static void case1and2(int whichCase) {
        String username;
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            username = sc.next();
            if (username.equals("-1")) return;
        } while (!manager.validName(username, whichCase));  // check if name exist 
        String password;
        System.out.println("Enter password: (Enter -1 to return main menu)");
        do {
            password = sc.next();
            if (password.equals("-1")) return;
        } while (password.isEmpty());
        if (whichCase == 1) {
            manager.addSeller(username, password);
            return;
        }
        String address;
        System.out.println("Enter your address: (Enter -1 to return main menu)");
        do {
            address = sc.next();
            if (address.equals("-1")) return;
        } while (address.isEmpty());
        manager.addBuyer(username, password, address);
    }

    public static void case3 () {
        if (manager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, return to menu.");
            return;
        }
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        System.out.println("Enter product name to add: (Enter -1 to return main menu)");
        String productName;
        do {
             productName = sc.next();
             if (productName.equals("-1")) return;
        } while (productName.isEmpty());
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
        System.out.println("Choose category: (Enter -1 to return main menu)\n");
        do {
            System.out.println(Categories.categoriesByNames());
            input = sc.next();
            if (input.equals("-1")) return;
            message = manager.validCategory(input);
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
        System.out.println("Enter product's number for adding to your cart: (Enter -1 to return main menu)");
        do {
            input = sc.next();
            if (input.equals("-1")) return;
            message = manager.validProductIndex(sellerIndex, input);
            if (message != null) {
                System.out.println(message);
                System.out.println(manager.getSellers()[sellerIndex].toString());
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
        System.out.println(manager.buyersInfo());
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        if (manager.isEmptyHistoryCart(buyerIndex)) return;
        System.out.println(manager.getBuyers()[buyerIndex].toString());
        System.out.println("Please choose cart number from history carts:");
        System.out.println("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
        int historyCartIndex;
        do {
             input = sc.next();
             if (input.equals("-1")) return;
             historyCartIndex = manager.isValidCartIndex(input, buyerIndex);
        } while (historyCartIndex == 0);
        manager.replaceCarts(historyCartIndex - 1, buyerIndex);

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

