package spells;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graphicTools.Colors;
import graphicTools.Fonts;
import graphics.deckPage.DeckController;
import resources.ResourceManager;


public class Card extends JPanel{
	public Spell spell;
	private BufferedImage image;
	private BufferedImage backImage;
	private boolean isReversed;
	Point positionOffset;
	private List<JLabel> textLabels = new ArrayList<>();
	public Spell getSpell() {
		return this.spell;
	}
	/**
	 * Class constructor
	 * @param {Integer} value The value of the card, in [1,14]
	 * @param {Suit} suit The suit of the card
	 */
	public Card(String s) {
		this.spell = new Spell(s);
	}
	public Card(Spell s) {
		this.spell = s;		
		setReversed(false);
		GridLayout layout = new GridLayout(5,1);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
		
		backImage = ResourceManager.loadImage(s.getName()+".jpg");
		
		//setBounds(0, 0, backImage.getWidth(), backImage.getHeight());
		setBounds(0,0,150,200);
		JLabel name = new JLabel(spell.getName()+" \n");
		JLabel pips = new JLabel(String.valueOf(spell.getPips()) + " pips\n");
		JLabel faction = new JLabel(spell.getFaction().name()+"	\n");
		JLabel chance = new JLabel(String.valueOf(spell.getCastChance()) + "% chance to cast \n");
		JLabel effect = new JLabel();
		
		switch(spell.getType()) {
		case Attack : effect.setText(String.valueOf(spell.getDamage())+" damage\n");
			break;
		case Attack_All : effect.setText(String.valueOf(spell.getDamage()) + " damage to all\n");
			break;
		case Heal : effect.setText(String.valueOf("+ " +spell.getHealth()) + " health\n");
			break;
		case Heal_ALL : effect.setText(String.valueOf("+ " + spell.getHealth()) + " health to all\n");
			break;
		case Shield : effect.setText(s.getImpactList() + " :: " +String.valueOf("+ " + spell.getResist()) + "% resist\n");
			break;
		case Shield_ALL : effect.setText(s.getImpactList() + " :: " +String.valueOf("+" +spell.getResist()) + " resist to all\n");
			break;
		case Blade : effect.setText(s.getImpactList() + " :: " +String.valueOf("+ " + spell.getBoost()) + "% damage\n");
			break;
		case Blade_ALL : effect.setText(s.getImpactList() + " :: " +String.valueOf("+" +spell.getBoost()) + "% damage to all\n");
			break;
		case Trap : effect.setText(s.getImpactList() + " :: " +String.valueOf("+" + spell.getBoost()) + "% damage to all\n");
			break;
		case Trap_ALL : effect.setText(s.getImpactList() + " :: " +String.valueOf("+" + spell.getBoost()) + "% damage to all\n");
			break;
		}
		
		name.setFont(Fonts.getFont((float) 10));
		name.setForeground(Colors.White);
		name.setSize(new Dimension(100,30));
		//username.setBounds(105,7,150,69);
		
		pips.setFont(Fonts.getFont((float) 10));
		pips.setForeground(Colors.White);
		pips.setSize(new Dimension(100,30));
		//classType.setBounds(105,27,150,69);
		
		faction.setFont(Fonts.getFont((float) 10));
		faction.setForeground(Colors.White);
		faction.setSize(new Dimension(100,30));
		//health.setBounds(105,47,150,69);
		
		effect.setFont(Fonts.getFont((float) 10));
		effect.setForeground(Colors.White);
		effect.setSize(new Dimension(100,30));
		//pips.setBounds(105,47,150,69);
		
		chance.setFont(Fonts.getFont(10f));
		chance.setForeground(Colors.White);
		chance.setSize(new Dimension(100,30));
		
		this.add(name);
		this.add(faction);
		this.add(effect);
		this.add(chance);
		this.add(pips);
		textLabels.add(name);
		textLabels.add(faction);
		textLabels.add(effect);
		textLabels.add(chance);
		textLabels.add(pips);
		
		this.setVisible(true);
		setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		positionOffset = new Point(0,0);
		setPreferredSize(new Dimension(150,200));
		//setSize(new Dimension(100, 145));
		setOpaque(false);
	}
	
	/**
	 * Turns the card with the back up
	 */
	public void hide() {
		setReversed(true);
		this.removeAll();
	}
	public void hideInfo() {
		this.removeAll();
	}
	/**
	 * Turns the card with the face up
	 */
	public void show() {
		setReversed(false);
		for(JLabel i : this.textLabels) {
			this.add(i);
		}
		this.validate();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		BufferedImage img = backImage;
		if(isReversed()) img = backImage;

		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	public boolean isReversed() {
		return isReversed;
	}

	public void setReversed(boolean isReversed) {
		this.isReversed = isReversed;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((spell == null) ? 0 : spell.hashCode());
		return result;
	}

	
}
