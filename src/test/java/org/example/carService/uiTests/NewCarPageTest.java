package org.example.carService.uiTests;

import org.example.carService.BaseUiTest;
import org.example.carService.pages.NewCarPage;
import org.example.carService.pages.NewClientPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.example.carService.utils.TestDataGenerator.*;

public class NewCarPageTest extends BaseUiTest {

    private NewClientPage newClientPage;
    private NewCarPage newCarPage;

    @BeforeMethod
    public void initAndOpenPage() {
        newClientPage = new NewClientPage();
        newCarPage = new NewCarPage();
//        newClientPage.openPage(newClientPage.getPageName());
        newCarPage.openPage(newCarPage.getPageName());
    }


    @Test
    public void createNewCar() {
        newCarPage.enterCarNumber(generateFirstName())
                .enterCarVinCode(generateLastName())
                .enterCarBrand(generatePhoneNumber())
                .enterCarModel(generateFirstName())
                .clickOnButton(newCarPage.getAddButton(), "Add")
                .shouldHave(newCarPage.getSuccessMessageLocator(), "Success Message");
        newCarPage.verifySuccessCreationMessage();
    }
}
