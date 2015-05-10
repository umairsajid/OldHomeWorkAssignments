package hw25;
 

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.File;

public class TinyHttpd4 extends TinyHttpd2 {


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
					try {
						Thread.sleep(1000);
						t.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

@SuppressWarnings("serial")
private class RequestHandler implements Runnable 
{
	Boolean keepAlive = false;
	InputStream i;
    int requestFlag =-1; //0 GET, 1 HEAD, 2 PUT, 3 POST, 4 DELETE
 	private final Hashtable< String,Integer > req = new Hashtable<String,Integer>()
			{{  put("GET",new Integer(0) );     
				put("HEAD",new Integer(1) );
				put("PUT",new Integer(2)  );  
				put("POST",new Integer(3)  );
				put("DELETE",new Integer(4)  );  }}; 
				
    private Socket client;
	public RequestHandler(Socket cli){
		System.out.println("New Connection");
		client = cli;
		try {
			i = client.getInputStream();
		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void executeCommand( Socket client, Boolean nested ){
		try {
			try {
				client.setSoTimeout(30000);
				BufferedReader in = new BufferedReader( new InputStreamReader( i ) );  
				PrintStream out = new PrintStream( client.getOutputStream() );  
				System.out.println( "I/O setup done" );
				String line="";
				if(in.ready())
					  line = in.readLine();
			 	 
				if(line.startsWith("GET") || line.startsWith("HEAD")|| line.startsWith("POST") || 
				   line.startsWith("PUT") || line.startsWith("DELETE"))
				{	
					
					if(checkSyntax(line)>0)	
					{
						handleRequest(line,requestFlag,out, in);
												 
					}
					else{
						sendErrorMessage(out,1);
					}
						
				}
				else if (line.equals("")){
					System.out.println("last message");
				}
				else{
					sendErrorMessage(out,0);
				}
				out.flush();
				out.close();
				in.close();
			}finally {
				if(!nested)
					{
						client.close();
						System.out.println( "A connection is closed." );
					}
			}
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	private void handleRequest(String line, int requestFlag2, PrintStream out, BufferedReader in) {
		StringBuffer httprequest = new StringBuffer();
 		File file = new File(parseRequest(line));
 		try {
			while ( in.ready() && line != null ) {
				System.out.println(line);
				line = in.readLine();
				if (line !=null)httprequest.append(line +"\r\n");
			}
 		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		if(CheckHeaders(httprequest.toString()))	
		{
 
		switch(requestFlag)
		{	
			
				case 0:
				{
					
						if(file.exists())
							sendFile(out, file, client,0, keepAlive);
						else
							sendErrorMessage(out,2);
					break;
				}
				case 1:
				{
					   if(file.exists())
							sendFile(out, file, client,0, keepAlive);
						else
							sendErrorMessage(out,2);
						
						break;
				}
				case 2:
				{
						int index = httprequest.indexOf("Content-Length");
						String stage =  httprequest.substring(index).toString();
						int index2 = stage.indexOf("\r\n\r\n") +4;
						String request = stage.substring(index2);
						StringBuffer filedata = new StringBuffer();
						filedata.append(request);
						if(file.exists()){
							 
							writeToFile(file.getAbsolutePath(),filedata.toString());
							sendFile(out, null, client,1, keepAlive);
							}
						else
						{
							try {
								file.createNewFile();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							writeToFile(file.getAbsolutePath(),filedata.toString());
							sendFile(out, null, client,2, keepAlive);
						}
						
						break;		 
				}
				case 3:
				{
				
					if(file.exists())
						sendFile(out, file, client, 1, keepAlive);
					else
						sendErrorMessage(out,2);
				
					break;
				}
				case 4:
				{
					if(file.exists()){
						file.delete();
						sendFile(out, file, client,1, keepAlive);
						}
					else{
						sendErrorMessage(out,2);
					}
					break;
				}
				
			}
		 }
		else{
			sendErrorMessage(out,2);
		} 
 
	}
	
	private boolean CheckHeaders(String httprequest) {
		String request[];
		String auth = null;
		String connection = "";
		request = httprequest.split("\r\n");			
		for(int k=0; k<request.length;k++){
			if(request[k].startsWith("Authorization")) auth = new String(request[k].toString());
			if(request[k].startsWith("Connection")) connection = new String(request[k].toString());
			
		}
		
		
		if(connection.indexOf("Keep-Alive") >= 0) keepAlive = true;
		if(connection.indexOf("Closed") >= 0) keepAlive =false;
		
		
		if(auth == null)
			return false;
		else{
			int idx =	auth.indexOf("Basic");
			idx = (idx < 0) ? 14:idx + 5;
			String credential = auth.substring(idx).trim();
			String	d = new String(Base64Coder.decodeString(credential));
			if(d.equals("jadd:jadd"))return true;
			else return false;
		}
			
	}
	public boolean writeToFile(String fileName, String dataLine) {
		DataOutputStream dos;

	

		    try {
		      File outFile = new File(fileName);
		      	dos = new DataOutputStream(new FileOutputStream(outFile));
		   

		      dos.writeBytes(dataLine);
		      dos.close();
		    } catch (FileNotFoundException ex) {
		      return (false);
		    } catch (IOException ex) {
		      return (false);
		    }
		    return (true);

		  }


	private String parseRequest(String line) {
		if(line.startsWith("GET")){
			return line.substring(5, line.indexOf("HTTP"));
		}
		else if(line.startsWith("HEAD")){
			return line.substring(6, line.indexOf("HTTP"));
		}
		else if(line.startsWith("PUT")){
			return line.substring(5, line.indexOf("HTTP"));
		}
		else if(line.startsWith("POST")){
			return line.substring(6, line.indexOf("HTTP"));
		}
		else if(line.startsWith("DELETE")){
		 	return line.substring(8, line.indexOf("HTTP"));
		}
	 	return line;
			
	} 
	
	private int checkSyntax(String line) {
		if(line.startsWith("GET")){
			requestFlag = req.get("GET");
		}
		else if(line.startsWith("HEAD")){
			requestFlag = req.get("HEAD");
		}
		else if(line.startsWith("PUT")){
			requestFlag = req.get("PUT");
		}
		else if(line.startsWith("POST")){
			requestFlag = req.get("POST");
		}
		else if(line.startsWith("DELETE")){
			requestFlag = req.get("DELETE");
		}
	 	String trim = removeSpaces(line);
		switch(requestFlag){
			case 0:{
				if(trim.charAt(3) == '/'){
					if(trim.charAt(trim.length()-1) =='1') keepAlive = true;
					return 1;
				}
				 break;
	
			}
			case 1:{
				if(trim.charAt(4) == '/')
				{
					if(trim.charAt(trim.length()-1) =='1') keepAlive = true;
					return 1;
				}
				 break;

			}
			case 2:{
				if(trim.charAt(3) == '/')
					return 1;
				 break;

			}
			case 3:{
				if(trim.charAt(4) == '/')
					return 1;
				 break;

			}
			case 4:{
				if(trim.charAt(6) == '/')
					return 1; 
				break;

			}
			
		}
			return -1;
		}
	
	private void sendErrorMessage(PrintStream out, int flag){
		switch(flag){
			case 0:
			{
				out.println("HTTP/1.0 501 Not Implemented");
				out.flush();
				 break;

			} 
			case 1:
			{
				out.println("HTTP/1.0 400 Bad Request");
				out.flush();
				 break;

			} 
			case 2:
			{
				out.println("HTTP/1.0 401 Not Authorized");
				out.println("WWW-Authenticate: Basic realm: \"protected area\"");
				out.flush();
				 break;

			} 
			case 3:
			{
				out.println("HTTP/1.0 404 Not found");
				out.flush();
				 break;

			} 
			default :{
				
			}
		}
	}
	
	private void sendFile(PrintStream out, File file, Socket client, int fl, boolean keepalive){
		switch(fl){
			case 0:
			{
				 try{
					 StringBuffer sb = new StringBuffer();
					Date now = new Date();
					out.println("HTTP/1.1 100 Continue");
					sb.append("HTTP/1.1 100 Continue\r\n");
				
					int len = (int) file.length();
					
					out.println("Content-Length: " + len);
					sb.append("Content-Length: " + len);
					
					if (file.getName().trim().toLowerCase().endsWith(".jpg")){
						System.out.println(file.getName() + " " + file.getName().length() + " " + len);
						System.out.println("jpg");
						out.println("Content-Type: image/jpeg");
						sb.append("\r\nContent-Type: image/jpeg\r\n");
						}
					else {
						System.out.println(file.getName() + " " + file.getName().length() + " " + len);
						System.out.println("html");
						out.println("Content-Type: text/html");
						sb.append("\r\nContent-Type: text/html\r\n");
					 }
					
					Date lastmod = new Date(file.lastModified());
					out.println("Last-Modified: " + lastmod.toString());
					out.println("Date: " + now.toString());	
					sb.append("Last-Modified: " + lastmod.toString());
					sb.append("\r\nDate: " + now.toString());
					out.println("Connection: Keep-Alive");
					sb.append("\r\nConnection: Keep-Alive\r\n");
					out.println("Server: " + client.getInetAddress().toString());
					sb.append("Server: " + client.getInetAddress().toString());
			
					out.println("");
					DataInputStream fin = new DataInputStream(new FileInputStream(file.getAbsolutePath()));
					byte buf[] = new byte[len];
					fin.readFully(buf);
					out.write(buf, 0, len);
					System.out.println(sb.toString());
					out.flush();
					fin.close();
			 
					if(keepalive){
			  
							executeCommand(client, true);
						}
									
				}
				 catch(IOException exception){
					exception.printStackTrace();
				}
				 break;

			}
			case 1:
			{
				 
					Date now = new Date();
					out.println("HTTP/1.1 200 OK");
					out.println("Date: " + now.toString());
					out.println("Server: " + client.getInetAddress().toString());
					int len = (int) file.length();
					out.println("Content-Length: " + len);
					out.println("Content-Type: text/html");
					Date lastmod = new Date(file.lastModified());
					out.println("Last Modified: " + lastmod.toString());
					 break;
								 
			}
			case 2:
			{
				System.out.println(fl);
				Date now = new Date();
				out.println("HTTP/1.1 201 Created");
				out.println("Date: " + now.toString());
				out.println("Server: " + client.getInetAddress().toString());
				int len = (int) file.length();
				out.println("Content-Length: " + len);
				out.println("Content-Type: text/html");
				Date lastmod = new Date(file.lastModified());
				out.println("Last Modified: " + lastmod.toString());
				 break;

			}
			case 3:
			{
				System.out.println(fl);
				Date now = new Date();
				out.println("HTTP/1.1 204 No Content");
				out.println("Date: " + now.toString());
				out.println("Server: " + client.getInetAddress().toString());
				int len = (int) file.length();
				out.println("Content-Length: " + len);
				out.println("Content-Type: text/html");
				Date lastmod = new Date(file.lastModified());
				out.println("Last Modified: " + lastmod.toString());
				 break;

			}
			 
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		executeCommand( client, false );
	}
	
}
	public static void main(String[] args) {
		TinyHttpd4 server = new TinyHttpd4();
		server.init();
	}
	public String removeSpaces(String s) {
		  StringTokenizer st = new StringTokenizer(s," ",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
		}

}
