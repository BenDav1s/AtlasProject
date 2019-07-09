package graphics.inventoryPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import character.Player;
import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.deckPage.DeckController;
import items.Item;
import items.Item.ItemType;
import resources.BackgroundPanel;
import resources.CustomCursor;
import resources.ResourceManager;
import statistics.Stats;
import character.FactionTypes;
public class InventoryPageView {

	private InventoryPageController inventoryController;
	private JFrame frame;
	private JPanel panel;
	private JTabbedPane tabs;
	private JPanel statsPanel;
	private JTabbedPane backpackPanel;
	private JPanel viewItemPanel;
	private JTabbedPane deckPanel;
	private JPanel viewCard;
	private JPanel itemPaneH;
	private JPanel itemPaneA;
	private JPanel itemPaneB;
	private JPanel itemPaneW;
	private JPanel itemPaneR;
	private JPanel itemPaneD;
	private JPanel itemPaneAT;
	
	
	public InventoryPageController getInventoryController() {
		return inventoryController;
	}


	public void setInventoryController(InventoryPageController inventoryController) {
		this.inventoryController = inventoryController;
	}


	public JFrame getFrame() {
		return frame;
	}


	public void setFrame(JFrame frame) {
		this.frame = frame;
	}


	public JPanel getPanel() {
		return panel;
	}


	public void setPanel(JPanel panel) {
		this.panel = panel;
	}


	public JTabbedPane getTabs() {
		return tabs;
	}


	public void setTabs(JTabbedPane tabs) {
		this.tabs = tabs;
	}


	public JPanel getStatsPanel() {
		return statsPanel;
	}


	public void setStatsPanel(JPanel statsPanel) {
		this.statsPanel = statsPanel;
	}


	public JTabbedPane getBackpackPanel() {
		return backpackPanel;
	}


	public void setBackpackPanel(JTabbedPane backpackPanel) {
		this.backpackPanel = backpackPanel;
	}


	public JPanel getViewItemPanel() {
		return viewItemPanel;
	}


	public void setViewItemPanel(JPanel viewItemPanel) {
		this.viewItemPanel = viewItemPanel;
	}


	public JTabbedPane getDeckPanel() {
		return deckPanel;
	}


	public void setDeckPanel(JTabbedPane deckPanel) {
		this.deckPanel = deckPanel;
	}


	public JPanel getViewCard() {
		return viewCard;
	}


	public void setViewCard(JPanel viewCard) {
		this.viewCard = viewCard;
	}


	public JPanel getItemPaneH() {
		return itemPaneH;
	}


	public void setItemPaneH(JPanel itemPaneH) {
		this.itemPaneH = itemPaneH;
	}


	public JPanel getItemPaneA() {
		return itemPaneA;
	}


	public void setItemPaneA(JPanel itemPaneA) {
		this.itemPaneA = itemPaneA;
	}


	public JPanel getItemPaneB() {
		return itemPaneB;
	}


	public void setItemPaneB(JPanel itemPaneB) {
		this.itemPaneB = itemPaneB;
	}


	public JPanel getItemPaneW() {
		return itemPaneW;
	}


	public void setItemPaneW(JPanel itemPaneW) {
		this.itemPaneW = itemPaneW;
	}


	public JPanel getItemPaneR() {
		return itemPaneR;
	}


	public void setItemPaneR(JPanel itemPaneR) {
		this.itemPaneR = itemPaneR;
	}


	public JPanel getItemPaneD() {
		return itemPaneD;
	}


	public void setItemPaneD(JPanel itemPaneD) {
		this.itemPaneD = itemPaneD;
	}


	public JPanel getItemPaneAT() {
		return itemPaneAT;
	}


	public void setItemPaneAT(JPanel itemPaneAT) {
		this.itemPaneAT = itemPaneAT;
	}


