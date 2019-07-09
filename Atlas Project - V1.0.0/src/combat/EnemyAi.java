package combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import character.Character;
import character.EnemyCharacter;
import gameRules.GameRules;
import spells.Card;
import spells.Spell;
import spells.SpellType;

public class EnemyAi {
	private Character self;
	private List<Spell> moveSet;
	private List<Character> teammates = CombatProcess.getenemyTeam();
	private List<Character> enemyTeam = CombatProcess.getuserTeam();
	private Character target;
	private Character maxThreat;
	private Character maxWeakness;
	private boolean pass = false;
	
	
	public Character getSelf() {
		return self;
	}
	public void setSelf(Character self) {
		this.self = self;
	}
	public List<Spell> getMoveSet() {
		return moveSet;
	}
	public void setMoveSet(List<Spell> moveSet) {
		this.moveSet = moveSet;
	}
	public List<Character> getTeammates() {
		return teammates;
	}
	public void setTeammates(List<Character> teammates) {
		this.teammates = teammates;
	}
	public List<Character> getEnemyTeam() {
		return enemyTeam;
	}
	public void setEnemyTeam(List<Character> enemyTeam) {
		this.enemyTeam = enemyTeam;
	}
	public Character getTarget() {
		return target;
	}
	public void setTarget(Character target) {
		this.target = target;
	}
	public Character getMaxThreat() {
		return maxThreat;
	}
	public void setMaxThreat(Character maxThreat) {
		this.maxThreat = maxThreat;
	}
	public Character getMaxWeakness() {
		return maxWeakness;
	}
	public void setMaxWeakness(Character maxWeakness) {
		this.maxWeakness = maxWeakness;
	}
	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	public EnemyAi(Character self) {
		this.self = self;
	}
	public Spell makeMove() {
		calculateThreat();
		assignMoveset();
		if(target == null) {
			if(GameRules.accuracy(Math.random())) {
				target = maxThreat;
			}
			else {
				target = maxWeakness;
			}
		}
		
		if(pass) {
			target = self;
			return null;
		}
		else {
			if(moveSet.get(0).getType().name().equalsIgnoreCase("Heal") ||moveSet.get(0).getType().name().equalsIgnoreCase("Blade") 
					|| moveSet.get(0).getType().name().equalsIgnoreCase("Shield") || moveSet.get(0).getType().name().equalsIgnoreCase("Heal_All") ||moveSet.get(0).getType().name().equalsIgnoreCase("Blade_All") 
					|| moveSet.get(0).getType().name().equalsIgnoreCase("Shield_All")) {
				target = self;
			}
			self.getHand().remove(moveSet.get(0));
			if(moveSet.get(0) == null) {
				target = self;
			}
			return moveSet.remove(0);
		}
	}
	public void calculateThreat() {
		for(Character c : enemyTeam) {
			//	calculate outgoing damage potential
			//	sum of blades on enemy, traps on self, shields on self * potential damage
			double outgoing = 0.00;
			
			for(Spell s : c.getBlades()) {
				outgoing += s.getBoost();
			}
			for(Spell s : self.getTraps()) {
				outgoing += s.getBoost();
			}
			for(Spell s : self.getShields()) {
				outgoing -= s.getResist();
			}
			outgoing *= (100*c.getPips());
			
			//	calculate incoming damage potential
			// 	sum of blades on self, traps on enemy and shields on enmy * potential damage
			double weakness = 0.00;
			for(Spell s : self.getBlades()) {
				weakness += s.getBoost();
			}
			for(Spell s : c.getTraps()) {
				weakness += s.getBoost();
			}
			for(Spell s : c.getShields()) {
				weakness -= s.getResist();
			}
			weakness *= self.getPips();
			c.setThreat(self.getHP()-outgoing);
			c.setWeakness(c.getHP() -weakness);
		}
		enemyTeam.sort(Comparator.comparing(Character::getThreat).reversed());
		Character maxThreat = enemyTeam.get(0);
		enemyTeam.sort(Comparator.comparing(Character::getWeakness).reversed());
		Character maxWeakness = enemyTeam.get(0);
		
		if(maxThreat.getThreat() > maxWeakness.getWeakness()) {
			System.out.println("dangerous attacker");
			target= maxThreat;
		}
		else if (maxThreat.getThreat() < maxWeakness.getWeakness()) {
			System.out.println("open to attack");
			target = maxWeakness;
		}
		else {
			System.out.println("no idea what to do ");
			target = null;
		}
		this.maxThreat = maxThreat;
		this.maxWeakness = maxWeakness;
	}
	//	cost function .. look for min
	public double generateCost(Spell s ) {
		//	score = 
		double score = 0;
		double  value = 0;
		int weight = 0;
		switch(s.getType().name()) {
		case "Attack" : 
			value = s.getDamage();
			break;
		case "Attack_All" : 
			value = s.getDamage();
			value *= enemyTeam.size();
			break;
		case "Heal" : 
			value = s.getHealth();
			break ;
		case  "Heal_ALL" : 
			value = s.getHealth();
			value *= teammates.size();
			break;
		case "Shield" : 
			value =  s.getResist();
			value *= maxThreat.getThreat();
			break;
		case "Shield_ALL" : 
			value =  s.getResist();
			value *= maxThreat.getThreat();
			value *= teammates.size();
			break;
		case "Blade" : 
			value =  s.getBoost();
			value *= maxWeakness.getWeakness();
			break; 
		case "Blade_ALL" : 
			value = s.getBoost();
			value *= maxWeakness.getWeakness();
			value *= teammates.size();
			break;
		case "Trap" : 
			value =  s.getBoost();
			value *= maxThreat.getThreat();
			break ;
		case "Trap_ALL": 
			value = s.getBoost();
			value *= enemyTeam.size();
			break;
			
		}
		weight = s.getPips();
		score = value;
		if(weight > self.getPips()) {
			score -= (weight - self.getPips()) * 50;
		}
		return score;
	}
	public void mutate(List<Spell> subset, List<Spell> mutations) {
		double mutation = 0.3;
		if(target == null) {
			mutation = 1.00;
		}
		for(Spell s: mutations) {
			if(GameRules.accuracy(mutation)) {
				subset.add(s);
			}
		}
		
	}
	public void assignMoveset() {
		//	cards in hand = answer candidates aka chromosomes
		Map<Spell, Double> chromosomeMap = new HashMap<>();
		List<Spell> population = new ArrayList<>();
		//	generate score for each card
		//	50 point penalty for being over weight
		for(Card c: self.getHand()) {
			Spell s = c.getSpell();
			chromosomeMap.put(s, generateCost(s));
			population.add(s);
		}
		
		//	sort population based on score in descending order
		Map<Spell,Double> sortedChromosomeMap = new HashMap<>(); 
		chromosomeMap.entrySet()
					.stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.forEachOrdered(x -> sortedChromosomeMap.put(x.getKey(), x.getValue()));
		
		
		//	kill any who cant be cast right now 
		List<Spell> cantBeCast = new ArrayList<>();
		List<Spell> subSet = new ArrayList<>();
		if(moveSet != null) {
			subSet.addAll(moveSet);
		}
		for(Spell s : chromosomeMap.keySet()) {
			if(s.getPips() > self.getPips()) {
				cantBeCast.add(s);
			}
			else {
				subSet.add(s);
			}
		}
		//	sort cards that could be played by pips cost
		subSet.sort(Comparator.comparing(Spell::getPips));
		//	mate aka filter out based on target then mutate
		List<Spell> mutations = new ArrayList<>();
		if(target != null) {
			if(target == maxThreat) {
				//	pick defense spells
				for(Spell s : subSet) {
					if(s.getType().equals(SpellType.Attack) || s.getType().equals(SpellType.Attack_All)
							|| s.getType().equals(SpellType.Blade) || s.getType().equals(SpellType.Blade_ALL) ||
							s.getType().equals(SpellType.Trap) || s.getType().equals(SpellType.Trap_ALL)) {
						mutations.add(s);
					}
				}
			}
			else if (target == maxWeakness) {
				// pick attack spells
				for(Spell s : subSet) {
					if(s.getType().equals(SpellType.Heal) || s.getType().equals(SpellType.Heal_ALL)
							|| s.getType().equals(SpellType.Shield) || s.getType().equals(SpellType.Shield_ALL)) {
						mutations.add(s);
					}
				}
			}
		}
		for(Spell s : mutations) {
			subSet.remove(s);
		}
		//	add spells back in for mutation
		mutate(subSet,mutations);
		
		if(subSet.size() == 0) {
			pass = true;
		}
		else {
			pass = false;
		}
		//	card to play is first in subset
		this.moveSet = subSet;
		//	fill
		subSet.addAll(cantBeCast);
		
	}
}
