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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CustomReportListener extends TestListenerAdapter {
    List<String> list = new ArrayList<>();
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
    public void onTestStart(ITestResult result) {
        // Update DataDriven test case name
        String tc = result.getName() + FileHelper.getRunningInfo();
        int i = Collections.frequency(list, tc);
        list.add(tc);
        tc = i > 0 ? tc + "_" + i : tc;
        result.setTestName(tc);
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