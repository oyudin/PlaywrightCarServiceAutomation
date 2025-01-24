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
    private final Locator carNumberLabel = playwrightPage.locator("//label[text()='Номер авто']");
    private final Locator carNumberPlaceHolder = playwrightPage.locator("//*[@id='number']");

    private final Locator vinCodeInput = playwrightPage.locator("input[name='vin_code']");
    private final Locator vinCodeLabel = playwrightPage.locator("//label[text() = 'Він код']");
    private final Locator vinCodePlaceholder = playwrightPage.locator("//*[@id= 'vin_code']");

    private final Locator brandInput = playwrightPage.locator("input[name='brand']");
    private final Locator brandLabel = playwrightPage.locator("//label[text() = 'Бренд']");
    private final Locator brandPlaceholder = playwrightPage.locator("//*[@id= 'brand']");

    private final Locator modelInput = playwrightPage.locator("input[name='model']");
    private final Locator modelLabel = playwrightPage.locator("//label[text() = 'Модель']");
    private final Locator modelPlaceholder = playwrightPage.locator("//*[@id= 'model']");

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
        assertThat(carNumberLabel).isVisible();
        assertThat(carNumberPlaceHolder).isVisible();
        carNumberInput.fill(carNumber);
        assertThat(carNumberInput).hasValue(carNumber);
        return this;
    }

    @Step("Entering the vin code: {vinCode}")
    public NewCarPage enterCarVinCode(String vinCode) {
        assertThat(vinCodeLabel).isVisible();
        assertThat(vinCodePlaceholder).isVisible();
        vinCodeInput.fill(vinCode);
        assertThat(vinCodeInput).hasValue(vinCode);
        return this;
    }

    @Step("Entering the brand value: {brand}")
    public NewCarPage enterCarBrand(String brand) {
        assertThat(brandLabel).isVisible();
        assertThat(brandPlaceholder).isVisible();
        brandInput.fill(brand);
        assertThat(brandInput).hasValue(brand);
        return this;
    }

    @Step("Entering the model value: {model}")
    public NewCarPage enterCarModel(String model) {
        assertThat(modelLabel).isVisible();
        assertThat(modelPlaceholder).isVisible();
        modelInput.fill(model);
        assertThat(modelInput).hasValue(model);
        return this;
    }
}