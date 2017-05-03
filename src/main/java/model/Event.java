package model;

import java.util.Date;

public class Event {
    private int id;
    private String name;
    private Date date;
    private String description;
    private Category category;
    private String link;

    public Event(String name, Date date, String description, Category category, String link) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.category = category;
        this.link = link;
    }

    public Event(int id, String name, Date date, String description, Category category, String link) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.category = category;
        this.link = link;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
