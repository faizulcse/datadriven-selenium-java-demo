package dataProvider;

import org.testng.annotations.DataProvider;
import com.automation.setup.Automation;
import utils.CSVHelper;

public class CsvDataProvider {
    @DataProvider(name = "userdata", parallel = false)
    public static Object[][] getUserData() {
        return CSVHelper.readCsvData(Automation.USER_DATA_CSV);
    }

    @DataProvider(name = "guestData", parallel = true)
    public static Object[][] getGuestData() {
        return CSVHelper.readCsvData(Automation.GUEST_DATA_CSV);
    }
}
