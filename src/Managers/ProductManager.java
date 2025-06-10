package Managers;

import Enums.Category;
import Enums.ExceptionsMessages;
import Models.Categories;
import Models.Product;
import Models.ProductSpecialPackage;

import java.sql.*;
import java.util.*;

public class ProductManager implements IProductManager {

    private Product[] allProducts;
    private int numberOfProducts;
    private final Categories categoriesArrays;
    private List<String> doubleNames;
    private static ProductManager instance;

    public static ProductManager getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new ProductManager();
        return instance;
    }

    public ProductManager() {
        categoriesArrays = new Categories();
        allProducts = new Product[0];
        doubleNames = new ArrayList<>();
    }

    public Categories getCategoriesArrays() {
        return categoriesArrays;
    }

    @Override
    public void addProductToDB(Product p1, int sellerID, double specialPackagePrice, Connection conn) {
        String sqlProduct = "INSERT INTO products (product_id, name, price, seller_id, category) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmtProduct = conn.prepareStatement(sqlProduct)) {
            stmtProduct.setInt(1, p1.getId());
            stmtProduct.setString(2, p1.getProductName());
            stmtProduct.setDouble(3, p1.getProductPrice());
            stmtProduct.setInt(4, sellerID);
            stmtProduct.setString(5, p1.getCategory().toString());
            stmtProduct.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding product: " + e.getMessage());
        }

        if (specialPackagePrice > 0) {
            String sqlSpecialPackageProduct = "INSERT INTO special_package_products (product_id, special_package_price) VALUES (?, ?)";

            try (PreparedStatement stmtSpecialProductPrice = conn.prepareStatement(sqlSpecialPackageProduct)) {
                stmtSpecialProductPrice.setInt(1, p1.getId());
                stmtSpecialProductPrice.setDouble(2, specialPackagePrice);
                stmtSpecialProductPrice.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error while add special package product to DB: " + e.getMessage());
            }
        }
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public String validPrice(double priceInput) {
        try {
            if (priceInput <= 0)
                throw new InputMismatchException(ExceptionsMessages.INVALID_PRICE_VALUE.getExceptionMessage());
        } catch (NullPointerException e) {
            return ExceptionsMessages.PRICE_EMPTY.getExceptionMessage();
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_PRICE_INPUT.getExceptionMessage();
        } catch (InputMismatchException e) {
            return e.getMessage();
        }
        return null;
    }

    public String validCategoryIndex(int categoryInput) {
        try {
            if (categoryInput <= 0 || categoryInput > Category.values().length)
                throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_CATEGORY_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public void addProductToProductArray(Product p) {
        if (allProducts.length == numberOfProducts) {
            if (allProducts.length == 0) {
                allProducts = Arrays.copyOf(allProducts, 1);
            }
            int SIZE_INCREASE = 2;
            allProducts = Arrays.copyOf(allProducts, allProducts.length * SIZE_INCREASE);
        }
        allProducts[numberOfProducts++] = p;

    }

    public void addProductNameToDoubleNameList(String name) {
        doubleNames.add(name);
        doubleNames.add(name);
    }

    public void printProductsName() {
        if (numberOfProducts != 0) {
            for (int i = 0; i < numberOfProducts; i++)
                System.out.println(allProducts[i].getProductName());
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void addToCategoryArray(Product p) {
        switch (p.getCategory()) {
            case ELECTRONIC:
                categoriesArrays.addElectronic(p);
                break;
            case CHILDREN:
                categoriesArrays.addChild(p);
                break;
            case CLOTHES:
                categoriesArrays.addClothes(p);
                break;
            case OFFICE:
                categoriesArrays.addOffice(p);
                break;
            default:
                break;
        }
    }

    public boolean isSpecialPackageProduct(Product p) {
        return p instanceof ProductSpecialPackage;
    }
}