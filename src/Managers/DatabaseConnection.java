package Managers;

import java.io.*;
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/DealZoneDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = readPasswordFromFile("C:/Users/dorzh/OneDrive/Desktop/DOR/PostgrePassword.txt");

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static String readPasswordFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.readLine().trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read DB password from file", e);
        }
    }
}
