package controller;

import dao.EventDaoSqlite;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;


public class EventController {
    public static ModelAndView renderProducts(Request req, Response res) {
        Map params = new HashMap<>();
        params.put("eventContainer", new EventDaoSqlite().getAll());
        return new ModelAndView(params, "product/events");
    }

    public static ModelAndView eventDetails(Request req, Response res, int id) {
        Map event = new HashMap<>();
        event.put("eventDetails", new EventDaoSqlite().getOne(id));
        return new ModelAndView(event, "product/details");
    }

    public static ModelAndView removeEvent(Request req, Response res, int id) {
        Map event = new HashMap<>();
        event.put("eventRemove", new EventDaoSqlite().remove(id));
        return new ModelAndView(event, "product/events");
    }
}
