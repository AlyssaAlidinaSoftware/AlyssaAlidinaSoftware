import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class TestPasswordScreen extends JFrame {


	private JPanel contentPane;
	String passwordType;
	JButton acceptPasswordButton;
	GridBagConstraints c;
	Password password;
	JPasswordField passwordTextfield;
	
	private JLabel attemptsLabel;
	private JLabel attemptsTakenLabel;
	private JLabel lblNewLabel;
	private JButton btnEnterPassword;
	private JLabel lblEnterPassword;
	private JLabel statusLabel;
	private  long startTimeAttempt;
	private long endTimeAttempt ;
	private long totalTimeAttempt;
	
	private long startTimeTotal;
	private long endTimeTotal;
	private long totalTimeTotal;
	
	private int testNum;
	
	public TestPasswordScreen(String passwordType) {
		testNum = 0;		
		initTestScreen(passwordType);
	}
	public void initTestScreen(String passwordType) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 150);
		contentPane = new JPanel();
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

		this.passwordType = passwordType;
		startTimeAttempt = System.nanoTime();
		startTimeTotal = startTimeAttempt;
		CreatePasswordSession cps = CreatePasswordSession.getInstance();
		lblNewLabel = new JLabel("Password for " + passwordType);
		contentPane.add(lblNewLabel, "2, 2, 2, 1, left, default");
		
		
		attemptsLabel = new JLabel("Attempts");
		contentPane.add(attemptsLabel, "2, 4");
		attemptsTakenLabel = new JLabel(""+cps.getAttemptsByType(passwordType) + "/3");
		contentPane.add(attemptsTakenLabel, "4, 4");
		
		lblEnterPassword = new JLabel("Enter password:");
		contentPane.add(lblEnterPassword, "2, 6");
		
		passwordTextfield = new JPasswordField();
		passwordTextfield.setText("");
		contentPane.add(passwordTextfield, "3, 6, 2, 1, fill, default");
		passwordTextfield.setColumns(10);
		
		statusLabel = new JLabel("Enter your password for: " + passwordType);
		contentPane.add(statusLabel, "2, 8, default, top");
		
		btnEnterPassword = new JButton("Enter Password");
		btnEnterPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		btnEnterPassword.addActionListener(new EnterPasswordListener());
		contentPane.add(btnEnterPassword, "4, 8, right, default");
		endTimeAttempt   = System.nanoTime();
		totalTimeAttempt = endTimeAttempt - startTimeAttempt;
	
		this.setVisible(true);
	}
	//Load the next test screen or the final report screen if all passwords have been tested
	private void loadNextTestScreen() {
		CreatePasswordSession cps = CreatePasswordSession.getInstance();
		endTimeTotal = System.nanoTime();
		totalTimeTotal = endTimeTotal- startTimeTotal;
		cps.setTotalTime(passwordType, totalTimeTotal);
		if (testNum == 0 || testNum == 1) {
			testNum++;
			initTestScreen(cps.getNextTest(testNum));
			return;

		} 
		if (testNum == 2) {
			dispose();
			new FinalReportScreen();
		}
	}
	//Checks if the user entered the password correctly. If the password is correct it wil
	//load the next test screen. 
    public class EnterPasswordListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CreatePasswordSession cps = CreatePasswordSession.getInstance();
			Password password = cps.getPasswordByType(passwordType);
			int attempts = cps.getAttemptsByType(passwordType);
			boolean isCorrect = password.getPassword().equals(String.valueOf(passwordTextfield.getPassword()));
			endTimeAttempt   = System.nanoTime();
			totalTimeAttempt = endTimeAttempt - startTimeAttempt;
			cps.addAttemptTime(passwordType, attempts, totalTimeAttempt);
			if (attempts == 3 && !isCorrect) {
				loadNextTestScreen();
			}
			if (isCorrect) {
				CreatePasswordSession.getInstance().setPasswordSuccess(passwordType);
				loadNextTestScreen();
			}
			if (!isCorrect && attempts < CreatePasswordSession.MAX_ATTEMPTS) {
				cps.incrementAttemptsByType(passwordType);
				attemptsTakenLabel.setText(""+cps.getAttemptsByType(passwordType) + "/3");
				statusLabel.setText("Wrong password! Try Again");
				passwordTextfield.setText("");
			}
		}
	 }
}
