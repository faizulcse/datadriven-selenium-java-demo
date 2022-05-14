package com.automation.testScenario;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetupTest {
    public static ResourceHelper config = new ResourceHelper().getResource("config");

    public static synchronized RemoteWebDriver startDriver(String browser) {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        RemoteWebDriver driver = null;
        try {
            driver = config.getBoolean("remote") ? getRemoteDriver(browser) : getLocalDriver(browser);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getInteger("wait")));
            driver.get(config.getString("url"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public static synchronized void stopDriver(RemoteWebDriver driver) {
        driver.quit();
    }

    public static synchronized RemoteWebDriver getRemoteDriver(String browser) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);
        return new RemoteWebDriver(new URL(config.getString("hub")), caps);
    }

    public static RemoteWebDriver getLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(config.getBoolean("headless"));
                return new ChromeDriver(chromeOptions);
            case "opera":
                WebDriverManager.operadriver().setup();
                return new OperaDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(config.getBoolean("headless"));
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setHeadless(config.getBoolean("headless"));
                return new EdgeDriver(edgeOptions);
            default:
                throw new RuntimeException("Error! Please enter a valid browser [" + browser + "]");
        }
    }
}