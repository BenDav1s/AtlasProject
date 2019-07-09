package graphics.characterSelectionPage;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import database.DatabaseAdapter;
import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.InvalidPopup;
import graphics.PageController;
import graphics.PageCreator;

public class CharacterSelectionController extends PageController{
	private CharacterSelectionView view;
	public static final String BACK = "back";
	public static final String SUBMIT = "submit";
	public static final String EXIT = "exit";
	public static final String NEXT = "next";
	public static final String NEW = "new";
	
	public static int current = 0;
	public static String previouspage;
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		previouspage=  back;
		view = new CharacterSelectionView(this,mainFrame);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case BACK: if(current == 0) {
						current = GameRules.activeUser.getCharacters().size()-1;
					}
					else {
						current = (current-1)%GameRules.activeUser.getCharacters().size();
					}
					
					view.setPlayerPanel(GameRules.activeUser.getCharacters().get(current).getPlayerAsPanel());
					view.getPlayerPanel().validate();
					view.getPlayerPanel().repaint();
					
					view.getPanel().removeAll();
					view.getPanel().add(view.getBackHolder());
					view.getPanel().add(view.getPlayerPanel());
					view.getPanel().add(view.getNextHolder());
					view.getPanel().add(view.getExitHolder());
					view.getPanel().add(view.getNewHolder());
					view.getPanel().add(view.getSubmitHolder());

					view.getFrame().validate();
					view.getFrame().repaint();
					//	update panel
			break;
		case NEXT: 
					current = (current+1)%GameRules.activeUser.getCharacters().size();
					System.out.println(current);
					System.out.println(GameRules.activeUser.getCharacters().get(current).getName());
					GameRules.activePlayer = GameRules.activeUser.getCharacters().get(current);
					view.setPlayerPanel(GameRules.activeUser.getCharacters().get(current).getPlayerAsPanel());
					view.getPlayerPanel().validate();
					view.getPlayerPanel().repaint();
					view.getPanel().removeAll();
					view.getPanel().add(view.getBackHolder());
					view.getPanel().add(view.getPlayerPanel());
					view.getPanel().add(view.getNextHolder());
					view.getPanel().add(view.getExitHolder());
					view.getPanel().add(view.getNewHolder());
					view.getPanel().add(view.getSubmitHolder());
					view.getFrame().validate();
					view.getFrame().repaint();
					//	update panel
			break;
		case SUBMIT: GameRules.activePlayer = GameRules.activeUser.getCharacters().get(current);
					GameRules.activePlayer.setPlayerID(GameRules.generatePlayerHash(GameRules.activePlayer.getName()));
						boolean valid = true;
						try {
							DatabaseAdapter.loadPlayerInfo(GameRules.activePlayer);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							InvalidPopup p =new InvalidPopup(this.view.getPanel(),"Failed to load stats");
							valid = false;
						}
						if(valid) {
							GraphicsController.processPage(PageCreator.HOME_PAGE, null);
						}
			break;
		case EXIT:	
					try {
						DatabaseAdapter.logout();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						InvalidPopup p = new InvalidPopup(this.view.getPanel(),"Database failed to log out");
					}
					GameRules.activeUser = null;
					GameRules.activeCharacter = null;
					GameRules.activePlayer = null;
					GraphicsController.processPage(PageCreator.LOGIN_PAGE, null);
					
			break;
		case NEW: 
			GraphicsController.processPage(PageCreator.Character_Creation, PageCreator.CHARACTER_SELECTION);
			break;
		}
	}
	
}
