import org.junit.*;
import static org.junit.Assert.*;


public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_InstantiateCorrectly() {
    Venue testVenue = new Venue("The Music Place", 4);
    assertTrue(testVenue instanceof Venue);
  }

  @Test
  public void and_returnVenueName() {
    Venue testVenue = new Venue("The Music Place", 4);
    assertEquals("The Music Place", testVenue.getName());
  }

  @Test
  public void Venue_returnVenueId() {
    Venue testVenue = new Venue("The Music Place", 4);
    assertEquals(0, testVenue.getId());
  }

  @Test
  public void Venue_getSavedCorrectly() {
    Venue testVenue = new Venue("The Music Place", 4);
    testVenue.save();
    assertEquals(testVenue, testVenue.all().get(0));
  }

  @Test
  public void Venue_checksCorrectlyIfABandCanPlayHere() {
    Venue testVenue = new Venue("The Music Place", 4);
    Band testBand = new Band("The Music Band", 3);
    assertTrue(testVenue.bandSizeChecker(testBand));
  }

  @Test
  public void Venue_returnFalseIfNameIsEmpty() {
    Venue testVenue = new Venue("", 4);
    assertEquals(false, testVenue.save());
  }

  @Test
  public void Venue_bandsAreCorrectlyReturned() {
    Band testBand = new Band("The Music Band", 4);
    testBand.save();
    Venue testVenue = new Venue("The Music Place", 4);
    testVenue.save();
    testBand.addVenue(testVenue);
    assertTrue(testBand.equals(testVenue.getBands().get(0)));
  }

}
