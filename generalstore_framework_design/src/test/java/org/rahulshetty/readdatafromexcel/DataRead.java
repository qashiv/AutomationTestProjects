package org.rahulshetty.readdatafromexcel;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataRead {

	public static void main(String[] args) throws IOException {

		FileInputStream fis = new FileInputStream("src\\test\\resources\\DataWorksheet.xlsx");
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheetObj = workbook.getSheet("Sheet1");
		int countRow = sheetObj.getLastRowNum();
		/** Read only string data */
//		for(int i=0; i<=countRow; i++) {
//			Row rowObj = sheetObj.getRow(i);
//			int countCell = rowObj.getLastCellNum();
//			for(int j=0; j<=countCell-1; j++) {
//				Cell cellObj = rowObj.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
//				String cellData = cellObj.getStringCellValue();
//				System.out.println(cellData+" - ");
//			}
//		}
		/** Read both string and numeric value */
		for (int i = 0; i <= countRow; i++) {
			Row rowObj = sheetObj.getRow(i);
			int countCell = rowObj.getLastCellNum();
			for (int j = 0; j <= countCell - 1; j++) {
				Cell cellObj = rowObj.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String cellData = null;
				if (cellObj.getCellType() == CellType.STRING) {
					cellData = cellObj.getStringCellValue();
				} else {
					Double doubleData = cellObj.getNumericCellValue();
					Integer intValue = doubleData.intValue();
					cellData = intValue.toString();
				}

				System.out.print(cellData + " - ");
			}
			System.out.println();
		}

	}
}
