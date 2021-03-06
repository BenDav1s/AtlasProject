package graphicTools;

import java.awt.Font;

import javax.swing.JLabel;

import resources.ResourceManager;

// TODO: Auto-generated Javadoc
/**
 * The Class Fonts.
 * @author BeMyPlayer2 Team
 */
public class Fonts {
	
	/** The Constant DEFAULT. */
	public static final Font DEFAULT = new JLabel().getFont();
	
	/** The Constant MONOSPACED. */
	public static final Font MONOSPACED = new Font("Monospaced",Font.BOLD,14);
	
	/** The Constant JETSET. */
	public static final Font JETSET = ResourceManager.loadFont("JetSet.ttf");
	
	public static final Font AGENTORANGE = ResourceManager.loadFont("AGENTORANGE.ttf");
	
	public static final Font SPLINTCIDE  = ResourceManager.loadFont("Splincide.ttf");
	
	public static final Font ALLSTAR = ResourceManager.loadFont("AllStarResort.ttf");
	
	public static final Font RICKS = ResourceManager.loadFont("RICKS.ttf");
	/** The Constant FONT_CLASS_DEFAULT_FONT. */
	private static final Font FONT_CLASS_DEFAULT_FONT = JETSET;
	
	public static Font getBoldFont(float size) {
		return FONT_CLASS_DEFAULT_FONT.deriveFont(Font.BOLD, size);
	}
	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public static Font getFont() {
		return FONT_CLASS_DEFAULT_FONT;
	}
	/**
	 * Gets the font.
	 *
	 * @param size the size
	 * @return the font
	 */
	public static Font getFont(float size) {
        return FONT_CLASS_DEFAULT_FONT.deriveFont(Font.PLAIN, size);
	}
	
	/**
	 * Gets the font.
	 *
	 * @param font the font
	 * @param size the size
	 * @return the font
	 */
	static Font getFont(Font font, float size) {
        return font.deriveFont(Font.PLAIN, size);
	}
}
