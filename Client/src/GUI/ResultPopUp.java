import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class ResultPopUp extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	
	public void show(String message) {
		int length = 0;
		int maxLength = 0;
		int lineCount = 1;
		
		for (int i = 0; i < message.length(); i++) {
			length++;
			if (message.charAt(i) == '\n' || i == message.length() - 1) {
				if (length > maxLength) {
					maxLength = length;
				}
				lineCount++;
				length = 4;
			}
		}
	
		message = "<html>" + message + "</html>";
	 	message = message.replace("\n", "<br/>").replace(" ", "&nbsp;");
		lblNewLabel.setText(message);
		
		this.setSize(422>=maxLength*13?422:maxLength*13, 107>=lineCount*25+45?107:lineCount*25+45);
		this.setVisible(true);
	}

	public ResultPopUp() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(420, 270, 422, 107);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblNewLabel = new JLabel("123456789012345678901234567890");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		contentPane.add(lblNewLabel);
	}
}
