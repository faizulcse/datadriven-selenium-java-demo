package com.automation.tests;

import com.automation.BaseTest;
import com.automation.webPages.GoogleSearchPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CSVHelper;

public class DataDrivenTest extends BaseTest {
    @DataProvider(name = "userdata", parallel = false)
    public Object[][] getUsersData() {
        String users_csv = System.getProperty("user.dir") + "/src/test/resources/users.csv";
        return CSVHelper.readCsvData(users_csv);
    }

    @Test(testName = "Search on Google with single browser.", dataProvider = "userdata")
    public void googleSearchTest(Object[] data) {
        GoogleSearchPage searchPage = new GoogleSearchPage(getCurrentDriver());
        searchPage.clickAgreeButton();
        searchPage.insertSearchText(data[0].toString());
    }
}
