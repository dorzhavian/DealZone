package Managers;
import Enums.Category;
import Enums.ExceptionsMessages;
import Factories.ProductFactory;
import Factories.UserFactory;
import Models.*;
import Actions.*;
import Adapters.*;
import java.sql.*;

import java.util.*;
import java.util.Date;

public class ManagerFacade {
    private final ISellerManager sellerManager;
    private final IBuyerManager buyerManager;
    private final ProductManager productManager;
    private final ActionServer  actionServer;
    private final Action1 action1;
    private final Action2 action2;
    private static ManagerFacade instance;
    private Connection conn;
    private Statement st = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;
    private static String message;
    private Stack<ProductManager.Memento>  stackProductNameList;

    public static ManagerFacade getInstance() {       // SINGLETON !!!!!!!
        if (instance == null)
            instance = new ManagerFacade();
        return instance;
    }

    private ManagerFacade() {
        sellerManager = SellerManager.getInstance();
        buyerManager = BuyerManager.getInstance();
        productManager = ProductManager.getInstance();
        actionServer = new ActionServer();
        action1 = new Action1();
        action2 = new Action2();
        stackProductNameList = new Stack<>();

        try {
            conn = DatabaseConnection.getConnection();
            System.out.println("\n-------Database connection established successfully-------");
            loadFromDatabase();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to DB", e);
        }
    }

    private void loadHistoryCartsFromDB(Connection conn) {
        String sql;
        Product p1;
        Timestamp timestamp = null;
        int productIndex, quantity, sellerIndex;

        try
        {
            for (int i = 0; i < buyerManager.getNumberOfBuyers(); i++)
            {
                for (int j = 1; j < buyerManager.getBuyers()[i].getHistoryCartsNum() + 1; j++)
                {
                    sql = "SELECT carts.cart_number, carts.paid_at, cart_items.quantity, seller_id , cart_items.product_id " +
                            "FROM carts " +
                            "JOIN cart_items ON carts.buyer_id = cart_items.buyer_id AND carts.cart_number = cart_items.cart_number " +
                            "JOIN products ON cart_items.product_id = products.product_id " +
                            "WHERE carts.is_active = false AND carts.buyer_id = ? AND carts.cart_number = ?";
                    prepStmt = conn.prepareStatement(sql);
                    prepStmt.setInt(1, buyerManager.getBuyers()[i].getId());
                    prepStmt.setInt(2, j);
                    rs = prepStmt.executeQuery();

                    Cart cart = new Cart();

                    while (rs.next()) {

                        quantity = rs.getInt("quantity");
                        timestamp = rs.getTimestamp("paid_at");
                        sellerIndex = sellerManager.findSellerIndexByID(rs.getInt("seller_id"));
                        productIndex = sellerManager.getSellers()[sellerIndex].productIndexInSellerArr(rs.getInt("product_id"));

                        if (productManager.isSpecialPackageProduct(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]))
                        {
                            p1 = ProductFactory.createProductSpecialPackageForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], ((ProductSpecialPackage) sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice());
                        } else {
                            p1 = ProductFactory.createProductForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]);
                        }

                        for (int k = 0; k < quantity; k++) {
                            cart.addProductToCart(p1);
                        }
                    }

