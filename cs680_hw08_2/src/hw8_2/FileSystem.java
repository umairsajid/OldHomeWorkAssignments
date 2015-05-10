package hw8_2;

import java.util.Date;

public class FileSystem {

	/**
	 * @param args
	 */
	static Directory root;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		root = new Directory(null,new Date(),"root");
		root.appendChild(new Directory(root,new Date(),"Windows"));
		root.appendChild(new Directory(root,new Date(),"MyDocument"));
		
		((Directory) root.findByName("Windows")).appendChild(new File((Directory) root.findByName("Windows"),new Date(),"a"));
		((Directory) root.findByName("Windows")).appendChild(new File((Directory) root.findByName("Windows"),new Date(),"b"));
		((Directory) root.findByName("Windows")).appendChild(new File((Directory) root.findByName("Windows"),new Date(),"c"));
		
		((Directory) root.findByName("Windows")).findByName("a").setSize(100);
		((Directory) root.findByName("Windows")).findByName("b").setSize(400);
		((Directory) root.findByName("Windows")).findByName("c").setSize(1300);
	 	((Directory) root.findByName("MyDocument")).appendChild(new File((Directory) root.findByName("MyDocument"),new Date(),"d"));
		
		((Directory) root.findByName("MyDocument")).appendChild(new Directory((Directory) root.findByName("MyDocument"),new Date(),"MyPictures"));
		
		((Directory) root.findByName("MyDocument")).findByName("d").setSize(10);
 	
		((Directory) ((Directory) root.findByName("MyDocument")).findByName("MyPictures")).appendChild(new File((Directory) ((Directory) root.findByName("MyDocument")).findByName("MyPictures"),new Date(),"e"));
		((Directory) ((Directory) root.findByName("MyDocument")).findByName("MyPictures")).appendChild(new File((Directory) ((Directory) root.findByName("MyDocument")).findByName("MyPictures"),new Date(),"f"));
		 
		((Directory)((Directory) root.findByName("MyDocument")).findByName("MyPictures")).findByName("e").setSize(500);
		showAllElements();
		 
	}
	
	public static void showAllElements(){
		System.out.println(root.getInfo());
	}
	

}
