package com.automation.testScenario;

import com.automation.setup.TestSetup;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;

public class BaseTest {
    @BeforeSuite
    public void beforeSuite() throws IOException {
        TestSetup.deleteAllScreenshot();
    }

    @BeforeMethod
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
        TestSetup.startDriver(browser);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        TestSetup.stopDriver();
    }
}
