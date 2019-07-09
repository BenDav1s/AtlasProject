package graphics.storyPage;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

import database.DatabaseAdapter;
import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.PageController;
import graphics.PageCreator;
import quest.Quest;
import quest.Quest.Status;

public class StoryPageController extends PageController{
	private StoryPageView view;
	private static final String BACK = "back";
	private static final String ACCEPT = "accept";
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		view = new StoryPageView(this,mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case BACK: GraphicsController.processPage(PageCreator.HOME_PAGE, PageCreator.STORY_MODE);
			break;
		case ACCEPT:
			//	determine quest name
			String name = ((JButton) e.getSource()).getName();
			Quest temp = new Quest();
			try {
				DatabaseAdapter.loadQuest(name, temp);
				temp.setQuestStatus(Status.Active);
				GameRules.activePlayer.setCurrentQuest(temp);
				DatabaseAdapter.updateQuestStatus(temp);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			GraphicsController.processPage(PageCreator.COMBAT_PAGE, PageCreator.STORY_MODE);
			break;
		}
	}

}
