package com.automation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.Arrays;

public class ExcelDataTest {
    static String filePath = System.getProperty("user.dir") + "/src/test/java/testData/TestData.xlsx";

    @DataProvider(name = "singleTripData")
    public static Object[][] getSingleTripData() {
        ExcelUtils excelUtils = new ExcelUtils(filePath).getWorkSheet("SingleTrip");
        return excelUtils.getExcelData();
    }

    @DataProvider(name = "roundTripData")
    public static Object[][] getRoundTripData() {
        ExcelUtils excelUtils = new ExcelUtils(filePath).getWorkSheet(1);
        return excelUtils.getExcelData();
    }

    @Test(dataProvider = "singleTripData")
    public void singleSheetDataTest(Object[] data) {
        System.out.println(Arrays.toString(data));
    }

    @Test(dataProvider = "roundTripData")
    public void roundSheetDataTest(Object[] data) {
        System.out.println(Arrays.toString(data));
    }
}
