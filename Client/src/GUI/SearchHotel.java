import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchHotel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	JLabel bsearchhotel_signin;
	JLabel bsearchhotel_x;
	JTextField sh_datecheckin;
	JTextField sh_datecheckout;
	JSpinner searchhotel_single;
	JSpinner searchhotel_double;
	JSpinner searchhotel_quad;
	JLabel lblMenu;
	JComboBox<String> searchhotel_citycombobox;
	JSpinner searchhotel_minstarspinner;
	JSpinner searchhotel_maxstarspinner;
	JButton bsearchhotel_search;

	public void showDate(String checkin) {
		sh_datecheckin.setText(checkin);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		Calendar calendar = Calendar.getInstance();
		try {calendar.setTime(formatter.parse(checkin));} catch (ParseException e) {}
		calendar.add(Calendar.DATE, 1);
		String nextDay = formatter.format(calendar.getTime());
		sh_datecheckout.setText(nextDay);
	}

	public SearchHotel() {
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

		bsearchhotel_signin = new JLabel("Sign in");
		bsearchhotel_signin.setForeground(new Color(51, 63, 125));
		bsearchhotel_signin.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		bsearchhotel_signin.setBounds(838, 0, 65, 32);
		contentPane.add(bsearchhotel_signin);

		JLabel lblTitle = new JLabel("Search your Hotel!");
		lblTitle.setForeground(new Color(51, 63, 125));
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(206, 15, 778, 139);
		contentPane.add(lblTitle);

		JLabel lblDate = new JLabel("Date from:");
		lblDate.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDate.setBackground(Color.GRAY);
		lblDate.setBounds(216, 190, 114, 23);
		contentPane.add(lblDate);

		sh_datecheckin = new JTextField();
		sh_datecheckin.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				DatePopup datepopup = new DatePopup(sh_datecheckin);
				datepopup.showDialog();
				showDate(sh_datecheckin.getText());
			}
		});
		sh_datecheckin.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		sh_datecheckin.setBounds(345, 178, 203, 48);
		contentPane.add(sh_datecheckin);
		sh_datecheckin.setColumns(10);

		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblTo.setBackground(Color.GRAY);
		lblTo.setBounds(596, 190, 31, 23);
		contentPane.add(lblTo);

		sh_datecheckout = new JTextField();
		sh_datecheckout.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				DatePopup datepopup = new DatePopup(sh_datecheckout);
				datepopup.showDialog();
			}
		});
		sh_datecheckout.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		sh_datecheckout.setColumns(10);
		sh_datecheckout.setBounds(651, 178, 218, 48);
		contentPane.add(sh_datecheckout);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		String today = formatter.format(new java.util.Date());
		showDate(today);

		JLabel lblRoomType = new JLabel("Room numbers");
		lblRoomType.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblRoomType.setBackground(Color.GRAY);
		lblRoomType.setBounds(216, 274, 156, 23);
		contentPane.add(lblRoomType);

		JLabel lblSingleRoom = new JLabel("Single");
		lblSingleRoom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblSingleRoom.setBackground(Color.GRAY);
		lblSingleRoom.setBounds(216, 350, 80, 23);
		contentPane.add(lblSingleRoom);

		searchhotel_single = new JSpinner();
		searchhotel_single.setModel(new SpinnerNumberModel(0, 0, null, 1));
		searchhotel_single.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		searchhotel_single.setBounds(300, 338, 70, 48);
		contentPane.add(searchhotel_single);

		JLabel lblDoubleRoom = new JLabel("Double");
		lblDoubleRoom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblDoubleRoom.setBackground(Color.GRAY);
		lblDoubleRoom.setBounds(416, 350, 80, 23);
		contentPane.add(lblDoubleRoom);

		searchhotel_double = new JSpinner();
		searchhotel_double.setModel(new SpinnerNumberModel(0, 0, null, 1));
		searchhotel_double.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		searchhotel_double.setBounds(500, 338, 70, 48);
		contentPane.add(searchhotel_double);

		JLabel lblQuadRoom = new JLabel("Quad");
		lblQuadRoom.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblQuadRoom.setBackground(Color.GRAY);
		lblQuadRoom.setBounds(616, 350, 80, 23);
		contentPane.add(lblQuadRoom);

		searchhotel_quad = new JSpinner();
		searchhotel_quad.setModel(new SpinnerNumberModel(0, 0, null, 1));
		searchhotel_quad.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		searchhotel_quad.setBounds(700, 338, 70, 48);
		contentPane.add(searchhotel_quad);

		bsearchhotel_x = new JLabel("");
		bsearchhotel_x.setHorizontalAlignment(SwingConstants.CENTER);
		bsearchhotel_x.setIcon(new ImageIcon(SearchHotel.class.getResource("/images/logout.jpg")));
		bsearchhotel_x.setFont(new Font("Arial", Font.BOLD, 25));
		bsearchhotel_x.setBounds(935, 0, 49, 32);
		bsearchhotel_x.setForeground(new Color(51, 63, 125));
		contentPane.add(bsearchhotel_x);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(SearchHotel.class.getResource("/images/101Half.jpg")));
		lblNewLabel.setBounds(32, 0, 218, 603);
		contentPane.add(lblNewLabel);

		JLabel lblCity = new JLabel("City");
		lblCity.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblCity.setBackground(Color.GRAY);
		lblCity.setBounds(216, 440, 70, 23);
		contentPane.add(lblCity);
		String[] citysName = new String[] { "\u53f0\u5317", "\u53f0\u4e2d", "\u9ad8\u96c4" };
		searchhotel_citycombobox = new JComboBox<>(citysName);
		searchhotel_citycombobox.setFont(new Font("Noto Sans CJK TC", Font.PLAIN, 18));
		searchhotel_citycombobox.setBounds(288, 435, 80, 32);
		((JLabel)searchhotel_citycombobox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(searchhotel_citycombobox);
		
		JLabel lblStar = new JLabel("Star from:");
		lblStar.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblStar.setBackground(Color.GRAY);
		lblStar.setBounds(506, 440, 114, 23);
		contentPane.add(lblStar);

		searchhotel_minstarspinner = new JSpinner();
		searchhotel_minstarspinner.setModel(new SpinnerNumberModel(2, 2, 5, 1));
		searchhotel_minstarspinner.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		searchhotel_minstarspinner.setBounds(636, 435, 50, 32);
		contentPane.add(searchhotel_minstarspinner);

		JLabel lblTo1 = new JLabel("to");
		lblTo1.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblTo1.setBackground(Color.GRAY);
		lblTo1.setBounds(729, 440, 31, 23);
		contentPane.add(lblTo1);

		searchhotel_maxstarspinner = new JSpinner();
		searchhotel_maxstarspinner.setModel(new SpinnerNumberModel(5, 2, 5, 1));
		searchhotel_maxstarspinner.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		searchhotel_maxstarspinner.setBounds(794, 435, 50, 32);
		contentPane.add(searchhotel_maxstarspinner);

		bsearchhotel_search = new JButton("Search");
		bsearchhotel_search.setBounds(492, 526, 111, 31);
		bsearchhotel_search.setForeground(new Color(51, 23, 125));
		bsearchhotel_search.setBackground(SystemColor.control);
		bsearchhotel_search.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		contentPane.add(bsearchhotel_search);
		
		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(ShowSearchHotel.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(895, 0, 49, 32);
		contentPane.add(lblMenu);
		
		JLabel lblupperbackground = new JLabel("");
		lblupperbackground.setBackground(Color.BLACK);
		lblupperbackground.setBounds(192, 0, 792, 31);
		lblupperbackground.setOpaque(true);
		lblupperbackground.setBackground(Color.white);
		contentPane.add(lblupperbackground);
	}
}
