package com.automation.testClasses;

import com.automation.setup.TestSetup;

public class BasePage extends TestSetup {
    public static void sleep(int seconds) {
        try {
            Thread.sleep(1000L * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
