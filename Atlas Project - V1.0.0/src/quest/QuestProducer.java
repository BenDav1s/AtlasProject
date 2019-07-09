package quest;

import character.Player;
import graphics.storyPage.StoryPageController;

public class QuestProducer {
	private QuestDirectory questList;
	private static QuestProducer instance;
	private QuestProducer() {};
	public static QuestProducer getInstance() {
		if(instance == null) {
			instance = new QuestProducer();
		}
		return instance;
	}
	//	gets the next quest for the player to complete
	public Quest getNextQuest(Player p,StoryPageController c) {
		Quest lastcompleted = QuestCompleteMap.getInstance().getCompletionMap().get(p);
		if(lastcompleted == null) {
			lastcompleted = new Quest("firstQuest",c);
			
			return lastcompleted;
		}
		int index = questList.getInstance().getQuestList().indexOf(lastcompleted)+1;
		return questList.getInstance().getQuestList().get(index);
	}
	protected QuestDirectory getQuestList() {
		return questList;
	}
	protected void setQuestList(QuestDirectory questList) {
		this.questList = questList;
	}
	
}
