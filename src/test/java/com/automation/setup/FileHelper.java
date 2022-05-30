package com.automation.setup;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileHelper {
    static List<String> list = new ArrayList<>();

    private static String getRunningInfo() {
        String browser = DriverManager.getCurrentDriver().getCapabilities().getBrowserName();
        String osName = System.getProperty("os.name").split("\\s")[0];
        return "[" + browser + "_" + osName + "]";
    }

    public static synchronized String modifyTestName(String str) {
        String tc = str + getRunningInfo();
        int i = Collections.frequency(list, tc);
        list.add(tc);
        return i > 0 ? tc + "_" + i : tc;
    }

    public static synchronized String takeScreenShot(RemoteWebDriver driver, String screenshotName) {
        String screenshot = screenshotName + ".png";
        String screenShotPath = Automation.SCREENSHOT_DIR + screenshot;
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(screenShotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenShotPath;
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
