package resources;

import java.util.ArrayList;
import java.util.List;

public class SlideshowThread extends Thread{
	private List<String> images;
	private int current = 0;
	private BackgroundPanel panel;
	
	public SlideshowThread(BackgroundPanel p) {
		this.panel = p;
		images = new ArrayList<>();
		for(int i = 1; i <10;i++) {
			images.add("background"+i+".jpg");
			
		}
	}
	private void advance() {
		current = (current+1)%images.size();
		panel.changeBackground(images.get(current));
		panel.repaint();
	}
	public void run() {
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			advance();
		}
	}
}
