package items;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import statistics.Effect;
import character.FactionTypes;
import gameRules.GameRules;
import graphicTools.Colors;
import graphics.inventoryPage.InventoryPageController;
import resources.BackgroundPanel;
public class Item {
	private Map<String, Double> effectMap = new HashMap<>();
	
	public void addEffect(String n, double amt) {
		effectMap.put(n,amt);
	}
	public double getEffect(String n) {
		double value = 0.00;
		if(effectMap.get(n) == null) {
			return value;
		}
		return effectMap.get(n);
	}
	public Map<String,Double> getEffectMap(){
		return effectMap;
	}
	private double dropRate;
	private int cost;
	private int worth;
	private boolean sellable;
	private int levelRequirement;
	private String name;
	private FactionTypes faction;
	private JButton equipButton;
	private int health;
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void setEffectMap(Map<String, Double> effectMap) {
		this.effectMap = effectMap;
	}
	public enum ItemType{
		Helmet,
		Armor,
		Boots,
		Athame,
		Ring,
		Wand,
		Deck;
	}
	public static ItemType getItemType(String id) {
		if(id.equalsIgnoreCase("Helmet")) return ItemType.Helmet;
		if(id.equalsIgnoreCase("Armor")) return ItemType.Armor;
		if(id.equalsIgnoreCase("Boots")) return ItemType.Boots;
		if(id.equalsIgnoreCase("Athame")) return ItemType.Athame;
		if(id.equalsIgnoreCase("Ring")) return ItemType.Ring;
		if(id.equalsIgnoreCase("Wand")) return ItemType.Wand;
		if(id.equalsIgnoreCase("Deck")) return ItemType.Deck;
		return null;
	}
	public JScrollPane getItemAsPanel(InventoryPageController c) {
		JPanel base = new JPanel();
		base.setBackground(new Color(0.0f,0.0f,0.0f,0.25f));
		GridLayout layout = new GridLayout(0,1);
		layout.setVgap(10);
		layout.setHgap(10);
		base.setLayout(layout);
		
		JLabel name = new JLabel(this.name);
		name.setFont(graphicTools.Fonts.getBoldFont(10f));
		name.setForeground(Color.BLACK);
		
		JLabel level = new JLabel("Level: " + this.levelRequirement);
		level.setFont(graphicTools.Fonts.getBoldFont(10f));
		level.setForeground(Color.BLACK);
		
		JLabel faction = new JLabel("Faction : " + this.faction.name());
		faction.setFont(graphicTools.Fonts.getBoldFont(10f));
		faction.setForeground(Color.BLACK);
		
		
		JLabel amt = new JLabel("Sell for: " + this.worth);
		amt.setFont(graphicTools.Fonts.getBoldFont(10f));
		amt.setForeground(Color.BLACK);
		
		base.add(name);
		base.add(level);
		base.add(faction);
		base.add(amt);
		int sizeModifier= 1;
		
		JButton equip = new JButton();
		switch(this.getWeaponType().name()) {
		case "Helmet":if(GameRules.activePlayer.getHelmet() == null || !GameRules.activePlayer.getHelmet().getName().equalsIgnoreCase(this.name)) {
			equip.setText("Equip");
		}
		else if(GameRules.activePlayer.getHelmet().getName().equalsIgnoreCase(this.name)){
			equip.setText("Un-Equip");
		}
			break;
		case "Armor":if(GameRules.activePlayer.getArmor() == null || !GameRules.activePlayer.getArmor().getName().equalsIgnoreCase(this.name)) {
			equip.setText("Equip");
		}
		else if(GameRules.activePlayer.getArmor().getName().equalsIgnoreCase(this.name)){
			equip.setText("Un-Equip");
		}
			break;
		case "Boots":if(GameRules.activePlayer.getBoots() == null || !GameRules.activePlayer.getBoots().getName().equalsIgnoreCase(this.name)) {
			equip.setText("Equip");
		}
		else if(GameRules.activePlayer.getBoots().getName().equalsIgnoreCase(this.name)){
			equip.setText("Un-Equip");
		}
			break;
		case "Athame":if(GameRules.activePlayer.getAthame() == null || !GameRules.activePlayer.getAthame().getName().equalsIgnoreCase(this.name)) {
			equip.setText("Equip");
		}
		else if(GameRules.activePlayer.getAthame().getName().equalsIgnoreCase(this.name)){
			equip.setText("Un-Equip");
		}
			break;
		case "Ring":if(GameRules.activePlayer.getRing() == null || !GameRules.activePlayer.getRing().getName().equalsIgnoreCase(this.name)) {
			equip.setText("Equip");
		}
		else if(GameRules.activePlayer.getRing().getName().equalsIgnoreCase(this.name)){
			equip.setText("Un-Equip");
		}
			break;
		case "Wand":if(GameRules.activePlayer.getWand() == null || !GameRules.activePlayer.getWand().getName().equalsIgnoreCase(this.name)) {
			equip.setText("Equip");
		}
		else if(GameRules.activePlayer.getWand().getName().equalsIgnoreCase(this.name)){
			equip.setText("Un-Equip");
		}
			break;
		case "Deck":if(GameRules.activePlayer.getDeck() == null || !GameRules.activePlayer.getDeck().getName().equalsIgnoreCase(this.name)) {
			equip.setText("Equip");
		}
		else if(GameRules.activePlayer.getDeck().getName().equalsIgnoreCase(this.name)){
			equip.setText("Un-Equip");
		}
			break;
		}
		
		equip.setFont(graphicTools.Fonts.getBoldFont(14f));
		equip.setForeground(Colors.Yellow);
		equip.setActionCommand("ItemEquipAction");
		equip.addActionListener(c);
		equip.setName(this.name);
		equip.setBackground(new Color(0.0f,0.0f,0.0f,0.3f));
		this.equipButton= equip;
		base.add(equip);
		sizeModifier++;
		for(Entry<String, Double> s : this.effectMap.entrySet()) {
			sizeModifier++;
			JLabel value = new JLabel(s.getKey() + " : " + s.getValue());
			value.setFont(graphicTools.Fonts.getBoldFont(10f));
			value.setForeground(Color.BLACK);
			base.add(value);
		}
		if(sizeModifier >1) {
			sizeModifier--;
		}
		sizeModifier+=4;
		base.setPreferredSize(new Dimension(200,100*sizeModifier));
		JScrollPane temp = new JScrollPane(base,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		temp.setPreferredSize(new Dimension(200,300));
		return temp;
	}
	
	private ItemType weaponType;
	
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
	public ItemType getWeaponType() {
		return weaponType;
	}
	public void setWeaponType(ItemType weaponType) {
		this.weaponType = weaponType;
	}
	public FactionTypes getFaction() {
		return faction;
	}
	public void setFaction(FactionTypes faction) {
		this.faction = faction;
	}
	public JButton getEquipButton() {
		return equipButton;
	}
	public void setEquipButton(JButton equipButton) {
		this.equipButton = equipButton;
	}
	
}
