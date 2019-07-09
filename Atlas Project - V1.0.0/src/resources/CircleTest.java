package resources;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import character.FactionTypes;
import graphicTools.Colors;
public class CircleTest extends JPanel{
	private static final int SIZE = 256;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private int n;
    private Map<Point,FactionTypes> factionMap;
    private FactionTypes [] factions = {FactionTypes.The_Called,FactionTypes.The_Children_Of_The_Mask,FactionTypes.The_Collective,
    							FactionTypes.The_Cult_Of_The_Stars,FactionTypes.The_Ennead,FactionTypes.The_Legion,FactionTypes.The_Ritualists};
    private Color [] colors = {Color.black,Color.blue,Color.cyan,Color.gray,Color.red,Color.yellow,Color.green};
    /** @param n  the desired number of circles. */
    public CircleTest(int n) {
        super(true);
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        this.n = n;
        factionMap = new HashMap<>();
    }
    /*
     * validPoint
     * given point on screen where clicked, returns the closest node to it, if there is one
     * uses a hashmap of distances and classes, adding to the hashmap, those with a minimum distance of 8 from each node
     */
    public FactionTypes validPoint(Point p) {
    	Map<Double, FactionTypes> minDist = new HashMap<>();
    	FactionTypes faction = null;
    	for(Point d: factionMap.keySet()) {
    		if(Math.abs(d.x-p.x) > 0 && Math.abs(d.x-p.x) < 22) {
    			if(Math.abs(d.y-p.y) > 0 && Math.abs(d.y - p.y) < 22) {
    				double distance = Math.sqrt(Math.abs(p.x-d.x)^2 + Math.abs(p.y-d.y)^2);
    	    		minDist.put(Double.valueOf(distance),factionMap.get(d));
    			}
    		}
    		
    	}
    	if(minDist.size()==0) {
    		return null;
    	}
    	else {
    		double value = minDist.entrySet().stream().mapToDouble(t->t.getKey()).min().getAsDouble();
    		return minDist.get(value);
    	}
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        a = getWidth() / 2;
        b = getHeight() / 2;
        int m = Math.min(a, b);
        r = 4 * m / 5;
        int r2 = Math.abs(m - r) / 2;
        g2d.drawOval(a - r, b - r, 2 * r, 2 * r);
        //g2d.setColor(Color.blue);
        for (int i = 0; i < n; i++) {
        	g2d.setColor(colors[i]);
            double t = 2 * Math.PI * i / n;
            int x = (int) Math.round(a + r * Math.cos(t));
            int y = (int) Math.round(b + r * Math.sin(t));
            factionMap.put(new Point(x,y),this.factions[i]);
            g2d.fillOval(x - r2, y - r2, 2 * r2, 2 * r2);
        }
    }

	public Map<Point, FactionTypes> getFactionMap() {
		return factionMap;
	}

	public void setFactionMap(Map<Point, FactionTypes> factionMap) {
		this.factionMap = factionMap;
	}
}
