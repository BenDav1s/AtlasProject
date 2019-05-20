package character;

import java.util.List;

import statistics.Stats;

public class Player {
	private String name;
	private int gold;
	private Stats stats;
	private List<Player> friendsList;
	private Character character;
	private Backpack backpack;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public Stats getStats() {
		return stats;
	}
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	public List<Player> getFriendsList() {
		return friendsList;
	}
	public void setFriendsList(List<Player> friendsList) {
		this.friendsList = friendsList;
	}
	public Character getCharacter() {
		return character;
	}
	public void setCharacter(Character character) {
		this.character = character;
	}
	public Backpack getBackpack() {
		return backpack;
	}
	public void setBackpack(Backpack backpack) {
		this.backpack = backpack;
	}
	
	
}
