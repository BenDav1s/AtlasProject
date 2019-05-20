package graphics.loginpage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import graphicTools.Colors;
import resources.BackgroundPanel;
import resources.SlideshowThread;

public class LoginPageView {
	private JFrame frame;
	private BackgroundPanel panel;
	
	private JTextField username;
	private JPasswordField password;
	private SlideshowThread sthread;
	
	public SlideshowThread getSlideThread() {
		return this.sthread;
	}
	public JFrame getFrame() {
		return this.frame;
	}
	public BackgroundPanel getPanel() {
		return this.panel;
	}
	public LoginPageView(LoginPageController loginController, JFrame mainFrame) {
		//	init frame
		this.frame = mainFrame;
		
		//	init panel
		BackgroundPanel background = new BackgroundPanel(null);
		
		this.panel = background;
		
		sthread = new SlideshowThread(this.panel);
		sthread.start();
		
		this.panel.setPreferredSize(new Dimension(1080, 720));
		this.frame.setContentPane(this.panel);
		this.frame.revalidate();
		//	add buttons and labels
		
		JButton loginbtn  = new JButton("Login");
		//loginbtn.setVisible(true);
		loginbtn.setText("Login");
		loginbtn.setPreferredSize(new Dimension(200,50));
		loginbtn.setContentAreaFilled(false);
		loginbtn.setFont(graphicTools.Fonts.getFont(14f));
		loginbtn.setForeground(graphicTools.Colors.Yellow);
		loginbtn.setActionCommand("login");
		loginbtn.addActionListener(loginController);
		
		JButton forgotPassbtn = new JButton("Forgot Password");
		forgotPassbtn.setPreferredSize(new Dimension(200,50));
		forgotPassbtn.setContentAreaFilled(false);
		forgotPassbtn.setFont(graphicTools.Fonts.getFont(14f));
		forgotPassbtn.setForeground(graphicTools.Colors.Yellow);
		forgotPassbtn.setActionCommand("forgot");
		forgotPassbtn.addActionListener(loginController);
		
		JButton createNewAccount = new JButton("Create New Account");
		createNewAccount.setPreferredSize(new Dimension(200,50));
		createNewAccount.setContentAreaFilled(false);
		createNewAccount.setFont(graphicTools.Fonts.getFont(14f));
		createNewAccount.setForeground(graphicTools.Colors.Yellow);
		createNewAccount.setActionCommand("create");
		createNewAccount.addActionListener(loginController);
		
		JButton exit = new JButton("Exit");
		exit.setPreferredSize(new Dimension(200,50));
		exit.setContentAreaFilled(false);
		exit.setFont(graphicTools.Fonts.getFont(14f));
		exit.setForeground(graphicTools.Colors.Yellow);
		exit.setActionCommand("exit");
		exit.addActionListener(loginController);
		
		JLabel user = new JLabel("Username");
		user.setFont(graphicTools.Fonts.getFont((float) 14));
		user.setForeground(graphicTools.Colors.Yellow);
		
		JTextField userbtn = new JTextField();
		userbtn.setFont(graphicTools.Fonts.getFont(12f));
		userbtn.setForeground(graphicTools.Colors.White);
		
		JLabel pass = new JLabel("Password");
		pass.setFont(graphicTools.Fonts.getFont((float) 14));
		pass.setForeground(graphicTools.Colors.Yellow);
		
		JPasswordField passbtn = new JPasswordField();
		passbtn.setFont(graphicTools.Fonts.getFont(12f));
		this.setPassword(passbtn);
		
		
		this.panel.setLayout(new GridLayout(3,6));
		
		
		JPanel buttonHolder = new JPanel();
		GridLayout layout = new GridLayout(8,1);
		layout.setVgap(15);
		buttonHolder.setLayout(layout);
		buttonHolder.setName("button");
		
		buttonHolder.add(user);
		buttonHolder.add(userbtn);
		buttonHolder.add(pass);
		buttonHolder.add(passbtn);
		buttonHolder.add(loginbtn);
		buttonHolder.add(createNewAccount);
		buttonHolder.add(forgotPassbtn);
		buttonHolder.setOpaque(false);
		buttonHolder.setPreferredSize(new Dimension(500,300));
		buttonHolder.repaint();
		//this.panel.add(loginbtn);
		
		for(int i  = 0; i < 6; i++) {
			this.panel.add(new JLabel());
		}
		for(int i  =0; i < 4; i++) {
			this.panel.add(new JLabel());
		}
		
		this.panel.add(buttonHolder);
		this.panel.add(new JLabel());
		
		JPanel tempHolder = new JPanel();
		tempHolder.setLayout(layout);
		for(int i = 0 ; i < 5; i++) {
			tempHolder.add(new JLabel());
		}
		tempHolder.add(exit);
		tempHolder.setOpaque(false);
		tempHolder.setPreferredSize(new Dimension(400,300));
		tempHolder.repaint();
		this.panel.add(tempHolder);
		for(int i  = 0; i < 5; i++) {
			this.panel.add(new JLabel());
		}
		
		
		//pack and make visible
		this.frame.pack();
		this.frame.setVisible(true);
	}
	public JPasswordField getPassword() {
		return password;
	}
	public void setPassword(JPasswordField password) {
		this.password = password;
	}
	public JTextField getUsername() {
		return username;
	}
	public void setUsername(JTextField username) {
		this.username = username;
	}
	

}
