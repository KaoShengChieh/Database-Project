import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Welcome extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JButton bwelcome_signin;
	JButton bwelcome_creataccount;
	JButton bwelcome_guest;
	JLabel bwelcome_x ;

	public Welcome() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(150,50,984,603);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 984, 603);
		panel.setBackground(new Color(255, 255, 255));
		add(panel);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		
		bwelcome_guest = new JButton("Guest");
		bwelcome_guest.setForeground(SystemColor.inactiveCaptionBorder);
		bwelcome_guest.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		bwelcome_guest.setBackground(new Color(241,57,83));
		bwelcome_guest.setBounds(187, 443, 143, 32);
		panel.add(bwelcome_guest);
		
		bwelcome_signin = new JButton("Log in");
		bwelcome_signin.setForeground(SystemColor.inactiveCaptionBorder);
		bwelcome_signin.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		bwelcome_signin.setBackground(new Color(241,57,83));
		bwelcome_signin.setBounds(398, 443, 175, 32);
		panel.add(bwelcome_signin);
		
		bwelcome_creataccount = new JButton("Sign up");
		bwelcome_creataccount.setForeground(SystemColor.inactiveCaptionBorder);
		bwelcome_creataccount.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		bwelcome_creataccount.setBackground(new Color(241,57,83));
		bwelcome_creataccount.setBounds(639, 443, 143, 32);
		panel.add(bwelcome_creataccount);
		
		JLabel lblWelcome = new JLabel("Welcome to");
		lblWelcome.setForeground(Color.BLACK);
		lblWelcome.setFont(new Font("Viner Hand ITC", Font.BOLD, 64));
		lblWelcome.setBounds(282, 144, 457, 139);
		panel.add(lblWelcome);
		
		JLabel lblHotel = new JLabel("Hotel Booking System");
		lblHotel.setFont(new Font("Viner Hand ITC", Font.BOLD, 64));
		lblHotel.setBounds(81, 247, 938, 114);
		panel.add(lblHotel);
		
		bwelcome_x = new JLabel("  X");
		bwelcome_x.setFont(new Font("Arial", Font.BOLD, 25));
		bwelcome_x.setBounds(935, 0, 49, 32);
		bwelcome_x.setForeground(SystemColor.textHighlightText);
		panel.add(bwelcome_x);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Welcome.class.getResource("/images/white.png")));
		lblNewLabel.setBounds(0, 146, 984, 219);
		panel.add(lblNewLabel);
		
		JLabel image = new JLabel("Image");
		image.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
		image.setBounds(0, -425, 2311, 1330);
		panel.add(image);
		image.setIcon(new ImageIcon(SignIn.class.getResource("/images/101white.png")));
	}
}
