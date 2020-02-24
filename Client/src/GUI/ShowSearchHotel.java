import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ShowSearchHotel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	JLabel showsh_signin;
	JLabel showsh_x;
	JLabel lblResult;
	JButton showsh_sort;
	JButton showsh_book;
	JLabel lblMenu;
	JTable table;
	JLabel lblLastPage ;
	JComboBox<String> showsh_combobox;
	/**
	 * 
	 */
	List<HotelInfo> hotelList;

	public void makeHotellist() {
		DefaultTableModel tablemodel = (DefaultTableModel)table.getModel();
		TableColumnModel columnModel = table.getColumnModel();
		tablemodel.setRowCount(0);
		
		int resultCount = hotelList.size();
		lblResult.setText(resultCount + "  results");
		
		// get data
		for (int i = 0; i < resultCount; i++) {
			int hotelid = hotelList.get(i).hotelID; // id
			String star = ""; // star
			for (int j = 0; j < hotelList.get(i).star; j++) {
				star += "\u2605";
			}
			String city = hotelList.get(i).city; // locality
			String address = hotelList.get(i).address; // address
			String price = "NT " + hotelList.get(i).price; // price
			Object[] data = { hotelid, star, city + "\u5e02" + address, price };
			tablemodel.addRow(data);
		}

		table.setRowHeight(22);
		columnModel.getColumn(0).setPreferredWidth(93);
		columnModel.getColumn(1).setPreferredWidth(100);
		columnModel.getColumn(2).setPreferredWidth(400);
		columnModel.getColumn(3).setPreferredWidth(150);
	}

	public void makeHotellist(List<HotelInfo> _AHR) {
		hotelList = _AHR;
		makeHotellist();
	}

	/**
	 * Create the panel.
	 */
	public ShowSearchHotel() {
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

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ShowSearchHotel.class.getResource("/images/101Half.jpg")));
		lblNewLabel.setBounds(32, 0, 218, 603);
		contentPane.add(lblNewLabel);

		showsh_signin = new JLabel("Sign in");
		showsh_signin.setForeground(new Color(51, 63, 125));
		showsh_signin.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		showsh_signin.setBounds(795, 0, 49, 32);
		contentPane.add(showsh_signin);
		
		showsh_x = new JLabel("");
		showsh_x.setHorizontalAlignment(SwingConstants.CENTER);
		showsh_x.setIcon(new ImageIcon(ShowSearchHotel.class.getResource("/images/logout.jpg")));
		showsh_x.setFont(new Font("Arial", Font.BOLD, 25));
		showsh_x.setBounds(935, 0, 49, 32);
		showsh_x.setForeground(new Color(51, 63, 125));
		contentPane.add(showsh_x);
		
		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(ShowSearchHotel.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(894, 0, 49, 32);
		contentPane.add(lblMenu);

		JLabel lblTitle = new JLabel("Search your Hotel!");
		lblTitle.setForeground(new Color(51, 63, 125));
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(206, 15, 778, 139);
		contentPane.add(lblTitle);

		lblResult = new JLabel("");
		lblResult.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblResult.setBackground(Color.GRAY);
		lblResult.setBounds(206, 132, 193, 23);
		contentPane.add(lblResult);

		String[] sortMethod = new String[] {"Price: Low to High", "Star", "Locality"};
		showsh_combobox = new JComboBox<>(sortMethod);
		showsh_combobox.setToolTipText("Filter");
		showsh_combobox.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		showsh_combobox.setForeground(new Color(51, 23, 125));
		showsh_combobox.setBounds(417, 127, 342, 32);
		contentPane.add(showsh_combobox);

		showsh_sort = new JButton("Sort");
		showsh_sort.setBounds(771, 128, 111, 31);
		showsh_sort.setForeground(new Color(51, 23, 125));
		showsh_sort.setBackground(SystemColor.control);
		showsh_sort.setFont(new Font("Bahnschrift", Font.PLAIN, 19));
		contentPane.add(showsh_sort);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(206, 178, 761, 382);
		contentPane.add(scrollPane);

		table = new JTable();
		table.getTableHeader().setFont(new Font("Bahnschrift", Font.PLAIN, 18));
		table.setFont(new Font("Noto Sans CJK TC", Font.PLAIN, 18));
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		lblLastPage = new JLabel("");
		lblLastPage.setIcon(new ImageIcon(SignIn.class.getResource("/images/back.png")));
		lblLastPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastPage.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLastPage.setBounds(854, -2, 49, 32);
		contentPane.add(lblLastPage);
		
		String[] heading = new String[] { "Hotel ID", "Star", "Address", "Price" };
		TableColumnModel columnModel = table.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		
		table.setModel(new DefaultTableModel(heading, 0));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		columnModel.getColumn(0).setCellRenderer(centerRenderer);
		columnModel.getColumn(1).setCellRenderer(centerRenderer);
		columnModel.getColumn(3).setCellRenderer(centerRenderer);
		
		showsh_book = new JButton("BOOK");
		showsh_book.setForeground(SystemColor.inactiveCaptionBorder);
		showsh_book.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		showsh_book.setBackground(new Color(51,63,125));
		showsh_book.setBounds(538, 565, 111, 31);
		contentPane.add(showsh_book);
	}
}
