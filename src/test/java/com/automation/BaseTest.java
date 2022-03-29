package com.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverSetup;

import java.time.Duration;

public class BaseTest {
    private static ThreadLocal<RemoteWebDriver> driverThread = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        RemoteWebDriver driver = DriverSetup.openBrowser("chrome");
        assert driver != null;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DriverSetup.config.getInteger("wait")));
        driver.get(DriverSetup.config.getString("url"));
        setCurrentDriver(driver);
    }

    @AfterMethod
    public void tearDown() {
        DriverSetup.closeBrowser(getCurrentDriver());
    }

    public static WebDriver getCurrentDriver() {
        return driverThread.get();
    }

    public static void setCurrentDriver(RemoteWebDriver driver) {
        driverThread.set(driver);
    }
}
