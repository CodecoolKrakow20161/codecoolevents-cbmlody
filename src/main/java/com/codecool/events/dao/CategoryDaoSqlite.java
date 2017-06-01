package com.codecool.events.dao;

import com.codecool.events.App;
import com.codecool.events.model.Category;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;

public class CategoryDaoSqlite implements CategoryDao {
    @Override
    public List<Category> getAll() throws Sql2oException {
        String query = "SELECT * FROM categories;";
        Table tab;
        try (Connection con = App.getApp().getConnection()) {
            tab = con.createQuery(query).executeAndFetchTable();
        }
        return getCategoriesFromTable(tab);
    }

    @Override
    public Category find(String name) throws Sql2oException {
        String query = "SELECT id, name FROM categories WHERE name LIKE :nameVal";
        Table tab;
        try (Connection con = App.getApp().getConnection()) {
            tab = con.createQuery(query).addParameter("nameVal", name).executeAndFetchTable();
        }
        List<Category> categoryList = getCategoriesFromTable(tab);
        return categoryList.isEmpty() ? null : categoryList.get(0);
    }

    @Override
    public Category find(int id) throws Sql2oException {
        String query = "SELECT id, name FROM categories WHERE id = :idVal";
        Table tab;
        try (Connection con = App.getApp().getConnection()) {
            tab = con.createQuery(query).addParameter("idVal", id).executeAndFetchTable();
        }
        List<Category> categoryList = getCategoriesFromTable(tab);
        return categoryList.isEmpty() ? null : categoryList.get(0);
    }

    private List<Category> getCategoriesFromTable(Table table) {
        List<Category> categoryList = new ArrayList<>();
        for (Row row : table.rows()) {
            int id = row.getInteger("id");
            String name = row.getString("name");
            categoryList.add(new Category(id, name));
        }
        return categoryList;
    }
}
