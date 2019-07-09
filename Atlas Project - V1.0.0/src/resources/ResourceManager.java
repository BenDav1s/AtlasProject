package resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import database.DatabaseAdapter;
import graphics.InvalidPopup;
import items.Item;


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
	
	public static final String TEXT_FOLDER = "src/main/resources/text/";
	
	/** The logger. */
	private static Logger LOGGER = Logger.getLogger(ResourceManager.class.getName());
	
	/** The Constant imageCache. */
	private static final Map<String, BufferedImage> imageCache = new HashMap<>();
	
	/** The Constant fontCache. */
	private static final Map<String, Font> fontCache = new HashMap<>();
	
	private static final Map<String,Item> itemCache = new HashMap<>();
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
				try {
					name = name.replace("jpg","png");
					retImage= ImageIO.read(new File(IMAGE_FOLDER+name));
				}
				catch(IOException e2) {
					LOGGER.log(Level.SEVERE, "Failed to load image resource: " + name);
					return null;
				}
				
			}
			imageCache.put(name, retImage);
		}
		
		return retImage;
	}
	public static String loadText(String name) {
		String text = "";
		try {
			FileInputStream fstream = new FileInputStream(TEXT_FOLDER+name);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String line = br.readLine();
			while(line != null) {
				text = text.concat(line);
				line = br.readLine();
			}
			fstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
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
	public static void addItem(Item b) {
		if(!itemCache.containsKey(b.getName())) {
			itemCache.put(b.getName(), b);
		}
	}
	public static Item loadItem(String name) {
		Item item = null;
		if(itemCache.containsKey(name)) {
			item = itemCache.get(name);
		}else {
			//	read item from file
			boolean valid = true;
			try {
				item = DatabaseAdapter.loadItemList(name);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				InvalidPopup p = new InvalidPopup(new JPanel(),"Database failed to load item");
				valid = false;
			}
			if(valid) {
				itemCache.put(name, item);
			}
			
		}
		return item;
	}
}
