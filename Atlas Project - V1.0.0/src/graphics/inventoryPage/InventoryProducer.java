package graphics.inventoryPage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gameRules.GameRules;
import items.Item;
import items.Item.ItemType;

public class InventoryProducer {
	public static final String BOOTS = "boots";
	public static final String WAND = "wand";
	public static final String HELMET = "helmet";
	public static final String ARMOR = "armor";
	public static final String DECK = "deck";
	public static final String RING = "ring";
	public static final String ATHAME = "athame";
	private static int sizeMultiplier;
	private static InventoryPageController pageController;
	public static JPanel getPanel(String panelname,JPanel itemPane,InventoryPageController c) {
		pageController = c;
		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new GridLayout(0,1));
		//	backpack panel
		sizeMultiplier  = 1;
		for(Item i : GameRules.activePlayer.getBackpack().getItems()) {
			switch(panelname) {
			case ARMOR: if(i.getWeaponType().equals(ItemType.Armor)) {
				addButtons(itemPanel,i);
						}
			break;
			case BOOTS: if(i.getWeaponType().equals(ItemType.Boots)) {
				addButtons(itemPanel,i);
						}
				break;
			case WAND:if(i.getWeaponType().equals(ItemType.Wand)) {
							addButtons(itemPanel,i);
						}
				break;
			case HELMET:if(i.getWeaponType().equals(ItemType.Helmet)) {
				addButtons(itemPanel,i);
						}
				break;
			case DECK:if(i.getWeaponType().equals(ItemType.Deck)) {
				addButtons(itemPanel,i);
						}
				break;
			case RING:if(i.getWeaponType().equals(ItemType.Ring)) {
				addButtons(itemPanel,i);
						}
				break;
			case ATHAME: if(i.getWeaponType().equals(ItemType.Athame)) {
				addButtons(itemPanel,i);
						}
				break;
			}
			
		}
		if(sizeMultiplier > 1) {
			sizeMultiplier--;
		}
		while(itemPanel.getComponentCount()<10) {
			itemPanel.add(new JLabel());
		}
		itemPanel.setPreferredSize(new Dimension(200,100*sizeMultiplier));
		JScrollPane itemScrollPane = new JScrollPane(itemPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		itemScrollPane.setOpaque(false);
		itemScrollPane.setPreferredSize(new Dimension(200,400));
		itemScrollPane.setSize(new Dimension(200,400));
		JPanel mainHelmetPanel = new JPanel();
		mainHelmetPanel.setLayout(new GridLayout(1,2));
		mainHelmetPanel.add(itemScrollPane);
		itemPane.setLayout(new GridLayout(0,1));
		itemPane.setPreferredSize(new Dimension(200,400));
		itemPane.setSize(new Dimension(200,400));
		mainHelmetPanel.add(itemPane);

		return mainHelmetPanel;
	}
	public static void addButtons(JPanel tempPanel,Item i) {
		sizeMultiplier++;
		JButton temp = new JButton();
		temp.setLayout(new GridLayout(3,1));
		temp.setName(i.getName() + "::" + i.getWeaponType());
		temp.add(new JLabel(i.getName()));
		temp.add(new JLabel("\n Faction: " + i.getFaction().name()));
		temp.add(new JLabel("\nLvl: " + i.getLevelRequirement()));
		
		//temp.setOpaque(false);
		//temp.setContentAreaFilled(false);
		temp.setActionCommand("ItemSelected");
		temp.addActionListener(pageController);
		temp.setPreferredSize(new Dimension(100,75));
		tempPanel.add(temp);
	}
}
