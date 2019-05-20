package statistics;

import character.Class;

public enum Type {
	DMG_BOOST(Class.Generic,0.0),
	DMG_RESIST(Class.Generic,0.0),
	Critical_RATING(Class.Generic,0.0),
	Critical_Block(Class.Generic,0.0),
	Heal_BOOST(Class.Generic,0.0);
	
	private Class type;
	private double amt;
	private Type(Class t, double amt) {
		this.type = t;
		this.amt = amt;
	}
	public Class getType() {
		return this.type;
	}
	public void setType(Class t) {
		this.type = t;
	}
	public double getAmt() {
		return this.amt;
	}
	public void setAmt(double a) {
		this.amt = a;
	}
}
