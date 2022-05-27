package com.automation.testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//*[text()='I agree']")
    protected WebElement agreeButton;

    @FindBy(name = "q")
    protected WebElement searchField;

    public void clickAgreeButton() {
        if (driver.findElements(By.xpath("//*[text()='I agree']")).size() > 0) {
            agreeButton.click();
        }
    }

    public void insertSearchText(String text) {
        searchField.sendKeys(text);
        sleep(2);   // wait for results display
    }
}
