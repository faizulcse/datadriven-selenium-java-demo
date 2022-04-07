package utils;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverSetup {
    public static ResourceHelper config = new ResourceHelper().getResource("config");
    private static String remoteUrl = Boolean.parseBoolean(System.getenv("DOCKER")) ? "http://hub:4444/wd/hub" : "http://localhost:4444/wd/hub";

    public static synchronized RemoteWebDriver openBrowser(String browser) {
        System.out.println("Selected Browser: =========> " + browser);
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        try {
            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setHeadless(config.getBoolean("headless"));
                    return new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setHeadless(config.getBoolean("headless"));
                    return new RemoteWebDriver(new URL(remoteUrl), firefoxOptions);
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.setHeadless(config.getBoolean("headless"));
                    return new RemoteWebDriver(new URL(remoteUrl), edgeOptions);
                default:
                    throw new RuntimeException("Error! Please enter a valid browser [" + browser + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized void closeBrowser(RemoteWebDriver driver) {
        driver.quit();
    }
}