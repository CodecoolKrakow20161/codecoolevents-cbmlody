package dao;

import model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoSqlite implements CategoryDao {
    @Override
    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "SELECT * FROM `categories`";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                categoryList.add(categoryResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    private Category categoryResultSet(ResultSet resultSet) throws SQLException{
        return new Category( resultSet.getInt("id"), resultSet.getString("name") );
    }
}
