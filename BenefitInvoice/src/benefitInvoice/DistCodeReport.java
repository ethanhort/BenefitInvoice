package benefitInvoice;

import java.util.ArrayList;
import java.util.List; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

public class DistCodeReport {
	private List<DistCodeRow> report; 
			
	public DistCodeReport(String filepath, int startIndex, int distCodeIndex, int programIndex, int grantIndex, int loanIndex, int fasIndex, int percentIndex) {
		report = new ArrayList<DistCodeRow>(); 
		
		try {
			Workbook wb = null; 
			FileInputStream fis = new FileInputStream(filepath); 
			wb = new XSSFWorkbook(fis); 
			Sheet sheet = wb.getSheetAt(0); 

			populateReport(sheet, startIndex, distCodeIndex, programIndex, grantIndex, loanIndex, fasIndex, percentIndex); 
			
			wb.close();
			
		} catch (FileNotFoundException e) {
			UIHandler.handleError("Cannot find file containing distributuion codes at specified location");
		} catch (IOException e) {
			UIHandler.handleError("Something bad happened. Please try again.");
		}
	}
	
	public void populateReport(Sheet sheet, int startIndex, int distCodeIndex, int programIndex, int grantIndex, int loanIndex, int fasIndex, int percentIndex) {
		for (int i = startIndex; i < sheet.getLastRowNum(); i++) {
			String distCode, program, grant, loan, fas, percent;
			if (sheet.getRow(i) != null && sheet.getRow(i).getCell(programIndex) != null && !sheet.getRow(i).getCell(programIndex).getStringCellValue().trim().equals("")) {
				distCode = sheet.getRow(i).getCell(distCodeIndex).getStringCellValue(); 
				program = sheet.getRow(i).getCell(programIndex).getStringCellValue(); 
				grant = sheet.getRow(i).getCell(grantIndex).getStringCellValue(); 
				loan = sheet.getRow(i).getCell(loanIndex).getStringCellValue(); 
				fas = sheet.getRow(i).getCell(fasIndex).getStringCellValue(); 
				percent = sheet.getRow(i).getCell(percentIndex).getStringCellValue(); 
				report.add(new DistCodeRow(distCode, program, grant, loan, fas, percent)); 
			}
		}
	}
	
	public int size() {
		return report.size(); 
	}
	
	public DistCodeRow get(int i) {
		return report.get(i); 
	}
	
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < report.size(); i++) {
			str += report.get(i) + "\n";
		}
		return str; 
	}

}
