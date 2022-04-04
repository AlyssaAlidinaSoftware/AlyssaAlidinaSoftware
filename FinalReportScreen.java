import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class FinalReportScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	String passwordType;
	JButton acceptPasswordButton;
	GridBagConstraints c;
	Password password;
	
	private JLabel bankLabel;
	private JLabel bankStatusLabel;
	private JLabel passwordLabel;
	private JLabel emailLabel;
	private JLabel shoppingLabel;
	private JLabel emailStatusLabel;
	private JLabel shoppingStatusLabel;
	private JLabel totalLabel;
	private JButton acceptButton;
	private JLabel lblStatus;
	
	public FinalReportScreen() {
		CreatePasswordSession cps = CreatePasswordSession.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 200);
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
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		contentPane.add(passwordLabel, "2, 2, 2, 1, left, default");
		
		
		lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblStatus, "4, 2");
		
		bankLabel = new JLabel("Bank");
		contentPane.add(bankLabel, "2, 4");
		bankStatusLabel = new JLabel(constructPasswordStatus(Password.BANK));
		bankStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(bankStatusLabel, "4, 4");
		
		emailLabel = new JLabel("Email");
		contentPane.add(emailLabel, "2, 6");
		
		emailStatusLabel = new JLabel(constructPasswordStatus(Password.EMAIL));
		emailStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(emailStatusLabel, "4, 6");
		
		shoppingLabel = new JLabel("Shopping");
		contentPane.add(shoppingLabel, "2, 8, default, top");
		
		shoppingStatusLabel = new JLabel(constructPasswordStatus(Password.SHOPPING));
		shoppingStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(shoppingStatusLabel, "4, 8");
		
		
		totalLabel = new JLabel("User got " + cps.getNumberCorrect() + " out of 3 passwords correct");
		totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(totalLabel, "4, 10");
		
		acceptButton = new JButton("Accept");
		acceptButton.addActionListener(new AcceptListener());
		contentPane.add(acceptButton, "2, 14, 3, 1");
	
		this.setVisible(true);
	}
	private String constructPasswordStatus(String passwordType) {
		CreatePasswordSession cps = CreatePasswordSession.getInstance();
		int attempts = cps.getAttemptsByType(passwordType);
		boolean status = cps.passwordSuccesful(passwordType);
	
		if (!status) {
			return "User failed to remember password";
		}
		return "User entered the correct password after " + attempts + " attempts";
	}
    public class AcceptListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			PASFileWriter.savePasswordSessionCSV(CreatePasswordSession.getInstance());
			CreatePasswordSession.getInstance().newSession();
			dispose();
			new CreateUsernameScreen();
		}
    }
}
