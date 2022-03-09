package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverSetup {
    public static ResourceHelper config = new ResourceHelper().getResource("config");

    public static WebDriver openBrowser() {
        WebDriver driver = null;
        try {
            Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + "/driver/chromedriver.exe");
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");

            ChromeDriverService service = new ChromeDriverService.Builder().build();
            service.sendOutputTo(new FileOutputStream("chromedriver_log.txt"));
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(config.getBoolean("headless"));

            driver = new ChromeDriver(service, options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getInteger("wait")));
            driver.get(config.getString("url"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }

    public static void closeBrowser(WebDriver driver) {
        driver.quit();
    }
}