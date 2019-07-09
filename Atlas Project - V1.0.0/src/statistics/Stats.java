package statistics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import gameRules.GameRules;
public class Stats {
	private int maxHP;
	private List<Effect> effects = new ArrayList<>();
	private Map<String, Double> effectMap = new HashMap<>();
	public static Map<String,Double> loadNewStatsMap(){
		Map<String,Double> map = new HashMap<>();
		map.put("The_Cult_Of_The_Stars_DMG", 0.0);
		map.put("The_Ritualists_DMG", 0.0);
		map.put("The_Collective_DMG", 0.0);
		map.put("The_Children_Of_The_Mask_DMG", 0.0);
		map.put("The_Legion_DMG", 0.0);
		map.put("The_Ennead_DMG", 0.0);
		map.put("The_Called_DMG", 0.0);
		map.put("GENERIC_DMG", 0.0);
		
		map.put("The_Cult_Of_The_Stars_RESIST", 0.0);
		map.put("The_Ritualists_RESIST", 0.0);
		map.put("The_Collective_RESIST", 0.0);
		map.put("The_Children_Of_The_Mask_RESIST", 0.0);
		map.put("The_Legion_RESIST", 0.0);
		map.put("The_Ennead_RESIST", 0.0);
		map.put("The_Called_RESIST", 0.0);
		map.put("GENERIC_RESIST", 0.0);
		
		map.put("The_Cult_Of_The_Stars_CRIT", 0.0);
		map.put("The_Ritualists_CRIT", 0.0);
		map.put("The_Collective_CRIT", 0.0);
		map.put("The_Children_Of_The_Mask_CRIT", 0.0);
		map.put("The_Legion_CRIT", 0.0);
		map.put("The_Ennead_CRIT", 0.0);
		map.put("The_Called_CRIT", 0.0);
		map.put("GENERIC_CRIT", 0.0);
		
		map.put("The_Cult_Of_The_Stars_CRIT_BLOCK", 0.0);
		map.put("The_Ritualists_CRIT_BLOCK", 0.0);
		map.put("The_Collective_CRIT_BLOCK", 0.0);
		map.put("The_Children_Of_The_Mask_CRIT_BLOCK", 0.0);
		map.put("The_Legion_CRIT_BLOCK", 0.0);
		map.put("The_Ennead_CRIT_BLOCK", 0.0);
		map.put("The_Called_CRIT_BLOCK", 0.0);
		map.put("GENERIC_CRIT_BLOCK", 0.0);
		
		return map;
	}
	public void addEffect(String n, double amt) {
		effectMap.put(n,amt);
	}
	public double getEffect(String n) {
		double value = 0.00;
		if(effectMap.get(n) == null) {
			return value;
		}
		return effectMap.get(n);
	}
	public Map<String, Double> getEffectMap(){
		return effectMap;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public List<Effect> getEffects() {
		return effects;
	}
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
	public void setEffectMap(Map<String, Double> loadNewStatsMap) {
		// TODO Auto-generated method stub
		this.effectMap = loadNewStatsMap;
	}

	
	
}
