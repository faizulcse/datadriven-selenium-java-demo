package utils;

public interface AppData {
    String PROJECT_DIR = System.getProperty("user.dir");
    String REPORT_DIR = System.getProperty("user.dir") + "/reports/";
    String EXTEND_REPORT = System.getProperty("user.dir") + "/reports/Test-Report.html";
    String REPORT_CONFIG = System.getProperty("user.dir") + "/extent-config.xml";
    String SCREENSHOT_DIR = System.getProperty("user.dir") + "/screenshot/";
    String USER_DATA_CSV = System.getProperty("user.dir") + "/src/test/java/testData/csvData/users.csv";
    String GUEST_DATA_CSV = System.getProperty("user.dir") + "/src/test/java/testData/csvData/guests.csv";
    String TEST_DATA_EXCEL = System.getProperty("user.dir") + "/src/test/java/testData/excelData/TestData.xlsx";
    ResourceHelper SETTINGS = new ResourceHelper().getResource("settings");
}
