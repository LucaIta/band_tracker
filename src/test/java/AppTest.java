import org.sql2o.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.*;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band Tracker");
  }

  @Test
  public void bandGetsCreated() {
    goTo("http://localhost:4567/");
    fill("#bandName").with("The Music Band");
    fill("#amountOfMembers").with("4");
    submit("#addBand");
    assertThat(pageSource()).contains("The Music Band");
  }

  @Test
  public void venueGetsCreated() {
    goTo("http://localhost:4567/");
    fill("#venueName").with("The Music Place");
    fill("#maxBandSize").with("4");
    submit("#addVenue");
    assertThat(pageSource()).contains("The Music Place");
  }

  @Test
  public void bandDetailPageIsDisplayed() {
    Band newBand = new Band("The Music Band", 4);
    newBand.save();
    String url = String.format("http://localhost:4567/band/%d", newBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("The Music Band").contains("4");
  }

  // I need to vheck that I have an error message for when the form is blank




}
