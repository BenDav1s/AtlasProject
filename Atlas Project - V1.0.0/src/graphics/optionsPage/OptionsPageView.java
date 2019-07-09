package graphics.optionsPage;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import resources.BackgroundPanel;

public class OptionsPageView {
	private OptionsPageController optionsController;
	private JFrame frame;
	private JPanel panel;
	private JScrollPane optionsPane;
	private JButton sound;
	
	public JButton getSound() {
		return sound;
	}
	public void setSound(JButton s) {
		this.sound=  s;
	}
	public OptionsPageView(OptionsPageController optionsPageController, JFrame mainFrame) {
		// TODO Ato-generated constructor stub
		this.optionsController = optionsPageController;
		this.frame = mainFrame;
		
		this.frame.setContentPane(this.panel = new BackgroundPanel("loginpage"));
		this.panel.setPreferredSize(new Dimension(1080,720));
		this.frame.setLayout(new BorderLayout());
		
		JButton exit = new JButton("Back");
		exit.setPreferredSize(new Dimension(200,50));
		exit.setContentAreaFilled(false);
		exit.setFont(graphicTools.Fonts.getFont(14f));
		exit.setForeground(graphicTools.Colors.Yellow);
		exit.setActionCommand("back");
		exit.addActionListener(this.optionsController);
		
		//	set layout
		GridLayout masterLayout = new GridLayout(1,3);
		this.panel.setLayout(masterLayout);
		this.panel.add(exit);
		
		//	options pane
		JPanel scrollBackground = new JPanel();
		scrollBackground.setLayout(new GridLayout(8,1));
		scrollBackground.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.25f));
		optionsPane = new JScrollPane(scrollBackground,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		optionsPane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));
		sound = new JButton("<HTML><U>Audio</U></HTML>");
		sound.setActionCommand("audio");
		sound.addActionListener(this.optionsController);
		sound.setPreferredSize(new Dimension(200,50));
		sound.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.25f));
		sound.setFont(graphicTools.Fonts.getBoldFont(14f));
		sound.setForeground(graphicTools.Colors.Yellow);
		scrollBackground.add(sound);
		
		JButton change = new JButton("<HTML><U>Change Character</U></HTML>");
		change.setActionCommand("change");
		change.addActionListener(this.optionsController);
		change.setPreferredSize(new Dimension(200,50));
		change.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.25f));
		change.setFont(graphicTools.Fonts.getBoldFont(14f));
		change.setForeground(graphicTools.Colors.Yellow);
		scrollBackground.add(change);
		
		
		//optionsPane.add(sound);
		this.panel.add(optionsPane);
		
		this.panel.add(new JLabel());
		
		
		this.frame.pack();
		this.frame.setVisible(true);
	}

}
