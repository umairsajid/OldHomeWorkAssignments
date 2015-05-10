package hw18;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class BankServer
{
	private static final int BANKPORT = 8888;
	private BankAccount account;
	private ServerSocket serverSocket;
	
	public BankServer()
	{
		account = new BankAccount();
	}
	
	public void init()
	{
		try
		{
			try
			{
				serverSocket = new ServerSocket(BANKPORT);
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
						if( command.equals("QUIT") )
						{
							System.out.println( "QUIT: Connection being closed." );
							out.println( "QUIT accepted. Connection being closed." );
							return;
						}
						accessAccount( command, in, out );
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

	private void accessAccount( String command, Scanner in, PrintWriter out )
	{

	 
			System.out.println(command  );
			out.println( command );
		 
		out.flush();
	}
	
	public static void main(String[] args)
	{
		BankServer server = new BankServer();
		server.init();
	}
}
