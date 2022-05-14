package com.automation.testScenario;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static com.automation.testScenario.SetupTest.config;

public class BaseTest {
    private static final ThreadLocal<RemoteWebDriver> driverThread = new ThreadLocal<>();

    @BeforeMethod
    @Parameters({"browserType"})
    private void setUp(@Optional("chrome") String browserType) {
        RemoteWebDriver driver = SetupTest.startDriver(browserType == null ? config.getString("browser") : browserType);
        setCurrentDriver(driver);
    }

    @AfterMethod
    private void tearDown() {
        if (getCurrentDriver() != null)
            SetupTest.stopDriver(getCurrentDriver());
    }

    protected static RemoteWebDriver getCurrentDriver() {
        return driverThread.get();
    }

    private static void setCurrentDriver(RemoteWebDriver driver) {
        driverThread.set(driver);
    }
}
