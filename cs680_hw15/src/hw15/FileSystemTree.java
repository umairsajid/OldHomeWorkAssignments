package hw15;

import hw11.Directory;
import hw11.FSElement;
import hw11.FileSystem;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;



public class FileSystemTree extends HW_JTree {

/**

• Understand JTree and JTable
– JTree
• TreeModel, DefaultTreeModel
• TreeSelectionModel, DefaultTreeSelectionModel
– JTable
• TableModel, DefaultTableModel
• Write two simple apps: one with JTree’s default

model and the other with JTable’s default model.
Change model states (values) and confirm that the
state changes are notified to J* components.

• Due: 3 weeks from today.

*/

private JTree tree;
@SuppressWarnings("unused")
private static final long serialVersionUID1 = 1L;
@SuppressWarnings("unused")
private Directory root;
protected DefaultTreeModel treeModel;


public FileSystemTree() {


	this.removeAll();
	FileSystem.getInstance();
	root = FileSystem.getRoot();
	FileSystem.root();
	DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root");
	
	createNodes(top);
	treeModel = new DefaultTreeModel(top);
	
	setTree(new JTree(treeModel));

	getTree().getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

	getTree().getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);

	//Listen for when the selection changes.
	 getTree().addTreeSelectionListener(this);

	 
	//Create the scroll pane and add the tree to it.
	JScrollPane treeView = new JScrollPane(getTree());

	//Add the scroll panes to a split pane.
	JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	splitPane.setTopComponent(treeView);

	Dimension minimumSize = new Dimension(100, 50);
	treeView.setMinimumSize(minimumSize);

	splitPane.setDividerLocation(100);
	splitPane.setPreferredSize(new Dimension(500, 300));

	//Add the split pane to this panel.
	add(splitPane);

}

public void setListener(TreeSelectionListener t){
	
    TreeSelectionListener[] listener ;
    listener = tree.getTreeSelectionListeners();
	for(int k= 0; k< listener.length;k++){
		tree.removeTreeSelectionListener(listener[k]);
	}
 	tree.addTreeSelectionListener(t);
	
}


private static final long serialVersionUID = 1L;

/**

*
@param args

*/
@Override

public void valueChanged(TreeSelectionEvent arg0) {

// TODO Auto-generated method stub
	  DefaultMutableTreeNode node = (DefaultMutableTreeNode) getTree().getLastSelectedPathComponent();

      if (node == null) return;

	System.out.println(node.getUserObject().toString() + " was selected");
}

private void createNodes(DefaultMutableTreeNode tp) {
	FSElement temp;
	Directory currentDirectory;
	if(!tp.toString().equals("Root")) FileSystem.changeDirectory(tp.toString());
    currentDirectory = FileSystem.getCurrentDirectory();
    
    for(int k = 0; k< currentDirectory.getChildren().size();k++)
    {
    	temp = currentDirectory.getChildren().get(k);
    	if(!temp.getName().equals("..")){
	    		
	    	
	    	if( temp!= null && temp.getKind().equals("Directory")){ //directory
	    		DefaultMutableTreeNode recursion = new DefaultMutableTreeNode(temp.getName());
	    		createNodes( recursion );
	    		tp.add(recursion);
	    		FileSystem.changeDirectory("..");
	    		currentDirectory = FileSystem.getCurrentDirectory();
    		}
	    	else //file
	    	{
	    		tp.add(new DefaultMutableTreeNode(new String (temp.getName())));
	    	
	    	}
    	}
    }


}

public void setTree(JTree tree) {
	this.tree = tree;
	this.tree.setEditable(true);
}

public JTree getTree() {
	return tree;
}

public void makeDirectory(String string) {
	// TODO Auto-generated method stub
	if(getTree().getLastSelectedPathComponent()!=null)
		treeModel.insertNodeInto(new DefaultMutableTreeNode(new String (string)),
								 (DefaultMutableTreeNode) getTree().getLastSelectedPathComponent(),
								 ((DefaultMutableTreeNode) getTree().getLastSelectedPathComponent()).getChildCount()
								 );
	else
		treeModel.insertNodeInto(new DefaultMutableTreeNode(new String (string)),
				 (DefaultMutableTreeNode) treeModel.getRoot(),
				 ((DefaultMutableTreeNode)treeModel.getRoot()).getChildCount()
				 );
    }
}
 

