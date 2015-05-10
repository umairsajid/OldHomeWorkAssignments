package hw15;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;



public class HW_JTree extends JPanel implements TreeSelectionListener {

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

public JTree tree;

public HW_JTree() {

super(new GridLayout(1,0));

//Create the nodes.

DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root");

createNodes(top);

//Create a tree that allows one selection at a time.
tree = new JTree(top);

tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

tree.getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);

//Listen for when the selection changes.
 tree.addTreeSelectionListener(this);

//Create the scroll pane and add the tree to it.
JScrollPane treeView = new JScrollPane(tree);

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

private static final long serialVersionUID = 1L;

/**

*
@param args

*/

public static void main(String[] args) {

// TODO Auto-generated method stub

//Create and set up the window.

JFrame frame = new JFrame("TreeDemo");

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//Add content to the window.

frame.add(new HW_JTree());

//Display the window.

frame.pack();

frame.setVisible(true);

}

@Override

public void valueChanged(TreeSelectionEvent arg0) {

// TODO Auto-generated method stub
	  DefaultMutableTreeNode node = (DefaultMutableTreeNode)
      tree.getLastSelectedPathComponent();

      if (node == null) return;

	System.out.println(node.getUserObject().toString() + " was selected");
}

private void createNodes(DefaultMutableTreeNode top) {

DefaultMutableTreeNode category = null;
DefaultMutableTreeNode child = null;

category = new DefaultMutableTreeNode("Folder 1");

top.add(category);

child = new DefaultMutableTreeNode(new String ("Child 1"));
category.add(child);

child = new DefaultMutableTreeNode(new String ("Child 2"));
category.add(child);

child = new DefaultMutableTreeNode(new String ("Child 3"));
category.add(child);

child = new DefaultMutableTreeNode(new String ("Child 4"));
category.add(child);

}

}

 

