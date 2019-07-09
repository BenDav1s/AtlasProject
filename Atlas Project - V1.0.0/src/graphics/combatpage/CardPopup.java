package graphics.combatpage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import character.Character;
import combat.CombatProcess;
import graphicTools.Colors;
import graphicTools.Fonts;
import graphics.GraphicsController;
import graphics.PageCreator;
import playerGraphics.ProfileBriefModel;
import resources.BackgroundPanel;
import resources.ResourceManager;
import spells.Card;
import spells.Spell;
import spells.SpellType;

public class CardPopup {
	//	stores previous background panel for redrawing
	private static JPanel previous;
	//	determines when to redraw background panel for card play
	private static int finalcount;
	/*
	 * playCard
	 * displays card play and goes through effects
	 */
	public static void playCard(JFrame mainFrame,Character caster,Character target,Spell s,int count) {
		 Timer time = new Timer(5000,new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	        	 while(finalcount==0);
	        	 System.out.println(caster.getName() + " ends with " + count + " vs " + (finalcount-1));
	        	 if(count == finalcount-1) {
		        	 mainFrame.setContentPane(previous);
		        	 mainFrame.pack();
		        	 mainFrame.setVisible(true);
		        	 CombatProcess.checkEnd();
	        	 }
	        	
	         }
	         
	     });
		
			 Timer waiter = new Timer(((5000*count)),new ActionListener() {
				 public void actionPerformed(ActionEvent e) {
					 if(caster.getHP()>  0 && target.getHP()>0) {
					 
						System.out.println("play time " + caster.getName() +" targets " + target.getName() + " with " + s.getName());
					 JPanel panel = new BackgroundPanel(s.getFaction().name()+"_faction");
				   	 panel.setPreferredSize(previous.getPreferredSize());
				   	 GridLayout layout = new GridLayout(2,5);
				   	 panel.setLayout(layout);
				   	 
				   	 //	caster spell target
				   	 //	stats  effect resist
				   	 ProfileBriefModel temp = new ProfileBriefModel(caster,new Rectangle(400,200));
				   	 JPanel briefPanelA = new JPanel();
				   	 briefPanelA.setLayout(new BorderLayout());
				   	 briefPanelA.setOpaque(false);
				   	 briefPanelA.add(temp, BorderLayout.CENTER);
				   	 //panel.add(temp);
				   	 
				   	 panel.add(briefPanelA);
				   	 
				   	 JPanel aDescriptPanel = new JPanel();
				   	 aDescriptPanel.setLayout(new BorderLayout());
				   	 aDescriptPanel.setOpaque(false);
				   	 JLabel aDescript = new JLabel();
				   	 aDescript.setText("                uses ");
				   	aDescript.setFont(graphicTools.Fonts.getFont(14f));
				   	aDescript.setForeground(graphicTools.Colors.Yellow);
				   	aDescript.setBackground(Color.BLACK);
				   	aDescriptPanel.add(aDescript,BorderLayout.CENTER);
				   	panel.add(aDescriptPanel);
				   	
				   	
				   	JPanel bDescriptPanel = new JPanel();
				   	bDescriptPanel.setLayout(new BorderLayout());
				   	bDescriptPanel.setOpaque(false);
				   	 JLabel bDescript = new JLabel();
				   	 bDescript.setText("           and targets ");
				   	bDescript.setFont(graphicTools.Fonts.getFont(14f));
				   	bDescript.setForeground(graphicTools.Colors.Yellow);
				   	bDescript.setBackground(Color.BLACK);
				   	bDescriptPanel.add(bDescript,BorderLayout.CENTER);
				   	
				   	 panel.add(new Card(s));
				   	 
				   	 panel.add(bDescriptPanel);
				   	 
				   	 
				   	 ProfileBriefModel temp2 = new ProfileBriefModel(target,new Rectangle(400,200));
				   	 
				 	 JPanel briefPanelB = new JPanel();
				   	 briefPanelB.setLayout(new BorderLayout());
				   	 briefPanelB.setOpaque(false);
				   	briefPanelB.add(temp2,BorderLayout.CENTER);
				   	 panel.add(briefPanelB);
				   	 //panel.add(temp2);
				   	 
				   	if(s.getType().equals(SpellType.Attack) || 
						s.getType().equals(SpellType.Attack_All)) {
						target.doDamage(caster, s);
						temp2.getHealth().setText((String.valueOf(target.getHP()) + "/" + String.valueOf(target.getMaxHP())));
						target.getProfileBrief().getHealth().setText((String.valueOf(target.getHP()) + "/" + String.valueOf(target.getMaxHP())));
					}
					
					if(s.getType().equals(SpellType.Trap) ||
						s.getType().equals(SpellType.Trap_ALL)) {
						target.addTrap(s);
					}
					
					if(s.getType().equals(SpellType.Shield) || 
						s.getType().equals(SpellType.Shield_ALL)) {
						target.addShield(s);
					}
					if(s.getType().equals(SpellType.Heal) ||
						s.getType().equals(SpellType.Heal_ALL)) {
						target.doHeal(s, caster);
						temp.getHealth().setText((String.valueOf(target.getHP()) + "/" + String.valueOf(target.getMaxHP())));
						caster.getProfileBrief().getHealth().setText((String.valueOf(target.getHP()) + "/" + String.valueOf(target.getMaxHP())));
					}
					if(s.getType().equals(SpellType.Blade)||
						s.getType().equals(SpellType.Blade_ALL)) {
						
						caster.addBlade(s);
					}
				   	 
				   	 if(s.getType().equals(SpellType.Shield) || s.getType().equals(SpellType.Shield_ALL)
				   			 || s.getType().equals(SpellType.Blade) || s.getType().equals(SpellType.Blade_ALL)
				   			 || s.getType().equals(SpellType.Trap) || s.getType().equals(SpellType.Trap_ALL)) {
				   		 
				   		 panel.add(new JLabel());
				   		 panel.add(new JLabel());
				   		 panel.add(new JLabel());
				   		 panel.add(new JLabel());
				   		 panel.add(new JLabel());
				   	 }
				   	 else {
				   		JLabel buffs = new JLabel();
					   	 buffs.setOpaque(false);
					   	 String text = "<HTML>";
					   	 for(String descript : caster.getBattlePlayerEffects().keySet()) {
					   		 text += descript + " :: " +caster.getBattlePlayerEffects().get(descript) + "<br>";
					   	 }
					   	 text+="</HTML>";
					   	 buffs.setText(text);
					   	 buffs.setForeground(Colors.Yellow);
					   	 buffs.setFont(Fonts.getFont(16f));
					   	 
					   	JLabel result = new JLabel();
					   	result.setOpaque(false);
					   	 String resultTxt = "";
					   	if(s.getType().equals(SpellType.Attack)|| s.getType().equals(SpellType.Attack_All)) {
					   		result.setText(String.valueOf("Damage : " + target.getDmg()));
						   	 
						   	result.setFont(Fonts.getFont(16f));
						   	result.setForeground(Colors.Yellow);
					   	}
					   	else if (s.getType().equals(SpellType.Heal) || s.getType().equals(SpellType.Heal_ALL)) {
					   		result.setText(String.valueOf("Health : " + target.getHeal()));
						   	 
						   	result.setFont(Fonts.getFont(16f));
						   	result.setForeground(Colors.Yellow);
					   	}
					   
					   	
					   	JLabel debuffs = new JLabel();
					   	debuffs.setOpaque(false);
					   	 String debuffTxt = "<HTML>";
					   	 for(String descript : target.getBattleTargetEffects().keySet()) {
					   		debuffTxt += descript + " 	"  +target.getBattleTargetEffects().get(descript) + "<br>";
					   	 }
					   	 debuffTxt+="</HTML>";
					   	debuffs.setText(debuffTxt);
					   	 
					   	debuffs.setFont(Fonts.getFont(16f));
					   	debuffs.setForeground(Colors.Yellow);
					   	
					   	
					   	panel.add(buffs);
					   	panel.add(new JLabel());
					   	panel.add(result);
					   	panel.add(new JLabel());
					   	panel.add(debuffs);
				   	 }
				   	 
				   	 
				   	 mainFrame.setContentPane(panel);
				   	 mainFrame.pack();
				   	 mainFrame.setVisible(true);
				   	 time.setRepeats(false);
				   	 time.start();
				 }
				 else {
					 pass(mainFrame,caster,count,true);
				}
				 }
			 });
			 waiter.setRepeats(false);
			 waiter.start();
		 
    	
	     
	}
	public static void pass(JFrame mainFrame,Character caster,int count,boolean dead) {
		 Timer time = new Timer(5000,new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	        	 while(finalcount==0);
	        	 System.out.println(caster.getName() + " ends pass turn with " + count + " vs "+ (finalcount-1));
	        	 if(count==finalcount-1) {
		        	 mainFrame.setContentPane(previous);
		        	 mainFrame.pack();
		        	 mainFrame.setVisible(true);
		        	 CombatProcess.checkEnd();
	        	 }
	        	 
	         }
	         
	     });
		 if(dead == false) {
			 Timer waiter = new Timer(((5000*count)),new ActionListener() {
				 public void actionPerformed(ActionEvent e) {
					 JPanel panel = new BackgroundPanel(caster.getCharClass().name()+"_faction");
				   	 panel.setPreferredSize(previous.getPreferredSize());
				   	
				   	
				   	 JLabel pass = new JLabel("Pass");
				   	pass.setFont(graphicTools.Fonts.getFont(14f));
				   	pass.setForeground(graphicTools.Colors.Yellow);
				   	panel.add(new ProfileBriefModel(caster,new Rectangle(400,200)));
				   	 panel.add(pass);
				   	 mainFrame.setContentPane(panel);
				   	 mainFrame.pack();
				   	 mainFrame.setVisible(true);
				   	 
				   	 time.setRepeats(false);
				   	 time.start();
		         }
			 });
			 waiter.setRepeats(false);
			 waiter.start();
		 }
		 else {
			 JPanel panel = new BackgroundPanel(caster.getCharClass().name()+"_faction");
		   	 panel.setPreferredSize(previous.getPreferredSize());
		   	
		   	
		   	 JLabel pass = new JLabel("Pass");
		   	pass.setFont(graphicTools.Fonts.getFont(14f));
		   	pass.setForeground(graphicTools.Colors.Yellow);
		   	panel.add(new ProfileBriefModel(caster,new Rectangle(400,200)));
		   	 panel.add(pass);
		   	 mainFrame.setContentPane(panel);
		   	 mainFrame.pack();
		   	 mainFrame.setVisible(true);
		   	 
		   	 time.setRepeats(false);
		   	 time.start();
		 }
		
		 
	   	 
	}
	public static void setPanel(Container contentPane) {
		// TODO Auto-generated method stub
		previous = (JPanel) contentPane;
	}
	public static void setFinalCount(int finalCount2) {
		// TODO Auto-generated method stub
		finalcount = finalCount2;
	}
}
