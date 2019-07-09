package statistics;
import character.FactionTypes;

public class Effect {
	private Type type;
	private int hpBoost;	
	private int manaBoost;
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getHpBoost() {
		return hpBoost;
	}
	public void setHpBoost(int hpBoost) {
		this.hpBoost = hpBoost;
	}
	public int getManaBoost() {
		return manaBoost;
	}
	public void setManaBoost(int manaBoost) {
		this.manaBoost = manaBoost;
	}
	
	
}
