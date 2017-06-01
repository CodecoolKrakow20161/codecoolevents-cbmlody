package com.codecool.events.dao;

import com.codecool.events.model.Event;

import java.sql.SQLException;
import java.util.List;


public interface EventDao {
    void add(Event event) throws SQLException;
    boolean remove(int id) throws SQLException;
    void edit(Event event) throws SQLException;
    Event find(int id) throws SQLException;
    List<Event> getAll() throws SQLException;
}
