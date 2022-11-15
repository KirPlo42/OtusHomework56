package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends AbsPageObj{
    public MainPage(WebDriver driver) {
        super(driver);
        path = "/";
    }
}
