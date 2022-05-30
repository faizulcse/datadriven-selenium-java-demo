package com.automation.testScenario;

import com.automation.setup.Automation;
import com.automation.setup.DriverManager;
import com.automation.setup.TestSetup;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class BaseTest {
    TestSetup testSetup = new TestSetup();

    @Parameters({"browserType"})
    @BeforeMethod
    public void setUp(@Optional() String browserType) throws FileNotFoundException {
        System.setErr(new PrintStream(new FileOutputStream(Automation.WEB_DRIVER_LOG, true)));
        String browser = browserType == null ? Automation.BROWSER : browserType;

        RemoteWebDriver driver = testSetup.startDriver(browser);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(Automation.BASE_URL);
        DriverManager.setCurrentDriver(driver);
    }

    @AfterMethod
    public void tearDown() {
        testSetup.stopDriver(DriverManager.getCurrentDriver());
    }
}