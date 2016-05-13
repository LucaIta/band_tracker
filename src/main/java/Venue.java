import java.util.List;
import org.sql2o.*;

public class Venue {
  private String name;
  private int id;
  private int max_band_size;

  public Venue(String name, int max_band_size) {
    this.name = name;
    this.max_band_size = max_band_size;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public boolean save() {
    if (this.name.equals("")) {
      return false;
    } else {
      try (Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO venues (name) VALUES (:name)";
        this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).executeUpdate().getKey();
        return true;
      }
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String deleteFromVenues = "DELETE FROM venues WHERE id = :id";
      String deleteFromJointTable = "DELETE FROM bands_venues WHERE venue_id = :id";
      con.createQuery(deleteFromVenues).addParameter("id", this.id).executeUpdate();
      con.createQuery(deleteFromJointTable).addParameter("id", this.id).executeUpdate();
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

  public boolean bandSizeChecker(Band band) {
    try (Connection con = DB.sql2o.open()) {
      return this.max_band_size >= band.getBandSize();
    }
  }

  // do i delete from joint table?

}
