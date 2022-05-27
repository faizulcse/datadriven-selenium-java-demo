package utils;

import com.automation.setup.DriverManager;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class BsReportListener extends TestListenerAdapter {
    BrowserStackUtils bsUtils = new BrowserStackUtils();

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
        bsUtils.setTestAsPassed(DriverManager.getCurrentDriver().getSessionId());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        bsUtils.setTestAsFailed(DriverManager.getCurrentDriver().getSessionId(), tr.getThrowable().getLocalizedMessage());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        bsUtils.setTestAsFailed(DriverManager.getCurrentDriver().getSessionId(), tr.getSkipCausedBy().toString());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
    }
}
