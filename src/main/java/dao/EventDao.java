package dao;

import model.Event;

import java.util.Date;
import java.util.List;


public interface EventDao {
    void add();
    void remove(int id);
    void edit();
    List<Event> getAll();
    List<Event> getBy(Date date);

}
