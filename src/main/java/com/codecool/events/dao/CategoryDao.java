package com.codecool.events.dao;

import com.codecool.events.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    List<Category> getAll() throws SQLException;
    Category find(String arg) throws SQLException;
    Category find(int arg) throws SQLException;
}
