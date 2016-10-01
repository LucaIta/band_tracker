import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import org.postgresql.*; // heroku


public class App {

  public static void main (String[] args){

    ProcessBuilder process = new ProcessBuilder();
    Integer port;
    if (process.environment().get("PORT") != null) {
      port = Integer.parseInt(process.environment().get("PORT"));
    } else {
      port = 4567;
    }

    setPort(port);

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
      if(!(request.queryParams("amountOfMembers").equals("") || request.queryParams("bandName").equals(""))){
        String bandName= request.queryParams("bandName");
        int amountOfMembers = Integer.parseInt(request.queryParams("amountOfMembers"));
        Band newBand = new Band(bandName,amountOfMembers);
        newBand.save();
      }
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

    get("/venue/:venue_id", (request, response) ->  {
      Map <String, Object> model = new HashMap<String, Object>();
      int venue_id = Integer.parseInt(request.params(":venue_id"));
      model.put("venue", Venue.find(venue_id));
      model.put("venuesBand", Venue.find(venue_id).getBands());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



    post("/venues", (request, response) ->  {
      if(!(request.queryParams("venueName").equals("") || request.queryParams("maxBandSize").equals(""))){
        String venueName = request.queryParams("venueName");
        int maxBandSize = Integer.parseInt(request.queryParams("maxBandSize"));
        System.out.println(maxBandSize);
        Venue newVenue = new Venue(venueName, maxBandSize);
        newVenue.save();
      }
      response.redirect("/");
      return null;
    });

    post("/band/:band_id/venues", (request, response) ->  {
      String[] venues_id = request.queryParamsValues("checkbox");
      int band_id = Integer.parseInt(request.params(":band_id"));
      Band newBand = Band.find(band_id);
      for (String venue_id : venues_id) {
        Venue newVenue = Venue.find(Integer.parseInt(venue_id));
        if(newVenue.bandSizeChecker(newBand)){
          newBand.addVenue(newVenue);
        }
      }
      String url = String.format("/band/%d", band_id);
      response.redirect(url);
      return null;
    });

    post("/band/:band_id/delete", (request, response) ->  {
      Band newBand = Band.find(Integer.parseInt(request.params(":band_id")));
      newBand.delete();
      response.redirect("/");
      return null;
    });


    post("/band/:band_id/edit", (request, response) ->  {
      String parameterToEdit = request.queryParams("editForm");
      Band newBand = Band.find(Integer.parseInt(request.params(":band_id")));
      String newValue = request.queryParams("newValue");
      newBand.update(parameterToEdit,newValue);
      String url = String.format("/band/%d", newBand.getId());
      response.redirect(url);
      return null;
    });

  }
}
