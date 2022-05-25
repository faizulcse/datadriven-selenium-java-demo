package com.automation.setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SeleniumRnD {
    public static WebDriver driver;
    public static int impWait = 10;
    static int i = 1;

    @BeforeMethod
    public void setUp() throws FileNotFoundException {
        System.setErr(new PrintStream(new FileOutputStream("web-driver.log")));

        WebDriverManager.chromedriver().setup();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.setExperimentalOption("useAutomationExtension", true);

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
    }

    @Test
    public void verifySeleniumWait() {
        driver.get("https://google.com");
        WebElement searchField = waitUntilElementLoading(By.name("qs"));
        System.out.println(searchField.isDisplayed());
        System.out.println(searchField.isEnabled());
        System.out.println(searchField.isSelected());
        System.out.println(searchField.getText());
        System.out.println(searchField.getTagName());
        System.out.println(searchField.getAccessibleName());
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

    public static WebElement waitUntilElementLoading(By by) {
        return waitUntilElementLoading(by, 30);
    }

    public static WebElement waitUntilElementLoading(By by, int timeout) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class)
                    .withMessage("Element not found. " + by.toString())
                    .until(driver -> driver.findElements(by).size() > 0);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
        } catch (Exception e) {
            Assert.fail(e.getLocalizedMessage());
        }
        return driver.findElement(by);
    }
}
