package benefitInvoice;

public class GLIRow {
	String distCode;
	DebitPair ee, er;  
	
	public GLIRow(String distCode, DebitPair ee, DebitPair er) {
		this.distCode = distCode; 
		this.ee = ee;
		this.er = er; 
	}
	
	public String getDistCode() {
		return distCode; 
	}
	
	public DebitPair getEePair() {
		return ee; 
	}
	
	public DebitPair getErPair() {
		return er; 
	}
	
	@Override
	public String toString() {
		return "DistCode: " + distCode + ", EE: (" + ee.getId() + ", " + ee.getValue() + "), ER: (" + er.getId() + ", " + er.getValue() + ")"; 
	}
}
