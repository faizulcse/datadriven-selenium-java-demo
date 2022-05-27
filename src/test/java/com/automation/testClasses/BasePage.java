package com.automation.testClasses;

import com.automation.setup.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    WebDriver driver;

    public BasePage() {
        driver = DriverManager.getCurrentDriver();
        PageFactory.initElements(driver, this);
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(1000L * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
