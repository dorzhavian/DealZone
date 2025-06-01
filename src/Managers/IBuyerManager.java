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

    String pay(int buyerIndex);

    void replaceCarts(int historyCartIndex, int buyerIndex);

    int getNumberOfBuyers();

    Buyer[] getBuyers();

    void loadBuyersFromDB(Connection conn);

    int findBuyerIndexByID (int id);
    }
