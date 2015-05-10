package hw23;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClientDelete {
	private static String readFileAsString(String filePath)
	
    throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        fileData.append("\r\n");
        return fileData.toString();
    }




	public static void main(String[] args) {
		HttpURLConnection conn = null;
		BufferedReader in = null;

		try {
			URL url = new URL("http://localhost:8888/idx2.html");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setRequestMethod("DELETE");
			conn.addRequestProperty("Authorization", "Jadd");
					
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
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				in.close();
				System.out.println( "I/O closed." );
			} catch (IOException e) {
				e.printStackTrace();
			}
			conn.disconnect();
			System.out.println( "Connection closed." );
		}	
	}
}
