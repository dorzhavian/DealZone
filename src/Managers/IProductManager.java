package Managers;

import Enums.Category;
import Enums.ExceptionsMessages;
import Models.Product;
import Models.ProductSpecialPackage;

import java.util.*;

public interface IProductManager {

    String validPrice(double priceInput);

    String validCategoryIndex(int categoryInput);

    void addProductName(Product p);

    void printProductsName();

    void addToCategoryArray(Product p);

    boolean isSpecialPackageProduct (Product p);

    Map<String, Integer> productsToLinkedMap();

    Set<Product> productsToTree();

    Set<String> productsNameToLinkedSet();
}
