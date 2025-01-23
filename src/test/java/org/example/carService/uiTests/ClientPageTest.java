package org.example.carService.uiTests;

import org.example.carService.BaseUiTest;

import org.example.carService.pages.ClientPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClientPageTest extends BaseUiTest {

    private ClientPage clientPage;

    @BeforeMethod
    public void initAndOpenPage() {
        clientPage = new ClientPage();
        clientPage.openPage();
    }

    @Test
    public void testSearchButton() {
        clientPage.checkSearchButton()
                .enterClientNameInSearchField("Олександр \uD83D\uDE01");
    }
}
