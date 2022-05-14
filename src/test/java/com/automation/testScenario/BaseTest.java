package com.automation.testScenario;

import com.automation.setup.SetupTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    @BeforeMethod
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browser) {
        SetupTest.startDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        SetupTest.stopDriver();
    }
}
