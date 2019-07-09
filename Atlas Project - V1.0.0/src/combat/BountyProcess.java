package combat;

import java.sql.SQLException;

import character.Character;
import character.EnemyCharacter;
import database.DatabaseAdapter;
import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.combatpage.CombatPageController;

public class BountyProcess extends CombatProcess{

	public BountyProcess(CombatPageController c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	/*
	 * drawgame function overriding 
	 * draw game function in combat process
	 * loads player from bounty instead of quest 
	 */
	public void drawgame() {
		//	load enemies from db
			Character c;
			try {
				c = DatabaseAdapter.loadBoss(GameRules.activePlayer.getActiveBounty());
				EnemyAi enemyController = new EnemyAi(c);
				enemyControllers.add(enemyController);
				enemyTeam.add(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	/*
	 * overriden player win function for bounties
	 * 
	 */
	public static void playerWin() {
		//	collect mob rewards
		for(Character c : enemyTeam) {
			((EnemyCharacter) c).collectDrops();
		}
		GameRules.updateLevel(GameRules.activePlayer);
		//	update player info with drops
		try {
			DatabaseAdapter.savePlayerInfo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	return to previous page
		GraphicsController.processPage(pageController.getPreviousPage(), "");
	}
}
