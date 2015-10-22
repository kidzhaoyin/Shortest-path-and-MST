//Yin Zhao UNI:yz2426
//HW#5 PROGRAMMING #2 Kruskal's
//DrawMap class to draw a minimum spanning tree in GUI window

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JComponent;


public class DrawMap extends JComponent{


	private Kruskal kr;
	private ArrayList<Kruskal.Edge> edges;
	private ArrayList<Kruskal.Vertex> vertices;
	private static final int AXES_OFFSET=50;


	public DrawMap(File aFile,ArrayList<Kruskal.Edge> ed) throws FileNotFoundException {
		kr=new Kruskal(aFile);
		edges=ed;
		vertices=kr.getVertices();
	}
	
	//method to draw the cities as magenta circles along with their names, and chosen paths
	public void paintComponent(Graphics g){
		Graphics2D g2=(Graphics2D) g;
		
		g2.setColor(Color.MAGENTA);
		for(Kruskal.Vertex v:vertices){
			g2.fillOval(xConverter(v.x)-5, yConverter(v.y)-5, 10, 10);
			g2.drawString(v.name, xConverter(v.x)-8, yConverter(v.y)-8);
		}
			
		
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(1));
		for(Kruskal.Edge e:edges)
			
			
			 g2.drawLine(xConverter(e.u.x), yConverter(e.u.y), xConverter(e.v.x),yConverter(e.v.y));
		
	}
	
	//method to convert x coordinate to fit frame
	private int xConverter(int x){
		return (int)((getWidth()-2*AXES_OFFSET)*(x/(double)kr.getX_max())+AXES_OFFSET);	
	}
	//method to convert y coordinate to fit frame
	private int yConverter(int y){
		return (int)((getHeight()-2*AXES_OFFSET)*(y/(double)kr.getY_max())+AXES_OFFSET);
	}
	
	
}
