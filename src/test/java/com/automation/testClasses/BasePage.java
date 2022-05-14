package com.automation.testClasses;

import com.automation.setup.SetupTest;

public class BasePage extends SetupTest {
    public static void sleep(int seconds) {
        try {
            Thread.sleep(1000L * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
