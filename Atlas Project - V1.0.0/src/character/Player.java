package character;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import items.Item;
import quest.Quest;
import resources.BackgroundPanel;
import spells.Deck;
import spells.Spell;
import statistics.Stats;

public class Player {
	private String name;
	private String playerID;
	private int level;
	private int maxHP;
	
	public String getPlayerID() {
		return playerID;
	}
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	private int xp;
	private int gold;
	private Stats stats;
	private List<Player> friendsList;
	private Character character;
	private Backpack backpack = new Backpack();
	private FactionTypes faction;
	private List<Spell> knownSpells;
	private List<String> knownQuests;
	private List<String> activeQuests;
	private Quest currentQuest;
	private String activeBounty;
	
	private List<String> bountyList = new ArrayList<>();
	
	public List<Spell> getKnownSpells() {
		return knownSpells;
	}
	public void setKnownSpells(List<Spell> knownSpells) {
		this.knownSpells = knownSpells;
	}
	private Item Helmet,
	Armor,
	Boots,
	Athame,
	Ring,
	Wand;
	private Deck deck;
	
	public Item getWand() {
		return Wand;
	}
	public void setWand(Item wand) {
		Wand = wand;
	}
	public spells.Deck getDeck() {
		return (spells.Deck) deck;
	}
	public void setDeck(spells.Deck deck) {
		this.deck = deck;
	}
	public JPanel getPlayerAsPanel() {
		JPanel player = new BackgroundPanel(this.faction.name()+"_faction");
		player.setPreferredSize(new Dimension(300,300));
		JPanel transparent = new JPanel();
		transparent.setBackground(new Color(0.0f,0.0f,0.0f,0.5f));
		transparent.setPreferredSize(new Dimension(300,300));
		JLabel name = new JLabel(" Name: " + this.name);
		name.setFont(graphicTools.Fonts.getFont(14f));
		name.setForeground(graphicTools.Colors.Yellow);
		
		JLabel level = new JLabel(" Level: " + this.getLevel());
		level.setFont(graphicTools.Fonts.getFont(14f));
		level.setForeground(graphicTools.Colors.Yellow);
		
		JLabel myFaction = new JLabel(" Faction: " + this.faction.name());
		myFaction.setFont(graphicTools.Fonts.getFont(14f));
		myFaction.setForeground(graphicTools.Colors.Yellow);
		
		GridLayout masterLayout = new GridLayout(8,1);
		masterLayout.setHgap(15);
		masterLayout.setVgap(15);
		transparent.setLayout(masterLayout);
		
		transparent.add(name);
		transparent.add(myFaction);
		transparent.add(level);
		for(int i = 0; i < 5; i++) {
			transparent.add(new JLabel());
		}
		player.add(transparent);
		return player;
	}
	public Item getHelmet() {
		return Helmet;
	}
	public void setHelmet(Item helmet) {
		Helmet = helmet;
	}
	public Item getArmor() {
		return Armor;
	}
	public void setArmor(Item armor) {
		Armor = armor;
	}
	public Item getBoots() {
		return Boots;
	}
	public void setBoots(Item boots) {
		Boots = boots;
	}
	public Item getAthame() {
		return Athame;
	}
	public void setAthame(Item athame) {
		Athame = athame;
	}
	public Item getRing() {
		return Ring;
	}
	public void setRing(Item ring) {
		Ring = ring;
	}
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
	public FactionTypes getFaction() {
		return faction;
	}
	public void setFaction(FactionTypes faction) {
		this.faction = faction;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public List<String> getKnownQuests() {
		return knownQuests;
	}
	public void setKnownQuests(List<String> knownQuests) {
		this.knownQuests = knownQuests;
	}
	public List<String> getActiveQuests() {
		return activeQuests;
	}
	public void setActiveQuests(List<String> activeQuests) {
		this.activeQuests = activeQuests;
	}
	public Quest getCurrentQuest() {
		return currentQuest;
	}
	public void setCurrentQuest(Quest currentQuest) {
		this.currentQuest = currentQuest;
	}
	public List<String> getBountyList() {
		return bountyList;
	}
	public void setBountyList(List<String> bountyList) {
		this.bountyList = bountyList;
	}
	public String getActiveBounty() {
		// TODO Auto-generated method stub
		return this.activeBounty;
	}
	public void setActiveBounty(String b) {
		this.activeBounty= b;
	}
	
}
