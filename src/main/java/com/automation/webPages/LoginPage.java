package com.automation.webPages;

import com.automation.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    WebDriver driver;
    By searchField = By.name("q");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void insertSearchText(String text) {
        driver.findElement(searchField).sendKeys(text);
        sleep(2);   // wait for results display
    }
}
