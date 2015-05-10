package hw23;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClientGet {
	public static void main(String[] args) {
		HttpURLConnection conn = null;
		BufferedReader in = null;
		String [] urls =
		{"http://localhost:8888/index.html"
		,"http://localhost:8888/idxPut.html"
		,"http://localhost:8888/i.html"};
		for(int k =0; k<urls.length;k++){	
		try {
			URL url = new URL(urls[k]);
			conn = (HttpURLConnection) url.openConnection();
		 	conn.connect();
			System.out.println( "Connection established." );
			
			in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );  
			System.out.println( "I/O setup done." );
			
			System.out.println( conn.getResponseCode() + conn.getResponseMessage() );
			System.out.println( conn.getHeaderFields() );
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String line = in.readLine();
				while ( in.ready() && line != null ) {                  
					System.out.println(line);
					line = in.readLine();
				}
				System.out.println(line);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			 
		} catch (IOException e) {
			e.printStackTrace();
			 
		} finally {
			try {
				in.close();
				System.out.println( "I/O closed." );
			} catch (IOException e) {
				e.printStackTrace();
			}
			conn.disconnect();
		}
			System.out.println( "Connection closed." );
		}	
	}
}
