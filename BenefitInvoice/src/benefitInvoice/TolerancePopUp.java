package benefitInvoice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TolerancePopUp {
	
	public TolerancePopUp(String msg) {
		JFrame errorFrame = new JFrame("Rounding Exceeded Tolerance"); 
		errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(); 
		errorFrame.add(panel);

		//initialize the error messages that will be displayed
		JLabel errorLabel = new JLabel("Error: " + msg); 
		errorLabel.setForeground(Color.RED);

		//close program button functionality
		JButton close = new JButton("Close"); 
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorFrame.dispose();
			}
		}); 

		//add subcomponents to top level panel; 
		panel.add(errorLabel); 
		panel.add(close); 

		//set layout for popup and draw it to screen
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		errorFrame.setPreferredSize(new Dimension(600, 400));
		errorFrame.pack();
		errorFrame.setLocationRelativeTo(null); 
		errorFrame.setVisible(true);

		
	}
}
