package com.codecool.events.dao;

import com.codecool.events.App;
import com.codecool.events.model.Category;
import com.codecool.events.model.Event;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EventDaoSqlite implements EventDao {
    @Override
    public void add(Event event) throws Sql2oException {
        String query = "INSERT INTO events (name, date, description, category_id, link) VALUES (:name, :date, :desc, :catId, :link);";
        try (Connection con = App.getApp().getConnection()){
            con.createQuery(query).addParameter("name", event.getName())
                    .addParameter("date", event.getFormattedDate())
                    .addParameter("desc", event.getDescription().replaceAll("'", "''"))
                    .addParameter("catId", event.getCategory().getId())
                    .addParameter("link", event.getLink())
                    .executeUpdate();
        }
    }

    @Override
    public boolean remove(int id) throws Sql2oException {
        String query = "DELETE FROM events WHERE id = :id;";
        try (Connection con = App.getApp().getConnection()) {
            con.createQuery(query).addParameter("id", id).executeUpdate();
        }
        return true;
    }

    @Override
    public void edit(Event event) throws Sql2oException {
        String query = "UPDATE events SET name = :name, date = :date, description = :desc, " +
                "category_id = :catId, link = :link WHERE id = :id;";
        try (Connection con = App.getApp().getConnection()) {
            con.createQuery(query).addParameter("name", event.getName())
                    .addParameter("date", event.getFormattedDate())
                    .addParameter("desc", event.getDescription())
                    .addParameter("catId", event.getCategory().getId())
                    .addParameter("link", event.getLink())
                    .addParameter("id", event.getId())
                    .executeUpdate();
        }
    }

    @Override
    public List<Event> getAll() throws Sql2oException {
        String query = "SELECT * FROM events ORDER BY date;";
        Table tab;
        try (Connection con = App.getApp().getConnection()) {
            tab = con.createQuery(query).executeAndFetchTable();
        }
        return getEventsFromTable(tab);
    }

    public Event find(int id) {
        String query = "SELECT * FROM events WHERE id = :idVal;";
        Table tab;
        try (Connection con = App.getApp().getConnection()) {
            tab = con.createQuery(query).addParameter("idVal", id).executeAndFetchTable();
        }
        List<Event> eventList = getEventsFromTable(tab);
        return eventList.isEmpty() ? null : eventList.get(0);
    }

    private List<Event> getEventsFromTable(Table table) {
        List<Event> eventList = new ArrayList<>();
        for (Row row : table.rows()) {
            int id = row.getInteger("id");
            String name = row.getString("name");
            Date date = eventDateHelper(row.getString("date"));
            String description = row.getString("description");
            Category category = new CategoryDaoSqlite().find(row.getInteger("category_id"));
            String link = row.getString("link");
            eventList.add(new Event(id, name, date, description, category, link));
        }
        return eventList;
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
