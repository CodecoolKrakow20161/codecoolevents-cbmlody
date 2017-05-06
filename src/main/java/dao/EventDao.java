package dao;

import model.Category;
import model.Event;

import java.sql.SQLException;
import java.util.List;


public interface EventDao {
    void add(Event event);
    boolean remove(int id);
    void edit();
    List<Event> getAll() throws SQLException;
    List<Event> getBy(Category category);

}
