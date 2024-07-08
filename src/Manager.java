import Exceptions.*;

import java.util.Arrays;
import java.util.Comparator;

public class Manager implements Manageable {
    private final int SIZE_INCREASE = 2;
    private Seller[] sellers;
    private int numberOfSellers;
    private Buyer[] buyers;
    private int numberOfBuyers;
    private Categories categoriesArrays;
    private final Comparator<Seller> comparatorSeller;
    private final Comparator<Buyer> comparatorBuyer;

    public Manager() {
        buyers = new Buyer[0];
        sellers = new Seller[0];
        categoriesArrays = new Categories();
        comparatorSeller = new CompareSellersByProductsNumber();
        comparatorBuyer = new CompareBuyersByName();
    }

    public Seller[] getSellers() {
        return sellers;
    }

    public void isEmptyCart(int buyerIndex) throws EmptyCartPayException {
        if (buyers[buyerIndex].getCurrentCart().getNumOfProducts() == 0) throw new EmptyCartPayException(buyers[buyerIndex].getUserName());
    }

    public Buyer[] getBuyers() {
        return buyers;
    }

    public boolean haveProductToSell (int indexSeller) {
        if (sellers[indexSeller].getNumOfProducts() == 0) {
            System.out.println(sellers[indexSeller].getUserName() + " haven't products to sell yet! ");
            return false;
        } else {
            System.out.println(sellers[indexSeller].toString());
            return true;
        }
    }

    public void isValidNumOfSellers() throws EmptyUsersArrayException {         /// remove method and add this exception for getSellers
        if (numberOfSellers == 0) throw new EmptyUsersArrayException("Sellers");
    }

    public void isValidNumOfBuyers() throws EmptyUsersArrayException {          /// remove method and add this exception for getBuyers
        if (numberOfBuyers == 0) throw new EmptyUsersArrayException("Buyers");
    }

