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

}
