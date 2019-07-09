package graphics.bountypage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import graphicTools.Colors;
import resources.BackgroundPanel;
import resources.CustomCursor;

public class BountyPageView {
	private BountyPageController controller;
	private JFrame frame;
	private JPanel panel;
	private JPanel listPanel;
	private JPanel previewPanel;
	private JButton backBtn;
	
	public JButton getBackButton() {
		return this.backBtn;
	}
	public void setBackButton(JButton back) {
		this.backBtn = back;
	}
	public BountyPageController getController() {
		return controller;
	}

	public void setController(BountyPageController controller) {
		this.controller = controller;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JPanel getListPanel() {
		return listPanel;
	}

	public void setListPanel(JPanel listPanel) {
		this.listPanel = listPanel;
	}

	public JPanel getPreviewPanel() {
		return previewPanel;
	}

	public void setPreviewPanel(JPanel previewPanel) {
		this.previewPanel = previewPanel;
	}

	public BountyPageView(BountyPageController bountyPageController, JFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.controller = bountyPageController;
		this.frame = mainFrame;
		
		//	init panel
		BackgroundPanel background = new BackgroundPanel("loginpage");
		
		this.panel = background;
		this.frame.setCursor(CustomCursor.getCursor());
		this.panel.setPreferredSize(new Dimension(1080, 720));
		this.frame.setContentPane(this.panel);
		this.frame.revalidate();
		
		GridLayout masterLayout = new GridLayout(1,3);
		masterLayout.setHgap(5);
		masterLayout.setVgap(5);
		this.panel.setLayout(masterLayout);
		//	list of bosses | boss preview with info
		//	sort alphabetically, by faction, level in combo box
		
		this.listPanel = ListPanelGenerator.generateListPanel(this.controller,this.frame);
		JScrollPane listScroller = new JScrollPane(listPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setOpaque(false);
		listScroller.setPreferredSize(new Dimension(200,600));
		
		this.previewPanel = new JPanel();
		this.previewPanel.setOpaque(false);
		
		this.panel.add(listScroller);
		this.panel.add(previewPanel);
		
		
		//	back button 
		
		backBtn  = new JButton("Back");
		backBtn.setText("<HTML><U>Back</U></HTML>");
		backBtn.setPreferredSize(new Dimension(200,50));
		backBtn.setContentAreaFilled(false);
		backBtn.setFont(graphicTools.Fonts.getBoldFont(14f));
		backBtn.setForeground(Colors.Yellow);
		backBtn.setActionCommand("back");
		backBtn.addActionListener(controller);
		
		this.panel.add(backBtn);
		
		this.frame.pack();
		this.frame.setVisible(true);
		
	}

}
