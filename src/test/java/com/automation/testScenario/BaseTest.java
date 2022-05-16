package com.automation.testScenario;

import com.automation.setup.TestSetup;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseTest implements ITest {
    public static List<String> list = new ArrayList<>();
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeSuite
    public void beforeSuite() throws IOException {
        TestSetup.deleteAllScreenshot();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browserType"})
    public void setUp(@Optional("chrome") String browser, Method method, Object[] row) throws MalformedURLException {
        String tcName = method.getName();
        if (list.contains(method.getName()))
            testName.set(tcName + "_" + Collections.frequency(list, tcName));
        list.add(tcName);

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
