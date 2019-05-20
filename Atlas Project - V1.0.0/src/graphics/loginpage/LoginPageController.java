package graphics.loginpage;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import combat.CombatProcess;
import graphics.GraphicsController;
import graphics.PageController;
import graphics.PageCreator;

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
			if(validateLogin())
			GraphicsController.processPage(PageCreator.COMBAT_PAGE, PageCreator.LOGIN_PAGE);
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
		return true;
	}
}
