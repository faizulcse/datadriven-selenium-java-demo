package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserStackCaps {
    private static final String deviceProfile = System.getProperty("device") == null ? "bs_default" : System.getProperty("device");
    private static final ResourceHelper bsDevice = new ResourceHelper().getResource(deviceProfile);

    private static final String PROJECT_NAME = "SELENIUM AUTOMATION PROJECT";
    private static final String BUILD_NAME = System.getenv("BROWSERSTACK_BUILD_NAME");
    private static final String GPS_LOCATION = "23.7746479,90.4031033";
    private static final String TIMEZONE = "Dhaka";

    public static DesiredCapabilities getBrowserStackCaps() throws Exception {
        return getBrowserStackCaps("");
    }

    public static DesiredCapabilities getBrowserStackCaps(String testName) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("device", bsDevice.getString("device"));
        caps.setCapability("os_version", bsDevice.getString("os_version"));
        caps.setCapability("platformName", bsDevice.getString("platformName"));
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("disableAnimations", true);

        caps.setCapability("app", BrowserStackApi.getRecentApp("test_app"));
        caps.setCapability("name", testName);
        caps.setCapability("project", PROJECT_NAME);
        caps.setCapability("build", BUILD_NAME);
        caps.setCapability("browserstack.gpsLocation", GPS_LOCATION);
        caps.setCapability("browserstack.timezone", TIMEZONE);
        caps.setCapability("browserstack.local", BrowserStackLocal.isLocalRunning());
        return caps;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getBrowserStackCaps());
        System.out.println(getBrowserStackCaps("faizulcse"));
    }
}
