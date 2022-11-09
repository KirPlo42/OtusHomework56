package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public abstract class AbsPageObj {
    protected WebDriver driver;
    protected Actions actions;

    public AbsPageObj(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);

        PageFactory.initElements(driver, this);
    }

    public void openMainPage(){
        driver.get(System.getProperty("base.url"));
    }

}
