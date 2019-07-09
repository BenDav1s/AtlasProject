package graphics.bountypage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gameRules.GameRules;

public class ListPanelGenerator {
	public static JPanel generateListPanel(BountyPageController controller, JFrame frame) {
		JPanel temp  = new JPanel();
		temp.setOpaque(false);
		temp.setLayout(new GridLayout(0,1));
		
		List<JButton> bossList = new ArrayList<>();
		for(String bossName : GameRules.activePlayer.getBountyList()) {
			//	get from database
			
			JButton bossButton = new JButton();
			
			//	set text to boss name  | faction | level req
			bossButton.setText(bossName);
			bossButton.setName(bossName);
			bossButton.setActionCommand("bossSelected");
			bossButton.setFont(graphicTools.Fonts.getBoldFont(14f));
			bossButton.setForeground(Color.BLACK);
			bossButton.addActionListener(controller);
			bossButton.setOpaque(false);
			bossList.add(bossButton);
		}
		for(JButton btn : bossList) {
			temp.add(btn);
		}
		//	name : faction : level req 
		temp.setPreferredSize(new Dimension(200,100*bossList.size()));
		
		return temp;
	}
}
