package com.automation.tests;

import com.automation.BaseTest;
import com.automation.webPages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CSVHelper;

public class DataDrivenTest extends BaseTest {
    @DataProvider(name = "userdata", parallel = false)
    public Object[][] getUsersData() {
        String users_csv = System.getProperty("user.dir") + "/src/test/resources/users.csv";
        return CSVHelper.readCsvData(users_csv);
    }

    @Test(dataProvider = "userdata")
    public void loginTest(Object[] data) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.insertSearchText(data[0].toString());
    }
}
