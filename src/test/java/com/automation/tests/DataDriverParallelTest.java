package com.automation.tests;

import com.automation.BaseTest;
import com.automation.webPages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CSVHelper;

public class DataDriverParallelTest extends BaseTest {
    @DataProvider(name = "guestData", parallel = true)
    public Object[][] getUsersData() {
        String users_csv = System.getProperty("user.dir") + "/src/test/resources/guests.csv";
        return CSVHelper.readCsvData(users_csv);
    }

    @Test(dataProvider = "guestData")
    public void loginTest(Object[] data) {
        WebDriver driver = driverThread.get();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickAgreeButton();
        loginPage.insertSearchText(data[0].toString());
    }
}
