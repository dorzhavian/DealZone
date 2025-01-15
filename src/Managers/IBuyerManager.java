package Managers;

import Comparators.CompareBuyersByName;
import Enums.ExceptionsMessages;
import Exceptions.EmptyCartPayException;
import Models.Buyer;
import Models.Product;

import java.util.Arrays;

public interface IBuyerManager {

    String isExistBuyer (String name);

    String chooseValidBuyer(String indexInput);

    void addBuyer(Buyer buyer);

    String buyersInfo();

    String buyersNames();

    void addProductToBuyer(Product p, int buyerIndex);

    String pay(int buyerIndex);

    void replaceCarts(int historyCartIndex, int buyerIndex);

}
