package com.automation.setup;

import org.openqa.selenium.remote.RemoteWebDriver;

public interface Automation {
    ResourceHelper settings = new ResourceHelper().getResource("settings");
    boolean REMOTE = settings.getBool("REMOTE");
    String BASE_URL = settings.getString("URL");
    String HUB_URL = settings.getString("HUB");
    String BROWSER = settings.getString("BROWSER");
    boolean HEADLESS = settings.getBool("HEADLESS");
    int IMPLICIT_WAIT = settings.getInt("IMPLICIT_WAIT");
    int FLUENT_WAIT = settings.getInt("FLUENT_WAIT");

    String PROJECT_DIR = System.getProperty("user.dir");
    String REPORT_DIR = PROJECT_DIR + "/reports/";
    String EXTEND_REPORT = REPORT_DIR + "/Test-Report.html";
    String REPORT_CONFIG = PROJECT_DIR + "/extent-config.xml";
    String WEB_DRIVER_LOG = PROJECT_DIR + "/web-driver.log";
    String SCREENSHOT_DIR = PROJECT_DIR + "/screenshot/";
    String USER_DATA_CSV = PROJECT_DIR + "/src/test/java/testData/csvData/users.csv";
    String GUEST_DATA_CSV = PROJECT_DIR + "/src/test/java/testData/csvData/guests.csv";
    String TEST_DATA_EXCEL = PROJECT_DIR + "/src/test/java/testData/excelData/TestData.xlsx";

    RemoteWebDriver startDriver(String browser);

    void stopDriver(RemoteWebDriver driver);
}