package com.automation;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverSetup;

public class BaseTest {
    public static WebDriver driver;
    public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        driver = DriverSetup.openBrowser();
        driverThread.set(driver);
    }

    @AfterMethod
    public void tearDown() {
        DriverSetup.closeBrowser(driverThread.get());
    }
}