    public int validProductIndex(int sellerIndex, String productIndexInput) {
        int productIndex;
        try {
            productIndex = Seller.validProductOfSeller(productIndexInput, sellers[sellerIndex].getNumOfProducts());
        } catch (NullPointerException e) {
            System.out.println("Your choice cannot be empty, please try again!");
            return 0;
        } catch (NumberFormatException e) {
            System.out.println("Your choice must be digit, please try again!");
            return 0;
        } catch (IndexOutOfRangeException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return productIndex;
    }

    public boolean buyerYesOrNoChoice(String input) {
        try {
            Buyer.yesOrNoChoiceForBuyer(input);
        } catch (EmptyException | YesNoChoiceException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    public boolean validProductName(String productNameInput) {
        try {
            Product.isValidProductName(productNameInput);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public double validPrice(String productPriceInput) {
        double productPrice;
        try {
            productPrice = Product.isValidPrice(productPriceInput);
        } catch (NullPointerException e){
            System.out.println("Price cannot be empty, please try again!");
            return 0;
        } catch (NumberFormatException e) {
            System.out.println("Price must enter as number, please try again!");
            return 0;
        } catch (NegativeOrZeroPriceException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return productPrice;
    }

    public int validCategory (String categoryInput) {
        int categoryChoice;
        try {
            categoryChoice = Categories.validCategoryChoice(categoryInput);
        } catch (NullPointerException e) {
            System.out.println("Your choice cannot be empty, please try again!");
            return 0;
        } catch (NumberFormatException e) {
            System.out.println("Your choice must be digit, please try again!");
            return 0;
        } catch (IndexOutOfRangeException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return categoryChoice;
    }

    public boolean validName (String name, int whichCase) {
        try {
            User.isValidUserName(name);
            if (whichCase == 1) User.isExist(sellers,name,numberOfSellers);
            else User.isExist(buyers,name,numberOfBuyers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean validPass (String pass) {
        try {
            User.isValidPassword (pass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean isValidAddress(String address) {
        try {
            Buyer.isValidAddress(address);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean isEmptySellers() {
        try {
            isValidNumOfSellers();
        } catch (EmptyUsersArrayException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    public boolean isEmptyBuyers() {
        try {
            isValidNumOfBuyers();
        } catch (EmptyUsersArrayException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    public int isInRangeSellers(String indexInput) throws IndexOutOfRangeException {
        int index = Integer.parseInt(indexInput);
        if (index > numberOfSellers || index <= 0) throw new IndexOutOfRangeException("Seller");
        return index;
    }

    public int isInRangeBuyers(String indexInput) throws IndexOutOfRangeException {
        int index = Integer.parseInt(indexInput);
        if (index > numberOfBuyers || index <= 0) throw new IndexOutOfRangeException("Buyer");
        return index;
    }
    
    public int chooseValidSeller(String indexInput) {
        int sellerIndex;
        try {
            sellerIndex = isInRangeSellers(indexInput);
        } catch (IndexOutOfRangeException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch (NumberFormatException e) {
            System.out.println("\nChoice must to be digit, please try again!\n");
            return 0;
        }
        return sellerIndex;
    }
    
    public int chooseValidBuyer(String indexInput) {
        int buyerIndex;
        try {
            buyerIndex = isInRangeBuyers(indexInput);
        } catch (IndexOutOfRangeException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch (NumberFormatException e) {
            System.out.println("\nChoice must to be digit, please try again!\n");
            return 0;
        }
        return buyerIndex;
    }

    public void addSeller(String username, String password) {
        Seller seller = new Seller(username, password);
        if (sellers.length == numberOfSellers) {
            if (sellers.length == 0) {
                sellers = Arrays.copyOf(sellers, 1);
            }
            sellers = Arrays.copyOf(sellers, sellers.length * SIZE_INCREASE);
        }
        sellers[numberOfSellers++] = seller;
        System.out.println("Seller added successfully.");
    }

    public void addBuyer(String username, String password, String address) {
        Buyer buyer = new Buyer(username, password, address);
        if (buyers.length == numberOfBuyers) {
            if (buyers.length == 0) {
                buyers = Arrays.copyOf(buyers, 1);
            }
            buyers = Arrays.copyOf(buyers, buyers.length * SIZE_INCREASE);
        }
        buyers[numberOfBuyers++] = buyer;
        System.out.println("Buyer added successfully.");
    }

    public void printSellersInfo() {
        if (numberOfSellers == 0) {
            System.out.println("Haven't sellers yet. return to main menu");
            return;
        }
        System.out.println("\nSellers info:");
        System.out.println("--------------");
        Arrays.sort(sellers, 0, numberOfSellers, comparatorSeller);
        for (int i = 0; i < numberOfSellers; i++) {
            System.out.println(i + 1 + ") " + sellers[i].getUserName() + ":");
            System.out.println(sellers[i].toString());
        }
    }

    public void printBuyersInfo() {
        System.out.println("\nBuyers info:");
        System.out.println("--------------");
        Arrays.sort(buyers, 0, numberOfBuyers, comparatorBuyer);
        for (int i = 0; i < numberOfBuyers; i++) {
            System.out.print(i + 1 + ") ");
            System.out.println(buyers[i].toString());
        }
    }

    public void printByCategory() {
        if (numberOfSellers == 0) {
            System.out.println("Haven't sellers yet. return to main menu");
            return;
        }
        System.out.println(categoriesArrays.toString());
    }

    public void printSellersNames() {
        try {
            isValidNumOfSellers();
            System.out.println("Seller's list:");
            System.out.println("--------------");
            for (int i = 0; i < numberOfSellers; i++) {
                System.out.println(i+1 + ")" + sellers[i].getUserName());
            } 
        } catch (EmptyUsersArrayException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void printBuyersNames() {
        try {
            isValidNumOfBuyers();
            System.out.println("Buyer's list:");
            System.out.println("--------------");
            for (int i = 0; i < numberOfBuyers; i++) {
                System.out.println(i+1 + ") " + buyers[i].getUserName());
            }
        } catch (EmptyUsersArrayException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printBuyerCurrentCart(int buyerIndex) {
        System.out.println(buyers[buyerIndex].getCurrentCart().toString());
    }

    public void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex, boolean specialPackage) {
        Product p1 = new Product(sellers[sellerIndex].getProducts()[productIndex]);
        buyers[buyerIndex].getCurrentCart().addProductToCart(p1, specialPackage);
    }

    public void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice) {
        Product p1 = new Product(productName, productPrice, c, specialPackagePrice);
        sellers[sellerIndex].addProduct(p1);
        addToCategoryArray(p1);
    }

    public void pay(int buyerIndex) {
        buyers[buyerIndex].payAndMakeHistoryCart();
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

    public void replaceCarts(int historyCartIndex, int buyerIndex) {
        buyers[buyerIndex].setCurrentCart(buyers[buyerIndex].getHistoryCart()[historyCartIndex]);
    }

    public static boolean isNumeric(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}


