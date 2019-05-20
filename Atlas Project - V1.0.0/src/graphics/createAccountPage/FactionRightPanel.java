package graphics.createAccountPage;
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
import resources.ResourceManager;
public class FactionRightPanel extends JPanel{
	private Class factionName;
	private JLabel text;
	
	//	denotes combat style
	public FactionRightPanel(Class c) {
		if(c != null) {
			this.factionName = c;
			this.setLayout(new GridLayout(1,2));
			this.text= new JLabel(ResourceManager.loadText(c.name()+ "_factionCombat.txt"));
			this.text.setSize(new Dimension(300,100));
			this.text.setFont(Fonts.getFont(14f));
			this.text.setForeground(Colors.Yellow);
			this.add(this.text);
			
			setPreferredSize(new Dimension(100,145));
			setSize(new Dimension(100, 145));
			this.setVisible(true);
			setOpaque(false);
		}
		else {
			
			this.setVisible(true);
			this.setOpaque(false);
		}
		
		
	}
	public void switchFaction(Class c) {
		this.removeAll();
		this.factionName = c;
		this.setLayout(new GridLayout(1,2));
		this.text= new JLabel(ResourceManager.loadText(c.name()+ "_factionCombat.txt"));
		this.text.setSize(new Dimension(300,100));
		this.text.setFont(Fonts.getFont(14f));
		this.text.setForeground(Colors.Yellow);
		this.add(this.text);
		
		this.setVisible(true);
		setOpaque(false);
		this.repaint();
	}
}
