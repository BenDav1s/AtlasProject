package graphics.musicPage;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import graphics.GraphicsController;
import graphics.PageController;
import graphics.PageCreator;
import resources.BackgroundMusic;

public class MusicPageController extends PageController{

	private static final String BACK = "back";
	private static final String VOLUME = "volume";
	private static final String ADD = "add";
	private static final String SELECT = "select";
	private MusicPageView view;
	@Override
	public void launchPage(JFrame mainFrame, String back) {
		// TODO Auto-generated method stub
		view = new MusicPageView(this,mainFrame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()) {
		case BACK:GraphicsController.processPage(PageCreator.OPTIONS, PageCreator.MUSIC_PAGE);
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
		case ADD:
			//load images
			JFileChooser fc = new JFileChooser();
			FileFilter imageFilter = new FileNameExtensionFilter(
					"mp3 & wav Images", "wav", "mp3");
			
			fc.addChoosableFileFilter(imageFilter);
			fc.setAcceptAllFileFilterUsed(false);
			fc.setCurrentDirectory(new java.io.File("."));
			
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				System.out.println(fc.getSelectedFile().getAbsolutePath());
				System.out.println(fc.getSelectedFile().getName());
				BackgroundMusic.getSongList().put(fc.getSelectedFile().getName(),fc.getSelectedFile().getAbsolutePath());
				JButton btn = new JButton();
				btn.setName(fc.getSelectedFile().getName());
				btn.setText(fc.getSelectedFile().getName() + " :: play" );
				btn.setActionCommand("select");
				btn.addActionListener(this);
				btn.setPreferredSize(new Dimension(200,50));
				btn.setContentAreaFilled(false);
				btn.setFont(graphicTools.Fonts.getFont(14f));
				btn.setForeground(graphicTools.Colors.White);
				view.getScrollBackground().add(btn);
				view.getScrollBackground().revalidate();
				/*
				try {
					Files.copy(Paths.get(fc.getSelectedFile().getAbsolutePath()),Paths.get("src/main/resources/audio/"+fc.getSelectedFile().getName()),
							StandardCopyOption.REPLACE_EXISTING);
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}*/
			}
			
			break;
		case SELECT:
			System.out.println(((JButton) e.getSource()).getName());
			if(!BackgroundMusic.isPlaying()) {
				JButton tmp = view.getSound();
				tmp.setText("<HTML><U>Turn Music Off</U></HTML>");
				view.setSound(tmp);
			}
			BackgroundMusic.getInstance().switchSong(((JButton) e.getSource()).getName());
			break;
		}
	}

}
