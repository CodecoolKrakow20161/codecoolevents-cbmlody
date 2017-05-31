package com.codecool.events.dao;

import com.codecool.events.App;
import com.codecool.events.model.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EventDaoSqlite implements EventDao {
    @Override
    public void add(Event event) throws SQLException {
        String query = "INSERT INTO events (name, date, description, category_id, link) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement prepState = App.getApp().getConnection().prepareStatement(query);
        prepState.setString(1, event.getName());
        prepState.setString(2, event.getFormattedDate());
        prepState.setString(3, event.getDescription());
        prepState.setInt(4, event.getCategory().getId());
        prepState.setString(5, event.getLink());
        prepState.executeUpdate();
    }

    @Override
    public boolean remove(int id) throws SQLException {
        String query = "DELETE FROM events WHERE id = (?);";
        PreparedStatement prepState = App.getApp().getConnection().prepareStatement(query);
        prepState.setInt(1, id);
        return prepState.execute();
    }

    @Override
    public void edit(Event event) throws SQLException{
        String query = "UPDATE events SET name = (?), date = (?), description = (?), " +
                "category_id = (?), link = (?) WHERE id = (?);";
        PreparedStatement prepState = App.getApp().getConnection().prepareStatement(query);
        prepState.setString(1, event.getName());
        prepState.setString(2, event.getFormattedDate());
        prepState.setString(3, event.getDescription());
        prepState.setInt(4, event.getCategory().getId());
        prepState.setString(5, event.getLink());
        prepState.setInt(6, event.getId());
        prepState.executeUpdate();
    }

    @Override
    public List<Event> getAll() throws SQLException {
        List<Event> eventList = new ArrayList<>();
        String query = "SELECT * FROM events ORDER BY date";
        PreparedStatement prepState = App.getApp().getConnection().prepareStatement(query);
        ResultSet resultSet = prepState.executeQuery();
        while (resultSet.next()) {
            eventList.add(eventResultSet(resultSet));
        }
        return eventList;
    }

    public Event find(int id) throws SQLException {
        String query = "SELECT * FROM events WHERE id =(?);";
        PreparedStatement prepState = App.getApp().getConnection().prepareStatement(query);
        return eventResultSet(prepState.executeQuery());
    }

    private Event eventResultSet(ResultSet resultSet) throws SQLException {
        return new Event(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                eventDateHelper(resultSet.getString("date")),
                resultSet.getString("description"),
                new CategoryDaoSqlite().find(resultSet.getInt("category_id")),
                resultSet.getString("link"));
    }

    private Date eventDateHelper(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }
}
