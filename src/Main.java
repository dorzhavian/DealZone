// DOR_ZHAVIAN-211337845_ALON_ETOS-207431487_DANIEL_SULTAN-323883751
// LECTURER - PINI SHLOMI

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static Manager manager;

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
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: you entered invalid input, please enter number!");
                sc.nextLine();
                choice = -1;
                continue;
            }
            sc.nextLine();
            switch (choice) {            /// MAKE EXCEPTION FOR ENTER NUMBER NOT IN RANGE!
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
            }
        } while (choice != 0);
        System.out.println("Thanks for using our system. GoodBye!");
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
        String productName = sc.nextLine();
        if (productName.equals("-1")) return ;
        System.out.println("Enter product price: (Enter -1 to return main menu)");
        double productPrice = sc.nextDouble();
        if (productPrice < 0) return ;
        Category[] allCategories = Category.values();
        for (Category category : allCategories) {
            System.out.println(category.ordinal() + 1 + ") " + category.name());
        }
        System.out.println("Choose category: (Enter -1 to return main menu)");
        int categoryIndex = sc.nextInt();
        sc.nextLine();
        if (categoryIndex < 0) return ;
        System.out.println("Enter price for special package: (Enter -1 to return main menu)");
        double specialPackagePrice = sc.nextDouble();
        sc.nextLine();
        manager.addProductSeller(sellerIndex, productName, productPrice, allCategories[categoryIndex - 1], specialPackagePrice);
    }

    public static void case4 () {
        if (manager.isEmptyBuyers() || manager.isEmptySellers() ) return;
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        if (manager.getSellers()[sellerIndex].getNumOfProducts() == 0) {
            System.out.println("This seller haven't products to sell yet.");
            return;
        }
        System.out.println(manager.getSellers()[sellerIndex].toString()); // print the chosen seller products
        System.out.println("Enter product's number for adding to your cart: (Enter -1 to return main menu)");
        int productIndex = sc.nextInt() - 1;
        sc.nextLine();
        if (productIndex < 0) return;
        System.out.println("Would you like a special package?");
        System.out.println("The price will be - " + manager.getSellers()[sellerIndex].getProducts()[productIndex].getSpecialPackagePrice());
        System.out.println("Enter YES or NO. (Enter -1 to return main menu)");
        String choice = sc.nextLine();
        boolean specialPackage = choice.equalsIgnoreCase("yes");
        manager.addProductBuyer(buyerIndex,sellerIndex,productIndex, specialPackage);
    }

    public static void case5 () {
        if (manager.isEmptyBuyers()) return;
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        if (manager.getBuyers()[buyerIndex].getCurrentCart().getNumOfProducts() == 0) {
            System.out.println("Cart is empty, cannot process to checkout.");
            return; }
        System.out.println(manager.getBuyers()[buyerIndex].getCurrentCart().toString());
        System.out.println("Are you sure you want to process checkout? Enter YES/NO");
        String answer = sc.nextLine();
        if (answer.equalsIgnoreCase("no")) return;
        manager.pay(buyerIndex);
    }

    public static void case9 () {
        if (manager.isEmptyBuyers()) return;
        manager.printBuyersInfo();
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        manager.getBuyers()[buyerIndex].toString();
        System.out.println("Please choose cart number from history carts:");
        System.out.println("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
        int historyCartIndex = sc.nextInt();
        sc.nextLine();
        if (historyCartIndex < 0) return;
        manager.replaceCarts(historyCartIndex - 1, buyerIndex);
    }

    public static int chooseSeller () {
        String sellerIndex;
        do {
            manager.printSellersNames();
            System.out.println("Please choose seller from the list above: (Enter -1 to return main menu)");
            sellerIndex = sc.nextLine();
            if (sellerIndex.equals("-1")) return -1;
        } while (!manager.chooseValidSeller(sellerIndex)) ;
        return Integer.parseInt(sellerIndex) -1;
    }

    public static int chooseBuyer () {
        String buyerIndex;
        do {
            manager.printBuyersNames();
            System.out.println("Please choose buyer from the list above: (Enter -1 to return main menu)");
            buyerIndex = sc.nextLine();
            if (buyerIndex.equals("-1")) return -1;
        } while (!manager.chooseValidBuyer(buyerIndex));
        return Integer.parseInt(buyerIndex) -1;
    }
}

