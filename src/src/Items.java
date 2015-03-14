import java.util.Random;
import javax.swing.JOptionPane;

public class Items {
	LootList randList = new LootList();
	
	//Prints the contents of an array to the screen
	public void displayList(LootList list, String name){
		AppFrame display = new AppFrame(list,name); 
	    display.setSize( 500, 500 ); // set frame size
	    display.setVisible( true ); // display frame
	}
	
	public void displayShop (LootList list, String name){
		AppFrame Display = new AppFrame(list, name); 
	    Display.setSize( 500, 500 ); // set frame size
	    Display.setVisible( false ); // display frame
	    Display.createAndShowGUI(list);
	}
	
	//Searches for an item in the array based on user input
	public LootList searchList(String search, LootList list){
		StringMan string = new StringMan();
		LootList found = new LootList();
		
		String searchTerm = "";
		String commaItemName = "";
		int commaCheck = 0;
		int numberOfItems = list.getTotalItems();
		
		String LSearch = string.soundex(search);
		
		for(int count = 0; count < numberOfItems; count++){
			commaCheck = list.getLoot(count).getName().indexOf(",");
			
			if(commaCheck > 0){
				commaItemName = list.getLoot(count).getName().substring(0,commaCheck);
				commaItemName = string.soundex(commaItemName);
			}
			searchTerm = string.soundex(list.getLoot(count).getName());
			
			if(LSearch.equals(searchTerm) || LSearch.equalsIgnoreCase(commaItemName)){		
				found.addLoot(list.getLoot(count));
			}
		}
		return(found);
	}
	
	//Starts the process of writing a string to a text file.
	public void DoWrite(LootList list){
		StringMan string = new StringMan();
		WriteLists file = new WriteLists();
		
		try{
			String fileName = JOptionPane.showInputDialog("Please enter file name:");
			fileName = string.removeTxt(fileName);
			
			file.openFile(fileName);
			file.writeList(list);
			file.closeFile();	
		}
		catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Bad File Name!","ERROR: Bad Name", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){}
	}


	public LootList goldRand(LootList list, int gold) {
		int itemPrice = 0;
		boolean FLAG = false;
		randList.reset();
		
		if(gold == 0){
			return randList;
		}
	
		while (0 < gold) {
			int sameGold = gold;
			itemPrice = doRandom(list, gold);
			//DoRandom will return 1 if item it picked was greater than gold amount
			if(itemPrice == 1){
				gold -= 2;
				FLAG = true;
			}
			if(itemPrice == sameGold){
				gold -= sameGold;
				FLAG = true;
			}
			//Otherwise it subtracts cost of item from gold
			if(FLAG == false){
				gold -= itemPrice;
			}
			FLAG = false;
		}
			
		return randList;
	}
	
	public int doRandom(LootList list, int gold){
		Random generator = new Random();
		int rand = generator.nextInt(list.getSize());
		
		if(gold < list.getLowestValue()){
			return gold;
		}
		if(gold < list.getLoot(rand).getValue()){
			return 1;
		}
		else {
			randList.addLoot(list.getLoot(rand));	
			return list.getLoot(rand).getValue();
		}
		
	}
	
}













