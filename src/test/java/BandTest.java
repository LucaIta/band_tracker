import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;


public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_InstantiateCorrectly() {
    Band testBand = new Band("The Music Band", 4);
    assertTrue(testBand instanceof Band);
  }

  @Test
  public void and_returnBandName() {
    Band testBand = new Band("The Music Band", 4);
    assertEquals("The Music Band", testBand.getName());
  }

  @Test
  public void Band_returnBandId() {
    Band testBand = new Band("The Music Band", 4);
    assertEquals(0, testBand.getId());
  }

  @Test
  public void Band_savesCorrectly() {
    Band testBand = new Band("The Music Band", 4);
    testBand.save();
    assertTrue(testBand.equals(Band.all().get(0)));
  }

  @Test
  public void Band_updatesNameCorrectly() {
    Band testBand = new Band("The Music Band", 4);
    testBand.save();
    testBand.update("Sex Bob-omb");
    assertEquals("Sex Bob-omb", Band.all().get(0).getName());
  }

  @Test
  public void Band_deletesBandCorrectly() {
    Band testBand = new Band("The Music Band", 4);
    testBand.save();
    Venue testVenue = new Venue("The Music Place", 4);
    testVenue.save();
    testBand.addVenue(testVenue);
    testBand.delete();
    assertEquals(0, Band.all().size());
    assertEquals(0, testBand.getVenues().size());
  }

  @Test
  public void Band_venueIsCorrectlyAdded() {
    Band testBand = new Band("The Music Band", 4);
    testBand.save();
    Venue testVenue = new Venue("The Music Place", 4);
    testVenue.save();
    testBand.addVenue(testVenue);
    assertTrue(testVenue.equals(testBand.getVenues().get(0)));
  }

  @Test
  public void Band_returnFalseIfNameIsEmpty() {
    Band testBand = new Band("", 4);
    assertEquals(false, testBand.save());
  }




}


// i need to override
