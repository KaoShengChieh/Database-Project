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

class ThreadInfo {
	boolean updateReservation;
	boolean updateOrder;
	MemberInfo memberInfo;
}

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
					if (query.type == QueryType.SIGN_UP) {
						response = hotelManager.signUp(query);
						if (response.isSuccessful)
							hasLogin = true;
					} else if (query.type == QueryType.LOG_IN) {
						MemberInfo memberInfo = new MemberInfo();
						response = hotelManager.logIn(query, memberInfo);
						if (response.isSuccessful) {
							hasLogin = true;
							myThreadInfo.memberInfo = memberInfo;
						}
					} else if (hasLogin) {
						response = hotelManager.handleQuery(myThreadInfo.memberInfo, query);
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
				
				if (response.isSuccessful == false) {
					return;
				}			
					
				if (response.type == QueryType.BOOK
					|| response.type == QueryType.MODIFY
					|| response.type == QueryType.CANCEL) {
					while (itr.hasNext()) {
						threadInfo = itr.next();
						if (threadInfo.memberInfo.memberID == myThreadInfo.memberInfo.memberID) {
							threadInfo.updateReservation = true;
						}
					}
					currentUser.notifyAll();
				} else if (response.type == QueryType.PAY) {
					while (itr.hasNext()) {
						threadInfo = itr.next();
						if (threadInfo.memberInfo.memberID == myThreadInfo.memberInfo.memberID) {
							threadInfo.updateOrder = true;
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
				while (!myThreadInfo.updateReservation && !myThreadInfo.updateOrder) {
					try {
						currentUser.wait();
					} catch (InterruptedException e)  {
						Thread.currentThread().interrupt(); 
					}
				}
				
				Response update = hotelManager.handleQuery(myThreadInfo.memberInfo, new Query(QueryType.LIST_RESERVATION));
				printReponse(update);
				update.type = QueryType.UPDATE_RESERVATION;
				try {
					outputObject.writeObject(update);
					outputObject.flush();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					System.out.println("IO error/ Client " + this.getName() + " terminated abruptly");
				}
				
				myThreadInfo.updateReservation = false;
				
				if (myThreadInfo.updateOrder) {
					update = hotelManager.handleQuery(myThreadInfo.memberInfo, new Query(QueryType.LIST_ORDER));
					printReponse(update);
					update.type = QueryType.UPDATE_ORDER;
					try {
						outputObject.writeObject(update);
						outputObject.flush();
					} catch (IOException e) {
						System.out.println(e.getMessage());
						System.out.println("IO error/ Client " + this.getName() + " terminated abruptly");
					}
					
					myThreadInfo.updateOrder = false;
				}
			}
		}
	}
		
	private void printQuery(Query query) {
		System.out.print("[type] " + query.type.toString() +
			" [additionalInfo] " + query.additionalInfo);
		
		switch (query.type) {
			case SIGN_UP: case LOG_IN:
				System.out.print(
					" [username] " + query.userInfo.userName +
					" [realName] " + query.userInfo.realName +
					" [password] " + query.userInfo.password +
					" [email] " + query.userInfo.email +
					" [membership] " + query.userInfo.membership);
				break;
			case LIST_HOTEL: case BOOK: case MODIFY:
				System.out.print(
					" [bookID] " + query.orderInfo.bookID +
					" [hotelID] " + query.orderInfo.hotelID +
					" [star] " + query.orderInfo.star +
					" [city] " + query.orderInfo.city +
					" [address] " + query.orderInfo.address +
					" [roomNum] " + Arrays.toString(query.orderInfo.roomNum) +
					" [checkin] " + query.orderInfo.checkin +
					" [checkout] " + query.orderInfo.checkout +
					" [price] " + query.orderInfo.price +
					" [additionalInfo] " + query.orderInfo.additionalInfo);
				break;
			case PAY:
				System.out.print(
					" [CS_ID] " + query.creditCardInfo.CS_ID +
					" [number] " + query.creditCardInfo.number +
					" [expirationDate] " + query.creditCardInfo.expirationDate +
					" [securityCode] " + query.creditCardInfo.securityCode +
					" [ownerName] " + query.creditCardInfo.ownerName);
				break;
			default:
				break;
		}
		
		System.out.println("");
	}		
		
	private void printReponse(Response response) {
		System.out.println("[isSuccess] " + response.isSuccessful +
			(response.isSuccessful ? "" : " [errorMessage] " + response.getErrorMessage()));
	}
}
