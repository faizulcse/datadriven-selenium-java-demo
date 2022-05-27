package com.automation.setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ResourceHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestSetup implements Automation {
    public ResourceHelper settings = new ResourceHelper().getResource("settings");

    public synchronized RemoteWebDriver startDriver(String browser) {
        try {
            Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
            System.setErr(new PrintStream(new FileOutputStream("web-driver.log", true)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RemoteWebDriver driver = getWebDriver(browser.toLowerCase());
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        return driver;
    }

    public synchronized void stopDriver() {
        DriverManager.getCurrentDriver().quit();
    }


    private RemoteWebDriver getRemoteDriver(String browser) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName(browser);
            return new RemoteWebDriver(new URL(settings.getString("hub")), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private RemoteWebDriver getWebDriver(String browser) {
        boolean isRemote = settings.getBool("remote");
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(settings.getBool("headless"));
                return isRemote ? getRemoteDriver(browser) : new ChromeDriver(chromeOptions);

            case "opera":
                WebDriverManager.operadriver().setup();
                return isRemote ? getRemoteDriver(browser) : new OperaDriver();

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(settings.getBool("headless"));
                return isRemote ? getRemoteDriver(browser) : new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setHeadless(settings.getBool("headless"));
                return isRemote ? getRemoteDriver(browser) : new EdgeDriver(edgeOptions);

            default:
                throw new RuntimeException("Error! Please enter a valid browser [" + browser + "]");
        }
    }
}