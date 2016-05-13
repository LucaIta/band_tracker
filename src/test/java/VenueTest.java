import org.junit.*;
import static org.junit.Assert.*;


public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_InstantiateCorrectly() {
    Venue testVenue = new Venue("The Music Place");
    assertTrue(testVenue instanceof Venue);
  }

  @Test
  public void and_returnVenueName() {
    Venue testVenue = new Venue("The Music Place");
    assertEquals("The Music Place", testVenue.getName());
  }

  @Test
  public void Venue_returnVenueId() {
    Venue testVenue = new Venue("The Music Place");
    assertEquals(0, testVenue.getId());
  }

  @Test
  public void Venue_getSavedCorrectly() {
    Venue testVenue = new Venue("The Music Place");
    testVenue.save();
    assertEquals(testVenue, testVenue.all().get(0));
  }
}
