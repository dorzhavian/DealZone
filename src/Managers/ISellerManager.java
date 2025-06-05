package Managers;

import Models.Product;
import Models.Seller;
import java.sql.*;

public interface ISellerManager {

    String isExistSeller (String name);

    void addSeller(Seller seller);

    String sellersInfo();

    String sellersNames();

    String chooseValidSeller(int indexInput);

    void addProductToSeller(Product p, int sellerIndex);

    int getNumberOfSellers();

    Seller[] getSellers();

    void loadSellersFromDB(Connection conn);

    int findSellerIndexByID (int id);

    void addSellerToDB (Seller seller, Connection conn);

    void updateProductsNumForSellerDB(int sellerIndex, Connection conn);
}
