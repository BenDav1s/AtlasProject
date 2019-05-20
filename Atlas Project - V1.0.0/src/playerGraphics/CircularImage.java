/*
 * 
 */
package playerGraphics;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import javax.swing.Icon;
import javax.swing.JLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class CircularImage.
 * @author Ben Davis, Colin Burdine, David Beggs, Jackson Raffety, Meghan Bibb ,Sam Muller
 */
public class CircularImage extends JLabel{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new circular image.
	 *
	 * @param image the image
	 */
	public CircularImage(Icon image) {
		super(image);
	}
	
	/**
	 * Instantiates a new circular image.
	 */
	public CircularImage() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	  protected void paintComponent(Graphics g) {
		Ellipse2D r = new Ellipse2D.Float(this.getAlignmentX(), this.getAlignmentY(), this.getWidth(), this.getHeight());
		g.setClip(r);
	    super.paintComponent(g);
	}
	
}
