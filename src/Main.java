//DOR_ZHAVIAN_211337845_&_ASAF_BANANI_211961933

import Enums.Category;
import Managers.ManagerFacade;
import Models.*;

import java.util.*;

public class Main {
    private static Scanner sc;
    private static ManagerFacade managerFacade;
    private static String input;
    private static String message;
    private static Factory factory;

    private static UserInput uI; // TRY
    private static Menu menu; // TRY

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        uI = new UserInput();
        menu = new Menu();
        managerFacade = ManagerFacade.getInstance();
        factory = new Factory();
        menu(managerFacade);
        sc.close();
    }

    private static void menu(ManagerFacade managerFacade) {
        menu.start();
        int choice;
        do {
            menu.printMenu();
            choice = uI.getInt();
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
                    System.out.println(managerFacade.buyersInfo());
                    break;
                case 7:
                    System.out.println(managerFacade.sellersInfo());
                    break;
                case 8:
                    System.out.println(managerFacade.productsByCategory());
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
                    System.out.println("\nPlease enter a valid choice in range 0-10 / 99 - 103!");
                    break;
            }
        } while (choice != 0);
    }

    public static void case1() {
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            input = uI.getString();
            if (input.equals("-1")) return;
            message = managerFacade.isExistSeller(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        System.out.println("Enter password: (Enter -1 to return main menu)");
        input = uI.getString();
        if (input.equals("-1")) return;
        managerFacade.addSeller(username, input);
        System.out.println("Seller added successfully.");
    }

    public static void case2() {
        System.out.println("Enter username: (Enter -1 to return main menu)");
        do {
            input = uI.getString();
            if (input.equals("-1")) return;
            message = managerFacade.isExistBuyer(input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        String username = input;
        System.out.println("Enter password: (Enter -1 to return main menu)");
        String password = uI.getString();
        if (password.equals("-1")) return;
        System.out.println("Enter your full address: ");
        System.out.println("Street: (Enter -1 to return main menu)");
        String street= uI.getString();
        if (street.equals("-1")) return;
        System.out.println("House number: (Enter -1 to return main menu)");
        String houseNum = uI.getString();
        if (houseNum.equals("-1")) return;
        System.out.println("City: (Enter -1 to return main menu)");
        String city = uI.getString();
        if (input.equals("-1")) return;
        System.out.println("State: (Enter -1 to return main menu)");
        String state = uI.getString();
        if (state.equals("-1")) return;
        Address address = new Address (street, houseNum, city, state);
        managerFacade.addBuyer(username, password, address);
        System.out.println("Buyer added successfully.");
    }

    public static void case3 () {
        if (managerFacade.getNumberOfSellers() == 0) {
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
            message = managerFacade.validPrice(input);
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
            message = managerFacade.validCategoryIndex(input);
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
                    message = managerFacade.validPrice(input);
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
        managerFacade.addProductSeller(sellerIndex, productName, productPrice, Category.values()[categoryIndex - 1], specialPackagePrice);
        System.out.println("Product added successfully.");
    }

    public static void case4 () {
        if (managerFacade.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        if (managerFacade.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;
        if (managerFacade.getSellers()[sellerIndex].getNumOfProducts() == 0) {
            System.out.println("This seller haven't products yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println(managerFacade.getSellers()[sellerIndex].toString());
        System.out.println("Enter product's number for adding to your cart: (Enter -1 to return main menu)");
        do {
            input = sc.next();
            if (input.equals("-1")) return;
            message = managerFacade.validProductIndex(sellerIndex, input);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);
        int productIndex = Integer.parseInt(input);
        managerFacade.addProductBuyer(buyerIndex,sellerIndex,productIndex - 1);
        System.out.println("Product added successfully to cart.");
    }

    public static void case5 () {
        if (managerFacade.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        System.out.println(managerFacade.pay(buyerIndex));
    }

    public static void case9 () {
        if (managerFacade.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        if (managerFacade.getBuyers()[buyerIndex].getHistoryCartsNum() == 0) {
            System.out.println("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
            return;
        }
        System.out.println(managerFacade.getBuyers()[buyerIndex].toString());
        System.out.println("Please choose cart number from history carts:");
        System.out.println("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
        do {
             input = sc.next();
             if (input.equals("-1")) return;
             message = managerFacade.isValidHistoryCartIndex(input, buyerIndex);
             if (message != null) {
                 System.out.println(message);
             }
        } while (message != null);
        int historyCartIndex = Integer.parseInt(input);
        managerFacade.replaceCarts(historyCartIndex - 1, buyerIndex);
        System.out.println("Your current cart update successfully.");
    }

    public static void case10(){
        int sellerIndex = managerFacade.getNumberOfSellers();
        int buyersIndex = managerFacade.getNumberOfBuyers();
        factory.hardcodedFactory(managerFacade, sellerIndex, buyersIndex);
    }

    public static void case99() {
        managerFacade.printProductsName();
    }

    public static void case100(){
        if(managerFacade.getNumberOfProducts() != 0){
          Map<String,Integer> map = managerFacade.productsToLinkedMap();                                     // first loop for create
          map.forEach((key, value) -> System.out.println(key + ".........." + value));         // second loop for print
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");

}

    public static void case101() {
        if (managerFacade.getNumberOfProducts() != 0) {
            Map<String, Integer> map = managerFacade.productsToLinkedMap();
            System.out.println("Please enter a string: (Enter -1 to return main menu)");
            input = sc.nextLine().toLowerCase();
            if (input.equals("-1")) return;
            System.out.printf("the number of times that " + input + " appears in the OG ARRAY is %d\n" , map.get(input) == null ? 0 : map.get(input));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public static void case102(){
        if(managerFacade.getNumberOfProducts() != 0) {
            List<String> setList = new ArrayList<>(managerFacade.productsNameToLinkedSet());
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

    public static void case103() {
        if (managerFacade.getNumberOfProducts() != 0) {
            Set<?> productsSet = managerFacade.productsToTree();
            Iterator<?> productsIterator = productsSet.iterator();
            while (productsIterator.hasNext()) {
                System.out.println(productsIterator.next().toString().toUpperCase());
            }
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public static int chooseSeller () {
        System.out.println(managerFacade.sellersNames());
        System.out.println("Please choose seller from the list above: (Enter -1 to return main menu)");
        while (true) {
            input = sc.next();
            if (input.equals("-1")) return -1;
            message = managerFacade.chooseValidSeller(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
    }

    public static int chooseBuyer () {
        System.out.println(managerFacade.buyersNames());
        System.out.println("Please choose buyer from the list above: (Enter -1 to return main menu)");
        while (true) {
            input = sc.next();
            if (input.equals("-1")) return -1;
            message = managerFacade.chooseValidBuyer(input);
            if (message != null) {
                System.out.println(message);
            } else {
                break;
            }
        }
        return Integer.parseInt(input) - 1;
    }
}

