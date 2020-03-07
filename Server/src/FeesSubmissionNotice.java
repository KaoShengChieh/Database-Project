import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

public class FeesSubmissionNotice {
	private static final String subject = "Fees Submission Notice";
	private static final String officalGmailAccount = "onlinehotelbookingsystem2@gmail.com";
	private static final String officalGmailPassword = "OOADFinalProject";
	private String senderName;
	private String senderAddress;
	private String receiverName;
	private String receiverAddress;
	private String smtpHostServer;
	private String content;
	private boolean SSLAuthentication;

	public static void main(String[] args) {
		FeesSubmissionNotice notice = new FeesSubmissionNotice("Citi Bank");
		notice.setReceiver("Ricky Kao", "b06902117@gmail.com");
		notice.setFees(3000);
		notice.send();
	}

	public FeesSubmissionNotice(String senderName) {
		this.senderName = senderName;

		String domain = senderName.replaceAll("\\s+","").toLowerCase() + ".com.tw";
		String sender = "credit_card_division";

		this.senderAddress = sender + "@" + domain;
	}

	public void setReceiver(String receiverName, String receiverAddress) {
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;

		StringTokenizer tokens = new StringTokenizer(receiverAddress, "@");

		tokens.nextToken();
		String domain = tokens.nextToken();
		
		switch (domain) {
			case "gmail.com":
				smtpHostServer = "smtp.gmail.com";
				SSLAuthentication = true;
				break;
			case "ntu.edu.tw":
				smtpHostServer = "mgw.ntu.edu.tw";
				break;
			default:
				smtpHostServer = "smtp." + domain;
				SSLAuthentication = true;
		}
	}

	public void setFees(int Fees) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  

		if (SSLAuthentication) {
			senderName = "Online Hotel Booking System";
		}

		content = "Dear " + receiverName + ":\n\n" + 
		"We've receive your fees submission via credit card " +
		"at " + formatter.format(now) + ".\n" +
		"The total fees are NT" + Fees + ".\n" +
		"If you have any problem about this tranction, " +
		"please contact us immediately.\n\n" +
		"Our gratitude.\n\n" +
		"Sincerely,\n" + senderName;
	}

	public void send() {
		if (SSLAuthentication) {
			sendWithAuthentication();
		} else {
			sendWithoutAuthentication();
		}
	}

	private void sendWithoutAuthentication() {
		System.out.println("Start SMTP service");

		Properties props = System.getProperties();

		props.put("mail.smtp.host", smtpHostServer);

		Session session = Session.getInstance(props, null);

		try {
			MimeMessage msg = new MimeMessage(session);

			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(senderAddress, senderName));

			msg.setSubject(subject, "UTF-8");

			msg.setText(content, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAddress, false));

			System.out.println("Message is ready");

			Transport.send(msg);  

			System.out.println("Email Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendWithAuthentication() {
		System.out.println("SSLEmail Start");

		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHostServer);
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(officalGmailAccount, officalGmailPassword);
			}
		};

		Session session = Session.getDefaultInstance(props, auth);

		try {
			MimeMessage msg = new MimeMessage(session);

			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(senderAddress, senderName));

			msg.setSubject(subject, "UTF-8");

			msg.setText(content, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAddress, false));

			System.out.println("Message is ready");

			Transport.send(msg);  

			System.out.println("Email Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
