package com.automation.setup;

import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverManager {
    public static ThreadLocal<RemoteWebDriver> driverThread = new ThreadLocal<>();

    public static RemoteWebDriver getCurrentDriver() {
        return driverThread.get();
    }

    public static void setCurrentDriver(RemoteWebDriver driver) {
        driverThread.set(driver);
    }
}
