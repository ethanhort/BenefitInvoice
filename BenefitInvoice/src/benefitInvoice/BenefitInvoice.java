package benefitInvoice;

import java.util.concurrent.TimeUnit;

public class BenefitInvoice {

	private static final int ID_TEXT = 0; 
	private static final int SESS_DATE_TEXT = 1; 
	private static final int SESS_DESCRIPTION_TEXT = 2;
	private static final int DOC_NUM_TEXT = 3;
	private static final int DOC_DATE_TEXT = 4;
	private static final int DOC_DESCRIPTION_TEXT = 5;
	private static final int EFFECTIVE_DATE_TEXT = 6;

	//values to be taken from UI
	private static String distCodeFilePath, GLIFilePath; //filepath of report spreadsheet 
	private static String[] userInputs; //array containing user-inputted data except gls
	
	public void populateDataFromUI(UIHandler ui) {
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

	}

}
