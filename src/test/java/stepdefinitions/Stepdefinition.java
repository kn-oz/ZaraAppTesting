package stepdefinitions;
import Page.Page;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import utilities.*;

import javax.sound.midi.InvalidMidiDataException;
public class Stepdefinition extends OptionsMet {

    AndroidDriver driver = (AndroidDriver) Driver.getAppiumDriver();
    Page card = new Page();

    @Given("User makes driver adjustments")
    public void user_makes_driver_adjustments() {
        Driver.getAppiumDriver();
    }
    @When("User scroll to the {string} button")
    public void user_scroll_to_the_button(String button) throws InvalidMidiDataException {
        ReusableMethods.scrollToElement(button);
    }

    @When("User clicks the {string} button")
    public void user_clicks_the_button(String button) {
        ReusableMethods.clickVisibleTextButton(button);
    }

    @Given("User opens keyboard and type {string}")
    public void user_enter_in_to_the_search_box(String string) {
        driver.pressKey(new KeyEvent(AndroidKey.N));
    }
    @Given("User verifies that {string} text is displayed")
    public void user_verifies_that_text_is_displayed(String string) {
        ReusableMethods.wait(5);
        OptionsMet.VerifyElementText(string);

    }
    @Given("User swipe from {int} to the {string} button")
    public void user_swipe_from_to_the_button(int y, String button) throws InvalidMidiDataException {
        By productElement = By.xpath("//*[@text='" + button + "']");
        ReusableMethods.swipeToElement(button,y);
    }

}




