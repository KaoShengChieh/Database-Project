import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class ShoppingCart extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JLabel cr_x;
	JTable table;
	JLabel taipei_101;
	JLabel bank;
	JLabel lblBookID;
	JLabel lblHotelID;
	JLabel lblDateFrom;
	JLabel lblDateTo;
	JLabel lblSingleNumber;
	JLabel lblDoubleNumber;
	JLabel lblQuadNumber;
	JLabel lblLastPage;
	JLabel lblMenu;
	JButton btnPay;
	JComboBox<String> bankSelection;
	JTextField creditCardNum1;
	JTextField creditCardNum2;
	JTextField creditCardNum3;
	JTextField creditCardNum4;
	JLabel lblHotelAddress;
	private JLabel dashline1;
	private JLabel dashline2;
	private JLabel dashline3;

	public ShoppingCart() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(150, 50, 984, 603);

		JPanel contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(new Color(255, 245, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(10, 0, 974, 603);
		contentPane.setLayout(null);
		add(contentPane);

		cr_x = new JLabel("");
		cr_x.setHorizontalAlignment(SwingConstants.CENTER);
		cr_x.setIcon(new ImageIcon(ShoppingCart.class.getResource("/images/logout.jpg")));
		cr_x.setFont(new Font("Bahnschrift", Font.BOLD, 25));
		cr_x.setBounds(925, 0, 49, 32);
		cr_x.setForeground(new Color(51, 63, 125));
		contentPane.add(cr_x);
		
		lblLastPage = new JLabel("");
		lblLastPage.setIcon(new ImageIcon(ShoppingCart.class.getResource("/images/back.png")));
		lblLastPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastPage.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLastPage.setBounds(839, -2, 49, 32);
		contentPane.add(lblLastPage);
		
		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(ShoppingCart.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(881, 0, 49, 32);
		contentPane.add(lblMenu);


		JLabel lbltext = new JLabel("Book ID:");
		lbltext.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbltext.setBackground(Color.GRAY);
		lbltext.setBounds(216, 150, 114, 23);
		contentPane.add(lbltext);
		
		lblBookID = new JLabel("");
		lblBookID.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblBookID.setBackground(Color.GRAY);
		lblBookID.setBounds(311, 150, 194, 23);
		contentPane.add(lblBookID);
		
		lblHotelID = new JLabel("Hotel ID:");
		lblHotelID.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblHotelID.setBackground(Color.GRAY);
		lblHotelID.setBounds(556, 150, 302, 23);
		contentPane.add(lblHotelID);
		
		lblDateFrom = new JLabel("Date from:");
		lblDateFrom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDateFrom.setBackground(Color.GRAY);
		lblDateFrom.setBounds(216, 211, 279, 23);
		contentPane.add(lblDateFrom);
		
		lblDateTo = new JLabel("to");
		lblDateTo.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDateTo.setBackground(Color.GRAY);
		lblDateTo.setBounds(444, 211, 205, 23);
		contentPane.add(lblDateTo);
		
		JLabel lblNumberOfPeople = new JLabel("Number of Rooms");
		lblNumberOfPeople.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblNumberOfPeople.setBackground(Color.GRAY);
		lblNumberOfPeople.setBounds(216, 322, 218, 23);
		contentPane.add(lblNumberOfPeople);
		
		lblSingleNumber = new JLabel("Single");
		lblSingleNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblSingleNumber.setBackground(Color.GRAY);
		lblSingleNumber.setBounds(216, 381, 151, 23);
		contentPane.add(lblSingleNumber);
		
		lblDoubleNumber = new JLabel("Double");
		lblDoubleNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDoubleNumber.setBackground(Color.GRAY);
		lblDoubleNumber.setBounds(416, 381, 165, 23);
		contentPane.add(lblDoubleNumber);
		
		lblQuadNumber = new JLabel("Quad");
		lblQuadNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblQuadNumber.setBackground(Color.GRAY);
		lblQuadNumber.setBounds(616, 381, 162, 23);
		contentPane.add(lblQuadNumber);

		JLabel lblTitle = new JLabel("Your Shopping Cart!");
		lblTitle.setForeground(new Color(51, 63, 125));
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(206, 15, 768, 139);
		contentPane.add(lblTitle);

		bank = new JLabel("Paying Bank :");
		bank.setFont(new Font("Arial", Font.BOLD, 25));
		bank.setBounds(211, 442, 196, 32);
		bank.setForeground(new Color(51, 63, 125));
		contentPane.add(bank);

		bankSelection = new JComboBox<>(new String[] { "臺灣銀行", "合作金庫", "花旗銀行", "滙豐銀行" });
		bankSelection.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		bankSelection.setBounds(390, 442, 286, 32);
		contentPane.add(bankSelection);

		btnPay = new JButton("Pay for reservation");
		btnPay.setForeground(SystemColor.inactiveCaptionBorder);
		btnPay.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		btnPay.setBackground(new Color(51, 63, 125));
		btnPay.setBounds(739, 537, 213, 32);
		contentPane.add(btnPay);
		
		lblHotelAddress = new JLabel("Hotel Address:");
		lblHotelAddress.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblHotelAddress.setBounds(216, 260, 299, 32);
		contentPane.add(lblHotelAddress);

		taipei_101 = new JLabel("");
		taipei_101.setIcon(new ImageIcon(SearchHotel.class.getResource("/images/101Half.jpg")));
		taipei_101.setBounds(32, 0, 161, 603);
		contentPane.add(taipei_101);
		
		dashline1 = new JLabel("-");
		dashline1.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		dashline1.setHorizontalAlignment(SwingConstants.CENTER);
		dashline1.setBounds(254, 546, 47, 15);
		contentPane.add(dashline1);
		
		dashline2 = new JLabel("-");
		dashline2.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		dashline2.setHorizontalAlignment(SwingConstants.CENTER);
		dashline2.setBounds(338, 546, 47, 15);
		contentPane.add(dashline2);
		
		dashline3 = new JLabel("-");
		dashline3.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		dashline3.setHorizontalAlignment(SwingConstants.CENTER);
		dashline3.setBounds(416, 546, 47, 15);
		contentPane.add(dashline3);
		
		creditCardNum1 = new JTextField();
		creditCardNum1.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		creditCardNum1.setBounds(216, 537, 49, 32);
		contentPane.add(creditCardNum1);
		creditCardNum1.setColumns(10);
		
		creditCardNum2 = new JTextField();
		creditCardNum2.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		creditCardNum2.setBounds(296, 538, 49, 32);
		contentPane.add(creditCardNum2);
		creditCardNum2.setColumns(10);
		
		creditCardNum3 = new JTextField();
		creditCardNum3.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		creditCardNum3.setBounds(376, 538, 49, 32);
		contentPane.add(creditCardNum3);
		creditCardNum3.setColumns(10);
		
		creditCardNum4 = new JTextField();
		creditCardNum4.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		creditCardNum4.setBounds(456, 538, 49, 32);
		contentPane.add(creditCardNum4);
		creditCardNum4.setColumns(10);
		
		JLabel lblCoorisbondingCreditCard = new JLabel("Corresponding credit card number : ");
		lblCoorisbondingCreditCard.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblCoorisbondingCreditCard.setBounds(216, 499, 337, 26);
		contentPane.add(lblCoorisbondingCreditCard);
		

	}
}
