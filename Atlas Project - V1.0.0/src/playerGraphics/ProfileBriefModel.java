package playerGraphics;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import character.Character;
import combat.CombatProcess;
import gameRules.GameRules;
import graphicTools.Colors;
import graphicTools.Fonts;
import graphics.combatpage.CardPopup;
import graphics.combatpage.CombatPageController;
import graphics.combatpage.Pile;
import resources.ResourceManager;
import spells.Card;
import spells.SpellType;
// TODO: Auto-generated Javadoc
/**
 * The Class ProfileBriefModel.
 * @author Ben Davis, Colin Burdine, David Beggs, Jackson Raffety, Meghan Bibb ,Sam Muller
 */
public class ProfileBriefModel extends JPanel{
	
	/** Default serial ID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant DEFAULT_PIC. */
	public static final BufferedImage DEFAULT_PIC = resources.ResourceManager.loadImage("Sunbird.jpg");
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(ProfileBriefModel.class.getName());
	
	/**
	 * Instantiates a new profile brief model.
	 *
	 * @param profile the profile
	 * @param rect the rect
	 * @param backPage the back page
	 */
	
	private JLabel health;
	private JLabel pips;
	private Character player;
	public JLabel getHealth() {
		return this.health;
	}
	public JLabel getPips() {
		return this.pips;
	}
	public ProfileBriefModel(Character profile, Rectangle rect){
		/*
		try {
			ClientManager.setOtherProfile(profile.getUserId());
		} catch (DBFailureException e2) {
			logger.severe("Database failed to load other profile" + profile.getUserId());
		}*/
		this.setName(profile.getName());
		this.player = profile;
		CircularImage setIcon = null;
		Image img1;
		//	load image from dbs
		try {
			img1 = ResourceManager.loadImage("Sunbird.jpg");
			setIcon = new CircularImage(new ImageIcon(new ImageIcon(img1).getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT)));
		} catch (Exception e1) {
			//logger.warning("Failed to load profile picture for " + profile.getUserId()) ;
			img1 = DEFAULT_PIC;
			setIcon = new CircularImage(new ImageIcon(new ImageIcon(img1).getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT)));
		}
			

		JLabel username = new JLabel(profile.getName());
		JLabel classType = new JLabel(profile.getCharClass().name());
		health = new JLabel(String.valueOf(profile.getHP()) + "/" + String.valueOf(profile.getMaxHP()));
		pips = new JLabel(String.valueOf(profile.getPips()) + " pips");
		
		
		this.setBounds(rect);
		
		//this.setLayout(null);
		
		username.setFont(Fonts.getFont((float) 20));
		username.setForeground(Colors.White);
		//username.setBounds(105,7,150,69);
		
		classType.setFont(Fonts.getFont((float) 15));
		classType.setForeground(Colors.White);
		//classType.setBounds(105,27,150,69);
		
		health.setFont(Fonts.getFont((float) 15));
		health.setForeground(Colors.White);
		//health.setBounds(105,47,150,69);
		
		pips.setFont(Fonts.getFont((float) 15));
		pips.setForeground(Colors.White);
		//pips.setBounds(105,47,150,69);
		
		this.add(username);
		this.add(classType);
		this.add(health);
		this.add(pips);
		
		setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		//setIcon.setBounds(17, 17, 75, 75);
		this.add(setIcon);
		this.setVisible(true);
	}
	public ProfileBriefModel() {
		// TODO Auto-generated constructor stub
	}
	public boolean acceptsPile(Character caster, Pile a) {
		boolean valid = true;
		Card b = a.getBase();
		if(caster.getPips() < b.getSpell().getPips()) {
			return false;
		}
		if(b.getSpell().getType().equals(SpellType.Attack) || 
				b.getSpell().getType().equals(SpellType.Attack_All) || 
				b.getSpell().getType().equals(SpellType.Trap) ||
				b.getSpell().getType().equals(SpellType.Trap_ALL)) {
			
			if(CombatProcess.getuserTeam().contains(this.player)) {
				return false;
			}else {
				if(this.player.getHP() > 0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		
		if(b.getSpell().getType().equals(SpellType.Shield) || 
				b.getSpell().getType().equals(SpellType.Shield_ALL) || 
				b.getSpell().getType().equals(SpellType.Heal) ||
				b.getSpell().getType().equals(SpellType.Heal_ALL) ||
				b.getSpell().getType().equals(SpellType.Blade)||
				b.getSpell().getType().equals(SpellType.Blade_ALL)) {
			
			if(CombatProcess.getuserTeam().contains(this.player)) {
				return true;
			}else {
				return false;
			}
		}
		
		
		
		return valid;
	}
	public void updatePlayer(Character caster,Pile a,CombatPageController c,int count) {
		if(a!= null) {
			Card b = a.getBase();
			
			CardPopup.playCard(c.getView().getFrame(), caster, this.player, b.getSpell(),count);
			this.repaint();
		}
		else {
			CardPopup.pass(c.getView().getFrame(), caster,count,false);
			this.repaint();
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	  protected void paintComponent(Graphics g) {
		RoundRectangle2D r = new RoundRectangle2D.Float(this.getAlignmentX(), this.getAlignmentY(), this.getWidth(), this.getHeight(), 7, 7);
		g.setClip(r);
	    super.paintComponent(g);
		Image backgroundImage;
		backgroundImage = resources.ResourceManager.loadImage("loginpage.jpg");
		g.drawImage(backgroundImage.getScaledInstance(this.getWidth()+20, this.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);	
		
	}
	public Character getPlayer() {
		// TODO Auto-generated method stub
		return this.player;
	}
}
