package framework;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader extends BasePage {

	public String[][] getExcelData(String fileName, String sheetName) throws IOException {
		String[][] data = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(fis); // WHY SHOWING AS NOT USED WHILE USED BELOW?
			XSSFSheet sheet = workbook.getSheet(sheetName);
			DataFormatter formatter = new DataFormatter();
			XSSFRow row = sheet.getRow(0);
			int noOfRows = sheet.getPhysicalNumberOfRows();
			int noOfCols = row.getLastCellNum();
			data = new String[noOfRows - 1][noOfCols];
			for (int rowIndex = 1; rowIndex < noOfRows; rowIndex++) {
				for (int colIndex = 0; colIndex < noOfCols; colIndex++) {
					// WHY 'rowIndex-1'... IS NOT THE HEADING?
					data[rowIndex - 1][colIndex] = formatter.formatCellValue(sheet.getRow(rowIndex).getCell(colIndex));
					// System.out.println(data[rowIndex - 1][colIndex]);
				}
			}
		} catch (Exception e) {
			System.out.println("The exception is: " + e.getMessage());
		}
		return data;
	}
}