package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import graphics.GraphicsController;
import resources.BackgroundMusic;


// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 * @author Ben Davis, Colin Burdine, David Beggs, Jackson Raffety, Meghan Bibb ,Sam Muller
 */
public class Main {
	

	/** The logger. */
	private static Logger logger = Logger.getLogger(Main.class.getName());
	
	static {
		try {
			InputStream configFile = Main.class.getClassLoader().getResourceAsStream("logger.properties");
			LogManager.getLogManager().readConfiguration(configFile);
			configFile.close();
		} catch (IOException ex) {
			System.out.println("WARNING: Could not open configuration file");
		    System.out.println("WARNING: Logging not configured (console output only)");
		}
		logger.info("running graphic controller");
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		}
		catch (Exception ex) {logger.warning("Failed to load look and feel for main");}
		
		// See the updated Account object in the model package...
		BackgroundMusic.getInstance().music();
		//ClientManager.initialize();
		
		SwingUtilities.invokeLater(new GraphicsController());
		//GraphicsController g = new GraphicsController();
	}

}
