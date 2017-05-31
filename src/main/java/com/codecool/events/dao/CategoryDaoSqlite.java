package com.codecool.events.dao;

import com.codecool.events.App;
import com.codecool.events.model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoSqlite implements CategoryDao {
    @Override
    public List<Category> getAll() throws SQLException{
        List<Category> categoryList = new ArrayList<>();
        String query = "SELECT * FROM categories;";
        PreparedStatement prepState = App.getApp().getConnection().prepareStatement(query);
        ResultSet resultSet = prepState.executeQuery();
        while (resultSet.next()) {
            categoryList.add(categoryResultSet(resultSet));
        }
        return categoryList;
    }

    @Override
    public Category find(String name) throws SQLException {
        String query = "SELECT * FROM categories WHERE name =(?)";
        PreparedStatement prepState = App.getApp().getConnection().prepareStatement(query);
        prepState.setString(1, name);
        return categoryResultSet(prepState.executeQuery());
    }

    @Override
    public Category find(int id) throws SQLException {
        String query = "SELECT * FROM categories WHERE id=(?)";
        PreparedStatement prepState = App.getApp().getConnection().prepareStatement(query);
        prepState.setInt(1, id);
        return categoryResultSet(prepState.executeQuery());
    }

    private Category categoryResultSet(ResultSet resultSet) throws SQLException {
        return new Category( resultSet.getInt("id"), resultSet.getString("name") );
    }
}
