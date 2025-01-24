package org.example.carService.uiTests;

import io.qameta.allure.Description;
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
    @Description("""
                    - Client name field should be entered with a valid first name.
                    - Client surname field should be entered with a valid last name.
                    - Client phone number should be entered with a valid phone number.
                    - 'Save' button should be clicked to submit the form.
                    - A success message should appear upon successful submission.
                    - The success message should match the expected notification text.
                    - Ensure proper functionality of success message verification.
            """)
    public void createNewClient() {
        newClientPage.enterClientName(generateFirstName())
                .enterClientSurname(generateLastName())
                .enterClientPhoneNumber(generatePhoneNumber())
                .clickOnButton(newClientPage.getSaveButton(), "Save")
                .shouldHave(newClientPage.getSuccessMessageLocator(), "Success Message")
                .verifyNotificationMessage(newClientPage.getSuccessMessageLocator(), newClientPage.getSuccessMessageValue());
    }

}
