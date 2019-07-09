package quest;

import java.util.HashMap;
import java.util.Map;

import character.Player;

public class QuestCompleteMap {
	private Map<Player,Quest> completionMap = new HashMap<>();
	private static QuestCompleteMap instance = null;
	private QuestCompleteMap() {};
	public static QuestCompleteMap getInstance() {
		if(instance == null) {
			instance = new QuestCompleteMap();
			LoadCompletionMap();
		}
		return instance;
	}
	public static void LoadCompletionMap() {
		
	}
	protected Map<Player,Quest> getCompletionMap(){
		return completionMap;
	}
}
