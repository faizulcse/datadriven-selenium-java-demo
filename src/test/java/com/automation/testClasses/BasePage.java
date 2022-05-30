package com.automation.testClasses;

import com.automation.setup.Automation;
import com.automation.setup.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;

public class BasePage {
    WebDriver driver;

    public BasePage() {
        driver = DriverManager.getCurrentDriver();
        PageFactory.initElements(driver, this);
    }

    public void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public void waitAndClickElement(By locator) {
        waitAndClickElement(locator, Automation.FLUENT_WAIT);
    }

    public void waitAndClickElement(By locator, int timeout) {
        waitForElementLoad(locator, timeout).click();
    }

    public WebElement waitForElementLoad(By locator) {
        return waitForElementLoad(locator, Automation.FLUENT_WAIT);
    }

    public WebElement waitForElementLoad(By locator, int timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        WebElement element = new FluentWait<>(driver)
                .withMessage("\nElement Not Found." + locator.toString())
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Automation.IMPLICIT_WAIT));
        return element;
    }

    public void setValue(By locator, String value) {
        WebElement element = driver.findElement(locator);
        Assert.assertTrue(element.isDisplayed());
        element.clear();
        element.sendKeys(value);
    }

    public boolean isPresent(By locator) {
        return isPresent(locator, 0);
    }

    public boolean isPresent(By locator, int timeout) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        boolean result = driver.findElements(locator).size() > 0;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Automation.IMPLICIT_WAIT));
        return result;
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(1000L * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
