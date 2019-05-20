package resources;
import character.Class;
import graphics.createAccountPage.CreateAccountPageView;
import graphics.createAccountPage.FactionLeftPanel;
import graphics.createAccountPage.FactionRightPanel;
public class PanelSwapThread extends Thread{
	private int id;
	private Class faction;
	private CreateAccountPageView view;
	public PanelSwapThread(int id, Class c,CreateAccountPageView v) {
		this.id = id;
		this.faction = c;
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

