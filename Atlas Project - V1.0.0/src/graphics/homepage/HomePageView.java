package graphics.homepage;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import resources.BackgroundPanel;

public class HomePageView {
	private HomePageController c;
	private JFrame frame;
	private JPanel panel;
	
	public HomePageView(HomePageController homePageController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.c = homePageController;
		
		this.frame = mainFrame;
		
		this.frame.setContentPane(this.panel = new BackgroundPanel(null));
		((BackgroundPanel) this.panel).changeBackground("homepage.jpg");
		this.panel.setPreferredSize(new Dimension(1080,720));
		this.frame.setLayout(new BorderLayout());
		
		
		this.frame.setContentPane(this.panel);
		this.frame.revalidate();
		
		
	}

}
