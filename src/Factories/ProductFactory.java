package Factories;

import Enums.Category;
import Models.Product;
import Models.ProductSpecialPackage;

public class ProductFactory {
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

}
