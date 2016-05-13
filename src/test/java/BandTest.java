import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;


public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_InstantiateCorrectly() {
    Band testBand = new Band("The Music Band");
    assertTrue(testBand instanceof Band);
  }

  @Test
  public void and_returnBandName() {
    Band testBand = new Band("The Music Band");
    assertEquals("The Music Band", testBand.getName());
  }

  @Test
  public void Band_returnBandId() {
    Band testBand = new Band("The Music Band");
    assertEquals(0, testBand.getId());
  }

  @Test
  public void Band_savesCorrectly() {
    Band testBand = new Band("The Music Band");
    testBand.save();
    assertTrue(testBand.equals(Band.all().get(0)));
  }

  @Test
  public void Band_updatesNameCorrectly() {
    Band testBand = new Band("The Music Band");
    testBand.save();
    testBand.update("Sex Bob-omb");
    assertEquals("Sex Bob-omb", Band.all().get(0).getName());
  }

  @Test
  public void Band_deletesBandCorrectly() {
    Band testBand = new Band("The Music Band");
    testBand.save();
    testBand.delete();
    assertEquals(0, Band.all().size());

  }






}


// i need to override
