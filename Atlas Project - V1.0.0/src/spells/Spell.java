package spells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import character.Class;
public class Spell{
	
	private Class faction;
	private SpellType type;
	private int pips;
	private String name;
	private double castChance;
	private int damage;
	private int health;
	private double resist;
	private double boost;
	private List<Class> impactList;
	public void makeAttackSpell(Class f,String n, double castChance, int d) {
		this.faction = f;
		this.type = SpellType.Attack;
		this.name = n;
		this.castChance = castChance;
		this.damage = d;
	}
	public void makeShieldSpell(Class f,String n, double resist,List<Class> types) {
		this.impactList = types;
		this.faction = f;
		this.type = SpellType.Shield;
		this.name = n;
		this.castChance = 100;
		this.resist= resist;
	}
	public void makeBladeSpell(Class f,String n, double boost,List<Class> types) {
		this.impactList = types;
		this.faction = f;
		this.type = SpellType.Blade;
		this.name = n;
		this.castChance = 100;
		this.boost= boost;
	}
	public void makeTrapSpell(Class f,String n, double boost,List<Class> types) {
		this.impactList = types;
		this.faction = f;
		this.type = SpellType.Trap;
		this.name = n;
		this.castChance = 100;
		this.boost= boost;
	}
	public void makeHealSpell(Class f,String n, double castChance, int amt) {
		this.faction = f;
		this.type = SpellType.Shield;
		this.name = n;
		this.castChance = castChance;
		this.health= amt;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public double getResist() {
		return resist;
	}
	public void setResist(double resist) {
		this.resist = resist;
	}
	public double getBoost() {
		return boost;
	}
	public void setBoost(double boost) {
		this.boost = boost;
	}
	public Class getFaction() {
		return faction;
	}
	public void setFaction(Class faction) {
		this.faction = faction;
	}
	public SpellType getType() {
		return type;
	}
	public void setType(SpellType type) {
		this.type = type;
	}
	public int getPips() {
		return pips;
	}
	public void setPips(int pips) {
		this.pips = pips;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCastChance() {
		return castChance;
	}
	public void setCastChance(double castChance) {
		this.castChance = castChance;
	}
	
	
	//Register spells
	/*
	public static Spell[] spellClasses = new Spell[]{
		new SpellDarkFairy(),
		new SpellDeathTrap(),
		new SpellScarab(),
		new SpellPixie(),
		new SpellSpiritTrap(),
		new SpellElementalTrap(),
		new SpellSandstorm(),
		new SpellLocustStorm(),
		new SpellPowerPlay(),
		new SpellSpectralBlast(),
		new SpellHydra(),
		new SpellReshuffle(),
		new SpellUnbalance(),
		new SpellSpiritBlade(),
		new SpellElementalBlade(),
		new SpellElementalShield(),
		new SpellSpiritShield(),
		new SpellGhoul(),
		new SpellDreamShield(),
		new SpellBanshee(),
		new SpellVampire(),
		new SpellSkeletalPirate(),
		new SpellFeint(),
		new SpellDoomAndGloom(),
		new SpellWraith(),
		new SpellStrangle(),
		new SpellDeathPrism(),
		new SpellSanctuary(),
		new SpellHealAura(),
		new SpellSunbird(),
		new SpellKraken()
	};
	
	public static String getCategory(Spell s){
		return SpellType.getTypeName(s.type())+" Spells";
	}
	
	public static Spell[] getAllInCategory(String s){
		List<Spell> ret = new ArrayList<Spell>();
		
		for(Spell sp : spellClasses){
			if(getCategory(sp) == s)
				ret.add(sp);
		}
		
		return ret.toArray(new Spell[0]);
	}
	
	public static boolean accuracy(double d){
		return Math.random() <= d;
	}
	
	public static Spell randomSpell(){
		Collections.shuffle(Arrays.asList(spellClasses));
		return spellClasses[0];	
	}
	*/
}
