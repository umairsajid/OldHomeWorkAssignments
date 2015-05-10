package hw26;
 

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TinyHttp {
	protected static final int PORT = 8888;
	protected ServerSocket serverSocket;

	public void init() {
		try {
		
			try {
				serverSocket = new ServerSocket(PORT);
				System.out.println("Socket created.");
				 
				
				while(true) {	
					System.out.println( "Listening to a connection on the local port " +
										serverSocket.getLocalPort() + "..." );
					Socket client = serverSocket.accept();
					System.out.println( "\nA connection established with the remote port " + 
										client.getPort() + " at " +
										client.getInetAddress().toString() );
					HttpExchange h = new HttpExchange(serverSocket, client); 
					client.close();
					//HttpHandler req = new HttpHandler(client);
					//Thread t = new Thread(req);
					//t.start();
					/*try {
						Thread.sleep(1000);
					//	t.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
			}
			finally {
				serverSocket.close();
			}
		}
		catch(IOException exception){
			exception.printStackTrace();
		}
	}

 
	
public static void main(String[] args) {
		TinyHttp server = new TinyHttp();
		server.init();
	}
	

}
