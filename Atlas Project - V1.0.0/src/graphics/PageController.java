package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class PageController.
 * @author BeMyPlayer2 Team
 */
public abstract class PageController implements ActionListener {
	
	/** The back page. */
	protected static String backPage = null;

	/**
	 * Launch page.
	 *
	 * @param mainFrame the main frame
	 * @param back the back
	 */
	public abstract void launchPage(JFrame mainFrame, String back);
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public abstract void actionPerformed(ActionEvent e);
	

}
