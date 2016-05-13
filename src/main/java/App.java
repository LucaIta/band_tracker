import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.HashMap;
import java.util.Map;

public class App {

  public static void main (String[] args){

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) ->  {
      Map <String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // post("/checkboxes", (request, response) ->  { // this works
    //   Map <String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/checkboxes.vtl");
    //   model.put("checkbox1",request.queryParams("checkbox1"));
    //   model.put("checkbox2",request.queryParams("checkbox2"));
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    post("/checkboxes", (request, response) ->  {
      Map <String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/checkboxes.vtl");
      model.put("checkboxesValues", request.queryParamsValues("checkbox"));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
