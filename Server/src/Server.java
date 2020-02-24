import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class Server
{
	private static final String CONFIG = "./ServerConfig.txt";
	private ServerSocket serverSocket;
	private HotelManager hotelManager;
	private List<ThreadInfo> currentUser;
	
	public static void main(String[] args) {
		Server server = new Server(CONFIG);
		
		System.out.println("Server listening......");

		while (true) {
			try {
				ServerThread serverThread = server.accept();
				serverThread.start();
				System.out.println("Connection with " + serverThread.getName() + " established");
			} catch (Exception e) {
				System.out.println("Connection error");
				e.printStackTrace();
				server.close();
			}
		}
	}
	
	public Server(String configuration) {
		String port = null;
		currentUser = Collections.synchronizedList(new LinkedList<ThreadInfo>());
		
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
					case "Port":
						tokens.nextToken();
						port = tokens.nextToken();
				}
				line = bufferReader.readLine();
			}
			bufferReader.close();
		} catch (IOException e) {
			System.out.println("Can't read server configuration");
			System.exit(0);
		}
			
		if (port == null) {
			System.out.println("Unknown server configuration");
			System.exit(0);
		}
		
		try {
			serverSocket = new ServerSocket(Integer.parseInt(port));
			hotelManager = new HotelManager();
		} catch (IOException e) {
			System.out.println("Server start up error");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public ServerThread accept() throws IOException {
		Socket socket = serverSocket.accept();
		ThreadInfo threadInfo = new ThreadInfo();
		currentUser.add(threadInfo);
		return new ServerThread(socket, socket.getRemoteSocketAddress().toString(), threadInfo);
	}
	
	public void close() {
		try {
			serverSocket.close();
			System.out.println("Server closed");
		} catch (IOException e) {
			System.out.println("Server socket close error");
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
	private class ServerThread extends Thread {
		private Socket socket;
		private ObjectInputStream inputObject;
		private ObjectOutputStream outputObject;
		private Query query;
		private Response response;
		private boolean hasLogin;
		private ThreadInfo myThreadInfo;
		private MVCThread myMVCThread;

		public ServerThread(Socket socket, String remoteSocketAddress, ThreadInfo threadInfo) {
			super(remoteSocketAddress);
			this.socket = socket;
			this.myThreadInfo = threadInfo;
			
			try {
				inputObject = new ObjectInputStream(socket.getInputStream());
				outputObject = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("IO error in server thread");
			}
			
			myMVCThread = new MVCThread(outputObject, threadInfo);
		}
		
		public void run() {
			try {
				myMVCThread.start();
				query = (Query)inputObject.readObject();
				printQuery(query);
				while (!query.quit()) {
					if (query.type == QueryType.SIGNUP) {
						response = hotelManager.signUp(query);
						if (response.isSuccess)
							hasLogin = true;
					} else if (query.type == QueryType.LOGIN) {
						response = hotelManager.logIn(query, myThreadInfo);
						if (response.isSuccess) {
							hasLogin = true;
							myThreadInfo.userName = new StringTokenizer(query.userName, "/").nextToken();
						}
					} else if (hasLogin) {
						response = hotelManager.handleQuery(query);
					} else {
						response.setErrorMessage("Has not logged in yet");
					}
					printReponse(response);
					outputObject.writeObject(response);
					outputObject.flush();
					prepareUpdateQuery(response);
					query = (Query)inputObject.readObject();
					printQuery(query);
				}   
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.out.println("IO error/ Client " + this.getName() + " terminated abruptly");
			} catch (NullPointerException e) {
				// Do nothing
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Fail to Serialized/Deserialized");
			} finally {    
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
					System.out.println("Client " + this.getName() + " closed");
				} catch (IOException ie) {
					System.out.println("Socket close error");
				} finally {
					currentUser.remove(myThreadInfo);
				}
			}
		}
		
		private void prepareUpdateQuery(Response response) {
			synchronized (currentUser) {
				ListIterator<ThreadInfo> itr = currentUser.listIterator();
				ThreadInfo threadInfo;
				
				if (response.result != null
					&& response.result.type == QueryType.BOOK
					&& response.isSuccess) {
					while (itr.hasNext()) {
						threadInfo = itr.next();
						if (threadInfo.userName.equals(myThreadInfo.userName)) {
							threadInfo.update = true;
						}
					}
					currentUser.notifyAll();
				}
			}
		}
	}
	
	private class MVCThread extends Thread {
		private ThreadInfo myThreadInfo;
		private ObjectOutputStream outputObject;
		
		public MVCThread(ObjectOutputStream outputObject, ThreadInfo threadInfo) {
			this.outputObject = outputObject;
			this.myThreadInfo = threadInfo;
		}
		
		public void run() {
			while (true) {
				sendUpdate();
			}
		}
		
		private void sendUpdate() {
			synchronized (currentUser) {
				while (!myThreadInfo.update) {
					try {
						currentUser.wait();
					} catch (InterruptedException e)  {
						Thread.currentThread().interrupt(); 
					}
				}
				
				Query query = new Query();
				query.type = QueryType.LISTORDER;
				query.userName = myThreadInfo.userName;
				Response update = hotelManager.handleQuery(query);
				printReponse(update);
				update.result = query;
				query.type = QueryType.UPDATE;
				try {
					outputObject.writeObject(update);
					outputObject.flush();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					System.out.println("IO error/ Client " + this.getName() + " terminated abruptly");
				}
			
				myThreadInfo.update = false;
			}
		}
	}
		
	private void printQuery(Query query) {
		System.out.println("[type] " + query.type.toString() +
			" [username] " + query.userName +
			" [bookID] " + query.bookID +
			" [hotelID] " + query.hotelID +
			" [roomNum] " + Arrays.toString(query.roomNum) +
			" [checkin] " + query.checkin +
			" [checkout] " + query.checkout);
	}		
		
	private void printReponse(Response response) {
		System.out.println("[isSuccess] " + response.isSuccess +
			(response.isSuccess ? "" : " [errorMessage] " + response.getErrorMessage()));
	}
}
