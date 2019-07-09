package character;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import combat.CombatProcess;
import gameRules.GameRules;
import graphics.combatpage.CardPopup;
import playerGraphics.ProfileBriefModel;
import resources.ResourceManager;
import spells.Card;
import spells.Deck;
import spells.Global;
import spells.Spell;
import spells.SpellType;
import spells.WardType;
import statistics.Effect;
import statistics.Stats;
import statistics.Type;


public class Character{
	//private HashMap<Integer, Object[]> wards = new HashMap<Integer, Object[]>();
	//	max hp
	private int maxHP;
	private int level;
	//	current hp
	private int HP;
	private int pips;
	private int maxPips;
	private String name;
	private Deck deck;
	private List<Card> hand = new ArrayList<>();
	private ProfileBriefModel profileBrief;
	private Stats stats;
	private FactionTypes charClass;
	private List<Spell> traps = new ArrayList<>();
	private List<Spell> shields= new ArrayList<>();
	private List<Spell> blades= new ArrayList<>();
	private List<Spell> damageOverTime= new ArrayList<>();
	private double threat;
	private double weakness;
	
	public void setTip() {
		String spellText ="<html>";
		for(Spell s : blades) {
			spellText += s.getImpactList()+ " outgoing damage boost :: " + s.getBoost() + "% <br>";
		}
		for(Spell s: shields) {
			spellText += s.getImpactList() + " incoming damage resist :: " + s.getResist() + "% <br>";
		}
		for(Spell s : traps) {
			spellText += s.getImpactList()+ " incoming damage boost :: " + s.getBoost() + "% <br>";
		}
		spellText += "</html>";
		this.getProfileBrief().setToolTipText(spellText);
	}
	public int getMaxPips() {
		return maxPips;
	}

	public void setMaxPips(int maxPips) {
		this.maxPips = maxPips;
	}

	public List<Spell> getTraps() {
		return traps;
	}

	public void setTraps(List<Spell> traps) {
		this.traps = traps;
	}

	public List<Spell> getShields() {
		return shields;
	}

	public void setShields(List<Spell> shields) {
		this.shields = shields;
	}

	public List<Spell> getBlades() {
		return blades;
	}

	public void setBlades(List<Spell> blades) {
		this.blades = blades;
	}

	public List<Spell> getDamageOverTime() {
		return damageOverTime;
	}

