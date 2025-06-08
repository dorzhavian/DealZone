package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class User {
    private static int idGenerator = 0;
    protected int id;
    protected String userName;
    protected String password;

    public User(String userName, String password) {
        this.id = ++idGenerator;
        this.userName = userName;
        this.password = password;
    }

    public User(int userID, String userName, String password) {
        this.id = userID;
        this.userName = userName;
        this.password = password;

        if (userID > idGenerator) {
            setIdGenerator(userID);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void addUserToDB(Connection conn, String type) {
        String sqlInsertUser = "INSERT INTO users (user_id, username, password, user_type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmtUser = conn.prepareStatement(sqlInsertUser)) {
            stmtUser.setInt(1, this.getId());
            stmtUser.setString(2, this.getUserName());
            stmtUser.setString(3, this.getPassword());
            stmtUser.setString(4, type);
            stmtUser.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while writing User to DB: " + e.getMessage());
        }
    }

    public static void setIdGenerator(int idGenerator) {
        User.idGenerator = idGenerator;
    }
}
