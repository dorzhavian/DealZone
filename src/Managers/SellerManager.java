package Managers;

import Comparators.CompareSellersByProductsNumber;
import Enums.ExceptionsMessages;
import Models.*;

import java.util.Arrays;
import java.util.Comparator;

public class SellerManager {
    private Seller[] sellers;
    private int numberOfSellers;
    private final Comparator<Seller> comparatorSeller;
    private static SellerManager instance;

    public static SellerManager getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new SellerManager();
        return instance;
    }

    private SellerManager() {
        sellers = new Seller[0];
        comparatorSeller = new CompareSellersByProductsNumber();
    }

    public int getNumberOfSellers() {
        return numberOfSellers;
    }

    public Seller[] getSellers() {
        return sellers;
    }

    public String isExistSeller (String name) {
        for (int i = 0; i < numberOfSellers; i++) {
            if (sellers[i].getUserName().equalsIgnoreCase(name)) return "Seller name already EXIST, please try again!";
        }
        return null;
    }

    public void addSeller(Seller seller) {
        if (sellers.length == numberOfSellers) {
            if (sellers.length == 0) {
                sellers = Arrays.copyOf(sellers, 1);
            }
            int SIZE_INCREASE = 2;
            sellers = Arrays.copyOf(sellers, sellers.length * SIZE_INCREASE);
        }
        sellers[numberOfSellers++] = seller;
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

    public String sellersNames() {
        StringBuilder sb = new StringBuilder("Seller's:\n--------------\n");
        for (int i = 0; i < numberOfSellers; i++) {
            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append("\n");
        }
        return sb.toString();
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

    public void addProductToSeller(Product p, int sellerIndex) {
        sellers[sellerIndex].addProduct(p);
    }

}
