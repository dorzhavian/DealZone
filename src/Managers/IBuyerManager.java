package Managers;
import Models.Buyer;
import Models.Product;

import java.sql.Connection;

public interface IBuyerManager {

    String isExistBuyer (String name);

    String chooseValidBuyer(int indexInput);

    void addBuyer(Buyer buyer);

    String buyersInfo();

    String buyersNames();

    void addProductToBuyer(Product p, int buyerIndex);

    String pay(int buyerIndex, Connection conn);

    void updateCartPurchaseToDB(Buyer buyer, Connection conn);

    void replaceCarts(int historyCartIndex, int buyerIndex);

    int getNumberOfBuyers();

    Buyer[] getBuyers();

    void loadBuyersFromDB(Connection conn);

    int findBuyerIndexByID (int id);

    void addBuyerToDB(Buyer buyer, Connection conn);

    void insertCartItemToDB(Buyer buyer, Product product, Connection conn);

    void insertNewCartToDB(Buyer buyer, Connection conn);

    void updateCartAfterInsertToDB(Buyer buyer, Product product, double specialPackagePrice, Connection conn);

    void deleteAllCartFromDB(int buyerIndex, Connection conn);

    void updateCartFromHistory(int buyerIndex, Connection conn);

    boolean checkIfProductInCartInDB(Buyer buyer, Product p, Connection conn);

    }
