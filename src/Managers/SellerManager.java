package Managers;

import Comparators.CompareSellersByProductsNumber;
import Enums.ExceptionsMessages;
import Factories.UserFactory;
import Models.*;

import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;

public class SellerManager implements ISellerManager{
    private Seller[] sellers;
    private int numberOfSellers;
    private final Comparator<Seller> comparatorSeller;
    private static SellerManager instance;
    private Statement st = null;
    private ResultSet rs = null;

    public static SellerManager getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new SellerManager();
        return instance;
    }

    private SellerManager() {
        sellers = new Seller[0];
        comparatorSeller = new CompareSellersByProductsNumber();
    }

    public int getNumberOfSellers() {
        return numberOfSellers;
    }

    public Seller[] getSellers() {
        return sellers;
    }

    @Override
    public void loadSellersFromDB(Connection conn) {
        String sql = "SELECT users.user_id, users.username, users.password FROM users JOIN sellers ON users.user_id = sellers.user_id";

        try (
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");

                addSeller(UserFactory.createSellerFromDB(id, username, password));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading sellers from DB: " + e.getMessage());
        }
    }


    public String isExistSeller (String name) {
        for (int i = 0; i < numberOfSellers; i++) {
            if (sellers[i].getUserName().equalsIgnoreCase(name)) return "Seller name already EXIST, please try again!";
        }
        return null;
    }

    public void addSeller(Seller seller) {
        if (sellers.length == numberOfSellers) {
            if (sellers.length == 0) {
                sellers = Arrays.copyOf(sellers, 1);
            }
            int SIZE_INCREASE = 2;
            sellers = Arrays.copyOf(sellers, sellers.length * SIZE_INCREASE);
        }
        sellers[numberOfSellers++] = seller;
    }

    @Override
    public void addSellerToDB(Seller seller, Connection conn) {
        String sqlInsertSeller = "INSERT INTO sellers (user_id, num_of_products) VALUES (?, 0)";
        seller.addUserToDB(conn, "SELLER");

        try (PreparedStatement stmtSeller = conn.prepareStatement(sqlInsertSeller)) {
            stmtSeller.setInt(1, seller.getId());
            stmtSeller.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while writing sellers to DB: " + e.getMessage());
        }
    }

    /*
    @Override
    public void updateProductsNumForSellerDB(int sellerIndex, Connection conn) {
        String sql = "UPDATE sellers SET num_of_products = num_of_products + 1 WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sellers[sellerIndex].getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while incrementing seller's product count: " + e.getMessage());
        }
    }
    */


    public String sellersInfo() {
        if (numberOfSellers == 0) {
            return "\nHaven't sellers yet, cannot be proceed. return to Menu.";
        }
        StringBuilder sb = new StringBuilder("\nSellers info:\n--------------\n");
        Arrays.sort(sellers, 0, numberOfSellers, comparatorSeller);
        for (int i = 0; i < numberOfSellers; i++) {
            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append(": (ID: ").append(sellers[i].getId()).append(")");
            sb.append(sellers[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public String sellersNames() {
        StringBuilder sb = new StringBuilder("Seller's:\n--------------\n");
        for (int i = 0; i < numberOfSellers; i++) {
            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append("\n");
        }
        return sb.toString();
    }

    public String chooseValidSeller(int index) {
        try {
            if (index > numberOfSellers || index <= 0) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_SELLER_INDEX.getExceptionMessage());
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        }
        return null;
    }

    public void addProductToSeller(Product p, int sellerIndex) {
        sellers[sellerIndex].addProduct(p);
    }

    public int findSellerIndexByID (int id) {
        int i;
        for (i = 0; i < numberOfSellers; i++)
        {
            if (sellers[i].getId() == id)
                break;
        }
        return i;
    }
}
