package hw11;

import java.util.Date;

public class FileSystem {

	/**
	 * @param args
	 */
	static Directory root;
	static Directory currentDirectory;
	private FileSystem(){}
	private static FileSystem instance = null;
	
	public static FileSystem getInstance(){
		if(instance==null){
				instance = new FileSystem();
				populateData();
		}
		return instance;
	}
	
	private static void populateData() {
		// TODO Auto-generated method stub
		root = new Directory(null,new Date(),".");
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

		((Directory) ((Directory) root.findByName("MyDocument")).findByName("MyPictures")).appendChild(new Link((Directory) ((Directory) root.findByName("MyDocument")).findByName("MyPictures"),new Date(),"y",(Directory) root.findByName("Windows")));
		showAllElements();
		currentDirectory = root;
	}
	public static void printWorkingDirectory(){
		String pwd = new String("/");
		Directory pointer = currentDirectory;
		while (pointer.getParent()!=null){
			pwd= "/" + pointer.getName() + pwd.toString();
			pointer = pointer.getParent();
		}
		System.out.println(pwd);
	}
		
	public static void changeDirectory(String d){
		FSElement temp =currentDirectory.findByName(d.toString()) ;
		if( temp!= null && temp.getKind().equals("Directory"))
			currentDirectory = (Directory) temp ;
		else
			System.out.println("Invalid Directory");
	}
	public static void makeDirectory(String d){
		currentDirectory.appendChild(new Directory(currentDirectory,new Date(),d));
	}
	public static void showAllElements(){
		System.out.println(root.getInfo());
	}
	public static void currentDirectoryListing(String inputCommands){
		if(inputCommands ==null) System.out.println(currentDirectory.nonRecursive_getInfo());
		else{
			FSElement temp =currentDirectory.findByName(inputCommands);
			if (temp!=null)			System.out.println(temp.getInfo());
		}
	}
	public static void root(){
		currentDirectory = root;
	}
	
	public static void currentDirectoryLS(){
		System.out.println(currentDirectory.nonRecursive_getName());
	}
	

}
