package graphics.homepage;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.swing.JFrame;

import graphics.PageController;
import graphics.createAccountPage.CreateAccountPageController;
import graphics.createAccountPage.CreateAccountPageView;

public class HomePageController extends PageController{
	private HomePageView view;
	private HomePageModel model;
	private static Logger logger = Logger.getLogger(HomePageController.class.getName());
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		logger.info("Launching home page");
		view = new HomePageView(this,mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
