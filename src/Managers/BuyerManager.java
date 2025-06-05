package Managers;

import Comparators.CompareBuyersByName;
import Enums.ExceptionsMessages;
import Exceptions.EmptyCartPayException;
import Factories.UserFactory;
import Models.*;

import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;

public class BuyerManager implements IBuyerManager {
    private Buyer[] buyers;
    private int numberOfBuyers;
    private final Comparator<Buyer> comparatorBuyer;
    private static BuyerManager instance;
    private Statement st = null;
    private ResultSet rs = null;

    public static BuyerManager getInstance() {                          // SINGLETON !!!!!!!
        if (instance == null)
            instance = new BuyerManager();
        return instance;
    }

    private BuyerManager() {
        buyers = new Buyer[0];
        comparatorBuyer = new CompareBuyersByName();
    }

    public int getNumberOfBuyers() {
        return numberOfBuyers;
    }

    public Buyer[] getBuyers() {
        return buyers;
    }

    public void loadBuyersFromDB(Connection conn) {
        String sql = "SELECT users.user_id, username,  password, num_of_history_cart, street, house_num, city, state FROM users JOIN buyers ON users.user_id = buyers.user_id";
        int id, numOfHistoryCart;
        String username, password, street, houseNum, city, state;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("user_id");
                username = rs.getString("username");
                password = rs.getString("password");
                numOfHistoryCart = rs.getInt("num_of_history_cart");
                street = rs.getString("street");
                houseNum = rs.getString("house_num");
                city = rs.getString("city");
                state = rs.getString("state");

                addBuyer(UserFactory.createBuyerFromDB(id, username, password, UserFactory.createAddress(street, houseNum, city, state),numOfHistoryCart));

            }
        } catch (SQLException e) {
            System.err.println("Error while loading buyers from DB: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                System.err.println("Error closing DB resources: " + e.getMessage());
            }
        }
    }

    public String isExistBuyer (String name) {
        for (int i = 0; i < numberOfBuyers; i++) {
            if (buyers[i].getUserName().equalsIgnoreCase(name)) return "Buyer name already EXIST, please try again!";
        }
        return null;
    }

    public String chooseValidBuyer(int index) {
        try {
            if (index > numberOfBuyers || index <= 0) throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_BUYER_INDEX.getExceptionMessage());
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        }
        return null;
    }

    public void addBuyer(Buyer buyer) {
        if (buyers.length == numberOfBuyers) {
            if (buyers.length == 0) {
                buyers = Arrays.copyOf(buyers, 1);
            }
            int SIZE_INCREASE = 2;
            buyers = Arrays.copyOf(buyers, buyers.length * SIZE_INCREASE);
        }
        buyers[numberOfBuyers++] = buyer;
    }

    public String buyersInfo() {
        if (numberOfBuyers == 0) {
            return "\nHaven't buyers yet, cannot be proceed. return to Menu.";
        }
        StringBuilder sb = new StringBuilder("\nBuyers info:\n--------------\n");
        Arrays.sort(buyers, 0, numberOfBuyers, comparatorBuyer);
        for (int i = 0; i < numberOfBuyers; i++) {
            sb.append(i + 1).append(") ");
            sb.append(buyers[i].toString());
        }
        return sb.toString();
    }

    public String buyersNames() {
        StringBuilder sb = new StringBuilder("Buyer's:\n--------------\n");
        for (int i = 0; i < numberOfBuyers; i++) {
            sb.append(i + 1).append(") ").append(buyers[i].getUserName()).append("\n");
        }
        return sb.toString();
    }

    public void addProductToBuyer(Product p, int buyerIndex) {
        buyers[buyerIndex].getCurrentCart().addProductToCart(p);
    }

    public String pay(int buyerIndex) {
        try {
            if (buyers[buyerIndex].getCurrentCart().getNumOfProducts() == 0) throw new EmptyCartPayException(buyers[buyerIndex].getUserName());
        } catch (EmptyCartPayException e) {
            return e.getMessage();
        }
        buyers[buyerIndex].payAndMakeHistoryCart();
        return """
                 ____   _ __   ____  __ _____ _   _ _____                              \s
                |  _ \\ / \\\\ \\ / /  \\/  | ____| \\ | |_   _|                             \s
                | |_) / _ \\\\ V /| |\\/| |  _| |  \\| | | |                               \s
                |  __/ ___ \\| | | |  | | |___| |\\  | | |                               \s
                |_| /_/   \\_\\_|_|_|_ |_|_____|_|_\\_|_|_|  _____ ____  _____ _   _ _    \s
                            / ___|| | | |/ ___/ ___/ ___|| ____/ ___||  ___| | | | |   \s
                            \\___ \\| | | | |   \\___ \\___ \\|  _| \\___ \\| |_  | | | | |   \s
                             ___) | |_| | |___ ___) |__) | |___ ___) |  _| | |_| | |___\s
                            |____/ \\___/ \\____|____/____/|_____|____/|_|    \\___/|_____|""";
    }

    public void replaceCarts(int historyCartIndex, int buyerIndex) {
        buyers[buyerIndex].setCurrentCart(buyers[buyerIndex].getHistoryCart()[historyCartIndex]);
    }

    public int findBuyerIndexByID (int id) {
        int i;
        for (i = 0; i < numberOfBuyers; i++)
        {
            if (buyers[i].getId() == id)
                break;
        }
        return i;
    }

    @Override
    public void addBuyerToDB(Buyer buyer, Connection conn) {
        String sqlInsertBuyer = "INSERT INTO buyers (user_id, num_of_history_cart, street, house_num, city, state) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmtBuyer = null;

        buyer.addUserToDB(conn);

        try {
            stmtBuyer = conn.prepareStatement(sqlInsertBuyer);
            stmtBuyer.setInt(1,buyer.getId());
            stmtBuyer.setInt(2,buyer.getHistoryCartsNum());
            stmtBuyer.setString(3,buyer.getAddress().getStreet());
            stmtBuyer.setString(4,buyer.getAddress().getHouseNum());
            stmtBuyer.setString(5,buyer.getAddress().getCity());
            stmtBuyer.setString(6,buyer.getAddress().getState());
            stmtBuyer.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error while writing buyer to DB: " + e.getMessage());
        } finally {
            try {
                if (stmtBuyer != null) stmtBuyer.close();
            } catch (SQLException e) {
                System.err.println("Error closing DB resources: " + e.getMessage());
            }
        }
    }

}
