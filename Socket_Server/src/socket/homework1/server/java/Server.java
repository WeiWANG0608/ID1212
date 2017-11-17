package socket.homework1.server.java;
import java.net.*;

public class Server {

	public static void main(String[] args) {
		try{
			ServerSocket server = new ServerSocket(20006);
			Socket client = null;
			boolean f = true;
			while(f){
				//waiting for the connection
				client = server.accept();
				String ip = client.getInetAddress().getHostAddress();
				System.out.println(ip + "TCP Connected !");  
		        //Open a thread for each client
				new Thread(new ServerThread(client)).start(); 
				 }
			server.close();
			} catch (Exception e) {
				e.printStackTrace();
				}
		}
	}




