import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class MainDisplay {
    private static final int SIZE = 10;
    private JButton[] buttons;
    private GridBagConstraints gbc;
    static SysMan sysMan = new SysMan();
	static ListManager listMan = new ListManager();
	static String[] checkedLists;

    public MainDisplay() {
        buttons = new JButton[SIZE];
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
    }

    private void createAndDisplayGUI() {
        JFrame frame = new JFrame("Loot Generator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(2, 1, 0, 5));
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        JPanel headerPanel = new JPanel(new GridBagLayout());
        
	    //List Selector
		final JList<String> allLists = new JList<String>(listMan.getLists());
		final CheckListManager checkListMan = new CheckListManager(allLists); 
		allLists.setVisibleRowCount( 15 ); // show 15 rows
		allLists.setFixedCellWidth( 190 ); // set width
		allLists.setFixedCellHeight( 25 ); // set height
		allLists.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION ); //Allows selection of more than one item
		allLists.setSelectionInterval(0,0); // Sets 1st entry to the default value
		contentPane.add( new JScrollPane( allLists ));			
		//END List Selector

        buttons[0] = new JButton("<html> &nbsp <br/>View List <br/> &nbsp <html/>");
        buttons[0].addActionListener( new ActionListener(){ // anonymous inner class 
    		public void actionPerformed( ActionEvent event ) {// handle button event
    			checkedLists = listMan.getSelectedLists(allLists,checkListMan);
    			if(checkedLists.length <= 0 ){
					JOptionPane.showMessageDialog(null, "You failed to select any lists.","ERROR: No Lists", JOptionPane.ERROR_MESSAGE);
	        	}else{
	        		listMan.viewList(checkedLists);
	        	}
		    }
	    });
        addComp(headerPanel, buttons[0], 0, 0, 0.25, 1.0, 1, 1, GridBagConstraints.BOTH);
        
        buttons[1] = new JButton("<html> &nbsp<center> <br/>Open Item List <br/>Folder <br/></center>&nbsp <html/>");
        buttons[1].addActionListener( new ActionListener(){ // anonymous inner class 
      		public void actionPerformed( ActionEvent event ) {// handle button event
      			String Listdir = System.getProperty("user.dir") + "\\Lists\\";
      			try {
      				Runtime.getRuntime().exec("explorer " + Listdir);
      			} catch (IOException e) {
      				JOptionPane.showMessageDialog(null, "Can't open directory Lists","ERROR: Opening Directory ", JOptionPane.ERROR_MESSAGE);
      			}
  		    }
  	    });
        addComp(headerPanel, buttons[1], 1, 0, 0.25, 1.0, 1, 1, GridBagConstraints.BOTH);
        
        buttons[2] = new JButton("<html> &nbsp <br/><center>Open Generated <br/> Lists Folder <br/></center>&nbsp <html/>");
        buttons[2].addActionListener( new ActionListener(){ // anonymous inner class 
      		public void actionPerformed( ActionEvent event ) {// handle button event
      			String Listdir = System.getProperty("user.dir") + "\\GeneratedLists\\";
      			try {
      				Runtime.getRuntime().exec("explorer " + Listdir);
      			} catch (IOException e) {
      				JOptionPane.showMessageDialog(null, "Can't open directory Lists","ERROR: Opening Directory ", JOptionPane.ERROR_MESSAGE);
      			}
  		    }
  	    });
        addComp(headerPanel, buttons[2], 1, 1, 0.25, 1.0, 1, 1, GridBagConstraints.BOTH);
       
        buttons[3] = new JButton("<html> &nbsp <br/>Search Lists <br/> &nbsp <html/>");
        buttons[3].addActionListener( new ActionListener(){ // anonymous inner class 
    		public void actionPerformed( ActionEvent event ) {// handle button event
    			checkedLists = listMan.getSelectedLists(allLists,checkListMan);
    			if(checkedLists.length <= 0 ){
					JOptionPane.showMessageDialog(null, "You failed to select any lists.","ERROR: No Lists", JOptionPane.ERROR_MESSAGE);
	        	}else{
	        		listMan.searchLists(checkedLists);
	        	}
		    }
	    });
        addComp(headerPanel, buttons[3], 0, 1, 0.25, 1.0, 1, 1, GridBagConstraints.BOTH);        
        
        buttons[4] = new JButton("<html>&nbsp<br/><center>Generate Loot <br/> (Number of Items) <br/> </center>&nbsp </html>");
        buttons[4].addActionListener( new ActionListener(){ // anonymous inner class 
    		public void actionPerformed( ActionEvent event ) {// handle button event
    			checkedLists = listMan.getSelectedLists(allLists,checkListMan);
    			if(checkedLists.length <= 0 ){
					JOptionPane.showMessageDialog(null, "You failed to select any lists.","ERROR: No Lists", JOptionPane.ERROR_MESSAGE);
	        	}else{
	        		AppFrame.runAgain = 0;
	        		listMan.randNumItems(checkedLists);
	        	}
		    }
	    });
        addComp(headerPanel, buttons[4], 2, 1, 0.25, 1.0, 1, 1, GridBagConstraints.BOTH);       
         
        buttons[5] = new JButton("<html>&nbsp<br/><center>Generate Loot <br/> (Gold Value) <br/> </center>&nbsp </html>");
        buttons[5].addActionListener( new ActionListener(){ // anonymous inner class 
    		public void actionPerformed( ActionEvent event ) {// handle button event
    			checkedLists = listMan.getSelectedLists(allLists,checkListMan);
    			if(checkedLists.length <= 0 ){
					JOptionPane.showMessageDialog(null, "You failed to select any lists.","ERROR: No Lists", JOptionPane.ERROR_MESSAGE);
	        	}else{
	        		AppFrame.runAgain = 1;
	        		listMan.randGoldValue(checkedLists);
	        	}
		    }
	    });
        addComp(headerPanel, buttons[5], 2, 0, 0.25, 1.0, 1, 1, GridBagConstraints.BOTH);
        
        contentPane.add(headerPanel);
        frame.setContentPane(contentPane);
        frame.setSize(500,500);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void addComp(JPanel panel, JComponent comp, int x, int y, double wx, double wy, int gw, int gh, int fill) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = wx;
        gbc.weighty = wy;
        gbc.gridwidth = gw;
        gbc.gridheight = gh;
        gbc.fill = fill;

        panel.add(comp, gbc);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainDisplay().createAndDisplayGUI();
            }
        });
    }
}