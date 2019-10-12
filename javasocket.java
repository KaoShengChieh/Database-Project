import java.net.*;
import java.io.*;

public class javasocket {
    public void run() throws Exception {
        String adrress = "127.0.0.1";
        Socket socket = new Socket(adrress, 5000);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
        streamWriter.write("HELLO TO SERVER FROM CLIENT");
        streamWriter.write("哈哈");
        streamWriter.flush();
        
        InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferReader = new BufferedReader(streamReader);

        String MESSAGE = bufferReader.readLine();
        System.out.println(MESSAGE + "java");
    }

	public static void main(String[] args) throws Exception {
		new javasocket().run();
	}
}
