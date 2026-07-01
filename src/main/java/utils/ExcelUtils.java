package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for Excel operations
 */
public class ExcelUtils {
    
    private Workbook workbook;
    private Sheet sheet;
    private String filePath;
    
    /**
     * Constructor to open Excel file
     */
    public ExcelUtils(String filePath, String sheetName) {
        this.filePath = filePath;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            fis.close();
            LoggerUtils.info("Excel file loaded: " + filePath + ", Sheet: " + sheetName);
        } catch (IOException e) {
            LoggerUtils.error("Failed to load Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to open Excel file: " + filePath);
        }
    }
    
    /**
     * Get row count
     */
    public int getRowCount() {
        return sheet.getLastRowNum() + 1;
    }
    
    /**
     * Get column count
     */
    public int getColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }
    
    /**
     * Get cell data as string
     */
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return "";
        
        Cell cell = row.getCell(colNum);
        if (cell == null) return "";
        
        return getCellValueAsString(cell);
    }
    
    /**
     * Get cell data by column name
     */
    public String getCellData(int rowNum, String columnName) {
        Row headerRow = sheet.getRow(0);
        int colNum = -1;
        
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            if (getCellValueAsString(headerRow.getCell(i)).equals(columnName)) {
                colNum = i;
                break;
            }
        }
        
        if (colNum == -1) {
            throw new RuntimeException("Column not found: " + columnName);
        }
        
        return getCellData(rowNum, colNum);
    }
    
    /**
     * Get all test data as list of maps
     */
    public List<Map<String, String>> getAllData() {
        List<Map<String, String>> data = new ArrayList<>();
        Row headerRow = sheet.getRow(0);
        
        for (int i = 1; i < getRowCount(); i++) {
            Map<String, String> rowData = new LinkedHashMap<>();
            for (int j = 0; j < getColumnCount(); j++) {
                String columnName = getCellValueAsString(headerRow.getCell(j));
                String cellValue = getCellData(i, j);
                rowData.put(columnName, cellValue);
            }
            data.add(rowData);
        }
        
        return data;
    }
    
    /**
     * Convert cell value to string
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    /**
     * Set cell data
     */
    public void setCellData(int rowNum, int colNum, String value) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        
        cell.setCellValue(value);
    }
    
    /**
     * Save and close workbook
     */
    public void close() {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            workbook.close();
            fos.close();
            LoggerUtils.info("Excel file saved and closed");
        } catch (IOException e) {
            LoggerUtils.error("Failed to close Excel file: " + e.getMessage());
        }
    }
}
