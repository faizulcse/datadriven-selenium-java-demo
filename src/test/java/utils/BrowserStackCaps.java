package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserStackCaps {
    public static final String PROJECT_NAME = "SELENIUM AUTOMATION PROJECT";
    public static final String BUILD_NAME = System.getenv("BROWSERSTACK_BUILD_NAME");
    public static final String GPS_LOCATION = "23.7746479,90.4031033";
    public static final String TIMEZONE = "Dhaka";
    public static String appId = BrowserStackApi.getRecentApp("test_app");
    public static String testCaseName = "";
    public static String deviceName = "";
    public static String osVersion = "";
    public static String platformName = "";

    public static DesiredCapabilities getBrowserStackCaps() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("device", deviceName);
        caps.setCapability("os_version", osVersion);
        caps.setCapability("platformName", platformName);
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("disableAnimations", true);

        caps.setCapability("app", appId);
        caps.setCapability("name", testCaseName);
        caps.setCapability("project", PROJECT_NAME);
        caps.setCapability("build", BUILD_NAME);
        caps.setCapability("browserstack.gpsLocation", GPS_LOCATION);
        caps.setCapability("browserstack.timezone", TIMEZONE);
        caps.setCapability("browserstack.local", BrowserStackLocal.isLocalRunning());
        return caps;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBrowserStackCaps());
    }
}
