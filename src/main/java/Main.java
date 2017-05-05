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
        exception(Exception.class, (e, request, response) -> e.printStackTrace());
        staticFileLocation("/static");
        port(8888);
        DatabaseConnect.getInstance();
        get("/", (request, response) -> new ThymeleafTemplateEngine()
                .render( new ModelAndView(new HashMap<>(), "product/index")) );

        get("/events", EventController::renderProducts, new ThymeleafTemplateEngine());

        get("/event/:id/details", (request, response) -> new ThymeleafTemplateEngine()
                .render( EventController.eventDetails(request, response, Integer.parseInt(request.params(":id"))) )
        );
        get("/add", (request, response) -> new ThymeleafTemplateEngine()
                .render( CategoryController.renderCategories(request, response) ));
        post("/add", (request, response) -> {
            Event event = new Event(
                    request.queryParams("name"),
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.queryParams("date")),
                    request.queryParams("desc"),
                    new CategoryDaoSqlite().find(request.queryParams("category")),
                    request.queryParams("link")
            );
            new EventDaoSqlite().add(event);
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
    }


}