package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.DriverFactory;

@Epic("UI Automation")
@Feature("Login Feature")
public class LoginTests {

    LoginPage loginPage;

    @BeforeMethod
    void setup() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.open();
    }

    @AfterMethod
    void tearDown() {
        DriverFactory.quitDriver();
    }

    @DataProvider
    Object[][] loginData() {
        return new Object[][]{
                {"tomsmith", "SuperSecretPassword!", "You logged into a secure area!"},
                {"tomsmith", "wrongpass", "Your password is invalid!"},
                {"", "SuperSecretPassword!", "Your username is invalid!"},
                {"@#!^@@", "SuperSecretPassword!", "Your username is invalid!"}

        };
    }

    @Test(dataProvider = "loginData")
    void testLogin(String user, String pass, String expected) {
        loginPage.login(user, pass);
        Assert.assertTrue(loginPage.getMessage().contains(expected));
    }
}

