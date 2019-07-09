package resources;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

//	cursor singleton 
public class CustomCursor {
	private static Cursor instance;
	protected CustomCursor() {};
	public static Cursor getCursor() {
		if(instance == null) {
			Toolkit t1 = Toolkit.getDefaultToolkit();
			Point point = new Point(0,0);
			BufferedImage cursorImg = ResourceManager.loadImage("customcursor.png");
			
			instance = t1.createCustomCursor(cursorImg, point, "Cursor");
			return instance;
		}
		else {
			return instance;
		}
	}
}
