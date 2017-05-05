import controller.CategoryController;
import controller.EventController;
import dao.DatabaseConnect;
import dao.EventDaoSqlite;
import model.Event;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.Date;
import java.util.List;

import  static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        System.out.println(new Date());
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/static");
        port(8888);
        DatabaseConnect.getInstance();
        // Always add generic routes to the end
        // Equivalent with above
        get("/index", (Request req, Response res) -> new ThymeleafTemplateEngine()
                .render( EventController.renderProducts(req, res) ));
        get("/event/:id", (Request req, Response res) -> new ThymeleafTemplateEngine()
                .render( EventController.eventDetails(req, res, Integer.parseInt(req.params(":id"))) ));
        get("/add", (Request req, Response res) -> new ThymeleafTemplateEngine()
                .render(CategoryController.renderCategories(req, res)));
        get("/events", EventController::renderProducts, new ThymeleafTemplateEngine());
    }


}