package com.automation.testScenario;

import com.automation.webPages.GoogleSearchPage;
import dataProvider.CsvDataProvider;
import org.testng.annotations.Test;

public class DataDrivenTest extends BaseTest {

    @Test(testName = "Search on Google with single browser.", dataProvider = "userdata",
            dataProviderClass = CsvDataProvider.class)
    public void googleSearchTest(Object[] data) {
        GoogleSearchPage searchPage = new GoogleSearchPage(getCurrentDriver());
        searchPage.clickAgreeButton();
        searchPage.insertSearchText(data[0].toString());
    }
}
