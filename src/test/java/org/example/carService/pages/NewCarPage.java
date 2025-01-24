package org.example.carService.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public class NewCarPage extends AbstractBasePage {

    private static final String PAGE_URL = "http://localhost:8080/carservice/clients/1/cars/add";
    private final String pageName = "Create New Car";
    private static final String headerValue = "Нове авто";
    private static final String successMessageValue = "Car created successfully!";
    private final Page playwrightPage = newPage();
    private final Locator headerLocator = playwrightPage.locator("//h2[text() = 'Нове авто']");
    private final Locator carNumberInput = playwrightPage.locator("input[name='number']");
    private final Locator vinCodeInput = playwrightPage.locator("input[name='vin_code']");
    private final Locator brandInput = playwrightPage.locator("input[name='brand']");
    private final Locator modelInput = playwrightPage.locator("input[name='model']");
    private final Locator addButton = playwrightPage.locator("button[type='submit']");
    private final Locator successMessageLocator = playwrightPage.locator("//div[@class='alert alert-success']");

    @Override
    public NewCarPage openPage(String pageName) {
        playwrightPage.navigate(PAGE_URL);
        assertThat(headerLocator).isVisible();
        assertThat(headerLocator).hasText(headerValue);
        return this;
    }

    @Override
    public NewCarPage shouldHave(Locator locator, String elementName) {
        assertThat(locator).isVisible();
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

    @Step("Verifying success notification message is correct")
    public NewCarPage verifySuccessCreationMessage() {
        var message = successMessageLocator.innerText();
        System.err.println(message);
        Assert.assertEquals(message.trim(), successMessageValue);
        return this;
    }
}
