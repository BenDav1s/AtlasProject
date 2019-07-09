package graphics.characterSelectionPage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import gameRules.GameRules;
import graphics.createAccountPage.CreateAccountPageView;
import resources.BackgroundPanel;
import resources.CustomCursor;
import resources.SlideshowThread;

public class CharacterSelectionView{
	private JFrame frame;
	private CharacterSelectionController selectionController;
	private JPanel panel;
	private JPanel playerPanel;
	
	
	public JFrame getFrame() {
		return frame;
	}


	public void setFrame(JFrame frame) {
		this.frame = frame;
	}


	public CharacterSelectionController getSelectionController() {
		return selectionController;
	}


	public void setSelectionController(CharacterSelectionController selectionController) {
		this.selectionController = selectionController;
	}


	public JPanel getPanel() {
		return panel;
	}


	public void setPanel(JPanel panel) {
		this.panel = panel;
	}


	public JPanel getPlayerPanel() {
		return playerPanel;
	}


	public void setPlayerPanel(JPanel playerPanel) {
		this.playerPanel = playerPanel;
	}

	private List<JButton>options = new ArrayList<>();
	private JPanel backHolder;
	private JPanel nextHolder;
	private JPanel exitHolder;
	private JPanel newHolder;
	private JPanel submitHolder;
	
	public JPanel getBackHolder() {
		return backHolder;
	}


	public void setBackHolder(JPanel backHolder) {
		this.backHolder = backHolder;
	}


	public JPanel getNextHolder() {
		return nextHolder;
	}


	public void setNextHolder(JPanel nextHolder) {
		this.nextHolder = nextHolder;
	}


	public JPanel getExitHolder() {
		return exitHolder;
	}


	public void setExitHolder(JPanel exitHolder) {
		this.exitHolder = exitHolder;
	}


	public JPanel getNewHolder() {
		return newHolder;
	}


	public void setNewHolder(JPanel newHolder) {
		this.newHolder = newHolder;
	}


	public JPanel getSubmitHolder() {
		return submitHolder;
	}


	public void setSubmitHolder(JPanel submitHolder) {
		this.submitHolder = submitHolder;
	}

	public CharacterSelectionView(CharacterSelectionController characterSelectionController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.selectionController = characterSelectionController;
		this.frame = mainFrame;
		
		//	init panel
		BackgroundPanel background = new BackgroundPanel("loginpage");
		
		this.panel = background;
		this.frame.setCursor(CustomCursor.getCursor());
		this.panel.setPreferredSize(new Dimension(1080, 720));
		this.frame.setContentPane(this.panel);
		this.frame.revalidate();
		
		GridLayout masterLayout = new GridLayout(2,3);
		this.panel.setLayout(masterLayout);
		
		backHolder = new JPanel();
		backHolder.setOpaque(false);
		backHolder.setLayout(new GridLayout(5,1));
		backHolder.add(new JLabel());
		backHolder.add(new JLabel());
		JButton backBtn  = new JButton("Previous");
		backBtn.setText("Previous");
		backBtn.setPreferredSize(new Dimension(200,50));
		backBtn.setBackground(new Color(0.0f,0.0f,0.0f,0.01f));
		backBtn.setFont(graphicTools.Fonts.getFont(14f));
		backBtn.setForeground(graphicTools.Colors.Yellow);
		backBtn.setActionCommand("back");
		backBtn.addActionListener(selectionController);
		backHolder.add(backBtn);
		backHolder.add(new JLabel());
		backHolder.add(new JLabel());
		
		this.panel.add(backHolder);
		
		
		playerPanel = GameRules.activePlayer.getPlayerAsPanel();
		//playerPanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
		playerPanel.setBorder(new LineBorder(Color.BLACK,3));
		playerPanel.setForeground(Color.BLACK);
		playerPanel.setBackground(Color.BLACK);
		this.panel.add(playerPanel);
		
		nextHolder = new JPanel();
		nextHolder.setOpaque(false);
		nextHolder.setLayout(new GridLayout(5,1));
		nextHolder.add(new JLabel());
		nextHolder.add(new JLabel());
		JButton nextBtn  = new JButton("Next");
		nextBtn.setText("Next");
		nextBtn.setPreferredSize(new Dimension(200,50));
		nextBtn.setBackground(new Color(0.0f,0.0f,0.0f,0.01f));
		nextBtn.setFont(graphicTools.Fonts.getFont(14f));
		nextBtn.setForeground(graphicTools.Colors.Yellow);
		nextBtn.setActionCommand("next");
		nextBtn.addActionListener(selectionController);
		nextHolder.add(nextBtn);
		nextHolder.add(new JLabel());
		nextHolder.add(new JLabel());
		this.panel.add(nextHolder);
		
		
		exitHolder = new JPanel();
		exitHolder.setOpaque(false);
		exitHolder.setLayout(new GridLayout(5,1));
		exitHolder.add(new JLabel());
		exitHolder.add(new JLabel());
		JButton exitBtn  = new JButton("Exit");
		exitBtn.setText("Exit");
		exitBtn.setPreferredSize(new Dimension(200,50));
		exitBtn.setBackground(new Color(0.0f,0.0f,0.0f,0.01f));
		exitBtn.setFont(graphicTools.Fonts.getFont(14f));
		exitBtn.setForeground(graphicTools.Colors.Yellow);
		exitBtn.setActionCommand("exit");
		exitBtn.addActionListener(selectionController);
		exitHolder.add(exitBtn);
		exitHolder.add(new JLabel());
		exitHolder.add(new JLabel());
		this.panel.add(exitHolder);
		
		newHolder = new JPanel();
		newHolder.setOpaque(false);
		newHolder.setLayout(new GridLayout(5,1));
		newHolder.add(new JLabel());
		newHolder.add(new JLabel());
		JButton newBtn  = new JButton("New");
		newBtn.setText("Create New Character");
		newBtn.setPreferredSize(new Dimension(300,50));
		newBtn.setBackground(new Color(0.0f,0.0f,0.0f,0.01f));
		newBtn.setFont(graphicTools.Fonts.getFont(14f));
		newBtn.setForeground(graphicTools.Colors.Yellow);
		newBtn.setActionCommand("new");
		newBtn.addActionListener(selectionController);
		newHolder.add(newBtn);
		newHolder.add(new JLabel());
		newHolder.add(new JLabel());
		this.panel.add(newHolder);
		
		submitHolder = new JPanel();
		submitHolder.setOpaque(false);
		submitHolder.setLayout(new GridLayout(5,1));
		submitHolder.add(new JLabel());
		submitHolder.add(new JLabel());
		JButton submitBtn  = new JButton("Submit");
		submitBtn.setText("Submit");
		submitBtn.setPreferredSize(new Dimension(200,50));
		submitBtn.setBackground(new Color(0.0f,0.0f,0.0f,0.01f));
		submitBtn.setFont(graphicTools.Fonts.getFont(14f));
		submitBtn.setForeground(graphicTools.Colors.Yellow);
		submitBtn.setActionCommand("submit");
		submitBtn.addActionListener(selectionController);
		submitHolder.add(submitBtn);
		submitHolder.add(new JLabel());
		submitHolder.add(new JLabel());
		this.panel.add(submitHolder);
		
		this.frame.pack();
		this.frame.setVisible(true);
	}

}
