package resources;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import character.Class;
import graphicTools.Colors;
import graphicTools.Fonts;
public class Faction extends JPanel{
	private Class factionName;
	private BufferedImage backImage;
	private JLabel text;
	
	public Faction(Class c) {
		if(c != null) {
			this.factionName = c;
			this.backImage = ResourceManager.loadImage(c.name()+ "_faction.jpg");
			
			setPreferredSize(new Dimension(200,145));
			setSize(new Dimension(200, 145));
			this.setVisible(true);
			setOpaque(false);
		}
		else {
			JLabel prompt = new JLabel("Select your faction");
			prompt.setForeground(Colors.Yellow);
			prompt.setFont(Fonts.getFont(14f));
			this.add(prompt);
			
			this.setVisible(true);
			this.setOpaque(false);
		}
		
		
	}
	public void switchFaction(Class c) {
		this.removeAll();
		this.factionName = c;
		this.backImage = ResourceManager.loadImage(c.name()+ "_faction.jpg");
		
		
		this.setVisible(true);
		setOpaque(false);
		this.repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
