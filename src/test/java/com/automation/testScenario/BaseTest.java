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
    private static final ThreadLocal<String> testName = new ThreadLocal<>();
    private static final List<String> list = new ArrayList<>();

    @BeforeSuite
    public void beforeSuite() throws IOException {
        TestSetup.deleteAllScreenshot();
    }

    @Parameters({"browserType"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, Method method) throws MalformedURLException {
        TestSetup.startDriver(browser);
        testName.set(dataDrivenTestName(method.getName()));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        TestSetup.stopDriver();
    }

    @Override
    public String getTestName() {
        return testName.get();
    }

    public static String dataDrivenTestName(String name) {
        int i = Collections.frequency(list, name);
        list.add(name);
        return i > 0 ? name + "_" + i : name;
    }
}
