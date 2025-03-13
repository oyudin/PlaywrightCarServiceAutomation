package org.example.carService.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.qameta.allure.Step;
import lombok.Getter;
import org.example.carService.BaseUiTest;
import org.testng.Assert;

@Getter
public class MainPage extends AbstractBasePage {

    public final Page playwrightPage = BaseUiTest.getPlaywrightConfig().getPage();
    private final static String MAIN_PAGE_URL = "http://localhost:8080/carservice/clients";
    private final Locator searchInput = playwrightPage.locator("//input[@class='form-control search-input' and @placeholder='Search']");
    private final Locator searchButton = playwrightPage.locator("//button[text()='Пошук']");

    public void openPage() {
//        playwrightPage.navigate("http://localhost:8080/carservice/clients");
    }

    @Override
    public MainPage openPage(String pageName) {
        playwrightPage.navigate(MAIN_PAGE_URL);
        return this;
    }

    @Step
    public MainPage isOpened() {
        String currentUrl = playwrightPage.url();
        Assert.assertEquals(currentUrl, MAIN_PAGE_URL);
        return this;
    }

    @Step
    public MainPage checkSearchButton() {
        playwrightPage.navigate("http://localhost:8080/carservice/clients");
        PlaywrightAssertions.assertThat(searchButton).isVisible();
        return this;
    }

    @Step
    public MainPage enterClientNameInSearchField(String clientName) {
        searchInput.fill(clientName);
        PlaywrightAssertions.assertThat(searchInput).hasValue(clientName);
        searchButton.click();
        return this;
    }


}
