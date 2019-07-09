package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import character.Character;
import character.EnemyCharacter;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import character.EnemyCharacter;
import character.FactionTypes;
import gameRules.GameRules;
import graphics.GraphicsController;
import items.Item;
import items.Item.ItemType;
import quest.Quest;
import quest.Quest.Status;
import resources.BackgroundMusic;
import resources.Faction;
import spells.Card;
import spells.Spell;
import spells.SpellType;
import statistics.Stats;

public class DatabaseLoader {
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
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			//storeItems();
			//storeSpells();
			storeQuests();
			/*
			storeEnemies();
			storeBosses();
			storeQuests();
			*/
			//storeSpells();
			//updateMobDecks();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static double generateRandomAmt(int scale) {
		double amt = 0.01;
		Random rand = new Random();
		amt*=rand.nextInt(scale+1);
		while(amt >= 1.0) {
			amt = 0.01;
			amt*=rand.nextInt(scale+1);
		}
		return amt;
	}
	public static String generateRandomEffect() {
		String arr [] = {"GENERIC_DMG","The_Cult_Of_The_Stars_DMG","The_Ritualists_DMG",
				"The_Collective_DMG","The_Children_Of_The_Mask_DMG","The_Legion_DMG","The_Ennead_DMG","The_Called_DMG",
				"GENERIC_RESIST","The_Cult_Of_The_Stars_RESIST","The_Ritualists_RESIST","The_Collective_RESIST","The_Children_Of_The_Mask_RESIST",
				"The_Legion_RESIST","The_Ennead_RESIST","The_Called_RESIST","The_Cult_Of_The_Stars_CRIT","The_Ritualists_CRIT",
				"The_Collective_CRIT","The_Children_Of_The_Mask_CRIT","The_Legion_CRIT","The_Ennead_CRIT",
				"The_Called_CRIT","GENERIC_CRIT","GENERIC_CRIT_BLOCK",
				"The_Cult_Of_The_Stars_CRIT_BLOCK","The_Ritualists_CRIT_BLOCK","The_Collective_CRIT_BLOCK",
				"The_Children_Of_The_Mask_CRIT_BLOCK","The_Legion_CRIT_BLOCK",""
						+ "The_Ennead_CRIT_BLOCK","The_Called_CRIT_BLOCK"};
				
		Random rand = new Random();
		return arr[rand.nextInt(arr.length)];
	}
	/*
	 * populates effect map with random effects given scale
	 */
	public static Map<String,Double> populateSet(int scale){
		Map<String,Double> effects = new HashMap<>();
		 // create instance of Random class 
        Random rand = new Random(); 
        // Generate random integers in range 0 to 10
        int rand_int1 = rand.nextInt(10);
        for(int i= 0; i < rand_int1;i++) {
        	effects.put(generateRandomEffect(),generateRandomAmt(scale));
        }
        return effects;
	}
	public static Map<String,Double> mutate(int scale,String comp1,double comp1amt,String comp2,double comp2amt){
		Map<String,Double> mutationMap = new HashMap<>();

		if(GameRules.accuracy(Math.random())) {
			mutationMap.put(comp1, comp2amt);
		}
		else{
			mutationMap.put(comp2, comp1amt);
		
		}
		return mutationMap;
	}
	/*
	 * generateEffects
	 * genetic algorithm to randomly assign effects based on scale and faction
	 */
	public static Map<String,Double> generateEffects(int scale,ItemType weaponType,FactionTypes faction){
		Map<String,Double> effectMap = populateSet(scale);
		Map<String,Double> scoreMap = new HashMap<>();
		boolean completed = false;
		int generation =0;
		while(!completed && generation < 5) {
//			calculate score
			for(String s : effectMap.keySet()) {
				if(s.contains(faction.name())) {
					scoreMap.put(s,(effectMap.get(s)*2));
				}
				else {
					scoreMap.put(s, (effectMap.get(s)*1));
				}
			}
			//	kill x weakest
			Map<String,Double> reducedScoreMap = new HashMap<>();
			scoreMap.entrySet().stream().filter(x -> x.getValue() > 0.01*(scale/5))
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
					.forEachOrdered(x -> reducedScoreMap.put(x.getKey(),x.getValue()));
			
			//	mutate at random
			Map<String,Double > mutationMap = new HashMap<>();
			List<String> effects = new ArrayList<>();
			effects.addAll(reducedScoreMap.keySet());
			for(int i  = 0;  i < effects.size(); i ++) {
				String a = effects.get(i);
				if(effects.size()>2) {
					if(i+1 == effects.size()) {
						String b = effects.get(0);
						mutationMap.putAll(mutate(scale,a, reducedScoreMap.get(a),b,reducedScoreMap.get(b)));
					}
					else {
						String b = effects.get(i+1);
						mutationMap.putAll(mutate(scale,a, reducedScoreMap.get(a),b,reducedScoreMap.get(b)));
					}
				}
				
			}
			reducedScoreMap.putAll(mutationMap);
			
			Map<String,Double> completedMap = new HashMap<>();
			reducedScoreMap.entrySet().stream().filter(x -> x.getValue()>0.01*(scale/5))
			.forEachOrdered(x -> completedMap.put(x.getKey(), x.getValue()));
			
			
			completed = completedMap.entrySet().stream().anyMatch(x -> x.getValue() > 0.01 *(scale/5));
			effectMap = completedMap;
			generation++;
		}
		
		//	completion test
		//	apply health boost based on weapon type
		double healthamt = 0.01;
		healthamt *=(scale*1000);
		switch(faction) {
		case Generic: healthamt*=.6;
			break;
		case The_Called: healthamt*=.8;
			break;
		case The_Children_Of_The_Mask:healthamt*=.7;
			break;
		case The_Collective:healthamt*=1.0;
			break;
		case The_Cult_Of_The_Stars:healthamt*=1.1;
			break;
		case The_Ennead:healthamt*=1.2;
			break;
		case The_Legion:healthamt*=1.5;
			break;
		case The_Ritualists:healthamt*=.9;
			break;
		default:
			break;
		
		}
		switch(weaponType.name()) {
		case "Helmet": healthamt/=3;
			effectMap.put("HEALTH_BOOST", healthamt);
			break;
		case "Armor": healthamt/=1.5;
		effectMap.put("HEALTH_BOOST", healthamt);
		break;
		case "Ring": healthamt/=2;
		effectMap.put("HEALTH_BOOST", healthamt);
			break;
		case "Athame": healthamt/=2.5;
		effectMap.put("HEALTH_BOOST", healthamt);
			break;
		case "Wand" : 
			break;
		case "Deck":
			break;
		case "Boots": healthamt/=3.5;
		effectMap.put("HEALTH_BOOST", healthamt);
			break;
		}
		return effectMap;
	}
	/*
	 * generateItem
	 * returns item with random stats given name, faction, and iteration for scale
	 * sets name, faction, level requirement, weapon  type, and scale
	 * passes to auxilary function to set effect map for weapon 
	 * 
	 */
	public static Item generateItem(String name, int faction, int count,ItemType weaponType) {
		Item temp = new Item();
		temp.setName(name);
		temp.setFaction(FactionTypes.loadClass(faction));
		temp.setLevelRequirement(3*count);
		temp.setWeaponType(weaponType);
		if(count % 26 ==0) {
			temp.setSellable(false);
			temp.setDropRate(0.05);
			temp.setCost(0);
			temp.setWorth(0);
		}
		else {
			temp.setSellable(true);
			temp.setCost(count%10 *count*3);
			temp.setWorth((int) (temp.getCost() / 1.5));
			if(1.0/(3*count%10+1) == 1.0) {
				temp.setDropRate(1.0/(3*count%10+2));
			}
			else {
				temp.setDropRate(1.0/(3*count%10+1));
			}
			
		}
		int scale = temp.getLevelRequirement();
		//	set stats
		//	determine number of stats for weapon
		temp.setEffectMap(generateEffects(scale,temp.getWeaponType(),temp.getFaction()));
		
		//	print
		
		
		
		return temp;
	}
	public static void storeItems() throws Exception{
		Connection dbConnection = null;
		Statement statement = null;
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// Open the file
			FileInputStream fstream = new FileInputStream("helmetnames.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			//Read File Line By Line
			int count = 1;
			int faction = 0;
			int up = 0;
			List<String> names = new ArrayList<>();
			Set<String> setnames = new HashSet<>();
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				 setnames.add(strLine);
			}
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			PrintWriter out = new PrintWriter(new FileWriter(new File("helmetStats.txt"), true));
			for(String sd : names) {
				Item temp = generateItem(sd,faction,count,ItemType.Helmet);
				//	insert item
				temp.setName(temp.getName().replace("'", ""));
				String INSERTITEMS = "INSERT INTO ITEMS (Items_Name,Items_Faction,Items_Level_Req,Items_Type,Items_Cost,Items_Worth,Items_Drop_Rate,Items_SELLABLE)" +
				"VALUES ('" + temp.getName() + "', '" + temp.getFaction().name() +"', " + temp.getLevelRequirement() + ", '" + temp.getWeaponType().name() + "', " 
				+	temp.getCost() + ", " + temp.getWorth() + ", " + temp.getDropRate() + ", " + temp.isSellable() + ")";
				System.out.println(INSERTITEMS);
				
				statement.executeUpdate(INSERTITEMS);
				//	insert item stats
				
				out.println("Name "  + temp.getName());
				out.println("Faction " +temp.getFaction().name());
				out.println("Level req " + temp.getLevelRequirement());
				out.println("Weapon type " + temp.getWeaponType().name());
				out.println("Drop rate " + temp.getDropRate());
				out.println("Cost " + temp.getCost());
				out.println("Effects");
				
				for(String s : temp.getEffectMap().keySet()) {
					String INSERTSTATS = "INSERT INTO ITEMS_STATS (Items_Name,Items_Stats_Name,Items_Stats_Amt) VALUES ('" + temp.getName() + "', '" + s + "', " 
							+temp.getEffect(s)+")";
					statement.executeUpdate(INSERTSTATS);
					out.println(s + " :: "+ temp.getEffect(s));
				}
				out.println();
				  faction++;
				  if(faction == 7) {
					  faction = 0;
					  up++;
					  if(up == 4) {
						  count++;
						  up = 0;
					  }
				  }
			}
			out.close();
			
			
			count = 1;
			faction = 0;
			up = 0;
			names.clear();
			setnames.clear();
			fstream = new FileInputStream("armornames.txt");
			br = new BufferedReader(new InputStreamReader(fstream));
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				 setnames.add(strLine);
			  
			}
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			out = new PrintWriter(new FileWriter(new File("armorStats.txt"), true));
			for(String sd : names) {
				Item temp = generateItem(sd,faction,count,ItemType.Armor);
				temp.setName(temp.getName().replace("'", ""));
//				insert item
				String INSERTITEMS = "INSERT INTO ITEMS (Items_Name,Items_Faction,Items_Level_Req,Items_Type,Items_Cost,Items_Worth,Items_Drop_Rate,Items_SELLABLE)" +
				"VALUES ('" + temp.getName() + "', '" + temp.getFaction().name() +"', " + temp.getLevelRequirement() + ", '" + temp.getWeaponType().name() + "', " 
				+	temp.getCost() + ", " + temp.getWorth() + ", " + temp.getDropRate() + ", " + temp.isSellable() + ")";
				
				statement.executeUpdate(INSERTITEMS);
				
				//	insert item stats
				
				out.println("Name "  + temp.getName());
				out.println("Faction " +temp.getFaction().name());
				out.println("Level req " + temp.getLevelRequirement());
				out.println("Weapon type " + temp.getWeaponType().name());
				out.println("Drop rate " + temp.getDropRate());
				out.println("Cost " + temp.getCost());
				out.println("Effects");
				
				for(String s : temp.getEffectMap().keySet()) {
					String INSERTSTATS = "INSERT INTO ITEMS_STATS (Items_Name,Items_Stats_Name,Items_Stats_Amt) VALUES ('" + temp.getName() + "', '" + s + "', " 
							+temp.getEffect(s)+")";
					statement.executeUpdate(INSERTSTATS);
					out.println(s + " :: "+ temp.getEffect(s));
				}
				out.println();
				  faction++;
				  if(faction == 7) {
					  faction = 0;
					  up++;
					  if(up == 4) {
						  count++;
						  up = 0;
					  }
				  }
			}
			out.close();
			System.out.println("done");
			
			count = 1;
			faction = 0;
			up = 0;
			setnames.clear();
			fstream = new FileInputStream("bootnames.txt");
			br = new BufferedReader(new InputStreamReader(fstream));
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				 setnames.add(strLine);
			  
			}
			names.clear();
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			out = new PrintWriter(new FileWriter(new File("bootStats.txt"), true));
			for(String sd : names) {
				Item temp = generateItem(sd,faction,count,ItemType.Boots);
				temp.setName(temp.getName().replace("'", ""));
//				insert item
				String INSERTITEMS = "INSERT INTO ITEMS (Items_Name,Items_Faction,Items_Level_Req,Items_Type,Items_Cost,Items_Worth,Items_Drop_Rate,Items_SELLABLE)" +
				"VALUES ('" + temp.getName() + "', '" + temp.getFaction().name() +"', " + temp.getLevelRequirement() + ", '" + temp.getWeaponType().name() + "', " 
				+	temp.getCost() + ", " + temp.getWorth() + ", " + temp.getDropRate() + ", " + temp.isSellable() + ")";
				System.out.println(INSERTITEMS);
				statement.executeUpdate(INSERTITEMS);
				
				//	insert item stats
				
				out.println("Name "  + temp.getName());
				out.println("Faction " +temp.getFaction().name());
				out.println("Level req " + temp.getLevelRequirement());
				out.println("Weapon type " + temp.getWeaponType().name());
				out.println("Drop rate " + temp.getDropRate());
				out.println("Cost " + temp.getCost());
				out.println("Effects");
				
				for(String s : temp.getEffectMap().keySet()) {
					String INSERTSTATS = "INSERT INTO ITEMS_STATS (Items_Name,Items_Stats_Name,Items_Stats_Amt) VALUES ('" + temp.getName() + "', '" + s + "', " 
							+temp.getEffect(s)+")";
					statement.executeUpdate(INSERTSTATS);
					out.println(s + " :: "+ temp.getEffect(s));
				}
				out.println();
				  faction++;
				  if(faction == 7) {
					  faction = 0;
					  up++;
					  if(up == 4) {
						  count++;
						  up = 0;
					  }
				  }
			}
			out.close();
			System.out.println("done");
			
			count = 1;
			faction = 0;
			up = 0;
			names.clear();
			setnames.clear();
			fstream = new FileInputStream("weaponnames.txt");
			br = new BufferedReader(new InputStreamReader(fstream));
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				 setnames.add(strLine);
			  
			}
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			out = new PrintWriter(new FileWriter(new File("weaponStats.txt"), true));
			for(String sd : names) {
				Item temp = generateItem(sd,faction,count,ItemType.Wand);
				temp.setName(temp.getName().replace("'", ""));
//				insert item
				String INSERTITEMS = "INSERT INTO ITEMS (Items_Name,Items_Faction,Items_Level_Req,Items_Type,Items_Cost,Items_Worth,Items_Drop_Rate,Items_SELLABLE)" +
				"VALUES ('" + temp.getName() + "', '" + temp.getFaction().name() +"', " + temp.getLevelRequirement() + ", '" + temp.getWeaponType().name() + "', " 
				+	temp.getCost() + ", " + temp.getWorth() + ", " + temp.getDropRate() + ", " + temp.isSellable() + ")";
				
				statement.executeUpdate(INSERTITEMS);
				
				//	insert item stats
				
				out.println("Name "  + temp.getName());
				out.println("Faction " +temp.getFaction().name());
				out.println("Level req " + temp.getLevelRequirement());
				out.println("Weapon type " + temp.getWeaponType().name());
				out.println("Drop rate " + temp.getDropRate());
				out.println("Cost " + temp.getCost());
				out.println("Effects");
				
				for(String s : temp.getEffectMap().keySet()) {
					String INSERTSTATS = "INSERT INTO ITEMS_STATS (Items_Name,Items_Stats_Name,Items_Stats_Amt) VALUES ('" + temp.getName() + "', '" + s + "', " 
							+temp.getEffect(s)+")";
					statement.executeUpdate(INSERTSTATS);
					out.println(s + " :: "+ temp.getEffect(s));
				}
				out.println();
				  faction++;
				  if(faction == 7) {
					  faction = 0;
					  up++;
					  if(up == 4) {
						  count++;
						  up = 0;
					  }
				  }
			}
			out.close();
			System.out.println("done");
			
			count = 1;
			faction = 0;
			 up = 0;
			names.clear();
			setnames.clear();
			fstream = new FileInputStream("decknames.txt");
			br = new BufferedReader(new InputStreamReader(fstream));
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				 setnames.add(strLine);
			  
			}
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			out = new PrintWriter(new FileWriter(new File("deckStats.txt"), true));
			for(String sd : names) {
				Item temp = generateItem(sd,faction,count,ItemType.Deck);
				temp.setName(temp.getName().replace("'", ""));
//				insert item
				String INSERTITEMS = "INSERT INTO ITEMS (Items_Name,Items_Faction,Items_Level_Req,Items_Type,Items_Cost,Items_Worth,Items_Drop_Rate,Items_SELLABLE)" +
				"VALUES ('" + temp.getName() + "', '" + temp.getFaction().name() +"', " + temp.getLevelRequirement() + ", '" + temp.getWeaponType().name() + "', " 
				+	temp.getCost() + ", " + temp.getWorth() + ", " + temp.getDropRate() + ", " + temp.isSellable() + ")";
				
				statement.executeUpdate(INSERTITEMS);
				
				//	insert item stats
				
				out.println("Name "  + temp.getName());
				out.println("Faction " +temp.getFaction().name());
				out.println("Level req " + temp.getLevelRequirement());
				out.println("Weapon type " + temp.getWeaponType().name());
				out.println("Drop rate " + temp.getDropRate());
				out.println("Cost " + temp.getCost());
				out.println("Effects");
				
				for(String s : temp.getEffectMap().keySet()) {
					String INSERTSTATS = "INSERT INTO ITEMS_STATS (Items_Name,Items_Stats_Name,Items_Stats_Amt) VALUES ('" + temp.getName() + "', '" + s + "', " 
							+temp.getEffect(s)+")";
					statement.executeUpdate(INSERTSTATS);
					out.println(s + " :: "+ temp.getEffect(s));
				}
				out.println();
				  faction++;
				  if(faction == 7) {
					  faction = 0;
					  up++;
					  if(up == 4) {
						  count++;
						  up = 0;
					  }
				  }
			}
			out.close();
			System.out.println("done");
			
			count = 1;
			faction = 0;
			up = 0;
			names.clear();
			setnames.clear();
			fstream = new FileInputStream("ringnames.txt");
			br = new BufferedReader(new InputStreamReader(fstream));
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				 setnames.add(strLine);
			  
			}
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			out = new PrintWriter(new FileWriter(new File("ringStats.txt"), true));
			for(String sd : names) {
				Item temp = generateItem(sd,faction,count,ItemType.Ring);
				temp.setName(temp.getName().replace("'", ""));
//				insert item
				String INSERTITEMS = "INSERT INTO ITEMS (Items_Name,Items_Faction,Items_Level_Req,Items_Type,Items_Cost,Items_Worth,Items_Drop_Rate,Items_SELLABLE)" +
				"VALUES ('" + temp.getName() + "', '" + temp.getFaction().name() +"', " + temp.getLevelRequirement() + ", '" + temp.getWeaponType().name() + "', " 
				+	temp.getCost() + ", " + temp.getWorth() + ", " + temp.getDropRate() + ", " + temp.isSellable() + ")";
				System.out.println(INSERTITEMS);
				statement.executeUpdate(INSERTITEMS);
				
				//	insert item stats
				
				out.println("Name "  + temp.getName());
				out.println("Faction " +temp.getFaction().name());
				out.println("Level req " + temp.getLevelRequirement());
				out.println("Weapon type " + temp.getWeaponType().name());
				out.println("Drop rate " + temp.getDropRate());
				out.println("Cost " + temp.getCost());
				out.println("Effects");
				
				for(String s : temp.getEffectMap().keySet()) {
					String INSERTSTATS = "INSERT INTO ITEMS_STATS (Items_Name,Items_Stats_Name,Items_Stats_Amt) VALUES ('" + temp.getName() + "', '" + s + "', " 
							+temp.getEffect(s)+")";
					statement.executeUpdate(INSERTSTATS);
					out.println(s + " :: "+ temp.getEffect(s));
				}
				out.println();
				  faction++;
				  if(faction == 7) {
					  faction = 0;
					  up++;
					  if(up == 4) {
						  count++;
						  up = 0;
					  }
				  }
			}
			out.close();
			System.out.println("done");
			
			count = 1;
			faction = 0;
			up = 0;
			names.clear();
			setnames.clear();
			fstream = new FileInputStream("athamenames.txt");
			br = new BufferedReader(new InputStreamReader(fstream));
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				 setnames.add(strLine);
			  
			}
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			out = new PrintWriter(new FileWriter(new File("athameStats.txt"), true));
			for(String sd : names) {
				Item temp = generateItem(sd,faction,count,ItemType.Athame);
				temp.setName(temp.getName().replace("'", ""));
//				insert item
				String INSERTITEMS = "INSERT INTO ITEMS (Items_Name,Items_Faction,Items_Level_Req,Items_Type,Items_Cost,Items_Worth,Items_Drop_Rate,Items_SELLABLE)" +
				"VALUES ('" + temp.getName() + "', '" + temp.getFaction().name() +"', " + temp.getLevelRequirement() + ", '" + temp.getWeaponType().name() + "', " 
				+	temp.getCost() + ", " + temp.getWorth() + ", " + temp.getDropRate() + ", " + temp.isSellable() + ")";
				System.out.println(INSERTITEMS);
				statement.executeUpdate(INSERTITEMS);
				
				//	insert item stats
				
				out.println("Name "  + temp.getName());
				out.println("Faction " +temp.getFaction().name());
				out.println("Level req " + temp.getLevelRequirement());
				out.println("Weapon type " + temp.getWeaponType().name());
				out.println("Drop rate " + temp.getDropRate());
				out.println("Cost " + temp.getCost());
				out.println("Effects");
				
				for(String s : temp.getEffectMap().keySet()) {
					String INSERTSTATS = "INSERT INTO ITEMS_STATS (Items_Name,Items_Stats_Name,Items_Stats_Amt) VALUES ('" + temp.getName() + "', '" + s + "', " 
							+temp.getEffect(s)+")";
					statement.executeUpdate(INSERTSTATS);
					out.println(s + " :: "+ temp.getEffect(s));
				}
				out.println();
				  faction++;
				  if(faction == 7) {
					  faction = 0;
					  up++;
					  if(up == 4) {
						  count++;
						  up = 0;
					  }
				  }
			}
			out.close();
			System.out.println("done");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			statement.executeUpdate("DELETE FROM ITEMS_STATS WHERE ITEMS_NAME != 'Starter_Helmet'");
			statement.executeUpdate("DELETE FROM ITEMS WHERE ITEMS_NAME != 'Starter_Helmet' AND ITEMS_NAME != 'Starter_Deck'");
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
	
	private static Character generateMob(String name, int faction, int scale,boolean boss) {
		// TODO Auto-generated method stub
		//	load spells
		List<Spell> spells = new ArrayList<>();
		List<String> tempspells = FactionTypes.loadStarterSpells(FactionTypes.loadClass(faction).name());
		try {
			spells.addAll(DatabaseLoader.loadSpells(faction,scale,boss));
			spells.addAll(DatabaseAdapter.LoadSpells(tempspells));
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//	set hp
		Random rand = new Random();
		int hp = 100;
		if(boss) {
			hp += rand.nextInt(500*scale);
		}
		else {
			hp += rand.nextInt(100*scale);
		}
		
		switch(FactionTypes.loadClass(faction)) {
		case Generic: hp*=.6;
			break;
		case The_Called: hp*=.8;
			break;
		case The_Children_Of_The_Mask:hp*=.7;
			break;
		case The_Collective:hp*=1.0;
			break;
		case The_Cult_Of_The_Stars:hp*=1.1;
			break;
		case The_Ennead:hp*=1.2;
			break;
		case The_Legion:hp*=1.5;
			break;
		case The_Ritualists:hp*=.9;
			break;
		default:
			break;
		}
		//	sets deck, hp, name, faction
		EnemyCharacter temp = new EnemyCharacter(spells,hp,name,FactionTypes.loadClass(faction));
		
		//	sets rank
		temp.setRank(scale);
		
		//	set xp and gold payout
		
		temp.setGold(rand.nextInt(scale*75));
		temp.setXP(rand.nextInt(scale*75));
		
		//	load drops
		if(boss) {
			try {
				temp.setDropMap(DatabaseLoader.loadDrops(true,scale,temp.getCharClass()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				temp.setDropMap(DatabaseLoader.loadDrops(false,scale,temp.getCharClass()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//	load effects
		temp.setStats(new Stats());
		if(boss) {
			temp.getStats().setEffectMap(DatabaseLoader.loadStats(true,scale,temp.getCharClass()));
		}else {
			temp.getStats().setEffectMap(DatabaseLoader.loadStats(false,scale,temp.getCharClass()));
		}
		
		return temp;
	}
	private static List<Spell> loadSpells(int fact, int scale, boolean boss) throws Exception{
		// TODO Auto-generated method stub
		//	if boss grab next 2 stages of spells
		//	if mob grab next 1 stage
		List<Spell> spells = new ArrayList<>();
		Connection dbConnection = null;
		Statement statement = null;
		String selectTableSQL = "SELECT * from SPELLS WHERE Spells_Rank <= " + scale + " AND Spells_Faction= '" + FactionTypes.loadString(fact) +"'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
				if (rs.next() == false) {
					System.out.println("ResultSet in empty in Java");
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
							List<String> targetFactions = Arrays.asList(rs.getString("Spells_Target_Factions").split("|"));
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
							break;
						case Heal:
							temp.makeHealSpell(faction, name, castChance, heal);
							spells.add(temp);
							break;
						case Heal_ALL:
							break;
						case Shield:
							temp.makeShieldSpell(faction, name, resist,targets);
							spells.add(temp);
							break;
						case Shield_ALL:
							break;
						case Blade:
							temp.makeBladeSpell(faction, name, boost, targets);
							spells.add(temp);
							break;
						case Blade_ALL:
							break;
						case Trap:
							temp.makeTrapSpell(faction, name, boost, targets);
							spells.add(temp);
							break;
						case Trap_ALL:
							break;
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
		return spells;
	}
	private static Map<String, Double> loadStats(boolean boss, int scale,FactionTypes faction) {
		// TODO Auto-generated method stub
		//	generate resist to self, damage boost and crit and crit block
		//	if boss boost stats. if scale high enough generate random other resists 
		Map<String,Double> effects = Stats.loadNewStatsMap();
		
		if(boss) {
			effects.replace(faction.name()+"_CRIT",effects.get(faction.name()+"_CRIT")+scale*0.001);
			effects.replace(faction.name()+"_DMG",effects.get(faction.name()+"_DMG")+scale*0.001);
			effects.replace(faction.name()+"_RESIST",effects.get(faction.name()+"_DMG")+scale*0.001);
			effects.replace(faction.name()+"_CRIT_BLOCK",effects.get(faction.name()+"_CRIT_BLOCK")+scale*0.001);
		}
		else if(scale > 50){
			effects.replace(faction.name()+"_CRIT",effects.get(faction.name()+"_CRIT")+scale*0.001);
			effects.replace(faction.name()+"_DMG",effects.get(faction.name()+"_DMG")+scale*0.001);
			effects.replace(faction.name()+"_RESIST",effects.get(faction.name()+"_DMG")+scale*0.001);
			effects.replace(faction.name()+"_CRIT_BLOCK",effects.get(faction.name()+"_CRIT_BLOCK")+scale*0.001);
		}
		return effects;
	}
	private static Map<String, Double> loadDrops(boolean boss, int scale,FactionTypes faction) throws Exception{
		// TODO Auto-generated method stub
		Map<String,Double> drops = new HashMap<>();
		//	if boss grab next 2 stages of drops
		Connection dbConnection = null;
		Statement statement = null;
		String selectTableSQL = "SELECT * from ITEMS WHERE Items_Level_Req <= " + scale + " AND Items_Level_Req >= " + (scale-1);
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				System.out.println("ResultSet in empty in Java");
			} else {
				do {
					String name = rs.getString("Items_Name");
					drops.put(name, rs.getDouble("Items_Drop_Rate"));
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
		Map<String,Double> dropMap = new HashMap<>();
		if(boss) {
			return drops;
		}
		else {
			int i = 0;
			for(String s : drops.keySet()) {
				if(i < 5) {
					dropMap.put(s, drops.get(s));
				}
				i++;
			}
		}
		return dropMap;
	}
	private static Quest generateQuest(String title, String nextQuest,int count) {
		// TODO Auto-generated method stub
		Quest temp = new Quest();
		temp.setTitle(title);
		if(nextQuest!= null) {
			List<String> next = new ArrayList<>();
			next.add(nextQuest);
			temp.setNextQuests(next);
		}
		//	generate gold and xp 
		Random rand = new  Random();
		
		int gold = 100;
		gold += rand.nextInt(count*100);
		temp.setGold(gold);
		
		int xp = 150;
		xp += rand.nextInt(count*150);
		temp.setXp(xp);
		
		List<String> enemies = new ArrayList<>();
		
		if(count%3==0) {
			//	do boss
			try {
				enemies.addAll(DatabaseLoader.loadBoss(count));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				enemies.addAll(DatabaseLoader.loadMobs(count,4));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		temp.setEnemies(enemies);
		//	generate mobs and bosses
		List<String> items = new ArrayList<>();
		try {
			items.addAll(DatabaseLoader.loadItems(count));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> rewards = new ArrayList<>();
		for(int i  = 0; i < items.size() && i < 2;i++) {
			rewards.add(items.get(i));
		}
		temp.setRewards(rewards);
		return temp;
	}
	private static List<String> loadItems(int count) throws Exception{
		// TODO Auto-generated method stub
		List<String> items = new ArrayList<>();
		double percentDrop = count*0.01;
		//	get a random item based on scale 
		if(GameRules.accuracy(percentDrop)) {
			Connection dbConnection = null;
			Statement statement = null;
			String selectTableSQL = "SELECT * from ITEMS WHERE Items_Level_Req <= " + count + " AND Items_Level_Req >= " + (count-1);
			try {
				dbConnection = getDBConnection();
				statement = dbConnection.createStatement();
				System.out.println(selectTableSQL);
				// execute select SQL stetement
				ResultSet rs = statement.executeQuery(selectTableSQL);
				if (rs.next() == false) {
					System.out.println("ResultSet in empty in Java");
				} else {
					do {
						String name = rs.getString("Items_Name");
						items.add(name);
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
		return items;
	}
	private static List<String> loadMobs(int count,int num) throws SQLException {
		// TODO Auto-generated method stub
		List<String> enemies = new ArrayList<>();
		Connection dbConnection = null;
		Statement statement = null;
		String selectTableSQL = "SELECT * from ENEMIES WHERE ENEMY_RANK= " + count + " AND ENEMY_IS_BOSS= 'false'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				System.out.println("ResultSet in empty in Java");
			} else {
				do {
					enemies.add(rs.getString("Enemy_Name"));
					num--;
				}while(rs.next() && num > 0);
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
		
		return enemies;
	}
	private static List<String> loadBoss(int count) throws SQLException {
		// TODO Auto-generated method stub
		List<String> enemies = new ArrayList<>();
		//	load boss
		Connection dbConnection = null;
		Statement statement = null;
		List<String> results = new ArrayList<>();
		String selectTableSQL = "SELECT * from ENEMIES WHERE ENEMY_RANK>= " + (count-1) + "AND ENEMY_RANK<= " + (count+1) + " AND ENEMY_IS_BOSS= 'TRUE'";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				System.out.println("ResultSet in empty in Java");
			} else {
				do {
					results.add(rs.getString("Enemy_Name"));
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
		Random rand = new Random();
		if(results.size()> 0) {
			enemies.add(results.get(rand.nextInt(results.size()-1)));
		}
		
		//	load mobs
		
		try {
			enemies.addAll(DatabaseLoader.loadMobs(count, rand.nextInt(3)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enemies;
	}
	
	public static void storeBosses() throws Exception{
		Connection dbConnection = null;
		Statement statement = null;
		List<String> names = new ArrayList<>();
		Set<String> setnames = new HashSet<>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// Open the file
			FileInputStream fstream = new FileInputStream("mobnames.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				setnames.add(strLine);
			}
			//////////////////////////////////////
			//	load mobs
			int count = 1;
			int faction = 0;
			int up = 0;
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			for(String sd : names) {
				Character temp = generateMob(sd,faction,count,false);
				
				faction++;
				if(faction == 7) {
					faction = 0;
					up++;
					if(up == 4) {
					  count++;
					  up = 0;
					}
				}
				//	enemy table
				String INSERTSQLTABLE = "INSERT INTO ENEMIES (ENEMY_NAME,ENEMY_FACTION,ENEMY_RANK,ENEMY_MAXHP,ENEMY_IS_BOSS,ENEMY_GOLD,ENEMY_XP) "
						+ "VALUES ('" + temp.getName() + "', '" + temp.getCharClass().name() + "', " + ((EnemyCharacter) temp).getRank()+", " + temp.getMaxHP() + ", FALSE, "+ ((EnemyCharacter) temp).getGold() + ", " + ((EnemyCharacter) temp).getXp() + ")";
				
				
				System.out.println(INSERTSQLTABLE);
				
				statement.executeUpdate(INSERTSQLTABLE);
				
				//	insert drops
				for(String s : ((EnemyCharacter) temp).getDropMap().keySet()) {
					String insertDrops = "INSERT INTO ENEMY_DROPS (ENEMY_NAME,DROP_NAME,DROP_RATE) "
							+ "VALUES ('" + temp.getName() + "', '" + s + "', " + ((EnemyCharacter) temp).getDropMap().get(s) + ")";
					System.out.println(insertDrops);
					statement.executeUpdate(insertDrops);
				}
				//	enemy deck table
				for(Card s : temp.getDeck().getCards()) {
					String INSERTDECKTABLE = "INSERT INTO ENEMY_DECK (Enemy_Name,Spell_Name,Spell_Count) VALUES ('"+
							temp.getName() +"', '" + s.getSpell().getName()+"', " + 1+")";
					
					System.out.println(INSERTDECKTABLE);
					statement.executeUpdate(INSERTDECKTABLE);
				}
				
				//	enemy stats table
				for(String s : temp.getStats().getEffectMap().keySet()) {
					String INSERTSTATSTABLE = "INSERT INTO ENEMY_STATS (Enemy_Name,Enemy_Stats_Name,Enemy_Stats_Amt) VALUES ('" +
							temp.getName() + "', '" + s + "', " + temp.getStats().getEffect(s) + ")";
					System.out.println(INSERTSTATSTABLE);
					statement.executeUpdate(INSERTSTATSTABLE);
				}
			}
			System.out.println("done");
			//Close the input stream

			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("resetting db");
			statement.executeUpdate("DELETE FROM ENEMY_DROPS");
			statement.executeUpdate("DELETE FROM ENEMY_DECK");
			statement.executeUpdate("DELETE FROM ENEMY_STATS");
			statement.executeUpdate("DELETE FROM ENEMIES");
			
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
	public static void updateMobDecks() throws Exception{
		Connection dbConnection = null;
		Statement statement = null;
		List<Spell> spells = new ArrayList<>();
		String selectTableSQL ="";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// execute select SQL stetement
			String searchBoss = "SELECT * FROM ENEMIES";
			ResultSet rs = statement.executeQuery(searchBoss);
			List<Character>enemyNames = new ArrayList<>();
			if (rs.next() == false) {
				System.out.println("ResultSet in empty in Java");
			} else {
				do {
					Character temp = new EnemyCharacter();
					temp.setName(rs.getString("Enemy_Name"));
					((EnemyCharacter) temp).setRank(rs.getInt("Enemy_Rank"));
					temp.setCharClass(FactionTypes.loadClass(rs.getString("Enemy_Faction")));
					enemyNames.add(temp);
				} while (rs.next());
			}
			List<String> insertSpells = new ArrayList<>();
			for(Character c : enemyNames) {
				String searchSpells = "SELECT * FROM SPELLS WHERE SPELLS_RANK<= " + ((EnemyCharacter) c).getRank() 
						+ " AND SPELLS_FACTION= '" + c.getCharClass().name() + "'";
				rs = statement.executeQuery(searchSpells);
				if (rs.next() == false) {
					System.out.println("ResultSet in empty in Java");
				} else {
					do {
						String insertEnemyDeck = "INSERT INTO ENEMY_DECK (Enemy_Name,Spell_Name,Spell_Count) " +
								"VALUES ('" + c.getName() + "', '" + rs.getString("Spells_Name") + "', " + 1 + ")";
						insertSpells.add(insertEnemyDeck);
					} while (rs.next());
				}
			}
			for(String s : insertSpells) {
				statement.executeUpdate(s);
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
	public static void storeQuests() throws Exception{
		Connection dbConnection = null;
		Statement statement = null;
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			// Open the file
			FileInputStream fstream = new FileInputStream("questnames.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			List<String> names = new ArrayList<>();
			Set<String> setnames = new HashSet<>();
			String strLine;

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  System.out.println (strLine);
			  setnames.add(strLine);
			}
			int count = 0;
			int faction = 0;
			int up = 1;
			names.addAll(setnames);
			Collections.shuffle(names);
			//Close the input stream
			fstream.close();
			for(String sd : names) {
				if(names.get(count)!= null) {
					boolean valid = true;
					try {
						names.get(count+1);
					}
					catch (Exception e){
						valid = false;
					}
					if(valid) {
						if(count%7==0) {
							up++;
						}
						Quest temp = generateQuest(sd,names.get(count+1),up);
						String INSERTQUESTSTABLE = "INSERT INTO QUESTS (Quests_ENEMIES,Quests_Next_Quest,Quests_Item_Drops,Quests_Gold,Quests_XP,Quests_Name) VALUES ('";
						for(String s : temp.getEnemies()) {
							INSERTQUESTSTABLE = INSERTQUESTSTABLE.concat(s + "::");
						}
						INSERTQUESTSTABLE =INSERTQUESTSTABLE.concat("', '");
						for(String s : temp.getNextQuests()) {
							INSERTQUESTSTABLE =INSERTQUESTSTABLE.concat(s + "::");
						}
						INSERTQUESTSTABLE =INSERTQUESTSTABLE.concat("', '");
						for(String s : temp.getRewards()) {
							INSERTQUESTSTABLE =INSERTQUESTSTABLE.concat(s + "::");
						}
						INSERTQUESTSTABLE =INSERTQUESTSTABLE.concat("', ");
						INSERTQUESTSTABLE =INSERTQUESTSTABLE.concat(String.valueOf(temp.getGold()) + ", ");
						INSERTQUESTSTABLE =INSERTQUESTSTABLE.concat(String.valueOf(temp.getXp()) + ", '");
						INSERTQUESTSTABLE =INSERTQUESTSTABLE.concat(temp.getTitle() + "')");
						count++;
						System.out.println(INSERTQUESTSTABLE);
						statement.executeUpdate(INSERTQUESTSTABLE);
					}
					
				}
				
			}
			//Close the input stream
			fstream.close();

			
			
		} catch (Exception e) {
			e.printStackTrace();
			statement.executeUpdate("DELETE FROM QUESTS");
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
	public static void storeSpells() throws Exception{
		
		Connection dbConnection = null;
		Statement statement = null;
		
		FileInputStream fstream = new FileInputStream("SupportSpellStats.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		//Read File Line By Line
		//	skip first line
		br.readLine();
		String selectTableSQL = "";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			while ((strLine = br.readLine()) != null)   {
				  // Print the content on the console
					String [] arr = strLine.split(",");
					String name = arr[0].trim();
					String fact = arr[1].trim();
					String type = arr[2].trim();
					int cost = Integer.parseInt(arr[3]);
					double cast = Double.parseDouble(arr[4]);
					double heal = Double.parseDouble(arr[5]);
					double dmg = Double.parseDouble(arr[6]);
					double resist = Double.parseDouble(arr[7]);
					double boost = Double.parseDouble(arr[8]);
					String targets = arr[9];
					int level = Integer.parseInt(arr[10]);
					
					selectTableSQL = "INSERT INTO SPELLS (Spells_Name,Spells_Faction,Spells_Type,Spells_Pip_Cost,Spells_Cast_Chance,Spells_Heal_Amt,Spells_DMG_Amt,Spells_Resist_Amt,Spells_Boost_Amt,Spells_Target_Factions,Spells_Rank)"
							+ "VALUES ('" + name + "', '"+fact+"', '" + type + "', " + cost + ", " + cast + ", " + heal + ", " 
							+ dmg + ", " + resist + ", " + boost + ", '" + targets + "', " + level + ")";
					System.out.println(selectTableSQL);
					
					//	uncomment to insert into table
					statement.executeUpdate(selectTableSQL);
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
		
		fstream.close();
	}
}
