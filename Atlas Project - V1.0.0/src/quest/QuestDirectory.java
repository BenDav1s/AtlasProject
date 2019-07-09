package quest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

public class QuestDirectory {
	private List<Quest> questList = new ArrayList<>();
	private static QuestDirectory instance;
	private QuestDirectory() {};
	public QuestDirectory getInstance() {
		if(instance == null) {
			loadQuests();
		}
		return instance;
	}
	public void loadQuests() {
		///	load quests from db
	}
	public void addQuest(Quest a) {
		questList.add(a);
	}
	public void removeQuest(Quest b) {
		questList.remove(b);
	}
	public Quest getQuest(String name) {
		for(Quest q : questList) {
			if(q.getName().equalsIgnoreCase(name)) {
				return q;
			}
		}
		return null;
	}
	public List<Quest> getQuestList(){
		return questList;
	}
}
