package hw15;

import hw11.Directory;
import hw11.FileSystem;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FileSystemTable extends HW_JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Directory root;
	private Directory currentDirectory;
	
	 public FileSystemTable() {
		 this.removeAll();
		 FileSystem.getInstance();
		  
		 FileSystem.root();
	     currentDirectory = FileSystem.getCurrentDirectory();
         initComponent(currentDirectory.getChildren().size());
     }

	 public void setDirectory(String directory){
		 	int currentDirectorySize = currentDirectory.getChildren().size();
		 	if(!currentDirectory.getName().equals("Root")) currentDirectorySize--;
		     for(int k =0;k < currentDirectorySize; k++)
	         {
	        	 tableModel.removeRow(0);
	         }
		  if(!directory.equals("Root")) FileSystem.changeDirectoryAbsolute(directory);
		  else FileSystem.root();
		  currentDirectory = FileSystem.getCurrentDirectory();
		  tableModel.setRowCount((currentDirectory.getChildren().size()));
		  for(int k = 0; k< currentDirectory.getChildren().size();k++)
	         {
			  	if(!currentDirectory.getChildren().get(k).getName().equals("..")){
	        	 tableModel.setValueAt(currentDirectory.getChildren().get(k).getName(), k, 0);
	        	 tableModel.setValueAt(currentDirectory.getChildren().get(k).getSize(), k, 1);
			  	}
			  	
	         }
		   for(int k = 0; k< currentDirectory.getChildren().size();k++)
		         {
				  	if(currentDirectory.getChildren().get(k).getName().equals("..")){
				  		tableModel.removeRow(k);
				  		k=0;
				  	}
				  	
		         }
		  
	
		}
	 
	 public void initComponent(int numOfFilesInDirectory) {
		
         tableModel = new DefaultTableModel(columnNames,numOfFilesInDirectory);
       
         tableModel.addTableModelListener(this);
         table = new JTable();
         table.setModel(tableModel);
         
         scroller = new javax.swing.JScrollPane(table);
         table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
      
         for(int k = 0; k< currentDirectory.getChildren().size();k++)
         {
        	 tableModel.setValueAt(currentDirectory.getChildren().get(k).getName(), k, 0);
        	 tableModel.setValueAt(currentDirectory.getChildren().get(k).getSize(), k, 1);
         }
     /* 
      *    
      *    TableColumn hidden = table.getColumnModel().getColumn();
         hidden.setMinWidth(2);
         hidden.setPreferredWidth(2);
         hidden.setMaxWidth(2);
         
         */
         //hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX));

         setLayout(new BorderLayout());
         add(scroller, BorderLayout.CENTER);
     }

	public void renameDirectory(String previousSelected) {
		// TODO Auto-generated method stub
		 FileSystem.renameDirectory(previousSelected);
	}

	public void makeDirectory(String string) {
		// TODO Auto-generated method stub
		FileSystem.makeDirectory(string);
	    Object[] row = { string,0 };

		tableModel.addRow( row );
	}

	 
}

