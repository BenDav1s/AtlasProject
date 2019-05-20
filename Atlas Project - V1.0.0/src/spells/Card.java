package spells;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graphicTools.Colors;
import graphicTools.Fonts;
import resources.ResourceManager;


public class Card extends JPanel{
	public Spell spell;
	private BufferedImage image;
	private BufferedImage backImage;
	private boolean isReversed;
	Point positionOffset;
	public Spell getSpell() {
		return this.spell;
	}
	/**
	 * Class constructor
	 * @param {Integer} value The value of the card, in [1,14]
	 * @param {Suit} suit The suit of the card
	 */
	public Card(Spell s) {
		this.spell = s;		
		setReversed(false);
		
		backImage = ResourceManager.loadImage("Sunbird.jpg");
		
		setBounds(0, 0, backImage.getWidth(), backImage.getHeight());
		
		JLabel name = new JLabel(spell.getName()+" \n");
		JLabel pips = new JLabel(String.valueOf(spell.getPips()) + " pips\n");
		JLabel faction = new JLabel(spell.getFaction().name()+"\n");
		JLabel chance = new JLabel(String.valueOf(spell.getCastChance()) + " chance to cast \n");
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
		case Shield : effect.setText(String.valueOf("+ " + spell.getBoost()) + "% resist\n");
			break;
		case Shield_ALL : effect.setText(String.valueOf("+" +spell.getResist()) + " resist to all\n");
			break;
		case Blade : effect.setText(String.valueOf("+ " + spell.getBoost()) + "% damage\n");
			break;
		case Blade_ALL : effect.setText(String.valueOf("+" +spell.getBoost()) + "% damage to all\n");
			break;
		case Trap : effect.setText(String.valueOf("+" + spell.getBoost()) + "% damage to all\n");
			break;
		case Trap_ALL : effect.setText(String.valueOf("+" + spell.getBoost()) + "% damage to all\n");
			break;
		}
		
		name.setFont(Fonts.getFont((float) 20));
		name.setForeground(Colors.White);
		//username.setBounds(105,7,150,69);
		
		pips.setFont(Fonts.getFont((float) 15));
		pips.setForeground(Colors.White);
		//classType.setBounds(105,27,150,69);
		
		faction.setFont(Fonts.getFont((float) 15));
		faction.setForeground(Colors.White);
		//health.setBounds(105,47,150,69);
		
		effect.setFont(Fonts.getFont((float) 15));
		effect.setForeground(Colors.White);
		//pips.setBounds(105,47,150,69);
		
		chance.setFont(Fonts.getFont(15f));
		chance.setForeground(Colors.White);
		
		this.add(name);
		this.add(faction);
		this.add(effect);
		this.add(chance);
		this.add(pips);
		
		
		this.setVisible(true);
		positionOffset = new Point(0,0);
		setPreferredSize(new Dimension(100,145));
		//setSize(new Dimension(100, 145));
		setOpaque(false);
	}
	
	/**
	 * Turns the card with the back up
	 */
	public void hide() {
		setReversed(true);
	}
	
	/**
	 * Turns the card with the face up
	 */
	public void show() {
		setReversed(false);
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

}
