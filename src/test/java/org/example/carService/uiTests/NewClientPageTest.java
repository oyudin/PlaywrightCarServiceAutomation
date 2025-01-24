package org.example.carService.uiTests;

import jdk.jfr.Description;
import org.example.carService.BaseUiTest;
import org.example.carService.pages.NewClientPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.example.carService.utils.TestDataGenerator.*;

public class NewClientPageTest extends BaseUiTest {

    private NewClientPage newClientPage;

    @BeforeMethod
    public void initAndOpenPage() {
        newClientPage = new NewClientPage();
        newClientPage.openPage(newClientPage.getPageName());
    }

    @Test
    @Description("")
    public void createNewClient() {
        newClientPage.enterClientName(generateFirstName())
                .enterClientSurname(generateLastName())
                .enterClientPhoneNumber(generatePhoneNumber())
                .clickOnButton(newClientPage.getSaveButton(), "Save")
                .shouldHave(newClientPage.getSuccessMessageLocator(), "Success Message")
                .verifyNotificationMessage(newClientPage.getSuccessMessageLocator(), newClientPage.getSuccessMessageValue());
    }

}
