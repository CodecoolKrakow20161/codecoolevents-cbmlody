import controller.CategoryController;
import controller.EventController;
import dao.CategoryDaoSqlite;
import dao.DatabaseConnect;
import dao.EventDaoSqlite;
import model.Event;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import  static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        DatabaseConnect.getInstance().queryFromFile("src/main/resources/reset.sql");
        DatabaseConnect.getInstance().queryFromFile("src/main/resources/data_inject.sql");
        exception(java.sql.SQLException.class, (e, request, response) -> {
            response.status(500);
            response.body(new ThymeleafTemplateEngine()
                    .render( new ModelAndView(new HashMap<>(), "product/500") ));
        });
        staticFileLocation("/static");
        port(8888);
        DatabaseConnect.getInstance();
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


}