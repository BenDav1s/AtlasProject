package statistics;

import character.FactionTypes;

public enum Type {
	DMG_BOOST(FactionTypes.Generic,0.0),
	DMG_RESIST(FactionTypes.Generic,0.0),
	Critical_RATING(FactionTypes.Generic,0.0),
	Critical_Block(FactionTypes.Generic,0.0),
	Heal_BOOST(FactionTypes.Generic,0.0),
	PIP_CHANCE(FactionTypes.Generic,0.0);
	private FactionTypes type;
	private double amt;
	private Type(FactionTypes t, double amt) {
		this.type = t;
		this.amt = amt;
	}
	public FactionTypes getType() {
		return this.type;
	}
	public void setType(FactionTypes t) {
		this.type = t;
	}
	public double getAmt() {
		return this.amt;
	}
	public void setAmt(double a) {
		this.amt = a;
	}
}
