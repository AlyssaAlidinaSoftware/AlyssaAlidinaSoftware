package com.comp3008.gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.comp3008.model.CreatePasswordSession;
import com.comp3008.model.Password;
import com.comp3008.password.utils.PasswordSentenceGenerator;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class CreatePasswordModal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 5059424337983700606L;
	String passwordType;
	JButton acceptPasswordButton;
	GridBagConstraints c;
	Password password;
	JPasswordField passwordTextfield;
	
	private JPanel contentPane;
	private JLabel lblMemorizeThisSentence;
	private JLabel sentenceLabel;
	private JLabel passwordErrorLabel;
	private JButton btnAcceptPassword;
	private JLabel label;
	private JLabel lblMemorizeThisNumber;
	private JLabel lblNewLabel;
	private JLabel lblYourPasswordIs;
	private JLabel passwordLabel;
	private JButton btnGenerateNewPassword;
	private JLabel lblConfirmPassword;
	private JButton testPasswordButton;
	private JFrame frame;
	public CreatePasswordModal(String passwordType) {
		this.passwordType = passwordType;
		initModal();
	}
	private void initModal() {
		PasswordSentenceGenerator pg =  PasswordSentenceGenerator.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 334);
		contentPane = new JPanel();
		frame = this;
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblNewLabel = new JLabel("New Password for: " + passwordType);
		contentPane.add(lblNewLabel, "2, 2, 2, 1, left, default");
		
		lblMemorizeThisSentence = new JLabel("Memorize this sentence:");
		contentPane.add(lblMemorizeThisSentence, "2, 6");
		
		password = pg.generatePassword();
		sentenceLabel = new JLabel(password.getSentence());
		contentPane.add(sentenceLabel, "4, 6");
		
		lblMemorizeThisNumber = new JLabel("Memorize this number:");
		contentPane.add(lblMemorizeThisNumber, "2, 10");
		
		label = new JLabel(Integer.toString(password.getNumber()));
		contentPane.add(label, "4, 10");
		
		lblYourPasswordIs = new JLabel("Your password is:");
		contentPane.add(lblYourPasswordIs, "2, 14");
		
		passwordLabel = new JLabel(password.getPassword());
		//System.out.println(passwordType + ": " + password.getPassword());
		contentPane.add(passwordLabel, "4, 14");
		
		lblConfirmPassword = new JLabel("Confirm Password:");
		contentPane.add(lblConfirmPassword, "2, 18, left, default");
		
		passwordTextfield = new JPasswordField();
		passwordTextfield.setText("");
		passwordTextfield.getDocument().addDocumentListener(new AcceptPasswordChangeListener());
		contentPane.add(passwordTextfield, "4, 18, fill, default");
		passwordTextfield.setColumns(10);
		
		
		passwordErrorLabel = new JLabel("please enter in your password");
		contentPane.add(passwordErrorLabel, "2, 20");
		
		testPasswordButton = new JButton("Test Password");
		testPasswordButton.addActionListener(new TestPasswordActionListener());
		contentPane.add(testPasswordButton, "2, 22");
		
		btnGenerateNewPassword = new JButton("Generate New Password");
		btnGenerateNewPassword.addActionListener(new GererateNewPasswordActionListener());
		contentPane.add(btnGenerateNewPassword, "2, 24");
		
		btnAcceptPassword = new JButton("Accept Password");
		btnAcceptPassword.addActionListener(new AcceptPasswordActionListener());
		contentPane.add(btnAcceptPassword, "4, 24");
		btnAcceptPassword.setEnabled(false);
	
		this.setVisible(true);
		
	}
	private void acceptPasswordChange() {

		btnAcceptPassword.setEnabled(false);
		if (String.valueOf(passwordTextfield.getPassword()).equals("")) {
			passwordErrorLabel.setText("please enter in your password");
		}
		if (!String.valueOf(passwordTextfield.getPassword()).equals(password.getPassword())) {
			passwordErrorLabel.setText("password does not match!");		
		}
		else {
			passwordErrorLabel.setText("passwords match!");
			btnAcceptPassword.setEnabled(true);
		}
	}
	
	public class AcceptPasswordChangeListener implements DocumentListener {
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			acceptPasswordChange();
		}
		@Override
		public void removeUpdate(DocumentEvent e) {	
			acceptPasswordChange();
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			acceptPasswordChange();
		}
		
	}
	
	public class AcceptPasswordActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CreatePasswordSession cps = CreatePasswordSession.getInstance();			
			if (passwordType.equals(Password.EMAIL)) {
				cps.setEmailPassword(password);
			}
			else if (passwordType.equals(Password.BANK)) {
				cps.setBankPassword(password);
			}
			else if (passwordType.equals(Password.SHOPPING)){
				cps.setShoppingPassword(password);
			}
			dispose();
		}
	}
	private class TestPasswordActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (String.valueOf(passwordTextfield.getPassword()).equals(password.getPassword())) {
				JOptionPane.showMessageDialog(frame, "Correct Password!");
			}
			else if (!String.valueOf(passwordTextfield.getPassword()).equals(password.getPassword())) {
				JOptionPane.showMessageDialog(frame, "Wrong password entered","", JOptionPane.ERROR_MESSAGE);
			}
			
		}		
	}
	public class GererateNewPasswordActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			initModal();
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
