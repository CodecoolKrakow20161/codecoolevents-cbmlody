package controller;

import dao.EventDao;
import dao.EventDaoSqlite;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;


public class EventController {
    public static ModelAndView renderProducts(Request req, Response res) {
        //Get events from database by Dao
        Map params = new HashMap<>();
        params.put("eventContainer", new EventDaoSqlite().getAll());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView eventDetails(Request req, Response res, int id) {
        Map event = new HashMap<>();
        event.put("eventDetails", new EventDaoSqlite().getOne(id));
        return new ModelAndView(event, "product/details");
    }
}
