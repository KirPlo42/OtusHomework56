package components;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginWindowComponent extends AbsBaseComponent implements IModal{
    public LoginWindowComponent(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "(//input[contains(@class, 'js-email-input')])[1]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[contains(@class, 'new-input_password')]")
    private WebElement passwordInput;

    private String modalWindowForLoginXPath = "//div[contains(@class, 'modal-container')][1]";

    public void loginAsUser(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        modalShouldBeVisible();
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys(System.getProperty("login"));
        passwordInput.sendKeys(System.getProperty("password"));
        passwordInput.submit();
        modalShouldNotBeVisible();
    }

    public void modalShouldBeVisible(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Assertions.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(modalWindowForLoginXPath))).isDisplayed()
                , "Login modal window doesn't displayed");
    }

    public void modalShouldNotBeVisible(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Assertions.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(modalWindowForLoginXPath)))
        , "Login modal window still open");
    }

}
