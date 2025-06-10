package Managers;
import Enums.Category;
import Enums.ExceptionsMessages;
import Factories.ProductFactory;
import Factories.UserFactory;
import Models.*;
import java.sql.*;
import java.util.Date;

public class ManagerFacade {
    private final ISellerManager sellerManager;
    private final IBuyerManager buyerManager;
    private final ProductManager productManager;
    private static ManagerFacade instance;
    private Connection conn;
    private Statement st = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;
    private static String message;

    public static ManagerFacade getInstance() {       // SINGLETON !!!!!!!
        if (instance == null)
            instance = new ManagerFacade();
        return instance;
    }

    private ManagerFacade() {
        sellerManager = SellerManager.getInstance();
        buyerManager = BuyerManager.getInstance();
        productManager = ProductManager.getInstance();

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
        Timestamp timestamp;

        try {
            for (int i = 0; i < buyerManager.getNumberOfBuyers(); i++) {
                for (int j = 1; j <= buyerManager.getBuyers()[i].getHistoryCartsNum(); j++) {
                    sql = "SELECT carts.cart_number, carts.paid_at, cart_items.quantity, seller_id, cart_items.product_id " +
                            "FROM carts " +
                            "JOIN cart_items ON carts.buyer_id = cart_items.buyer_id AND carts.cart_number = cart_items.cart_number " +
                            "JOIN products ON cart_items.product_id = products.product_id " +
                            "WHERE carts.is_active = false AND carts.buyer_id = ? AND carts.cart_number = ?";

                    try (
                            PreparedStatement prepStmt = conn.prepareStatement(sql)
                    ) {
                        prepStmt.setInt(1, buyerManager.getBuyers()[i].getId());
                        prepStmt.setInt(2, j);

                        try (ResultSet rs = prepStmt.executeQuery()) {
                            Cart cart = new Cart();
                            timestamp = null;

                            while (rs.next()) {
                                int quantity = rs.getInt("quantity");
                                timestamp = rs.getTimestamp("paid_at");
                                int sellerIndex = sellerManager.findSellerIndexByID(rs.getInt("seller_id"));
                                int productIndex = sellerManager.getSellers()[sellerIndex].productIndexInSellerArr(rs.getInt("product_id"));
                                Product sellerProduct = sellerManager.getSellers()[sellerIndex].getProducts()[productIndex];

                                if (productManager.isSpecialPackageProduct(sellerProduct)) {
                                    double specialPrice = ((ProductSpecialPackage) sellerProduct).getSpecialPackagePrice();
                                    p1 = ProductFactory.createProductSpecialPackageForBuyer(sellerProduct, specialPrice);
                                } else {
                                    p1 = ProductFactory.createProductForBuyer(sellerProduct);
                                }

                                for (int k = 0; k < quantity; k++) {
                                    cart.addProductToCart(p1);
                                }
                            }

                            if (timestamp != null) {
                                Date date = new Date(timestamp.getTime());
                                buyerManager.getBuyers()[i].insertHistoryCartFromDB(cart, j, date);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while loading history Carts from DB: " + e.getMessage());
        }
    }

    private void loadProductsFromDB(Connection conn) {
        String sqlRegularProducts = "SELECT * FROM products WHERE product_id NOT IN (SELECT product_id FROM special_package_products)";
        String sqlSpecialProducts = "SELECT products.product_id, products.name, products.price, products.seller_id, products.category, special_package_products.special_package_price " +
                "FROM products JOIN special_package_products ON special_package_products.product_id = products.product_id";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sqlRegularProducts)
        ) {
            while (rs.next()) {
                int productID = rs.getInt("product_id");
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                int sellerID = rs.getInt("seller_id");
                Category category = Category.valueOf(rs.getString("category"));

                int sellerIndex = sellerManager.findSellerIndexByID(sellerID);

                makeProductToSeller(sellerIndex, productName, productPrice, category, 0, productID);
            }
        } catch (SQLException e) {
            System.err.println("Error while loading regular products from DB: " + e.getMessage());
        }

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sqlSpecialProducts)
        ) {
            while (rs.next()) {
                int productID = rs.getInt("product_id");
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                int sellerID = rs.getInt("seller_id");
                Category category = Category.valueOf(rs.getString("category"));
                double specialPrice = rs.getDouble("special_package_price");

                int sellerIndex = sellerManager.findSellerIndexByID(sellerID);

                makeProductToSeller(sellerIndex, productName, productPrice, category, specialPrice, productID);
            }
        } catch (SQLException e) {
            System.err.println("Error while loading special package products from DB: " + e.getMessage());
        }
    }


    private void loadCartsFromDB(Connection conn) {
        String sql = "SELECT carts.buyer_id, cart_items.quantity, seller_id, cart_items.product_id " +
                "FROM carts " +
                "JOIN cart_items ON carts.buyer_id = cart_items.buyer_id AND carts.cart_number = cart_items.cart_number " +
                "JOIN products ON cart_items.product_id = products.product_id " +
                "WHERE carts.is_active = true";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                int buyerIndex = buyerManager.findBuyerIndexByID(rs.getInt("buyer_id"));
                int quantity = rs.getInt("quantity");
                int sellerIndex = sellerManager.findSellerIndexByID(rs.getInt("seller_id"));
                int productIndex = sellerManager.getSellers()[sellerIndex].productIndexInSellerArr(rs.getInt("product_id"));

                for (int i = 0; i < quantity; i++) {
                    makeProductToBuyer(buyerIndex, sellerIndex, productIndex, true);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while loading carts from DB: " + e.getMessage());
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
        buyerManager.updateCartFromHistoryToDB(buyerIndex, conn);

        System.out.println("Your current cart update successfully.");
    }

    public void case10() {
        productManager.printProductsName();
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
        //double specialPackagePrice = 0;
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
                //specialPackagePrice = ((ProductSpecialPackage) sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice();
            } else {
                p1 = ProductFactory.createProductForBuyer(sellerManager.getSellers()[sellerIndex].getProducts()[productIndex]);
            }
            buyerManager.insertCartItemToDB(buyerManager.getBuyers()[buyerIndex], sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], conn);
            //buyerManager.updateCartAfterInsertToDB(buyerManager.getBuyers()[buyerIndex], sellerManager.getSellers()[sellerIndex].getProducts()[productIndex], specialPackagePrice, conn);
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
            //sellerManager.updateProductsNumForSellerDB(sellerIndex, conn);
        }

        sellerManager.addProductToSeller(p1, sellerIndex);
        productManager.addToCategoryArray(p1);
        productManager.addProductToProductArray(p1);
        productManager.addProductNameToDoubleNameList(p1.getProductName().toLowerCase());
    }

}


