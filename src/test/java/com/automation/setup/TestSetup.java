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

import java.net.MalformedURLException;
import java.net.URL;

public class TestSetup implements Automation {
    @Override
    public synchronized RemoteWebDriver startDriver(String browser) {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(HEADLESS);
                return REMOTE ? getRemoteDriver(browser) : new ChromeDriver(chromeOptions);

            case "opera":
                WebDriverManager.operadriver().setup();
                return REMOTE ? getRemoteDriver(browser) : new OperaDriver();

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(HEADLESS);
                return REMOTE ? getRemoteDriver(browser) : new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setHeadless(HEADLESS);
                return REMOTE ? getRemoteDriver(browser) : new EdgeDriver(edgeOptions);

            default:
                throw new RuntimeException("Error! Please enter a valid browser [" + browser + "]");
        }
    }

    @Override
    public synchronized void stopDriver(RemoteWebDriver driver) {
        if (driver != null)
            driver.quit();
    }

    private RemoteWebDriver getRemoteDriver(String browser) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setBrowserName(browser);
            return new RemoteWebDriver(new URL(HUB_URL), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}