	public void setDamageOverTime(List<Spell> damageOverTime) {
		this.damageOverTime = damageOverTime;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public void setPips(int pips) {
		this.pips = pips;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	public FactionTypes getCharClass() {
		return charClass;
	}

	public void setCharClass(FactionTypes charClass) {
		this.charClass = charClass;
	}

	public ProfileBriefModel getProfileBrief() {
		return profileBrief;
	}

	public void setProfileBrief(ProfileBriefModel profileBrief) {
		this.profileBrief = profileBrief;
	}

	public Character(List<Spell> spellsval, int HPval, String name,FactionTypes clas){
		this.maxHP = HPval;
		this.HP = HPval;
		this.pips = 1;
		this.maxPips = 18;
		this.name = name;
		this.deck = new Deck(spellsval);
		this.charClass = clas;
		profileBrief = new ProfileBriefModel(this,new Rectangle(100,100));
	}

	public Character() {
		// TODO Auto-generated constructor stub
	}

	public Deck getDeck() {
		return this.deck;
	}
	public List<Card> getHand(){
		return this.hand;
	}
	public void drawNewHand() {
		int counter = 0;
		while(deck.getCurrentSize()> 0 && counter < 6) {
			
			hand.add(deck.getTop());
		}
	}
	public boolean drawCard() {
		if(deck.getCurrentSize() == 0) {
			return false;
		}
		hand.add(deck.drawCard());
		
		return true;
	}
	public void addTrap(Spell s){
		this.traps.add(s);
		setTip();
	}
	public void addBlade(Spell s) {
		this.blades.add(s);
		setTip();
	}
	public void addDamageOverTime(Spell s) {
		this.damageOverTime.add(s);
	}
	public void addShield(Spell s) {
		this.shields.add(s);
		setTip();
	}
	public void doLifeDrain(Character caster, Spell s) {
		doDamage(caster,s);
		doHeal(s,caster);
	}
	
	private boolean failCast;
	private Map<String,Double> battlePlayerEffects;
	private Map<String,Double> battleTargetEffects;
	public boolean isFailCast() {
		return failCast;
	}

	public void setFailCast(boolean failCast) {
		this.failCast = failCast;
	}

	public Map<String, Double> getBattlePlayerEffects() {
		return this.battlePlayerEffects;
	}

	public void setBattlePlayerEffects(Map<String, Double> battlePlayerEffects) {
		this.battlePlayerEffects = battlePlayerEffects;
	}

	public Map<String, Double> getBattleTargetEffects() {
		return this.battleTargetEffects;
	}

	public void setBattleTargetEffects(Map<String, Double> battleTargetEffects) {
		this.battleTargetEffects = battleTargetEffects;
	}

	public double getDmg() {
		return this.dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}

	public double getHeal() {
		return this.heal;
	}

	public void setHeal(double heal) {
		this.heal = heal;
	}

	private double dmg;
	public void doDamage(Character caster,Spell s) {
		double damage = s.getDamage();
		double boost = 0.00;
		double resist = 0.00;
		double critical = 0.00;
		double criticalBlock = 0.00;
		caster.battlePlayerEffects = new HashMap<>();
		caster.battleTargetEffects = new HashMap<>();
		this.battlePlayerEffects = new HashMap<>();
		this.battleTargetEffects = new HashMap<>();
		this.dmg = 0.00;
		//	calculate accuracy first
		if(GameRules.accuracy(s.getCastChance())) {
			//	take pips
			caster.takePips(s.getPips());
			
			//	calculate caster boost 
			boost += caster.getStats().getEffect(s.getFaction().name()+"_DMG");
			boost += caster.getStats().getEffect(FactionTypes.Generic+"_DMG");
			caster.battlePlayerEffects.put("Gear Damage Boost", boost);
			damage += (boost*damage);
			//	calculate critical 
			critical += caster.getStats().getEffect(s.getFaction().name() + "_CRIT");
			if(GameRules.accuracy(criticalBlock)) {
				//	calculate counter critical
				caster.battlePlayerEffects.put("Critical!", (double) 2);
				criticalBlock += this.stats.getEffect(s.getFaction().name() +"_CRIT_BLOCK");
				if(!GameRules.accuracy(criticalBlock)) {
					damage *=2;
					this.battleTargetEffects.put("Critical blocked!", (double) 1);
					System.out.println("critical");
				}
				
			}
			boost = 0.00;
			List<Spell> removals = new ArrayList<>();
			List<Spell> activated = new ArrayList<>();
//			calculate blades
			for(Spell p : caster.getBlades()) {
				//	fix boost for each
				if(p.getFaction().equals(s.getFaction()) || p.getFaction().equals(FactionTypes.Generic)) {
					if(!activated.contains(p)) {
						damage +=((p.getBoost()/100)*damage); 
						boost+=p.getBoost();
						caster.battlePlayerEffects.put(p.getName(), p.getBoost());
						removals.add(p);
						activated.add(p);
					}
					
				}
				
			}
			caster.getBlades().removeAll(removals);
			removals.clear();
			activated.clear();
			//	calculate traps
			for(Spell p:this.getTraps()) {
				if(p.getFaction().equals(s.getFaction()) || p.getFaction().equals(FactionTypes.Generic)) {
					if(!activated.contains(p)) {
						boost+=p.getBoost();
						damage += ((p.getBoost()/100)*damage);
						this.battleTargetEffects.put(p.getName(), p.getBoost()/100);
						removals.add(p);
						activated.add(p);
					}
					
				}
			}
			this.getTraps().removeAll(removals);
			
			//	calculate shields
			removals.clear();
			activated.clear();
			for(Spell p : this.getShields()) {
				if(p.getFaction().equals(s.getFaction()) || p.getFaction().equals(FactionTypes.Generic)) {
					if(!activated.contains(p)) {
						damage *=(1-(p.getResist()/100));
						resist += p.getResist();
						this.battleTargetEffects.put(p.getName(), p.getResist()/100);
						removals.add(p);
						activated.add(p);
					}
					
				}
			}
			this.getShields().removeAll(removals);
			activated.clear();
			removals.clear();
			//	calculate resist 
			damage *=(1- this.stats.getEffect(s.getFaction().name() + "_RESIST"));
			resist += this.stats.getEffect(s.getFaction().name() + "_RESIST");
			damage *=(1- this.stats.getEffect(FactionTypes.Generic.name()+"_RESIST"));
			resist += this.stats.getEffect(FactionTypes.Generic.name() + "_RESIST");
			this.battleTargetEffects.put("Resist", resist/100);
			
			this.dmg = damage;
			this.HP-=damage;
			if(this.HP<=0) {
				this.traps.clear();
				this.shields.clear();
				this.blades.clear();
			}
			setTip();
		}
		else {
			caster.battlePlayerEffects.put("Failed to Cast", 0.00);
			this.dmg = 0;
			System.out.println("failed to cast");
		}
		
		
	}
	double heal;
	public void doHeal(Spell s, Character caster) {
		caster.battlePlayerEffects = new HashMap<>();
		if(GameRules.accuracy(s.getCastChance())) {
			caster.takePips(s.getPips());
			if(this.maxHP == this.HP)
				System.out.println("But they couldn't heal because they had max HP!");
			else{
				double boost = 1.00;
				int newHP = s.getHealth();
				boost += this.stats.getEffect(s.getFaction().name()+"_HEAL_BOOST");
				boost += this.stats.getEffect(FactionTypes.Generic + "_HEAL_BOOST");
				caster.battlePlayerEffects.put("Heal boost", boost);
				newHP *= boost;
				if(GameRules.accuracy(this.stats.getEffect(s.getFaction().name()+"_CRIT"))) {
					caster.battlePlayerEffects.put("Critical", 2.00);
					newHP*=2;
				}
				
				if(this.HP + newHP > this.maxHP)
					this.HP = maxHP;
				else
					this.HP += newHP;
				
				heal = newHP;
				System.out.println("They recieved "+newHP+" health!");
				
			}
		}
		else {
			caster.battlePlayerEffects.put("Failed to Cast", 0.00);
			System.out.println("failed to cast");
		}
		setTip();
	}
	
	public void addPip(){
		if (getPips() <= this.maxPips - 1) {
		this.pips++;
		}
		/*
		double greaterThan = this.stats.getEffect("PowerPips");
		
		//TODO Add proper Power Pips
		
		if(CombatProcess.currentGlobal == Global.POWERPLAY)
			greaterThan += greaterThan * .35;
		
		if(Math.random() <= greaterThan){
			if(this.getName() == "They")
				System.out.println("The opponent gained an extra pip!");
			else
				System.out.println("You gained an extra pip!");
			
			this.pips++;
		}*/
		this.getProfileBrief().getPips().setText(String.valueOf(this.pips) + " pips");
	}
	
	public void takePips(int amount){
		this.pips -= amount;
	}
	
	public int getHP(){
		return this.HP;
	}
	
	public int getMaxHP(){
		return this.maxHP;
	}
	
	public int getPips(){
		return this.pips;
	}
	
	public String getName(){
		return this.name;
	}
	

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public double getThreat() {
		return threat;
	}

	public void setThreat(double threat) {
		this.threat = threat;
	}

	public double getWeakness() {
		return weakness;
	}

	public void setWeakness(double weakness) {
		this.weakness = weakness;
	}

}

