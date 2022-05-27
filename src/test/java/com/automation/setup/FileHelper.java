package com.automation.setup;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileHelper {
    public static String takeScreenShot(RemoteWebDriver driver, String screenshotName) {
        String screenshot = screenshotName + ".png";
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(Automation.SCREENSHOT_DIR + screenshot));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(screenshot);
        return screenshot;
    }

    public static void deleteAllScreenshot() {
        try {
            for (File listOfFile : Objects.requireNonNull(new File(Automation.SCREENSHOT_DIR).listFiles())) {
                if (listOfFile.getName().endsWith(".png"))
                    Files.deleteIfExists(Paths.get(String.valueOf(listOfFile)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