	public InventoryPageView(InventoryPageController inventoryPageController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.frame = mainFrame;
		this.inventoryController = inventoryPageController;
		this.frame.setContentPane(this.panel = new BackgroundPanel("background8"));
		this.panel.setPreferredSize(new Dimension(1080,720));
		//this.frame.setLayout(new BorderLayout());
		tabs = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.WRAP_TAB_LAYOUT);
		tabs.setOpaque(false);
		//	inventory page
		backpackPanel = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.WRAP_TAB_LAYOUT);
		backpackPanel.setOpaque(false);
		
		
		//	tabs for each item type
		
		itemPaneH = new JPanel();
		itemPaneA = new JPanel();
		itemPaneB = new JPanel();
		itemPaneW = new JPanel();
		itemPaneR = new JPanel();
		itemPaneD = new JPanel();
		itemPaneAT = new JPanel();
		
		backpackPanel.addTab("Helmet", InventoryProducer.getPanel("helmet", itemPaneH,inventoryController));
		backpackPanel.addTab("Armor", InventoryProducer.getPanel("armor", itemPaneA,inventoryController));
		backpackPanel.addTab("Boots", InventoryProducer.getPanel("boots", itemPaneB,inventoryController));
		backpackPanel.addTab("Wand", InventoryProducer.getPanel("wand", itemPaneW,inventoryController));
		backpackPanel.addTab("Deck", InventoryProducer.getPanel("deck", itemPaneD,inventoryController));
		backpackPanel.addTab("Athame", InventoryProducer.getPanel("athame", itemPaneAT,inventoryController));
		backpackPanel.addTab("Ring", InventoryProducer.getPanel("ring", itemPaneR,inventoryController));
		
		tabs.addTab("Backpack",backpackPanel);
		
		//	stats page
		statsPanel = new JPanel();
		statsPanel.setLayout(new GridLayout(7,8));
		statsPanel.setOpaque(false);
		// 	layer 1 name -> class -> level -> blanks
		JLabel name = new JLabel(GameRules.activePlayer.getName());
		name.setFont(graphicTools.Fonts.getFont(10f));
		name.setForeground(graphicTools.Colors.Yellow);
		
		JLabel faction = new JLabel("Faction : ");
		faction.setFont(graphicTools.Fonts.getFont(10f));
		faction.setForeground(graphicTools.Colors.Yellow);
		
		JLabel factionname = new JLabel(GameRules.activePlayer.getFaction().name());
		factionname.setFont(graphicTools.Fonts.getFont(10f));
		factionname.setForeground(graphicTools.Colors.Yellow);
		
		JLabel level = new JLabel("Level " + GameRules.activePlayer.getLevel());
		level.setFont(graphicTools.Fonts.getFont(10f));
		level.setForeground(graphicTools.Colors.Yellow);
		statsPanel.add(name);
		statsPanel.add(faction);
		statsPanel.add(factionname);
		for(int i  =0 ; i < 4; i++) {
			statsPanel.add(new JLabel());
		}statsPanel.add(level);
		//	layer 2 xp -> health -> pip chance
		JLabel xp = new JLabel("XP: " + GameRules.activePlayer.getXp());
		xp.setFont(graphicTools.Fonts.getFont(10f));
		xp.setForeground(graphicTools.Colors.Yellow);
		
		JLabel health = new JLabel("Health: " + GameRules.activePlayer.getMaxHP());
		health.setFont(graphicTools.Fonts.getFont(10f));
		health.setForeground(graphicTools.Colors.Yellow);
		JLabel pips = new JLabel("Extra Pip % ");
		pips.setFont(graphicTools.Fonts.getFont(10f));
		pips.setForeground(graphicTools.Colors.Yellow);
		JLabel pipAmt = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("PowerPips")));
		pipAmt.setFont(graphicTools.Fonts.getFont(10f));
		pipAmt.setForeground(graphicTools.Colors.Yellow);
		statsPanel.add(xp);
		statsPanel.add(health);
		statsPanel.add(pips);
		statsPanel.add(pipAmt);
		for(int i  = 0; i < 4; i ++) {
			statsPanel.add(new JLabel());
		}
		//	layer 3 factions
		JLabel fact = new JLabel("Factions");
		fact.setFont(graphicTools.Fonts.getFont(10f));
		fact.setForeground(graphicTools.Colors.Yellow);
		
		JLabel	The_Cult_Of_The_Stars = new JLabel("The_Cult_Of_The_Stars"), //	future tech 
		The_Ritualists= new JLabel("The_Ritualists"),	//	demonics
		The_Collective= new JLabel("The_Collective"),	//	victorian era
		The_Children_Of_The_Mask= new JLabel("The_Children_Of_The_Mask"),	//	mayan 
		The_Legion= new JLabel("The_Legion"),	//	greco - roman
		The_Ennead= new JLabel("The_Ennead"),	//	egpytians
		The_Called = new JLabel("The_Called");
		
		The_Cult_Of_The_Stars.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars.setSize(new Dimension(200,50));
		The_Cult_Of_The_Stars.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists.setForeground(graphicTools.Colors.Yellow);
		The_Ritualists.setSize(new Dimension(200,50));
		The_Collective.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective.setForeground(graphicTools.Colors.Yellow);
		The_Collective.setSize(new Dimension(200,50));
		The_Children_Of_The_Mask.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask.setForeground(graphicTools.Colors.Yellow);
		The_Children_Of_The_Mask.setSize(new Dimension(200,50));
		The_Legion.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion.setForeground(graphicTools.Colors.Yellow);
		The_Legion.setSize(new Dimension(200,50));
		The_Ennead.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead.setForeground(graphicTools.Colors.Yellow);
		The_Ennead.setSize(new Dimension(200,50));
		The_Called.setFont(graphicTools.Fonts.getFont(10f));
		The_Called.setForeground(graphicTools.Colors.Yellow);
		The_Called.setSize(new Dimension(200,50));
		
		statsPanel.add(fact);
		statsPanel.add(The_Cult_Of_The_Stars);
		statsPanel.add(The_Ritualists);
		statsPanel.add(The_Collective);
		statsPanel.add(The_Children_Of_The_Mask);
		statsPanel.add(The_Legion);
		statsPanel.add(The_Ennead);
		statsPanel.add(The_Called);
		//	layer 3 boost for each
		JLabel	The_Cult_Of_The_Stars_DMG = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Cult_Of_The_Stars_DMG")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))), //	future tech 
				The_Ritualists_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ritualists_DMG")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	demonics
				The_Collective_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Collective_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	victorian era
				The_Children_Of_The_Mask_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Children_Of_The_Mask_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	mayan 
				The_Legion_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Legion_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	greco - roman
				The_Ennead_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ennead_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	egpytians
				The_Called_DMG = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Called_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG")));
		
		The_Cult_Of_The_Stars_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Called_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Called_DMG.setForeground(graphicTools.Colors.Yellow);
		
		JLabel damageBoost = new JLabel("Damage boost: ");
		damageBoost.setFont(graphicTools.Fonts.getFont(10f));
		damageBoost.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(damageBoost);
		statsPanel.add(The_Cult_Of_The_Stars_DMG);
		statsPanel.add(The_Ritualists_DMG);
		statsPanel.add(The_Collective_DMG);
		statsPanel.add(The_Children_Of_The_Mask_DMG);
		statsPanel.add(The_Legion_DMG);
		statsPanel.add(The_Ennead_DMG);
		statsPanel.add(The_Called_DMG);
		
