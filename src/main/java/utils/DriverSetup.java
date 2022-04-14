package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverSetup {
    public static ResourceHelper config = new ResourceHelper().getResource("config");

    public static synchronized WebDriver openBrowser(String browser) {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        switch (browser) {
            case "chrome":
                System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, getDriverPath("chrome"));
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(config.getBoolean("headless"));
                return new ChromeDriver(chromeOptions);
            case "firefox":
                System.setProperty("webdriver.gecko.driver", getDriverPath("firefox"));
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(config.getBoolean("headless"));
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                System.setProperty("webdriver.edge.driver", getDriverPath("edge"));
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setHeadless(config.getBoolean("headless"));
                return new EdgeDriver(edgeOptions);
            default:
                throw new RuntimeException("Error! Please enter a valid browser [" + browser + "]");
        }
    }

    public static String getDriverPath(String browser) {
        switch (browser) {
            case "chrome":
                return System.getenv("CHROMEWEBDRIVER") == null ? "./driver/chromedriver.exe" : System.getenv("CHROMEWEBDRIVER") + "/chromedriver.exe";
            case "firefox":
                return System.getenv("GECKOWEBDRIVER") == null ? "./driver/geckodriver.exe" : System.getenv("GECKOWEBDRIVER") + "/geckodriver.exe";
            case "edge":
                return System.getenv("EDGEWEBDRIVER") == null ? "./driver/msedgedriver.exe" : System.getenv("EDGEWEBDRIVER") + "/msedgedriver.exe";
            default:
                throw new RuntimeException();
        }
    }

    public static synchronized void closeBrowser(WebDriver driver) {
        driver.quit();
    }
}