package resources;
import character.FactionTypes;
import graphics.createAccountPage.CreateAccountPageView;
import graphics.createAccountPage.FactionLeftPanel;
import graphics.createAccountPage.FactionRightPanel;
import graphics.createCharacterPage.CharacterCreationView;
public class PanelSwapThread extends Thread{
	private int id;
	private FactionTypes faction;
	private CreateAccountPageView view;
	public PanelSwapThread(int id, FactionTypes faction2,CreateAccountPageView v) {
		this.id = id;
		this.faction = faction2;
		this.view = v;
	}
	public void run() {
		switch(id) {
		case 0:
			((Faction) this.view.getFactionSelected()).switchFaction(faction);
			break;
		case 1:
			((FactionRightPanel) this.view.getFactionCombat()).switchFaction(faction);
			 break;
		case 2: 
			((FactionLeftPanel) this.view.getFactionFlavor()).switchFaction(faction);
			break;
		
		}
	}
}

