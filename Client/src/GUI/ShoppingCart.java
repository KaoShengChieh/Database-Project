import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class ShoppingCart extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JLabel cr_x;
	JTable table;
	JLabel taipei_101;
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
	JLabel lblHotelAddress;

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
	}
}
