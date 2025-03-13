package org.example.carService.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;

@Getter
public class NewClientPage extends AbstractBasePage {

    private static final String PAGE_URL = "http://localhost:8080/carservice/clients/add";
    private final String pageName = "Create New Client";
    private final String headerValue = "Новий клієнт";
    private final String successMessageValue = "Клієнт успішно створений!";
    private final Page playwrightPage = newPage();
    private final Locator headerLocator = playwrightPage.locator("//h2[text() = 'Новий клієнт']");
    private final Locator nameInput = playwrightPage.locator("input[name='name']");
    private final Locator surnameInput = playwrightPage.locator("input[name='surname']");
    private final Locator phoneNumberInput = playwrightPage.locator("input[name='phoneNumber']");
    private final Locator saveButton = playwrightPage.locator("button[type='submit']");
    private final Locator successMessageLocator = playwrightPage.locator("#successMessage");
    private final Locator backToClientsButton = playwrightPage.locator("a.btn.btn-outline-primary[href='/carservice/clients']");


    @Override
    public NewClientPage openPage(String pageName) {
        playwrightPage.navigate(PAGE_URL);
        assertThat(headerLocator).isVisible();
        assertThat(headerLocator).hasText(headerValue);
        return this;
    }

    @Step("Entering the client name: {clientName}")
    public NewClientPage enterClientName(String clientName) {
        nameInput.fill(clientName);
        assertThat(nameInput).hasValue(clientName);
        return this;
    }

    @Step("Entering the client surname: {clientSurname}")
    public NewClientPage enterClientSurname(String clientSurname) {
        surnameInput.fill(clientSurname);
        assertThat(surnameInput).hasValue(clientSurname);
        return this;
    }

    @Step("Entering the phone number {phoneNumber}")
    public NewClientPage enterClientPhoneNumber(String phoneNumber) {
        phoneNumberInput.fill(phoneNumber);
        assertThat(phoneNumberInput).hasValue(phoneNumber);
        return this;
    }
}
