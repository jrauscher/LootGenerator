
public class Loot {
	public String name = "";
	public int magic = 0;
	public int value = 0;
	public String valueType = "";
	public int weight = 0;
	public int quantity = 1;
	
	public Loot(){
		name = "";
		magic = 0;
		value = 0;
		valueType = "";
		weight = 0;
		quantity = 1;
	}
	
	public int getMagic () {
		return weight;
	}
	public void setMagic(int isMagic) {
		magic = isMagic;
	}
	
	public String getName () {
		return name;
	}
	public void setName(String newName) {
		name = newName;
	}
	
	public int getValue () {
		return value;
	}
	public void setValue(int val) {
		value = val;
	}
	public String getValueType () {
		return name;
	}
	public void setValueType(String type) {
		valueType = type;
	}
	
	public int getWeight () {
		return weight;
	}
	public void setWeight(int weight1) {
		weight = weight1;
	}
	
	public int getQuantity () {
		return quantity;
	}
	public void setQuantity(int quant) {
		quantity = quant;
	}
	
	public void reset() {
		name = "";
		magic = 0;
		value = 0;
		valueType = "";
		weight = 0;
		quantity = 1;
	}
}

