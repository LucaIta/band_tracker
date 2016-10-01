import org.sql2o.*;
import org.postgresql.*; // heroku


public class DB {
  public static Sql2o sql2o = new Sql2o("jbdc:postgresql://ysokxizkpzypgx:KRojW1PMJQCqXA4k1MJfXR-9jw@ec2-54-221-229-37.compute-1.amazonaws.com:5432/df5l76otnfghdh", null, null);
  // public static Sql2o sql2o = new Sql2o("postgres://ysokxizkpzypgx:KRojW1PMJQCqXA4k1MJfXR-9jw@ec2-54-221-229-37.compute-1.amazonaws.com:5432/df5l76otnfghdh", null, null);

}

// jdbc:postgresql:database
// jdbc:postgresql://host/database
// jdbc:postgresql://host:port/database
