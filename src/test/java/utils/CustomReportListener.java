package utils;

import com.automation.setup.DriverManager;
import com.automation.setup.TestSetup;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CustomReportListener extends TestListenerAdapter {
    List<String> list = new ArrayList<>();
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    public void onStart(ITestContext testContext) {
        htmlReporter = new ExtentHtmlReporter(AppData.EXTEND_REPORT);
        htmlReporter.loadXMLConfig(AppData.REPORT_CONFIG);
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Host", "localhost");
        extent.setSystemInfo("Environment", "QA");

        htmlReporter.config().setDocumentTitle("Selenium Automation Test Project"); // Tile of report
        htmlReporter.config().setReportName("Functional Test Automation Report"); // name of the report
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); //location of the chart
        htmlReporter.config().setTheme(Theme.DARK);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String browser = DriverManager.getCurrentDriver().getCapabilities().getBrowserName();
        String tc = result.getName() + "_[" + browser + "]";
        int i = Collections.frequency(list, tc);
        list.add(tc);
        tc = i > 0 ? tc + "_" + i : tc;
        result.setTestName(tc);
    }

    public void onTestSuccess(ITestResult tr) {
        String testName = "[✓]" + tr.getName();
        logger = extent.createTest(tr.getName()); // create new entry in th report
        logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN)); // send the passed information to the report with GREEN color highlighted

        try {
            String screenShotName = TestSetup.takeScreenShot(testName);
            logger.pass(screenShotName, MediaEntityBuilder.createScreenCaptureFromPath(AppData.SCREENSHOT_DIR + screenShotName).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestFailure(ITestResult tr) {
        String testName = "[✗]" + tr.getName();
        logger = extent.createTest(tr.getName()); // create new entry in th report
        logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED)); // send the passed information to the report with GREEN color highlighted

        try {
            String screenShotName = TestSetup.takeScreenShot(testName);
            logger.fail(screenShotName, MediaEntityBuilder.createScreenCaptureFromPath(AppData.SCREENSHOT_DIR + screenShotName).build());
            logger.fail(tr.getThrowable().getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult tr) {
        logger = extent.createTest(tr.getName()); // create new entry in th report
        logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}