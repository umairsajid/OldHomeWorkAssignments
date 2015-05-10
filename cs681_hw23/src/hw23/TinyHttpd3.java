package hw23;
 

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.File;

public class TinyHttpd3 extends TinyHttpd2 {


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
					RequestHandler req = new RequestHandler(client);
					Thread t = new Thread(req);
					t.start();
				}
			}finally {
				serverSocket.close();
			}
		}
		catch(IOException exception){
			exception.printStackTrace();
		}
	}

private class RequestHandler implements Runnable 
{
	private Socket client;
	public RequestHandler(Socket cli){
		client = cli;
	}
	private void executeCommand( Socket client ){
		try {
			try {
				client.setSoTimeout(30000);
				BufferedReader in = new BufferedReader( new InputStreamReader( client.getInputStream() ) );  
				PrintStream out = new PrintStream( client.getOutputStream() );  
				System.out.println( "I/O setup done" );
				File file = null;
				String line = in.readLine();
				if(line.startsWith("GET") || line.startsWith("HEAD") )
				{	
					
					if(checkSyntax(line)>0)	
					{
						file = new File(parseRequest(line));
						while ( in.ready() && line != null ) {
								
								 
									System.out.println(line);
									line = in.readLine();
								 
							
						}
						System.out.println(line);
						System.out.println(file.getName() + " requested.");
						 
							if(file.exists())
								sendFile(out, file, client);
							else
								sendErrorMessage(out,2);
						 
					}
					else{
						sendErrorMessage(out,1);
					}
						
				}
				else{
					sendErrorMessage(out,0);
				}
				out.flush();
				out.close();
				in.close();
			}finally {
				client.close();
				System.out.println( "A connection is closed." );				
			}
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	private String parseRequest(String line) {
		if(line.startsWith("GET") )
			return line.substring(5, line.indexOf("HTTP"));
		else
			return line.substring(6, line.indexOf("HTTP"));
	} 
	private int checkSyntax(String line) {
		String trim = removeSpaces(line);
		if(trim.startsWith("GET") ){
			if(trim.charAt(3) == '/')
					return 1;
			
			}
		else{
				if(trim.charAt(4) == '/')
					return 1;
				}
			return -1;
		}
	private void sendErrorMessage(PrintStream out, int flag){
		switch(flag){
			case 0:
			{
				out.println("HTTP/1.0 501 Not Implemented");
				out.flush();
			} 
			case 1:
			{
				out.println("HTTP/1.0 400 Bad Request");
				out.flush();
			} 
			case 2:
			{
				out.println("HTTP/1.0 404 Not found");
				out.flush();
			} 
			
		}
	}
	
	private void sendFile(PrintStream out, File file, Socket client){
		try{
			Date now = new Date();
			out.println("HTTP/1.0 200 OK");
			out.println("Date: " + now.toString());
			out.println("Server: " + client.getInetAddress().toString());
			int len = (int) file.length();
			out.println("Content-Length: " + len);
			out.println("Content-Type: text/html");
			Date lastmod = new Date(file.lastModified());
			out.println("Last Modified: " + lastmod.toString());
			
			DataInputStream fin = new DataInputStream(new FileInputStream(file));
			byte buf[] = new byte[len];
			fin.readFully(buf);
			out.write(buf, 0, len);
			out.flush();
			fin.close();
		}catch(IOException exception){
			exception.printStackTrace();
		}         
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		executeCommand( client );
	}
}
	public static void main(String[] args) {
		TinyHttpd3 server = new TinyHttpd3();
		server.init();
	}
	public String removeSpaces(String s) {
		  StringTokenizer st = new StringTokenizer(s," ",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
		}

}
