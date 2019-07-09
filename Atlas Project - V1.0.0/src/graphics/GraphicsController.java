package graphics;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import database.DatabaseAdapter;
import resources.BackgroundPanel;
import resources.ResourceManager;
import spells.Card;
import spells.Spell;

// TODO: Auto-generated Javadoc
/**
 * The Class GraphicsController.
 * @author BeMyPlayer2 Team
 */
public class GraphicsController implements Runnable, WindowListener {

	/** The Constant ACTIVE_ACCOUNT. */
	private static final String ACTIVE_ACCOUNT = "active account";
	
	/** The Constant OTHER_ACCOUNT. */
	private static final String OTHER_ACCOUNT = "other account";
	
	/** The main frame. */
	private static JFrame mainFrame;
	
	/** The profile account. */
	private static String profileAccount;
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(GraphicsController.class.getName());
	
	/**
	 * Instantiates a new graphics controller.
	 */
	public GraphicsController() {
		
			//init default jframe as base frame
			mainFrame = (new JFrame("Atlas Project"));
			mainFrame.setSize(1080, 720);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setLocationRelativeTo(null);
			mainFrame.addWindowListener(this);
			try {
			
				ImageIcon icon = new ImageIcon(ResourceManager.loadImage("app_icon.jpg"));
				mainFrame.setIconImage(icon.getImage());
			}
			catch (Exception exc) {
			    logger.warning("Failed to load app icon");
			}
			processPage(PageCreator.LOGIN_PAGE, null);
			
	}
	
	/**
	 * Process page.
	 *
	 * @param page the page
	 * @param backPage the back page
	 */
	public static void processPage(String page, String backPage) {
		PageController newPage = PageCreator.getPage(page);
		newPage.launchPage(mainFrame, backPage);
	}
	
	/**
	 * Sets the profile account active.
	 */
	public static void setProfileAccountActive() {
		profileAccount = ACTIVE_ACCOUNT;
	}
	
	/**
	 * Sets the profile account other.
	 */
	public static void setProfileAccountOther() {
		profileAccount = OTHER_ACCOUNT;
	}
	
	/**
	 * Gets the profile string.
	 *
	 * @return the profile string
	 */
	public static String getProfileString() {
		return profileAccount;
	}
	
	/**
	 * Gets the main frame.
	 *
	 * @return the main frame
	 */
	public static JFrame getMainFrame() {
		return mainFrame;
	}
	
	/**
	 * Sets the main frame.
	 *
	 * @param frame the new main frame
	 */
	public void setMainFrame(JFrame frame) {
		mainFrame = frame;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		try {
			DatabaseAdapter.logout();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
