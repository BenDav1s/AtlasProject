package graphics.bountypage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import character.EnemyCharacter;
import database.DatabaseAdapter;
import graphicTools.Colors;
import resources.BackgroundPanel;

public class PreviewPanelGenerator {
	public static JPanel generatePreviewPanel(String name, BountyPageController controller, JFrame frame) {
		JPanel temp = null;
		
		try {
			EnemyCharacter boss = (EnemyCharacter) DatabaseAdapter.loadBoss(name);
			temp = new JPanel();
			temp.setPreferredSize(new Dimension(300,600));
			temp.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.3f));
			temp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));
			GridLayout masterLayout = new GridLayout(5,1);
			masterLayout.setHgap(10);
			masterLayout.setVgap(10);
			temp.setLayout(masterLayout);
			
			JLabel charName = new JLabel(boss.getName());
			charName.setPreferredSize(new Dimension(300,50));
			charName.setFont(graphicTools.Fonts.getBoldFont(14f));
			charName.setForeground(Colors.Yellow);
			
			JLabel faction = new JLabel("Faction: " + boss.getCharClass().name());
			faction.setPreferredSize(new Dimension(300,50));
			faction.setFont(graphicTools.Fonts.getBoldFont(14f));
			faction.setForeground(Colors.Yellow);
			
			JLabel rank = new JLabel("Rank : " + String.valueOf(((EnemyCharacter) boss).getRank()));
			rank.setPreferredSize(new Dimension(300,50));
			rank.setFont(graphicTools.Fonts.getBoldFont(14f));
			rank.setForeground(Colors.Yellow);
			
			JLabel hp = new JLabel("HP : " + String.valueOf(boss.getHP()));
			hp.setPreferredSize(new Dimension(300,50));
			hp.setFont(graphicTools.Fonts.getBoldFont(14f));
			hp.setForeground(Colors.Yellow);
			
			JButton fight = new JButton("<HTML><U>FIGHT</U></HTML>");
			fight.setActionCommand("fight");
			fight.setName(boss.getName());
			fight.setPreferredSize(new Dimension(200,50));
			fight.setBackground(new Color(0.0f,0.0f,0.0f,0.3f));
			fight.setFont(graphicTools.Fonts.getBoldFont(14f));
			fight.setForeground(Colors.Yellow);
			fight.addActionListener(controller);
			
			temp.add(charName);
			temp.add(faction);
			temp.add(rank);
			temp.add(hp);
			temp.add(fight);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//	load from database
		// set background image to faction
		//	add "fight" button to load fight 
		
		return temp;
	}
}	
