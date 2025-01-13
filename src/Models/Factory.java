package Models;
import Enums.Category;


public class Factory {

    // FACTORY FUNCTIONS FOR CREATE OBJECTS :

    public static Buyer createBuyer (String username, String password, Address address) {
            return new Buyer(username, password, address);
    }

    public static Seller createSeller (String username, String password) {
            return new Seller(username, password);
    }

    public static Product createProduct (String name, double price, Category category) {
            return new Product(name, price, category);
    }

    public static ProductSpecialPackage createProductSpecialPackage (String name, double price, Category category, double specialPackagePrice) {
            return new ProductSpecialPackage(name, price, category, specialPackagePrice);
    }

    public static Product createProductForBuyer(Product product){
        return new Product(product);
    }

    public static ProductSpecialPackage createProductSpecialPackageForBuyer(Product product, double specialPackagePrice){
        return new ProductSpecialPackage(product, specialPackagePrice);
    }

    public static Address createAddress (String street, String houseName, String city, String state) {
            return new Address(street, houseName, city, state);
    }
}
