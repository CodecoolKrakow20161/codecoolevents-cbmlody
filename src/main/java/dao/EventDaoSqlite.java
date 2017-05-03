package dao;

import model.Category;
import model.Event;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EventDaoSqlite implements EventDao {
    @Override
    public void add() {
    }

    @Override
    public void remove(int id) {
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "DELETE * FROM `events` WHERE id = '" + id + "'";
        try {
            statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit() {
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        Statement statement = DatabaseConnect.getInstance().getStatement();
        String query = "SELECT * FROM `events`";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            events.add(eventResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public List<Event> getBy(Date date) {
        return null;
    }

    private Event eventResultSet(ResultSet resultSet) throws SQLException {
        return new Event(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDate("date"),
                resultSet.getString("description"),
                Category.find(resultSet.getInt("id")),
                resultSet.getString("link"));
    }
}
