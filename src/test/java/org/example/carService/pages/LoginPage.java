package org.example.carService.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.carService.BaseUiTest;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage extends AbstractBasePage {

    public final Page playwrightPage = BaseUiTest.getPlaywrightConfig().getPage();
    private final static String LOGIN_PAGE_URL = "http://localhost:8080/login";
    private final Locator usernameInput = playwrightPage.locator("#username");
    private final Locator passwordInput = playwrightPage.locator("#password");
    private final Locator loginButton = playwrightPage.locator("button[type='submit']");

//    public void openPage() {
//        playwrightPage.navigate("http://localhost:8080/carservice/clients");
//    }

    @Override
    public LoginPage openPage(String pageName) {
        playwrightPage.navigate(LOGIN_PAGE_URL);
        return this;
    }

    @Step("Entering the username: {username}")
    public LoginPage enterUsername(String username) {
        assertThat(usernameInput).isVisible();
        usernameInput.fill(username);
        assertThat(usernameInput).hasValue(username);
        return this;
    }

    @Step("Entering the password: {password}")
    public LoginPage enterPassword(String password) {
        assertThat(passwordInput).isVisible();
        passwordInput.fill(password);
        assertThat(passwordInput).hasValue(password);
        return this;
    }

    @Step("Clicking the log in button")
    public LoginPage clickLoginButton() {
        assertThat(loginButton).isVisible();
        loginButton.click();
        return this;
    }

    public void validLogin(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();
    }
}
