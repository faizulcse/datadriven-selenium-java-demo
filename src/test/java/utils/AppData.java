package utils;

public class AppData {
    public static String rootDir = System.getProperty("user.dir");
    public static String reportDir = rootDir + "/reports/";
    public static String extendReportName = reportDir + "Test-Report.html";
    public static String reportConfig = rootDir + "/extent-config.xml";
    public static String screenShotDir = rootDir + "/screenshot/";
    public static String userData = rootDir + "/src/test/java/testData/csvData/users.csv";
    public static String guestData = rootDir + "/src/test/java/testData/csvData/guests.csv";
    public static String excelData = rootDir + "/src/test/java/testData/excelData/TestData.xlsx";
}
