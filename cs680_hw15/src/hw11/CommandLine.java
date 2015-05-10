package hw11;

import java.util.Scanner;

public class CommandLine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	 
	
		Scanner scanner = new Scanner( System.in );
		String input =new String();
		String [] inputCommands = new String[2] ;
		int command = -1;
	 	while (!input.equals("exit"))
		{
	 		FileSystem.getInstance();
		    System.out.print( ">" );
	 	    input = scanner.nextLine();
	 	    if(input.split(" ").length <= 1) inputCommands[0] = input.toString();
	 	    else inputCommands=input.split(" ");
	 	   command = getCommand(inputCommands[0]);
	 	   switch(command){
	    		case 1: FileSystem.currentDirectoryListing(inputCommands[1]); break;
	    		case 2: FileSystem.currentDirectoryLS(); break;
	 	    	case 3: FileSystem.changeDirectory(inputCommands[1]); break;
	 	   		case 4: FileSystem.makeDirectory(inputCommands[1]); break;
	 	     	case 5: FileSystem.root();break;
	 	     	case 6: FileSystem.printWorkingDirectory();break;
	 	    	default:; 	    	
	 	    }
	 	    command =-1;
	 	   inputCommands = new String[2];
		}
 
	}
	private static int getCommand(String userInput){
		int returnValue = -1;
		if(userInput.equals("dir")) returnValue = 1;
		if(userInput.equals("ls"))    returnValue = 2;
		if(userInput.equals("cd"))  returnValue = 3;
		if(userInput.equals("mkdir")) returnValue = 4;
		if(userInput.equals("root"))  returnValue = 5;
		if(userInput.equals("pwd"))   returnValue = 6;
		return returnValue;
	}

}
