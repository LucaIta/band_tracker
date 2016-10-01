import java.util.List;
// import org.sql2o.*;

import java.util.ArrayList;

import com.heroku.sdk.jdbc.DatabaseUrl;  // heroku lib
import java.sql.*; // heroku lib


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

  public int maxBandSize() {
    return max_band_size;
  }

  public int getId() {
    return id;
  }

  public boolean save() {
    if (this.name.equals("")) {
      return false;
    } else {
      try (Connection con = DatabaseUrl.extract(true).getConnection()) {
        String sql = "INSERT INTO venues (name, max_band_size) VALUES (:name, :max_band_size)";
        this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).addParameter("max_band_size", this.max_band_size).executeUpdate().getKey();
        return true;
      }
    }
  }

  public static Venue find(int venue_id) {
    try (Connection con = DatabaseUrl.extract(true).getConnection()) {
      String sql = "SELECT * FROM venues WHERE id = :venue_id";
      Venue newVenue = con.createQuery(sql).addParameter("venue_id", venue_id).executeAndFetchFirst(Venue.class);
      return newVenue;
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.name.equals(newVenue.getName());
    }
  }

  public static List<Venue> all() {
    try (Connection con = DatabaseUrl.extract(true).getConnection()) {
      String sql = "SELECT * FROM venues";
      List<Venue> venues = con.createQuery(sql).executeAndFetch(Venue.class);
      return venues;
    }
  }

  public boolean bandSizeChecker(Band band) {
    try (Connection con = DatabaseUrl.extract(true).getConnection()) {
      return this.max_band_size >= band.getBandSize();
    }
  }

  public List<Band> getBands() {
    try (Connection con = DatabaseUrl.extract(true).getConnection()) {
      String band_id_query = "SELECT band_id FROM bands_venues WHERE venue_id = :venue_id";
      List<Integer> bands_id = con.createQuery(band_id_query).addParameter("venue_id", this.id).executeAndFetch(Integer.class);

      List<Band> bands = new ArrayList<Band>();
      String bands_query = "SELECT * FROM bands WHERE id = :band_id";

      for (Integer band_id : bands_id) {
        Band band = con.createQuery(bands_query).addParameter("band_id", band_id).executeAndFetchFirst(Band.class);
        bands.add(band);
      }

      return bands;

    }
  }

}
