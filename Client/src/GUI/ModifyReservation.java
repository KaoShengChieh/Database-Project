import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.SpinnerNumberModel;

public class ModifyReservation extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	JLabel cr_x;
	JButton cr_ok;
	JLabel lblLastPage;
	JLabel lblMenu;
	JLabel lblTitle;
	JLabel lblBookID;
	JLabel lblHotelID;
	JTextField txtDateFrom;
	JTextField txtDateTo;
	JLabel lblSingleNumber;
	JLabel lblDoubleNumber;
	JLabel lblQuadNumber;
	JSpinner txt_singleroom;
	JSpinner txt_doubleroom;
	JSpinner txt_quadroom;
	JLabel lblHotelAddress;

	public ModifyReservation() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(150, 50, 984, 603);

		JPanel contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(new Color(255, 245, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, 984, 603);
		contentPane.setLayout(null);
		add(contentPane);

		cr_x = new JLabel("");
		cr_x.setHorizontalAlignment(SwingConstants.CENTER);
		cr_x.setIcon(new ImageIcon(ModifyReservation.class.getResource("/images/logout.jpg")));
		cr_x.setFont(new Font("Arial", Font.BOLD, 25));
		cr_x.setBounds(935, 0, 49, 32);
		cr_x.setForeground(new Color(51, 63, 125));
		contentPane.add(cr_x);

		lblLastPage = new JLabel("");
		lblLastPage.setIcon(new ImageIcon(ModifyReservation.class.getResource("/images/back.png")));
		lblLastPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastPage.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLastPage.setBounds(849, -2, 49, 32);
		contentPane.add(lblLastPage);

		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(ModifyReservation.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(892, 0, 49, 32);
		contentPane.add(lblMenu);

		lblTitle = new JLabel("Modify your Reservation");
		lblTitle.setForeground(new Color(51, 63, 125));
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
		lbltext.setBounds(216, 149, 114, 23);
		contentPane.add(lbltext);

		lblBookID = new JLabel("");
		lblBookID.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblBookID.setBackground(Color.GRAY);
		lblBookID.setBounds(321, 149, 204, 23);
		contentPane.add(lblBookID);

		lblHotelID = new JLabel("Hotel ID:");
		lblHotelID.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblHotelID.setBackground(Color.GRAY);
		lblHotelID.setBounds(556, 149, 313, 23);
		contentPane.add(lblHotelID);

		JLabel lblDateFrom = new JLabel("Date from:");
		lblDateFrom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDateFrom.setBackground(Color.GRAY);
		lblDateFrom.setBounds(216, 209, 114, 23);
		contentPane.add(lblDateFrom);

		txtDateFrom = new JTextField();
		txtDateFrom.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				DatePopup datepopup = new DatePopup(txtDateFrom);
				datepopup.showDialog();
			}
		});
		txtDateFrom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtDateFrom.setBounds(345, 197, 218, 48);
		contentPane.add(txtDateFrom);
		txtDateFrom.setColumns(10);

		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblTo.setBackground(Color.GRAY);
		lblTo.setBounds(596, 209, 31, 23);
		contentPane.add(lblTo);

		txtDateTo = new JTextField();
		txtDateTo.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txtDateTo.setColumns(10);
		txtDateTo.setBounds(651, 197, 218, 48);
		txtDateTo.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				DatePopup datepopup = new DatePopup(txtDateTo);
				datepopup.showDialog();
			}
		});
		contentPane.add(txtDateTo);
		
		lblHotelAddress = new JLabel("Hotel Address:");
		lblHotelAddress.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblHotelAddress.setBounds(216, 277, 653, 32);
		contentPane.add(lblHotelAddress);

		JLabel lblNumberOfPeople = new JLabel("Number of Rooms");
		lblNumberOfPeople.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblNumberOfPeople.setBackground(Color.GRAY);
		lblNumberOfPeople.setBounds(216, 351, 218, 23);
		contentPane.add(lblNumberOfPeople);

		lblSingleNumber = new JLabel("Single");
		lblSingleNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblSingleNumber.setBackground(Color.GRAY);
		lblSingleNumber.setBounds(216, 431, 74, 23);
		contentPane.add(lblSingleNumber);

		txt_singleroom = new JSpinner();
		txt_singleroom.setModel(new SpinnerNumberModel(0, 0, null, 1));
		txt_singleroom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txt_singleroom.setBounds(293, 414, 66, 48);
		contentPane.add(txt_singleroom);

		lblDoubleNumber = new JLabel("Double");
		lblDoubleNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDoubleNumber.setBackground(Color.GRAY);
		lblDoubleNumber.setBounds(416, 431, 74, 23);
		contentPane.add(lblDoubleNumber);

		txt_doubleroom = new JSpinner();
		txt_doubleroom.setModel(new SpinnerNumberModel(0, 0, null, 1));
		txt_doubleroom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txt_doubleroom.setBounds(493, 414, 66, 48);
		contentPane.add(txt_doubleroom);

		lblQuadNumber = new JLabel("Quad");
		lblQuadNumber.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblQuadNumber.setBackground(Color.GRAY);
		lblQuadNumber.setBounds(616, 431, 74, 23);
		contentPane.add(lblQuadNumber);

		txt_quadroom = new JSpinner();
		txt_quadroom.setModel(new SpinnerNumberModel(0, 0, null, 1));
		txt_quadroom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		txt_quadroom.setBounds(693, 414, 66, 48);
		contentPane.add(txt_quadroom);

		cr_ok = new JButton("OK");
		cr_ok.setForeground(SystemColor.inactiveCaptionBorder);
		cr_ok.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		cr_ok.setBackground(new Color(51, 63, 125));
		cr_ok.setBounds(473, 521, 132, 32);
		contentPane.add(cr_ok);
	}
}
