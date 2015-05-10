package hw19;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class EchoServer
{
	private static final int ECHOPORT = 8889;
	 
	private ServerSocket serverSocket;
	
	public EchoServer()
	{
		 
	}
	
	public void init()
	{
		try
		{
			try
			{
				serverSocket = new ServerSocket(ECHOPORT);
				System.out.println("Socket created.");
			
				while(true)
				{	
					System.out.println( "Listening for a connection on the local port " +
										serverSocket.getLocalPort() + "..." );
					Socket socket = serverSocket.accept();
					System.out.println( "\nA connection established with the remote port " + 
										socket.getPort() + " at " +
										socket.getInetAddress().toString() );
					executeCommand( socket );
				}
			}
			finally
			{
				serverSocket.close();
			}
		}
		catch(IOException exception){}
	}
	
	private void executeCommand( Socket socket )
	{
		try
		{
			try
			{
				Scanner in = new Scanner( socket.getInputStream() );
				PrintWriter out = new PrintWriter( socket.getOutputStream() );
				System.out.println( "I/O setup done" );
				
				while(true)
				{
					if( in.hasNext() )
					{
						String command = in.next();
						//System.out.println(command.toString());
						if( command.equals("QUIT") )
						{
							System.out.println( "QUIT: Connection being closed." );
							out.println( "QUIT accepted. Connection being closed." );
							return;
						}
						System.out.println(command.toString());
						out.println(command.toString());
						out.flush();
						return;
						 
					}
				}
			}	
			finally
			{
				socket.close();
				System.out.println( "A connection is closed." );
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}


	public static void main(String[] args)
	{
		EchoServer server = new EchoServer();
		server.init();
	}
}
