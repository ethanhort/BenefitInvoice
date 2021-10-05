package benefitInvoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList; 

public class GLIRow {
	String distCode;
	DebitPair ee, er;  

	public GLIRow(String distCode, DebitPair ee, DebitPair er) {
		this.distCode = distCode; 
		this.ee = ee;
		this.er = er; 
	}

	public ArrayList<OutputRow> calculateResults(DistCodeReport codes) {
		ArrayList<OutputRow> eeTable = new ArrayList<OutputRow>(); 
		ArrayList<OutputRow> erTable = new ArrayList<OutputRow>(); 

		//multiply values by percentages and add output rows for each one
		if (ee.getValue().compareTo(BigDecimal.ZERO) > 0) {
			for (int i = 0; i < codes.size(); i++) {
				DistCodeRow row = codes.get(i); 
				if (row.getDistCode().toUpperCase().equals(distCode)) {
					eeTable.add(new OutputRow(row.getProgram(), row.getGrant(), row.getLoanNum(), row.getFas(), ee.getId(), ee.getValue().multiply(row.getPercent()).setScale(2, RoundingMode.HALF_UP), BigDecimal.ZERO.setScale(2))); 
				}
			}
		}

		if (er.getValue().compareTo(BigDecimal.ZERO) > 0) {
			for (int i = 0; i < codes.size(); i++) {
				DistCodeRow row = codes.get(i);
				if (row.getDistCode().toUpperCase().equals(distCode)) {
					erTable.add(new OutputRow(row.getProgram(), row.getGrant(), row.getLoanNum(), row.getFas(), er.getId(), er.getValue().multiply(row.getPercent()).setScale(2, RoundingMode.HALF_UP), BigDecimal.ZERO.setScale(2)));
				}
			}
		}


		//add percentage-multiplied values to see if rounding errors need to be accounted for
		BigDecimal total; 
		if (eeTable.size() > 0) {
			total = eeTable.get(0).getDebitValue();
			for (int i = 1; i < eeTable.size(); i++) {
				total = total.add(eeTable.get(i).getDebitValue()); 
			}

			//check if total is equal to original dollar amount
			if (total.compareTo(ee.getValue()) != 0) {

				//find largest value and add difference to that value
				int index = findMax(eeTable); 
				BigDecimal difference = ee.getValue().subtract(total);
				if (difference.compareTo(new BigDecimal(0.10)) > 0) {
					new TolerancePopUp("Rounding tolerance was exceeded on line \n" + eeTable.get(index)); 
				}
				eeTable.get(index).add(ee.getValue().subtract(total));
			}
		}

		//add percentage-multiplied values for other table
		if (erTable.size() > 0) {
			total = erTable.get(0).getDebitValue(); 
			for (int i = 1; i < erTable.size(); i++) {
				total = total.add(erTable.get(i).getDebitValue()); 
			}

			//check total for other table
			if (total.compareTo(er.getValue()) != 0) {

				//find largest value in other table and add difference
				int index = findMax(erTable);
				BigDecimal difference = er.getValue().subtract(total);
				if (difference.compareTo(new BigDecimal(0.10)) > 0) {
					new TolerancePopUp("Rounding tolerance was exceeded. Added " + difference + " on line \n " + erTable.get(index)); 
				}
				erTable.get(index).add(er.getValue().subtract(total));
			}
		}

		//add credit rows in
		ArrayList <OutputRow> credits = new ArrayList<OutputRow>(); 
		if (eeTable.size() > 0 && erTable.size() > 0) {
			for (int i = 0; i < eeTable.size(); i++) {
				OutputRow eeRow = eeTable.get(i); 
				OutputRow erRow = erTable.get(i); 
				credits.add(new OutputRow(eeRow.getProgram(), eeRow.getGrant(), eeRow.getLoan(), eeRow.getFas(), "1005", BigDecimal.ZERO.setScale(2),
						eeRow.getDebitValue().add(erRow.getDebitValue()))); 
			}
		} else if (eeTable.size() > 0) {
			for (int i = 0; i < eeTable.size(); i++) {
				OutputRow row = eeTable.get(i);
				credits.add(new OutputRow(row.getProgram(), row.getGrant(), row.getLoan(), row.getFas(), "1005", BigDecimal.ZERO.setScale(2), row.getDebitValue())); 
			}
		} else {
			for (int i = 0; i < erTable.size(); i++) {
				OutputRow row = erTable.get(i);
				credits.add(new OutputRow(row.getProgram(), row.getGrant(), row.getLoan(), row.getFas(), "1005", BigDecimal.ZERO.setScale(2), row.getDebitValue())); 
			}
		}

		//merge lists
		eeTable.addAll(erTable);
		eeTable.addAll(credits); 

		return eeTable; 
	}

	public int findMax(ArrayList<OutputRow> list) {
		int index = 0; 
		BigDecimal max = list.get(index).getDebitValue(); 
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getDebitValue().compareTo(max) > 0) {
				max = list.get(i).getDebitValue();
				index = i; 
			}
		}

		return index; 
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
