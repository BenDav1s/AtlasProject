package quest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import character.Player;
import database.DatabaseAdapter;
import graphicTools.Colors;
import graphics.storyPage.StoryPageController;
import items.Item;
import resources.ResourceManager;

public class Quest extends JPanel {
	private int xp;
	private int gold;
	private String title;
	private List<String> rewards;
	private boolean completed;
	private String description;
	private boolean active;
	private List<String> enemies;
	private List<String> nextQuests;
	
	public enum Status{
		Active,Completed,Known,Unknown;

		public static Status getStatus(String string) {
			// TODO Auto-generated method stub
			if(string.equalsIgnoreCase("active")) return Status.Active;
			if(string.equalsIgnoreCase("completed")) return Status.Completed;
			if(string.equalsIgnoreCase("known")) return Status.Known;
			return Status.Unknown;
		}
	}
	private Status questStatus;
	
	public List<String> getEnemies() {
		return enemies;
	}
	public void setEnemies(List<String> enemies) {
		this.enemies = enemies;
	}
	public List<String> getNextQuests() {
		return nextQuests;
	}
	public void setNextQuests(List<String> nextQuests) {
		this.nextQuests = nextQuests;
	}
	public Status getQuestStatus() {
		return questStatus;
	}
	public void setQuestStatus(Status questStatus) {
		this.questStatus = questStatus;
	}
	public BufferedImage getBackImage() {
		return backImage;
	}
	public void setBackImage(BufferedImage backImage) {
		this.backImage = backImage;
	}

	private BufferedImage backImage = ResourceManager.loadImage("The_Called_faction.jpg");
	public Quest(String name, StoryPageController c) {
		this.title = name;
		loadQuest();
		setBackground(new Color(0.0f, 0.0f, 0.0f, 0.25f));
		//backImage = ResourceManager.loadImage(title + "_quest.jpg");
		//setBounds(0, 0, backImage.getWidth(), backImage.getHeight());
		setPreferredSize(new Dimension(300,600));
		
		GridLayout layout = new GridLayout(5,1);
		setLayout(layout);
		
		JLabel header = new JLabel(title);
		header.setFont(graphicTools.Fonts.getFont(14f));
		header.setForeground(graphicTools.Colors.Yellow);
		header.setSize(new Dimension(400,100));
		JLabel text = new JLabel(description);
		text.setFont(graphicTools.Fonts.getFont(14f));
		text.setForeground(graphicTools.Colors.Yellow);
		text.setSize(new Dimension(300,100));
		JLabel goldPayout = new JLabel("Gold: + " + gold);
		goldPayout.setFont(graphicTools.Fonts.getFont(14f));
		goldPayout.setForeground(graphicTools.Colors.Yellow);
		JLabel xpPayout = new JLabel("XP: + " + xp);
		xpPayout.setFont(graphicTools.Fonts.getFont(14f));
		xpPayout.setForeground(graphicTools.Colors.Yellow);
		
		String drops = "<HTML>Drops : ";
		for(String i : rewards) {
			drops += i + "<br>";
		}
		drops+="</HTML>";
		JLabel dropLabel = new JLabel(drops);
		dropLabel.setFont(graphicTools.Fonts.getFont(14f));
		dropLabel.setForeground(graphicTools.Colors.Yellow);
		add(header);
		
		add(xpPayout);
		
		add(goldPayout);
		
		add(dropLabel);
		
		JButton btnAccept = new JButton("<HTML><U>Accept</U></HTML>");
		btnAccept.setFont(graphicTools.Fonts.getBoldFont(14f));
		btnAccept.setForeground(graphicTools.Colors.Yellow);
		btnAccept.setActionCommand("accept");
		btnAccept.setName(title);
		btnAccept.setContentAreaFilled(false);
		btnAccept.addActionListener(c);
		
		add(btnAccept);
		
		setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
	}
	public Quest() {
		// TODO Auto-generated constructor stub
	}
	private void loadQuest() {
		// TODO Auto-generated constructor stub
		try {
			DatabaseAdapter.loadQuest(this.title, this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void completeQuest(Player p) {
		p.setGold(p.getGold()+this.gold);
		p.setXp(p.getXp()+this.xp);
		for(String i : this.rewards) {
			p.getBackpack().getItems().add(ResourceManager.loadItem(i));
		}
		QuestCompleteMap.getInstance().getCompletionMap().put(p, this);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(backImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	public int getGold() {
		return this.gold;
	}
	public void setGold(int g) {
		this.gold = g;
	}
	public boolean isActive() {
		return this.active;
	}
	public void setActive(boolean a) {
		this.active = a;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String s) {
		this.description =s;
	}
	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getRewards() {
		return rewards;
	}

	public void setRewards(List<String> rewards) {
		this.rewards = rewards;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
}
