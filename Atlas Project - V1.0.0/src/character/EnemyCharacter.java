package character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import gameRules.GameRules;
import items.Item;
import resources.ResourceManager;
import spells.Spell;

public class EnemyCharacter extends Character{
	public EnemyCharacter(List<Spell> spellsval, int HPval, String name, FactionTypes clas) {
		super(spellsval, HPval, name, clas);
		// TODO Auto-generated constructor stub
	}
	public EnemyCharacter() {
		// TODO Auto-generated constructor stub
	}
	private List<Item> drops;
	private Map<String,Double> dropmap = new HashMap<>();
	private int xpDrop;
	private int goldDrop;
	private int rank;
	public void setXP(int xp) {
		this.xpDrop = xp;
	}
	public void setGold(int gold) {
		this.goldDrop = gold;
	}
	public int getXp() {
		return xpDrop;
	}
	public int getGold() {
		return goldDrop;
	}
	public Map<String,Double> getDropMap(){
		return dropmap;
	}
	public void setDropMap(Map<String,Double> drop) {
		this.dropmap = drop;
	}
	/*
	 * collectDrops
	 * adds xp gold and items to player inventory
	 * 
	 */
	public void collectDrops() {
		//	xp and gold
		System.out.println(this.getName() + " drops : ");
		GameRules.activePlayer.setXp(GameRules.activePlayer.getXp()+xpDrop);
		GameRules.activePlayer.setGold(GameRules.activePlayer.getGold()+goldDrop);
		System.out.println(xpDrop);
		System.out.println(goldDrop);
		//	items
		GameRules.activePlayer.getBackpack().getItems().addAll(getDrops());
	}
	/*
	 * getDrops
	 * helper function for collect drops
	 * based on strings and percents in drop map, loads drops the player will receive
	 */
	public List<Item> getDrops(){
		List<Item> items = new ArrayList<>();
		for(String s : this.dropmap.keySet()) {
			if(GameRules.accuracy(this.dropmap.get(s))) {
				items.add(ResourceManager.loadItem(s));
			}
		}
		System.out.println(items);
		return items;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
}
