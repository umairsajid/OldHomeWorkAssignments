package hw24;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpClientPost {
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
			URL url = new URL("http://localhost:8888/indexpost.html");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			
			conn.addRequestProperty("Authorization", Base64Coder.encodeString("jadd:jadd") );
			conn.setDoOutput(true);
  			OutputStreamWriter out = new OutputStreamWriter( conn.getOutputStream()); 
  		
  			 String data = URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode("zzz", "UTF-8");
			    data+= "\r\n";
			    //  Send data
			    //  URL url = new URL("http://hostname:80/cgi");
			    //  URLConnection conn = url.openConnection();
			   
			    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			    wr.write(data);
			    wr.flush();
			    wr.close();
			
			
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
