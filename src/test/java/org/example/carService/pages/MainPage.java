package org.example.carService.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.qameta.allure.Step;
import org.example.carService.BaseUiTest;

public class MainPage {

    public final Page playwrightPage = BaseUiTest.getPlaywrightConfig().getPage();
    private final Locator searchInput = playwrightPage.locator("//input[@class='form-control search-input' and @placeholder='Search']");
    private final Locator searchButton = playwrightPage.locator("//button[text()='Пошук']");

    public void openPage() {
        playwrightPage.navigate("http://localhost:8080/carservice/clients");
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
