package com.codecool.events;

import com.codecool.events.controller.CategoryController;
import com.codecool.events.controller.EventController;
import com.codecool.events.dao.CategoryDaoSqlite;
import com.codecool.events.dao.DatabaseConnection;
import com.codecool.events.dao.EventDaoSqlite;
import com.codecool.events.model.Event;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.Spark.post;

public class App {
    private static App INSTANCE;
    private DatabaseConnection dbConn;

    private App() {
        dbConn = new DatabaseConnection();
    }

    public static App getApp() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return dbConn.getConnection();
    }

    public void closeConnection() {
        dbConn.connectionClose();
    }

    public static void run() {
        if (INSTANCE == null) {
            INSTANCE = new App();
        }
    }

    public void dispatchRoutes() {
        port(getHerokuAssignedPort());
        staticFileLocation("/static");
        exception(Sql2oException.class, (e, request, response) -> {
            response.status(500);
            System.err.println(e.toString());
            response.body(new ThymeleafTemplateEngine()
                    .render( new ModelAndView(new HashMap<>(), "product/500") ));
        });
        notFound((request, response) -> new ThymeleafTemplateEngine()
                .render( new ModelAndView(new HashMap<>(), "product/404") ));
        get("/", (request, response) -> new ThymeleafTemplateEngine()
                .render( new ModelAndView(new HashMap<>(), "product/index") )
        );
        get("/events", EventController::renderEvents, new ThymeleafTemplateEngine());

        get("/event/:id/details", (request, response) -> new ThymeleafTemplateEngine()
                .render( EventController.eventDetails(request, response, Integer.parseInt(request.params(":id"))) )
        );
        get("/add", (request, response) -> new ThymeleafTemplateEngine()
                .render( CategoryController.renderCategories(request, response) )
        );
        post("/add", (request, response) -> {
            new EventDaoSqlite().add(new Event(
                    request.queryParams("name"),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.queryParams("date")),
                    request.queryParams("desc"),
                    new CategoryDaoSqlite().find(request.queryParams("category")),
                    request.queryParams("link")
            ));
            response.redirect("/create");
            return new ThymeleafTemplateEngine();
        });
        get("/create", (request, response) -> {
            response.redirect("/events");
            return new ThymeleafTemplateEngine();
        });
        get("/event/:id/delete", (request, response) -> {
            EventController.removeEvent(request, response, Integer.parseInt(request.params(":id")));
            response.redirect("/events");
            return new ThymeleafTemplateEngine();
        });
        get("/event/:id/edit", (request, response) -> new ThymeleafTemplateEngine()
                .render( EventController.editEvent(request, response, Integer.parseInt(request.params(":id"))) )
        );
        post("/event/:id/edit", ((request, response) -> {
            new EventDaoSqlite().edit( new Event(
                    Integer.parseInt(request.params(":id")),
                    request.queryParams("name"),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.queryParams("date")),
                    request.queryParams("desc"),
                    new CategoryDaoSqlite().find(request.queryParams("category")),
                    request.queryParams("link")
            ));
            response.redirect("/update");
            return new ThymeleafTemplateEngine();
        }));
        get("/update", (request, response) -> {
            response.redirect("/events");
            return new ThymeleafTemplateEngine();
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8080;
    }
}
