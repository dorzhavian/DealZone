package Managers;

import Models.Categories;
import Models.Product;
import java.util.*;

public interface IProductManager {

    String validPrice(double priceInput);

    String validCategoryIndex(int categoryInput);

    void addProductToProductArray(Product p);

    void printProductsName();

    void addToCategoryArray(Product p);

    boolean isSpecialPackageProduct (Product p);

    Map<String, Integer> productsToLinkedMap();

    Set<Product> productsToTree();

    Set<String> productsNameToLinkedSet();

    int getNumberOfProducts();

    Categories getCategoriesArrays();
}
