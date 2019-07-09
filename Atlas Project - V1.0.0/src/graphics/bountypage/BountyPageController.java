package graphics.bountypage;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import gameRules.GameRules;
import graphics.GraphicsController;
import graphics.PageController;
import graphics.PageCreator;
import graphics.characterSelectionPage.CharacterSelectionView;

public class BountyPageController extends PageController{
	private BountyPageView view;
	public static final String BACK = "back";
	public static final String SUBMIT = "submit";
	public static final String EXIT = "exit";
	public static final String NEXT = "next";
	public static final String SELECT = "bossSelected";
	public static final String FIGHT = "fight";
	public static int current = 0;
	public static String previouspage;
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		previouspage=  back;
		view = new BountyPageView(this,mainFrame);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()==BACK) {
			GraphicsController.processPage(PageCreator.HOME_PAGE, null);
		}
		else if (e.getActionCommand() == SELECT) {
			view.setPreviewPanel(PreviewPanelGenerator.generatePreviewPanel(((JButton) e.getSource()).getText(), this, view.getFrame()));
			view.getPanel().removeAll();
			JScrollPane listScroller = new JScrollPane(view.getListPanel(),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			listScroller.setOpaque(false);
			listScroller.setPreferredSize(new Dimension(200,600));
			view.getPanel().add(listScroller);
			view.getPanel().add(view.getPreviewPanel());
			view.getPanel().add(view.getBackButton());
			
			view.getPanel().revalidate();
			view.getPanel().repaint();
			view.getFrame().revalidate();
			view.getFrame().repaint();
		}
		else if (e.getActionCommand()==FIGHT) {
			GameRules.activePlayer.setActiveBounty(((JButton) e.getSource()).getName());
			GraphicsController.processPage(PageCreator.COMBAT_PAGE, PageCreator.Bounty_Page);
		}
	}
}
