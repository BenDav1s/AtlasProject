package character;

import java.util.List;

import items.Item;

public class Backpack {
	private List<Item> items;
	private int maxSize;
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
}
