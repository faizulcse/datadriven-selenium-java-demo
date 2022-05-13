package com.automation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.util.Arrays;

public class ExcelDataTest {
    static String filePath = System.getProperty("user.dir") + "/src/test/java/testData/TestData.xlsx";

    @DataProvider(name = "singleTripData")
    public static Object[][] getSingleTripData() {
        return ExcelUtils.getExcelData(filePath, "SingleTrip");
    }

    @Test(dataProvider = "singleTripData")
    public void excelSheetDataTest(Object[] data) {
        System.out.println(Arrays.toString(data));
    }
}
