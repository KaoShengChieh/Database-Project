import java.awt.Font;
import java.awt.Frame;
import java.awt.Dialog;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class ResultPopUp extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	private JLabel lblNewLabel;
	private final int BottonHeight = 20;
	private boolean addBotton;
	JButton btnNewButton;
	public void show(String message) {
		if (message == null) {
			message = "error";
		}
		
		int length = 0;
		int maxLength = 0;
		int lineCount = 1;
		
		
		if (addBotton)message += "\n\n";
		
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
		
		this.setSize(422>=maxLength*13?422:maxLength*13, (addBotton?BottonHeight:0)+(107>=lineCount*25+45?107:lineCount*25+45));
		
		this.setVisible(true);
	}

	public ResultPopUp(Frame owner, boolean model) {
		
		super(owner, "ResultPopUp");
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(420, 270, 422, 107);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblNewLabel = new JLabel("123456789012345678901234567890");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
		contentPane.add(lblNewLabel);
		
		if(model == false) {
			addBotton =true;
			btnNewButton = new JButton("Upgrade VIP now!! Just for 100NT doller");
			btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
			contentPane.add(btnNewButton);
		}
		
	}
}
