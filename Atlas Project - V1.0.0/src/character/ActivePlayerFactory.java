package character;

import gameRules.GameRules;

public class ActivePlayerFactory extends PlayerAbstractFactory{

	@Override
	protected Player makePlayer() {
		// TODO Auto-generated method stub
		return GameRules.activePlayer;
	}

}
