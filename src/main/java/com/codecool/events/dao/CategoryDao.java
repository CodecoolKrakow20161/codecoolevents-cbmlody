package com.codecool.events.dao;

import com.codecool.events.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
    Category find(String arg);
    Category find(int arg);
}
