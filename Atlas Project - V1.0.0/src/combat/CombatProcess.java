package combat;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import character.FactionTypes;
import database.DatabaseAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import spells.Card;
import spells.Deck;
import spells.Global;
import spells.Spell;
import spells.SpellType;
import statistics.Effect;
import statistics.Stats;
import statistics.Type;
import character.Character;
import character.EnemyCharacter;
import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.PageCreator;
import graphics.combatpage.CardPopup;
import graphics.combatpage.CombatPageController;
import graphics.combatpage.Pile;
import playerGraphics.ProfileBriefModel;
import quest.Quest.Status;
public class CombatProcess {
	protected static List<Character> userTeam = new ArrayList<Character>();
	protected static List<Character> enemyTeam = new ArrayList<Character>();
	protected static List<EnemyAi> enemyControllers = new ArrayList<EnemyAi>();
	protected static CombatPageController pageController;
	private static boolean active;
	//	use to wait to check if game is over after playing cards 
	public  static boolean waiting = true;
	public static List<Character> getuserTeam(){
		return userTeam;
	}
	public static List<Character> getenemyTeam(){
		return enemyTeam;
	}
	public static Global currentGlobal = Global.NORMAL;
	
	public CombatProcess(CombatPageController c) {
		this.pageController = c;
	}
	/*
	 * init game
	 * loads user deck and adds user to user team
	 * loads enemies
	 * shuffles deck
	 * determines who goes first
	 */
	public void initgame() {
		//	load user info
		List<Spell> spells = new ArrayList<>();
		for(Card s : GameRules.activePlayer.getDeck().getCards()) {
			spells.add(s.getSpell());
		}
		GameRules.activeCharacter = new Character(spells,GameRules.activePlayer.getMaxHP(),GameRules.activePlayer.getName(),GameRules.activePlayer.getFaction());;
		//GameRules.activeCharacter.setDeck(new Deck(spells));
		GameRules.activeCharacter.setStats(GameRules.activePlayer.getStats());
		userTeam.clear();
		userTeam.add(GameRules.activeCharacter);
		
		//	load enemies
		drawgame();
		
		//	shuffle decks
		for(Character ch : userTeam) {
			ch.getDeck().shuffle();
		}
		for(Character che : enemyTeam) {
			che.getDeck().shuffle();
		}
		
		//	determine who goes first
		active = GameRules.accuracy(Math.random());
		
		

	}
	/*
	 * default draw game function
	 * loads enemies based on quest 
	 */
	public void drawgame() {

		// 	set up player and enmy stats
		//	load enemies from db
		enemyTeam.clear();
		enemyControllers.clear();
		for(String s : GameRules.activePlayer.getCurrentQuest().getEnemies()) {
			Character c;
			try {
				c = DatabaseAdapter.loadBoss(s);
				List<Card> addList = new ArrayList<>();
				for(Card card: c.getDeck().getCards()) {
					addList.add(card);
					addList.add(card);
					addList.add(card);
					addList.add(card);
					addList.add(card);
				}
				c.setHP(c.getMaxHP());
				c.getDeck().setCards(addList);
				c.getDeck().setReserveList(addList);
				EnemyAi enemyController = new EnemyAi(c);
				enemyControllers.add(enemyController);
				enemyTeam.add(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void waitFor() {
		System.out.println("timing turn");
	}
	/*
	 * playerWin
	 * updates quest status to completed
	 * collect rewards
	 * loads bounties from bosses beaten
	 * saves player info
	 * returns to previous page
	 */
	public static void playerWin() {
		System.out.println("you win");
		
		//	set status as completed
		GameRules.activePlayer.getCurrentQuest().setQuestStatus(Status.Completed);
		//	collect quest rewards
		GameRules.activePlayer.getCurrentQuest().completeQuest(GameRules.activePlayer);
		//	collect mob rewards
		for(Character c : enemyTeam) {
			((EnemyCharacter) c).collectDrops();
		}
		GameRules.updateLevel(GameRules.activePlayer);
		try {
			//	update status as completed
			DatabaseAdapter.updateQuestStatus(GameRules.activePlayer.getCurrentQuest());
			
			//	remove current quest from known list
			GameRules.activePlayer.getKnownQuests().remove(GameRules.activePlayer.getCurrentQuest().getTitle());
			
			GameRules.activePlayer.getActiveQuests().remove(GameRules.activePlayer.getCurrentQuest().getTitle());
			
			//	load next quest
			DatabaseAdapter.loadNextQuest();
			
			//	load bounty boss if possible
			DatabaseAdapter.makeBounty();
			
			//	update player info with drops, quest status, new quest, new bounties
			DatabaseAdapter.savePlayerInfo();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	restore deck
		
		//	return to previous page
		GraphicsController.processPage(pageController.getPreviousPage(), "");
	}
	public static void enemyWin() {
		System.out.println("you lose");
		for(Character c : enemyTeam) {
			c.setHP(c.getMaxHP());
			c.getProfileBrief().getHealth().setText(c.getMaxHP()+"/"+c.getMaxHP());
			c.setPips(1);
			c.getProfileBrief().getPips().setText(1 + " pips");
		}
		GameRules.activePlayer.setCurrentQuest(null);
		if(pageController.getPreviousPage() == PageCreator.STORY_MODE) {
			GraphicsController.processPage(PageCreator.STORY_MODE, "");
		}
		else if(pageController.getPreviousPage() == PageCreator.Bounty_Page){
			GraphicsController.processPage(PageCreator.Bounty_Page, "");
		}
		
	}
	/*
	 * checkEnd
	 * determines if the game is over
	 * if game is not over, adds pips and draws cards
	 */
	public static void checkEnd() {
		boolean gameOver = false;
		if(userTeam.stream().allMatch(t-> t.getHP()<=0)) {
			enemyWin();
			gameOver= true;
		}
		if(enemyTeam.stream().allMatch(t->t.getHP()<=0)) {
			System.out.println("You win");
			playerWin();
			gameOver = true;
		}
		if(!gameOver) {
			for(Character c :userTeam) {
				if(c.equals(GameRules.activeCharacter)) {
					if(GameRules.activeCharacter.getDeck().getCards().size() >0 && pageController.getView().getColumns().getComponentCount()+1 <7) {
						Card temp = GameRules.activeCharacter.getDeck().drawCard();
						temp.addMouseListener(pageController);
						temp.addMouseMotionListener(pageController);
						temp.show();
						Pile p = new Pile(temp);
						List<Pile> tempPiles = new ArrayList<>();
						for(Component q: pageController.getView().getColumns().getComponents()) {
								tempPiles.add((Pile) q);	
						}
						pageController.getView().getColumns().removeAll();
						for(Pile d : tempPiles) {
							pageController.getView().getColumns().add(d);
						}
						pageController.getView().getColumns().add(p);
						
						//this.pageController.getView().handPiles.add(p);
					}
				}
				else {
					if(c.getHP() > 0) {
						if(c.getDeck().getCards().size() > 0 && c.getHand().size()+1 < 7) {
							c.drawCard();
						}
					}
				}
				if(c.getHP() > 0) {
					c.addPip();
				}
				c.getProfileBrief().repaint();
			}
			for(Character c: enemyTeam) {
				if(c.getHP() > 0) {
					if(c.getDeck().getCards().size() > 0 && c.getHand().size()+1 < 7) {
						c.drawCard();
					}
					else if(c.getHand().size()==5) {
						c.getDeck().setCards(c.getDeck().getReserveList());
						c.drawCard();
					}
					c.addPip();
				}
				
				c.getProfileBrief().repaint();
			}
			pageController.getView().getFrame().repaint();
		}
	}
	/*
	 * combat phase
	 * player combat function
	 */
	/*
	public void combatphase(Pile a, boolean d) {
		
		Spell s = a.getBase().getSpell();
		int count = 0;
		if(active) {
			if(s.getType().equals(SpellType.Attack_All) || s.getType().equals(SpellType.Trap_ALL)) {
				for(Character c : this.enemyTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeCharacter, a,this.pageController,count++);
				}
			}
			if(s.getType().equals(SpellType.Blade_ALL) || s.getType().equals(SpellType.Heal_ALL) || 
					s.getType().equals(SpellType.Shield_ALL)) {
				for(Character c: this.userTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeCharacter, a,this.pageController,count++);
				}
			}
		}
		else {
			if(s.getType().equals(SpellType.Attack_All) || s.getType().equals(SpellType.Trap_ALL)) {
				for(Character c : this.enemyTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeCharacter, a,this.pageController,count++);
				}
			}
			if(s.getType().equals(SpellType.Blade_ALL) || s.getType().equals(SpellType.Heal_ALL) || 
					s.getType().equals(SpellType.Shield_ALL)) {
				for(Character c: this.userTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeCharacter, a,this.pageController,count++);
				}
			}
		}
		checkEnd();
	}*/
	/*
	 * combatphase
	 * enemy ai combat function
	 */
	public void combatphase(Component a, Pile b) {
		//	friendly goes first
//		set back panel
		//	prevents b from being changed during run time
		CardPopup.setPanel(this.pageController.getView().getFrame().getContentPane());
		int count = 0;
		int finalCount = 0;
		CardPopup.setFinalCount(finalCount);
		///	if player goes first
		if(active) {
			int temp  =playerTurn(a,b,count,finalCount);
			System.out.println("player gives " + temp);
			finalCount+=enemyTurn(a,b,temp,temp);
			System.out.println("enemy gives " + finalCount);
		}else {
			int temp =enemyTurn(a,b,count,finalCount);
			System.out.println("enemy gives " + temp);
			finalCount+=playerTurn(a,b,temp,temp);
			System.out.println("player gives " + finalCount);
		}
		
		System.out.println(finalCount);
		CardPopup.setFinalCount(finalCount);
		//	wait until done displaying cards to update info
		//while(CombatProcess.getWaiting()==true);
		//CombatProcess.setWaiting(true);
		
	}
	public int enemyTurn(Component a , Pile b,int count,int finalCount) {
		int enemyCounter = 0;
		for(EnemyAi ai : enemyControllers) {
			//	if alive 
			if(ai.getSelf().getHP() >0) {
				Spell s = ai.makeMove();
				//	if turn made
				if(s != null) {
					Pile d = new Pile(new Card(s));
					//	teammate targets 
					for(Component c : this.pageController.getView().getTopColumns().getComponents()) {
						if(((ProfileBriefModel) c).getName().equalsIgnoreCase(ai.getTarget().getName())) {
							SpellType type2 = d.getBase().getSpell().getType();
							System.out.println(ai.getSelf().getName() + " targets " + ai.getTarget().getName() +" with " + d.getBase().getSpell().getName());
							if(type2.equals(SpellType.Attack_All) || type2.equals(SpellType.Trap_ALL)) {
								for(Character cha : userTeam) {
									System.out.println("e");
									enemyCounter++;
									cha.getProfileBrief().updatePlayer(ai.getSelf(), d, this.pageController, count++);
									
								}
							}
							else if(type2.equals(SpellType.Heal_ALL) || type2.equals(SpellType.Blade_ALL) || type2.equals(SpellType.Shield_ALL)) {
								for(Character cha : enemyTeam) {
									System.out.println("f");
									enemyCounter++;
									cha.getProfileBrief().updatePlayer(ai.getSelf(), d, this.pageController, count++);
									
								}
							}
							else {
								System.out.println("g");
								enemyCounter++;
								((ProfileBriefModel) c).updatePlayer(ai.getSelf(),d,this.pageController,count++);
								
							}
						}
					}
					//	enmey targets
					for(Component c : this.pageController.getView().getBotColumns().getComponents()) {
						if(((ProfileBriefModel) c).getName().equalsIgnoreCase(ai.getTarget().getName())) {
							SpellType type2 = d.getBase().getSpell().getType();
							System.out.println(ai.getSelf().getName() + " targets " + ai.getTarget().getName() +" with " + d.getBase().getSpell().getName());
							if(type2.equals(SpellType.Attack_All) || type2.equals(SpellType.Trap_ALL)) {
									for(Character cha : userTeam) {
										System.out.println("h");
										enemyCounter++;
										cha.getProfileBrief().updatePlayer(ai.getSelf(), d, this.pageController, count++);
										
									}
							}
							else if(type2.equals(SpellType.Heal_ALL) || type2.equals(SpellType.Blade_ALL) || type2.equals(SpellType.Shield_ALL)) {
								for(Character cha : enemyTeam) {
									System.out.println("i");
									enemyCounter++;
									cha.getProfileBrief().updatePlayer(ai.getSelf(), d, this.pageController, count++);
										
								}
							}
							else {
								System.out.println("j");
								enemyCounter++;
								((ProfileBriefModel) c).updatePlayer(ai.getSelf(),d,this.pageController,count++);
									
							}
						}
					}
				}
				//	if no spell, pass
				else {
					System.out.println(ai.getSelf().getName() + " pass");
					for(Component c : this.pageController.getView().getTopColumns().getComponents()) {
						if(((ProfileBriefModel) c).getName().equalsIgnoreCase(ai.getTarget().getName())) {
							System.out.println("k");
							enemyCounter++;
							((ProfileBriefModel) c).updatePlayer(ai.getSelf(),null,this.pageController,count++);
							
						}
					}
				}
			}
		}
		finalCount+=enemyCounter;
		return finalCount;
	}
	public  int playerTurn(Component a, Pile b,int count,int finalCount) {
		int tempCounter = 0;
		if(a == null || b == null) {
			CardPopup.pass(this.pageController.getView().getFrame(),GameRules.activeCharacter,count++,false);
			System.out.println("a");
			tempCounter++;
		}
		else {
			//	if player makes a move 
			System.out.println("you target " + ((ProfileBriefModel) a).getPlayer().getName() + " with " + b.getBase().getSpell().getName());
			SpellType type = b.getBase().getSpell().getType();
			//	target all teammates
			if(type.equals(SpellType.Heal_ALL) || type.equals(SpellType.Blade_ALL) || type.equals(SpellType.Shield_ALL)) {
				for(Character c : userTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeCharacter, b, this.pageController, count++);
					System.out.println("b");
					tempCounter++;
				}
			}
			//	target all enemies
			else if(type.equals(SpellType.Attack_All) || type.equals(SpellType.Trap_ALL)) {
				for(Character c : enemyTeam) {
					c.getProfileBrief().updatePlayer(GameRules.activeCharacter, b, this.pageController, count++);
					System.out.println("c");
					tempCounter++;
				}
			}
			//	target enemy 
			else {
				
				((ProfileBriefModel) a).updatePlayer(GameRules.activeCharacter,b,this.pageController,count++);
				System.out.println("d");
				tempCounter++;
			}
		}
		finalCount+=tempCounter;
		return finalCount;
	}
	public static boolean getWaiting() {
		return waiting;
	}
	public static void setWaiting(boolean wait) {
		waiting = wait;
	}
}
