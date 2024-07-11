package Managers;

import Enums.Category;
import Models.Product;


public interface Manageable {

    boolean isEmptyHistoryCart (int buyerIndex);

    String validProductIndex(int sellerIndex, String productIndexInput);

    String validPrice(String priceInput);

    String validCategory (String categoryInput);

    String validName (String name, int whichCase);

    int isValidCartIndex (String indexCartInput, int buyerIndex);

    String chooseValidSeller(String indexInput);

    String chooseValidBuyer(String indexInput);

    void addSeller(String username, String password);

    void addBuyer(String username, String password, String address);

    String sellersInfo();

    String buyersInfo();

    String productsByCategory();

    String sellersNames();

    String buyersNames();

    void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex);

    void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice);

    void addToCategoryArray(Product p);

    String pay(int buyerIndex);

    void replaceCarts(int historyCartIndex, int buyerIndex);
    }
