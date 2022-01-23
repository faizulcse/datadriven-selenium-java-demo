package com.automation;

public class BasePage {
    public static void sleep(int seconds){
        try {
            Thread.sleep(1000L*seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
