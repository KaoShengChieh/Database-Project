import java.util.Arrays;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class CreateAccount extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	JTextField usernameField;
	JPasswordField passwordField;
	JPasswordField passwordField_1;
	JButton bcreateaccount_createaccount;
	JLabel bcreateaccount_x;
	JLabel createaccount_sign;
	JLabel image;
	JTextField textEmail;
	JComboBox<String> comboEmail;
	private JLabel lblNewLabel;
	private JLabel lblexplainMem;
	private JLabel lblexplainVIP;
	JRadioButton rdbtnMember;
	JRadioButton rdbtnVIP ;
	ButtonGroup btnGroup;
	JTextField txtRealName;
	private JLabel lblExplainRealName;
	
	public boolean isCorrect(char[] a, char[] b) {
		return Arrays.equals(a,b);
	}
	
	public CreateAccount() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(150,50,984,603);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 984, 603);
		panel.setBackground(new Color(255, 255, 255));
		add(panel);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		
		bcreateaccount_x = new JLabel("");
		bcreateaccount_x.setHorizontalAlignment(SwingConstants.CENTER);
		bcreateaccount_x.setIcon(new ImageIcon(CreateAccount.class.getResource("/images/logout.jpg")));
		bcreateaccount_x.setFont(new Font("Arial", Font.BOLD, 25));
		bcreateaccount_x.setBounds(935, 0, 49, 32);
		bcreateaccount_x.setForeground(SystemColor.textHighlightText);
		panel.add(bcreateaccount_x);
		
		JLabel lblTitle = new JLabel("Hotel - Create your Account");
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(58, 15, 836, 139);
		panel.add(lblTitle);
		
		JLabel lblUserName = new JLabel("User name");
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setFont(new Font("Bahnschrift", Font.PLAIN, 22));
		lblUserName.setBackground(Color.white);
		lblUserName.setBounds(246, 127, 130, 23);
		panel.add(lblUserName);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		usernameField.setColumns(10);
		usernameField.setBounds(246, 160, 232, 32);
		panel.add(usernameField);
		
		JLabel lblRealName = new JLabel("RealName");
		lblRealName.setForeground(Color.WHITE);
		lblRealName.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblRealName.setBounds(490, 124, 186, 30);
		panel.add(lblRealName);
				
		txtRealName = new JTextField();
		txtRealName.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtRealName.setColumns(10);
		txtRealName.setBounds(490, 160, 232, 32);
		panel.add(txtRealName);
		
		JLabel lblMembership = new JLabel("Membership");
		lblMembership.setForeground(Color.WHITE);
		lblMembership.setFont(new Font("Bahnschrift", Font.PLAIN, 22));
		lblMembership.setBackground(Color.white);
		lblMembership.setBounds(246, 281, 220, 23);
		panel.add(lblMembership);
		
		JLabel lblEmail = new JLabel("Your Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblEmail.setBounds(246, 199, 186, 30);
		panel.add(lblEmail);
		
		textEmail = new JTextField();
		textEmail.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		textEmail.setBounds(245, 239, 232, 32);
		panel.add(textEmail);
		textEmail.setColumns(10);
		
		comboEmail = new JComboBox<>(new String[] {"@ntu.edu.tw", "@gmail.com"});
		comboEmail.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		comboEmail.setBounds(490, 239, 232, 32);
		panel.add(comboEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Bahnschrift", Font.PLAIN, 22));
		lblPassword.setBackground(Color.GRAY);
		lblPassword.setBounds(246, 401, 130, 23);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
		passwordField.setBounds(246, 434, 232, 32);
		panel.add(passwordField);
		
		JLabel lblRepeatYourPassword = new JLabel("Confirm");
		lblRepeatYourPassword.setForeground(Color.WHITE);
		lblRepeatYourPassword.setFont(new Font("Bahnschrift", Font.PLAIN, 22));
		lblRepeatYourPassword.setBackground(Color.GRAY);
		lblRepeatYourPassword.setBounds(489, 401, 103, 23);
		panel.add(lblRepeatYourPassword);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Arial", Font.PLAIN, 20));
		passwordField_1.setBounds(489, 434, 232, 32);
		panel.add(passwordField_1);
		
		createaccount_sign = new JLabel("");
		createaccount_sign.setHorizontalAlignment(SwingConstants.CENTER);
		createaccount_sign.setForeground(Color.RED);
		createaccount_sign.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		createaccount_sign.setBounds(164, 491, 657, 23);
		panel.add(createaccount_sign);
		
		bcreateaccount_createaccount = new JButton("Create account");
		bcreateaccount_createaccount.setForeground(new Color(51,23,125));
		bcreateaccount_createaccount.setBackground(SystemColor.control);
		bcreateaccount_createaccount.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		bcreateaccount_createaccount.setBounds(401, 535, 186, 32);
		panel.add(bcreateaccount_createaccount);
		
		rdbtnMember = new JRadioButton("MEMBER");
		rdbtnMember.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		rdbtnMember.setBounds(247, 310, 231, 32);
		panel.add(rdbtnMember);
		
		rdbtnVIP = new JRadioButton("VIP");
		rdbtnVIP.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		rdbtnVIP.setBounds(490, 310, 232, 32);
		panel.add(rdbtnVIP);
		
		btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnMember);
		btnGroup.add(rdbtnVIP);
		
		lblExplainRealName = new JLabel("use space to seperate names");
		lblExplainRealName.setToolTipText("");
		lblExplainRealName.setForeground(Color.WHITE);
		lblExplainRealName.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
		lblExplainRealName.setBounds(490, 202, 245, 15);
		panel.add(lblExplainRealName);
		
		lblexplainMem = new JLabel("With AD while modifying");
		lblexplainMem.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
		lblexplainMem.setForeground(Color.WHITE);
		lblexplainMem.setBounds(246, 348, 220, 43);
		panel.add(lblexplainMem);
		
		lblexplainVIP = new JLabel("Just 100$, No AD for all!!");
		lblexplainVIP.setToolTipText("");
		lblexplainVIP.setForeground(Color.WHITE);
		lblexplainVIP.setFont(new Font("Bahnschrift", Font.PLAIN, 12));
		lblexplainVIP.setBounds(490, 348, 233, 43);
		panel.add(lblexplainVIP);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(935, 0, 49, 32);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.WHITE);
		panel.add(lblNewLabel);
		
		
		image = new JLabel("Image");
		image.setBackground(Color.WHITE);
		image.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
		image.setBounds(0, -425, 2311, 1330);
		panel.add(image);
		image.setIcon(new ImageIcon(CreateAccount.class.getResource("/images/101white.png")));
		
		JLabel black = new JLabel("");
		black.setBounds(164, 108, 657, 485);
		black.setOpaque(true);
		black.setBackground(new Color(0,0,0,180));
		panel.add(black);
	}
}
