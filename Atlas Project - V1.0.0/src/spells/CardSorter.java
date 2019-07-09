package spells;

import java.util.Comparator;

public class CardSorter implements Comparator<Card>{
	@Override
	public int compare(Card o1, Card o2) {
		// TODO Auto-generated method stub
				Spell a = ((Card)o1).getSpell();
				Spell b = ((Card)o1).getSpell();
				int score = 0;
				score += a.getFaction().name().compareTo(b.getFaction().name());
				score +=a.getName().compareTo(b.getName());
				score += a.getPips() - b.getPips();
				return score;
	}

}
