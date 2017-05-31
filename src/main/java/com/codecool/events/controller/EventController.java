package com.codecool.events.controller;

import com.codecool.events.dao.CategoryDaoSqlite;
import com.codecool.events.dao.EventDaoSqlite;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class EventController {
    public static ModelAndView renderEvents(Request req, Response res) throws SQLException{
        Map params = new HashMap<>();
        params.put("eventContainer", new EventDaoSqlite().getAll());
        params.put("categories", new CategoryDaoSqlite().getAll());
        return new ModelAndView(params, "product/events");
    }

    public static ModelAndView eventDetails(Request req, Response res, int id) throws SQLException{
        Map event = new HashMap<>();
        event.put("eventDetails", new EventDaoSqlite().find(id));
        return new ModelAndView(event, "product/details");
    }

    public static ModelAndView removeEvent(Request req, Response res, int id) throws SQLException {
        Map event = new HashMap<>();
        event.put("eventRemove", new EventDaoSqlite().remove(id));
        return new ModelAndView(event, "product/events");
    }

    public static ModelAndView editEvent(Request req, Response res, int id) throws SQLException{
        Map event = new HashMap<>();
        event.put("eventEdit", new EventDaoSqlite().find(id));
        event.put("categories", new CategoryDaoSqlite().getAll());
        return new ModelAndView(event, "product/edit");
    }
}
