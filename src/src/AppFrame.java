import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class AppFrame extends JFrame {	
	static ListManager listMan = new ListManager();
	Random generator = new Random();
	static int runAgain = 0;
	
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	private static final long serialVersionUID = 1L;

	private JLabel listInfo;
	private JTable table;
    private static JButton reRun; 
    private static JButton writeOut; 
   
   public AppFrame(LootList list, String name) {
	  super(name); 
	  
	  //Start Table
	  table = new JTable();
	  DefaultTableModel model = (DefaultTableModel) table.getModel();
	  
	  //Table column Names
	  model.addColumn("Item");
	  model.addColumn("Price");
	  model.addColumn("Weight");
	  model.addColumn("Quantity");
	  
	  //Contents of each column
	  for(int i = 0; i < list.getSize(); i++){
		Loot loot = list.getLoot(i);
	  	model.addRow(new Object[]{loot.getName(), loot.getValue(), loot.getWeight(),loot.getQuantity()});
	  }
	  
	  
	  table.getColumn("Item").setMinWidth(200); // makes coulumn item bigger than the rest
	  add( new JScrollPane( table )); // add table with scrollpane
	 //End Table
   }
   
   public void createAndShowGUI(LootList list) {
       //Create and set up the window.
       JFrame frame = new JFrame("Loot Generator");

       //Set up the content pane.
       addComponentsToPane(frame.getContentPane(),list);

       //Display the window.
       frame.setSize(710,550);
       frame.setResizable(false);
       frame.setVisible(true);
   }
   
   public void addComponentsToPane(Container pane, final LootList list) {   
       if (RIGHT_TO_LEFT) {
           pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       }

	    pane.setLayout(new GridBagLayout());
	    GridBagConstraints constraints = new GridBagConstraints();
	    
	    if (shouldFill) {
	    	//natural height, maximum width
	    	constraints.fill = GridBagConstraints.HORIZONTAL;
	    }
	 
	    if (shouldWeightX) {
	    	constraints.weightx = 0.5;
	    }
	    
	  //Start Table
	      table = new JTable();
	      DefaultTableModel model = (DefaultTableModel) table.getModel();
	      
	      //Table coloumn names
	      model.addColumn("Item");
	      model.addColumn("Price");
	      model.addColumn("Weight");
	      model.addColumn("Quantity");
	      
	      //Table contents
	      for(int i = 0; i < list.getSize(); i++){
	  		Loot loot = list.getLoot(i);
	  		int magicProb = generator.nextInt(100);
	  		//If its magic add * otherwise dont
	  		if (magicProb <= list.getMagicProb()){
	  			loot.setMagic(1);
	  			loot.setName(loot.getName() + "*");
	  			model.addRow(new Object[]{loot.getName(), loot.getValue(), loot.getWeight(),loot.getQuantity()});
			}else{
				model.addRow(new Object[]{loot.getName(), loot.getValue(), loot.getWeight(),loot.getQuantity()});
			}
	  	  }
	      
	      table.getColumn("Item").setMinWidth(200); // makes coulumn item bigger than the rest
	      pane.add( new JScrollPane( table )); // add table with scrollpane
	  //End Table
      
      //Start reRun button
	    reRun = new JButton("Run Again");
		reRun.addActionListener(
	         new ActionListener(){ // anonymous inner class 
	            // handle button event
				public void actionPerformed( ActionEvent event ) {
					if(runAgain == 0){
						listMan.randNumItems(MainDisplay.checkedLists);
					}
					else{
						listMan.randGoldValue(MainDisplay.checkedLists);
					}
	            } 
	         }
	      );
    	
	    constraints.ipady = 40;      //make this component tall
	    constraints.ipadx = 10; 
	    constraints.gridx = 0;
	    constraints.gridy = 1;
		constraints.insets = new Insets(10,10,10,10);
	    pane.add(reRun, constraints);
	   // End reRun button
	    
	   //Start writeOut button 
	    writeOut = new JButton("Write File Out");
	    writeOut.addActionListener(
	         new ActionListener(){ // anonymous inner class 
	            // handle button event
				public void actionPerformed( ActionEvent event ) {
					Items itemFunctions = new Items();
					itemFunctions.DoWrite(list);
	            } 
	         }
	      );
	    constraints.ipady = 40;      //make this component tall
	    constraints.ipadx = 40;
	    constraints.gridx = 1;
	    constraints.gridy = 1;
	    pane.add(writeOut, constraints);
	  //End writeOut button  
	    
	  //Start listInfo label    
	    listInfo = new JLabel( "<html>Number of Items = " + list.getTotalItems() +
		"<br>Total value of items = " + list.totalValue + " gp" +
		"<br>Total item weight = " + list.totalWeight + " lbs<br>" +
        "<br>* = magic item (" + list.getMagicProb() + "% chance)" +
       	"</html>");

	    constraints.weightx = 0.25;
	    constraints.gridx = 1;
	    constraints.gridy = 0;
        pane.add(listInfo,constraints);
      //End listInfo label
   }
   
}
	