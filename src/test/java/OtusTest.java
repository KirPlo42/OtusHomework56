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
        UserPage userPage = new UserPage(driver);
        new MainPage(driver).openClassPage();
        new LoginWindowComponent(driver)
                .openEnterOrRegisterForm()
                .loginAsUser();
        new MainMenuComponent(driver).openUserPage();

        //Заполняем личные данные пользователя
        userPage.fillUserMainInfo(FieldsInUserPageData.Name, name)
                .fillUserMainInfo(FieldsInUserPageData.NameLatin, nameLatin)
                .fillUserMainInfo(FieldsInUserPageData.LastName, lastName)
                .fillUserMainInfo(FieldsInUserPageData.LastNameLatin, lastNameLatin)
                .fillUserMainInfo(FieldsInUserPageData.BlogName, blogName)
                .fillUserMainInfo(FieldsInUserPageData.BirthDate, dateOfBirth);

        //Заполняем данные для связи
        userPage.deleteCommunicationMethodsIfExist()
                .setNRandomCommunicationMethods(3)
                .fillCommunicationMethods(contacts)
                .saveAndContinue();

        close();

        //Открываем чистый браузер + логин на сайт
        initDriver();
        new MainPage(driver).openClassPage();
        new LoginWindowComponent(driver)
                .openEnterOrRegisterForm()
                .loginAsUser();
        new MainMenuComponent(driver).openUserPage();

        //Проверка персональных данных
        new UserPage(driver).checkUserMainInfo(FieldsInUserPageData.Name, name)
                .checkUserMainInfo(FieldsInUserPageData.NameLatin, nameLatin)
                .checkUserMainInfo(FieldsInUserPageData.LastName, lastName)
                .checkUserMainInfo(FieldsInUserPageData.LastNameLatin, lastNameLatin)
                .checkUserMainInfo(FieldsInUserPageData.BlogName, blogName)
                .checkUserMainInfo(FieldsInUserPageData.BirthDate, dateOfBirth)
                .checkContactData(contacts);

        log.info("Test complete successful");
    }

}
