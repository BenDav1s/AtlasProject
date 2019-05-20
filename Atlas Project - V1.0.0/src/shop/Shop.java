package shop;

import java.util.Map;

import items.Item;

public class Shop {
	private Map<String,Integer> inventory;
	
	public boolean containsItem(String name) {
		return inventory.containsKey(name) && inventory.get(name) < 0;
	}
	public Item purchase(String name, int gold) {
		Item temp = new Item();
		//	assume valid containsItem check
		
		//	get database and update inventory map
		
		return temp;
		
	}
	
	//	update inventory with database
	public void updateInventory() {
		
	}
	
	//	pull from database
	public void init() {
		
	}
	
	//	load into db
	public void loadDB() {
		
	}
	
	//	add item to shop 
	public void addItem(Item item) {
		if(this.inventory.containsKey(item.getName())) {
			this.inventory.put(item.getName(), this.inventory.get(item.getName())+1);
		}
		else {
			this.inventory.put(item.getName(), 1);
		}
		
		updateInventory();
		
	}
	
}
