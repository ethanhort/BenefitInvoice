package benefitInvoice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List; 

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GLIReport {
	private List<GLIRow> report; 

	public GLIReport(String filepath, int startIndex, int distCodeIndex, int eeIdIndex, int eeValueIndex, int erIdIndex, int erValueIndex) {
		report = new ArrayList<GLIRow>(); 

		try {
			Workbook wb = null; 
			FileInputStream fis = new FileInputStream(filepath); 
			wb = new XSSFWorkbook(fis); 
			Sheet sheet = wb.getSheetAt(0); 
			populateReport(sheet, startIndex, distCodeIndex, eeIdIndex, eeValueIndex, erIdIndex, erValueIndex); 

			wb.close();

		} catch (FileNotFoundException e) {
			UIHandler.handleError("Cannot find file containing distributuion codes at specified location");
		} catch (IOException e) {
			UIHandler.handleError("Something bad happened. Please try again.");
		}
	}

	public void populateReport(Sheet sheet, int startIndex, int distCodeIndex, int eeIdIndex, int eeValueIndex, int erIdIndex, int erValueIndex) {
		int i = startIndex; 
		String distCode, eeId, erId;
		BigDecimal eeValue, erValue; 
		while (sheet.getRow(i) != null && sheet.getRow(i).getCell(distCodeIndex) != null && !sheet.getRow(i).getCell(distCodeIndex).getStringCellValue().trim().equals("")) {
			distCode = sheet.getRow(i).getCell(distCodeIndex).getStringCellValue().replace(".", ""); 

			if (sheet.getRow(i).getCell(eeIdIndex) != null && !sheet.getRow(i).getCell(eeIdIndex).getStringCellValue().trim().equals("")) {
				eeId = sheet.getRow(i).getCell(eeIdIndex).getStringCellValue(); 
				eeValue = BigDecimal.valueOf(sheet.getRow(i).getCell(eeValueIndex).getNumericCellValue()); 				
			} else {
				eeId = ""; 
				eeValue = BigDecimal.valueOf(-1); 
			}

			if (sheet.getRow(i).getCell(erIdIndex) != null && !sheet.getRow(i).getCell(erIdIndex).getStringCellValue().trim().equals("")) {
				erId = sheet.getRow(i).getCell(erIdIndex).getStringCellValue(); 
				erValue = BigDecimal.valueOf(sheet.getRow(i).getCell(erValueIndex).getNumericCellValue()); 	
			} else {
				erId = "";
				erValue = BigDecimal.valueOf(-1); 
			}
			report.add(new GLIRow(distCode, new DebitPair(eeId, eeValue), new DebitPair(erId, erValue))); 

			i++; 
		}
	}

	/**
	 * returns a row of the gli table
	 * @param i index of row to be returned
	 * @return
	 */
	public GLIRow get(int i) {
		return report.get(i); 
	}

	/**
	 * specifies the number of rows in the gli table
	 * @return the number of rows in the table
	 */
	public int size() {
		return report.size(); 
	}

	@Override
	/**
	 * converts table to string representation
	 */
	public String toString() {
		String str = ""; 
		for (int i = 0; i < report.size(); i++) {
			str += report.get(i) + "\n"; 
		}
		return str; 
	}

}
