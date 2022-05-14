package dataProvider;

import org.testng.annotations.DataProvider;
import utils.AppData;
import utils.ExcelUtils;

public class ExcelDataProvider {
    @DataProvider(name = "singleTripData")
    public static Object[][] getSingleTripData() {
        return getExcelData(AppData.excelData, "SingleTrip");
    }

    @DataProvider(name = "roundTripData")
    public static Object[][] getRoundTripData() {
        return getExcelData(AppData.excelData, "RoundTrip");
    }

    public static Object[][] getExcelData(String excelFile, String sheetName) {
        ExcelUtils excelUtils = new ExcelUtils(excelFile).getWorkSheet(sheetName);

        int r = excelUtils.getRowCount();
        int c = excelUtils.getCellCount(1);

        String[][] data = new String[r][c];
        for (int i = 1; i <= r; i++) {
            for (int j = 0; j < c; j++) {
                data[i - 1][j] = excelUtils.getCellData(i, j);
            }
        }
        return data;
    }
}
