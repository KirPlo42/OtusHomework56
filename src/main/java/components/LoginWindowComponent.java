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

    @FindBy(className = "header2__auth")
    private WebElement enterOrRegistrationButton;

    private String modalWindowForLoginXPath = "//div[contains(@class, 'modal-container')][1]";

    public LoginWindowComponent loginAsUser(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        modalShouldBeVisible();
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys(System.getProperty("login"));
        passwordInput.sendKeys(System.getProperty("password"));
        passwordInput.submit();
        modalShouldNotBeVisible();
        return this;
    }

    public LoginWindowComponent modalShouldBeVisible(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Assertions.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(modalWindowForLoginXPath))).isDisplayed()
                , "Login modal window doesn't displayed");
        return this;
    }

    public void modalShouldNotBeVisible(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Assertions.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(modalWindowForLoginXPath)))
        , "Login modal window still open");
    }

    public LoginWindowComponent openEnterOrRegisterForm(){
        enterOrRegistrationButton.click();
        return this;
    }

}
