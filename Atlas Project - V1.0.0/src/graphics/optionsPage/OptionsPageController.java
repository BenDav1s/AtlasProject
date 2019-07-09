package graphics.optionsPage;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import graphics.GraphicsController;
import graphics.PageController;
import graphics.PageCreator;
import resources.BackgroundMusic;

public class OptionsPageController extends PageController{
	private OptionsPageView view;
	private static final String VOLUME = "volume";
	private static final String BACK = "back";
	private static final String AUDIO = "audio";
	private static final String CHANGE = "change";
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		view = new OptionsPageView(this,mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case BACK:GraphicsController.processPage(PageCreator.HOME_PAGE, PageCreator.OPTIONS);
			break;
		case VOLUME:
			BackgroundMusic.getInstance().music();
			JButton temp = view.getSound();
			if(BackgroundMusic.isPlaying()) {
				temp.setText("<HTML><U>Turn Music Off</U></HTML>");
			}else {
				temp.setText("<HTML><U>Turn Music On</U></HTML>");
			}
			view.setSound(temp);
			break;
		case AUDIO:
			GraphicsController.processPage(PageCreator.MUSIC_PAGE, PageCreator.OPTIONS);
			break;
		case CHANGE:
			GraphicsController.processPage(PageCreator.CHARACTER_SELECTION, PageCreator.OPTIONS);
			break;
		}
	}

}
