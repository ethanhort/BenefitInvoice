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
		coalesce(); 
	}
	
	public void createTable() {
		for (int i = 0; i < gli.size(); i++) {
			table.addAll(gli.get(i).calculateResults(codes)); 
		}
	}
	
	public void coalesce() {
		List<OutputRow> temp = new ArrayList<OutputRow>();
		boolean found; 
		
		//iterate through entire report
		for (int i = 0; i < table.size(); i++) {
			//find rows of 1005
			if (table.get(i).getId() == "1005") {
				found = false; 
				
				//check temp list to see if that row already exists there
				for (int j = 0; j < temp.size(); j++) {
					if (table.get(i).equals(temp.get(j))) {
						temp.get(j).addCredit(table.get(i).getCreditValue());
						found = true; 
					}
				}
				
				//if row not in temp, add it to temp
				if (!found) {
					temp.add(table.get(i)); 
				}
				table.remove(i); 
				i--; 
			}
		}
		table.addAll(temp); 
	}
	
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
