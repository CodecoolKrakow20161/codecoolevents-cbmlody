package dao;

import model.Category;
import model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EventDaoSqlite implements EventDao {
    @Override
    public void add(Event event) {
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "INSERT INTO `events` (name, date, description, category_id, link) VALUES ('"
                + event.getName() + "','"
                + event.getFormattedDate() + "','"
                + event.getDescription().replaceAll("'", "''") + "','"
                + event.getCategory().getId() + "','"
                + event.getLink() + "')";
        try {
           statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(int id) {
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "DELETE FROM `events` WHERE id = '" + id + "'";
        try {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void edit() {
    }

    @Override
    public List<Event> getAll() {
        List<Event> eventList = new ArrayList<>();
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "SELECT * FROM `events`";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                eventList.add(eventResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventList;
    }

    @Override
    public List<Event> getBy(Category category) {
        return null;
    }

    public Event getOne(int id) {
        Event event = null;
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "SELECT * FROM `events` WHERE id ='" + id + "'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            event = eventResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
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
