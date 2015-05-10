package hw19;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;

public class EchoClient
{
	private static final int ECHOPORT = 8889;
	private Socket socket;
	public Scanner in;
	private PrintWriter out;

	public void init()
	{
		try
		{
			socket = new Socket( "localhost", ECHOPORT );
			System.out.println( "Socket created on the local port " +
								socket.getLocalPort() );
			System.out.println( "A connection established with the remote port " +
								socket.getPort() + " at " +
								socket.getInetAddress().toString() );

			in = new Scanner( socket.getInputStream() );
			out = new PrintWriter( socket.getOutputStream() );
			System.out.println( "I/O setup done." );
		}
		catch(IOException exception){}
	}
	
	public void sendCommand( String command )
	{
		System.out.print( "Sending " + command );
		out.print( command );
		out.flush();
		if( command.equals("QUIT") )
		{
			try
			{
				socket.close();
				System.out.println( "A connection closed." );
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	public String getResponse()
	{
		return in.nextLine();
	}

	public static void main(String[] args)
	{
		EchoClient client = new EchoClient();
		client.init();
		
		client.sendCommand( "TEST\n" );
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println( client.getResponse() );
		
		client.sendCommand( "QUIT\n" );
	}
}
