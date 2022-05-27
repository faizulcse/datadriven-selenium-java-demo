package com.automation.testScenario;

import com.automation.setup.DriverManager;
import com.automation.setup.FileHelper;
import com.automation.setup.TestSetup;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.ResourceHelper;

import java.time.Duration;

public class BaseTest {
    ResourceHelper settings = new ResourceHelper().getResource("settings");
    TestSetup testSetup = new TestSetup();

    @Parameters({"browserType"})
    @BeforeMethod
    public void setUp(@Optional() String browserType) {
        String browser = browserType == null ? settings.getString("browser") : browserType;
        RemoteWebDriver driver = testSetup.startDriver(browser);
        DriverManager.setCurrentDriver(driver);
        DriverManager.getCurrentDriver().get(settings.getString("url"));

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        FileHelper.takeScreenShot(DriverManager.getCurrentDriver(), result.getName());
        testSetup.stopDriver();
    }
}