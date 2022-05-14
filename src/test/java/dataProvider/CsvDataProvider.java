package dataProvider;

import org.testng.annotations.DataProvider;
import utils.AppData;
import utils.CSVHelper;

public class CsvDataProvider {
    @DataProvider(name = "userdata", parallel = false)
    public static Object[][] getUserData() {
        return CSVHelper.readCsvData(AppData.userData);
    }

    @DataProvider(name = "guestData", parallel = true)
    public static Object[][] getGuestData() {
        return CSVHelper.readCsvData(AppData.guestData);
    }
}
