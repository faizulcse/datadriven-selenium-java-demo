package com.automation.tests;

import com.automation.BaseTest;
import com.automation.webPages.GoogleSearchPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CSVHelper;

public class DataDrivenParallelTest extends BaseTest {
    @DataProvider(name = "guestData", parallel = true)
    public Object[][] getUsersData() {
        String users_csv = System.getProperty("user.dir") + "/src/test/resources/guests.csv";
        return CSVHelper.readCsvData(users_csv);
    }

    @Test(testName = "Search on Google with parallel browser.", dataProvider = "guestData")
    public void googleSearchParallelTest(Object[] data) {
        GoogleSearchPage searchPage = new GoogleSearchPage(getCurrentDriver());
        searchPage.clickAgreeButton();
        searchPage.insertSearchText(data[0].toString());
    }
}
