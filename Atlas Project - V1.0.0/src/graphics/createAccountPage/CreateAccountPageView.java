package graphics.createAccountPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import resources.BackgroundPanel;
import resources.CircleTest;
import resources.Faction;
import character.Class;
import graphicTools.Colors;
import graphicTools.Fonts;
public class CreateAccountPageView {
	private JFrame frame;
	private JPanel panel;
	
	private CreateAccountPageController c;
	
	private JPasswordField password;
	private JTextField username;
	private JPasswordField passConfirm;
	private JComboBox gender;
	private JComboBox securityquestions;
	private JTextField securityAnswer;
	private JTextField email;
	private JPanel circle;
	private JPanel factionSelected;
	private JPanel factionFlavor;
	private JPanel factionCombat;
	
	public JPanel getFactionFlavor() {
		return factionFlavor;
	}
	public void setFactionFlavor(JPanel factionFlavor) {
		this.factionFlavor = factionFlavor;
	}
	public JPanel getFactionCombat() {
		return factionCombat;
	}
	public void setFactionCombat(JPanel factionCombat) {
		this.factionCombat = factionCombat;
	}
	public JPanel getFactionSelected() {
		return factionSelected;
	}
	public void setFactionSelected(JPanel factionSelected) {
		this.factionSelected = factionSelected;
	}
	public JPanel getCircle() {
		return circle;
	}
	public void setCircle(JPanel circle) {
		this.circle = circle;
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public CreateAccountPageController getC() {
		return c;
	}
	public void setC(CreateAccountPageController c) {
		this.c = c;
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
	public JPasswordField getPassConfirm() {
		return passConfirm;
	}
	public void setPassConfirm(JPasswordField passConfirm) {
		this.passConfirm = passConfirm;
	}


	public CreateAccountPageView(CreateAccountPageController createAccountPageController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.c = createAccountPageController;
		
		this.frame = mainFrame;
		
		this.frame.setContentPane(this.panel = new BackgroundPanel(null));
		this.panel.setPreferredSize(new Dimension(1080,720));
		this.frame.setLayout(new BorderLayout());
		
		
		this.frame.setContentPane(this.panel);
		this.frame.revalidate();
		
		
		//	add buttons and labels
		
		JButton createNewAccount = new JButton("Submit");
		createNewAccount.setPreferredSize(new Dimension(200,50));
		createNewAccount.setContentAreaFilled(false);
		createNewAccount.setFont(graphicTools.Fonts.getFont(14f));
		createNewAccount.setForeground(graphicTools.Colors.Yellow);
		createNewAccount.setActionCommand("submit");
		createNewAccount.addActionListener(this.c);
		
		JButton exit = new JButton("Back");
		exit.setPreferredSize(new Dimension(200,50));
		exit.setContentAreaFilled(false);
		exit.setFont(graphicTools.Fonts.getFont(14f));
		exit.setForeground(graphicTools.Colors.Yellow);
		exit.setActionCommand("back");
		exit.addActionListener(this.c);
		
		JLabel user = new JLabel("Username");
		user.setFont(graphicTools.Fonts.getFont((float) 14));
		user.setForeground(graphicTools.Colors.Yellow);
		
		JTextField userbtn = new JTextField();
		userbtn.setFont(graphicTools.Fonts.getFont(12f));
		userbtn.setForeground(graphicTools.Colors.White);
		this.setUsername(userbtn);
		
		JLabel email = new JLabel("Email");
		email.setFont(graphicTools.Fonts.getFont((float) 14));
		email.setForeground(graphicTools.Colors.Yellow);
		
		JTextField emailbtn = new JTextField();
		emailbtn.setFont(graphicTools.Fonts.getFont(12f));
		emailbtn.setForeground(graphicTools.Colors.White);
		this.setEmail(userbtn);
		
		JLabel pass = new JLabel("Password");
		pass.setFont(graphicTools.Fonts.getFont((float) 14));
		pass.setForeground(graphicTools.Colors.Yellow);
		
		JPasswordField passbtn = new JPasswordField();
		passbtn.setFont(graphicTools.Fonts.getFont(12f));
		this.setPassword(passbtn);
		
		JLabel repass = new JLabel("ReEnter password");
		repass.setFont(graphicTools.Fonts.getFont((float) 14));
		repass.setForeground(graphicTools.Colors.Yellow);
		
		JPasswordField repassbtn = new JPasswordField();
		repassbtn.setFont(graphicTools.Fonts.getFont(12f));
		this.setPassConfirm(repassbtn);
		
		
		JComboBox<String> gender = new JComboBox<String>();
		gender.setForeground(graphicTools.Colors.Yellow);
		gender.setFont(graphicTools.Fonts.getFont((float) 12));
		gender.setToolTipText("Gender");
		gender.setModel(new DefaultComboBoxModel<String>(new String[] {"Male", "Female"}));
		gender.setVisible(true);
		this.setGender(gender);
		
		JComboBox<String> securityQuestions = new JComboBox<String>();
		securityQuestions.setForeground(graphicTools.Colors.Yellow);
		securityQuestions.setFont(graphicTools.Fonts.getFont((float) 12));
		securityQuestions.setToolTipText("Security Questions");
		securityQuestions.setModel(new DefaultComboBoxModel<String>(new String[] {"Favorite Game", "First Car","Parent's name"}));
		securityQuestions.setVisible(true);
		this.setSecurityquestions(securityQuestions);
		
		JTextField securityAnswer = new JTextField();
		securityAnswer.setFont(graphicTools.Fonts.getFont(12f));
		this.setSecurityAnswer(securityAnswer);
		
		GridLayout masterLayout = new GridLayout(3,3);
		masterLayout.setHgap(20);
		masterLayout.setVgap(15);
		this.panel.setLayout(masterLayout);
		
		GridLayout layout = new GridLayout(8,1);
		//	holds user name and password info
		JPanel leftHolder = new JPanel();
		layout.setVgap(7);
		leftHolder.setLayout(layout);
		leftHolder.add(user);
		leftHolder.add(userbtn);
		leftHolder.add(pass);
		leftHolder.add(passbtn);
		leftHolder.add(repass);
		leftHolder.add(repassbtn);
		leftHolder.setOpaque(false);
		//leftHolder.setPreferredSize(new Dimension(500,300));
		leftHolder.repaint();
		//	adds blank labels to take up space 
		factionSelected = new Faction(null);
		factionFlavor = new FactionLeftPanel(null);
		factionCombat = new FactionRightPanel(null);
		this.panel.add(factionFlavor);
		this.panel.add(this.getFactionSelected());
		this.panel.add(factionCombat);
		
		
		this.panel.add(leftHolder);
		
		//	holds circle of factions
		JPanel centerHolder = new JPanel();
		centerHolder.setLayout(new GridLayout(1,1));
		centerHolder.setOpaque(false);
		
		//	adds faction circle to the screen
		circle = new CircleTest(7);
		circle.setVisible(true);
		circle.setOpaque(false);
		circle.addMouseListener(this.c);
		centerHolder.add(circle);
		centerHolder.setOpaque(false);
		this.panel.add(centerHolder);
		
		//	holds security questions and answers as well as email
		JPanel rightHolder = new JPanel();
		rightHolder.setLayout(layout);
		rightHolder.add(gender);
		rightHolder.add(securityQuestions);
		rightHolder.add(securityAnswer);
		rightHolder.add(email);
		rightHolder.add(emailbtn);
		rightHolder.setOpaque(false);
		this.panel.add(rightHolder);
		
		//	holds back button
		JPanel bottomLeftHolder = new JPanel();
		bottomLeftHolder.setLayout(layout);
		for(int i = 0 ; i < 6; i++) {
			bottomLeftHolder.add(new JLabel());
		}
		bottomLeftHolder.add(exit);
		bottomLeftHolder.setOpaque(false);
		//bottomLeftHolder.setPreferredSize(new Dimension(400,300));
		
		this.panel.add(bottomLeftHolder);
		
		this.panel.add(new JLabel());
		//	holds submit button
		JPanel bottomRightHolder = new JPanel();
		bottomRightHolder.setLayout(layout);
		bottomRightHolder.setOpaque(false);
		for(int i = 0; i < 6;i++) {
			bottomRightHolder.add(new JLabel());
		}
		bottomRightHolder.add(createNewAccount);
		this.panel.add(bottomRightHolder);
		
		//pack and make visible
		this.frame.pack();
		this.frame.setVisible(true);
	}
	public JComboBox getGender() {
		return gender;
	}
	public void setGender(JComboBox gender) {
		this.gender = gender;
	}
	public JComboBox getSecurityquestions() {
		return securityquestions;
	}
	public void setSecurityquestions(JComboBox securityquestions) {
		this.securityquestions = securityquestions;
	}
	public JTextField getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(JTextField securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public JTextField getEmail() {
		return email;
	}
	public void setEmail(JTextField email) {
		this.email = email;
	}

}
