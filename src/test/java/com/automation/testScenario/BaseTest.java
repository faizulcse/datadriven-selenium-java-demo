package com.automation.testScenario;

import com.automation.setup.TestSetup;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class BaseTest implements ITest {
    private final ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeSuite
    public void beforeSuite() throws IOException {
        TestSetup.deleteAllScreenshot();
    }

    @BeforeMethod
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browser, Method method) throws MalformedURLException {
        testName.set(TestSetup.getDataDrivenTestName(method.getName()));
        TestSetup.startDriver(browser);
    }


    @AfterMethod
    public void tearDown(ITestResult result) {
        TestSetup.stopDriver();
    }

    @Override
    public String getTestName() {
        return testName.get();
    }
}
