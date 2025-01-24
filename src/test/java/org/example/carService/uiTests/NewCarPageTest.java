package org.example.carService.uiTests;

import io.qameta.allure.Description;
import org.example.carService.BaseUiTest;
import org.example.carService.pages.NewCarPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.example.carService.utils.TestDataGenerator.*;

public class NewCarPageTest extends BaseUiTest {
    private NewCarPage newCarPage;

    @BeforeMethod
    public void initAndOpenPage() {
        newCarPage = new NewCarPage();
        String clientId = "1";
        newCarPage.openPage(clientId);
    }

    @Test
    @Description("""
                    - Car number field should be entered with a valid car number.
                    - VIN code should be valid and entered into the appropriate field.
                    - Car brand should be entered correctly.
                    - Car model should be entered correctly.
                    - 'Add' button should be clicked to submit the form.
                    - A success message should appear upon successful submission.
                    - The success message should match the expected notification text.
            """)
    public void createNewCar() {
        newCarPage.enterCarNumber(generateCarNumber())
                .enterCarVinCode(generateVinCode())
                .enterCarBrand(generateCarBrand())
                .enterCarModel(generateCarModel())
                .clickOnButton(newCarPage.getAddButton(), "Add")
                .shouldHave(newCarPage.getSuccessMessageLocator(), "Success Message")
                .verifyNotificationMessage(newCarPage.getSuccessMessageLocator(), newCarPage.getSuccessMessageValue());
    }
}
