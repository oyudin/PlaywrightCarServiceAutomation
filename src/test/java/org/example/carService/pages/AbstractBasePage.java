package org.example.carService.pages;

import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;

import io.qameta.allure.Step;
import org.testng.Assert;

public abstract class AbstractBasePage implements PageObject {

    @Step("The page is open: {pageName}")
    public AbstractBasePage openPage(String pageName) {
        return this;
    }

    @Step("Clicking on button: {buttonName}")
    public AbstractBasePage clickOnButton(Locator locator, String buttonName) {
        assertThat(locator).isVisible();
        locator.click();
        return this;
    }

    @Step("The element {elementName} should be displayed")
    public AbstractBasePage shouldHave(Locator locator, String elementName) {
        assertThat(locator).isVisible();
        return this;
    }

    @Step("The element {elementName} should NOT be displayed")
    public AbstractBasePage shouldNotHave(Locator locator, String elementName) {
        assertThat(locator).not().isVisible();
        return this;
    }

    @Step("Verifying notification message is correct")
    public AbstractBasePage verifyNotificationMessage(Locator locator, String message) {
        var locatorMessage = locator.innerText();
        Assert.assertEquals(locatorMessage.trim(), message);
        return this;
    }
}
