// DOR_ZHAVIAN-211337845_ALON_ETOS-207431487_DANIEL_SULTAN-323883751
// LECTURER - PINI SHLOMI
import Enums.Category;
import Exceptions.EmptyCartPayException;
import Managers.Manager;
import Models.Categories;

import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static Manager manager;
    private static String input;

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
                input = sc.nextLine();
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a digit (in range 0-9)!");
                choice = -1;
                continue;
            }
            switch (choice) {
                case 0:
                    System.out.println("Thanks for using our system. GoodBye!");
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
                    manager.printBuyersInfo();
                    break;
                case 7:
                    manager.printSellersInfo();
                    break;
                case 8:
                    manager.printByCategory();
                    break;
                case 9:
                    case9();
                    break;
                default:
                    System.out.println("Please enter a valid choice in range 0-9!");
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
            username = sc.nextLine();
            if (username.equals("-1")) return;
        } while (!manager.validName(username, whichCase));
        String password;
        System.out.println("Enter password: (Enter -1 to return main menu)");
        do {
            password = sc.nextLine();
            if (password.equals("-1")) return;
        } while (!manager.validPass(password));
        if (whichCase == 1) {
            manager.addSeller(username, password);
            return;
        }
        String address;
        System.out.println("Enter your address: (Enter -1 to return main menu)");
        do {
            address = sc.nextLine();
            if (address.equals("-1")) return;
        } while (!manager.isValidAddress(address));
        manager.addBuyer(username, password, address);
    }

    public static void case3 () {
        if (manager.isEmptySellers()) return;
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        System.out.println("Enter product name to add: (Enter -1 to return main menu)");
        String productName;
        do {
             productName = sc.nextLine();
             if (productName.equals("-1")) return;
        } while (!manager.validProductName(productName));
        System.out.println("Enter product price: (Enter -1 to return main menu)");
        double productPrice;
        do {
            input = sc.nextLine();
            if (input.equals("-1")) return;
            productPrice = manager.validPrice(input);
        } while ( productPrice == 0);
        System.out.println("Choose category: (Enter -1 to return main menu)\n");
        int categoryIndex;
        do {
            System.out.println("Category list:");
            System.out.println("--------------");
            Categories.printCategories();
            input = sc.nextLine();
            if (input.equals("-1")) return;
            categoryIndex = manager.validCategory(input);
        } while (categoryIndex == 0);
        System.out.println("Enter price for special package: (Enter -1 to return main menu)");
        double specialPackagePrice;
        do {
            input = sc.nextLine();
            if (input.equals("-1")) return;
            specialPackagePrice = manager.validPrice(input);
        } while (specialPackagePrice == 0);
        manager.addProductSeller(sellerIndex, productName, productPrice, Category.values()[categoryIndex - 1], specialPackagePrice);
    }

    public static void case4 () {
        if (manager.isEmptyBuyers() || manager.isEmptySellers() ) return;
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        if (!manager.haveProductToSell(sellerIndex)) return;
        System.out.println("Enter product's number for adding to your cart: (Enter -1 to return main menu)");
        int productIndex;
        do {
            input = sc.nextLine();
            if (input.equals("-1")) return;
            productIndex = manager.validProductIndex(sellerIndex, input);
        } while (productIndex == 0);
        System.out.println("Would you like a special package?");
        System.out.println("The price will be - " + manager.getSellers()[sellerIndex].getProducts()[productIndex - 1].getSpecialPackagePrice());
        System.out.println("Enter YES or NO. (Enter -1 to return main menu)");
        String choice;
        do {
            choice = sc.nextLine();
        } while (manager.buyerYesOrNoChoice(choice));
        boolean specialPackage = choice.equalsIgnoreCase("yes");
        manager.addProductBuyer(buyerIndex,sellerIndex,productIndex - 1, specialPackage);
    }

    public static void case5 () {
        if (manager.isEmptyBuyers()) return;
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        try {
            manager.isEmptyCart(buyerIndex);
        } catch (EmptyCartPayException e) {
            System.out.println(e.getMessage());
            return;
        }
        manager.printBuyerCurrentCart(buyerIndex);
        System.out.println("Are you sure you want to process checkout? Enter YES/NO");
        String choice;
        do {
            choice = sc.nextLine();
        } while (manager.buyerYesOrNoChoice(choice));
        if (choice.equalsIgnoreCase("yes")) manager.pay(buyerIndex);
    }

    public static void case9 () {
        if (manager.isEmptyBuyers()) return;
        manager.printBuyersInfo();
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        System.out.println(manager.getBuyers()[buyerIndex].toString());
        System.out.println("Please choose cart number from history carts:");
        System.out.println("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
        int historyCartIndex;
        do {
             input = sc.nextLine();
             if (input.equals("-1")) return;
             historyCartIndex = manager.isValidCartIndex(input, buyerIndex);
        } while (historyCartIndex == 0);
        manager.replaceCarts(historyCartIndex - 1, buyerIndex);

    }

    public static int chooseSeller () {
        int sellerIndex;
        do {
            manager.printSellersNames();
            System.out.println("Please choose seller from the list above: (Enter -1 to return main menu)");
            input = sc.nextLine();
            if (input.equals("-1")) return -1;
            sellerIndex = manager.chooseValidSeller(input);
        } while (sellerIndex == 0) ;
        return sellerIndex -1;
    }

    public static int chooseBuyer () {
        String input;
        int buyerIndex;
        do {
            manager.printBuyersNames();
            System.out.println("Please choose buyer from the list above: (Enter -1 to return main menu)");
            input = sc.nextLine();
            if (input.equals("-1")) return -1;
            buyerIndex = manager.chooseValidBuyer(input);
        } while (buyerIndex == 0);
        return buyerIndex - 1;
    }
}

