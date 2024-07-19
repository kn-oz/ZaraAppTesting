package utilities;
import io.appium.java_client.*;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;
import static utilities.Driver.getAppiumDriver;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class ReusableMethods {
    private static DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    public static void apkYukle() {
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigReader.getProperty("deviceName"));
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, ConfigReader.getProperty("version"));
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        //desiredCapabilities.setCapability(MobileCapabilityType.APP,ConfigReader.getProperty(apk));
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, ConfigReader.getProperty("appPackage"));
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ConfigReader.getProperty("appActivity"));
    }
    public static void elementClick(WebElement elementName) {
        var el1 = getAppiumDriver().findElement(androidUIAutomator("new UiSelector().className(\"" + elementName + "\").instance(0)"));
        el1.click();
    }
    public static void koordinatTiklama(int xKoordinat, int yKoordinat, int bekleme, WebElement slider) throws InterruptedException {
        Point source = slider.getLocation();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0),
                PointerInput.Origin.viewport(), source.x, source.y));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(600)));
        sequence.addAction(finger.createPointerMove(ofMillis(600),
                PointerInput.Origin.viewport(), source.x + 400, source.y));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
        getAppiumDriver().perform(singletonList(sequence));
    }
    //  static AndroidDriver<AndroidElement> driver=Driver.getAppiumDriver();
    public static void koordinatTiklamaMethodu(int x, int y) throws InterruptedException {
        TouchAction action = new TouchAction((PerformsTouchActions) getAppiumDriver());
        action.press(PointOption.point(x, y)).release().perform();
        Thread.sleep(1000);
    }
    public static void clickVisibleTextButton(String elementText) {
        AndroidDriver driver = (AndroidDriver) Driver.getAppiumDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='" + elementText + "']")));
        driver.findElement(By.xpath("//*[@text='" + elementText + "']")).click();
    }


    public static WebElement scrollAndSearchElement(String elementText) {
        // Scroll and search for element by text
        WebElement element = null;
        try {
            // Try to find the element by scrolling to it
            element = getAppiumDriver().findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + elementText + "\"))"));
        } catch (Exception e) {
            // Handle any exception if the element is not found
            System.out.println("Element with text '" + elementText + "' not found after scrolling.");
        }
        return element;
    }
    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getAppiumDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/target/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }
    public static void ekranKaydirmaMethodu(int xPress, int yPress, int wait, int xMove, int yMove) {
        TouchAction action = new TouchAction<>((PerformsTouchActions) getAppiumDriver());
        action.press(PointOption.point(xPress, yPress))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(wait)))
                .moveTo(PointOption.point(xMove, yMove))
                .release()
                .perform();
    }
    public static void wait(int saniye) {
        try {
            Thread.sleep(saniye * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToElement(String button) throws InvalidMidiDataException {
        boolean isElementFound = false;
        while (!isElementFound) {
            try {
                WebElement element = getAppiumDriver().findElement( By.xpath("//*[@text='" + button + "']"));
                if (element.isDisplayed()) {
                    isElementFound = true;
                }
            } catch (Exception e) {
                // Perform scroll action
                int startX = getAppiumDriver().manage().window().getSize().width / 2;
                int startY = (int) (getAppiumDriver().manage().window().getSize().height * 0.8);
                int endY = (int) (getAppiumDriver().manage().window().getSize().height * 0.2);
                OptionsMet.swipe(startX, startY, startX, endY);
            }
        }
    }

    public static void swipeToElement(String button,int swipeCoordinateY) throws InvalidMidiDataException {
        boolean isElementFound = false;
        while (!isElementFound) {
            try {
                WebElement element = getAppiumDriver().findElement( By.xpath("//*[@text='" + button + "']"));
                if (element.isDisplayed()) {
                    isElementFound = true;
                }
            } catch (Exception e) {
                // Perform scroll action
                int startX = getAppiumDriver().manage().window().getSize().width / 2;
                int startY = (int) (getAppiumDriver().manage().window().getSize().height * 0.8);
                int endY = (int) (getAppiumDriver().manage().window().getSize().height * 0.2);
                OptionsMet.swipe(1100, swipeCoordinateY, 400, swipeCoordinateY);
            }
        }
    }



    public static void swipeRight(int startX, int startY) {
        int width = getAppiumDriver().manage().window().getSize().width;

        new TouchAction<>((PerformsTouchActions) getAppiumDriver())
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(width - 1, startY))  // Swipe to the end of the screen horizontally
                .release()
                .perform();
    }
    public static void getAllProducts() {
        Set<String> allProductDetails = new HashSet<>();
        boolean loadMore = true;
        int initialSize;
        int newSize;

        while (loadMore) {
            List<WebElement> productContainers = getAppiumDriver().findElements(By.xpath("//*[@resource-id='com.inditex.zara:id/product_info_product_name']"));
            initialSize = allProductDetails.size();

            for (WebElement container : productContainers) {
                String productName = container.getText();
                allProductDetails.add(productName);
            }

            scrollDown();
            newSize = allProductDetails.size();

            // If no new products are added, assume end of list
            loadMore = newSize > initialSize;
        }

        for (String productDetail : allProductDetails) {
            System.out.println(productDetail);
        }
    }
    public static boolean scrollDown() {
        try {
            int startX = getAppiumDriver().manage().window().getSize().width / 2;
            int startY = (int) (getAppiumDriver().manage().window().getSize().height * 0.8);
            int endY = (int) (getAppiumDriver().manage().window().getSize().height * 0.2);
            OptionsMet.swipe(startX, startY, startX, endY);
            return true;
        } catch (Exception e) {
            return false; // No more scrolling possible
        }
    }
    public static int getAllProductsCount() {
        Set<String> allProductDetails = new HashSet<>();
        boolean loadMore = true;
        int initialSize;
        int newSize;

        while (loadMore) {
            List<WebElement> productContainers = getAppiumDriver().findElements(By.xpath("//*[@resource-id='com.inditex.zara:id/product_info_product_name']"));
            initialSize = allProductDetails.size();

            for (WebElement container : productContainers) {
                String productName = container.getText();
                allProductDetails.add(productName);
            }

            ReusableMethods.wait(1);
            scrollDown();
            newSize = allProductDetails.size();

            // If no new products are added, assume end of list
            loadMore = newSize > initialSize;
        }

        for (String productDetail : allProductDetails) {
            System.out.println(productDetail);
        }

        return allProductDetails.size();
    }



}

