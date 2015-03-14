import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.IllegalStateException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class ListManager {
	static SysMan sysMan = new SysMan();
	static StringMan string = new StringMan();
	static Items itemFunctions = new Items();
	private Scanner input;
	
	public void viewList(String[] lists){ 
		for(int i = 0; i < lists.length; i++){
			String temp = string.removeTxt(lists[i]);
			LootList openFile = readList(temp);
			itemFunctions.displayList(openFile, lists[i]);
		}
	}
	
	public void searchLists(String[] lists){ 
		int numberOfFiles = lists.length;
		LootList results = new LootList();
		
		//gets the search term from the user
		String searchTerm = JOptionPane.showInputDialog("Enter the item your looking for:");
	
		try{
			//loops though all the files and searches for what the user specified.
			for(int i = 0; i < numberOfFiles; i++){
				String temp = string.removeTxt(lists[0]);
				LootList openFile = readList(temp);
				results = itemFunctions.searchList(searchTerm,openFile); //searches the list for the term
			}
			itemFunctions.displayList(results,"ItemsFound");
		} 
		catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "You failed to enter a vaild search term.","ERROR: Bad Search Term", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){}
	}
	
	public String[] getSelectedLists(JList<String> allLists, CheckListManager checkListMan){ 
		int start = 0;
		int size = 0;
		int listSize = allLists.getModel().getSize();	
	    
	    for (int i = 0; i < listSize; i++) {
	    	 boolean isSelected = checkListMan.getSelectionModel().isSelectedIndex(i);
	    	 if(isSelected == true){
	    		 size ++;
	    	 }
	    }	
	    
	    String listNames[] = new String[size];
	    for (int i = 0; i < listSize; i++) {
	    	 boolean isSelected = checkListMan.getSelectionModel().isSelectedIndex(i);
	    	 if(isSelected == true){
	    		 listNames[start] = allLists.getModel().getElementAt(i);
	    		 start ++;
	    	 }
	    }
	    if(listNames.length < 1){
	    	  Arrays.sort(listNames); //Sorts the lists alphabetically 	
	    }
	
		return(listNames);	
	}
	
	public String[] getLists(){
	    String listDir = System.getProperty("user.dir"); //Grabs directory of program + lists\
	    listDir += "\\Lists";
	
	    //Gets the names of the lists from the "Lists" directory.
	    String listNames[] = sysMan.analyzePath(listDir);
		Arrays.sort(listNames); //Sorts the lists alphabetically 
		
		return(listNames);
		
	}

	//Reads in a file based on user input
	public LootList readList(String listName){
		LootList lootList = new LootList();
		
	    String listDir = System.getProperty("user.dir"); //Grabs directory of program + lists\
        listDir += "\\Lists\\";
    
	   //Reads in a file Line by Line.
	    try{     
			 FileInputStream fstream = new FileInputStream( listDir + listName + ".txt" );
			 DataInputStream in = new DataInputStream(fstream);
			 BufferedReader br = new BufferedReader(new InputStreamReader(in));
			 String strLine;
			 
			 //Reads in Line by Line and adds contents to a Array for later processing
			 while ( (strLine = br.readLine()) != null ){
				 	Loot loot = new Loot();
					int start = strLine.indexOf(":");
					int priceStart = string.nthOccurrence(strLine,':',1);
					int end = strLine.indexOf("lbs");
					
					loot.setName(strLine.substring(0,start));
					loot.setValue(Integer.parseInt(strLine.substring(start+1,priceStart-2)));
					loot.setValueType(strLine.substring(priceStart-2,priceStart));
					loot.setWeight(Integer.parseInt(strLine.substring(priceStart+1,end)));	
					lootList.addLoot(loot);
				 }
			 fstream.close();
			 br.close();
			 return lootList;
      } 
      //Catching possible exceptions
      catch ( NoSuchElementException elementException ){
    	 JOptionPane.showMessageDialog(null, "Formated = ItemName:Price:Weight","ERROR: File Improperly Formatted!", JOptionPane.ERROR_MESSAGE);
         input.close();
      } 
      catch ( IllegalStateException stateException ){
    	 JOptionPane.showMessageDialog(null, "Possible file permission issue?","ERROR: Cannot Read From file!", JOptionPane.ERROR_MESSAGE);
      }
      catch ( FileNotFoundException fileNotFoundException ){
    	 JOptionPane.showMessageDialog(null, listDir,"ERROR: File Cannot Be Found!", JOptionPane.ERROR_MESSAGE);
      }
      catch(Exception e){
    	  JOptionPane.showMessageDialog(null, "Unknown Error!","ERROR: Unexpected Error", JOptionPane.ERROR_MESSAGE);
    	  e.printStackTrace(System.out);
      }
      finally{
         if ( input != null )
            input.close();
      }
      return lootList;
   }

	public void randNumItems(String[] selectedLists) {
		Random rand = new Random();
		LootList lootList = new LootList();
		int min = 1;
		int numberOfFiles = selectedLists.length;
		int input[] = new int[numberOfFiles];
		String userInput = null;
		
		try{
			for(int i = 0; i < numberOfFiles; i++){
				//Gets the number of items from the user
				userInput = JOptionPane.showInputDialog("Number of items from " + selectedLists[i] + ":");			
				input[i] = Integer.parseInt(userInput);
			}
			
			userInput = JOptionPane.showInputDialog("Magic Item Chance (Percentage):");			
			lootList.setMagicProb(Integer.parseInt(userInput));
			
			for(int i = 0; i < input.length; i++){
				for(int j = 0; j < input[i]; j++){
					String temp = string.removeTxt(selectedLists[i]);
					LootList openFile = readList(temp);
					int max = openFile.getTotalItems()-1;
					int randomNum = rand.nextInt((max - min) + 1) + min;
					lootList.addLoot(openFile.getLoot(randomNum-1));
				}
			}
			itemFunctions.displayShop(lootList,"Shop");	
		}
		catch (NumberFormatException e) {
		    if(userInput == null){}
		    else{
		    	JOptionPane.showMessageDialog(null, "Please enter a number!","ERROR: Bad Input", JOptionPane.ERROR_MESSAGE);
		    }
		}
		catch(Exception e){}
	}

	public void randGoldValue(String[] selectedLists) {
		LootList lootList = new LootList();
		int numberOfFiles = selectedLists.length;
		int input[] = new int[numberOfFiles];
		String userInput = null;
	
		try{
			for(int i = 0; i < numberOfFiles; i++){
				//Gets the number of items from the user
				userInput = JOptionPane.showInputDialog("Amount of gold from " + selectedLists[i] + ":");
				input[i] = Integer.parseInt(userInput);
				
				String temp = string.removeTxt(selectedLists[i]);
				LootList openFile = readList(temp);
				if(openFile.getLowestValue() >= input[i]){
					JOptionPane.showMessageDialog(null, "The gold value entered for one of the lists is smaller than the cheapest item.\n\n Cannot pick any items," +
					" please try again.\n Lowest value from " + selectedLists[i] + " is " + openFile.getLowestValue() + "gp","ERROR: Bad Gold Value", JOptionPane.ERROR_MESSAGE);
					return;
				}	
			}
			
			userInput = JOptionPane.showInputDialog("Magic Item Chance (Percentage):");			
			lootList.setMagicProb(Integer.parseInt(userInput));
			
			for(int i = 0; i < input.length; i++){
				String temp = string.removeTxt(selectedLists[i]);
				LootList openFile = readList(temp);
				LootList goldRand = new LootList();
				goldRand = itemFunctions.goldRand(openFile,input[i]);
				
				for(int j = 0; j < goldRand.getSize(); j++){
					lootList.addLoot(goldRand.getLoot(j));
				}
			}
			itemFunctions.displayShop(lootList,"Shop");
		}
		catch (NumberFormatException e) {
		    if(userInput == null){}
		    else{
		    	JOptionPane.showMessageDialog(null, "Please enter a number!","ERROR: Bad Input", JOptionPane.ERROR_MESSAGE);
		    }
		}
		catch(Exception e){}
	}
}
	   

