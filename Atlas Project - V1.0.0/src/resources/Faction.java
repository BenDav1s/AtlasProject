package resources;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import character.FactionTypes;
import graphicTools.Colors;
import graphicTools.Fonts;
public class Faction extends JPanel{
	private FactionTypes factionName;
	private BufferedImage backImage;
	private JLabel text;
	
	public Faction(FactionTypes c) {
		if(c != null) {
			this.setFactionName(c);
			this.backImage = ResourceManager.loadImage(c.name()+ "_faction.jpg");
			
			setPreferredSize(new Dimension(200,145));
			setSize(new Dimension(200, 145));
			this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
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
	public void switchFaction(FactionTypes c) {
		this.removeAll();
		this.setFactionName(c);
		this.backImage = ResourceManager.loadImage(c.name()+ "_faction.jpg");

		this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
		
		this.setVisible(true);
		setOpaque(false);
		this.repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	public FactionTypes getFactionName() {
		return factionName;
	}
	public void setFactionName(FactionTypes factionName) {
		this.factionName = factionName;
	}
}
