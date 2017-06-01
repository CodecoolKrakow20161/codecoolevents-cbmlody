package com.codecool.events.dao;


import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public final class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        this.openConnection();
    }

    public void openConnection() {
        try {
            String dbPath = System.getenv("JDBC_DATABASE_URL");
            Sql2o sql2o = new Sql2o(dbPath);
            connection = sql2o.open();
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
            for (String query : queries) {
                if (!query.trim().equals("")) {
                    connection.createQuery(query).executeUpdate();
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
        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
