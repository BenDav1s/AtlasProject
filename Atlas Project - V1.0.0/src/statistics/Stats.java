package statistics;
import java.util.ArrayList;
import java.util.List;
public class Stats {
	private int level;
	private int maxHP;
	private int XP;
	private List<Effect> effects = new ArrayList<>();
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getXP() {
		return XP;
	}
	public void setXP(int xP) {
		XP = xP;
	}
	public List<Effect> getEffects() {
		return effects;
	}
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}

	
	
}
