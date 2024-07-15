// DOR_ZHAVIAN-211337845_ALON_ETOS-207431487_DANIEL_SULTAN-323883751
// LECTURER - PINI SHLOMI

import Enums.Category;
import Managers.Manager;
import Models.Address;
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

    public static void case1() {
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            input = sc.next();
            rebootScanner();
            if (input.equals("-1")) return;
            message = manager.isExistSeller(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        System.out.println("Enter password: (Enter -1 to return main menu)");
        String password = sc.next();
        rebootScanner();
        if (password.equals("-1")) return;
        manager.addSeller(username, password);
        System.out.println("Seller added successfully.");
    }

    public static void case2() {
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            input = sc.next();
            rebootScanner();
            if (input.equals("-1")) return;
            message = manager.isExistBuyer(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        System.out.println("Enter password: (Enter -1 to return main menu)");
        String password = sc.next();
        if (password.equals("-1")) return;
        rebootScanner();
        System.out.println("Enter your full address: ");
        System.out.println("Street: (Enter -1 to return main menu)");
        String street = sc.next();
        if (street.equals("-1")) return;
        System.out.println("House number: (Enter -1 to return main menu)");
        String houseNum = sc.next();
        if (houseNum.equals("-1")) return;
        System.out.println("City: (Enter -1 to return main menu)");
        String city = sc.next();
        if (city.equals("-1")) return;
        System.out.println("State: (Enter -1 to return main menu)");
        String state = sc.next();
        if (state.equals("-1")) return;
        rebootScanner();
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
        String productName = sc.next();
        if (productName.equals("-1")) return;
        rebootScanner();
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

    public static void rebootScanner() {
        sc.nextLine();
    }
}

