package utils;

import com.automation.setup.DriverManager;
import com.automation.setup.TestSetup;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class BrowserStackReportListener extends TestListenerAdapter {
    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
    }

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        BrowserStackApi.setTestAsPassed(DriverManager.getCurrentDriver().getSessionId(), BrowserStack.PASSED_MESSAGE);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        BrowserStackApi.setTestAsFailed(DriverManager.getCurrentDriver().getSessionId(), tr.getThrowable().getLocalizedMessage());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        BrowserStackApi.setTestAsFailed(DriverManager.getCurrentDriver().getSessionId(), tr.getSkipCausedBy().toString());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
    }
}
