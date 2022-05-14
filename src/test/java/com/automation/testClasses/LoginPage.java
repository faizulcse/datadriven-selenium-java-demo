package com.automation.testClasses;

import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    public LoginPage() {
        PageFactory.initElements(getCurrentDriver(), this);
    }
}
