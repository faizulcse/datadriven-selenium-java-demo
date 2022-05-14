package com.automation.testClasses;

import com.automation.testScenario.BaseTest;

public class BasePage extends BaseTest {
    public static void sleep(int seconds) {
        try {
            Thread.sleep(1000L * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
