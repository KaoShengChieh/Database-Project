import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class CheckReservation extends JPanel
{
	private static final long serialVersionUID = 1L;
		
	JLabel sr_x;
	JButton sh_ok;
	JLabel lblMenu;
	JComboBox<String> changeOrder;
	JTable table;
	JTextField text_BookID;
	List<Query> orderList;
	List<Query> historyList;

	public void makeOrderList(List<Query> orderList) {
		DefaultTableModel tablemodel = (DefaultTableModel)table.getModel();
		TableColumnModel columnModel = table.getColumnModel();
		tablemodel.setRowCount(0);
		
		int resultCount = orderList.size();
		// get data
		for (int i = 0; i < resultCount; i++) {
			String bookid = orderList.get(i).bookID;
			String hotelid = "" + orderList.get(i).hotelID + " ";
			String singleNum = "" + orderList.get(i).roomNum[0] + " ";
			String doubleNum = "" + orderList.get(i).roomNum[1] + " ";
			String quadNum = "" + orderList.get(i).roomNum[2] + " ";
			String checkin = orderList.get(i).checkin;
			String checkout = orderList.get(i).checkout;
//			String hotelAddress = orderList.get(i).hotelAddress;
			String price = " NT " + orderList.get(i).price;
			
			String expiration = "";
			
			Object[] data = { bookid, hotelid, singleNum, doubleNum, quadNum, checkin, checkout, price, expiration};
			tablemodel.addRow(data);
		}
		
		table.setRowHeight(22);
		columnModel.getColumn(0).setPreferredWidth(90);
		columnModel.getColumn(1).setPreferredWidth(80);
		columnModel.getColumn(2).setPreferredWidth(70);
		columnModel.getColumn(3).setPreferredWidth(70);
		columnModel.getColumn(4).setPreferredWidth(70);
		columnModel.getColumn(5).setPreferredWidth(90);
		columnModel.getColumn(6).setPreferredWidth(90);
		columnModel.getColumn(7).setPreferredWidth(80);
		columnModel.getColumn(8).setPreferredWidth(120);

		this.orderList = orderList;
	}

	public CheckReservation() {
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

		sr_x = new JLabel("");
		sr_x.setHorizontalAlignment(SwingConstants.CENTER);
		sr_x.setIcon(new ImageIcon(CheckReservation.class.getResource("/images/logout.jpg")));
		sr_x.setFont(new Font("Arial", Font.BOLD, 25));
		sr_x.setBounds(935, 0, 49, 32);
		sr_x.setForeground(new Color(51, 63, 125));
		contentPane.add(sr_x);
		
		lblMenu = new JLabel("");
		lblMenu.setIcon(new ImageIcon(CheckReservation.class.getResource("/images/menu.png")));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblMenu.setBounds(897, 0, 49, 32);
		contentPane.add(lblMenu);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(SearchHotel.class.getResource("/images/101Half.jpg")));
		lblNewLabel.setBounds(32, 0, 218, 603);
		contentPane.add(lblNewLabel);

		JLabel lblTitle = new JLabel("Check your Reservation");
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
		
		String[] heading = new String[] { "Book ID", "Hotel ID",
				"Single", "Double", "Quad", "Check in", "Check out", "Price", "expiration" };
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
		

		JLabel lblChangeReservation = new JLabel("Change reservation:");
		lblChangeReservation.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblChangeReservation.setBounds(235, 518, 218, 31);
		contentPane.add(lblChangeReservation);

		text_BookID = new JTextField("Select Book ID");
		text_BookID.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		text_BookID.setBounds(432, 518, 151, 32);
		contentPane.add(text_BookID);
		text_BookID.setColumns(10);

		changeOrder = new JComboBox<>(new String[] {"Modify", "Cancel", "Pay"});
		changeOrder.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		changeOrder.setBounds(604, 518, 177, 32);
		contentPane.add(changeOrder);

		sh_ok = new JButton("OK");
		sh_ok.setForeground(SystemColor.inactiveCaptionBorder);
		sh_ok.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		sh_ok.setBackground(new Color(51, 63, 125));
		sh_ok.setBounds(803, 518, 132, 32);
		contentPane.add(sh_ok);
	}
}
