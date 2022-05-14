package com.automation.testScenario;

import org.testng.annotations.Test;
import dataProvider.ExcelDataProvider;

import java.util.Arrays;

public class ExcelDataTest {

    @Test(dataProvider = "singleTripData", dataProviderClass = ExcelDataProvider.class)
    public void singleSheetDataTest(Object[] data) {
        System.out.println(Arrays.toString(data));
    }

    @Test(dataProvider = "roundTripData", dataProviderClass = ExcelDataProvider.class)
    public void roundSheetDataTest(Object[] data) {
        System.out.println(Arrays.toString(data));
    }
}
