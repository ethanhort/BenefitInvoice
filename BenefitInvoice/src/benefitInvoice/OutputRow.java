package benefitInvoice;

import java.math.BigDecimal;

public class OutputRow {
	private String program, grant, loan, id, fas; 
	BigDecimal debitValue, creditValue; 
	
	public OutputRow(String program, String grant, String loan, String fas, String id, BigDecimal debitValue, BigDecimal creditValue) {
		this.program = program; 
		this.grant = grant;
		this.loan = loan; 
		this.fas = fas; 
		this.id = id; 
		this.debitValue = debitValue; 
		this.creditValue = creditValue; 
	}
	
	public void add(BigDecimal num) {
		debitValue = debitValue.add(num); 
	}
	
	public void addCredit(BigDecimal num) {
		creditValue = creditValue.add(num); 
	}
	
	public String getProgram() {
		return program;
	}
	
	public String getGrant() {
		return grant; 
	}
	
	public String getLoan() {
		return loan; 
	}
	
	public String getFas() {
		return fas; 
	}
	
	public String getId() {
		return id;
	}
	
	public BigDecimal getDebitValue() {
		return debitValue;
	}
	
	public BigDecimal getCreditValue() {
		return creditValue; 
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof OutputRow) {
			OutputRow row = (OutputRow) o; 
			return row.getProgram().equals(program) && row.getGrant().equals(grant) && row.getFas().equals(fas); 			
		}
		return false; 
	}
	
	@Override
	public String toString() {
		return "Program: "+ program + ", Grant: " + grant + ", Loan: " + loan + ", fas: " + fas + ", ID: " + id 
				+ ", Values: (" + debitValue + ", " + creditValue + ")";
	}
}
