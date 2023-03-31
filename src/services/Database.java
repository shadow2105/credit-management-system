package services;

import java.sql.*;

public class Database {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/java2";
    private static final String USERNAME = "java2";
    private static final String PASSWORD = "java2";

    public static Connection connectDatabase() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }

        return connection;
    }
}
