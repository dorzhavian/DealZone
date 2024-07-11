package Managers;

import Comparators.CompareBuyersByName;
import Comparators.CompareSellersByProductsNumber;
import Enums.Category;
import Exceptions.*;
import Models.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class Manager implements Manageable {
    private final int SIZE_INCREASE = 2;
    private Seller[] sellers;
    private int numberOfSellers;
    private Buyer[] buyers;
    private int numberOfBuyers;
    private final Categories categoriesArrays;
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

    public Buyer[] getBuyers() {
        return buyers;
    }

    public int getNumberOfSellers() {
        return numberOfSellers;
    }

    public int getNumberOfBuyers() {
        return numberOfBuyers;
    }

    public boolean isEmptyHistoryCart (int buyerIndex) {
        try {
            buyers[buyerIndex].getHistoryCartsNum();
        } catch (EmptyHistoryCartException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    public String validProductIndex(int sellerIndex, String productIndexInput) {
        try {
            int productIndex = Integer.parseInt(productIndexInput);
            if (productIndex <= 0 || productIndex > sellers[sellerIndex].getNumOfProducts()) throw new IndexOutOfBoundsException("\n Product number is NOT exist, PLEASE choose from the RANGE!\n");
        } catch (NumberFormatException e) {
            return "Your choice must be digit, please try again!\n";
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String validPrice(String priceInput) {
        try {
            double price = Double.parseDouble(priceInput);
            if (price <= 0) throw new InputMismatchException("Price cannot be zero or negative, please try again!");
        } catch (NullPointerException e){
            return "Price cannot be empty, please try again!";
        } catch (NumberFormatException e) {
            return "Price must enter as number, please try again!";
        } catch (InputMismatchException e) {
            return e.getMessage();
        }
        return null;
    }

    public String validCategory (String categoryInput) {
        try {
            int categoryChoice = Integer.parseInt(categoryInput);
            if (categoryChoice <= 0 || categoryChoice > Category.values().length) throw new IndexOutOfBoundsException("\nCategory number is NOT exist, PLEASE choose from the RANGE!\n");
        } catch (NumberFormatException e) {
            return "Your choice must be digit, please try again!";
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public boolean validName (String name, int whichCase) {
        try {
            if (whichCase == 1) User.isExist(sellers,name,numberOfSellers);
            else User.isExist(buyers,name,numberOfBuyers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public int isValidCartIndex (String indexCartInput, int buyerIndex) {
        int indexCart;
        try {
            indexCart = Integer.parseInt(indexCartInput);
            if (buyers[buyerIndex].getHistoryCartsNum() < indexCart) throw new IndexOutOfRangeException("History cart");
        } catch (NumberFormatException e) {
            System.out.println("\nChoice must be a digit, please try again!\n");
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return indexCart;
    }
    
    public String chooseValidSeller(String indexInput) {
        try {
            int index = Integer.parseInt(indexInput);
            if (index > numberOfSellers || index <= 0) throw new IndexOutOfRangeException("Seller");
        } catch (IndexOutOfRangeException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "\nChoice must to be digit, please try again!\n";
        }
        return null;
    }
    
    public String chooseValidBuyer(String indexInput) {
        try {
            int index = Integer.parseInt(indexInput);
            if (index > numberOfBuyers || index <= 0) throw new IndexOutOfRangeException("Buyer");
        } catch (IndexOutOfRangeException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return  "\nChoice must to be digit, please try again!\n";
        }
        return null;
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

    public String sellersInfo() {
        if (numberOfSellers == 0) {
            return "Haven't sellers yet, cannot be proceed. return to Menu.";
        }
        StringBuilder sb = new StringBuilder("\nSellers info:\n--------------\n");
        Arrays.sort(sellers, 0, numberOfSellers, comparatorSeller);
        for (int i = 0; i < numberOfSellers; i++) {
            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append(":");
            sb.append(sellers[i].toString());
        }
        return sb.toString();
    }

    public String buyersInfo() {
        if (numberOfBuyers == 0) {
            return "Haven't buyers yet, cannot be proceed. return to Menu.";
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
            return "Haven't buyers yet, cannot be proceed. return to Menu.";
        }
        return categoriesArrays.toString();
    }

    public String sellersNames() {
        StringBuilder sb = new StringBuilder("Seller's:\n--------------\n");
        for (int i = 0; i < numberOfSellers; i++) {
            sb.append(i + 1).append(") ").append(sellers[i].getUserName());
        }
        return sb.toString();
    }
    
    public String buyersNames() {
        StringBuilder sb = new StringBuilder("Buyer's:\n--------------\n");
        for (int i = 0; i < numberOfBuyers; i++) {
            sb.append(i + 1).append(") ").append(buyers[i].getUserName());
        }
        return sb.toString();
    }

    public void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex) {
        Product p1 = new Product(sellers[sellerIndex].getProducts()[productIndex]);
        buyers[buyerIndex].getCurrentCart().addProductToCart(p1);
    }

    public void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice) {
        Product p1 = new Product(productName, productPrice, c, specialPackagePrice);
        sellers[sellerIndex].addProduct(p1);
        addToCategoryArray(p1);
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
        System.out.println("Your current cart update successfully.");
    }
}


