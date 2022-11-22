package pageobject;

import data.FieldsInUserPageData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Slf4j
public class UserPage extends AbsPageObj{
    public UserPage(WebDriver driver) {
        super(driver);
        path = System.getProperty("user.page.path");
    }

    @FindBy(xpath = "(//div[contains(@data-prefix, 'contact')]//div[contains(@data-selected-option-class, 'lk-cv-block__select-option_selected')])[last()]")
    private WebElement selectCommunicationMethod;

    @FindBy(xpath = "(//div[@data-prefix = 'contact']//div[contains(@class, 'lk-cv-block__select-scroll')]//button)[last()]/../button")
    private List<WebElement> communicationMethodsList;

    @FindBy(xpath = "//button[contains(@class, 'js-lk-cv-custom-select-add')]")
    private WebElement addContactButton;

    @FindBy(xpath = "//div[contains(@data-prefix, 'contact')]//input[contains(@name, '-value')]")
    private List<WebElement> contactInfoList;

    @FindBy(css = "button[title = 'Сохранить и продолжить']")
    private WebElement saveAndContinueButton;

    @FindBy(xpath = "//div[contains(@data-prefix, 'contact')]//*[not(contains(@class, '_ssm'))]/*/button[text() = 'Удалить']")
    private List<WebElement> deleteButtonsList;


    public UserPage fillUserMainInfo(FieldsInUserPageData someInput, String someText){
        String cssSelector = String.format("input[name = '%s']", someInput.getSelector());
        driver.findElement(By.cssSelector(cssSelector)).clear();
        driver.findElement(By.cssSelector(cssSelector)).sendKeys(someText);
        return this;
    }

    public UserPage checkUserMainInfo(FieldsInUserPageData someInput, String textForCheck) {
        String cssSelector = String.format("input[name = '%s']", someInput.getSelector());
        Assertions.assertEquals(textForCheck,  driver.findElement(By.cssSelector(cssSelector)).getAttribute("value"));
        return this;
    }

    public UserPage setNRandomCommunicationMethods(int n){
        selectCommunicationMethod.click();
        communicationMethodsList.get((int) (Math.random()*7 + 1)).click();
        for (int i = 1; i<n; i++){
            addContactButton.click();
            selectCommunicationMethod.click();
            communicationMethodsList.get((int) (Math.random()*7 + 1)).click();
        }
        return this;
    }

    public UserPage fillCommunicationMethods(List<String> contacts){
        for (WebElement input : contactInfoList){
            input.sendKeys(contacts.get((int) (Math.random()*(contacts.size()-1))));
        }
        return this;
    }

    public UserPage deleteCommunicationMethodsIfExist(){
        if (deleteButtonsList.size()>0) {
            String personalURL = driver.getCurrentUrl();
            for (WebElement deleteButton : deleteButtonsList) {
                deleteButton.click();
            }
            saveAndContinueButton.click();
            driver.get(personalURL);
        }else{
            log.info("There are not delete buttons");
        }
        return this;
    }

    public UserPage saveAndContinue(){
        saveAndContinueButton.click();
        return this;
    }

    public UserPage checkContactData(List<String> contacts){
        int i = 0;
        for (WebElement input : contactInfoList){
            input.getAttribute("value");
            for (String contact : contacts){
                if (input.getAttribute("value").equals(contact)){
                    i++;
                    break;
                }
            }
        }
        Assertions.assertEquals(contactInfoList.size(), i);
        return this;
    }

}