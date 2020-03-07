import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PayReservation extends JPanel {
	private static final long serialVersionUID = 1L;

	enum payType {FOR_VIP, FOR_HOTEL};
	payType type;
	
	JLabel bCreditCard_x;
	JLabel image;
	JTextField txtRealName;
	JTextField txtYear;
	JTextField txtMonth;
	JTextField txtSecurityNum;
	JButton btnOK;
	JLabel bank;
	JLabel lblLastPage;
	JLabel lblMenu;
	JTextField creditCardNum1;
	JTextField creditCardNum2;
	JTextField creditCardNum3;
	JTextField creditCardNum4;
	JLabel lblHotelAddress;
	JLabel lblTitle;
	private JLabel dashline1;
	private JLabel dashline2;
	private JLabel dashline3;
	private JLabel lblCitibankHsbc;

	public PayReservation() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(150,50,984,603);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 984, 603);
		panel.setBackground(new Color(255, 255, 255));
		add(panel);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		
		bCreditCard_x = new JLabel("");
		bCreditCard_x.setHorizontalAlignment(SwingConstants.CENTER);
		bCreditCard_x.setIcon(new ImageIcon(CreateAccount.class.getResource("/images/logout.jpg")));
		bCreditCard_x.setFont(new Font("Arial", Font.BOLD, 25));
		bCreditCard_x.setBounds(935, 0, 49, 32);
		bCreditCard_x.setForeground(SystemColor.textHighlightText);
		panel.add(bCreditCard_x);
		
		lblLastPage = new JLabel("");
		lblLastPage.setIcon(new ImageIcon(ShoppingCart.class.getResource("/images/back.png")));
		lblLastPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastPage.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLastPage.setBounds(845, 0, 49, 32);
		panel.add(lblLastPage);
		
		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(ShoppingCart.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(891, 0, 49, 32);
		panel.add(lblMenu);	
		
		lblTitle = new JLabel();
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(58, 15, 836, 139);
		panel.add(lblTitle);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(842, 0, 142, 32);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.WHITE);
		panel.add(lblNewLabel);
		
		txtRealName = new JTextField();
		txtRealName.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtRealName.setBounds(382, 160, 342, 32);
		txtRealName.setColumns(10);
		panel.add(txtRealName);

		bank = new JLabel("Accept credit card from : ");
		bank.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		bank.setBounds(236, 462, 568, 42);
		bank.setForeground(Color.RED);
		panel.add(bank);
		
		JLabel lblLastName = new JLabel("Name on Credit Card: ");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLastName.setBounds(236, 116, 233, 32);
		panel.add(lblLastName);
		
		JLabel lblExpiration = new JLabel("Credit Card Expiration Date (YY/MM) : ");
		lblExpiration.setForeground(Color.WHITE);
		lblExpiration.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblExpiration.setBounds(236, 303, 460, 32);
		panel.add(lblExpiration);
		
		JLabel lblSecurity = new JLabel("Credit Card Security Code: ");
		lblSecurity.setForeground(Color.WHITE);
		lblSecurity.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblSecurity.setBounds(236, 365, 247, 32);
		panel.add(lblSecurity);
		
		txtYear = new JTextField();
		txtYear.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtYear.setBounds(584, 304, 38, 32);
		panel.add(txtYear);
		txtYear.setColumns(10);
		
		txtMonth = new JTextField();
		txtMonth.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtMonth.setColumns(10);
		txtMonth.setBounds(632, 304, 38, 32);
		panel.add(txtMonth);
		
		txtSecurityNum = new JTextField();
		txtSecurityNum.setBounds(493, 371, 55, 32);
		panel.add(txtSecurityNum);
		txtSecurityNum.setColumns(10);
		
		dashline1 = new JLabel("-");
		dashline1.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		dashline1.setHorizontalAlignment(SwingConstants.CENTER);
		dashline1.setBounds(422, 247, 47, 15);
		panel.add(dashline1);
		
		dashline2 = new JLabel("-");
		dashline2.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		dashline2.setHorizontalAlignment(SwingConstants.CENTER);
		dashline2.setBounds(506, 247, 47, 15);
		panel.add(dashline2);
		
		dashline3 = new JLabel("-");
		dashline3.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		dashline3.setHorizontalAlignment(SwingConstants.CENTER);
		dashline3.setBounds(584, 247, 47, 15);
		panel.add(dashline3);
		
		creditCardNum1 = new JTextField();
		creditCardNum1.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		creditCardNum1.setBounds(384, 238, 49, 32);
		panel.add(creditCardNum1);
		creditCardNum1.setColumns(10);
		
		creditCardNum2 = new JTextField();
		creditCardNum2.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		creditCardNum2.setBounds(464, 239, 49, 32);
		panel.add(creditCardNum2);
		creditCardNum2.setColumns(10);
		
		creditCardNum3 = new JTextField();
		creditCardNum3.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		creditCardNum3.setBounds(544, 239, 49, 32);
		panel.add(creditCardNum3);
		creditCardNum3.setColumns(10);
		
		creditCardNum4 = new JTextField();
		creditCardNum4.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		creditCardNum4.setBounds(624, 239, 49, 32);
		panel.add(creditCardNum4);
		creditCardNum4.setColumns(10);
		
		lblCitibankHsbc = new JLabel("1. \u81fa\u7063\u9280\u884c 2.\u5408\u4f5c\u91d1\u5eab  3.\u82b1\u65d7\u9280\u884c  4.\u532f\u8c50\u9280\u884c");
		lblCitibankHsbc.setForeground(Color.RED);
		lblCitibankHsbc.setFont(new Font("Noto Sans CJK TC", Font.PLAIN, 16));
		lblCitibankHsbc.setBounds(236, 489, 585, 51);
		panel.add(lblCitibankHsbc);
		
		JLabel lblCoorisbondingCreditCard = new JLabel("Corresponding credit card number : ");
		lblCoorisbondingCreditCard.setForeground(Color.WHITE);
		lblCoorisbondingCreditCard.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblCoorisbondingCreditCard.setBounds(236, 202, 337, 26);
		panel.add(lblCoorisbondingCreditCard);
		
		btnOK = new JButton("OK");
		btnOK.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		btnOK.setBounds(428, 550, 126, 21);
		panel.add(btnOK);
		
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
