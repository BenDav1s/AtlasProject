package graphics.storyPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameRules.GameRules;
import quest.Quest;
import quest.QuestProducer;
import quest.Quest.Status;
import resources.BackgroundPanel;

public class StoryPageView {
	private StoryPageController storyController;
	private JPanel panel;
	private JFrame frame;
	
	public StoryPageView(StoryPageController storyPageController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		storyController = storyPageController;
		frame = mainFrame;
		
		this.frame.setContentPane(this.panel = new BackgroundPanel("loginpage"));
		this.panel.setPreferredSize(new Dimension(1080,720));
		GridLayout masterLayout = new GridLayout(2,3);
		this.panel.setLayout(masterLayout);
		
		JButton exit = new JButton("Back");
		exit.setPreferredSize(new Dimension(200,50));
		exit.setContentAreaFilled(false);
		exit.setFont(graphicTools.Fonts.getFont(14f));
		exit.setForeground(graphicTools.Colors.Yellow);
		exit.setActionCommand("back");
		exit.addActionListener(this.storyController);
		
		this.panel.add(exit);
		Set<String> quests = new HashSet<>();
		if(GameRules.activePlayer.getKnownQuests()!= null) {
			for(String s : GameRules.activePlayer.getKnownQuests()) {
				quests.add(s);
			}
		}
		if(GameRules.activePlayer.getActiveQuests()!= null) {
			for(String s :GameRules.activePlayer.getActiveQuests()) {
				quests.add(s);
			}
		}
		for(String s : quests) {
			this.panel.add(new Quest(s,storyController));
		}
		quests.clear();
		//this.panel.add(QuestProducer.getInstance().getNextQuest(GameRules.activePlayer,storyController));
		
		for(int i  =0; i < 4;i++) {
			this.panel.add(new JLabel());
		}
		this.frame.pack();
		this.frame.setVisible(true);
		
	}

}
