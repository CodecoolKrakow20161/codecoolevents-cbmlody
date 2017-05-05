package dao;

import model.Category;
import model.Event;

import java.util.List;


public interface EventDao {
    void add(Event event);
    boolean remove(int id);
    void edit();
    List<Event> getAll();
    List<Event> getBy(Category category);

}
