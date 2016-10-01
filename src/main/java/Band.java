import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

import com.heroku.sdk.jdbc.DatabaseUrl; // heroku lib
import java.sql.*; // heroku lib


public class Band {
  private String name;
  private int id;
  private int band_size;

  public Band(String name, int band_size){
    this.name = name;
    this.band_size = band_size;
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
        String sql = "INSERT INTO bands (name, band_size) VALUES (:name, :band_size)";
        this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).addParameter("band_size", this.band_size).executeUpdate().getKey();
        return true;
      }
    }
  }

  public void update(String parameterToEdit,String newValue) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET " + parameterToEdit + " = :newValue WHERE id = :id";
      if (parameterToEdit.equals("band_size")) {
        con.createQuery(sql).addParameter("newValue", Integer.parseInt(newValue)).addParameter("id", this.id).executeUpdate();
      } else {
        con.createQuery(sql).addParameter("newValue", newValue).addParameter("id", this.id).executeUpdate();
      }
    }
  }


  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String deleteFromBands = "DELETE FROM bands WHERE id = :id";
      String deleteFromJointTable = "DELETE FROM bands_venues WHERE band_id = :id";
      con.createQuery(deleteFromBands).addParameter("id", this.id).executeUpdate();
      con.createQuery(deleteFromJointTable).addParameter("id", this.id).executeUpdate();
    }
  }

  public static Band find(int band_id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id = :band_id";
      Band newBand = con.createQuery(sql).addParameter("band_id", band_id).executeAndFetchFirst(Band.class);
      return newBand;
    }
  }

  public static List<Band> all() {
    try (Connection con = DatabaseUrl.extract(true).getConnection()) {
      String sql = "SELECT * FROM bands";
      List<Band> bands = con.createQuery(sql).executeAndFetch(Band.class);
      return bands;
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if(!(otherBand instanceof Band)){
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) && this.getId() == newBand.getId();
    }
  }

  public int getBandSize() {
    return band_size;
  }

  public void addVenue(Venue venue) {
    if (!(this.getVenues().contains(venue))){
      try (Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id ,:venue_id)";
        con.createQuery(sql).addParameter("band_id", this.id).addParameter("venue_id", venue.getId()).executeUpdate();
      }
    }
  }

  public List<Venue> getVenues() {
    try (Connection con = DB.sql2o.open()) {
      String venue_id_query = "SELECT venue_id FROM bands_venues WHERE band_id = :band_id";
      List<Integer> venues_id = con.createQuery(venue_id_query).addParameter("band_id", this.id).executeAndFetch(Integer.class);

      List<Venue> venues = new ArrayList<Venue>();
      String venues_query = "SELECT * FROM venues WHERE id = :venue_id";

      for (Integer venue_id : venues_id) {
        Venue venue = con.createQuery(venues_query).addParameter("venue_id", venue_id).executeAndFetchFirst(Venue.class);
        venues.add(venue);
      }

      return venues;

    }
  }
}
