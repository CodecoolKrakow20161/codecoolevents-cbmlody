package model;

import dao.DatabaseConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Category {
    private int id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category find(int id) {
        Category category = null;
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "SELECT * FROM `category` WHERE id ='" + id + "'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            return new Category( resultSet.getInt("id"), resultSet.getString("name") );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
