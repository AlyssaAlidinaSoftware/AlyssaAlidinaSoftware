import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MainMenu extends Frame implements ActionListener, Observer{
	
	 private static final long serialVersionUID = 1L;
	 private Label label;    
	 private JButton createEmailPasswordButton;
	 private JButton createBankPasswordButton;
	 private JButton createShoppingPasswordButton;
	 private JButton testButton;
	 
	 final static boolean shouldFill = true;
	 final static boolean shouldWeightX = true;
	 final static boolean RIGHT_TO_LEFT = false;
	 boolean pressed = false;
	 JFrame buttonFrame;
	 
	 
	 
	 public MainMenu () {
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		CreatePasswordSession.getInstance().addObserver(this);
		setSize(500, 200);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		  
		c.fill = GridBagConstraints.HORIZONTAL;
		label = new Label("Email"); 
		c.weightx = 0.5;
		  
		  
		label.setAlignment(FlowLayout.LEFT);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(label, c);    
  
 
		createEmailPasswordButton = new JButton("Create Password");
		createEmailPasswordButton.setVerticalAlignment(FlowLayout.LEFT);
		label.setAlignment(FlowLayout.LEFT);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.ipadx = 1;
		c.gridy = 0;
		createEmailPasswordButton.addActionListener(new CreateEmailPasswordListener());

		add(createEmailPasswordButton, c);
  
      
      	label = new Label("Bank"); 
		c.weightx = 0.5;	  
		label.setAlignment(FlowLayout.LEFT);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		add(label, c);  
  

		createBankPasswordButton = new JButton("Create Password");
		createBankPasswordButton.setEnabled(false);
		createBankPasswordButton.setVerticalAlignment(FlowLayout.LEFT);
		label.setAlignment(FlowLayout.LEFT);
		  
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.ipadx = 1;
		c.gridy = 1;
		  
  
		createBankPasswordButton.addActionListener(new CreateBankPasswordListener());
  
		add(createBankPasswordButton, c);
		  
		c.fill = GridBagConstraints.HORIZONTAL;
		label = new Label("Shopping"); 
		c.weightx = 0.5;
		  
		  
		label.setAlignment(FlowLayout.LEFT);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		add(label, c);    
		  
 
		createShoppingPasswordButton = new JButton("Create Password");
		createShoppingPasswordButton.setEnabled(false);
		createShoppingPasswordButton.setVerticalAlignment(FlowLayout.LEFT);
		label.setAlignment(FlowLayout.LEFT);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.ipadx = 1;
		c.gridy = 2;
  
		createShoppingPasswordButton.addActionListener(new CreateShoppingPasswordListener());
		add(createShoppingPasswordButton, c);
  
		c =new GridBagConstraints();
		testButton = new JButton("Try them out!");
		testButton.addActionListener(new TestPasswordsListener());
		testButton.setEnabled(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       
		c.weighty = 1.0;   
		c.anchor = GridBagConstraints.PAGE_END; 
		c.insets = new Insets(3,0,0,0); 
		c.gridx = 1;       
		c.gridy = 3;
		add(testButton, c);
  
		setVisible(true);	  
		addWindowListener(new WindowListener());
	 }
	 public class CreateEmailPasswordListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new CreatePasswordModal(Password.EMAIL);
			}
	 }
	 public class CreateBankPasswordListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CreatePasswordModal(Password.BANK);
			}
	 }
	 public class CreateShoppingPasswordListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new CreatePasswordModal(Password.SHOPPING);
		}
	 }
	 public class TestPasswordsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new TestPasswordScreen(CreatePasswordSession.getInstance().getNextTest(0));
			dispose();
		}
		 
	 }
	 public class WindowListener extends WindowAdapter {
		 public void windowClosing(WindowEvent we) {
			 dispose();
		 }
	 } 
	 
	 @Override
	 public void actionPerformed(ActionEvent evt) {
		   
	 }

	@Override
	public void update(Observable o, Object arg) {
		CreatePasswordSession cps = CreatePasswordSession.getInstance();
		if (cps.emailPasswordSet()&& !cps.bankPasswordSet() && !cps.shoppingPasswordSet()) {
			createEmailPasswordButton.setEnabled(false);
			createShoppingPasswordButton.setEnabled(false);
			createBankPasswordButton.setEnabled(true);
			testButton.setEnabled(false);
		}
		if (cps.emailPasswordSet() && cps.bankPasswordSet() && !cps.shoppingPasswordSet()) {
			createBankPasswordButton.setEnabled(false);
			createShoppingPasswordButton.setEnabled(true);
			testButton.setEnabled(false);
		}
		else if (cps.emailPasswordSet() && cps.bankPasswordSet() && cps.shoppingPasswordSet()) {
			createEmailPasswordButton.setEnabled(false);
			createShoppingPasswordButton.setEnabled(false);
			createBankPasswordButton.setEnabled(false);
			testButton.setEnabled(true);
		}
		
	}
}
