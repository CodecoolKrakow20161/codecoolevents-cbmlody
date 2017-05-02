package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnect {
    private final static DatabaseConnect CONNECT = new DatabaseConnect();
    private Connection connection;

    public static DatabaseConnect getInstance() {
        return CONNECT;
    }

    private DatabaseConnect() {
        this.openConnection();
    }

    public void openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/database.db");
            System.out.println("Connection with DB established...");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void connectionClose() {
        try {
            connection.close();
            System.out.println("Connection terminated...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
