package Managers;

import Enums.Category;
import Enums.ExceptionsMessages;
import Models.Categories;
import Models.Product;
import Models.ProductSpecialPackage;

import java.util.*;

public class ProductManager implements IProductManager {

    private Product[] allProducts;
    private int numberOfProducts;
    private final Categories categoriesArrays;
    private  List<String> doubleNames = new ArrayList<>();
    private static ProductManager instance;

    public static ProductManager getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new ProductManager();
        return instance;
    }

    public ProductManager() {
        categoriesArrays = new Categories();
        allProducts = new Product[0];
    }

    public void setDoubleNames(List<String> doubleNames) {
        this.doubleNames = doubleNames;
    }

    public List<String> getDoubleNames() {
        return doubleNames;
    }

    public Categories getCategoriesArrays() {
        return categoriesArrays;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public String validPrice(double priceInput) {
        try {
            if (priceInput <= 0) throw new InputMismatchException(ExceptionsMessages.INVALID_PRICE_VALUE.getExceptionMessage());
        } catch (NullPointerException e){
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
            if (categoryInput <= 0 || categoryInput > Category.values().length) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_CATEGORY_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public void addProductName(Product p){
        if (allProducts.length == numberOfProducts) {
            if (allProducts.length == 0) {
                allProducts = Arrays.copyOf(allProducts, 1);
            }
            int SIZE_INCREASE = 2;
            allProducts = Arrays.copyOf(allProducts, allProducts.length * SIZE_INCREASE);
        }
        allProducts[numberOfProducts++] = p;

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

    public boolean isSpecialPackageProduct (Product p) {
        return p instanceof ProductSpecialPackage;
    }

    public Map<String, Integer> productsToLinkedMap(){                   // Object Oriented Design - Assignment 1
        Map<String, Integer> map = new LinkedHashMap<>();
        for(int i = 0; i < numberOfProducts; i++){
            String key = allProducts[i].getProductName().toLowerCase();
            if (map.containsKey(key)){
                map.put(key,map.get(key) + 1);
            }
            else{
                map.put(key, 1);
            }
        }
        return map;
    }

    public Set<Product> productsToTree() {                   // Object Oriented Design - Assignment 1
        Set<Product> treeSet = new TreeSet<>((p1, p2) -> {
            int lengthCompare = Integer.compare(p1.getProductName().length(), p2.getProductName().length());
            if (lengthCompare == 0) {
                return p1.getProductName().compareToIgnoreCase(p2.getProductName());
            }
            return lengthCompare;
        });
        for(int i = 0; i < numberOfProducts; i++){
            treeSet.add(allProducts[i]);
        }
        return treeSet;
    }

    public Set<String> productsNameToLinkedSet() {                  // Object Oriented Design - Assignment 1
        Set <String> set = new LinkedHashSet<>();
        for (int i = 0; i < numberOfProducts; i++)
            set.add(allProducts[i].getProductName().toLowerCase());
        return set;
    }

    public ListIterator<String> myListIterator() {return new ConcreteListIterator(0);}

    public ListIterator<String> myListIterator(int index) {return new ConcreteListIterator(index);}

    // function that uses Iterator class
    public Iterator<String> myIterator(){
        return new ConcreteIterator();
    }

    // New iterator class

    private class ConcreteIterator implements Iterator<String>{
        int cur = 0;

        @Override
        public boolean hasNext() {

            return cur < doubleNames.size();
      }

        @Override
        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            String name = doubleNames.get(cur);
            cur++;
         return name;
        }
    }

    private class ConcreteListIterator extends ConcreteIterator implements ListIterator<String>{
        public ConcreteListIterator(int index){
            cur = index;
        }


        @Override
        public boolean hasPrevious() {
            return cur > 0;
        }

        @Override
        public String previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            return doubleNames.get(--cur);
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(String s) {

        }

        @Override
        public void add(String s) {

        }
    }
}
