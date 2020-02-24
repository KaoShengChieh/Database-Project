import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import java.text.SimpleDateFormat;

public class OrderHotel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	JLabel borderhotel_x;
	JButton borderhotel_ok;
	JLabel lblLastPage;
	JLabel lblMenu;
	JComboBox<Integer> bh_hotelid;
	JTextField txtCheckin;
	JTextField txtCheckout;
	JSpinner txt_singleroom;
	JSpinner txt_doubleroom;
	JSpinner txt_quadroom;

	public void showDate(JTextField dateField) {
		java.util.Date d = new java.util.Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		dateField.setText(f.format(d));
	}

	/**
	 * Create the panel.
	 */
	public OrderHotel() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(150, 50, 984, 603);

		JPanel contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(new Color(255, 245, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, 984, 603);
		add(contentPane);
		contentPane.setLayout(null);

		borderhotel_x = new JLabel("");
		borderhotel_x.setHorizontalAlignment(SwingConstants.CENTER);
		borderhotel_x.setIcon(new ImageIcon(OrderHotel.class.getResource("/images/logout.jpg")));
		borderhotel_x.setFont(new Font("Arial", Font.BOLD, 25));
		borderhotel_x.setBounds(935, 0, 49, 32);
		borderhotel_x.setForeground(new Color(51, 63, 125));
		contentPane.add(borderhotel_x);
		/**
		 * 12/14 add last page
		 */
		lblLastPage = new JLabel("");
		lblLastPage.setIcon(new ImageIcon(OrderHotel.class.getResource("/images/back.png")));
		lblLastPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastPage.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLastPage.setBounds(850, -2, 49, 32);
		contentPane.add(lblLastPage);
		/**
		 * 12/14 add menu
		 */
		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(OrderHotel.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(891, 0, 49, 32);
		contentPane.add(lblMenu);

		JLabel lblTitle = new JLabel("Book your Hotel!");
		lblTitle.setForeground(new Color(51, 63, 125));
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(206, 15, 778, 139);
		contentPane.add(lblTitle);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(SearchHotel.class.getResource("/images/101Half.jpg")));
		lblNewLabel.setBounds(32, 0, 218, 603);
		contentPane.add(lblNewLabel);

		JLabel lblHotelId = new JLabel("Hotel ID:");
		lblHotelId.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblHotelId.setBackground(Color.GRAY);
		lblHotelId.setBounds(216, 195, 114, 23);
		contentPane.add(lblHotelId);

		Integer[] content = new Integer[1500];
		for (int i = 0; i < 1500; i++) {
			content[i] = i;
		}

		bh_hotelid = new JComboBox<>(content);
		bh_hotelid.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		bh_hotelid.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		bh_hotelid.setBounds(314, 190, 106, 32);
		contentPane.add(bh_hotelid);

		JLabel lblDate = new JLabel("Date from:");
		lblDate.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDate.setBackground(Color.GRAY);
		lblDate.setBounds(216, 278, 114, 23);
		contentPane.add(lblDate);

		txtCheckin = new JTextField();
		txtCheckin.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				DatePopup datepopup = new DatePopup(txtCheckin);
				datepopup.showDialog();
			}
		});
		txtCheckin.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtCheckin.setBounds(345, 266, 218, 48);
		txtCheckin.setColumns(10);
		contentPane.add(txtCheckin);
		showDate(txtCheckin);

		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblTo.setBackground(Color.GRAY);
		lblTo.setBounds(596, 278, 31, 23);
		contentPane.add(lblTo);

		txtCheckout = new JTextField();
		txtCheckout.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtCheckout.setColumns(10);
		txtCheckout.setBounds(651, 266, 218, 48);
		txtCheckout.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				DatePopup datepopup = new DatePopup(txtCheckout);
				datepopup.showDialog();
			}
		});
		contentPane.add(txtCheckout);
		showDate(txtCheckout);

		JLabel lblNumberOfPeople = new JLabel("Number of Rooms");
		lblNumberOfPeople.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblNumberOfPeople.setBackground(Color.GRAY);
		lblNumberOfPeople.setBounds(216, 356, 218, 23);
		contentPane.add(lblNumberOfPeople);

		JLabel lblSingleNumber = new JLabel("Single");
		lblSingleNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblSingleNumber.setBackground(Color.GRAY);
		lblSingleNumber.setBounds(216, 431, 74, 23);
		contentPane.add(lblSingleNumber);

		txt_singleroom = new JSpinner();
		txt_singleroom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txt_singleroom.setBounds(293, 419, 66, 48);
		contentPane.add(txt_singleroom);

		JLabel lblDoubleNumber = new JLabel("Double");
		lblDoubleNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDoubleNumber.setBackground(Color.GRAY);
		lblDoubleNumber.setBounds(416, 431, 74, 23);
		contentPane.add(lblDoubleNumber);

		txt_doubleroom = new JSpinner();
		txt_doubleroom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txt_doubleroom.setBounds(493, 419, 66, 48);
		contentPane.add(txt_doubleroom);

		JLabel lblQuadNumber = new JLabel("Quad");
		lblQuadNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblQuadNumber.setBackground(Color.GRAY);
		lblQuadNumber.setBounds(616, 431, 74, 23);
		contentPane.add(lblQuadNumber);

		txt_quadroom = new JSpinner();
		txt_quadroom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txt_quadroom.setBounds(693, 419, 66, 48);
		contentPane.add(txt_quadroom);

		borderhotel_ok = new JButton("BOOK");
		borderhotel_ok.setForeground(SystemColor.inactiveCaptionBorder);
		borderhotel_ok.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		borderhotel_ok.setBackground(new Color(51, 63, 125));
		borderhotel_ok.setBounds(495, 509, 132, 32);
		contentPane.add(borderhotel_ok);
		
		JLabel lblupperbackground = new JLabel("");
		lblupperbackground.setBackground(Color.BLACK);
		lblupperbackground.setBounds(192, 0, 792, 31);
		lblupperbackground.setOpaque(true);
		lblupperbackground.setBackground(Color.white);
		contentPane.add(lblupperbackground);
	}
}