//		layer 4 resist for each
		JLabel	The_Cult_Of_The_Stars_RESIST = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Cult_Of_The_Stars_RESIST")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))), //	future tech 
				The_Ritualists_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ritualists_RESIST")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	demonics
				The_Collective_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Collective_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	victorian era
				The_Children_Of_The_Mask_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Children_Of_The_Mask_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	mayan 
				The_Legion_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Legion_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	greco - roman
				The_Ennead_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ennead_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	egpytians
				The_Called_RESIST = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Called_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST")));
		
		The_Cult_Of_The_Stars_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Called_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Called_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		JLabel resist = new JLabel("Resist: ");
		resist.setFont(graphicTools.Fonts.getFont(10f));
		resist.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(resist);
		statsPanel.add(The_Cult_Of_The_Stars_RESIST);
		statsPanel.add(The_Ritualists_RESIST);
		statsPanel.add(The_Collective_RESIST);
		statsPanel.add(The_Children_Of_The_Mask_RESIST);
		statsPanel.add(The_Legion_RESIST);
		statsPanel.add(The_Ennead_RESIST);
		statsPanel.add(The_Called_RESIST);
		
		//	layer 5 critical for each
		JLabel	The_Cult_Of_The_Stars_CRIT = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Cult_Of_The_Stars_CRIT")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))), //	future tech 
				The_Ritualists_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ritualists_CRIT")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	demonics
				The_Collective_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Collective_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	victorian era
				The_Children_Of_The_Mask_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Children_Of_The_Mask_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	mayan 
				The_Legion_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Legion_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	greco - roman
				The_Ennead_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ennead_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	egpytians
				The_Called_CRIT = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Called_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT")));
		
		The_Cult_Of_The_Stars_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Called_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Called_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		JLabel critical = new JLabel("Critical: ");
		critical.setFont(graphicTools.Fonts.getFont(10f));
		critical.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(critical);
		statsPanel.add(The_Cult_Of_The_Stars_CRIT);
		statsPanel.add(The_Ritualists_CRIT);
		statsPanel.add(The_Collective_CRIT);
		statsPanel.add(The_Children_Of_The_Mask_CRIT);
		statsPanel.add(The_Legion_CRIT);
		statsPanel.add(The_Ennead_CRIT);
		statsPanel.add(The_Called_CRIT);
		//	layer 6 critical block for each
		JLabel	The_Cult_Of_The_Stars_CRIT_BLOCK = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Cult_Of_The_Stars_CRIT_BLOCK")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))), //	future tech 
				The_Ritualists_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ritualists_CRIT_BLOCK")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	demonics
				The_Collective_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Collective_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	victorian era
				The_Children_Of_The_Mask_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Children_Of_The_Mask_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	mayan 
				The_Legion_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Legion_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	greco - roman
				The_Ennead_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ennead_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	egpytians
				The_Called_CRIT_BLOCK = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Called_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK")));
		
		The_Cult_Of_The_Stars_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Called_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Called_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		JLabel critBlock = new JLabel("Critical block: ");
		critBlock.setFont(graphicTools.Fonts.getFont(10f));
		critBlock.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(critBlock);
		statsPanel.add(The_Cult_Of_The_Stars_CRIT_BLOCK);
		statsPanel.add(The_Ritualists_CRIT_BLOCK);
		statsPanel.add(The_Collective_CRIT_BLOCK);
		statsPanel.add(The_Children_Of_The_Mask_CRIT_BLOCK);
		statsPanel.add(The_Legion_CRIT_BLOCK);
		statsPanel.add(The_Ennead_CRIT_BLOCK);
		statsPanel.add(The_Called_CRIT_BLOCK);
		statsPanel.setPreferredSize(new Dimension(720,480));
		tabs.addTab("Stats", statsPanel);
		
		
		//	add deck view
		JPanel deckpage = new JPanel();
		DeckController deckController = new DeckController();
		deckController.launchPage(deckpage, "");
		
		tabs.addTab("Deck Editor", deckpage);
		tabs.revalidate();
		tabs.repaint();
		this.frame.getContentPane().add(tabs);
		
		JButton exit = new JButton("Back");
		exit.setPreferredSize(new Dimension(200,50));
		exit.setContentAreaFilled(false);
		exit.setFont(graphicTools.Fonts.getFont(14f));
		exit.setForeground(graphicTools.Colors.Yellow);
		exit.setActionCommand("back");
		exit.addActionListener(this.inventoryController);
		
		tabs.addTab("Main Menu",exit);
		//this.frame.getContentPane().add(exit);
		
		
		this.frame.pack();
		this.frame.setVisible(true);
		
	}


	public void updateStats() {
		// TODO Auto-generated method stub
		statsPanel.removeAll();
		GridLayout layout = new GridLayout(7,8);
		layout.setVgap(20);
		layout.setHgap(20);
		statsPanel.setLayout(layout);
		
		statsPanel.setOpaque(false);
		// 	layer 1 name -> class -> level -> blanks
		JLabel name = new JLabel(GameRules.activePlayer.getName());
		name.setFont(graphicTools.Fonts.getFont(10f));
		name.setForeground(graphicTools.Colors.Yellow);
		
		JLabel faction = new JLabel("Faction : ");
		faction.setFont(graphicTools.Fonts.getFont(10f));
		faction.setForeground(graphicTools.Colors.Yellow);
		
		JLabel factionname = new JLabel(GameRules.activePlayer.getFaction().name());
		factionname.setFont(graphicTools.Fonts.getFont(10f));
		factionname.setForeground(graphicTools.Colors.Yellow);
		
		JLabel level = new JLabel("Level " + GameRules.activePlayer.getLevel());
		level.setFont(graphicTools.Fonts.getFont(10f));
		level.setForeground(graphicTools.Colors.Yellow);
		statsPanel.add(name);
		statsPanel.add(faction);
		statsPanel.add(factionname);
		for(int i  =0 ; i < 4; i++) {
			statsPanel.add(new JLabel());
		}statsPanel.add(level);
		//	layer 2 xp -> health -> pip chance
		JLabel xp = new JLabel("XP: " + GameRules.activePlayer.getXp());
		xp.setFont(graphicTools.Fonts.getFont(10f));
		xp.setForeground(graphicTools.Colors.Yellow);
		
		JLabel health = new JLabel("Health: " + GameRules.activePlayer.getMaxHP());
		health.setFont(graphicTools.Fonts.getFont(10f));
		health.setForeground(graphicTools.Colors.Yellow);
		JLabel pips = new JLabel("Extra Pip % ");
		pips.setFont(graphicTools.Fonts.getFont(10f));
		pips.setForeground(graphicTools.Colors.Yellow);
		JLabel pipAmt = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("PowerPips")));
		pipAmt.setFont(graphicTools.Fonts.getFont(10f));
		pipAmt.setForeground(graphicTools.Colors.Yellow);
		statsPanel.add(xp);
		statsPanel.add(health);
		statsPanel.add(pips);
		statsPanel.add(pipAmt);
		for(int i  = 0; i < 4; i ++) {
			statsPanel.add(new JLabel());
		}
		//	layer 3 factions
		JLabel fact = new JLabel("Factions");
		fact.setFont(graphicTools.Fonts.getFont(10f));
		fact.setForeground(graphicTools.Colors.Yellow);
		
		JLabel	The_Cult_Of_The_Stars = new JLabel("The_Cult_Of_The_Stars"), //	future tech 
		The_Ritualists= new JLabel("The_Ritualists"),	//	demonics
		The_Collective= new JLabel("The_Collective"),	//	victorian era
		The_Children_Of_The_Mask= new JLabel("The_Children_Of_The_Mask"),	//	mayan 
		The_Legion= new JLabel("The_Legion"),	//	greco - roman
		The_Ennead= new JLabel("The_Ennead"),	//	egpytians
		The_Called = new JLabel("The_Called");
		
		The_Cult_Of_The_Stars.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead.setForeground(graphicTools.Colors.Yellow);
		
		The_Called.setFont(graphicTools.Fonts.getFont(10f));
		The_Called.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(fact);
		statsPanel.add(The_Cult_Of_The_Stars);
		statsPanel.add(The_Ritualists);
		statsPanel.add(The_Collective);
		statsPanel.add(The_Children_Of_The_Mask);
		statsPanel.add(The_Legion);
		statsPanel.add(The_Ennead);
		statsPanel.add(The_Called);
		//	layer 3 boost for each
		JLabel	The_Cult_Of_The_Stars_DMG = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Cult_Of_The_Stars_DMG")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))), //	future tech 
				The_Ritualists_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ritualists_DMG")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	demonics
				The_Collective_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Collective_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	victorian era
				The_Children_Of_The_Mask_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Children_Of_The_Mask_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	mayan 
				The_Legion_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Legion_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	greco - roman
				The_Ennead_DMG= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ennead_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG"))),	//	egpytians
				The_Called_DMG = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Called_DMG")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_DMG")));
		
		The_Cult_Of_The_Stars_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead_DMG.setForeground(graphicTools.Colors.Yellow);
		
		The_Called_DMG.setFont(graphicTools.Fonts.getFont(10f));
		The_Called_DMG.setForeground(graphicTools.Colors.Yellow);
		
		JLabel damageBoost = new JLabel("Damage boost: ");
		damageBoost.setFont(graphicTools.Fonts.getFont(10f));
		damageBoost.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(damageBoost);
		statsPanel.add(The_Cult_Of_The_Stars_DMG);
		statsPanel.add(The_Ritualists_DMG);
		statsPanel.add(The_Collective_DMG);
		statsPanel.add(The_Children_Of_The_Mask_DMG);
		statsPanel.add(The_Legion_DMG);
		statsPanel.add(The_Ennead_DMG);
		statsPanel.add(The_Called_DMG);
		
