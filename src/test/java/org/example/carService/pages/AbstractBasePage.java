package org.example.carService.pages;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;

public abstract class AbstractBasePage implements PageObject {

    @Step("The page is open: {pageName}")
    public AbstractBasePage openPage(String pageName) {
        return this;
    }

    @Step("Clicking on button: {buttonName}")
    public AbstractBasePage clickOnButton(Locator locator, String buttonName) {
        return this;
    }

    @Step("The element {elementName} should be displayed")
    public AbstractBasePage shouldHave(Locator locator, String elementName) {
        return this;
    }

    @Step("The element {elementName} should NOT be displayed")
    public AbstractBasePage shouldNotHave(Locator locator, String elementName) {
        return this;
    }
}
