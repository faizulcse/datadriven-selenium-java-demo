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
    private String excelFile;
    private XSSFSheet excelWSheet;
    private XSSFWorkbook excelWBook;
    private XSSFCell cell;
    private XSSFRow row;

    public ExcelUtils(String excelFile) {
        try {
            this.excelFile = excelFile;
            excelWBook = new XSSFWorkbook(new FileInputStream(excelFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExcelUtils getWorkSheet(String sheetName) {
        excelWSheet = excelWBook.getSheet(sheetName);
        return this;
    }

    public ExcelUtils getWorkSheet(int index) {
        excelWSheet = excelWBook.getSheetAt(index);
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

    public Object[][] getExcelData() {
        int r = this.getRowCount();
        int c = this.getCellCount(1);

        String[][] data = new String[r][c];
        for (int i = 1; i <= r; i++) {
            for (int j = 0; j < c; j++) {
                data[i - 1][j] = this.getCellData(i, j);
            }
        }
        return data;
    }

    public void setCellData(int rowNum, int colNum, String data) {
        try {
            row = excelWSheet.getRow(rowNum);
            cell = row.createCell(colNum);
            cell.setCellValue(data);
            excelWBook.write(new FileOutputStream(excelFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
