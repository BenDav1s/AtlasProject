package graphics.homepage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import graphicTools.Colors;
import graphicTools.Fonts;
import graphics.PageCreator;
import resources.BackgroundPanel;
import resources.CustomCursor;
import resources.ResourceManager;

public class HomePageView {
	private HomePageController c;
	private JFrame frame;
	private JPanel panel;
	
	public HomePageView(HomePageController homePageController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.c = homePageController;
		
		this.frame = mainFrame;
		
		this.frame.setContentPane(this.panel = new BackgroundPanel("background7"));
		this.panel.setPreferredSize(new Dimension(1080,720));
		this.frame.setLayout(new BorderLayout());
		
		GridLayout masterLayout = new GridLayout(5,5);
		
		this.panel.setLayout(masterLayout);
		
		//	set up cursor

		this.frame.setCursor(CustomCursor.getCursor());
		
		//	set up buttons and labels
		////	TOP AREA
		JButton exit = new JButton("Logout");
		exit.setContentAreaFilled(false);
		exit.setFont(graphicTools.Fonts.getBoldFont(24f));
		exit.setForeground(Colors.DarkGreen);
		exit.setActionCommand("Logout");
		exit.addActionListener(homePageController);
		
		
		JButton profile = new JButton("Profile");
		profile.setContentAreaFilled(false);
		profile.setFont(graphicTools.Fonts.getFont(14f));
		profile.setForeground(graphicTools.Colors.Yellow);
		profile.setActionCommand("profile");
		profile.addActionListener(homePageController);
		
		this.panel.add(exit);
		this.panel.add(new JLabel());
		this.panel.add(new JLabel());
		this.panel.add(new JLabel());
		this.panel.add(new JLabel());
		
		for(int i = 0; i < 5; i++) {
			this.panel.add(new JLabel());
		}
		/////////	Middle area
		
		JButton btnStory = new JButton("storyMode");
		btnStory.setActionCommand("storymode");
		btnStory.setContentAreaFilled(false);
		btnStory.addActionListener(homePageController);
		BufferedImage storyPic = ResourceManager.loadImage("storyMode.png");
		btnStory.setIcon(new ImageIcon(new ImageIcon(storyPic).getImage().getScaledInstance(200, 140, Image.SCALE_SMOOTH)));
		btnStory.setToolTipText("Story Mode");
		
		JButton btnBounty = new JButton("BountyBoard");
		btnBounty.setActionCommand("bounty");
		btnBounty.setContentAreaFilled(false);
		btnBounty.addActionListener(homePageController);
		BufferedImage onlinePic = ResourceManager.loadImage("wantedPoster.jpg");
		btnBounty.setIcon(new ImageIcon(new ImageIcon(onlinePic).getImage().getScaledInstance(200, 140, Image.SCALE_SMOOTH)));
		btnBounty.setToolTipText("Rematch your greatest foes");
		
		this.panel.add(new JLabel());
		this.panel.add(btnStory);
		this.panel.add(new JLabel());
		this.panel.add(btnBounty);
		this.panel.add(new JLabel());
		
		//	LOWER MIDDLE AREA
		
		JButton btnBackpack = new JButton("packnstats");
		btnBackpack.setActionCommand("packnstats");
		btnBackpack.setContentAreaFilled(false);
		btnBackpack.addActionListener(homePageController);
		BufferedImage backpack = ResourceManager.loadImage("backpack2.png");
		btnBackpack.setIcon(new ImageIcon(new ImageIcon(backpack).getImage().getScaledInstance(200, 140, Image.SCALE_SMOOTH)));
		btnBackpack.setToolTipText("View inventory and stats");
		
		this.panel.add(new JLabel());
		this.panel.add(new JLabel());
		this.panel.add(btnBackpack);
		this.panel.add(new JLabel());
		this.panel.add(new JLabel());
		
		/////	Bottom Area
		JButton btnOptions = new JButton("<HTML><U>Options</U></HTML>");
		btnOptions.setActionCommand("options");
		btnOptions.setContentAreaFilled(false);
		btnOptions.addActionListener(homePageController);
		BufferedImage optionspic = ResourceManager.loadImage("options.png");
		btnOptions.setIcon(new ImageIcon(new ImageIcon(optionspic).getImage().getScaledInstance(200, 140, Image.SCALE_SMOOTH)));
		
		JButton site = new JButton("<HTML><U>BeMyPlayer2 Webpage</U></HTML>");
		site.setActionCommand("launchsite");
		site.addActionListener(homePageController);
		site.setOpaque(true);
		site.setContentAreaFilled(false);
		site.setBorderPainted(false);
		site.setFont(Fonts.getFont(12f));
		site.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		site.setForeground(graphicTools.Colors.Yellow);
		
		this.panel.add(btnOptions);
		this.panel.add(new JLabel());
		this.panel.add(new JLabel());
		this.panel.add(new JLabel());
		this.panel.add(new JLabel());
		
		this.frame.setContentPane(this.panel);
		this.frame.revalidate();
		
		this.frame.pack();
		this.frame.setVisible(true);
		
	}

}
