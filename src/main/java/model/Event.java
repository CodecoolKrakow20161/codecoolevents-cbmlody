package model;

import java.text.SimpleDateFormat;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public String getLink() {
        return link;
    }

    public String getFormattedDate() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getDate());
    }

    public String getDateLocal() {
        return new SimpleDateFormat("yyyy-MM-dd'"+'T'+"'HH:mm").format(getDate());
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
