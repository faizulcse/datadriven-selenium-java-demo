package com.automation.testClasses;

import org.openqa.selenium.By;

public class GoogleSearchPage extends BasePage {
    By agreeButton = By.xpath("//*[text()='I agree']");
    By searchField = By.name("q");

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
