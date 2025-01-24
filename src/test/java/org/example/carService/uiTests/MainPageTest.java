package org.example.carService.uiTests;

import org.example.carService.BaseUiTest;

import org.example.carService.pages.MainPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MainPageTest extends BaseUiTest {

    private MainPage mainPage;

    @BeforeMethod
    public void initAndOpenPage() {
        mainPage = new MainPage();
        mainPage.openPage();
    }

    @Test
    public void testSearchButton() {
        mainPage.checkSearchButton()
                .enterClientNameInSearchField("Олександр \uD83D\uDE01");
    }
}
