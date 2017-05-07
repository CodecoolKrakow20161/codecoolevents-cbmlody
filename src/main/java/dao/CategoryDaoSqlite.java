package dao;

import model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoSqlite implements CategoryDao {
    @Override
    public List<Category> getAll() throws SQLException{
        List<Category> categoryList = new ArrayList<>();
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "SELECT * FROM `categories`;";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            categoryList.add(categoryResultSet(resultSet));
        }
        return categoryList;
    }

    @Override
    public Category find(String name) {
        String query = "SELECT * FROM `categories` WHERE name ='" + name + "'";
        return executeQuery(query);
    }

    @Override
    public Category find(int id) {
        String query = "SELECT * FROM `categories` WHERE id='" + id + "'";
        return executeQuery(query);
    }

    private Category categoryResultSet(ResultSet resultSet) throws SQLException{
        return new Category( resultSet.getInt("id"), resultSet.getString("name") );
    }

    private Category executeQuery (String query) throws SQLException {
        Category category = null;
        Statement statement = DatabaseConnect.getInstance().getStatement();
        ResultSet resultSet = statement.executeQuery(query);
        category = categoryResultSet(resultSet);
        return category;
    }
}
