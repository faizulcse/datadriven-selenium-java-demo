package com.automation.setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumRnD {
    public static WebDriver driver;
    public static int impWait = 20;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
    }

    @Test
    public void verifySeleniumWait() {
        driver.get("https://google.com");
        WebElement searchField = waitForLoading(By.name("q"), 10);
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

    public static WebElement waitForLoading(By by, int timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        try {
            new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class)
                    .withMessage("Element not found: " + by.toString())
                    .until(driver -> driver.findElements(by).size() > 0);
        } catch (Exception e) {
            Assert.fail(e.getLocalizedMessage());
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
        return driver.findElement(by);
    }
}
