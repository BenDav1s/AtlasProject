package graphics.deckPage;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import character.FactionTypes;
import gameRules.GameRules;
import graphics.combatpage.Pile;
import resources.BackgroundPanel;
import spells.Card;
import spells.CardSorter;
import spells.Deck;
import spells.Spell;

/*
 * card faction tab producer
 * responsible for producing panels with
 * cards the player owns for each faction 
 * and linking to the deck controller
 */
public class CardFactionTabProducer {

	public static JPanel loadCardFaction(DeckController controller, String faction) {
		// TODO Auto-generated method stub
		JPanel background = new JPanel();
		background.setOpaque(false);
		background.setLayout(new BoxLayout(background,BoxLayout.PAGE_AXIS));
		JLabel descriptor = new JLabel(faction);
		descriptor.setFont(graphicTools.Fonts.getFont(14f));
		descriptor.setForeground(graphicTools.Colors.Yellow);
		
		background.add(descriptor);
		background.add(Box.createRigidArea(new Dimension(0,3)));
		
		JPanel deckPane = new JPanel();
		deckPane.setLayout(new GridLayout(0,5));
		//	sort cards and add to panel
		//	load cards player has access to for each faction

		for(Spell spell : GameRules.activePlayer.getKnownSpells()) {
			if(spell.getFaction().name().equalsIgnoreCase(faction)) {
				Card c = new Card(spell);
				c.setName(spell.getName());
				c.addMouseListener(controller);
				c.hide();
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
		}

		while(deckPane.getComponentCount()<36) {
			deckPane.add(new JLabel());
		}
		int sizeMultiplier = 1;
		sizeMultiplier += ((Deck) GameRules.activePlayer.getDeck()).getCards().size()/5;
		deckPane.setPreferredSize(new Dimension(200,100*sizeMultiplier));
		JScrollPane deckScrollPane = new JScrollPane(deckPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		deckScrollPane.setOpaque(false);
		deckScrollPane.setPreferredSize(new Dimension(200,600));
		
		background.add(deckScrollPane);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		return background;
	}

}
