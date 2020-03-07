import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ListHistory extends JPanel {
	private static final long serialVersionUID = 1L;

	JLabel cr_x;
	JLabel lblMenu;
	JTable table;
	List<OrderInfo> historyList;
	final String banks[] = { "\u81fa\u7063\u9280\u884c", "\u5408\u4f5c\u91d1\u5eab","\u82b1\u65d7\u9280\u884c", "\u532f\u8c50\u9280\u884c"};
	
	public void makeHistoryList(List<OrderInfo> historyList) {
		DefaultTableModel tablemodel = (DefaultTableModel)table.getModel();
		TableColumnModel columnModel = table.getColumnModel();
		tablemodel.setRowCount(0);		
		
		int resultCount = historyList.size();
		
		for (int i = 0; i < resultCount; i++) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
			String today = formatter.format(new java.util.Date());
		
		if (countDays(historyList.get(i).checkout, today) <= 0) {
//			String bookid = historyList.get(i).bookID;
			String hotelid = "" + historyList.get(i).hotelID + " ";
			String singleNum = "" + historyList.get(i).roomNum[0] + " ";
			String doubleNum = "" + historyList.get(i).roomNum[1] + " ";
			String quadNum = "" + historyList.get(i).roomNum[2] + " ";
			String checkin = historyList.get(i).checkin;
			String checkout = historyList.get(i).checkout;
			String hotelAddress = historyList.get(i).address;
			String price = " NT " + historyList.get(i).price;
			String bank = banks[Integer.valueOf(historyList.get(i).additionalInfo)];
			
			Object[] data = { hotelid, hotelAddress,singleNum, doubleNum, quadNum, checkin, checkout, price, bank};
			tablemodel.addRow(data);
			
			table.setRowHeight(22);
			columnModel.getColumn(0).setPreferredWidth(80);
			columnModel.getColumn(1).setPreferredWidth(500);
			columnModel.getColumn(2).setPreferredWidth(80);
			columnModel.getColumn(3).setPreferredWidth(80);
			columnModel.getColumn(4).setPreferredWidth(80);
			columnModel.getColumn(5).setPreferredWidth(120);
			columnModel.getColumn(6).setPreferredWidth(120);
			columnModel.getColumn(7).setPreferredWidth(80);
			columnModel.getColumn(8).setPreferredWidth(150);

			this.historyList = historyList;
			}
		}
	}
	
	private int countDays(String D1, String D2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate firstDate = LocalDate.parse(D1, formatter);
		LocalDate secondDate = LocalDate.parse(D2, formatter);
		
		return (int)ChronoUnit.DAYS.between(firstDate, secondDate) + 1;
	}
	
	public ListHistory() {
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
		
		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(CheckReservation.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(883, 0, 49, 32);
		contentPane.add(lblMenu);
		
		cr_x = new JLabel("");
		cr_x.setHorizontalAlignment(SwingConstants.CENTER);
		cr_x.setIcon(new ImageIcon(ShoppingCart.class.getResource("/images/logout.jpg")));
		cr_x.setFont(new Font("Bahnschrift", Font.BOLD, 25));
		cr_x.setBounds(925, 0, 49, 32);
		cr_x.setForeground(new Color(51, 63, 125));
		contentPane.add(cr_x);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(SearchHotel.class.getResource("/images/101Half.jpg")));
		lblNewLabel.setBounds(32, 0, 218, 603);
		contentPane.add(lblNewLabel);
		
		JLabel lblTitle = new JLabel("Your booking history");
		lblTitle.setForeground(new Color(51, 63, 125));
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(206, 15, 778, 139);
		contentPane.add(lblTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(211, 148, 761, 342);
		scrollPane.setVisible(true);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.getTableHeader().setFont(new Font("Bahnschrift", Font.PLAIN, 18));
		table.setFont(new Font("Bahnschrift", Font.PLAIN, 18));
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		String[] heading = new String[] { "Hotel ID","Hotel Address",
				"Single", "Double", "Quad", "Check in", "Check out", "Price", "bank" };
		TableColumnModel columnModel = table.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		
		table.setModel(new DefaultTableModel(heading, 0));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		columnModel.getColumn(0).setCellRenderer(centerRenderer);
		columnModel.getColumn(5).setCellRenderer(centerRenderer);
		columnModel.getColumn(6).setCellRenderer(centerRenderer);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		columnModel.getColumn(1).setCellRenderer(rightRenderer);
		columnModel.getColumn(2).setCellRenderer(rightRenderer);
		columnModel.getColumn(3).setCellRenderer(rightRenderer);
		columnModel.getColumn(4).setCellRenderer(rightRenderer);
	}
}
