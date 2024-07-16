package utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.junit.Assert;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.midi.InvalidMidiDataException;
import java.time.Duration;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static utilities.Driver.getAppiumDriver;

public class OptionsMet {


    public static void swipe(int x, int y, int endX, int endY) throws InvalidMidiDataException {
        /******  PointerInput ve Sequence Kullanımı: PointerInput ile parmak hareketlerini
         *      ve Sequence ile bu hareketlerin sırasını tanımlıyoruz.
         addAction metodunu doğru PointerInput nesnesi üzerinde kullanarak sıraya işlemler ekliyoruz.  ***********/
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Point start = new Point(x, y);
        Point end = new Point(endX, endY);
        /** Bu sınıflar Selenium WebDriver içinde ekran üzerinde işaretlemeler yapmak için kullanılır.**/
        Sequence swipe = new Sequence(finger, 0); // 0 or any other index

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getAppiumDriver().perform(Arrays.asList(swipe));
    }

    public static void touchDown(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Point tapPoint = new Point(x, y);
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), tapPoint.x, tapPoint.y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(50)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getAppiumDriver().perform(Arrays.asList(tap));
    }

    public static void scrollWithUiScrollable(String elementText) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\""
                        + elementText + "\").instance(0))"));

    }

    //description ile tanımlı bir öğeye kaydırma metodu
    public static void scrollToDescription(String elementDescription) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                        ".scrollIntoView(new UiSelector().description(\"" + elementDescription + "\"))"));
    }

    // Ekrandaki bir butona tıklama metodu
    public static void clickButtonByDescription(String description) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebElement button = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"" + description + "\")"));
        button.click();
    }

    // Ekrandaki bir butonu görünürlüğünü test etme
    public static void verifyButtonByDescription(String description) {
        try {
            AndroidDriver driver = (AndroidDriver) getAppiumDriver();
            WebElement button = driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiSelector().description(\"" + description + "\")"));

            if (button == null) {
                System.out.println("Button element not found.");
                Assert.fail("Button element not found.");
            } else {

                String buttonText = button.getText();
                String buttonDescription = button.getAttribute("contentDescription");
                System.out.println("Button description: " + buttonDescription);

                Assert.assertTrue("Button description does not match.",
                        buttonDescription != null && buttonDescription.equalsIgnoreCase(description));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An exception occurred: " + e.getMessage());
        }
    }

    public static void verifyItemNameContainsByDescription(String description) {
        try {
            AndroidDriver driver = (AndroidDriver) getAppiumDriver();
            WebElement button = driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiSelector().description(\"" + description + "\")"));

            if (button == null) {
                System.out.println("Button element not found.");
                Assert.fail("Button element not found.");
            } else {

                String buttonText = button.getText();
                String buttonDescription = button.getAttribute("contentDescription");
                System.out.println("Button description: " + buttonDescription);

                Assert.assertTrue("Button description does not match.",
                        buttonDescription != null && buttonDescription.contains(description));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An exception occurred: " + e.getMessage());
        }
    }

    public static void clickAndSendKeys(WebElement element, String context) {
        assertTrue(element.isDisplayed());
        element.click();
        element.clear();
        element.sendKeys(context);
    }

    public static void clickAndVerify(WebElement element) {
        assertTrue(element.isDisplayed());
        assertTrue(element.isEnabled());
        element.click();
    }


    public static void VerifyElementText(String description) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebElement webElement = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"" + description + "\")"));
        assertTrue(webElement.isDisplayed());

    }

    public static void visibleAndActive(String description) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebElement webElement = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"" + description + "\")"));
        ReusableMethods.wait(1);
        assertTrue(webElement.isDisplayed());
        ReusableMethods.wait(1);
        assertTrue(webElement.isEnabled());

    }

    public static void hideKeyboard() {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        driver.hideKeyboard(); // Klavyeyi kapatma komutu
    }

    public static void KeyBack() {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        // Geri tuşuna basın
        driver.pressKey(new KeyEvent(AndroidKey.BACK));

    }

    //istenen sayfaya geri gelmek için kaç kez geri tıklamak gerekirse count kısmına o rakam yazılır
    public static void navigateKeyBack(int count) {
        for (int i = 0; i < count; i++) {
            Driver.getAppiumDriver().navigate().back();
            ReusableMethods.wait(1);

        }
    }

    public static void xPathElementClick(String itemName, String reviews, String price) {
        String xpathExpression = String.format("//android.view.View[contains(@content-desc, '%s') and contains(@content-desc, '%s') and contains(@content-desc, '%s')]/android.widget.ImageView", itemName, reviews, price);

        // Öğeyi bulma
        WebElement element = getAppiumDriver().findElement(MobileBy.xpath(xpathExpression));

        // Öğeyle etkileşim
        element.click();

    }

    public static void xPathElementVerify(String itemName, String reviews, String price) {
        String xpathExpression = String.format("//android.view.View[contains(@content-desc, '%s') and contains(@content-desc, '%s') and contains(@content-desc, '%s')]/android.widget.ImageView", itemName, reviews, price);

        // Öğeyi bulma
        WebElement element = getAppiumDriver().findElement(MobileBy.xpath(xpathExpression));

        // Öğenin varlığını verify etmek
        Assert.assertTrue(element.isDisplayed());

    }
    public static void waitForElementVisibility(WebElement element, int timeoutInSeconds) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }



    public static void swipetimes(int x, int y, int endX, int endY, int times) throws InvalidMidiDataException {
        for (int i = 0; i < times; i++) {
            /******  PointerInput ve Sequence Kullanımı: PointerInput ile parmak hareketlerini
             *      ve Sequence ile bu hareketlerin sırasını tanımlıyoruz.
             addAction metodunu doğru PointerInput nesnesi üzerinde kullanarak sıraya işlemler ekliyoruz.  ***********/
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Point start = new Point(x, y);
            Point end = new Point(endX, endY);
            /** Bu sınıflar Selenium WebDriver içinde ekran üzerinde işaretlemeler yapmak için kullanılır.**/
            Sequence swipe = new Sequence(finger, 0); // 0 or any other index

            swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), start.getX(), start.getY()));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                    PointerInput.Origin.viewport(), end.getX(), end.getY()));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            getAppiumDriver().perform(Arrays.asList(swipe));
        }
    }

}

