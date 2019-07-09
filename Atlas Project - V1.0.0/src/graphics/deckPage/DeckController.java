package graphics.deckPage;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.DatabaseAdapter;
import gameRules.GameRules;
import graphics.PageController;
import spells.Card;
import spells.CardSorter;
import spells.Deck;
import spells.Spell;

public class DeckController extends PageController implements MouseListener{
	private DeckView view;
	private Deck model;
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		
		 
	}
	public void launchPage(JPanel mainPanel, String back) {
		model = (Deck) GameRules.activePlayer.getDeck();
		view = new DeckView(this,mainPanel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Card temp = ((Card) e.getSource());
		//	if reversed, card is not added to deck. add it
		if(temp.isReversed()) {
			Spell spell = ((Card) e.getSource()).getSpell();
			Card newcard = new Card(spell);
			newcard.setName(newcard.getSpell().getName());
			((Deck) GameRules.activePlayer.getDeck()).getCards().add(newcard);
			this.view.getDeckPane().removeAll();
			((Deck) GameRules.activePlayer.getDeck()).getCards().sort(new CardSorter());
			for(Card c : ((Deck) GameRules.activePlayer.getDeck()).getCards()) {
				c.setName(c.getSpell().getName());
				c.addMouseListener(this);
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
				this.view.getDeckPane().add(c);
			}
			int sizeMultiplier = 1;
			sizeMultiplier += ((Deck) GameRules.activePlayer.getDeck()).getCards().size()/5;
			if(sizeMultiplier> 1) {
				sizeMultiplier--;
			}
			int count = ((Deck) GameRules.activePlayer.getDeck()).getCards().size();
			for(;count<36; count++) {
				this.view.getDeckPane().add(new JLabel());
			}
			this.view.getDeckDescriptor().setText(GameRules.activePlayer.getDeck().getName() + " | " + GameRules.activePlayer.getFaction().name() + " | " 
					+ GameRules.activePlayer.getDeck().getLevelRequirement() + " | Count: " + ((Deck) GameRules.activePlayer.getDeck()).getCards().size());
		}
		else {
			//	must be in deck already... remove it
			((Deck) GameRules.activePlayer.getDeck()).getCards().remove(((Card) e.getSource()));
			this.view.getDeckDescriptor().setText(GameRules.activePlayer.getDeck().getName() + " | " + GameRules.activePlayer.getFaction().name() + " | " 
					+ GameRules.activePlayer.getDeck().getLevelRequirement() + " | Count: " + ((Deck) GameRules.activePlayer.getDeck()).getCards().size());
			this.view.getDeckPane().remove(e.getComponent());
			for(;this.view.getDeckPane().getComponentCount()<36;) {
				this.view.getDeckPane().add(new JLabel());
			}
		}
		//	update deck in db
		try {
			DatabaseAdapter.updateDeck();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.view.getPanel().validate();
		this.view.getPanel().repaint();
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
