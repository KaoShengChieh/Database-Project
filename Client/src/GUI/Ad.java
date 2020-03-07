import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Ad extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private JLabel textField;
	private int count = 5;
	
	public Ad(Frame owner, int adcode) {
		super(owner, "Ad");
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setUndecorated(true);
		setBounds(100, 100, 984, 603);
		getContentPane().setLayout(null);
		
		textField = new JLabel();
		textField.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		textField.setText("5");
		textField.setForeground(Color.RED);
		textField.setBackground(Color.BLACK);
		textField.setBounds(960, 580, 36, 21);
		getContentPane().add(textField);

		JLabel lblad = new JLabel("");
		lblad.setBounds(0, 0, 984, 603);
		lblad.setIcon(new ImageIcon(Ad.class.getResource("/images/" + adcode + ".jpg")));
		
		getContentPane().add(lblad);
		
	}
	
	public void start() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (count == 0) {
					dispose();
				} else {
					textField.setText("" + count + "");
					count--;
				}
			}
		});
		timer.setInitialDelay(0);
		timer.start();
		setVisible(true);	
	}
}