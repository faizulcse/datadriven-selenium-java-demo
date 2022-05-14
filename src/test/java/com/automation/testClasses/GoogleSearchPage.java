package com.automation.testClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class GoogleSearchPage extends BasePage {
    By agreeButton = By.xpath("//*[text()='I agree']");
    By searchField = By.name("q");

    public GoogleSearchPage() {
        PageFactory.initElements(getCurrentDriver(), this);
    }

    public void clickAgreeButton() {
        if (getCurrentDriver().findElements(agreeButton).size() > 0) {
            getCurrentDriver().findElement(agreeButton).click();
        }
    }

    public void insertSearchText(String text) {
        getCurrentDriver().findElement(searchField).sendKeys(text);
        sleep(2);   // wait for results display
    }
}
