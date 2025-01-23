package org.example.carService.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.example.carService.BaseUiTest;

public class ClientPage {

    public final Page playwrightPage = BaseUiTest.getPlaywrightConfig().getPage();

    private final Locator searchButton = playwrightPage.locator("//button[text()='Пошук']");

    private final Locator searchInput = playwrightPage.locator("//input[@class='form-control search-input' and @placeholder='Search']");

    public void openPage() {
        playwrightPage.navigate("http://localhost:8080/carservice/clients");
    }

    public ClientPage checkSearchButton() {
        playwrightPage.navigate("http://localhost:8080/carservice/clients");
        PlaywrightAssertions.assertThat(searchButton).isVisible();
        return this;
    }

    public ClientPage enterClientNameInSearchField(String clientName) {
        searchInput.fill(clientName);
        PlaywrightAssertions.assertThat(searchInput).hasValue(clientName);
        searchButton.click();
        return this;
    }
}
