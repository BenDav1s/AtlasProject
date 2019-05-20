package combat;

import java.awt.Component;
import java.util.ArrayList;
import character.Class;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import spells.Card;
import spells.Global;
import spells.Spell;
import spells.SpellType;
import statistics.Effect;
import statistics.Stats;
import statistics.Type;
import character.Character;
import gameRules.GameRules;
import graphics.combatpage.CombatPageController;
import graphics.combatpage.Pile;
import playerGraphics.ProfileBriefModel;
public class CombatProcess {
	private static List<Character> userTeam = new ArrayList<Character>();
	private static List<Character> enemyTeam = new ArrayList<Character>();
	private CombatPageController pageController;
	private static boolean active;
	public static boolean accuracy(double d) {
		return Math.random() <= d;
	}
	public static List<Character> getuserTeam(){
		return userTeam;
	}
	public static List<Character> getenemyTeam(){
		return enemyTeam;
	}
	public static Global currentGlobal = Global.NORMAL;
	
	public CombatProcess(CombatPageController c) {
		this.pageController = c;
	}
	public void initgame() {
		drawgame();
		active = accuracy(Math.random());
		//	while health loop

	}
	public void drawgame() {
		System.out.println("drawing game");
		String deckStr;
		List<Spell> aSpells = new ArrayList<>();
		Spell b = new Spell();
		b.makeAttackSpell(Class.The_Called, "Bob", 79, 800);
		aSpells.add(b);
		Spell c = new Spell();
		c.makeHealSpell(Class.The_Called, "heals", 100, 800);
		aSpells.add(c);
		
		
		Character a = new Character (aSpells,8000,"bob",Class.The_Children_Of_The_Mask);
		Stats ast = new Stats();
		ast.setLevel(1);
		Effect e = new Effect();
		e.setType(Type.DMG_BOOST);
		ast.getEffects().add(e);
		a.setStats(ast);
		
		GameRules.activeUser = a;
		
		
		Character d= new Character (aSpells,8000,"jane",Class.The_Called);
		Stats bst = new Stats();
		bst.setLevel(1);
		Effect ef = new Effect();
		ef.setType(Type.DMG_BOOST);
		ast.getEffects().add(ef);
		d.setStats(bst);
		
		userTeam.add(a);
		enemyTeam.add(d);
		for(Character ch : userTeam) {
			ch.getDeck().shuffle();
		}
		for(Character che : enemyTeam) {
			che.getDeck().shuffle();
		}
	}
	public void endgame() {
		System.out.println("game over");
	}
	
	public void waitFor() {
		System.out.println("timing turn");
	}
	public void checkEnd() {
		boolean gameOver = false;
		if(this.userTeam.stream().allMatch(t-> t.getHP()<=0)) {
			System.out.println("enemy wins");
			gameOver= true;
		}
		if(this.userTeam.stream().allMatch(t->t.getHP()<=0)) {
			System.out.println("You win");
			gameOver = true;
		}
		if(!gameOver) {
			for(Character c :  this.userTeam) {
				if(c.equals(GameRules.activeUser)) {
					
					if(GameRules.activeUser.getDeck().getCards().size() >0) {
						Card temp = GameRules.activeUser.getDeck().drawCard();
						temp.addMouseListener(this.pageController);
						temp.addMouseMotionListener(this.pageController);
						temp.show();
						Pile p = new Pile(temp);
						
						this.pageController.getView().getColumns().add(p);
						this.pageController.getView().handPiles.add(p);
					}
				}
				else {
					c.drawCard();
				}
				c.addPip();
				c.getProfileBrief().repaint();
			}
			for(Character c: this.enemyTeam) {
			/*
				if(c.getDeck().getCards().size() > 0) {
					c.drawCard();
				}*/
				c.addPip();
				c.getProfileBrief().repaint();
			}
			this.pageController.getView().getFrame().repaint();
		}
	}
	public void combatphase(Pile a, boolean d) {
		Spell s = a.getBase().getSpell();
		if(active) {
			if(s.getType().equals(SpellType.Attack_All) || s.getType().equals(SpellType.Trap_ALL)) {
				for(Character c : this.enemyTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeUser, a);
				}
			}
			if(s.getType().equals(SpellType.Blade_ALL) || s.getType().equals(SpellType.Heal_ALL) || 
					s.getType().equals(SpellType.Shield_ALL)) {
				for(Character c: this.userTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeUser, a);
				}
			}
		}
		else {
			if(s.getType().equals(SpellType.Attack_All) || s.getType().equals(SpellType.Trap_ALL)) {
				for(Character c : this.enemyTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeUser, a);
				}
			}
			if(s.getType().equals(SpellType.Blade_ALL) || s.getType().equals(SpellType.Heal_ALL) || 
					s.getType().equals(SpellType.Shield_ALL)) {
				for(Character c: this.userTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeUser, a);
				}
			}
		}
		checkEnd();
	}
	public void combatphase(Component a, Pile b) {
		//	friendly goes first
		if(active) {
			((ProfileBriefModel) a).updatePlayer(GameRules.activeUser,b);
		}
		else {
			for(Character c : this.enemyTeam)
			System.out.println("its my turn now!!");
			((ProfileBriefModel) a).updatePlayer(GameRules.activeUser,b);
		}
		checkEnd();
	}
}
