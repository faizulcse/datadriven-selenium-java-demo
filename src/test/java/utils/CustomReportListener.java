package utils;

import com.automation.setup.Automation;
import com.automation.setup.DriverManager;
import com.automation.setup.FileHelper;
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


public class CustomReportListener extends TestListenerAdapter {
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    public void onStart(ITestContext testContext) {
        htmlReporter = new ExtentHtmlReporter(Automation.EXTEND_REPORT);
        htmlReporter.loadXMLConfig(Automation.REPORT_CONFIG);
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Host", "localhost");
        extent.setSystemInfo("Env", "QA");

        htmlReporter.config().setDocumentTitle("Selenium Automation Test Project"); // Tile of report
        htmlReporter.config().setReportName("Functional Test Automation Report"); // name of the report
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); //location of the chart
        htmlReporter.config().setTheme(Theme.DARK);
    }

    @Override
    public void onTestStart(ITestResult tr) {
        String tc = FileHelper.modifyTestName(tr.getName());
        tr.setTestName(tc);        // Modify DataDriven test name
    }

    public void onTestSuccess(ITestResult tr) {
        try {
            String screenShotPath = FileHelper.takeScreenShot(DriverManager.getCurrentDriver(), tr.getName());
            logger = extent.createTest(tr.getName());
            logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
            logger.pass("Screenshot:", MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestFailure(ITestResult tr) {
        try {
            String screenShotPath = FileHelper.takeScreenShot(DriverManager.getCurrentDriver(), tr.getName());
            logger = extent.createTest(tr.getName());
            logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
            logger.error(tr.getThrowable());
            logger.fail("Screenshot:", MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult tr) {
        logger = extent.createTest(tr.getName());
        logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}