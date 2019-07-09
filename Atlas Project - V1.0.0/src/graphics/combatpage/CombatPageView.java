package graphics.combatpage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import combat.CombatProcess;
import gameRules.GameRules;
import resources.BackgroundPanel;
import resources.CustomCursor;
import spells.Card;
import spells.Deck;
import spells.Spell;
import character.Character;

public class CombatPageView {
	private JFrame frame;
	//	main game panel
	private JPanel gameArea;
	//	panel of cards 
	private JPanel columns;
	//	enemy panels
	private JPanel topColumns;
	//	friendly panels
	private JPanel botColumns;
	private JLayeredPane lp;
	Point mouseOffset;
	private CombatPageController c;
	Pile tempPile;
	public ArrayList<Pile> piles;
	public ArrayList<Pile> handPiles;
	public ArrayList<Pile> deckPile;
	public ArrayList<Pile> getHandPiles(){
		return this.handPiles;
	}
	public JPanel getColumns() {
		return this.columns;
	}
	public JPanel getTopColumns() {
		return topColumns;
	}
	public JPanel getBotColumns() {
		return botColumns;
	}
	public CombatPageView(CombatPageController combatPageController, JFrame mainFrame) {
		
		//	set up frame
		this.frame = mainFrame;
		this.frame.setContentPane(new BackgroundPanel());
		this.c = combatPageController;
		this.frame.setLayout(new BorderLayout());
		//	set up game area
		this.gameArea = new JPanel();
		this.gameArea.setPreferredSize(new Dimension(1080,720));
		this.gameArea.setOpaque(false);
		this.gameArea.setLayout(new BoxLayout(gameArea, BoxLayout.PAGE_AXIS));
		this.frame.setCursor(CustomCursor.getCursor());
	    // Add GUI elements
		//createTopMenu();
		
		// Flow layout to display multiple columns on the same row
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
		flow.setAlignOnBaseline(true);
		
		// Add the columns panel
		columns = new JPanel();
		columns.setOpaque(false);
		columns.setLayout(flow);
		columns.setMinimumSize(new Dimension(200, 900));
		
		// Add the top columns panel
		FlowLayout topFlow = new FlowLayout(FlowLayout.LEFT);
		topFlow.setAlignOnBaseline(true);
		
		topColumns = new JPanel();
		topColumns.setOpaque(false);
		topColumns.setLayout(topFlow);
		
		//	add bottom columns panel
		FlowLayout botFlow = new FlowLayout(FlowLayout.RIGHT);
		botFlow.setAlignOnBaseline(true);
		botColumns = new JPanel();
		botColumns.setOpaque(false);
		botColumns.setLayout(botFlow);
		

		//layers.add(dragLayer, JLayeredPane.DRAG_LAYER);
		
		//this.frame.add(gameArea);
		
		// Display the window
		setLp(this.frame.getLayeredPane());
	
		// Auxiliarry elements
		mouseOffset = new Point(0, 0);
		
		initialize();
	}
	
	/**
	 * Add cards from the game to the GUI
	 */
	private void initialize() {
		//	draw cards in hand
		topColumns.removeAll();
		columns.removeAll();
		CombatProcess q = c.getModel();
		q.initgame();
		
		//	set up cards from user's deck to have listeners
		handPiles = new ArrayList<Pile>();
		deckPile = new ArrayList<Pile>();
		List<Spell> spells = new ArrayList<>();
		
		//	add cards to hand area and deck area
		int max = GameRules.activeCharacter.getDeck().getCards().size();
		for(int i = 0; i < max && i <6; i++) {
			Card a = GameRules.activeCharacter.getDeck().drawCard();
			a.addMouseListener(this.c);
			a.addMouseMotionListener(this.c);
			a.show();
			Pile p = new Pile(a);
			
			columns.add(p);
			handPiles.add(p);
		}
		JButton pass = new JButton("Pass");
		pass.setActionCommand("pass");
		pass.addActionListener(this.c);
		this.gameArea.add(pass);
		JButton quit = new JButton("Quit");
		quit.setActionCommand("quit");
		quit.addActionListener(this.c);
		this.gameArea.add(quit);
//		add cards to hand area and deck area
		for(Character c : q.getenemyTeam()) {
			int maximum = c.getDeck().getCards().size();
			for(int i = 0; i < maximum && i < 6; i++) {
				c.drawCard();
				
			}/*
			for(Card x : c.getHand()) {
				System.out.println(c.getName() + " has " + x.getSpell().getName());
			}*/
		}
			
		//	set top column to display enemy
		for(Character x: q.getenemyTeam()) {
			topColumns.add(x.getProfileBrief());
		}
		//	set bottom column to display team
		for(Character y : q.getuserTeam()) {
			botColumns.add(y.getProfileBrief());
		}
		
		gameArea.add(topColumns);
		gameArea.add(columns);
		gameArea.add(botColumns);
		this.frame.getContentPane().add(gameArea);
		this.frame.pack();
		this.frame.setVisible(true);
	}

	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return this.frame;
	}

	public JLayeredPane getLp() {
		return lp;
	}

	public void setLp(JLayeredPane lp) {
		this.lp = lp;
	}
	

}
