package com.automation.testScenario;

import com.automation.setup.TestSetup;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    @BeforeMethod
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browser) {
        TestSetup.startDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        TestSetup.stopDriver();
    }
}
