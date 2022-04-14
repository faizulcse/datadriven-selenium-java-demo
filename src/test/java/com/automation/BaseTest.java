package com.automation;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverSetup;

import java.time.Duration;

public class BaseTest {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final String browser = System.getProperty("browser");

    @BeforeMethod
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browserType) {
        WebDriver driver = DriverSetup.openBrowser(browser == null ? browserType : browser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DriverSetup.config.getInteger("wait")));
        driver.get(DriverSetup.config.getString("url"));
        setCurrentDriver(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (getCurrentDriver() != null)
            DriverSetup.closeBrowser(getCurrentDriver());
    }

    public static WebDriver getCurrentDriver() {
        return driverThread.get();
    }

    public static void setCurrentDriver(WebDriver driver) {
        driverThread.set(driver);
    }
}
