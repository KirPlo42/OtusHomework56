package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainMenuComponent extends AbsBaseComponent{
    public MainMenuComponent(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "header2__auth")
    private WebElement enterOrRegistrationButton;

    @FindBy(className = "header2-menu__dropdown-text")
    private WebElement userPageButton;

    @FindBy(xpath = "(//div[contains(@class, 'header2__right__menu__item')])[1]")
    private WebElement userRightMenu;

    public void openUserPage(){
        actions.moveToElement(userRightMenu).build().perform();
        userPageButton.click();
    }
}
