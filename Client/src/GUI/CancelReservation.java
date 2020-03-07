import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CancelReservation extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	JLabel cr_x;
	JButton cr_ok;
	JLabel lblLastPage;
	JLabel lblMenu;
	JLabel lblBookID;
	JLabel lblHotelID;
	JLabel lblDateFrom;
	JLabel lblDateTo;
	JLabel lblSingleNumber;
	JLabel lblDoubleNumber;
	JLabel lblQuadNumber;
	JLabel lblHotelAddress;

	public CancelReservation() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(150,50,984,603);
		
		JPanel contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(new Color(255,245,238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, 984, 603);
		add(contentPane);
		contentPane.setLayout(null);
		
		cr_x = new JLabel("");
		cr_x.setHorizontalAlignment(SwingConstants.CENTER);
		cr_x.setIcon(new ImageIcon(CancelReservation.class.getResource("/images/logout.jpg")));
		cr_x.setFont(new Font("Arial", Font.BOLD, 25));
		cr_x.setBounds(935, 0, 49, 32);
		cr_x.setForeground(new Color(51,63,125));
		contentPane.add(cr_x);

		lblLastPage = new JLabel("");
		lblLastPage.setIcon(new ImageIcon(CancelReservation.class.getResource("/images/back.png")));
		lblLastPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastPage.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLastPage.setBounds(842, -2, 49, 32);
		contentPane.add(lblLastPage);

		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(CancelReservation.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(889, 0, 49, 32);
		contentPane.add(lblMenu);
		
		JLabel lblTitle = new JLabel("Cancel your Reservation");
		lblTitle.setForeground(new Color(51,63,125));
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(206, 15, 778, 139);
		contentPane.add(lblTitle);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(SearchHotel.class.getResource("/images/101Half.jpg")));
		lblNewLabel.setBounds(32, 0, 161, 603);
		contentPane.add(lblNewLabel);
		
		JLabel lbltext = new JLabel("Book ID:");
		lbltext.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lbltext.setBackground(Color.GRAY);
		lbltext.setBounds(206, 138, 114, 23);
		contentPane.add(lbltext);
		
		lblBookID = new JLabel("");
		lblBookID.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblBookID.setBackground(Color.GRAY);
		lblBookID.setBounds(311, 138, 194, 23);
		contentPane.add(lblBookID);
		
		lblHotelID = new JLabel("Hotel ID:");
		lblHotelID.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblHotelID.setBackground(Color.GRAY);
		lblHotelID.setBounds(546, 138, 302, 23);
		contentPane.add(lblHotelID);
		
		lblDateFrom = new JLabel("Date from:");
		lblDateFrom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDateFrom.setBackground(Color.GRAY);
		lblDateFrom.setBounds(206, 221, 317, 23);
		contentPane.add(lblDateFrom);
		
		lblDateTo = new JLabel("to");
		lblDateTo.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDateTo.setBackground(Color.GRAY);
		lblDateTo.setBounds(535, 221, 275, 23);
		contentPane.add(lblDateTo);
		
		JLabel lblNumberOfPeople = new JLabel("Number of Rooms");
		lblNumberOfPeople.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblNumberOfPeople.setBackground(Color.GRAY);
		lblNumberOfPeople.setBounds(206, 355, 218, 23);
		contentPane.add(lblNumberOfPeople);
		
		lblHotelAddress = new JLabel("Hotel Address:");
		lblHotelAddress.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblHotelAddress.setBounds(206, 282, 696, 32);
		contentPane.add(lblHotelAddress);
		
		lblSingleNumber = new JLabel("Single");
		lblSingleNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblSingleNumber.setBackground(Color.GRAY);
		lblSingleNumber.setBounds(206, 435, 151, 23);
		contentPane.add(lblSingleNumber);
		
		lblDoubleNumber = new JLabel("Double");
		lblDoubleNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDoubleNumber.setBackground(Color.GRAY);
		lblDoubleNumber.setBounds(406, 435, 165, 23);
		contentPane.add(lblDoubleNumber);
		
		lblQuadNumber = new JLabel("Quad");
		lblQuadNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblQuadNumber.setBackground(Color.GRAY);
		lblQuadNumber.setBounds(606, 435, 162, 23);
		contentPane.add(lblQuadNumber);
		
		cr_ok = new JButton("OK");
		cr_ok.setForeground(SystemColor.inactiveCaptionBorder);
		cr_ok.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		cr_ok.setBackground(new Color(51,63,125));
		cr_ok.setBounds(463, 520, 132, 32);
		contentPane.add(cr_ok);
	}
}
