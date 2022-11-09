import components.LoginWindowComponent;
import components.MainMenuComponent;
import data.FieldsInUserPageData;
import driver.DriverFactory;
import exceptions.DriverNotSupportedException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pageobject.MainPage;
import pageobject.UserPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static driver.DriverFactory.setDriverName;

@Slf4j
public class OtusTest {
    private WebDriver driver;

    @BeforeAll
    public static void init() throws DriverNotSupportedException {
        new DriverFactory().init();
    }

    @BeforeEach
    public void initDriver() throws DriverNotSupportedException {
        List<String> options = new ArrayList<>();
        options.add("--window-size=1920,1080");
        driver = new DriverFactory().create(setDriverName(), options);
        log.info("Test started in {} browser", setDriverName());
    }

    @AfterEach
    public void close() {
        if (this.driver != null) {
            this.driver.close();
            this.driver.quit();
        }
    }

    @Test
    public void letsGo() throws DriverNotSupportedException {
        final String name = "Федя";
        final String nameLatin = "Fedor";
        final String lastName = "Петров";
        final String lastNameLatin = "Petrov";
        final String blogName = "testBlog";
        final String dateOfBirth = "01.01.1990";
        List<String> contacts = Arrays.asList("somePhone", "someUrl@gg.ru", "someString");

        //Логин на сайт
        MainMenuComponent mainMenuComponent = new MainMenuComponent(driver);
        UserPage userPage = new UserPage(driver);
        new MainPage(driver).openMainPage();
        mainMenuComponent.openEnterOrRegisterForm();
        new LoginWindowComponent(driver).loginAsUser();
        mainMenuComponent.openUserPage();

        //Заполняем личные данные пользователя
        userPage.fillUserMainInfo(FieldsInUserPageData.Name, name);
        userPage.fillUserMainInfo(FieldsInUserPageData.NameLatin, nameLatin);
        userPage.fillUserMainInfo(FieldsInUserPageData.LastName, lastName);
        userPage.fillUserMainInfo(FieldsInUserPageData.LastNameLatin, lastNameLatin);
        userPage.fillUserMainInfo(FieldsInUserPageData.BlogName, blogName);
        userPage.fillUserMainInfo(FieldsInUserPageData.BirthDate, dateOfBirth);

        //Заполняем данные для связи
        userPage.deleteCommunicationMethodsIfExist();
        userPage.setNRandomCommunicationMethods(3);
        userPage.fillCommunicationMethods(contacts);
        userPage.saveAndContinue();

        close();

        //Открываем чистый браузер + логин на сайт
        initDriver();
        UserPage userPageV2 = new UserPage(driver);
        MainMenuComponent mainMenuComponentV2 = new MainMenuComponent(driver);
        new MainPage(driver).openMainPage();
        mainMenuComponentV2.openEnterOrRegisterForm();
        new LoginWindowComponent(driver).loginAsUser();
        mainMenuComponentV2.openUserPage();

        //Проверка персональных данных

        userPageV2.checkUserMainInfo(FieldsInUserPageData.Name, name);
        userPageV2.checkUserMainInfo(FieldsInUserPageData.NameLatin, nameLatin);
        userPageV2.checkUserMainInfo(FieldsInUserPageData.LastName, lastName);
        userPageV2.checkUserMainInfo(FieldsInUserPageData.LastNameLatin, lastNameLatin);
        userPageV2.checkUserMainInfo(FieldsInUserPageData.BlogName, blogName);
        userPageV2.checkUserMainInfo(FieldsInUserPageData.BirthDate, dateOfBirth);

        //Проверка контактных данных
        userPageV2.checkContactData(contacts);
        log.info("Test complete successful");
    }

}
