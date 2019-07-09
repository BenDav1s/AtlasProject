package graphics.createAccountPage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import character.FactionTypes;
import graphicTools.Colors;
import graphicTools.Fonts;
import resources.ResourceManager;
public class FactionLeftPanel extends JPanel{
	private FactionTypes factionName;
	private JLabel text;
	
	//	denotes combat style
	public FactionLeftPanel(FactionTypes c) {
		if(c != null) {
			this.factionName = c;
			this.setLayout(new GridLayout(1,2));
			JLabel name = new JLabel(factionName.name());
			name.setFont(Fonts.getFont(13f));
			name.setSize(new Dimension(300,50));
			name.setForeground(Colors.Yellow);
			this.add(name);
			this.text= new JLabel(ResourceManager.loadText(c.name()+ "_factionFlavor.txt"));
			this.text.setSize(new Dimension(300,100));
			this.text.setFont(Fonts.getFont(14f));
			this.text.setForeground(Colors.Yellow);
			this.add(this.text);
			
			//setPreferredSize(new Dimension(100,145));
			this.setVisible(true);
			setOpaque(false);
			this.repaint();
			System.out.println("a" + this.getSize());
		}
		else {
			this.setVisible(true);
			this.setOpaque(false);
		}
		
		
	}
	public void switchFaction(FactionTypes c) {
		this.removeAll();
		this.factionName = c;
		this.setLayout(new GridLayout(1,2));
		JLabel name = new JLabel(factionName.name());
		name.setFont(Fonts.getFont(13f));
		name.setSize(new Dimension(300,50));
		name.setForeground(Colors.Yellow);
		this.add(name);
		this.text= new JLabel(ResourceManager.loadText(c.name()+ "_factionFlavor.txt"));
		this.text.setSize(new Dimension(300,100));
		this.text.setFont(Fonts.getFont(14f));
		this.text.setForeground(Colors.Yellow);
		this.add(this.text);
		
		this.setVisible(true);
		setOpaque(false);
		this.repaint();
	}
}
