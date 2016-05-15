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

  @Test
  public void venueAssociatedWithBandIsDisplayed() {
    Band newBand = new Band("The Music Band", 4);
    newBand.save();
    Venue newVenue = new Venue("The Music Place", 4);
    newVenue.save();
    newBand.addVenue(newVenue);
    String url = String.format("http://localhost:4567/band/%d", newBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("The Music Place");
  }

  @Test
  public void bandGetDeleted() {
    Band newBand = new Band("The Music Band", 4);
    newBand.save();
    String url = String.format("http://localhost:4567/band/%d", newBand.getId());
    goTo(url);
    submit("#deleteBand");
    assertThat(pageSource()).doesNotContain("The Music Band").contains("List of bands");
  }

  @Test
  public void bandGetUpdated() {
    Band newBand = new Band("The Music Band", 4);
    newBand.save();
    String url = String.format("http://localhost:4567/band/%d", newBand.getId());
    goTo(url);
    click("option", withText("Edit band Name"));
    fill("#newValue").with("Sex Bob-omb");
    submit("#editBand");
    assertThat(pageSource()).contains("Sex Bob-omb");
    click("option", withText("Edit band members number"));
    fill("#newValue").with("6");
    submit("#editBand");
    assertThat(pageSource()).contains("Number of members: 6");
  }

  @Test
  public void bandIsDisplayedInVenuesPage() {
    Band newBand = new Band("The Music Band", 4);
    newBand.save();
    Venue newVenue = new Venue("The Music Place", 4);
    newVenue.save();
    newBand.addVenue(newVenue);
    String url = String.format("http://localhost:4567/venue/%d", newVenue.getId());
    goTo(url);
    assertThat(pageSource()).contains("The Music Band");
  }

  // I need to vheck that I have an error message for when the form is blank




}
