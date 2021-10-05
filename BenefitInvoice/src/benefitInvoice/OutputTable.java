package benefitInvoice;

import java.util.List;
import java.util.ArrayList; 

public class OutputTable {

	private GLIReport gli; 
	private DistCodeReport codes;
	private List<OutputRow> table;
	
	public OutputTable(GLIReport gli, DistCodeReport codes) {
		this.gli = gli; 
		this.codes = codes; 
		table = new ArrayList<OutputRow>(); 
		createTable(); 
	}
	
	public void createTable() {
		for (int i = 0; i < gli.size(); i++) {
			table.addAll(gli.get(i).calculateResults(codes)); 
		}
	}
	
//	public void addRow(String program, String grant, String loan, String fas, String debitId, BigDecimal debitValue, String creditId, BigDecimal creditValue) {
//		table.add(new OutputRow(program, grant, loan, fas, debitId, debitValue, creditId, creditValue)); 
//	}
	
	public OutputRow get(int i) {
		return table.get(i); 
	}
	
	public int size() {
		return table.size(); 
	}
	
	@Override 
	public String toString() {
		String str = ""; 
		for (int i = 0; i < table.size(); i++) {
			str += table.get(i) + "\n"; 
		}
		return str; 
	}
}
