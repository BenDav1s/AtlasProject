package graphics.inventoryPage;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;

import database.DatabaseAdapter;
import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.InvalidPopup;
import graphics.PageController;
import graphics.PageCreator;
import graphics.homepage.HomePageController;
import graphics.homepage.HomePageModel;
import graphics.homepage.HomePageView;
import items.Item;
import resources.ResourceManager;

public class InventoryPageController extends PageController{
	private InventoryPageView view;
	private InventoryPageModel model;
	private static Logger logger = Logger.getLogger(HomePageController.class.getName());
	private static final String BACK = "back";
	private static final String ITEMCLICK = "ItemSelected";
	private static final String ITEMEQUIPACTION = "ItemEquipAction";
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		logger.info("Launching inventory page");
		view = new InventoryPageView(this,mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case BACK: GraphicsController.processPage(PageCreator.HOME_PAGE, PageCreator.INVENTORY_PAGE);
		break;
		case ITEMCLICK:
			String name = ((JButton) e.getSource()).getName();
			String arr [] = name.split("::");
			Item temp = ResourceManager.loadItem(arr[0]);
			
			if(temp != null) {
				switch(arr[1]) {
				case "Helmet": 
					view.getItemPaneH().removeAll();
					view.getItemPaneH().add(temp.getItemAsPanel(this));
					view.getItemPaneH().validate();
					view.getItemPaneH().repaint();
					break;
				case "Armor": 
					view.getItemPaneA().removeAll();
					view.getItemPaneA().add(temp.getItemAsPanel(this));
					view.getItemPaneA().validate();
					view.getItemPaneA().repaint();
					break;
				case "Boots": 
					view.getItemPaneB().removeAll();
					view.getItemPaneB().add(temp.getItemAsPanel(this));
					view.getItemPaneB().validate();
					view.getItemPaneB().repaint();
					break;
				case "Athame": 
					view.getItemPaneAT().removeAll();
					view.getItemPaneAT().add(temp.getItemAsPanel(this));
					view.getItemPaneAT().validate();
					view.getItemPaneAT().repaint();
					break;
				case "Ring": 
					view.getItemPaneR().removeAll();
					view.getItemPaneR().add(temp.getItemAsPanel(this));
					view.getItemPaneR().validate();
					view.getItemPaneR().repaint();
					break;
				case "Wand": 
					view.getItemPaneW().removeAll();
					view.getItemPaneW().add(temp.getItemAsPanel(this));
					view.getItemPaneW().validate();
					view.getItemPaneW().repaint();
					break;
				case "Deck": 
					view.getItemPaneD().removeAll();
					view.getItemPaneD().add(temp.getItemAsPanel(this));
					view.getItemPaneD().validate();
					view.getItemPaneD().repaint();
					break;
				}
			}
			break;
		case ITEMEQUIPACTION:
			if(((JButton) e.getSource()).getText().equals("Equip")) {
				Item tempItem = ResourceManager.loadItem(((JButton) e.getSource()).getName());
				if(tempItem != null && tempItem.getLevelRequirement() <= GameRules.activePlayer.getLevel()) {
					switch(tempItem.getWeaponType().name()) {
					case "Helmet": 
						GameRules.activePlayer.setHelmet(tempItem);
						view.getItemPaneH().validate();
						view.getItemPaneH().repaint();
						break;
					case "Armor": 
						GameRules.activePlayer.setArmor(tempItem);
						view.getItemPaneA().validate();
						view.getItemPaneA().repaint();
						break;
					case "Boots": 
						GameRules.activePlayer.setBoots(tempItem);
						view.getItemPaneB().validate();
						view.getItemPaneB().repaint();
						break;
					case "Athame": 
						GameRules.activePlayer.setAthame(tempItem);
						view.getItemPaneAT().validate();
						view.getItemPaneAT().repaint();
						break;
					case "Ring": 
						GameRules.activePlayer.setRing(tempItem);
						view.getItemPaneR().validate();
						view.getItemPaneR().repaint();
						break;
					case "Wand": 
						GameRules.activePlayer.setWand(tempItem);
						view.getItemPaneW().validate();
						view.getItemPaneW().repaint();
						break;
					case "Deck": 
						GameRules.activePlayer.setWand(tempItem);
						view.getItemPaneD().validate();
						view.getItemPaneD().repaint();
						break;
					}
				}
				((JButton) e.getSource()).setText("Un-Equip");
			}
			else {
				Item tempItem = ResourceManager.loadItem(((JButton) e.getSource()).getName());
				if(tempItem != null) {
					switch(tempItem.getWeaponType().name()) {
					case "Helmet": 
						GameRules.activePlayer.setHelmet(null);
						view.getItemPaneH().validate();
						view.getItemPaneH().repaint();
						break;
					case "Armor": 
						GameRules.activePlayer.setArmor(null);
						view.getItemPaneA().validate();
						view.getItemPaneA().repaint();
						break;
					case "Boots": 
						GameRules.activePlayer.setBoots(null);
						view.getItemPaneB().validate();
						view.getItemPaneB().repaint();
						break;
					case "Athame": 
						GameRules.activePlayer.setAthame(null);
						view.getItemPaneAT().validate();
						view.getItemPaneAT().repaint();
						break;
					case "Ring": 
						GameRules.activePlayer.setRing(null);
						view.getItemPaneR().validate();
						view.getItemPaneR().repaint();
						break;
					case "Wand": 
						GameRules.activePlayer.setWand(null);
						view.getItemPaneW().validate();
						view.getItemPaneW().repaint();
						break;
					case "Deck": 
						GameRules.activePlayer.setDeck(null);
						view.getItemPaneD().validate();
						view.getItemPaneD().repaint();
						break;
					}
				}
				((JButton) e.getSource()).setText("Equip");
			}
			try {
				DatabaseAdapter.updatePlayerInfo("Update-Equiped");
				this.view.updateStats();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				InvalidPopup p = new InvalidPopup(this.view.getPanel(),"Database failed to update");
			}
			break;
		}
	}

}
