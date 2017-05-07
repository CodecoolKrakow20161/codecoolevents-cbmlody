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
    public void add(Event event) throws SQLException {
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "INSERT INTO `events` (name, date, description, category_id, link) VALUES ('"
                + event.getName() + "','"
                + event.getFormattedDate() + "','"
                + event.getDescription().replaceAll("'", "''") + "','"
                + event.getCategory().getId() + "','"
                + event.getLink() + "');";
       statement.executeUpdate(query);
    }

    @Override
    public boolean remove(int id) throws SQLException {
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "DELETE FROM `events` WHERE id = '" + id + "';";
        statement.executeUpdate(query);
        return true;
    }

    @Override
    public void edit(Event event) throws SQLException{
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "UPDATE `events` SET " +
                "name ='" + event.getName() + "'," +
                "date = '" + event.getFormattedDate() + "'," +
                "description = '" + event.getDescription().replaceAll("'", "''") + "'," +
                "category_id = '" + event.getCategory().getId() + "'," +
                "link = '" + event.getLink() + "'" +
                "WHERE id = '" + event.getId() + "';";
        statement.executeUpdate(query);
    }

    @Override
    public List<Event> getAll() throws SQLException{
        List<Event> eventList = new ArrayList<>();
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "SELECT * FROM `events`";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            eventList.add(eventResultSet(resultSet));
        }
        return eventList;
    }

    @Override
    public List<Event> getBy(Category category) throws SQLException {
        return null;
    }

    public Event getOne(int id) throws SQLException{
        Event event = null;
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "SELECT * FROM `events` WHERE id ='" + id + "'";
        ResultSet resultSet = statement.executeQuery(query);
        event = eventResultSet(resultSet);
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