//		layer 4 resist for each
		JLabel	The_Cult_Of_The_Stars_RESIST = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Cult_Of_The_Stars_RESIST")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))), //	future tech 
				The_Ritualists_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ritualists_RESIST")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	demonics
				The_Collective_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Collective_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	victorian era
				The_Children_Of_The_Mask_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Children_Of_The_Mask_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	mayan 
				The_Legion_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Legion_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	greco - roman
				The_Ennead_RESIST= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ennead_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST"))),	//	egpytians
				The_Called_RESIST = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Called_RESIST")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_RESIST")));
		
		The_Cult_Of_The_Stars_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		The_Called_RESIST.setFont(graphicTools.Fonts.getFont(10f));
		The_Called_RESIST.setForeground(graphicTools.Colors.Yellow);
		
		JLabel resist = new JLabel("Resist: ");
		resist.setFont(graphicTools.Fonts.getFont(10f));
		resist.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(resist);
		statsPanel.add(The_Cult_Of_The_Stars_RESIST);
		statsPanel.add(The_Ritualists_RESIST);
		statsPanel.add(The_Collective_RESIST);
		statsPanel.add(The_Children_Of_The_Mask_RESIST);
		statsPanel.add(The_Legion_RESIST);
		statsPanel.add(The_Ennead_RESIST);
		statsPanel.add(The_Called_RESIST);
		
		//	layer 5 critical for each
		JLabel	The_Cult_Of_The_Stars_CRIT = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Cult_Of_The_Stars_CRIT")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))), //	future tech 
				The_Ritualists_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ritualists_CRIT")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	demonics
				The_Collective_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Collective_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	victorian era
				The_Children_Of_The_Mask_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Children_Of_The_Mask_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	mayan 
				The_Legion_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Legion_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	greco - roman
				The_Ennead_CRIT= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ennead_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT"))),	//	egpytians
				The_Called_CRIT = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Called_CRIT")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT")));
		
		The_Cult_Of_The_Stars_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		The_Called_CRIT.setFont(graphicTools.Fonts.getFont(10f));
		The_Called_CRIT.setForeground(graphicTools.Colors.Yellow);
		
		JLabel critical = new JLabel("Critical: ");
		critical.setFont(graphicTools.Fonts.getFont(10f));
		critical.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(critical);
		statsPanel.add(The_Cult_Of_The_Stars_CRIT);
		statsPanel.add(The_Ritualists_CRIT);
		statsPanel.add(The_Collective_CRIT);
		statsPanel.add(The_Children_Of_The_Mask_CRIT);
		statsPanel.add(The_Legion_CRIT);
		statsPanel.add(The_Ennead_CRIT);
		statsPanel.add(The_Called_CRIT);
		//	layer 6 critical block for each
		JLabel	The_Cult_Of_The_Stars_CRIT_BLOCK = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Cult_Of_The_Stars_CRIT_BLOCK")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))), //	future tech 
				The_Ritualists_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ritualists_CRIT_BLOCK")
				+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	demonics
				The_Collective_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Collective_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	victorian era
				The_Children_Of_The_Mask_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Children_Of_The_Mask_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	mayan 
				The_Legion_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Legion_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	greco - roman
				The_Ennead_CRIT_BLOCK= new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Ennead_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK"))),	//	egpytians
				The_Called_CRIT_BLOCK = new JLabel(String.valueOf(GameRules.activePlayer.getStats().getEffect("The_Called_CRIT_BLOCK")
						+ GameRules.activePlayer.getStats().getEffect("GENERIC_CRIT_BLOCK")));
		
		The_Cult_Of_The_Stars_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Cult_Of_The_Stars_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Ritualists_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Ritualists_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Collective_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Collective_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Children_Of_The_Mask_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Children_Of_The_Mask_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Legion_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Legion_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Ennead_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Ennead_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		The_Called_CRIT_BLOCK.setFont(graphicTools.Fonts.getFont(10f));
		The_Called_CRIT_BLOCK.setForeground(graphicTools.Colors.Yellow);
		
		JLabel critBlock = new JLabel("Critical block: ");
		critBlock.setFont(graphicTools.Fonts.getFont(10f));
		critBlock.setForeground(graphicTools.Colors.Yellow);
		
		statsPanel.add(critBlock);
		statsPanel.add(The_Cult_Of_The_Stars_CRIT_BLOCK);
		statsPanel.add(The_Ritualists_CRIT_BLOCK);
		statsPanel.add(The_Collective_CRIT_BLOCK);
		statsPanel.add(The_Children_Of_The_Mask_CRIT_BLOCK);
		statsPanel.add(The_Legion_CRIT_BLOCK);
		statsPanel.add(The_Ennead_CRIT_BLOCK);
		statsPanel.add(The_Called_CRIT_BLOCK);
		statsPanel.validate();
		statsPanel.repaint();
		
		frame.pack();
		frame.setVisible(true);
	}

}
