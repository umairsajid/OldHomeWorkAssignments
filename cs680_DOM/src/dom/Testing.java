package dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Testing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   // Step 1: create a DocumentBuilderFactory     
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();       
// Step 2: create a DocumentBuilder      
		Document doc = null;
		DocumentBuilder db;
		File f = new File("C:\\Users\\jadd\\workspace\\cs680_DOM\\src\\build_test.xml");
		// Step 3: parse the input file to get a Document object   
		try {
			  db = dbf.newDocumentBuilder();    
			  doc = db.parse(new File("C:\\Users\\jadd\\workspace\\cs680_DOM\\src\\build_test.xml"));
			  
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(doc.toString());
	}

}
