package graphics.createAccountPage;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import combat.CombatProcess;
import graphics.GraphicsController;
import graphics.PageController;
import graphics.PageCreator;
import graphics.combatpage.CombatPageController;
import graphics.combatpage.CombatPageView;
import resources.CircleTest;
import resources.Faction;
import resources.PanelSwapThread;
import character.Class;
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
			if(validatePage())
			GraphicsController.processPage(PageCreator.HOME_PAGE, PageCreator.LOGIN_PAGE);
		break;
		}
	}
	public boolean validatePage() {
		boolean valid = true;
		
		return valid;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//	changes faction info displayed
		Class faction = null;
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
