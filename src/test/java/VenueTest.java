import org.junit.*;
import static org.junit.Assert.*;

public class VenueTest {

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

}
