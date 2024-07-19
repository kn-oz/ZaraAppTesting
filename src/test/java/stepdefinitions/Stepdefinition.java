package stepdefinitions;
import Page.Page;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
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

    @Given("User list all products on product listing page")
    public void user_list_all_products_on_product_listing_page() {
        ReusableMethods.getAllProducts();
    }

    @Given("User list all products on product listing page and verify product count is {int}")
    public void user_list_all_products_on_product_listing_page_and_verify_product_count_is(Integer int1) {
        int actualProductCount =ReusableMethods.getAllProductsCount();
        int expectedProductCount= int1;
        Assert.assertEquals(expectedProductCount,actualProductCount);
        System.out.println("expected count is: "+int1+" actual count is: "+actualProductCount);

    }


}




