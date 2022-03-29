package com.automation;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverSetup;

import java.time.Duration;

public class BaseTest {
    private static ThreadLocal<RemoteWebDriver> driverThread = new ThreadLocal<>();

    @BeforeMethod
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browserType) {
        RemoteWebDriver driver = DriverSetup.openBrowser(browserType);
        assert driver != null;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DriverSetup.config.getInteger("wait")));
        driver.get(DriverSetup.config.getString("url"));
    }

    @AfterMethod
    public void tearDown() {
        DriverSetup.closeBrowser(getCurrentDriver());
    }

    public static RemoteWebDriver getCurrentDriver() {
        return driverThread.get();
    }

    public static void setCurrentDriver(RemoteWebDriver driver) {
        driverThread.set(driver);
    }
}
