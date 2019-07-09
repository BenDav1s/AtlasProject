package graphics.loginpage;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import character.Player;
import character.User;
import combat.CombatProcess;
import database.DatabaseAdapter;
import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.InvalidPopup;
import graphics.PageController;
import graphics.PageCreator;
import statistics.Stats;
import character.FactionTypes;
public class LoginPageController extends PageController{
	/** The previous page. */
	public static final String EXIT = "exit";
	public static final String LOGIN = "login";
	public static final String CREATE_NEW_ACCOUNT = "create";
	public static final String FORGOT_PASS = "forgot";
	/** The logger. */
	private static Logger logger = Logger.getLogger(LoginPageController.class.getName());
	
	private LoginPageModel model;
	
	private LoginPageView view;
	
	public void launchPage(JFrame mainFrame, String back) {
		logger.info("Launching login page");
		backPage = back;
		view = new LoginPageView(this,mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case LOGIN:	//GraphicsController.processPage(PageCreator.HOME_PAGE, PageCreator.LOGIN_PAGE);
			try {
				
				if(validateLogin() && DatabaseAdapter.loginPlayer(view.getUsername().getText(), view.getPassword().getText())) {
					try {
						this.view.getSlideThread().stop();
						this.view.getSlideThread().join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					GraphicsController.processPage(PageCreator.CHARACTER_SELECTION, PageCreator.LOGIN_PAGE);
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				logger.warning("SQL EXCEPTION THROWN");
			}
			
			break;
		case EXIT:System.exit(0);
			break;
		case CREATE_NEW_ACCOUNT:
			try {
				this.view.getSlideThread().stop();
				this.view.getSlideThread().join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			GraphicsController.processPage(PageCreator.CREATE_ACCOUNT_PAGE, PageCreator.LOGIN_PAGE);
			break;
		case FORGOT_PASS:
			GraphicsController.processPage(PageCreator.FORGOT_PASSWORD_PAGE, PageCreator.LOGIN_PAGE);
			break;
		}
	}
	public boolean validateLogin() {
		List<String> errors = new ArrayList<>();
		boolean valid = true;
		if(view.getUsername().getText().equals("")) {
			errors.add("Please enter valid username \n");
			valid = false;
		}
		if(view.getPassword().getText().equals("")) {
			errors.add("Please enter valid password\n");
			valid = false;
		}
		if(valid == false) {
			InvalidPopup p = new InvalidPopup(this.view.getPanel(),errors);
		}
		
		return valid;
	}
}
