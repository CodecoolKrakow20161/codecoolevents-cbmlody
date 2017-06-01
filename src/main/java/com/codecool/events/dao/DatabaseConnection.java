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
            String dbPath = "jdbc:postgresql://ec2-107-21-108-204.compute-1.amazonaws.com:5432/d3g7vnn5hnehke";
            Sql2o sql2o = new Sql2o(dbPath, "nplmytlzrudpcx", "b7ac87f3e99173a89390fe7b673d058f0bd51fba089e0b9e87df2b4f629452bb");
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
