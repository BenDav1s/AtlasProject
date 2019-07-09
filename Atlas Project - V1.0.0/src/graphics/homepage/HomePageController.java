package graphics.homepage;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.JFrame;

import database.DatabaseAdapter;
import graphics.GraphicsController;
import graphics.PageController;
import graphics.PageCreator;
import graphics.createAccountPage.CreateAccountPageController;
import graphics.createAccountPage.CreateAccountPageView;

public class HomePageController extends PageController{
	private HomePageView view;
	private HomePageModel model;
	private static Logger logger = Logger.getLogger(HomePageController.class.getName());
	private static final String OPTIONS = "options";
	private static final String WEBSITE = "launchsite";
	private static final String INVENTORY = "packnstats";
	private static final String BOUNTY = "bounty";
	private static final String STORY = "storymode";
	private static final String PROFILE = "profile";
	private static final String LOGOUT = "Logout";
	
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		logger.info("Launching home page");
		view = new HomePageView(this,mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case OPTIONS:GraphicsController.processPage(PageCreator.OPTIONS, PageCreator.HOME_PAGE);
			break;
		case WEBSITE: System.out.println("launch website");
			break;
		case INVENTORY:GraphicsController.processPage(PageCreator.INVENTORY_PAGE, PageCreator.HOME_PAGE);
			break;
		case BOUNTY: GraphicsController.processPage(PageCreator.Bounty_Page, PageCreator.HOME_PAGE);
			break;
		case STORY:GraphicsController.processPage(PageCreator.STORY_MODE, PageCreator.HOME_PAGE);
			break;
		case PROFILE: GraphicsController.processPage(PageCreator.PROFILE_PAGE, PageCreator.HOME_PAGE);
			break;
		case  LOGOUT:
			try {
				DatabaseAdapter.logout();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.warning("Logout failed");
			}
			
			GraphicsController.processPage(PageCreator.LOGIN_PAGE, PageCreator.HOME_PAGE);
			break;
		}
	}

}
