package resources;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
	/** Singleton instance of background panel. */
	//private static BackgroundPanel instance;
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(BackgroundPanel.class.getName());
	
	//	other page options
	public static final String LOGINPAGE = "loginpage";
	public static final String BACKGROUND = "background";
	/** The base image. */
	public static BufferedImage baseImage = ResourceManager.loadImage("loginpage.jpg");
	
	/** The scaled background. */
	private static Image scaledBackground;
	
	/** The updated. */
	private static boolean updated = false;
	
	/**
	 * Instantiates a new background panel.
	 *
	 * @param object the object
	 */
	/*
	public BackgroundPanel(Object object) {
		super(null);
		scaledBackground = baseImage;
		updated = false;
	}*//*
	public static BackgroundPanel getInstance(String page){
		String picname = null;
		switch(page) {
			case LOGINPAGE: picname = "loginpage.jpg";
				break;
			case BACKGROUND: picname = "background.jpg";
				break;
		}
		baseImage = ResourceManager.loadImage(picname);
		scaledBackground = baseImage;
		if(instance == null) {
			instance = new BackgroundPanel();
			updated = false;
		}
		return instance;
	}*/
	public BackgroundPanel() {
		super();
		scaledBackground = baseImage;
		updated = false;
	}
	public BackgroundPanel(String page) {
		super();
		scaledBackground = baseImage = ResourceManager.loadImage(page+".jpg");
		updated = false;
		
	}
	public void changeBackground(String page) {
		if(page.equalsIgnoreCase(LOGINPAGE)) {
			scaledBackground = ResourceManager.loadImage(LOGINPAGE);
		}
		else {
			scaledBackground = ResourceManager.loadImage(page);
		}
		updated = true;
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	   // super.paintComponents(g);
	    /*
	    if(this.getComponentCount() > 14 && this.getComponent(14).getName().equalsIgnoreCase("button")) {

		    this.getComponent(14).repaint();
	    }
	    */
	    if(!updated && this.getHeight() > 0 && this.getWidth() > 0) {
	    	resize();
	    	this.updated = false;
	    }
	    Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(scaledBackground, 0, 0, this);
		
		
	}
	/*
	public void paint(Graphics g){
		super.paint(g);
		super.paintComponents(g);
		resize();
		g.drawImage(scaledBackground, 0, 0, this);
	}*/

	/**
	 * Resize.
	 */
	public void resize() {
		int newWidth = 0, newHeight = 0;
		double baseAspect = (double) baseImage.getHeight() / (double) baseImage.getWidth();
		double fitAspect = (double) this.getHeight() / (double) this.getWidth();
		
		if(fitAspect > baseAspect) {
			newHeight = this.getHeight();
			newWidth = 1 + (int)(this.getHeight() / baseAspect);
		}else {
			newWidth = this.getWidth();
			newHeight = 1 + (int)(this.getWidth() * baseAspect);
		}
				
		scaledBackground = baseImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	}
	
	/**
	 * Update.
	 */
	public void update() {
		updated = false;
	}
	
}

