import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class Menu extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	JLabel bmenu_x;
	JButton bmenu_search_hotel;
	JButton bmenu_check_reservation;
	JLabel lblTitle;
	JLabel lblLastPage;
	JButton bbook_history;
	

	/**
	 * Create the panel.
	 */
	public Menu() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(150, 50, 984, 603);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 984, 603);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		add(panel);

		bmenu_x = new JLabel("");
		bmenu_x.setHorizontalAlignment(SwingConstants.CENTER);
		bmenu_x.setIcon(new ImageIcon(Menu.class.getResource("/images/logout.jpg")));
		bmenu_x.setFont(new Font("Arial", Font.BOLD, 25));
		bmenu_x.setBounds(935, 0, 49, 32);
		bmenu_x.setForeground(SystemColor.textHighlightText);
		panel.add(bmenu_x);

		JLabel lblTitle = new JLabel("Hotel - Menu");
		lblTitle.setBackground(SystemColor.menu);
		lblTitle.setFont(new Font("Viner Hand ITC", Font.BOLD, 50));
		lblTitle.setBounds(58, 15, 889, 139);
		panel.add(lblTitle);

		JLabel lblSearchHotel = new JLabel("Search available hotel &  Book");
		lblSearchHotel.setFont(new Font("Bahnschrift", Font.PLAIN, 26));
		lblSearchHotel.setBackground(Color.GRAY);
		lblSearchHotel.setBounds(56, 164, 393, 36);
		panel.add(lblSearchHotel);

		bmenu_search_hotel = new JButton("");
		bmenu_search_hotel.setIcon(new ImageIcon(Menu.class.getResource("/images/ok.png")));
		bmenu_search_hotel.setForeground(null);
		bmenu_search_hotel.setBackground(null);
		bmenu_search_hotel.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		bmenu_search_hotel.setBounds(471, 164, 54, 54);
		bmenu_search_hotel.setOpaque(false);
		bmenu_search_hotel.setContentAreaFilled(false);
		panel.add(bmenu_search_hotel);
		
		lblLastPage = new JLabel("");
		lblLastPage.setIcon(new ImageIcon(ShoppingCart.class.getResource("/images/back.png")));
		lblLastPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastPage.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		lblLastPage.setBounds(886, -2, 49, 32);
		panel.add(lblLastPage);

		JLabel lblCheckOrder = new JLabel("Check your reservation");
		lblCheckOrder.setFont(new Font("Bahnschrift", Font.PLAIN, 26));
		lblCheckOrder.setBackground(Color.GRAY);
		lblCheckOrder.setBounds(58, 240, 343, 42);
		panel.add(lblCheckOrder);

		bmenu_check_reservation = new JButton("");
		bmenu_check_reservation.setIcon(new ImageIcon(Menu.class.getResource("/images/ok.png")));
		bmenu_check_reservation.setForeground((Color) null);
		bmenu_check_reservation.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		bmenu_check_reservation.setContentAreaFilled(false);
		bmenu_check_reservation.setOpaque(false);
		bmenu_check_reservation.setBackground((Color) null);
		bmenu_check_reservation.setBounds(471, 241, 54, 54);
		panel.add(bmenu_check_reservation);
		
//		JLabel lblbookHistory = new JLabel("Book History");
//		lblbookHistory.setForeground(Color.BLACK);
//		lblbookHistory.setFont(new Font("Bahnschrift", Font.PLAIN, 26));
//		lblbookHistory.setBounds(58, 310, 343, 51);
//		panel.add(lblbookHistory);
//		
//		bbook_history = new JButton("");
//		bbook_history.setIcon(new ImageIcon(Menu.class.getResource("/images/ok.png")));
//		bbook_history.setOpaque(false);
//		bbook_history.setForeground((Color) null);
//		bbook_history.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
//		bbook_history.setContentAreaFilled(false);
//		bbook_history.setBackground((Color) null);
//		bbook_history.setBounds(471, 320, 54, 54);
//		panel.add(bbook_history);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(886, 0, 98, 32);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.WHITE);
		panel.add(lblNewLabel);
		

		JLabel image = new JLabel("Image");
		image.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
		image.setBounds(0, -425, 2311, 1330);
		panel.add(image);
		image.setIcon(new ImageIcon(Menu.class.getResource("/images/101white.png")));
	}
}
