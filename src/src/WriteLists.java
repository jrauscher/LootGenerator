import java.io.IOException;
import java.util.Formatter;

import javax.swing.JOptionPane;
public class WriteLists{
	
	private Formatter output;
	//Location we are going to write the file too. 
	private String Listdir = System.getProperty("user.dir") + "\\GeneratedLists\\";
	
	//Opens the file that we are going to write stuff out too.
	public void openFile(String FileName){
		
		try{
			output = new Formatter(Listdir + FileName +".txt");
		}
		catch(IOException exception){
			JOptionPane.showMessageDialog(null, "Possible directory permission issue?","ERROR: Cannont make file!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Closes the file we've written too once were done with it
	public void closeFile(){
		if(output !=null){
			output.close();
		}
	}

	//Adds stuff to the file we've opened 
	public void writeList (LootList list){
		output.format("%-30s %-20s %-20s %-20s\r\n","Item Name","Price","Weight","Quantity");
		output.format("---------------------------------------------------------------------------------------\r\n");
		
		//loops though and writes contents from the string to the text file
		//Format given Item:Price:Weight
		//Spaces them out and prints them out in even columns 
		for(int i=0; i < list.getSize(); i++){
			output.format("%-30s %-20s %-20s %-20s\r\n",list.getLoot(i).getName(),list.getLoot(i).getValue(),list.getLoot(i).getWeight(),list.getLoot(i).getQuantity());		
		}
		
		output.format("---------------------------------------------------------------------------------------\r\n\r\n");
			 output.format("\r\nTotal value of items: " + list.getTotalValue() + "gp");
			 output.format("\r\nTotal item weight: " + list.getTotalWeight() + "lbs");
			 output.format("\rNumber of items: " + list.getTotalItems() + "\r\n");
		
		output.format("\r\n\r\n * = magic item (" + list.getMagicProb() + "%% chance) \r\n");

		JOptionPane.showMessageDialog(null, "File written successfully!!\nFile Location: " + Listdir, "Writting File", JOptionPane.INFORMATION_MESSAGE);
		
		try {
			Runtime.getRuntime().exec("explorer " + Listdir);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't open directory GeneratedLists","ERROR: Opening Directory ", JOptionPane.ERROR_MESSAGE);
		}
	}	
}
