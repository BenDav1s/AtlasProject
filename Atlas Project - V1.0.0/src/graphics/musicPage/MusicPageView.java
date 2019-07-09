package graphics.musicPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import graphics.bountypage.BountyPageController;
import graphics.bountypage.ListPanelGenerator;
import graphics.bountypage.PreviewPanelGenerator;
import resources.BackgroundMusic;
import resources.BackgroundPanel;
import resources.CustomCursor;

public class MusicPageView {
	private MusicPageController controller;
	private JFrame frame;
	private JPanel panel;
	private JPanel listPanel;
	private JPanel previewPanel;
	private JButton sound;
	private JButton addSound;
	private JPanel scrollBackground;
	public JPanel getScrollBackground() {
		return this.scrollBackground;
	}
	public void setSound(JButton s) {
		this.sound = s;
	}
	public JButton getSound() {
		return this.sound;
	}
	public MusicPageView(MusicPageController musicPageController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.controller = musicPageController;
		this.frame = mainFrame;
		this.frame.setContentPane(this.panel = new BackgroundPanel("loginpage"));
		this.panel.setPreferredSize(new Dimension(1080,720));
		this.frame.setLayout(new BorderLayout());
		GridLayout masterLayout = new GridLayout(1,2);
		masterLayout.setHgap(10);
		masterLayout.setVgap(10);
		this.panel.setLayout(masterLayout);
		
		
		//	songs list
		scrollBackground = new JPanel();
		scrollBackground.setOpaque(false);
		scrollBackground.setLayout(new GridLayout(0,1));
		
		for(String s : BackgroundMusic.getSongList().keySet()) {
			JButton temp = new JButton();
			temp.setName(s);
			temp.setText(s + " :: play" );
			temp.setActionCommand("select");
			temp.addActionListener(this.controller);
			temp.setPreferredSize(new Dimension(200,50));
			temp.setContentAreaFilled(false);
			temp.setFont(graphicTools.Fonts.getFont(14f));
			temp.setForeground(graphicTools.Colors.White);
			scrollBackground.add(temp);
		}
		JScrollPane optionsPane = new JScrollPane(scrollBackground,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		optionsPane.getViewport().setOpaque(false);
		optionsPane.setOpaque(false);
		
		//	functionality
		sound = new JButton("<HTML><U>Music OFF</U></HTML>");
		sound.setActionCommand("volume");
		sound.addActionListener(this.controller);
		sound.setPreferredSize(new Dimension(200,50));
		sound.setContentAreaFilled(false);
		sound.setFont(graphicTools.Fonts.getFont(14f));
		sound.setForeground(graphicTools.Colors.White);
		
		addSound = new JButton("<HTML><U>Add Music</U></HTML>");
		addSound.setActionCommand("add");
		addSound.addActionListener(this.controller);
		addSound.setPreferredSize(new Dimension(200,50));
		addSound.setContentAreaFilled(false);
		addSound.setFont(graphicTools.Fonts.getFont(14f));
		addSound.setForeground(graphicTools.Colors.White);
		
		JPanel functionPanel = new JPanel();
		functionPanel.setOpaque(false);
		functionPanel.setLayout(new GridLayout(3,1));
		functionPanel.add(sound);
		functionPanel.add(addSound);
		
		//	back button 
		JButton backBtn  = new JButton("Back");
		backBtn.setText("Back");
		backBtn.setPreferredSize(new Dimension(200,50));
		backBtn.setContentAreaFilled(false);
		backBtn.setFont(graphicTools.Fonts.getFont(14f));
		backBtn.setForeground(graphicTools.Colors.Yellow);
		backBtn.setActionCommand("back");
		backBtn.addActionListener(controller);
		
		functionPanel.add(backBtn);
		
		this.panel.add(optionsPane);
		this.panel.add(functionPanel);
		
		this.frame.pack();
		this.frame.setVisible(true);
		
	}

}
