package com.codecool.events.dao;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        this.openConnection();
    }

    public void openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String dbPath = System.getenv("JDBC_DATABASE_URL");
//            connection = DriverManager.getConnection("jdbc:postgresql://ec2-107-21-108-204.compute-1.amazonaws.com:5432/d3g7vnn5hnehke?user=nplmytlzrudpcx&password=b7ac87f3e99173a89390fe7b673d058f0bd51fba089e0b9e87df2b4f629452bb&sslmode=require");
            connection = DriverManager.getConnection(dbPath);
            System.out.println("Connection with DB established...");
        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
        }
    }

    public void queryFromFile(String path) {
        String string;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(new File(path));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            bufferedReader.close();

            String[] queries = stringBuilder.toString().split(";");
            Statement statement = getStatement();
            for (String query : queries) {
                if (!query.trim().equals("")) {
                    statement.executeUpdate(query);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
            System.out.println("Something went wrong while loading queries from file...\nClosing connection...");
            connectionClose();
        }
    }

    public void connectionClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e.toString());
        }
    }

    private Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Error: " + e.toString());
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
