package com.automation.testScenario;

import com.automation.webPages.GoogleSearchPage;
import dataProvider.CsvDataProvider;
import org.testng.annotations.Test;

public class DataDrivenParallelTest extends BaseTest {
    @Test(testName = "Search on Google with parallel browser.", dataProvider = "guestData",
            dataProviderClass = CsvDataProvider.class)
    public void googleSearchParallelTest(Object[] data) {
        GoogleSearchPage searchPage = new GoogleSearchPage(getCurrentDriver());
        searchPage.clickAgreeButton();
        searchPage.insertSearchText(data[0].toString());
    }
}
