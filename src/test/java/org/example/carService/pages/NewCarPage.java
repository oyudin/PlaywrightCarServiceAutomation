package org.example.carService.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public class NewCarPage extends AbstractBasePage {

    private static final String PAGE_URL = "http://localhost:8080/carservice/clients/%s/cars/add";
    private final String pageName = "Create New Car";
    private final String headerValue = "Нове авто";
    private final String successMessageValue = "A new car added";
    private final Page playwrightPage = newPage();
    private final Locator headerLocator = playwrightPage.locator("//h2[text() = 'Нове авто']");
    private final Locator carNumberInput = playwrightPage.locator("input[name='number']");
    private final Locator vinCodeInput = playwrightPage.locator("input[name='vin_code']");
    private final Locator brandInput = playwrightPage.locator("input[name='brand']");
    private final Locator modelInput = playwrightPage.locator("input[name='model']");
    private final Locator addButton = playwrightPage.locator("button[type='submit']");
    private final Locator successMessageLocator = playwrightPage.locator("//div[@class='alert alert-success']");

    @Step("The car page is opened")
    public NewCarPage openPage(String clientId) {
        playwrightPage.navigate(String.format(PAGE_URL, clientId));
        assertThat(headerLocator).isVisible();
        assertThat(headerLocator).hasText(headerValue);
        return this;
    }

    @Step("Entering the car number: {carNumber}")
    public NewCarPage enterCarNumber(String carNumber) {
        carNumberInput.fill(carNumber);
        assertThat(carNumberInput).hasValue(carNumber);
        return this;
    }

    @Step("Entering the vin code: {vinCode}")
    public NewCarPage enterCarVinCode(String vinCode) {
        vinCodeInput.fill(vinCode);
        assertThat(vinCodeInput).hasValue(vinCode);
        return this;
    }

    @Step("Entering the brand value: {brand}")
    public NewCarPage enterCarBrand(String brand) {
        brandInput.fill(brand);
        assertThat(brandInput).hasValue(brand);
        return this;
    }

    @Step("Entering the model value: {model}")
    public NewCarPage enterCarModel(String model) {
        modelInput.fill(model);
        assertThat(modelInput).hasValue(model);
        return this;
    }
}
