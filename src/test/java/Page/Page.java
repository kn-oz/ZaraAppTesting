package Page;


import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;
import static utilities.Driver.getAppiumDriver;

@Getter
public class Page {
    public Page() {
        PageFactory.initElements(new AppiumFieldDecorator(getAppiumDriver()), this);
    }

    // WEB ELEMENTS
    @AndroidFindBy(xpath = "(//android.widget.ImageView[1])[1]")
    private WebElement exampleElement;

}




