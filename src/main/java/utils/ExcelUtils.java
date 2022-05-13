package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    private static XSSFSheet excelWSheet;
    private static XSSFWorkbook excelWBook;
    private static XSSFCell cell;
    private static XSSFRow row;

    public ExcelUtils(String excelFile) throws IOException {
        excelWBook = new XSSFWorkbook(new FileInputStream(excelFile));
    }

    public ExcelUtils getWorkSheet(String sheetName) {
        excelWSheet = excelWBook.getSheet(sheetName);
        return this;
    }

    public int getRowCount() {
        return excelWSheet.getLastRowNum();
    }

    public int getCellCount(int rowNumber) {
        return excelWSheet.getRow(rowNumber).getLastCellNum();
    }

    public String getCellData(int rowNum, int columnNum) {
        row = excelWSheet.getRow(rowNum);
        cell = row.getCell(columnNum);
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    public static Object[][] getExcelData(String excelFile, String sheetName) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setCellData(String excelFile, String sheetName, int rowNum, int colNum, String data) {
        try {
            new ExcelUtils(excelFile).getWorkSheet(sheetName);
            row = excelWSheet.getRow(rowNum);
            cell = row.createCell(colNum);
            cell.setCellValue(data);
            excelWBook.write(new FileOutputStream(excelFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
