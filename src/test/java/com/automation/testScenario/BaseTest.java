package com.automation.testScenario;

import com.automation.setup.TestSetup;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.AppData;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class BaseTest {
    @BeforeSuite
    public void beforeSuite() throws IOException {
        for (File listOfFile : Objects.requireNonNull(new File(AppData.screenShotDir).listFiles())) {
            if (listOfFile.getName().endsWith(".png"))
                Files.deleteIfExists(Paths.get(String.valueOf(listOfFile)));
        }
    }

    @BeforeMethod
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
        TestSetup.startDriver(browser);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = (ITestResult.FAILURE == result.getStatus() ? "✗" : "✓") + result.getName();
        TestSetup.takeScreenShot(testName);
        TestSetup.stopDriver();
    }
}
