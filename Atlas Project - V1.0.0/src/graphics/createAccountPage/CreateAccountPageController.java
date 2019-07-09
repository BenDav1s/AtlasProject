package graphics.createAccountPage;

import java.awt.event.ActionEvent;

import character.Backpack;
import character.Character;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import combat.CombatProcess;
import database.DatabaseAdapter;
import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.InvalidPopup;
import graphics.PageController;
import graphics.PageCreator;
import graphics.combatpage.CombatPageController;
import graphics.combatpage.CombatPageView;
import items.Item;
import items.Item.ItemType;
import resources.CircleTest;
import resources.Faction;
import resources.PanelSwapThread;
import spells.Deck;
import spells.Spell;
import statistics.Stats;
import character.FactionTypes;
import character.Player;
import character.User;
public class CreateAccountPageController extends PageController implements MouseListener{

	/** The previous page. */
	public static final String BACK = "back";
	public static final String SUBMIT= "submit";
	public static String previous;
	/** The logger. */
	private static Logger logger = Logger.getLogger(CreateAccountPageController.class.getName());
	
	private CreateAccountPageModel model;
	private CreateAccountPageView view;
	
	public void launchPage(JFrame mainFrame, String back) {
		logger.info("Launching create account page");
		previous = back;
		view = new CreateAccountPageView(this,mainFrame);
	}
	
	public CreateAccountPageModel getModel() {
		return this.model;
	}
	public CreateAccountPageView getView() {
		return this.view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case BACK: GraphicsController.processPage(PageCreator.LOGIN_PAGE, PageCreator.LOGIN_PAGE);
		break;
		
		case SUBMIT:
			if(validatePage()) {
				User temp  = new User();
				temp.setUsername(view.getUsername().getText());
				temp.setPassword(view.getPassword().getText());
				temp.setEmail(view.getEmail().getText());
				temp.setSecurityQuestion(view.getSecurityquestions().getSelectedItem().toString());
				temp.setSecurityAnswer(view.getSecurityAnswer().getText());
				temp.setGender(view.getGender().getSelectedItem().toString());
				Player tempPlayer = new Player();
				tempPlayer.setFaction(((Faction) view.getFactionSelected()).getFactionName());
				tempPlayer.setLevel(1);
				tempPlayer.setXp(0);
				tempPlayer.setGold(0);
				tempPlayer.setMaxHP(1000);
				tempPlayer.setName(view.getUsername().getText());
				tempPlayer.setStats(new Stats());
				temp.setCharacters(new ArrayList<>());
				temp.getCharacters().add(tempPlayer);
				List<Spell> starterSpells = new ArrayList<>();
				try {
					starterSpells = DatabaseAdapter.LoadSpells(FactionTypes.loadStarterSpells(tempPlayer.getFaction().name()));
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					InvalidPopup p = new InvalidPopup(this.view.getPanel(),"Database failed to load spell");
				}
				System.out.println(starterSpells.size());
				tempPlayer.setCharacter(new Character(starterSpells, tempPlayer.getMaxHP(), tempPlayer.getName(), tempPlayer.getFaction()));
				tempPlayer.setKnownSpells(starterSpells);
				
				Deck starterDeck = new Deck(starterSpells);
				starterDeck.setName("StarterDeck");
				starterDeck.setSellable(false);
				starterDeck.setFaction(FactionTypes.Generic);
				starterDeck.setWeaponType(ItemType.Deck);
				starterDeck.setLevelRequirement(0);
				tempPlayer.setDeck(starterDeck);
				
				tempPlayer.setBackpack(new Backpack());
				tempPlayer.getBackpack().setItems(new ArrayList<>());
				tempPlayer.getBackpack().getItems().add(starterDeck);
				
				tempPlayer.setStats(new Stats());
				tempPlayer.getStats().setEffectMap(Stats.loadNewStatsMap());
				GameRules.activeUser= temp;
				GameRules.activePlayer = tempPlayer;
				boolean valid = true;
				try {
					DatabaseAdapter.updateUserInfo(GameRules.activeUser);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					InvalidPopup p = new InvalidPopup(this.view.getPanel(),"Database failure");
					GameRules.activeUser = null;
					GameRules.activePlayer= null;
					GraphicsController.processPage(PageCreator.LOGIN_PAGE, null);
					valid = false;
				}
				if(valid) {
					GraphicsController.processPage(PageCreator.HOME_PAGE, PageCreator.LOGIN_PAGE);
				}
				
			}
			
		break;
		}
	}
	public boolean validatePage() {
		boolean valid = true;
		List<String> errors = new ArrayList<>();
		if(((Faction) view.getFactionSelected()).getFactionName() == null) {
			errors.add("Please select a faction!\n");
			valid = false;
		}
		if(view.getUsername().getText().equals("")) {
			errors.add("Please enter a username!\n");
			valid = false;
		}
		if(view.getEmail().getText().equals("")) {
			errors.add("Please enter an email\n");
			valid = false;
		}
		if(view.getPassword().getText().equals("")) {
			errors.add("Please enter a password\n");
			valid = false;
		}
		if(view.getPassConfirm().getText().equals("")) {
			errors.add("Please re enter your password\n");
			valid = false;
		}
		if(!view.getPassword().getText().equals(view.getPassConfirm().getText())){
			errors.add("Passwords must be matching\n");
			valid = false;
		}
		if(view.getSecurityAnswer().getText().equals("")) {
			errors.add("Please answer a security question!\n");
			valid = false;
		}
		try {
			if(DatabaseAdapter.scanForUsers(view.getUsername().getText())) {
				errors.add("User with that name already exists\n");
				valid =false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			errors.add("Database Error on scanning for existing user\n");
			valid = false;
			
		}
		if(!valid) {
			InvalidPopup p = new InvalidPopup(this.view.getPanel(),errors);
		}
		return valid;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//	changes faction info displayed
		FactionTypes faction = null;
		if((faction = ((CircleTest)view.getCircle()).validPoint(e.getPoint()))!= null) {
			PanelSwapThread a = new PanelSwapThread(0,faction,this.view);
			PanelSwapThread b = new PanelSwapThread(1,faction,this.view);
			PanelSwapThread c = new PanelSwapThread(2,faction,this.view);
			a.start();
			b.start();
			c.start();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
