package graphics.createCharacterPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import graphics.createAccountPage.CreateAccountPageController;
import graphics.createAccountPage.CreateAccountPageView;
import graphics.createAccountPage.FactionLeftPanel;
import graphics.createAccountPage.FactionRightPanel;
import resources.BackgroundPanel;
import resources.CircleTest;
import resources.CustomCursor;
import resources.Faction;

public class CharacterCreationView extends CreateAccountPageView{
	private JFrame frame;
	private JPanel panel;
	
	private CharacterCreationController c2;
	
	private JTextField username;
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
	public CharacterCreationController getC2() {
		return this.c2;
	}
	public void setC(CharacterCreationController c) {
		this.c2 = c;
	}

	public JTextField getUsername() {
		return username;
	}
	public void setUsername(JTextField username) {
		this.username = username;
	}


	public CharacterCreationView(CharacterCreationController characterCreationController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		// TODO Auto-generated constructor stub
		super();
				this.c2 = characterCreationController;
				
				this.frame = mainFrame;
				
				this.frame.setContentPane(this.panel = new BackgroundPanel());
				this.panel.setPreferredSize(new Dimension(1080,720));
				this.frame.setLayout(new BorderLayout());
				this.frame.setCursor(CustomCursor.getCursor());
				
				this.frame.setContentPane(this.panel);
				this.frame.revalidate();
				
				
				//	add buttons and labels
				
				JButton createNewAccount = new JButton("Submit");
				createNewAccount.setPreferredSize(new Dimension(200,50));
				createNewAccount.setContentAreaFilled(false);
				createNewAccount.setFont(graphicTools.Fonts.getFont(14f));
				createNewAccount.setForeground(graphicTools.Colors.Yellow);
				createNewAccount.setActionCommand("submit");
				createNewAccount.addActionListener(this.c2);
				
				JButton exit = new JButton("Back");
				exit.setPreferredSize(new Dimension(200,50));
				exit.setContentAreaFilled(false);
				exit.setFont(graphicTools.Fonts.getFont(14f));
				exit.setForeground(graphicTools.Colors.Yellow);
				exit.setActionCommand("back");
				exit.addActionListener(this.c2);
				
				JLabel user = new JLabel("Username");
				user.setFont(graphicTools.Fonts.getFont((float) 14));
				user.setForeground(graphicTools.Colors.Yellow);
				
				JTextField userbtn = new JTextField();
				userbtn.setFont(graphicTools.Fonts.getFont(12f));
				userbtn.setForeground(graphicTools.Colors.Yellow);
				this.setUsername(userbtn);

				
				GridLayout masterLayout = new GridLayout(3,3);
				masterLayout.setHgap(20);
				masterLayout.setVgap(15);
				this.panel.setLayout(masterLayout);
				
				GridLayout layout = new GridLayout(8,1);
				//	holds user name and password info
				JPanel leftHolder = new JPanel();
				layout.setVgap(5);
				leftHolder.setLayout(layout);
				leftHolder.add(user);
				leftHolder.add(userbtn);
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
				circle.addMouseListener(this.c2);
				centerHolder.add(circle);
				centerHolder.setOpaque(false);
				this.panel.add(centerHolder);
				
				//	holds security questions and answers as well as email
				JPanel rightHolder = new JPanel();
				rightHolder.setLayout(layout);
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

}
