package playerGraphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageConverter.
 * @author Ben Davis, Colin Burdine, David Beggs, Jackson Raffety, Meghan Bibb ,Sam Muller
 */
public class ImageConverter {
	
	//static final String OS_TEMP_DIR_FILE = System.getProperty("java.io.tmpdir") + "img.jpg";
	//private static final Logger LOGGER = Logger.getLogger(ImageConverter.class.getName());
	
	//private static File tmpFile = new File(OS_TEMP_DIR_FILE);
	
	
	/**
	 * Convert to JPG.
	 *
	 * @param image the image
	 * @return the buffered image
	 */
	public static BufferedImage convertToJPG(BufferedImage image) {
		BufferedImage newBufferedImage = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_RGB);
			  newBufferedImage.createGraphics().drawImage(image, 0, 0, 
					  image.getWidth(), image.getHeight(), Color.WHITE, null);
		return newBufferedImage;
	}
}
