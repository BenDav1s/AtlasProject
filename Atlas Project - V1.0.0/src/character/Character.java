package character;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import combat.CombatProcess;
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
	//	current hp
	private int HP;
	private int pips;
	private int maxPips;
	private String name;
	private Deck deck;
	private List<Card> hand;
	private ProfileBriefModel profileBrief;
	private Stats stats;
	private Class charClass;
	private List<Spell> traps = new ArrayList<>();
	private List<Spell> shields= new ArrayList<>();
	private List<Spell> blades= new ArrayList<>();
	private List<Spell> damageOverTime= new ArrayList<>();
	
	
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

	public Class getCharClass() {
		return charClass;
	}

	public void setCharClass(Class charClass) {
		this.charClass = charClass;
	}

	public ProfileBriefModel getProfileBrief() {
		return profileBrief;
	}

	public void setProfileBrief(ProfileBriefModel profileBrief) {
		this.profileBrief = profileBrief;
	}

	public Character(List<Spell> spellsval, int HPval, String name,Class clas){
		this.maxHP = HPval;
		this.HP = HPval;
		this.pips = 1;
		this.maxPips = 18;
		this.name = name;
		this.deck = new Deck(spellsval);
		this.charClass = clas;
		profileBrief = new ProfileBriefModel(this,new Rectangle(100,100));
	}
	public Deck getDeck() {
		return this.deck;
	}
	public List<Card> getHand(){
		return this.hand;
	}
	public void drawNewHand() {
		int counter = 0;
		while(deck.getSize()> 0 && counter < 6) {
			
			hand.add(deck.getTop());
		}
	}
	public boolean drawCard() {
		if(deck.getSize() == 0) {
			return false;
		}
		hand.add(deck.getTop());
		return true;
	}
	public void addTrap(Spell s){
		this.traps.add(s);
	}
	public void addBlade(Spell s) {
		this.blades.add(s);
	}
	public void addDamageOverTime(Spell s) {
		this.damageOverTime.add(s);
	}
	public void addShield(Spell s) {
		this.shields.add(s);
	}
	
	public void doDamage(Character caster,Spell s) {
		double damage = s.getDamage();
		double boost = 1.00;
		double resist = 1.00;
		
		//	calculate critical first
		double critical = 0.00;
		double criticalBlock = 0.00;
		caster.takePips(s.getPips());
		//	sum critical rating
		for(Effect e : caster.getStats().getEffects()) {
			if(e.getType().equals(Type.Critical_RATING) && e.getType().getType().equals(s.getFaction())) {
				critical += e.getType().getAmt();
			}
		}
		
		boolean doCrit = CombatProcess.accuracy(critical);
		if(doCrit) {
			for(Effect e : caster.getStats().getEffects()) {
				if(e.getType().equals(Type.Critical_Block) && e.getType().getType().equals(s.getFaction())) {
					criticalBlock += e.getType().getAmt();
				}
			}
			if(CombatProcess.accuracy(criticalBlock)) {
				doCrit= false;
			}
			else {
				damage*=2;
			}
		}	
		
		//	add self boost, caster boost, trap boost
		for(Effect e : caster.getStats().getEffects()) {
			if(e.getType().equals(Type.DMG_BOOST) && e.getType().getType().equals(s.getFaction()) || e.getType().getType().equals(Class.Generic)) {
				boost+=e.getType().getAmt();
			}
		}
		for(Spell p : caster.getBlades()) {
			if(p.getFaction().equals(s.getFaction()) || p.getFaction().equals(Class.Generic)) {
				boost+=p.getBoost();
				caster.getBlades().remove(p);
			}
			
		}
		for(Spell p:this.getTraps()) {
			if(p.getFaction().equals(s.getFaction()) || p.getFaction().equals(Class.Generic)) {
				boost+=p.getBoost();
				this.getTraps().remove(p);
			}
		}
		damage *=boost;
		
		//	gear resist, shield resist
		for(Effect e : this.getStats().getEffects()) {
			if(e.getType().equals(Type.DMG_RESIST) && e.getType().getType().equals(s.getFaction()) || e.getType().getType().equals(Class.Generic)) {
				resist-=e.getType().getAmt();
			}
		}
		for(Spell p : this.getShields()) {
			if(p.getFaction().equals(s.getFaction()) || p.getFaction().equals(Class.Generic)) {
				resist-=p.getBoost();
				this.getShields().remove(p);
			}
		}
		damage *= resist;
		
		
		this.HP-=damage;
		if(this.HP <= 0) {
			System.out.println("dead");
		}
	}
	public void doHeal(Spell s, Character caster) {
		
		if(this.maxHP == this.HP)
			System.out.println("But they couldn't heal because they had max HP!");
		else{
			int newHP = s.getHealth();
			if(this.stats.getEffects().contains(Type.Heal_BOOST)) {
				double boost = 0.00;
				for(Effect e: this.stats.getEffects()) {
					if(e.getType().equals(Type.Heal_BOOST)) {
						boost+= e.getType().getAmt();
					}
				}
				newHP *= boost;
			}
			
			if(this.HP + newHP > this.maxHP)
				this.HP = maxHP;
			else
				this.HP += newHP;
			
			System.out.println("They recieved "+newHP+" health!");
			
		}
			
	}
	
	public void addPip(){
		if (getPips() <= this.maxPips - 1) {
		this.pips++;
		}
		double greaterThan = .4;
		
		//TODO Add proper Power Pips
		
		if(CombatProcess.currentGlobal == Global.POWERPLAY)
			greaterThan += greaterThan * .35;
		
		if(Math.random() <= greaterThan){
			if(this.getName() == "They")
				System.out.println("The opponent gained an extra pip!");
			else
				System.out.println("You gained an extra pip!");
			
			this.pips++;
		}
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
	
	private static int trapDamage(int amount, int damage, boolean print){
		if(print){
			System.out.println("The trap boosted the attack!");
			System.out.println("[DEBUG] Adding trap damage - " + trapDamage(amount, damage, false));
		}
		
		return (int) (amount * 100.0 / damage);
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
}

