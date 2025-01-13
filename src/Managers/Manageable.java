package Managers;

import Enums.Category;
import Models.Address;
import Models.Product;

import java.util.Map;
import java.util.Set;


public interface Manageable {

    String validProductIndex(int sellerIndex, int productIndexInput);

    String validPrice(double priceInput);

    String validCategoryIndex(int categoryInput);

    String isExistSeller (String name);

    String isExistBuyer (String name);

    String isValidHistoryCartIndex(String indexCartInput, int buyerIndex);

    String chooseValidSeller(String indexInput);

    String chooseValidBuyer(String indexInput);

    void addProductName(Product p);

    void addSeller(String username, String password);

    void addBuyer(String username, String password, Address address);

    String sellersInfo();

    String buyersInfo();

    String productsByCategory();

    void printProductsName();

    String sellersNames();

    String buyersNames();

    void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex);

    void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice);

    void addToCategoryArray(Product p);

    String pay(int buyerIndex);

    void replaceCarts(int historyCartIndex, int buyerIndex);

    Map<String, Integer> productsToLinkedMap();

    Set<Product> productsToTree();

    Set<String> productsNameToLinkedSet();

}
