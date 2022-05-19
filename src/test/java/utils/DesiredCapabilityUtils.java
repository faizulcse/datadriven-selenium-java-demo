package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DesiredCapabilityUtils {
    public static DesiredCapabilities getBrowserStackCaps(String appPath, String tcName) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("device", "Samsung Galaxy S20");
        caps.setCapability("os_version", "11");
        caps.setCapability("platformName", "Android");
        caps.setCapability("app", appPath);
        caps.setCapability("appPackage", "app.ferdia.ferdiadriver.qa");
        caps.setCapability("appActivity", "app.ferdia.ferdiadriver.SplashActivity");
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("disableAnimations", true);
        // Additional capabilities for BrowserStack
        caps.setCapability("name", tcName);
        caps.setCapability("project", "SELENIUM AUTOMATION PROJECT");
        caps.setCapability("build", "BUILD NAME");
        caps.setCapability("browserstack.gpsLocation", "23.7746479,90.4031033");
        caps.setCapability("browserstack.timezone", "Dhaka");
        caps.setCapability("browserstack.local", "false");
        return caps;
    }

    public static DesiredCapabilities getLocalCaps(String appPath) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "ANDROID");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11");
        caps.setCapability("app", appPath);
        caps.setCapability("appPackage", "app.ferdia.ferdiadriver.qa");
        caps.setCapability("appActivity", "app.ferdia.ferdiadriver.SplashActivity");
        caps.setCapability("autoGrantPermissions", true);
        caps.setCapability("disableAnimations", true);
        return caps;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getLocalCaps("/users/bs23/downloads/demo.apk"));
        System.out.println(getBrowserStackCaps("/users/bs23/downloads/demo.apk", "Successfully login to the app"));
    }
}
