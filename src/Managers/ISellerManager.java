package Managers;

import Models.Product;
import Models.Seller;


public interface ISellerManager {

    String isExistSeller (String name);

    void addSeller(Seller seller);

    String sellersInfo();

    String sellersNames();

    String chooseValidSeller(int indexInput);

    void addProductToSeller(Product p, int sellerIndex);

    int getNumberOfSellers();

    Seller[] getSellers();
}
