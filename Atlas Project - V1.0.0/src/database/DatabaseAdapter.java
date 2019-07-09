package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import character.Backpack;
import character.Character;
import character.EnemyCharacter;
import character.FactionTypes;
import character.Player;
import character.User;
import gameRules.GameRules;
import graphics.InvalidPopup;
import items.Item;
import items.Item.ItemType;
import quest.Quest;
import quest.Quest.Status;
import spells.Card;
import spells.Deck;
import spells.Spell;
import spells.SpellType;
import statistics.Stats;

public class DatabaseAdapter {
	private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String DB_CONNECTION = "jdbc:derby:C:\\Backup of student files\\Summer 2019\\Atlas project\\AtlasProject\\Atlas Project - V1.0.0\\src\\main\\resources\\db;create=true;upgrade=true;";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
	public static boolean scanForUsers(String username) throws SQLException {
		boolean loggedIn = true;
		Connection dbConnection = null;
		Statement statement = null;
		System.out.println("scanning for users");
		String selectTableSQL = "SELECT * from USERS WHERE User_Name= '" + username 
				+"'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			
			//	no users found with given info 
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
				loggedIn = false;
			} 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		return loggedIn;
		
	}
	/*
	 * savePlayerInfo
	 * Explicitly saves all player info before logging out
	 * 
	 */
	public static void savePlayerInfo() throws SQLException {
		
		Connection dbConnection = null;
		Statement statement = null;
		System.out.println("saving player info");
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			//			save gold xp health and level
			String updateTableSQL = "UPDATE PLAYERS "
					+ "SET Players_MAXHP= " + GameRules.activePlayer.getMaxHP() +", Players_XP= " + GameRules.activePlayer.getXp()
					+ ", Players_Level= " + GameRules.activePlayer.getLevel() + ", Players_GOLD= "+ GameRules.activePlayer.getGold()
					+ "WHERE Players_ID= '"+GameRules.activePlayer.getPlayerID()+"'";
			statement.executeUpdate(updateTableSQL);
			
			//	update backpack
			String removeItems ="DELETE FROM PLAYERS_BACKPACK WHERE Players_ID= '"+ GameRules.activePlayer.getPlayerID() +"'";
			statement.executeUpdate(removeItems);
			Map<String,Integer> itemMap = new HashMap<>();
			for(Item s : GameRules.activePlayer.getBackpack().getItems()) {
				if(itemMap.containsKey(s.getName())) {
					itemMap.replace(s.getName(), itemMap.get(s.getName())+1);
				}
				else {
					itemMap.put(s.getName(), 1);
				}
			}
			for(String s : itemMap.keySet()) {
				String saveItems = "INSERT INTO PLAYERS_BACKPACK (Item_Name,Item_Count,Players_ID)"
						+ "VALUES ('" + s + "', " + itemMap.get(s) + ", '" + GameRules.activePlayer.getPlayerID()+"')";
				statement.executeUpdate(saveItems);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
		
	}
	/*
	 * Login Function
	 * Loads player from database or returns false if does not exist
	 * Checks given username and password and checks if they exist in db
	 */
	public static boolean loginPlayer(String username, String password) throws SQLException {
		boolean loggedIn = false;
		Connection dbConnection = null;
		Statement statement = null;
		String selectTableSQL = "SELECT * from USERS WHERE User_Name= '" + username 
				+"' AND User_Password= '"+ password +"'";
		User temp = new User();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			
			//	no users found with given info 
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
				return false;
			} else {
				do {
					String userid = rs.getString("User_ID");
					String name = rs.getString("User_Name");
					String email = rs.getString("User_Email");
					loggedIn = rs.getBoolean("User_Logged_IN");
					//	user alredy logged in 
					if(loggedIn) {
						InvalidPopup p = new InvalidPopup(new JPanel(), "User is already logged in");
						return false;
					}
					
					temp.setUserid(userid);
					temp.setName(name);
					temp.setPassword(password);
					temp.setEmail(email);
					temp.setLoggedIn(true);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		//	if user is not already logged in, update them in db
		if(!loggedIn) {
			//	update logged in value
			try {
			updateUserInfo(temp);
			}catch(Exception e) {
				e.printStackTrace();
				InvalidPopup p = new InvalidPopup(new JPanel(),"Database error");
			}
		}
		// set as active
		GameRules.activeUser = temp;
		
		//	load player list
		GameRules.activeUser.setCharacters(loadUsersPlayerList());
		
		GameRules.activePlayer = GameRules.activeUser.getCharacters().get(0);
		
		return true;
		
	}
	public static void updatePlayerInfo(String command) throws SQLException {
		String updatePlayerTableSQL = null;
		String updatePlayerStatsTableSQL = null;
		System.out.println("updating player equiped items");
		switch(command) {
		case "Update-Equiped": 
			//	update existing stats
			GameRules.activePlayer.getStats().setEffectMap(Stats.loadNewStatsMap());
			if(GameRules.activePlayer.getHelmet() != null) {
				for(String s : GameRules.activePlayer.getHelmet().getEffectMap().keySet()) {
					if(GameRules.activePlayer.getStats().getEffectMap().containsKey(s)) {
						GameRules.activePlayer.getStats().getEffectMap().replace(s, GameRules.activePlayer.getStats().getEffectMap().get(s)+GameRules.activePlayer.getHelmet().getEffect(s));
					}
					else {
						GameRules.activePlayer.getStats().getEffectMap().put(s, GameRules.activePlayer.getHelmet().getEffect(s));
					}
				}	
			}
			if(GameRules.activePlayer.getArmor()!=null) {
				for(String s : GameRules.activePlayer.getArmor().getEffectMap().keySet()) {
					if(GameRules.activePlayer.getStats().getEffectMap().containsKey(s)) {
						GameRules.activePlayer.getStats().getEffectMap().replace(s, GameRules.activePlayer.getStats().getEffectMap().get(s)+GameRules.activePlayer.getArmor().getEffect(s));
					}
					else {
						GameRules.activePlayer.getStats().getEffectMap().put(s, GameRules.activePlayer.getArmor().getEffect(s));
					}
				}
			}
			if(GameRules.activePlayer.getBoots()!=null) {
				for(String s : GameRules.activePlayer.getBoots().getEffectMap().keySet()) {
					if(GameRules.activePlayer.getStats().getEffectMap().containsKey(s)) {
						GameRules.activePlayer.getStats().getEffectMap().replace(s, GameRules.activePlayer.getStats().getEffectMap().get(s)+GameRules.activePlayer.getBoots().getEffect(s));
					}
					else {
						GameRules.activePlayer.getStats().getEffectMap().put(s, GameRules.activePlayer.getBoots().getEffect(s));
					}
				}
			}
			if(GameRules.activePlayer.getAthame()!=null) {
				for(String s : GameRules.activePlayer.getAthame().getEffectMap().keySet()) {
					if(GameRules.activePlayer.getStats().getEffectMap().containsKey(s)) {
						GameRules.activePlayer.getStats().getEffectMap().replace(s, GameRules.activePlayer.getStats().getEffectMap().get(s)+GameRules.activePlayer.getAthame().getEffect(s));
					}
					else {
						GameRules.activePlayer.getStats().getEffectMap().put(s, GameRules.activePlayer.getAthame().getEffect(s));
					}
				}
			}
			if(GameRules.activePlayer.getRing()!=null) {
				for(String s : GameRules.activePlayer.getRing().getEffectMap().keySet()) {
					if(GameRules.activePlayer.getStats().getEffectMap().containsKey(s)) {
						GameRules.activePlayer.getStats().getEffectMap().replace(s, GameRules.activePlayer.getStats().getEffectMap().get(s)+GameRules.activePlayer.getRing().getEffect(s));
					}
					else {
						GameRules.activePlayer.getStats().getEffectMap().put(s, GameRules.activePlayer.getRing().getEffect(s));
					}
				}
			}
			if(GameRules.activePlayer.getWand()!=null) {
				for(String s : GameRules.activePlayer.getWand().getEffectMap().keySet()) {
					if(GameRules.activePlayer.getStats().getEffectMap().containsKey(s)) {
						GameRules.activePlayer.getStats().getEffectMap().replace(s, GameRules.activePlayer.getStats().getEffectMap().get(s)+GameRules.activePlayer.getWand().getEffect(s));
					}
					else {
						GameRules.activePlayer.getStats().getEffectMap().put(s, GameRules.activePlayer.getWand().getEffect(s));
					}
				}
			}
			if(GameRules.activePlayer.getDeck()!=null) {
				for(String s : GameRules.activePlayer.getDeck().getEffectMap().keySet()) {
					if(GameRules.activePlayer.getStats().getEffectMap().containsKey(s)) {
						GameRules.activePlayer.getStats().getEffectMap().replace(s, GameRules.activePlayer.getStats().getEffectMap().get(s)+GameRules.activePlayer.getDeck().getEffect(s));
					}
					else {
						GameRules.activePlayer.getStats().getEffectMap().put(s, GameRules.activePlayer.getDeck().getEffect(s));
					}
				}
			}
			updatePlayerTableSQL = "UPDATE PLAYERS SET ";
			
			//	armor
			if(GameRules.activePlayer.getArmor() == null) {
				updatePlayerTableSQL +="Players_Armor= 'null', ";
			}
			else {
				updatePlayerTableSQL+="Players_Armor= '" + GameRules.activePlayer.getHelmet().getName()+"', ";
			}
			//	helmet
			if(GameRules.activePlayer.getHelmet() == null) {
				updatePlayerTableSQL+="Players_Helmet= 'null', ";
			}
			else {
				updatePlayerTableSQL+="Players_Helmet= '" + GameRules.activePlayer.getHelmet().getName()+"', ";
			}
			//	boots
			if(GameRules.activePlayer.getBoots() == null) {
				updatePlayerTableSQL+="Players_Boots= 'null', ";
			}
			else {
				updatePlayerTableSQL+="Players_Boots= '" + GameRules.activePlayer.getBoots().getName()+"', ";
			}
			//	ring
			if(GameRules.activePlayer.getRing() == null) {
				updatePlayerTableSQL+="Players_Ring= 'null', ";
			}
			else {
				updatePlayerTableSQL+="Players_Ring= '" + GameRules.activePlayer.getRing().getName()+"', ";
			}
			//	athame
			if(GameRules.activePlayer.getAthame() == null) {
				updatePlayerTableSQL+="Players_Athame= 'null', ";
			}
			else {
				updatePlayerTableSQL+="Players_Athame= '" + GameRules.activePlayer.getAthame().getName()+"', ";
			}
			//	wand
			if(GameRules.activePlayer.getWand() == null) {
				updatePlayerTableSQL+="Players_Wand= 'null', ";
			}
			else {
				updatePlayerTableSQL+="Players_Wand= '" + GameRules.activePlayer.getWand().getName()+"', ";
			}
			//	deck
			if(GameRules.activePlayer.getDeck() == null) {
				updatePlayerTableSQL+="Players_Deck= 'null'";
			}
			else {
				updatePlayerTableSQL+="Players_Deck= '" + GameRules.activePlayer.getDeck().getName()+"'";
			}
			updatePlayerTableSQL+=" WHERE Players_ID= '"+GameRules.activePlayer.getPlayerID()+"'";
			
		
			break;
		}
		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.executeUpdate(updatePlayerTableSQL);

			for(String effectName : GameRules.activePlayer.getStats().getEffectMap().keySet()) {
				updatePlayerStatsTableSQL = "UPDATE PLAYERS_STATS "
						+ "SET PLAYERS_STATS.Players_STATS_AMT= " + GameRules.activePlayer.getStats().getEffectMap().get(effectName)
						+ " WHERE Players_ID= '" + GameRules.activePlayer.getPlayerID()+"' AND "+
						"Players_Stats_Name= '"+ effectName +"'";
				statement.executeUpdate(updatePlayerStatsTableSQL);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
	}
	
	/*
	 * loads quests into players known list  
	 */
	public static void loadPlayerKnownQuests() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		System.out.println("loading player's known quests");
		String selectTableSQL = "SELECT * from PLAYER_QUEST_STATUS WHERE Players_ID= '" + GameRules.activePlayer.getPlayerID() 
				+"' AND Quests_Status != 'completed'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			GameRules.activePlayer.setKnownQuests(new ArrayList<>());
			GameRules.activePlayer.setActiveQuests(new ArrayList<>());
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					if(rs.getString("Quests_Status").equalsIgnoreCase("known")) {
						GameRules.activePlayer.getKnownQuests().add(rs.getString("Quests_Name"));
					}
					if(rs.getString("Quests_Status").equalsIgnoreCase("active")) {
						GameRules.activePlayer.getActiveQuests().add(rs.getString("Quests_Name"));
					}
				} while (rs.next());
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
	}
	/*
	 * loads next quest from db
	 */
	public static void loadNextQuest() throws SQLException{
		Connection dbConnection = null;
		Statement statement = null;
		System.out.println("loading next quest");
		List<String> names = GameRules.activePlayer.getCurrentQuest().getNextQuests();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			//	load next quest
			for(String s : names) {
				String searchQuest = "SELECT * FROM QUESTS WHERE QUESTS_NAME= '"+ s + "'";
				ResultSet rs = statement.executeQuery(searchQuest);
				if (rs.next() == false) {
					//System.out.println("ResultSet in empty in Java");
				} else {
					do {
						//	store in known list
						GameRules.activePlayer.getKnownQuests().add(rs.getString("Quests_Name"));
												
					} while (rs.next());
				}
				for(String title : GameRules.activePlayer.getKnownQuests()) {
//					store in database
					String insertQuestsSQL = "INSERT INTO PLAYER_QUEST_STATUS (Players_Id,Quests_Name,Quests_Status) values ('" +
							GameRules.activePlayer.getPlayerID() +"', '"+ title+"', 'known')";
					statement.executeUpdate(insertQuestsSQL);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	
	/*
	 * loads quest from db based on name
	 */
	public static void loadQuest(String name,Quest q) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		System.out.println("loading quest " + name);
		String selectTableSQL = "SELECT * from QUESTS WHERE Quests_Name= '" + name 
				+"'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			GameRules.activePlayer.setKnownQuests(new ArrayList<>());
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					q.setXp(rs.getInt("Quests_XP"));
					q.setGold(rs.getInt("Quests_GOLD"));
					q.setDescription("none");
					List<String> rewards = new ArrayList<>();
					rewards.addAll(Arrays.asList(rs.getString("Quests_Item_Drops").split("::")));
					q.setRewards(rewards);
					List<String> enemies = new ArrayList<>();
					enemies.addAll(Arrays.asList(rs.getString("Quests_Enemies").split("::")));
					q.setEnemies(enemies);
					List<String> nextQuests = new ArrayList<>();
					nextQuests.addAll(Arrays.asList(rs.getString("Quests_Next_Quest").split("::")));
					q.setNextQuests(nextQuests);
				} while (rs.next());
			}
			String filterTableSQL ="SELECT * FROM PLAYER_QUEST_STATUS WHERE Quests_Status != 'completed' AND Quests_Name= '" + name +"'";
			rs = statement.executeQuery(filterTableSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					GameRules.activePlayer.getKnownQuests().add(rs.getString("Quests_Name"));
				} while (rs.next());
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	/*
	 * updates quest status
	 * if quest is completed, load next quest into known list 
	 */
	public static void updateQuestStatus(Quest q) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		System.out.println("updating quest status");
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
		
			String updateTableSQL = "UPDATE PLAYER_QUEST_STATUS "
					+ "SET Quests_Status= '" + q.getQuestStatus().name()
					+ "' WHERE Players_ID= '"+GameRules.activePlayer.getPlayerID()+"'";
			statement.executeUpdate(updateTableSQL);
			GameRules.activePlayer.getKnownQuests().remove(q.getTitle());
			if(q.getQuestStatus().name().equalsIgnoreCase("completed")) {
				for(String s : q.getNextQuests()) {
					GameRules.activePlayer.getKnownQuests().add(s);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	/*
	 * loads player equiped items, stats, backpack, known quests, known bounties
	 */
	public static void loadPlayerInfo(Player p) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		System.out.println("loading player info for " + p.getName());
		String selectStatsTableSQL = "SELECT * from PLAYERS_STATS WHERE Players_ID= '" + GameRules.activePlayer.getPlayerID()+"'";
		String selectBackpackTableSQL = "SELECT * from PLAYERS_BACKPACK WHERE Players_ID= '" + GameRules.activePlayer.getPlayerID()+"'";
		String selectPlayerTableSQL = "SELECT * from PLAYERS WHERE Players_ID= '" + GameRules.activePlayer.getPlayerID()+"'";
		Map<String,Double> playerEffects = new HashMap<>();
		Map<String,Integer> playerBackpack = new HashMap<>();
		Map<String,Integer> playerEquiped = new HashMap<>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			
			//	set up equiped items //////////////////////
			ResultSet rs = statement.executeQuery(selectPlayerTableSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					GameRules.activePlayer.setName(rs.getString("Players_Name"));
					GameRules.activePlayer.setFaction(FactionTypes.loadClass(rs.getString("Players_Faction")));
					GameRules.activePlayer.setLevel(rs.getInt("Players_Level"));
					GameRules.activePlayer.setXp(rs.getInt("Players_XP"));
					GameRules.activePlayer.setMaxHP(rs.getInt("Players_MAXHP"));
					if(rs.getString("Players_Helmet")!= null) {
						playerEquiped.put(rs.getString("Players_Helmet"), 1);
					}
					if(rs.getString("Players_Boots")!= null) {
						playerEquiped.put(rs.getString("Players_Boots"), 1);
					}
					if(rs.getString("Players_Armor")!= null) {
						playerEquiped.put(rs.getString("Players_Armor"), 1);
					}
					if(rs.getString("Players_Ring")!= null) {
						playerEquiped.put(rs.getString("Players_Ring"), 1);
					}
					if(rs.getString("Players_Athame")!= null) {
						playerEquiped.put(rs.getString("Players_Athame"), 1);
					}
					if(rs.getString("Players_Deck")!= null) {
						playerEquiped.put(rs.getString("Players_Deck"), 1);
					}
					if(rs.getString("Players_Wand")!= null) {
						playerEquiped.put(rs.getString("Players_Wand"), 1);
					}
					
				} while (rs.next());
			}
			//	set up stats	///////////////////////////////////////
			rs = statement.executeQuery(selectStatsTableSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					String effectname = rs.getString("Players_Stats_Name");
					double effectamt= rs.getDouble("Players_Stats_AMT");
					playerEffects.put(effectname, Double.valueOf(effectamt));
				} while (rs.next());
			}
			//	set up backpack	////////////////////////////////////
			rs = statement.executeQuery(selectBackpackTableSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					String itemName = rs.getString("Item_Name");
					int itemcount = rs.getInt("Item_Count");
					playerBackpack.put(itemName, itemcount);
				} while (rs.next());
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		//		load spells into equiped deck
			List<String> deckSpells = new ArrayList<>();
			try {
				deckSpells = DatabaseAdapter.LoadDeck(GameRules.activePlayer.getPlayerID());
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			List<Spell> deckSpellCards = new ArrayList<>();
			try {
				deckSpellCards = DatabaseAdapter.LoadSpells(deckSpells);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		//	load items into backpack
		List<Item> equiped = loadItemList(playerEquiped);
		for(Item i : equiped) {
			ItemType temp = i.getWeaponType();
			switch(temp.name()) {
			case "Helmet" : GameRules.activePlayer.setHelmet(i);
				break;
			case "Armor" : GameRules.activePlayer.setArmor(i);
				break;
			case "Boots" : GameRules.activePlayer.setBoots(i);
				break;
			case "Athame": GameRules.activePlayer.setAthame(i);
				break;
			case "Ring": GameRules.activePlayer.setRing(i);
				break;
			case "Wand": GameRules.activePlayer.setWand(i);
				break;
			case "Deck" : 
				Deck deck = new Deck(deckSpellCards);
				deck.setCost(i.getCost());
				deck.setDropRate(i.getDropRate());
				deck.setFaction(i.getFaction());
				deck.setLevelRequirement(i.getLevelRequirement());
				deck.setName(i.getName());
				deck.setSellable(i.isSellable());
				deck.setWeaponType(i.getWeaponType());
				deck.setWorth(i.getWorth());
				GameRules.activePlayer.setDeck(deck);
				break;
			}
		}
		GameRules.activePlayer.getBackpack().setItems(loadItemList(playerBackpack));
		
		////////	load known spells
		List<String> knownSpells = new ArrayList<>();
		try {
			knownSpells = DatabaseAdapter.loadKnownSpells(GameRules.activePlayer.getPlayerID());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
		}
		List<Spell> knownSpellCards = new ArrayList<>();
		try {
			knownSpellCards = DatabaseAdapter.LoadSpells(knownSpells);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
		}
		GameRules.activePlayer.setKnownSpells(knownSpellCards);
		
		//	load quests
		DatabaseAdapter.loadPlayerKnownQuests();
		//	load bounties
		DatabaseAdapter.loadPlayerBounties();
		//---------------------------------------------------------------------///
		
		
		GameRules.activePlayer.setCharacter(new Character(deckSpellCards, GameRules.activePlayer.getMaxHP(), GameRules.activePlayer.getName(), GameRules.activePlayer.getFaction()));
		GameRules.activeCharacter = GameRules.activePlayer.getCharacter();
		
		System.out.println("Finished loading player ");
	}
	private static void loadPlayerBounties() throws SQLException{
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		Statement statement = null;
		List<String> results = new ArrayList<>();
		System.out.println("loading bounty list");
		String selectTableSQL = "SELECT * from PLAYERS_BOUNTIES WHERE Players_ID= '" + GameRules.activePlayer.getPlayerID()+"'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					results.add(rs.getString("Enemy_Name"));
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		GameRules.activePlayer.setBountyList(results);
	}
	private static List<String> LoadDeck(String playerID) throws SQLException{
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		Statement statement = null;
		List<String> results = new ArrayList<>();
		System.out.println("loading deck");
		String selectTableSQL = "SELECT * from PLAYERS_DECK WHERE Players_ID= '" + GameRules.activePlayer.getPlayerID()+"'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					String name = rs.getString("Spell_Name");
					int count = rs.getInt("Spell_Count");
					for(int i  =0 ; i < count; i++) {
						results.add(name);
					}
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return results;
	}
	private static List<String> loadKnownSpells(String playerID) throws SQLException{
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		Statement statement = null;
		List<String> spells = new ArrayList<>();
		System.out.println("loading known spells");
		String selectTableSQL = "SELECT * from PLAYERS_KNOWN_SPELLS WHERE Players_ID= '" + GameRules.activePlayer.getPlayerID()+"'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					String name = rs.getString("Spell_Name");
					spells.add(name);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return spells;
	}
	/*
	 * loadItemList 
	 * loads items into backpack from name and count given
	 */
	public static List<Item> loadItemList(Map<String,Integer> playerBackpack) throws SQLException{
		List<Item> items=  new ArrayList<>();
		Connection dbConnection = null;
		Statement statement = null;
		System.out.println("Loading items list");
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			for(String itemtitle : playerBackpack.keySet()) {
				String selectItemSQL = "SELECT * FROM ITEMS WHERE Items_Name= '" + itemtitle+"'";
				
				ResultSet rs = statement.executeQuery(selectItemSQL);
				if (rs.next() == false) {
					System.out.println("ResultSet in empty in Java");
				} else {
					do {
						Item temp = new Item();
						temp.setName(itemtitle);
						temp.setWeaponType(Item.getItemType(rs.getString("Items_Type")));
						temp.setDropRate(rs.getDouble("Items_Drop_Rate"));
						temp.setCost(rs.getInt("Items_Cost"));
						temp.setWorth(rs.getInt("Items_Worth"));
						temp.setSellable(rs.getBoolean("Items_Sellable"));
						temp.setLevelRequirement(rs.getInt("Items_Level_REQ"));
						temp.setFaction(FactionTypes.loadClass(rs.getString("Items_Faction")));
						for(int i  =0 ; i < playerBackpack.get(itemtitle);i++) {
							items.add(temp);
						}
					} while (rs.next());
				}
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return items;
	}
	public static List<Player> loadUsersPlayerList() throws SQLException{
		Connection dbConnection = null;
		Statement statement = null;
		List<Player> allEmps = new ArrayList<>();
		System.out.println("loading user's player list");
		String selectTableSQL = "SELECT * from PLAYERS WHERE User_ID= '" + GameRules.activeUser.getUserid()+"'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					Player temp = new Player();
					String playerid = rs.getString("Players_ID");
					String name = rs.getString("Players_Name");
					String faction = rs.getString("Players_Faction");
					int level = rs.getInt("Players_Level");
					int xp = rs.getInt("Players_XP");
					int gold = rs.getInt("Players_Gold");
					int maxhp = rs.getInt("Players_MAXHP");
					temp.setPlayerID(playerid);
					temp.setName(name);
					temp.setFaction(FactionTypes.loadClass(faction));
					temp.setLevel(level);
					temp.setXp(xp);
					temp.setMaxHP(maxhp);
					temp.setStats(new Stats());
					allEmps.add(temp);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return allEmps;
		
	}

	public static void logout() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		boolean update = false;
		if(GameRules.activeUser!= null) {
			savePlayerInfo();
		String updateTableSQL = "UPDATE USERS "
				+ "SET User_Logged_IN= FALSE "
				+ "WHERE USER_ID= '"+GameRules.activeUser.getUserid()+"'";
		
		try {
			DatabaseAdapter.savePlayerInfo();
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.executeUpdate(updateTableSQL);
			System.out.println("logged out!");
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		}
	}
	public static void addPlayer() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		boolean update = false;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			///	new user initialization/////////////
				System.out.println("Storing new player info");
				GameRules.activePlayer.setPlayerID(GameRules.generatePlayerHash(GameRules.activePlayer.getName()));
				
				String insertPlayerTableSQL = "INSERT INTO PLAYERS" + "(User_ID,Players_ID,Players_Name,Players_Faction,Players_Level,Players_XP,Players_GOLD,Players_MAXHP,Players_Deck)"
						+ " VALUES ('" + GameRules.activeUser.getUserid() + "', '" 
						+ GameRules.generatePlayerHash(GameRules.activePlayer.getName()) + "', '"
						+ GameRules.activePlayer.getName() + "', '" + GameRules.activePlayer.getFaction().name() + "', "
						+ GameRules.activePlayer.getLevel()+ ", " + GameRules.activePlayer.getXp()+ ", "
								+ GameRules.activePlayer.getGold()+", " + GameRules.activePlayer.getMaxHP() + ", '"+GameRules.activePlayer.getDeck().getName()+"')";
				
				String insertPlayerBackpackSQL = "INSERT INTO PLAYERS_BACKPACK (Players_ID,Item_Name,Item_Count) values ('"+
						 GameRules.generatePlayerHash(GameRules.activePlayer.getName()) + "', '" +
						 GameRules.activePlayer.getDeck().getName()+"', "+ 1 +")";
				String insertQuestsSQL = "INSERT INTO PLAYER_QUEST_STATUS (Players_Id,Quests_Name,Quests_Status) values ('" +
						GameRules.generatePlayerHash(GameRules.activePlayer.getName())  +"', 'Corellons Corpse', 'known')";
				
				GameRules.activePlayer.setKnownQuests(new ArrayList<>());
				GameRules.activePlayer.getKnownQuests().add("Corellons Corpse");
				
				
				//	load player
				statement.executeUpdate(insertPlayerTableSQL);
				//	load player backpack
				statement.executeUpdate(insertPlayerBackpackSQL);
				//	load quest
				statement.executeUpdate(insertQuestsSQL);
				
				//	load known spells into deck
				for(Spell s : GameRules.activePlayer.getKnownSpells()) {
					String insertIntoKnownSpells = "INSERT INTO PLAYERS_KNOWN_SPELLS (Players_ID,Spell_Name) values ('"+
							GameRules.generatePlayerHash(GameRules.activePlayer.getName())+"', '" + s.getName() + "' )"; 
					String insertIntoDeck = "INSERT INTO PLAYERS_DECK (Players_ID,Spell_Name,Spell_Count) values ('" +
							GameRules.generatePlayerHash(GameRules.activePlayer.getName())+"', '" + s.getName() + "', "+1+")";
					
					statement.executeUpdate(insertIntoKnownSpells);
					statement.executeUpdate(insertIntoDeck);
				}
				//	load new stats
				for(String s: GameRules.activePlayer.getStats().getEffectMap().keySet()) {
					String insertIntoStats = "INSERT INTO PLAYERS_STATS (Players_ID,Players_Stats_Name,Players_Stats_AMT)"
							+ "VALUES ('"+ GameRules.activePlayer.getPlayerID() +"', '" + s + "', " + GameRules.activePlayer.getStats().getEffect(s) + ")";
					
					statement.executeUpdate(insertIntoStats);
				}
				System.out.println("Record is inserted into player table!");
			}
			catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	/*
	 * update user info in database
	 * creates new user if not already in system
	 * else updates user info 
	 */
	public static void updateUserInfo(User tempUser) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		boolean update = false;
		if(tempUser.getUserid() != null) {
			update = true;
		}
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			///	new user initialization/////////////
			if(update == false) {
				System.out.println("Storing new user info");
				GameRules.activeUser.setUserid(GameRules.generateUserHash(tempUser.getUsername()));
				GameRules.activePlayer.setPlayerID(GameRules.generatePlayerHash(tempUser.getCharacters().get(0).getName()));
				String insertTableSQL = "INSERT INTO USERS" + "(User_ID,User_Name,User_Password,User_Email,User_Logged_In,User_Gender,User_Security_Question,User_Security_Answer) " 
						+ "VALUES ('" + GameRules.generateUserHash(tempUser.getUsername()) + "', '" 
								+ tempUser.getUsername() + "', '" + tempUser.getPassword()+ "', '" + tempUser.getEmail() + "', "
								+ Boolean.valueOf(tempUser.isLoggedIn())+ ", '" + tempUser.getGender() + "', '" + tempUser.getSecurityQuestion() +"', '" + tempUser.getSecurityAnswer() +"'"+")";
				String insertPlayerTableSQL = "INSERT INTO PLAYERS" + "(User_ID,Players_ID,Players_Name,Players_Faction,Players_Level,Players_XP,Players_GOLD,Players_MAXHP,Players_Deck)"
						+ " VALUES ('" + GameRules.generateUserHash(tempUser.getUsername()) + "', '" 
						+ GameRules.generatePlayerHash(tempUser.getCharacters().get(0).getName()) + "', '"
						+ tempUser.getCharacters().get(0).getName() + "', '" + tempUser.getCharacters().get(0).getFaction().name() + "', "
						+ tempUser.getCharacters().get(0).getLevel()+ ", " + tempUser.getCharacters().get(0).getXp()+ ", "
								+ tempUser.getCharacters().get(0).getGold()+", " + tempUser.getCharacters().get(0).getMaxHP() + ",'" + tempUser.getCharacters().get(0).getDeck().getName()+"')";
				
				String insertPlayerBackpackSQL = "INSERT INTO PLAYERS_BACKPACK (Players_ID,Item_Name,Item_Count) values ('"+
						 GameRules.generatePlayerHash(tempUser.getCharacters().get(0).getName()) + "', '" +
						 tempUser.getCharacters().get(0).getDeck().getName()+"', "+ 1 +")";
				String insertQuestsSQL = "INSERT INTO PLAYER_QUEST_STATUS (Players_Id,Quests_Name,Quests_Status) values ('" +
						GameRules.generatePlayerHash(tempUser.getCharacters().get(0).getName())  +"', 'Corellons Corpse', 'known')";
				
				GameRules.activePlayer.setKnownQuests(new ArrayList<>());
				GameRules.activePlayer.getKnownQuests().add("Corellons Corpse");
				
				
				//	load user 
				statement.executeUpdate(insertTableSQL);
				//	load player
				statement.executeUpdate(insertPlayerTableSQL);
				//	load player backpack
				statement.executeUpdate(insertPlayerBackpackSQL);
				//	load quest
				statement.executeUpdate(insertQuestsSQL);
				
				//	load known spells into deck
				for(Spell s : GameRules.activePlayer.getKnownSpells()) {
					String insertIntoKnownSpells = "INSERT INTO PLAYERS_KNOWN_SPELLS (Players_ID,Spell_Name) values ('"+
							GameRules.generatePlayerHash(tempUser.getCharacters().get(0).getName())+"', '" + s.getName() + "' )"; 
					String insertIntoDeck = "INSERT INTO PLAYERS_DECK (Players_ID,Spell_Name,Spell_Count) values ('" +
							GameRules.generatePlayerHash(tempUser.getCharacters().get(0).getName())+"', '" + s.getName() + "', "+1+")";
					
					statement.executeUpdate(insertIntoKnownSpells);
					statement.executeUpdate(insertIntoDeck);
				}
				//	load new stats
				for(String s: GameRules.activePlayer.getStats().getEffectMap().keySet()) {
					String insertIntoStats = "INSERT INTO PLAYERS_STATS (Players_ID,Players_Stats_Name,Players_Stats_AMT)"
							+ "VALUES ('"+ GameRules.activePlayer.getPlayerID() +"', '" + s + "', " + GameRules.activePlayer.getStats().getEffect(s) + ")";
					
					statement.executeUpdate(insertIntoStats);
				}
				System.out.println("Record is inserted into User table!");
			}
			else {
				String updateTableSQL = "UPDATE USERS "
						+ "SET User_Logged_IN= TRUE "
						+ "WHERE USER_ID= '"+tempUser.getUserid()+"'";
				statement.executeUpdate(updateTableSQL);
				System.out.println("Record is updated in User table!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		
	}
	public static List<Spell> LoadSpells(List<String> loadStarterSpells) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		List<Spell> spells = new ArrayList<>();
		System.out.println("loading spells");
		String selectTableSQL ="";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			
			ResultSet rs; 
			for(String s : loadStarterSpells) {
				selectTableSQL = "SELECT * FROM SPELLS WHERE Spells_Name= '" + s+ "'";
				rs = statement.executeQuery(selectTableSQL);
				if (rs.next() == false) {
					//System.out.println("ResultSet in empty in Java");
				} else {
					do {
						Spell temp = new Spell();
						SpellType type = SpellType.getSpellType(rs.getString("Spells_Type"));
						String name = rs.getString("Spells_Name"); 
						FactionTypes faction = FactionTypes.loadClass(rs.getString("Spells_Faction"));
						int cost = rs.getInt("Spells_Pip_Cost");
						double castChance = rs.getDouble("Spells_Cast_Chance");
						int heal = (int) rs.getDouble("Spells_Heal_AMT");
						int dmg = (int) rs.getDouble("Spells_DMG_AMT");
						double resist = rs.getDouble("Spells_RESIST_AMT");
						double boost = rs.getDouble("Spells_BOOST_AMT");
						Set<FactionTypes> targets = new HashSet<>();
						if(rs.getString("Spells_Target_Factions") != null) {
							List<String> targetFactions = Arrays.asList(rs.getString("Spells_Target_Factions").split("::"));
							for(String s1 : targetFactions) {
								targets.add(FactionTypes.loadClass(s1));
							}
						}
						switch(type) {
						case Attack: 
							temp.makeAttackSpell(faction,name,castChance,dmg);
							spells.add(temp);
							break;
						case Attack_All:
							temp.makeAttackSpell(faction,name,castChance,dmg);
							temp.setType(SpellType.Attack_All);
							spells.add(temp);
							break;
						case Heal:
							temp.makeHealSpell(faction, name, castChance, heal);
							spells.add(temp);
							break;
						case Heal_ALL:
							temp.makeHealSpell(faction, name, castChance, heal);
							spells.add(temp);
							temp.setType(SpellType.Heal_ALL);
							break;
						case Shield:
							temp.makeShieldSpell(faction, name, resist,targets);
							spells.add(temp);
							break;
						case Shield_ALL:
							temp.makeShieldSpell(faction, name, resist,targets);
							spells.add(temp);
							temp.setType(SpellType.Shield_ALL);
							break;
						case Blade:
							temp.makeBladeSpell(faction, name, boost, targets);
							spells.add(temp);
							break;
						case Blade_ALL:
							temp.makeBladeSpell(faction, name, boost, targets);
							spells.add(temp);
							temp.setType(SpellType.Blade_ALL);
							break;
						case Trap:
							temp.makeTrapSpell(faction, name, boost, targets);
							spells.add(temp);
							break;
						case Trap_ALL:
							temp.makeTrapSpell(faction, name, boost, targets);
							spells.add(temp);
							temp.setType(SpellType.Trap_ALL);
							break;
						}
					} while (rs.next());
				}
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return spells;
	}
	public static List<Spell> saveSpells(List<String> loadStarterSpells) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * Loads a single item into database
	 */
	public static Item loadItemList(String name) throws SQLException {
		// TODO Auto-generated method stub
		List<Item> items=  new ArrayList<>();
		Connection dbConnection = null;
		Statement statement = null;
		Item temp = new Item();
		System.out.println("Loading item");
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			String selectItemSQL = "SELECT * FROM ITEMS WHERE Items_Name= '" + name+"'";
			//System.out.println(selectItemSQL);
			ResultSet rs = statement.executeQuery(selectItemSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					temp.setName(name);
					temp.setWeaponType(Item.getItemType(rs.getString("Items_Type")));
					temp.setDropRate(rs.getDouble("Items_Drop_Rate"));
					temp.setCost(rs.getInt("Items_Cost"));
					temp.setWorth(rs.getInt("Items_Worth"));
					temp.setSellable(rs.getBoolean("Items_Sellable"));
					temp.setLevelRequirement(rs.getInt("Items_Level_REQ"));
					temp.setFaction(FactionTypes.loadClass(rs.getString("Items_Faction")));
					
				} while (rs.next());
			}
			String selectItemStatsSQL = "SELECT * FROM ITEMS_STATS WHERE Items_Name= '" +name+"'";
			rs = statement.executeQuery(selectItemStatsSQL);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					String effect = rs.getString("Items_Stats_Name");
					double amt = rs.getDouble("Items_Stats_AMT");
					temp.addEffect(effect, amt);
					
				} while (rs.next());
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return temp;
	}
	public static void updateDeck() throws SQLException {
		// TODO Auto-generated method stub
		Connection dbConnection = getDBConnection();
		final Statement statement = dbConnection.createStatement();
		List<Spell> spells = new ArrayList<>();
		String removeSpellsFromDeck ="DELETE FROM PLAYERS_DECK WHERE Players_ID= '"+ GameRules.activePlayer.getPlayerID() +"'";
		try {
			// execute select SQL stetement
			System.out.println("removing existing spells");
			System.out.println(removeSpellsFromDeck);
			statement.executeUpdate(removeSpellsFromDeck);
			System.out.println("saving new spells");
			int i  =0;
			Set<String> cards = new HashSet<>();
			for(Card s : GameRules.activePlayer.getDeck().getCards()) {
				cards.add(s.getSpell().getName());
			}
			for(String s : cards) {
				String insertSpellsIntoDeck = "INSERT INTO PLAYERS_DECK (Players_ID,Spell_Name,Spell_Count) values ('"
						+ GameRules.activePlayer.getPlayerID() + "', '"+ s + "', " + GameRules.activePlayer.getDeck().getCards().stream().filter(b -> b.getName().equals(s)).count() + ")";	
				//System.out.println(insertSpellsIntoDeck);
				try {
					statement.executeUpdate(insertSpellsIntoDeck);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	public static Character loadBoss(String bossName) throws Exception{
		// TODO Auto-generated method stub
				Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();
				String selectDeck = "SELECT * FROM ENEMY_DECK WHERE ENEMY_NAME= '" + bossName + "'";
				String selectBoss ="SELECT * FROM ENEMIES WHERE Enemy_Name='"+ bossName + "'";
				String selectStats = "SELECT * FROM ENEMY_STATS WHERE Enemy_Name= '" + bossName +"'";
				List<String> spells = new ArrayList<>();
				Character boss = null;
				//	load spells
				try {	
					ResultSet rs = statement.executeQuery(selectDeck);
					if (rs.next() == false) {
						//System.out.println("deck ResultSet in empty in Java");
					} else {
						do {
							spells.add(rs.getString("Spell_Name"));
							
						} while (rs.next());
					}
					List<Spell> spellList = DatabaseAdapter.LoadSpells(spells);
					//	load boss
					 rs = statement.executeQuery(selectBoss);
					 
						if (rs.next() == false) {
							//System.out.println("ResultSet in empty in Java");
						} else {
							do {
								int hp  = rs.getInt("Enemy_MAXHP");
								FactionTypes faction = FactionTypes.loadClass(rs.getString("Enemy_Faction"));
								int rank = rs.getInt("Enemy_Rank");
								boss = new EnemyCharacter(spellList,hp,bossName,faction);
								((EnemyCharacter) boss).setRank(rank);
							} while (rs.next());
						}
						//	load stats
					rs = statement.executeQuery(selectStats);
					boss.setStats(new Stats());
					if(rs.next() == false) {
						//System.out.println("ResultSet in empty in java");
					}
					else {
						do {
							String statName = rs.getString("Enemy_Stats_Name");
							double amt = rs.getDouble("Enemy_Stats_Amt");
							boss.getStats().getEffectMap().put(statName, amt);
						}while(rs.next());
					}
					//	load drops
					((EnemyCharacter) boss).setDropMap(new HashMap<>());
					String selectDrops = "SELECT * FROM ENEMY_DROPS WHERE ENEMY_NAME ='" + bossName + "'";
					rs = statement.executeQuery(selectDrops);
					if(rs.next()!= false) {
						do {
							((EnemyCharacter) boss).getDropMap().put(rs.getString("Drop_Name"), rs.getDouble("Drop_Rate"));
						}while(rs.next());
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				} finally {
					if (statement != null) {
						statement.close();
					}
					if (dbConnection != null) {
						dbConnection.close();
					}
				}
				return boss;
	}
	public static void makeBounty() throws SQLException{
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		Statement statement = null;
		boolean update = false;
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			for(String s : GameRules.activePlayer.getCurrentQuest().getEnemies()) {
				String scanBoss = "SELECT * FROM ENEMIES WHERE Enemy_Name= '" + s + "' AND Enemy_IS_Boss = TRUE";
				ResultSet rs = statement.executeQuery(scanBoss);
				if (rs.next() == false) {
					//System.out.println("ResultSet in empty in Java");
				} else {
					do {
						//	store in bounty list
						GameRules.activePlayer.getBountyList().add(rs.getString("Enemy_Name"));
						//	add to bounty table in db
						String insertBountySQL = "INSERT INTO PLAYERS_BOUNTIES (Players_ID,Enemy_Name) VALUES ('" +
								GameRules.activePlayer.getPlayerID()  +"', '" + rs.getString("Enemy_Name")+"')";
						statement.executeUpdate(insertBountySQL);
					} while (rs.next());
				}
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	public static void addNewSpell(int level) throws SQLException{
		// TODO Auto-generated method stub
		Connection dbConnection = null;
		Statement statement = null;
		boolean update = false;
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			String scanSpell = "SELECT * FROM SPELLS WHERE Spells_Rank= "+ level + " AND Spells_Faction= '"
					+ GameRules.activePlayer.getFaction().name() + "'";
		
			ResultSet rs = statement.executeQuery(scanSpell);
			if (rs.next() == false) {
				//System.out.println("ResultSet in empty in Java");
			} else {
				do {
					//	store in knwon list
					GameRules.activePlayer.getKnownSpells().add(new Spell(rs.getString("Spells_Name")));
					
					//	add to known table in db
					String insertKnownSql = "INSERT INTO PLAYERS_KNOWN_SPELLS (Players_ID,Spell_Name) VALUES ('" +
							GameRules.activePlayer.getPlayerID()  +"', '" + rs.getString("Spells_Name")+"')";
					statement.executeUpdate(insertKnownSql);
				} while (rs.next());
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
}
