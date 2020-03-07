import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class Client
{
	private static final String CONFIG = "./ClientConfig.txt";
	private Socket socket;
	private ObjectInputStream inputObject;
	private ObjectOutputStream outputObject;
	private Packet packet;
	private GUIThread user;
	
	public static void main(String args[]) {
		Client client = new Client(CONFIG);
		
		Thread sendMessage = new Thread(new Runnable() { 
			public void run() { 
				Query query = null;
				try {
					do {
						query = client.getQuery();
						client.writeSocket(query);
					} while (!query.quit());
				} catch (IOException e) { 
                    e.printStackTrace();
                    client.close("Disconnected with server");
                }
                client.close();
				System.exit(0);
			}
		});
		
		Thread readMessage = new Thread(new Runnable() { 
			public void run() { 
				Response response = null;
				try {
					while (true) { 
						response = client.readSocket();
						client.setResponse(response);
					}
				} catch (IOException e) { 
                    e.printStackTrace();
                    client.close("Disconnected with server");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    client.close("Unexpected error");
                }
                client.close();
				System.exit(0);
			} 
		});
		
		try {
			sendMessage.start();
			readMessage.start();
		} catch (Exception e) {
			e.printStackTrace();
			client.close("Unexpected error");
		}
	}
	
	public Client(String configuration) {
		String serverAddress = null;
		String port = null;
		
		try {
			InputStream inputStream = getClass().getResourceAsStream(configuration); 
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = bufferReader.readLine();
			while (line != null) {
				StringTokenizer tokens = new StringTokenizer(line);
				if (tokens.hasMoreTokens() == false)
					continue;
				switch (tokens.nextToken()) {
					default:
						continue;
					case "ServerAddress":
						tokens.nextToken();
						serverAddress = tokens.nextToken();
						break;
					case "Port":
						tokens.nextToken();
						port = tokens.nextToken();
				}
				line = bufferReader.readLine();
			}
			bufferReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			this.close("Can't read client configuration");
		}

		if (serverAddress == null || port == null) {
			this.close("Unknown client configuration");
		}
		
		try {
			socket = new Socket(serverAddress, Integer.parseInt(port));
			outputObject = new ObjectOutputStream(socket.getOutputStream());
			inputObject = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			this.close("Connection to server failed");
		}
		
		try {
			this.packet = new Packet();
			this.user = new GUIThread(packet);
			this.user.start();
		} catch (Exception e) {
			this.close("Fail to open GUI");
			e.printStackTrace();
		}
	}
	
	public Response readSocket() throws IOException, ClassNotFoundException {
		return (Response)inputObject.readObject();
	}
	
	public void writeSocket(Query query) throws IOException {
		outputObject.writeObject(query);
		outputObject.flush();
	}
	
	public Query getQuery() {
		return packet.getQuery();
	}
	
	public void setResponse(Response response) {
		if (response.type == QueryType.UPDATE_RESERVATION){
			user.updateReservation(response);
		} else if (response.type == QueryType.UPDATE_ORDER){
			user.updateReservation(response);
		} else {
			packet.setResponse(response);
		}
	}
	
	public void close() {
		try {
			if (inputObject != null) {
				inputObject.close();
			}
			if (outputObject != null) {
				outputObject.close();
			}
			if (socket != null) {
				socket.close();
			}
			System.out.println("Connection closed");
		} catch (IOException e) {
			System.err.println("Socket close error");
		}
	}
	
	public void close(String errMessage) {
		JOptionPane.showMessageDialog(null, errMessage, "Error", JOptionPane.WARNING_MESSAGE);
		close();
		System.exit(0);
	}
}
