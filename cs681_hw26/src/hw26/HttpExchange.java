package hw26;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

@SuppressWarnings("serial")
public class HttpExchange {
	private   Hashtable< String,String > requestHeaders = new Hashtable<String,String>();
	BufferedReader in; 
	ServerSocket serverSocket;
	Socket client;
	PrintStream out;
	
	public String requestCommand;
	public String requestProtocolName;
	public String requestProtocolVersion;
	public String requestCommandType;
	public String requestURI; 

	private   Hashtable< String,String > responseHeaders = new Hashtable<String,String>();

	
	private InputStream responseBody;
	private int responseBodyLength =-1;
 
	private final Hashtable< String,Integer > commandKeys = new Hashtable<String,Integer>()
	{{  put("GET",new Integer(0) );     
		put("HEAD",new Integer(1) );
		put("PUT",new Integer(2)  );  
		put("POST",new Integer(3)  );
		put("DELETE",new Integer(4)  );  }};
	private File file; 
		
	public HttpExchange(ServerSocket sk, Socket cl){
		serverSocket = sk;
		client = cl;
		try {
			in = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
			out = new PrintStream( client.getOutputStream() ); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		loadRequestCommand();
		parseRequest(this.getRequestCommand());
		loadHeaders();
	 
		handleRequest();
	 
	}
	
	
	public String getLocalAddress(){
		
		return client.getLocalAddress().toString();
		
	}
	public String getProtocol() {
		return requestProtocolName;
	}
	public String getProtocolVersion() {
		return requestProtocolVersion;
	}
	public String getRemoteAddress(){
	 
		return client.getInetAddress().toString();
		
	}
	
	public Hashtable<String, String> getRequest() {
		return requestHeaders;
	}
	public String getRequestCommand() {
		return requestCommand;
	}
	public String getRequestCommandType() {
		return requestCommandType;
	}	
	public String getRequestHeader(String key) {
		return requestHeaders.get(key);
	}
	public String[] getRequestHeaders(){
		int count = 0;
		String []headers = new String[requestHeaders.size()];
		String key = "";
		Enumeration<String> keys = requestHeaders.keys();
		
		while(keys.hasMoreElements()){
			key = (String) keys.nextElement();
			headers[count++] = key + ":" + requestHeaders.get(key);
		}
		
		return headers;
		
	}
	public String getRequestURI() {
		return requestURI;
	}
	public String getResponseHeader(String key) {
		return requestHeaders.get(key);
	}
	public String[] getResponseHeaders(){
		int count = 0;
		String []headers = new String[responseHeaders.size()];
		String key = "";
		Enumeration<String> keys = responseHeaders.keys();
		
		while(keys.hasMoreElements()){
			key = (String) keys.nextElement();
			headers[count++] = key + ": " + responseHeaders.get(key);
		}
		
		return headers;
		
	}
	public void loadHeaders(){
		try {
			String line = "";
			String key = "";
			String value = "";
			while ( in.ready() && line  != null ) {
				line = in.readLine();
				System.out.println(line);
				if(line.equals("")) break;
				key = line.substring(0,line.indexOf(':'));
				value = line.substring(line.indexOf(':')+1);
				requestHeaders.put(key.trim(), value.trim());
			}
 		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
 		}
	}
	public void loadRequestCommand(){
		try {
			if(in.ready()){
				  requestCommand = in.readLine();
				  System.out.println(requestCommand);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void parseRequest(String requestLine){
		int frontSlashIDX;
		frontSlashIDX = requestLine.indexOf('/');
		requestCommandType = requestLine.substring(0,frontSlashIDX).trim();
		String[] restOfCommand = requestLine.substring(frontSlashIDX+1).split(" ");
		requestURI = restOfCommand[0].toString().trim();
		frontSlashIDX = restOfCommand[1].indexOf('/');
		requestProtocolName = restOfCommand[1].substring(0,frontSlashIDX).toString();
		requestProtocolVersion = restOfCommand[1].substring(frontSlashIDX+1).toString();
			
	}
	public void setResponseHeader(String key, String value) {
		
		 responseHeaders.put(key, value);
		 
	}
	public void setResponseBody(int len, InputStream b){
		responseBody=b;
		responseBodyLength = len;
	/** 
	
 
		if(keepalive){
  
				executeCommand(client, true);
			}**/
	}
	
	/**
	 * Sends error message to client based on errorCode parameter
	 * 
	 * @param errorCode
	 */
	public void makeErrorResponse(int errorCode){
		 
		switch(errorCode){
			case 400:
			{
				out.println(getProtocol()+ "/"+ getProtocolVersion() + " 400 Bad Request");
				out.flush();
				break;
		
			} 
			case 401:
			{
				out.println(getProtocol()+ "/"+ getProtocolVersion() + " 401 Not Authorized");
				out.println("WWW-Authenticate: Basic realm: \"protected area\"");
				out.flush();
				break;
		
			} 
			case 404:
			{
				out.println(getProtocol()+ "/"+ getProtocolVersion() + " 404 Not found");
			 	out.flush();
				break;
		
			} 
			case 501:
			{
				out.println(getProtocol()+ "/"+ getProtocolVersion() + " 501 Not Implemented");
				out.flush();
				break;
		
			} 
			default :{
				
			}
		}
	}
	
	private void handleRequest() {
		  		 
		int commandKey = commandKeys.get(requestCommandType.toUpperCase());
 
		switch(commandKey)
		{	
			
				case 0: //GET
				{
					file = new File(requestURI);
					if(file.exists()){
						try {
							setResponseBody((int) file.length(), 
											 new FileInputStream(file)
											);
						} catch (FileNotFoundException e) {
						
							e.printStackTrace();
						}
						setResponseHeaders();
						makeSucessfulResponse();
					}
					break;
				}
				case 1: //HEAD
				{
					setResponseHeaders();
					makeSucessfulResponse();
					break;
				}
				case 2: //PUT
				{
						/*int index = httprequest.indexOf("Content-Length");
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
						*/
						break;		 
				}
				case 3: // POST
				{
				 
					break;
				}
				case 4:   //DELETE
				{
					 
					break;
				}
				
			}
		 }
	 
 
	
	

	private void setResponseHeaders() {
		if(responseBodyLength>=0)
			responseHeaders.put("Content-Length", "" + responseBodyLength);
		
		if (requestURI.trim().toLowerCase().endsWith(".jpg") 
		 || requestURI.trim().toLowerCase().endsWith(".jpeg"))
			responseHeaders.put("Content-Type", "image/jpeg");
		else
			responseHeaders.put("Content-Type", "text/html");
		if(!(file==null)){
			Date lastmod = new Date(file.lastModified());
			responseHeaders.put("Last-Modified", lastmod.toString());
		}
		responseHeaders.put("Date", new Date().toString());	
		responseHeaders.put("Connection", "Keep-Alive");	
		responseHeaders.put("Server", getLocalAddress().toString());
	 
	}


	public void makeSucessfulResponse(){
		
		System.out.println(getProtocol() + "/" + getProtocolVersion() +" 200 OK");
		out.println(getProtocol() + "/" + getProtocolVersion() +" 200 OK");
	 	String[] tmpResponseHeaders = getResponseHeaders();
		for(int k=0;k<tmpResponseHeaders.length;k++){
			out.println(tmpResponseHeaders[k].toString());	
			System.out.println(tmpResponseHeaders[k].toString());
		}
		
		if(!(responseBody == null) ){
			out.println("");
			DataInputStream fin = new DataInputStream(responseBody);
			byte buf[] = new byte[responseBodyLength];
			try {
				fin.readFully(buf);
				out.write(buf, 0, responseBodyLength);
				fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 	
		}
		out.flush();
		out.close();
		
	}
}