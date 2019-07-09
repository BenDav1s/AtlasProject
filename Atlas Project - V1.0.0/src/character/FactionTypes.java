package character;

import java.util.ArrayList;
import java.util.List;

public enum FactionTypes {
	The_Cult_Of_The_Stars, //	future tech 85	life drain / support
	The_Ritualists,	//	demonics 85	solo rouge
	The_Collective,	//	victorian era 75	healer support
	The_Children_Of_The_Mask,	//	mayan 70 all out damage
	The_Legion,	//	greco - roman 80	tank
	The_Ennead,	//	egpytians 80	healer
	The_Called,	//	cold stuff 75	damage support
	Generic; 
	public static FactionTypes loadClass(String id) {
		if(id==null) {
			return FactionTypes.Generic;
		}
		if(id.equalsIgnoreCase("THE_CULT_OF_THE_STARS")) return FactionTypes.The_Cult_Of_The_Stars;
		if(id.equalsIgnoreCase("THE_RITUALISTS")) return FactionTypes.The_Ritualists;
		if(id.equalsIgnoreCase("The_Collective")) return FactionTypes.The_Collective;
		if(id.equalsIgnoreCase("The_Children_Of_The_Mask")) return FactionTypes.The_Children_Of_The_Mask;
		
		if(id.equalsIgnoreCase("The_Legion")) return FactionTypes.The_Legion;
		if(id.equalsIgnoreCase("The_Ennead")) return FactionTypes.The_Ennead;
		if(id.equalsIgnoreCase("The_Called")) return FactionTypes.The_Called;
		return FactionTypes.Generic;
	}
	public static FactionTypes loadClass(int id) {
		switch(id) {
		case 0: return FactionTypes.The_Called;
		case 1: return FactionTypes.The_Children_Of_The_Mask;
		case 2: return FactionTypes.The_Collective;
		case 3: return FactionTypes.The_Cult_Of_The_Stars;
		case 4: return FactionTypes.The_Ennead;
		case 5: return FactionTypes.The_Legion;
		case 6: return FactionTypes.The_Ritualists;
		}
		return null;
	}
	public static String loadString(int id) {
		switch(id) {
		case 0: return "The_Called";
		case 1: return "The_Children_Of_The_Mask";
		case 2: return "The_Collective";
		case 3: return "The_Cult_Of_The_Stars";
		case 4: return "The_Ennead";
		case 5: return "The_Legion";
		case 6: return "The_Ritualists";
		}
		return null;
	}
	public static List<String> loadStarterSpells(String id){
		List<String> starterSpells = new ArrayList<>();
		if(id.equalsIgnoreCase("THE_CULT_OF_THE_STARS")) {
			starterSpells.add("The_Cult_Of_The_Stars_Melee");
			starterSpells.add("The_Cult_Of_The_Stars_Shield");
			starterSpells.add("The_Cult_Of_The_Stars_Trap");
			starterSpells.add("The_Cult_Of_The_Stars_Blade");
			starterSpells.add("Generic_Shield");
		}
		if(id.equalsIgnoreCase("THE_RITUALISTS")) {
			starterSpells.add("The_Ritualists_Melee");
			starterSpells.add("The_Ritualists_Shield");
			starterSpells.add("The_Ritualists_Trap");
			starterSpells.add("The_Ritualists_Blade");
			starterSpells.add("Generic_Shield");
		}
		if(id.equalsIgnoreCase("The_Collective")) {
			starterSpells.add("The_Collective_Melee");
			starterSpells.add("The_Collective_Shield");
			starterSpells.add("The_Collective_Trap");
			starterSpells.add("The_Collective_Blade");
			starterSpells.add("Generic_Shield");
		}
		if(id.equalsIgnoreCase("The_Children_Of_The_Mask")) {
			starterSpells.add("The_Children_Of_The_Mask_Melee");
			starterSpells.add("The_Children_Of_The_Mask_Shield");
			starterSpells.add("The_Children_Of_The_Mask_Trap");
			starterSpells.add("The_Children_Of_The_Mask_Blade");
			starterSpells.add("Generic_Shield");
		}
		if(id.equalsIgnoreCase("The_Legion")) {
			starterSpells.add("The_Legion_Melee");
			starterSpells.add("The_Legion_Shield");
			starterSpells.add("The_Legion_Trap");
			starterSpells.add("The_Legion_Blade");
			starterSpells.add("Generic_Shield");
		}
		if(id.equalsIgnoreCase("The_Ennead")) {
			starterSpells.add("The_Ennead_Melee");
			starterSpells.add("The_Ennead_Shield");
			starterSpells.add("The_Ennead_Trap");
			starterSpells.add("The_Ennead_Blade");
			starterSpells.add("Generic_Shield");
		}
		if(id.equalsIgnoreCase("The_Called")) {
			starterSpells.add("The_Called_Melee");
			starterSpells.add("The_Called_Shield");
			starterSpells.add("The_Called_Trap");
			starterSpells.add("The_Called_Blade");
			starterSpells.add("Generic_Shield");
		}
		starterSpells.add("Raider_Initiate");
		starterSpells.add("Unborn_Initiate");
		starterSpells.add("Union_Initiate");
		starterSpells.add("Haunted_Initiate");
		starterSpells.add("Trained_Initiate");
		starterSpells.add("Fallen_Initiate");
		starterSpells.add("Cult_Initiate");
		return starterSpells;
	}
}
