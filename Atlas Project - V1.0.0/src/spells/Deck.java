package spells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import items.Item;

public class Deck extends Item{
	private List<Card> spells;
	private int maxSize;
	public Deck(List<Spell>spells) {
		this.spells = new ArrayList<Card>();
		for(Spell s : spells) {
			this.spells.add(new Card(s));
		}
		
	}
	public List<Card> getCards(){
		return this.spells;
	}
	public void addSpell(Spell s) {
		this.spells.add(new Card(s));
	}
	
	public int getSize() {
		return this.spells.size();
	}
	public Card getTop() {
		return this.spells.get(0);
	}
	public Card drawCard() {
		Card temp  = this.getTop();
		this.spells.remove(0);
		return temp;
	}
	public void removeSpell(Spell s) {
		this.spells.remove(s);
	}
	public void shuffle() {
		// TODO Auto-generated method stub
		Collections.shuffle(this.spells);
	}
}
