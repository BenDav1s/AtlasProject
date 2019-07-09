package graphics;

import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class InvalidPopup.
 * @author Ben Davis, Colin Burdine, David Beggs, Jackson Raffety, Meghan Bibb ,Sam Muller
 */
public class InvalidPopup {
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(InvalidPopup.class.getName());
	
	/**
	 * Instantiates a new invalid popup for forms.
	 *
	 * @param p the p
	 * @param warnings the warnings
	 */
	public InvalidPopup(JPanel p,List<String> warnings) {
		StringBuilder warning = new StringBuilder();
		for(String s : warnings) {
			warning.append(s);
		}
		logger.info("Invalid input" + warnings);
		JOptionPane.showConfirmDialog(p, warning, "Invalid input", JOptionPane.DEFAULT_OPTION);
	}
	
	/**
	 * Instantiates a new invalid popup for small errors.
	 *
	 * @param p the p
	 * @param error the error
	 */
	public InvalidPopup(JPanel p, String error) {
		JOptionPane.showConfirmDialog(p, error,"Error",JOptionPane.DEFAULT_OPTION);
		logger.info("cannot submit info because: " + error);
	}
	
	/**
	 * intantiates a new invalid page for big erros and logs out the page.
	 *
	 * @param p the p
	 * @param error the error
	 * @param d the d
	 */
	public InvalidPopup(JPanel p, String error,int d) {
		JOptionPane.showConfirmDialog(p, error,"Error",JOptionPane.DEFAULT_OPTION);
		
		GraphicsController.processPage(PageCreator.LOGIN_PAGE, PageController.backPage);
	}
}
