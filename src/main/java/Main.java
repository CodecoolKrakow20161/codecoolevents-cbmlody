import controller.CategoryController;
import controller.EventController;
import dao.DatabaseConnect;
import dao.EventDaoSqlite;
import model.Event;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


import  static spark.Spark.*;


public class Main {

    public static void main(String[] args) {
        exception(Exception.class, (e, request, response) -> e.printStackTrace());
        staticFileLocation("/static");
        port(8888);
        DatabaseConnect.getInstance();
        get("/add", (Request req, Response res) -> new ThymeleafTemplateEngine()
                .render(CategoryController.renderCategories(req, res)));
        get("/", (request, response) -> new ThymeleafTemplateEngine()
                .render( new ModelAndView(new HashMap<>(), "product/index")) );

        get("/events", EventController::renderProducts, new ThymeleafTemplateEngine());

        get("/event/:id/details", (request, response) -> new ThymeleafTemplateEngine()
                .render( EventController.eventDetails(request, response, Integer.parseInt(request.params(":id"))) )
        );
    }


}