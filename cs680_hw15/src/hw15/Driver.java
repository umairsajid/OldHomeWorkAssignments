package hw15;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class Driver extends JPanel  implements   TreeSelectionListener, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int newFileCounter = 0;
	FileSystemTree tree= new FileSystemTree();
	FileSystemTable table =new FileSystemTable();
	Button mkdirButton = new Button("mkdir");
	Button pwdButton = new Button("pwd"); 
	DefaultMutableTreeNode PreviousSelected;
	public Driver(){
		  tree= new FileSystemTree();
		  table =new FileSystemTable();
		  setListeners();
		  add(tree);
		  add(table);
		  add(mkdirButton);  
		 
	}
	
	
	
	private void setListeners() {
		
		mkdirButton.addActionListener(this);
		tree.setListener(this);
		
	}


	private static void createAndShowGUI() {
	     
	        //Create and set up the window.
	        JFrame frame = new JFrame("FileSystem");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Add content to the window.
	        frame.add(new Driver());

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }

	    public static void main(String[] args) {
	        //Schedule a job for the event dispatch thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }



	@Override
	public void valueChanged(TreeSelectionEvent arg0) {

			 if(PreviousSelected!=null) table.renameDirectory(PreviousSelected.getUserObject().toString());
		// TODO Auto-generated method stub
			  DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getTree().getLastSelectedPathComponent();
			  
		      if (node == null) return;

			 System.out.println(node.getUserObject().toString() + " was selected in Driver");
	  
		     table.setDirectory(node.getUserObject().toString());
		     
		     PreviousSelected = node;
			
		}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		 table.makeDirectory("New File"+ newFileCounter);
		 tree.makeDirectory("New File" + newFileCounter++);
	} 
 


}
