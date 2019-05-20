package spells;
public enum Global {
	NORMAL, POWERPLAY, DOOMANDGLOOM, SANCTUARY, HEALAURA;
	
	public static String getName(Global name){
		switch(name){
			case POWERPLAY:
				return "Power Play";
			case DOOMANDGLOOM:
				return "Doom and Gloom";
			case SANCTUARY:
				return "Sanctuary";
			case HEALAURA:
				return "Heal Aura";
			default:
				return "None";
		}
	}
}
