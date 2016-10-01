import org.sql2o.*;
import org.postgresql.*; // heroku


public class DB {
  // public static Sql2o sql2o = new Sql2o("postgresql://ysokxizkpzypgx:KRojW1PMJQCqXA4k1MJfXR-9jw@ec2-54-221-229-37.compute-1.amazonaws.com:5432/df5l76otnfghdh", null, null); // with this URL I get the error "org.sql2o.Sql2oException: Could not acquire a connection from DataSource - The connection attempt failed"
  // public static Sql2o sql2o = new Sql2o("postgres://ysokxizkpzypgx:KRojW1PMJQCqXA4k1MJfXR-9jw@ec2-54-221-229-37.compute-1.amazonaws.com:5432/df5l76otnfghdh", null, null); // original
  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-54-221-229-37.compute-1.amazonaws.com/df5l76otnfghdh", "ysokxizkpzypgx", "KRojW1PMJQCqXA4k1MJfXR-9jw");


}

// jdbc:postgresql:database
// jdbc:postgresql://host/database
// jdbc:postgresql://host:port/database
