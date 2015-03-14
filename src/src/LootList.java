import java.util.ArrayList;

public class LootList {   	
	public  ArrayList<Loot> allLoot = new ArrayList<Loot>();
	public int totalItems = 0;
	public int magicProb = 0;
	public int totalValue = 0;
	public int lowestValue = -1;
	public int totalWeight = 0;
	
	public LootList(){
		totalItems = 0;
		magicProb = 0;
		totalValue = 0;
		lowestValue = -1;
		totalWeight = 0;
	}
	
	public void addLoot (Loot item) {
		boolean add = true;
		
		for(int i = 0; i < allLoot.size(); i++){
			if(allLoot.get(i).getName().equals(item.getName())){
				allLoot.get(i).setQuantity(allLoot.get(i).getQuantity() + 1);
				add = false;
			}
		}
		if(add == true){
			allLoot.add(item);
		}
		totalItems ++;
		totalValue += item.getValue();
		totalWeight += item.getWeight();
		
		if(lowestValue == -1){
			lowestValue = item.getValue();
		}
		else{
			if(item.getValue() < lowestValue){
				lowestValue = item.getValue();
			}
		}
	}
	
	public Loot getLoot (int i) {
		return allLoot.get(i);
	}
	
	public int getSize () {
		return allLoot.size();
	}
	public int getLowestValue () {
		return lowestValue;
	}

	public void removeLoot (Loot item) {
		allLoot.remove(item);
		totalItems --;
		totalValue -= item.getValue();
		totalWeight -= item.getWeight();
	}
	
	public int getTotalItems () {
		return totalItems;
	}
	public void setTotalItems (int total) {
		totalItems = total;
	}

	public int getMagicProb () {
		return magicProb;
	}
	public void setMagicProb (int probability) {
		magicProb = probability;
	}
	
	public int getTotalValue () {
		return totalValue;
	}
	public void setTotalValue (int gold) {
		totalValue = gold;
	}
	
	public int getTotalWeight () {
		return totalWeight;
	}
	public void setTotalWeight (int weight) {
		totalWeight = weight;
	}

	public void reset() {
		allLoot.clear();
		totalItems = 0;
		magicProb = 0;
		totalValue = 0;
		lowestValue = -1;
		totalWeight = 0;
	}
}
