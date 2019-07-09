package gameRules;

import character.Player;
import character.User;
import database.DatabaseAdapter;

import java.sql.SQLException;

import character.Character;
public class GameRules {
	public static final int MAX_LEVEL = 132;
	public static final int MAX_FRIENDS = 10;
	public static final int MAX_CHARACTERS = 6;
	public static Player activePlayer;
	public static Character activeCharacter;
	public static User activeUser;
	public static void updateLevel(Player p) {
		int xp = p.getXp();
		int level = p.getLevel();
		int multiplier = level * 1000;
		
		//	come back for max level set up 
		while(xp >= multiplier) {
			level++;
			increaseHP();
			try {
				DatabaseAdapter.addNewSpell(level);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("level up " + level);
			p.setLevel(level);
			
			if(level == MAX_LEVEL) {
				System.out.println("max level");
				continue;
			}
			xp -=multiplier;
			
			multiplier = level * 1000;
		}
		p.setXp(xp);
		p.setLevel(level);
	}
	public static void increaseHP() {
		
	}
	public static boolean accuracy(double d) {
		return Math.random() <= d;
	}
	public static String generateUserHash(String username) {
		// TODO Auto-generated method stub
		return Hasher.hashString(username);
	}
	public static String generatePlayerHash(String name) {
		// TODO Auto-generated method stub
		return Hasher.hashString(name);
	}
	
}
