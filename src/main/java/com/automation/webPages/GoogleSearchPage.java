package com.automation.webPages;

import com.automation.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleSearchPage extends BasePage {
    WebDriver driver;
    By agreeButton = By.xpath("//*[text()='I agree']");
    By searchField = By.name("q");

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAgreeButton() {
        if (driver.findElements(agreeButton).size() > 0) {
            driver.findElement(agreeButton).click();
        }
    }

    public void insertSearchText(String text) {
        driver.findElement(searchField).sendKeys(text);
        sleep(2);   // wait for results display
    }
}
