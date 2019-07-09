package graphics.deckPage;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import character.FactionTypes;
import gameRules.GameRules;
import graphics.combatpage.Pile;
import spells.Card;
import spells.CardSorter;
import spells.Deck;
import spells.Spell;
import spells.SpellType;

public class DeckView {
	private DeckController controller;
	private JPanel panel;
	//	shows deck
	private JPanel leftPanel;
	//	shows filtered faction cards default player's faction
	private JPanel rightPanel;
	
	private JPanel deckPane;
	
	private JLabel deckDescriptor;
	public JLabel getDeckDescriptor() {
		return deckDescriptor;
	}
	public void setDeckDescriptor(JLabel deckDescriptor) {
		this.deckDescriptor = deckDescriptor;
	}
	public DeckController getController() {
		return controller;
	}
	public void setController(DeckController controller) {
		this.controller = controller;
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public JPanel getLeftPanel() {
		return leftPanel;
	}
	public void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
	}
	public JPanel getRightPanel() {
		return rightPanel;
	}
	public void setRightPanel(JPanel rightPanel) {
		this.rightPanel = rightPanel;
	}
	public JPanel getDeckPane() {
		return deckPane;
	}
	public void setDeckPane(JPanel deckPane) {
		this.deckPane = deckPane;
	}
	/*
	 * deck view
	 * responsible for displaying active deck 
	 * and providing user with options for changing out cards
	 * 
	 * deck name : faction : stats : 		JTabbed pane with factions 
	 * 								|						combo box for filtering
	 *  	active cards			|
	 *  							|
	 *    							|
	 *     							|
	 *								|
	 */		
	public DeckView(DeckController deckController, JPanel panel) {
		this.controller = deckController;
		//	panel settings
		this.panel = panel;
		this.panel.setOpaque(false);
		
		//	layout
		GridLayout masterLayout = new GridLayout(1,2);
		this.panel.setLayout(masterLayout);
		
		//	left panel
		this.leftPanel= new JPanel();
		this.leftPanel.setOpaque(false);
		this.leftPanel.setLayout(new BoxLayout(this.leftPanel,BoxLayout.PAGE_AXIS));
		
		deckDescriptor = new JLabel(GameRules.activePlayer.getDeck().getName() + " | " + GameRules.activePlayer.getFaction().name() + " | " 
				+ GameRules.activePlayer.getDeck().getLevelRequirement() + " | Count: " + ((Deck) GameRules.activePlayer.getDeck()).getCards().size());
		deckDescriptor.setFont(graphicTools.Fonts.getFont(14f));
		deckDescriptor.setForeground(graphicTools.Colors.Yellow);
		
		this.leftPanel.add(deckDescriptor);
		this.leftPanel.add(Box.createRigidArea(new Dimension(0,3)));
		
		deckPane = new JPanel();
		GridLayout deckPaneLayout = new GridLayout(0,5);
		deckPaneLayout.setHgap(10);
		deckPaneLayout.setVgap(10);
		deckPane.setLayout(deckPaneLayout);
		
		//	sort cards and add to panel
		//	hard coded for spell testing
		
		((Deck) GameRules.activePlayer.getDeck()).getCards().sort(new CardSorter());
		for(Card c : ((Deck) GameRules.activePlayer.getDeck()).getCards()) {
			c.setName(c.getSpell().getName());
			c.addMouseListener(this.controller);
			c.show();
			c.hideInfo();
			c.setSize(new Dimension(50,50));
			String spellText ="<html> Name: "+ c.getName() + " <br> Faction: " +c.getSpell().getFaction().name() + " <br> Pips: " + c.getSpell().getPips() + " <br> Chance to cast: " + c.getSpell().getCastChance() + " <br> ";
			switch(c.getSpell().getType().name()) {
			case "Attack": spellText+= "Damage: " +c.getSpell().getDamage() + " <br> </html> ";
				break;
			case "Attack_All": spellText+="Damage: " + c.getSpell().getDamage() + " to all enemies <br> </html>";
				break;
			case "Heal": spellText+= "Heal: " + c.getSpell().getHealth()+"<br> </html>";
				break;
			case "Heal_ALL": spellText+= "Heal: "+ c.getSpell().getHealth() + " to all teammates <br> </html>";
				break;
			case "Shield": spellText+= "Resist: " + c.getSpell().getResist() + " <br> </html>";
				break;
			case "Shield_ALL": spellText+= "Resist: " + c.getSpell().getResist() + " to all teammates <br> </html>";
				break;
			case "Blade": spellText+= "Boost: " + c.getSpell().getBoost() + " <br> </html>";
				break;
			case "Blade_ALL": spellText+= "Boost: " +c.getSpell().getBoost() + " to all teammates <br> </html>";
				break;
			case "Trap": spellText += "Boost: " +c.getSpell().getBoost() +" <br> </html>";
				break;
			case "Trap_ALL": spellText+= "Boost: " +c.getSpell().getBoost() + " to all enemies <br> </html>";
				break;
			}
			c.setToolTipText(spellText);
			deckPane.add(c);
		}
		int sizeMultiplier = 1;
		sizeMultiplier += ((Deck) GameRules.activePlayer.getDeck()).getCards().size()/5;
		if(sizeMultiplier> 1) {
			sizeMultiplier--;
		}
		int count = ((Deck) GameRules.activePlayer.getDeck()).getCards().size();
		for(;count<36; count++) {
			deckPane.add(new JLabel());
		}
		deckPane.setPreferredSize(new Dimension(200,100*sizeMultiplier));
		JScrollPane deckScrollPane = new JScrollPane(deckPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		deckScrollPane.setOpaque(false);
		deckScrollPane.setPreferredSize(new Dimension(200,600));
		
		this.leftPanel.add(deckScrollPane);
		this.leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//	right panel
		this.rightPanel = new JPanel();
		this.rightPanel.setOpaque(false);
		this.rightPanel.setLayout(new BoxLayout(this.rightPanel,BoxLayout.PAGE_AXIS));
		JLabel factionDescriptor = new JLabel("Sort by Faction");
		factionDescriptor.setFont(graphicTools.Fonts.getFont(14f));
		factionDescriptor.setForeground(graphicTools.Colors.Yellow);
		this.rightPanel.add(factionDescriptor);
		this.rightPanel.add(Box.createRigidArea(new Dimension(0,3)));
		
		JTabbedPane factionPanes = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.WRAP_TAB_LAYOUT);
		factionPanes.setOpaque(false);
		factionPanes.addTab("The_Cult_Of_The_Stars", CardFactionTabProducer.loadCardFaction(this.controller,"The_Cult_Of_The_Stars"));
		factionPanes.addTab("The_Ritualists", CardFactionTabProducer.loadCardFaction(this.controller,"The_Ritualists"));
		factionPanes.addTab("The_Collective", CardFactionTabProducer.loadCardFaction(this.controller,"The_Collective"));
		factionPanes.addTab("The_Children_Of_The_Mask", CardFactionTabProducer.loadCardFaction(this.controller,"The_Children_Of_The_Mask"));
		factionPanes.addTab("The_Legion", CardFactionTabProducer.loadCardFaction(this.controller,"The_Legion"));
		factionPanes.addTab("The_Ennead", CardFactionTabProducer.loadCardFaction(this.controller,"The_Ennead"));
		factionPanes.addTab("The_Called", CardFactionTabProducer.loadCardFaction(this.controller,"The_Called"));
		factionPanes.addTab("Generic", CardFactionTabProducer.loadCardFaction(this.controller,"Generic"));
		this.rightPanel.add(factionPanes);
		this.rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		this.panel.add(leftPanel);
		this.panel.add(rightPanel);
		this.panel.validate();
		this.panel.repaint();
	}
}
