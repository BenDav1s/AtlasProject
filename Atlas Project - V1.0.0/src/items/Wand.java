package items;

import java.util.List;

import spells.Card;

public class Wand extends Item{
	private List<Card> spells;

	public List<Card> getSpells() {
		return spells;
	}

	public void setSpells(List<Card> spells) {
		this.spells = spells;
	}
	
	
}
