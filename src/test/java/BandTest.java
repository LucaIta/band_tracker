import org.junit.*;
import static org.junit.Assert.*;

public class BandTest {

  @Test
  public void Band_InstantiateCorrectly() {
    Band testBand = new Band("The Music Band");
    assertTrue(testBand instanceof Band);
  }

  @Test
  public void Category_getCategoryName() {
    Band testBand = new Band("The Music Band");
    assertEquals("The Music Band", testBand.getName());
  }

}
