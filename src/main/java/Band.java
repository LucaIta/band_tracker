import java.util.List;
import org.sql2o.*;

public class Band {
  private String name;
  private int id;

  public Band(String name){
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
      String sql = "INSERT INTO bands (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).executeUpdate().getKey();
    }
  }

  public void update(String name) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET name = :name WHERE id = :id";
      con.createQuery(sql).addParameter("name", name).addParameter("id", this.id).executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM bands WHERE id = :id";
      con.createQuery(sql).addParameter("id", this.id).executeUpdate();
    }
  }

  public static List<Band> all() {
    try (Connection con = DB.sql2o.open()) {
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

  // should check for empty entries
  // delete

}
