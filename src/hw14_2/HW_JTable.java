package hw14_2;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class HW_JTable extends JPanel implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
 
	
	 public static final String[] columnNames = {
         "Name", "Size" 
     };

     protected JTable table;
     protected JScrollPane scroller; 
     protected DefaultTableModel tableModel;
       TableModelListener listener;

     public HW_JTable() {
         initComponent();
     }

     public void initComponent() {
         tableModel = new DefaultTableModel(columnNames,5);
       
         tableModel.addTableModelListener(this);
         table = new JTable();
         table.setModel(tableModel);
         
         scroller = new javax.swing.JScrollPane(table);
         table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
     /*    TableColumn hidden = table.getColumnModel().getColumn();
         hidden.setMinWidth(2);
         hidden.setPreferredWidth(2);
         hidden.setMaxWidth(2);*/
         //hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX));

         setLayout(new BorderLayout());
         add(scroller, BorderLayout.CENTER);
     }

 

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		TableModel model = (TableModel)arg0.getSource();
     


	    if (arg0.getType() == TableModelEvent.UPDATE) {
	        int column = arg0.getColumn();
	        int row = arg0.getFirstRow();
	          System.out.println("row: " + row + " column: " + column + " was updated to " + model.getValueAt(row, column) );
	    }
	}
 
	
	 public static void main(String[] args) {
	         try {
	        
	             JFrame frame = new JFrame("Interactive Form");
	             frame.addWindowListener(new WindowAdapter() {
	                 public void windowClosing(WindowEvent evt) {
	                     System.exit(0);
	                 }
	             });
	             frame.getContentPane().add(new HW_JTable());
	             frame.pack();
	             frame.setVisible(true);
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	     }

}
