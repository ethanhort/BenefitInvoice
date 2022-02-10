package benefitInvoice;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BenefitInvoice {

	//UI indices
	private static final int ID_TEXT = 0; 
	private static final int SESS_DATE_TEXT = 1; 
	private static final int SESS_DESCRIPTION_TEXT = 2;
	private static final int DOC_NUM_TEXT = 3;
	private static final int DOC_DATE_TEXT = 4;
	private static final int DOC_DESCRIPTION_TEXT = 5;
	private static final int EFFECTIVE_DATE_TEXT = 6;
	
	//dist code spreadsheet indices
	private static final int DIST_START_INDEX = 2; 
	private static final int CODE_INDEX = 0;
	private static final int PROGRAM_INDEX = 2; 
	private static final int GRANT_INDEX = 3; 
	private static final int LOAN_INDEX = 4;
	private static final int FAS_INDEX = 5;
	private static final int PERCENT_INDEX = 6; 
	
	//GLI spreadsheet indices
	private static final int GLI_START_INDEX = 1;
	private static final int DIST_CODE_INDEX = 2;
	private static final int EE_ID_INDEX = 5;
	private static final int EE_VALUE_INDEX = 6;
	private static final int ER_ID_INDEX = 7;
	private static final int ER_VALUE_INDEX = 8; 

	//values to be taken from UI
	private static String distCodeFilePath, GLIFilePath; //filepath of report spreadsheet 
	private static String[] userInputs; //array containing user-inputted data except gls
	
	public static void populateDataFromUI(UIHandler ui) {
		GLIFilePath =  ui.getGLIFilePath();
		distCodeFilePath = ui.getDistCodeFilePath(); 
		userInputs = ui.getUserInputs(); 
	}

	public static void main(String[] args) {
		UIHandler ui = new UIHandler(); 

		//wait for user to finish inputting data into UI. could probably be done with listeners but... this works 
		while (!ui.isFinished()) {
			try {
				TimeUnit.SECONDS.sleep(1);  				
			}
			catch (Exception e) {

			}
		}
		
		populateDataFromUI(ui);
		
		DistCodeReport distCodes = new DistCodeReport(distCodeFilePath, DIST_START_INDEX, CODE_INDEX, PROGRAM_INDEX, GRANT_INDEX, LOAN_INDEX, FAS_INDEX, PERCENT_INDEX);
		GLIReport gli = new GLIReport(GLIFilePath, GLI_START_INDEX, DIST_CODE_INDEX, EE_ID_INDEX, EE_VALUE_INDEX, ER_ID_INDEX, ER_VALUE_INDEX);
		OutputTable output = new OutputTable(gli, distCodes); 
		
		try {
			FileWriter writer = new FileWriter("benefit_payment.csv");
			for (int i = 0; i < output.size(); i++) {
				writer.write(userInputs[ID_TEXT] + ", " + userInputs[SESS_DATE_TEXT] + ", " + userInputs[SESS_DESCRIPTION_TEXT] + ", " + userInputs[DOC_NUM_TEXT] 
						+ ", " + userInputs[DOC_DATE_TEXT] + ", " + userInputs[DOC_DESCRIPTION_TEXT] + ", " + userInputs[EFFECTIVE_DATE_TEXT] 
						+ ", BP, JV, no, N, " + output.get(i).getProgram() + ", " + output.get(i).getGrant() + ", " + output.get(i).getId() + ", " 
						+ output.get(i).getLoan() + ", " + output.get(i).getFas() + ", " + output.get(i).getDebitValue() + ", " + output.get(i).getCreditValue()
						+ "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			UIHandler.handleError("Problem writing to file. Please try again.");
		}
//		
		//System.out.println(output);
		//System.out.println(gli);
		//System.out.println(distCodes);
	}

}
