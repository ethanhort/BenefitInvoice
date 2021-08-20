package benefitInvoice;

import java.math.BigDecimal;

public class DebitPair {
	String id; 
	BigDecimal value; 
	
	public DebitPair(String id, BigDecimal value) {
		this.id = id; 
		this.value = value; 
	}
	
	public String getId() {
		return id; 
	}
	
	public BigDecimal getValue() {
		return value; 
	}
}
