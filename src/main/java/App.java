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
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands", (request, response) ->  {
      String bandName= request.queryParams("bandName");
      int amountOfMembers= Integer.parseInt(request.queryParams("amountOfMembers"));
      Band newBand = new Band(bandName,amountOfMembers);
      newBand.save();
      response.redirect("/");
      return null;
    });

    get("/band/:band_id", (request, response) ->  {
      Map <String, Object> model = new HashMap<String, Object>();
      int band_id = Integer.parseInt(request.params(":band_id"));
      model.put("band", Band.find(band_id));
      model.put("venues", Venue.all());
      model.put("bandVenues", Band.find(band_id).getVenues());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues", (request, response) ->  {
      String venueName = request.queryParams("venueName");
      int maxBandSize = Integer.parseInt(request.queryParams("maxBandSize"));
      Venue newVenue = new Venue(venueName, maxBandSize);
      newVenue.save();
      response.redirect("/");
      return null;
    });

    post("/band/:band_id/venues", (request, response) ->  {
      String[] venues_id = request.queryParamsValues("checkbox");
      int band_id = Integer.parseInt(request.params(":band_id"));
      Band newBand = Band.find(band_id);
      for (String venue_id : venues_id) {
        Venue newVenue = Venue.find(Integer.parseInt(venue_id));
        newBand.addVenue(newVenue);
      }
      String url = String.format("/band/%d", band_id);
      response.redirect(url);
      return null;
    });

    // post("/checkboxes", (request, response) ->  { // this works, I think I was using Band.vtl
    //   Map <String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/checkboxes.vtl");
    //   model.put("checkbox1",request.queryParams("checkbox1"));
    //   model.put("checkbox2",request.queryParams("checkbox2"));
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

    // post("/checkboxes", (request, response) ->  { // this works better
    //   Map <String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/checkboxes.vtl");
    //   model.put("checkboxesValues", request.queryParamsValues("checkbox"));
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

  }
}
