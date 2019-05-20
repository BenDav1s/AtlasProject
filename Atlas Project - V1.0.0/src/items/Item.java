package items;

import java.util.List;

import statistics.Effect;

public class Item {
	private List<Effect> effects;
	private double dropRate;
	private int cost;
	private int worth;
	private boolean sellable;
	private int levelRequirement;
	private String name;
	public enum ItemType{
		Helmet,
		Armor,
		Boots,
		Athame,
		Ring,
		Other;
	}
	public List<Effect> getEffects() {
		return effects;
	}
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
	public double getDropRate() {
		return dropRate;
	}
	public void setDropRate(double dropRate) {
		this.dropRate = dropRate;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getWorth() {
		return worth;
	}
	public void setWorth(int worth) {
		this.worth = worth;
	}
	public boolean isSellable() {
		return sellable;
	}
	public void setSellable(boolean sellable) {
		this.sellable = sellable;
	}
	public int getLevelRequirement() {
		return levelRequirement;
	}
	public void setLevelRequirement(int levelRequirement) {
		this.levelRequirement = levelRequirement;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
