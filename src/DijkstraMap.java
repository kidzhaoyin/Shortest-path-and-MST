//Yin Zhao UNI:yz2426
//HW#5 PROGRAMMING#1 Dijkstra's
//DijkstraMap class for drawing a map given two files: one containing all city pairs and their distance, the other containing all coordinates of cities

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JComponent;


public class DijkstraMap extends JComponent {

	private DijkstraPath dp;
	private HashMap<String, Point> cities;
	private HashMap<String, DijkstraPath.Vertex> vertices;
	private ArrayList<DijkstraPath.Vertex> path;
	private int x_max, y_max;
	private static final int AXES_OFFSET = 30;
	private double totalDis;

	//given the above mentioned two files and the names of source and destination city, construct the map
	public DijkstraMap(File aFile, File bFile, String s, String d) throws FileNotFoundException {
		cities = new HashMap<String,Point>();
		dp = new DijkstraPath(aFile);
		vertices = dp.getVertices();
		Scanner in = new Scanner(bFile);
		
		
		x_max = 0;
		y_max = 0;//keep track of maximum coordinates in order to adjust coordinates to fit frame
		while (in.hasNextLine()) {
			String ln = in.nextLine();
			String[] info = ln.split("\\s+");
			Point p = new Point(Integer.parseInt(info[1]), Integer.parseInt(info[2]));
			cities.put(info[0], p);
			if (p.x > x_max) {
				x_max = p.x;
			}
			if (p.y > y_max) {
				y_max = p.y;
			}
		}
		dp.findShortestPath(s);
		path = dp.getPath(d);
		totalDis = dp.getTotal(d);
		
	}
	//method to draw all cities as circles along with their names and all edges in black, chosen path in red
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.BLACK);
		
		for (Entry<String, DijkstraPath.Vertex> v : vertices.entrySet()) {
			Point p = coordsConverter(cities.get(v.getKey()));
			g2.fillOval(p.x - 5, p.y - 5, 10, 10);
			for (DijkstraPath.Edge e : v.getValue().adj) {
				DijkstraPath.Vertex n = e.next;
				Point q = coordsConverter(cities. get(n.name));
				g2.drawLine(p.x, p.y, q.x, q.y);
			}
			g2.drawString(v.getKey(), p.x-6, p.y-6);
		}
		
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		Point p = coordsConverter(cities.get(path.get(0).name));
		for (int i = 1; i < path.size(); i++) {
			Point q = coordsConverter(cities.get(path.get(i).name));
			g2.drawLine(p.x, p.y, q.x, q.y);
			p = q;
		}
		
		g2.drawString("Total Distance: " + totalDis, AXES_OFFSET, AXES_OFFSET);
	}
	
	//method to convert coordinates to fit frame
	private Point coordsConverter(Point p) {

		int x = p.x;
		int y = p.y;
		int xCoord = (int)((getWidth() - 2 * AXES_OFFSET) * (x / (double)x_max) + AXES_OFFSET);
		int yCoord = (int)((getHeight() - 2 * AXES_OFFSET) * (y / (double)y_max) + AXES_OFFSET);
		
		return new Point(xCoord, yCoord);
		
	}
}
