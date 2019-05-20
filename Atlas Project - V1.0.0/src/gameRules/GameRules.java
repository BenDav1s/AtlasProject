package gameRules;

import character.Player;
import character.Character;
public class GameRules {
	public static final int MAX_LEVEL = 100;
	public static final int MAX_FRIENDS = 10;
	public static final int MAX_CHARACTERS = 6;
	public static Character activeUser;
	public static void updateLevel(Player p) {
		int xp = p.getStats().getXP();
		int level = p.getStats().getLevel();
		int multiplier = level * 1000;
		
		//	come back for max level set up 
		while(xp >= multiplier) {
			level++;
			System.out.println("level up " + level);
			p.getStats().setLevel(level);
			
			if(level == MAX_LEVEL) {
				System.out.println("max level");
				continue;
			}
			xp -=multiplier;
			
			multiplier = level * 1000;
		}
		p.getStats().setXP(xp);
	}
	
}
