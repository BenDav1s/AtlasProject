package spells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import character.FactionTypes;
public class Spell{
	
	private FactionTypes faction;
	private SpellType type;
	private int pips;
	private String name;
	private double castChance;
	private int damage;
	private int health;
	private double resist;
	private double boost;
	private Set<FactionTypes> impactList;
	//	used for creating spells from names pulled
	public Spell(String name2) {
		// TODO Auto-generated constructor stub
		this.name = name2;
	}
	public Spell() {
		// TODO Auto-generated constructor stub
	}
	public void makeAttackSpell(FactionTypes f,String n, double castChance, int d) {
		this.faction = f;
		this.type = SpellType.Attack;
		this.name = n;
		this.castChance = castChance*100;
		this.damage = d;
	}
	public void makeShieldSpell(FactionTypes f,String n, double resist,Set<FactionTypes> types) {
		this.setImpactList(types);
		this.faction = f;
		this.type = SpellType.Shield;
		this.name = n;
		this.castChance = 100;
		this.resist= resist*100.0;
	}
	public void makeBladeSpell(FactionTypes f,String n, double boost,Set<FactionTypes> types) {
		this.setImpactList(types);
		this.faction = f;
		this.type = SpellType.Blade;
		this.name = n;
		this.castChance = 100;
		this.boost= boost*100.0;
	}
	public void makeTrapSpell(FactionTypes f,String n, double boost,Set<FactionTypes> types) {
		this.setImpactList(types);
		this.faction = f;
		this.type = SpellType.Trap;
		this.name = n;
		this.castChance = 100;
		this.boost= boost*100.0;
	}
	public void makeHealSpell(FactionTypes f,String n, double castChance, int amt) {
		this.faction = f;
		this.type = SpellType.Heal;
		this.name = n;
		this.castChance = castChance*100;
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
	public FactionTypes getFaction() {
		return faction;
	}
	public void setFaction(FactionTypes faction) {
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
	public Set<FactionTypes> getImpactList() {
		return impactList;
	}
	public void setImpactList(Set<FactionTypes> impactList) {
		this.impactList = impactList;
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
