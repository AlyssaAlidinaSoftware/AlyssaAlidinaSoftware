package com.comp3008.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.comp3008.gui.TestPasswordScreen.EnterPasswordListener;
import com.comp3008.model.CreatePasswordSession;
import com.comp3008.model.Password;
import com.comp3008.password.utils.PasswordSentenceGenerator;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

public class CreateUsernameScreen extends JFrame {
	
	JButton acceptPasswordButton;
	GridBagConstraints c;
	Password password;
	JTextField usernameTextfield;
	JButton generateUsernameButton;
	
	private final String USERNAME_TOO_SHORT = "username must be at least 5 characters";
	private JLabel errorLabel;
	private JButton startButton;
	
	public CreateUsernameScreen() {
				initEnterUsernameScreen();
	}
	public void initEnterUsernameScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 264);
		getContentPane().setLayout(new MigLayout("", "[367px,grow][]", "[16px][][][][][][][]"));
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the Eazy Breezy Degreezy Password Generator!");
		lblWelcomeToThe.setForeground(Color.RED);
		getContentPane().add(lblWelcomeToThe, "cell 0 0,alignx center,aligny center");
		
		JLabel lblPleaseEnterA = new JLabel("Please enter a username or generate a newone!");
		getContentPane().add(lblPleaseEnterA, "cell 0 2");
		
		usernameTextfield = new JTextField("");
		getContentPane().add(usernameTextfield, "cell 0 3,growx");
		usernameTextfield.setColumns(10);
		usernameTextfield.getDocument().addDocumentListener(new UsernameDocumentListener());
		generateUsernameButton = new JButton("Generate username");
		generateUsernameButton.addActionListener(new GenerateUsernameListener());
		getContentPane().add(generateUsernameButton, "flowx,cell 0 4");
		
		startButton = new JButton("Start!");
		getContentPane().add(startButton, "cell 0 7,alignx center");
		startButton.addActionListener(new StartActionListener());
		startButton.setEnabled(false);
		errorLabel = new JLabel(USERNAME_TOO_SHORT);
		getContentPane().add(errorLabel, "cell 0 4");
		

		
	
		this.setVisible(true);
	}
	private void usernameTooShort() {
		if (usernameTextfield.getText().length() < 5) {
			errorLabel.setText(USERNAME_TOO_SHORT);
			startButton.setEnabled(false);
			return;
		}
		errorLabel.setText("");
		startButton.setEnabled(true);
	}
	public class GenerateUsernameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			usernameTextfield.setText(PasswordSentenceGenerator.getInstance().generatePassword().getPassword());
		}
		
	}
	public class UsernameDocumentListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			usernameTooShort();
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			usernameTooShort();
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			usernameTooShort();
		}
	}
	public class StartActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CreatePasswordSession cps = CreatePasswordSession.getInstance();
			cps.setUsername(usernameTextfield.getText());
			dispose();
			new MainMenu();
		}
	}


	public static void main(String[] args) {
		new CreateUsernameScreen();
	}
}
