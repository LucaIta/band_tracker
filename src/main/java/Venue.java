import java.util.List;
import org.sql2o.*;

public class Venue {
  private String name;
  private int id;

  public Venue(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (name) VALUES (:name)";
      // this.id = (int)
      con.createQuery(sql, true).addParameter("name", this.name).executeUpdate();
      // .getKey(); do I need to retrieve the id?
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.name.equals(newVenue.getName()); // should I add the ID comparison?
    }
  }

  public static List<Venue> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues";
      List<Venue> venues = con.createQuery(sql).executeAndFetch(Venue.class);
      return venues;
    }

  }

  // all

  // add
  // update
  // delete

}
