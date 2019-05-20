package resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;


// TODO: Auto-generated Javadoc
/**
 * The Class ResourceManager.
 * @author BeMyPlayer2 Project Team
 */
public class ResourceManager {
	
	/** The Constant IMAGE_FOLDER. */
	public static final String IMAGE_FOLDER = "src/main/resources/img/";
	
	/** The Constant FONTS_FOLDER. */
	public static final String FONTS_FOLDER = "src/main/resources/fonts/";
	
	/** The logger. */
	private static Logger LOGGER = Logger.getLogger(ResourceManager.class.getName());
	
	/** The Constant imageCache. */
	private static final Map<String, BufferedImage> imageCache = new HashMap<>();
	
	/** The Constant fontCache. */
	private static final Map<String, Font> fontCache = new HashMap<>();
	
	/**
	 * Load image.
	 *
	 * @param name the name
	 * @return the buffered image
	 */
	public static BufferedImage loadImage(String name) {
		
		BufferedImage retImage = null;
		
		if(imageCache.containsKey(name)) {
			retImage = imageCache.get(name);
		}else {
			try {
				retImage = ImageIO.read(new File(IMAGE_FOLDER + name));
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Failed to load image resource: " + name);
				return null;
			}
			imageCache.put(name, retImage);
		}
		
		return retImage;
	}
	public static String loadText(String name) {
		return "hi";
	}
	/**
	 * Load font.
	 *
	 * @param name the name
	 * @param size the size
	 * @return the font
	 */
	public static Font loadFont(String name, float size) {
		return loadFont(name).deriveFont(Font.PLAIN, size);
	}
	
	/**
	 * Load font.
	 *
	 * @param name the name
	 * @return the font
	 */
	public static Font loadFont(String name) {
		Font retFont = null;
		if(fontCache.containsKey(name)) {
			retFont = fontCache.get(name);
		}else {
			InputStream fontStream;
			try {
				File fontFile = new File(FONTS_FOLDER + name);
				fontStream = new BufferedInputStream(new FileInputStream(fontFile));
			} catch (FileNotFoundException e1) {
				LOGGER.log(Level.SEVERE, "Failed to find font resource: " + name);
				return null;
			}
			try {
				retFont =  Font.createFont(Font.TRUETYPE_FONT, fontStream);
				fontCache.put(name, retFont);
			} catch (FontFormatException | IOException e) {
				LOGGER.log(Level.SEVERE, "Failed to load font resource: " + name);
				return null;
			}
		}
		return retFont;
	}
}
