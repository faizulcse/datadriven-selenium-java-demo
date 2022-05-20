package com.automation.setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.AppData;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestSetup implements AppData {
    static ThreadLocal<RemoteWebDriver> driverThread = new ThreadLocal<>();
    static ThreadLocal<String> browserType = new ThreadLocal<>();
    static List<String> list = new ArrayList<>();

    public static String getBrowserType() {
        return browserType.get();
    }

    public static void setBrowserType(String name) {
        browserType.set(name == null ? BROWSER_SETTINGS.getString("browser") : name);
    }

    public static RemoteWebDriver getCurrentDriver() {
        return driverThread.get();
    }

    private static void setCurrentDriver(RemoteWebDriver driver) {
        driverThread.set(driver);
    }

    public static synchronized void startDriver(String browser) {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        try {
            setBrowserType(browser);
            RemoteWebDriver driver = BROWSER_SETTINGS.getBoolean("remote") ? getRemoteDriver(getBrowserType()) : getLocalDriver(getBrowserType());
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(BROWSER_SETTINGS.getInteger("wait")));
            driver.get(BROWSER_SETTINGS.getString("url"));
            setCurrentDriver(driver);
        } catch (MalformedURLException ignored) {
        }
    }

    public static synchronized void stopDriver() {
        getCurrentDriver().quit();
    }

    public static String takeScreenShot(String screenshotName) throws IOException {
        String screenshot = screenshotName + ".png";
        File screenshotFile = ((TakesScreenshot) getCurrentDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(SCREENSHOT_DIR + screenshot));
        return screenshot;
    }

    public static void deleteAllScreenshot() {
        try {
            for (File listOfFile : Objects.requireNonNull(new File(SCREENSHOT_DIR).listFiles())) {
                if (listOfFile.getName().endsWith(".png"))
                    Files.deleteIfExists(Paths.get(String.valueOf(listOfFile)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String updateTcName(String name) {
        String tcName = name + "_[" + getBrowserType().toUpperCase() + "]";
        int i = Collections.frequency(list, tcName);
        list.add(tcName);
        return i > 0 ? tcName + "_" + i : tcName;
    }

    public static synchronized RemoteWebDriver getRemoteDriver(String browser) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);
        return new RemoteWebDriver(new URL(BROWSER_SETTINGS.getString("hub")), caps);
    }

    public static RemoteWebDriver getLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(BROWSER_SETTINGS.getBoolean("headless"));
                return new ChromeDriver(chromeOptions);
            case "opera":
                WebDriverManager.operadriver().setup();
                return new OperaDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(BROWSER_SETTINGS.getBoolean("headless"));
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setHeadless(BROWSER_SETTINGS.getBoolean("headless"));
                return new EdgeDriver(edgeOptions);
            default:
                throw new RuntimeException("Error! Please enter a valid browser [" + browser + "]");
        }
    }
}