package com.automation.testScenario;

import com.automation.setup.TestSetup;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

public class BaseTest implements ITest {
    ThreadLocal<String> testName = new ThreadLocal<>();

    @Parameters({"browserType"})
    @BeforeMethod
    public void setUp(@Optional() String browser, Method method) {
        TestSetup.startDriver(browser);
        testName.set(TestSetup.updateTcName(method.getName()));
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
