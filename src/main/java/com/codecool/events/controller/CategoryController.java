package com.codecool.events.controller;

import com.codecool.events.dao.CategoryDaoSqlite;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CategoryController {
    public static ModelAndView renderCategories(Request req, Response res) throws SQLException {
        Map categories = new HashMap<>();
        categories.put("categoriesContainer", new CategoryDaoSqlite().getAll());
        return new ModelAndView(categories, "product/add");
    }
}