                    if(timestamp != null) {
                        Date date = new Date(timestamp.getTime());
                        buyerManager.getBuyers()[i].insertHistoryCartFromDB(cart, j, date);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while loading history Carts from DB: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (prepStmt != null) prepStmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing DB resources: " + e.getMessage());
            }
        }
    }

    private void loadProductsFromDB(Connection conn) {
        String sql = "SELECT * FROM products WHERE product_id NOT IN (SELECT product_id FROM special_package_products)";
        int sellerIndex, productID, sellerID;
        double productPrice, specialPrice;
        String productName;
        Category category;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                productID = rs.getInt("product_id");
                productName = rs.getString("name");
                productPrice = rs.getDouble("price");
                sellerID = rs.getInt("seller_id");
                category = Category.valueOf(rs.getString("category"));

                sellerIndex = sellerManager.findSellerIndexByID(sellerID);

                makeProductToSeller(sellerIndex, productName, productPrice, category, 0, productID);

            }

        } catch (SQLException e) {
            System.err.println("Error while loading sellers from DB: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                System.err.println("Error closing DB resources: " + e.getMessage());
            }
        }

        sql = "SELECT products.product_id, products.name, products.price, products.seller_id, products.category, special_package_products.special_package_price FROM products JOIN special_package_products ON special_package_products.product_id = products.product_id";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                productID = rs.getInt("product_id");
                productName = rs.getString("name");
                productPrice = rs.getDouble("price");
                sellerID = rs.getInt("seller_id");
                category = Category.valueOf(rs.getString("category"));
                specialPrice = rs.getDouble("special_package_price");

                sellerIndex = sellerManager.findSellerIndexByID(sellerID);

                makeProductToSeller(sellerIndex, productName, productPrice, category, specialPrice, productID);

            }
        } catch (SQLException e) {
            System.err.println("Error while loading products from DB: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                System.err.println("Error closing DB resources: " + e.getMessage());
            }
        }
    }

    private void loadCartsFromDB(Connection conn) {
        String sql = "SELECT carts.buyer_id, cart_items.quantity, seller_id , cart_items.product_id FROM carts JOIN cart_items ON carts.buyer_id = cart_items.buyer_id and carts.cart_number = cart_items.cart_number JOIN products ON cart_items.product_id = products.product_id WHERE carts.is_active = true;";

        int buyerIndex, productIndex, quantity, sellerIndex;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                buyerIndex = buyerManager.findBuyerIndexByID(rs.getInt("buyer_id"));
                quantity = rs.getInt("quantity");
                sellerIndex = sellerManager.findSellerIndexByID(rs.getInt("seller_id"));
                productIndex = sellerManager.getSellers()[sellerIndex].productIndexInSellerArr(rs.getInt("product_id"));

                for(int i = 0; i < quantity; i++)
                    makeProductToBuyer(buyerIndex, sellerIndex, productIndex, true);
            }

        } catch (SQLException e) {
            System.err.println("Error while loading sellers from DB: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                System.err.println("Error closing DB resources: " + e.getMessage());
            }
        }

    }

    private void loadFromDatabase() {
        sellerManager.loadSellersFromDB(conn);
        loadProductsFromDB(conn);
        buyerManager.loadBuyersFromDB(conn);
        loadCartsFromDB(conn);
        loadHistoryCartsFromDB(conn);
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close DB connection: " + e.getMessage());
        }
    }

    public void case1() {
        String username, password;

        do {
            username = UserInput.getString("Enter username:");
            if (username == null) return;
            message = sellerManager.isExistSeller(username);
            if (message != null) System.out.println(message);
        } while (message != null);
        password = UserInput.getString("Enter password:");
        if (password == null) return;
        Seller seller = UserFactory.createSeller(username, password);
        sellerManager.addSeller(seller);
        sellerManager.addSellerToDB(seller, conn);
        System.out.println("Seller added successfully.");
    }

    public void case2() {
        String username, password, houseNum, city, state;
        do {
            username = UserInput.getString("Enter username:");
            if (username == null) return;
            message = buyerManager.isExistBuyer(username);
            if (message != null) System.out.println(message);
        } while (message != null);

        password = UserInput.getString("Enter password:");
        if (password == null ) return;

        System.out.println("Enter your full address:");
        String street = UserInput.getString("Street:");
        if (street == null) return;

        houseNum = UserInput.getString("House number:");
        if (houseNum == null) return;

        city = UserInput.getString("City:");
        if (city == null) return;

        state = UserInput.getString("State:");
        if (state == null) return;

        Address address = UserFactory.createAddress(street, houseNum, city, state);
        Buyer buyer = UserFactory.createBuyer(username, password, address);
        buyerManager.addBuyer(buyer);
        buyerManager.addBuyerToDB(buyer, conn);
        System.out.println("Buyer added successfully.");
    }

    public void case3() {
        String productName;
        double productPrice,specialPackagePrice = 0;
        int categoryIndex;

        if (sellerManager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot proceed. Returning to Menu.");
            return;
        }

        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;

        productName = UserInput.getString("Enter product name:");
        if (productName == null) return;

        do {
            productPrice = UserInput.getDouble("Enter product price:");
            if (productPrice == -1) return;
            message = productManager.validPrice(productPrice);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);

        System.out.println(Categories.categoriesByNames());

        do {
            categoryIndex = UserInput.getInt("Choose category:");
            if (categoryIndex == -1) return;
            message = productManager.validCategoryIndex(categoryIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);

        if (UserInput.getYesNo("Does this product have a special package? (Y/y) ")) {
            do {
                specialPackagePrice = UserInput.getDouble("Enter price for special package: ");
                if (specialPackagePrice == -1) return;
                message = productManager.validPrice(specialPackagePrice);
                if (message != null) {
                    System.out.println(message);
                }
            } while (message != null);
        }
        makeProductToSeller(sellerIndex, productName, productPrice, Category.values()[categoryIndex - 1], specialPackagePrice, -1);
        System.out.println("Product added successfully.");
    }

    public void case4() {
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot proceed. Returning to Menu.");
            return;
        }
        if (sellerManager.getNumberOfSellers() == 0) {
            System.out.println("Haven't sellers yet, cannot proceed. Returning to Menu.");
            return;
        }

        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;

        int sellerIndex = chooseSeller();
        if (sellerIndex == -1) return;

        if (sellerManager.getSellers()[sellerIndex].getNumOfProducts() == 0) {
            System.out.println("This seller has no products yet, cannot proceed. Returning to Menu.");
            return;
        }

        System.out.println(sellerManager.getSellers()[sellerIndex].toString());

        int productIndex;
        do {
            productIndex = UserInput.getInt("Enter product's number to add to your cart: ");
            if (productIndex == -1) return;
            message = validProductIndex(sellerIndex, productIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);

        makeProductToBuyer(buyerIndex, sellerIndex, productIndex - 1, false);

        System.out.println("Product added successfully to cart.");
    }

    public void case5 () {
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        System.out.println(buyerManager.pay(buyerIndex, conn));
    }

    public void case6(){
        System.out.println(buyerManager.buyersInfo());
    }

    public void case7() {
        System.out.println((sellerManager.sellersInfo()));
    }

    public void case8(){
        System.out.println(productsByCategory());
    }

    public void case9 () {
        int choice;
        if (buyerManager.getNumberOfBuyers() == 0) {
            System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
            return;
        }
        int buyerIndex = chooseBuyer();
        if (buyerIndex == -1) return;
        if (buyerManager.getBuyers()[buyerIndex].getHistoryCartsNum() == 0) {
            System.out.println("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
            return;
        }
        System.out.println(buyerManager.getBuyers()[buyerIndex].toString());
        System.out.println("Please choose cart number from history carts:");
        do {
            choice = UserInput.getInt("If you have products in your current cart - they will be replaced. (Enter -1 to return main menu)");
            if (choice == -1) return;
            message = isValidHistoryCartIndex(choice, buyerIndex);
            if (message != null) {
                System.out.println(message);
            }
        } while (message != null);

        buyerManager.deleteAllCartFromDB(buyerIndex , conn);
        buyerManager.replaceCarts(choice - 1, buyerIndex);
        buyerManager.updateCartFromHistory(buyerIndex, conn);

        System.out.println("Your current cart update successfully.");
    }

    public void hardcoded() {

        int sellerIndex = sellerManager.getNumberOfSellers();
        int buyerIndex = buyerManager.getNumberOfBuyers();

        // Adding 5 sellers with real names and realistic passwords
        sellerManager.addSeller(UserFactory.createSeller("Jack", "J@ck2024!"));
        sellerManager.addSeller(UserFactory.createSeller("Dor", "DorPass@123"));
        sellerManager.addSeller(UserFactory.createSeller("Tal", "Tal2024Secure"));
        sellerManager.addSeller(UserFactory.createSeller("Maya", "Maya@Home2024"));
        sellerManager.addSeller(UserFactory.createSeller("Avi", "AviPassword#45"));

        // Adding 5 buyers with usernames, realistic passwords, and addresses
        buyerManager.addBuyer(UserFactory.createBuyer("Asaf", "J@ck2024!", UserFactory.createAddress("Main St", "123", "Los Angeles", "California")));
        buyerManager.addBuyer(UserFactory.createBuyer("Yakov", "DorPass@123", UserFactory.createAddress("Oak Rd", "456", "San Francisco", "California")));
        buyerManager.addBuyer(UserFactory.createBuyer("Miki", "Tal2024Secure", UserFactory.createAddress("Pine Ave", "789", "New York", "New York")));
        buyerManager.addBuyer(UserFactory.createBuyer("Ami", "Maya@Home2024", UserFactory.createAddress("Maple St", "101", "Chicago", "Illinois")));
        buyerManager.addBuyer(UserFactory.createBuyer("Kobe", "AviPassword#45", UserFactory.createAddress("Cedar Blvd", "202", "Houston", "Texas")));

        // Adding products to sellers

        // Seller 0 products
        makeProductToSeller(sellerIndex, "TV", 325.00, Category.ELECTRONIC, 15.00, -1);
        makeProductToSeller(sellerIndex, "Shirt", 50.00, Category.CLOTHES, 0, -1);

        // Seller 1 products
        makeProductToSeller(sellerIndex + 1, "Laptop", 950.00, Category.ELECTRONIC, 50.00, -1);
        makeProductToSeller(sellerIndex + 1, "Jacket", 75.00, Category.CLOTHES, 0, -1);
        makeProductToSeller(sellerIndex + 1, "Desk Lamp", 45.00, Category.OFFICE, 5.00, -1);

        // Seller 2 products
        makeProductToSeller(sellerIndex + 2, "TV", 330.00, Category.ELECTRONIC, 18.00, -1);
        makeProductToSeller(sellerIndex + 2, "Hat", 60.00, Category.CLOTHES, 0, -1);

        // Seller 3 products
        makeProductToSeller(sellerIndex + 3, "Headphones", 120.00, Category.ELECTRONIC, 0, -1);
        makeProductToSeller(sellerIndex + 3, "Sweater", 80.00, Category.CLOTHES, 15.00, -1);
        makeProductToSeller(sellerIndex + 3, "desk lamP", 130.00, Category.OFFICE, 0, -1);

        // Seller 4 products
        makeProductToSeller(sellerIndex + 4, "Smartwatch", 220.00, Category.ELECTRONIC, 30.00, -1);
        makeProductToSeller(sellerIndex + 4, "hAt", 25.00, Category.CLOTHES, 5.00, -1);
        makeProductToSeller(sellerIndex + 4, "Office Organizer", 40.00, Category.OFFICE, 0, -1);
        makeProductToSeller(sellerIndex + 4, "Board Game", 28.00, Category.CHILDREN, 10.00, -1);

        // Adding products to carts

        // Buyer 0
        makeProductToBuyer(buyerIndex, sellerIndex, 0, false);  // Buyer 0 buys TV from Seller 0
        makeProductToBuyer(buyerIndex, sellerIndex + 1, 1, false);  // Buyer 0 buys Jacket from Seller 1

        // Buyer 1
        makeProductToBuyer(buyerIndex + 1, sellerIndex + 2, 0,false);  // Buyer 1 buys TV from Seller 2
        makeProductToBuyer(buyerIndex + 1, sellerIndex + 3, 1, false);  // Buyer 1 buys Sweater from Seller 3

        // Buyer 2
        makeProductToBuyer(buyerIndex + 2, sellerIndex + 4, 0, false);  // Buyer 2 buys Smartwatch from Seller 4
        makeProductToBuyer(buyerIndex + 2, sellerIndex, 1, false);  // Buyer 2 buys Shirt from Seller 0

        // Buyer 3
        makeProductToBuyer(buyerIndex + 3, sellerIndex + 1, 2, false);  // Buyer 3 buys Desk Lamp from Seller 1
        makeProductToBuyer(buyerIndex + 3, sellerIndex + 4, 1, false);  // Buyer 3 buys Hat from Seller 4

        // Buyer 4
        makeProductToBuyer(buyerIndex + 4, sellerIndex + 3, 0, false);  // Buyer 4 buys Headphones from Seller 3
        makeProductToBuyer(buyerIndex + 4, sellerIndex + 2, 1, false);  // Buyer 4 buys Hat from Seller 2

        System.out.println("Hardcoded added successfully!");
    }

    public void case99() {
        productManager.printProductsName();
    }

    public void case100(){
        if(productManager.getNumberOfProducts() != 0){
            Map<String,Integer> map = productManager.productsToLinkedMap();
            map.forEach((key, value) -> System.out.println(key + ".........." + value));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case101() {
        String input;
        if (productManager.getNumberOfProducts() != 0) {
            Map<String, Integer> map = productManager.productsToLinkedMap();
            input = UserInput.getString("Please enter a string: (Enter -1 to return main menu)");
            if (input == null) return;
            System.out.printf("the number of times that " + input.toLowerCase() + " appears in the OG ARRAY is %d\n" , map.get(input.toLowerCase()) == null ? 0 : map.get(input));
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case102(){

        //TASK 1:
        if(productManager.getNumberOfProducts() != 0) {
            System.out.println("Task 1: ");
            List<String> productNameList = new ArrayList<>(productManager.productsNameToLinkedSet());
            List<String> doubleNames = new ArrayList<>();
            Target<String> iterator =new ListIteratorAdapter<>(productNameList.listIterator());
            while(iterator.myHasNext()){
                String key = iterator.next();
                doubleNames.add(key);
                doubleNames.add(key);
            }
            Target<String> doubleIterator = new ListIteratorAdapter<>(doubleNames.listIterator(doubleNames.size()));
            while(doubleIterator.myHasPrevious()){
                System.out.println(doubleIterator.previous());
            }
        }else System.out.println("No products yet!");

        // TASK2 :
        System.out.println("Task 2+3: ");
        if(!productManager.getDoubleNames().isEmpty())
        {
            if (!UserInput.getYesNo("Do you want to see the output of my self-implemented iterators (Y/y or any other key to skip):"))
                return;
            actionServer.attach(action1);
            actionServer.attach(action2);
            System.out.println("\nMy custom name ArrayList iterator: ");
            Iterator<String> myIterator = productManager.myIterator();
            while(myIterator.hasNext())
                System.out.println(myIterator.next());
            actionServer.setMsg("My Iterator ended!");
            actionServer.myNotify();

            System.out.println("\nMy custom name ArrayList List iterator (Start --> End): ");
            ListIterator<String> myListIterator = productManager.myListIterator();
            while(myListIterator.hasNext()){
                System.out.println(myListIterator.next());
            }
            actionServer.setMsg("My ListIterator ended!");
            actionServer.myNotify();

            System.out.println("\nMy custom name ArrayList List iterator (End --> Start): ");
            ListIterator<String> myListIterator2 = productManager.myListIterator(productManager.getDoubleNames().size());
            while(myListIterator2.hasPrevious()){
                System.out.println(myListIterator2.previous());
            }
            actionServer.setMsg("My ListIterator ended!");
            actionServer.myNotify();

        } else System.out.println("Product double names list is empty!");
    }

    public void case103() {
        if (productManager.getNumberOfProducts() != 0) {
            Set<?> productsSet = productManager.productsToTree();
            Iterator<?> productsIterator = productsSet.iterator();
            while (productsIterator.hasNext()) {
                System.out.println(productsIterator.next().toString().toUpperCase());
            }
        } else System.out.println("No products yet! cannot be proceed. Return to main menu. ");
    }

    public void case104() {
        stackProductNameList.push(productManager.createMemento());
        System.out.println("Memento created, the name list saved.");
    }

    public void case105() {
        if(!stackProductNameList.isEmpty())
        {
            productManager.setMemento(stackProductNameList.pop());
            System.out.println("Last save of the name list has been restored");
        }
    }

    public int chooseSeller() {
        System.out.println(sellerManager.sellersNames());
        int sellerIndex;
        do {
            sellerIndex = UserInput.getInt("Please choose seller from the list above:");
            if (sellerIndex == -1) return -1;
            message = sellerManager.chooseValidSeller(sellerIndex);
            if (message != null) System.out.println(message);
        } while (message != null);
        return sellerIndex - 1;
    }

    public int chooseBuyer() {
        System.out.println(buyerManager.buyersNames());
        int buyerIndex;
        do {
            buyerIndex = UserInput.getInt("Please choose buyer from the list above:");
            if (buyerIndex == -1) return -1;
            message = buyerManager.chooseValidBuyer(buyerIndex);
            if (message != null) System.out.println(message);
        } while (message != null);
        return buyerIndex - 1;
    }

    public String validProductIndex(int sellerIndex, int productIndexInput) {
        try {
            if (productIndexInput <= 0 || productIndexInput > sellerManager.getSellers()[sellerIndex].getNumOfProducts()) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_PRODUCT_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String isValidHistoryCartIndex(int indexCartInput, int buyerIndex) {
        try {
            if (buyerManager.getBuyers()[buyerIndex].getHistoryCartsNum() < indexCartInput || indexCartInput <= 0) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_HISTORY_CART_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String productsByCategory() {
        if (sellerManager.getNumberOfSellers() == 0) {
            return "\nHaven't sellers yet - no products available, cannot be proceed. return to Menu.";
        }
        return productManager.getCategoriesArrays().toString();
    }

    public void makeProductToBuyer(int buyerIndex, int sellerIndex, int productIndex, boolean fromDB) {
        Product p1;
        double specialPackagePrice = 0;
        if(fromDB)
        {
            if (productManager.isSpecialPackageProduct(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]))
            {
                p1 = ProductFactory.createProductSpecialPackageForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], ((ProductSpecialPackage) sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice());
            } else {
                p1 = ProductFactory.createProductForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]);
            }
        }
        else
        {
            if (productManager.isSpecialPackageProduct(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]))
            {
                p1 = ProductFactory.createProductSpecialPackageForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], ((ProductSpecialPackage) sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice());
                specialPackagePrice = ((ProductSpecialPackage) sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice();
            } else {
                p1 = ProductFactory.createProductForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]);
            }
            buyerManager.insertCartItemToDB(buyerManager.getBuyers()[buyerIndex], sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], conn);
            buyerManager.updateCartAfterInsertToDB(buyerManager.getBuyers()[buyerIndex], sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], specialPackagePrice, conn);
        }
        buyerManager.addProductToBuyer(p1, buyerIndex);

    }

    public void  makeProductToSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice, int indexFromDB) {
        Product p1;
        if(indexFromDB > -1)
        {
            if (specialPackagePrice == 0) {
                p1 = ProductFactory.createProductFromDB(indexFromDB,productName, productPrice, c);
            } else {
                p1 = ProductFactory.createProductSpecialPackageFromDB(indexFromDB, productName, productPrice, c, specialPackagePrice);
            }
        } else {
            if (specialPackagePrice == 0) {
                p1 = ProductFactory.createProduct(productName, productPrice, c);
            } else {
                p1 = ProductFactory.createProductSpecialPackage(productName, productPrice, c, specialPackagePrice);
            }
            productManager.addProductToDB(p1, sellerManager.getSellers()[sellerIndex].getId(), specialPackagePrice, conn);
            sellerManager.updateProductsNumForSellerDB(sellerIndex, conn);
        }

        sellerManager.addProductToSeller(p1, sellerIndex);
        productManager.addToCategoryArray(p1);
        productManager.addProductToProductArray(p1);
        productManager.addProductNameToDoubleNameList(p1.getProductName().toLowerCase());
    }

}


