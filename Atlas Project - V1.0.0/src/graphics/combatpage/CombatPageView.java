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
import spells.Card;
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
		this.frame.setContentPane(new BackgroundPanel(null));
		this.c = combatPageController;
		this.frame.setLayout(new BorderLayout());
		//	set up game area
		this.gameArea = new JPanel();
		this.gameArea.setPreferredSize(new Dimension(1080,720));
		this.gameArea.setOpaque(false);
		this.gameArea.setLayout(new BoxLayout(gameArea, BoxLayout.PAGE_AXIS));
		
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
		
		//	add cards to hand area and deck area
		for(int i = 0; i <= GameRules.activeUser.getDeck().getCards().size(); i++) {
			
			if(i < 5 || GameRules.activeUser.getDeck().getCards().size() >=0) {
				Card a = GameRules.activeUser.getDeck().drawCard();
				a.addMouseListener(this.c);
				a.addMouseMotionListener(this.c);
				a.show();
				Pile p = new Pile(a);
				
				columns.add(p);
				handPiles.add(p);
			}
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
