package graphics.combatpage;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import combat.BountyProcess;
import combat.CombatProcess;
import gameRules.GameRules;
import graphics.PageController;
import graphics.PageCreator;
import graphics.combatpage.Pile.PileType;
import graphics.loginpage.LoginPageController;
import graphics.loginpage.LoginPageModel;
import graphics.loginpage.LoginPageView;
import playerGraphics.ProfileBriefModel;
import resources.BackgroundMusic;
import spells.Card;
import spells.SpellType;

public class CombatPageController extends PageController implements MouseListener, MouseMotionListener {
	/** The previous page. */
	public static final String BACK = "back";
	public static final String LOGIN = "login";
	public static final String PASS = "pass";
	public static final String QUIT = "quit";
	/** The logger. */
	private static Logger logger = Logger.getLogger(CombatPageController.class.getName());
	
	private CombatProcess model;
	private CombatPageView view;
	private String previousPage;
	public void launchPage(JFrame mainFrame, String back) {
		logger.info("Launching combat page");
		setPreviousPage(back);
		if(back == PageCreator.STORY_MODE) {
			model = new CombatProcess(this);
		}
		else if(back == PageCreator.Bounty_Page){
			model = new BountyProcess(this);
		}
		
		view = new CombatPageView(this,mainFrame);
	}
	
	public CombatProcess getModel() {
		return this.model;
	}
	public CombatPageView getView() {
		return this.view;
	}
	
	/**
	 * Handles the activation of any of the menu bar buttons
	 * @param {ActionEvent} e
	 *//*
	private void handleMenuInteraction(ActionEvent e) {
		JMenuItem item = (JMenuItem)e.getSource();
		
		if(item.getText().equals(displayText.get("Exit"))) {
			this.dispose();
			return;
		}
		if(item.getText().equals(displayText.get("New"))) {
			reset();
			return;
		}
		if(item.getText().equals(displayText.get("Save"))) {
			game.save();
			JOptionPane.showMessageDialog(this, "Game saved!");
			return;
		}
		if(item.getText().equals(displayText.get("Load"))) {
			game.load();
			validate();
			return;
		}
	}*/

	@Override
	public void mouseDragged(MouseEvent e) {
		if(view.tempPile != null) {
			
			Point pos = view.getFrame().getLocationOnScreen();
			pos.x = e.getLocationOnScreen().x - pos.x - view.mouseOffset.x;
			pos.y = e.getLocationOnScreen().y - pos.y - view.mouseOffset.y;
			
			view.tempPile.setLocation(pos);
		}
		view.getFrame().repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() instanceof Card) {
			Card c = (Card)e.getComponent();
			//Pile p = (Pile)c.getParent();
			/*
			switch(p.type) {
				case Draw:
					game.drawCard();
				break;
				case Normal:
					game.clickPile(p);
				break;
				case Get:
					game.turnGetPile();
				break;
			}	*/
			System.out.println("clicked");
			if(c.getSpell().getType().equals(SpellType.Attack_All) || 
					c.getSpell().getType().equals(SpellType.Blade_ALL) ||
					c.getSpell().getType().equals(SpellType.Heal_ALL)||
					c.getSpell().getType().equals(SpellType.Blade_ALL) ||
					c.getSpell().getType().equals(SpellType.Shield_ALL) ||
					c.getSpell().getType().equals(SpellType.Trap_ALL) && c.getSpell().getPips() <= GameRules.activeCharacter.getPips()) {
				
				//model.combatphase(view.tempPile,true);
			}
			view.getFrame().repaint();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case LOGIN:	//GraphicsController.processPage(PageCreator.HOME_PAGE, PageCreator.LOGIN_PAGE);
			CombatProcess p = new CombatProcess(this);
			p.initgame();
			break;
		case PASS:
			model.combatphase(null, null);
			break;
		case QUIT:
			model.enemyWin();
			break;
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getComponent() instanceof Card) {
			Card c = (Card)e.getComponent();
			
			// Do nothing if card is reversed
			if(c.isReversed())
				return;
			
			Pile p  = (Pile)c.getParent();
			
			if(p.cards.isEmpty() || p.type == PileType.Final) return;
			
			view.tempPile = p.split(c);

			
			view.getLp().add(view.tempPile, JLayeredPane.DRAG_LAYER);
			//view.tempPile = new Pile(c);
			//view.tempPile.add(c);
			Point pos = view.getFrame().getLocationOnScreen();
			view.mouseOffset = e.getPoint();
			pos.x = e.getLocationOnScreen().x - pos.x - view.mouseOffset.x;
			pos.y = e.getLocationOnScreen().y - pos.y - view.mouseOffset.y;
			
			view.tempPile.setLocation(pos);
			
			view.getFrame().repaint();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(view.tempPile != null) {
			Point mousePos = e.getLocationOnScreen();
			boolean match = false;
			
			// Check if pile can merge with the pile it is dropped on
			Component temp = null;
			ArrayList<Pile> droppable = new ArrayList<Pile>();//(view.piles);
			//	check enemy pile
			for(Component p : view.getTopColumns().getComponents()) {

				Point pilePos = p.getLocationOnScreen();
				Rectangle r = p.getBounds();
				r.x = pilePos.x;
				r.y = pilePos.y;
		
				if(r.contains(mousePos) && ((ProfileBriefModel) p).acceptsPile(GameRules.activeCharacter,view.tempPile)) {
					temp = p;
					match = true;
					break;
				}
			}
			//	if not cast on enemy, see if cast on friendly
			//	check friendly pile
			if(!match) {
				for(Component p : view.getBotColumns().getComponents()) {
					Point pilePos = p.getLocationOnScreen();
					Rectangle r = p.getBounds();
					r.x = pilePos.x;
					r.y = pilePos.y;
			
					if(r.contains(mousePos) && ((ProfileBriefModel) p).acceptsPile(GameRules.activeCharacter,view.tempPile)) {
						temp  = p;
						match = true;
						break;
					}
				}
			}
			if(match) {
				//	remove from hand
				List<Pile> tempPiles = new ArrayList<>();
				int count = 0;
				for(Component c: view.getColumns().getComponents()) {
					if(((Pile) c).getBase().getSpell().getName().equals(view.tempPile.base.getSpell().getName())) {
						
						count++;
					}
					if(count == 1) {
						count++;
					}
					else {
						tempPiles.add((Pile) c);
					}
				}
				view.getColumns().removeAll();
				
				for(Pile p : tempPiles) {
					view.getColumns().add(p);
				}
				model.combatphase(temp,view.tempPile);
			}
			// Snap back if no merge is found
			
			if(!match) view.tempPile.parent.merge(view.tempPile);
				
			view.getLp().remove(view.tempPile);
			view.tempPile = null;

			view.getFrame().repaint();
			
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}
}
