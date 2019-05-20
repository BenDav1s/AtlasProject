package graphics.combatpage;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import spells.Card;


public class Pile extends JLayeredPane {

	Card base;
	ArrayList<Card> cards;
	int offset = 30;
	int width;
	Pile parent;
	PileType type;
	public Card getBase() {
		return this.base;
	}
	enum PileType {Normal, Draw, Get, Final};
	
	/**
	 * Class constructor
	 * @param width
	 */
	public Pile(Card c) {
		cards = new ArrayList<Card>();
		this.width = 240;
		c.setLocation(0, offset);
		base = c;
		add(base,1,0);
		updateSize();
		cards.add(c);
		type = PileType.Normal;
	}
	/**
	 * Adds a new card to the top of the pile.
	 * No checking is done, card is always added
	 * @param {Card} c The card to be added
	 */
	public void addCard(Card c) {
		c.setLocation(0, offset);
		cards.add(c);
		this.add(c, 1, 0);
		updateSize();
	}
	
	/**
	 * Removes a card from the pile
	 * No checking is done, card is always remove
	 * @param {Card} c The card to be removed
	 */
	public void removeCard(Card c) {
		cards.remove(c);
		this.remove(c);
		
		updateSize();
	}
	
	/**
	 * Returns the first card of the pile, without removing it
	 * @return {Card}
	 */
	public Card peekTopCard() {
		return cards.get(cards.size() - 1);
	}
	
	/**
	 * Draws a card from the pile. Pack must not be empty.
	 * @return First card in pack
	 */
	public Card drawCard() {
		Card c = cards.get(0);
		removeCard(c);

		return c;
	}
	
	/**
	 * Sets the width of the pile column.
	 * This is mostyl used for adding padding.
	 */
	public void setWidth(int width) {
		this.width = width;
		updateSize();		
	}
	
	/**
	 * Updates pile size based on the number of cards in it
	 */
	public void updateSize() {
		int height = base.getSize().height;
		/*
		if(!cards.isEmpty()) {
			height += offset * (cards.size() - 1);
		}
*/
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);
	}
	
	
	/**
	 * Changes the offset of the pile
	 * @param {Integer} offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
		updateSize();
	}
	
	/**
	 * Breaks the pile into two piles
	 * The top half is kept in this pile
	 * @param {Card} first The card where the break starts
	 * @return
	 */
	public Pile split(Card first) {
		Pile p = new Pile(first);
		
		for(int i = 0; i < cards.size(); ++i) {
			if(cards.get(i) == first) {
				for(; i < cards.size();) {
					p.addCard(cards.get(i));
					removeCard(cards.get(i));
				}
			}
		}
		
		p.parent = this;
		
		return p;
	}
	
	/**
	 * Merge the current pile with the given pile
	 * The given pile is placed on top
	 * @param {Pile} p The pile to merge with
	 */
	public void merge(Pile p) {
		for(Card c: p.cards) {
			addCard(c);
		}
		updateSize();
	}
	
	/**
	 * Searches for a card in the pack based on value and suit name.
	 * @param {int} value
	 * @param {String} suitName
	 * @return {Card} The found card
	 *//*
	public Card searchCard(int value, String suitName) {
		
		for(Card c: cards) {
			if(c.value == value && c.suit.name().equals(suitName))
				return c;
		}
		
		return null;
	}*/
	
	/**
	 * Checks wether the pile is empty or not
	 * @return {Boolean} True if the pile is empty
	 */
	public boolean isEmpty() {
		return cards.size() == 0;
	}
	
	/**
	 * Solitaire conditions to check if a move is valid
	 */
	public boolean acceptsPile(Pile p) {
		// Can not add to itself
		if(this == p) return false;
		
		return true;
	}
	
	public boolean isOptimizedDrawingEnabled() {
        return false;
	}

	
	// Change baseline, so pile is aligned to top
	@Override
	public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
	    return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
	}

	@Override
	public int getBaseline(int width, int height) {
	    return 0;
	}
}

