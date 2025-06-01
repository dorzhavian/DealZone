package Factories;
import Models.*;


public class UserFactory {

    // FACTORY FUNCTIONS FOR CREATE OBJECTS :

    public static Buyer createBuyer (String username, String password, Address address) {
            return new Buyer(username, password, address);
    }

    public static Buyer createBuyerFromDB (int id, String username, String password, Address address, int numOfHistoryCarts) {
        return new Buyer(id, username, password, address, numOfHistoryCarts);
    }

    public static Seller createSeller (String username, String password) {
            return new Seller(username, password);
    }

    public static Seller createSellerFromDB (int id, String username, String password) {
        return new Seller(id, username, password);
    }

    public static Address createAddress (String street, String houseName, String city, String state) {
            return new Address(street, houseName, city, state);
    }
}
