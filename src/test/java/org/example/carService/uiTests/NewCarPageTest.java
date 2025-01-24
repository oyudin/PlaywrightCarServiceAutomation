package org.example.carService.uiTests;

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
