//Yin Zhao UNI:yz2426
//HW#5 PROGRAMMING #1 Dijkstra's
//DijkstraPath class to find the shortest path from any source city to any destination city

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;


public class DijkstraPath {
	private static final double INFINITE=99999999;
	
	//Vertex class each object contains its name, adjacency list, mark known/unknown, distance and previous vertex
	//compare according to distance
	class Vertex implements Comparable<Vertex>{
		public String name;
		public LinkedList<Edge> adj;
		public boolean known;
		public double dist;
		public Vertex path;
		
		public Vertex (String aName){
			this.name=aName;
			adj=new LinkedList<Edge>();
			known=false;
			dist=INFINITE;
			path=null;
		}
		public int compareTo(Vertex oth){
			return Double.compare(this.dist, oth.dist);
		}
		
		
	}
	//Edge class each object contains the next Vertex and distance between them
	class Edge{
		public Vertex next;
		public double distance;
		
		public Edge(Vertex next,double dis){
			this.next=next;
			distance=dis;
		}
	}
	//HashMap that maps a city name to the Vertex
	private HashMap<String,Vertex> vertices;
	
	//receives the file that contains all city pairs
	public DijkstraPath(File aFile) throws FileNotFoundException{
		
		vertices=new HashMap<String,Vertex>();
		Scanner scan=new Scanner(aFile);
		//put all vertices without duplicate into the HashMap vertices and update adjacency lists of all vertices
		while(scan.hasNextLine()){
			String line=scan.nextLine();
			String[] info=line.split("\\s+");

			if(!vertices.containsKey(info[0]))
				vertices.put(info[0], new Vertex(info[0]));
			if(!vertices.containsKey(info[1]))
				vertices.put(info[1], new Vertex(info[1]));
			
			vertices.get(info[0]).adj.add(new Edge(vertices.get(info[1]),Double.parseDouble(info[2])));	
			vertices.get(info[1]).adj.add(new Edge(vertices.get(info[0]),Double.parseDouble(info[2])));
		}
		
		
	}
	
	//method to find the shortest path to every other city given a source name
	public void findShortestPath(String sourceName){
		PriorityQueue<Vertex> vqueue=new PriorityQueue<Vertex>();
		vertices.get(sourceName).dist=0;
		//put all vertices into a priority queue
		for (Entry<String, Vertex> entry:vertices.entrySet())
			vqueue.add(entry.getValue());
		//decide the vertex to be known and update vertices in its adjacency list
		while(!vqueue.isEmpty()){
			Vertex min=vqueue.poll();
			min.known=true;
			for(Edge e:min.adj){
				Vertex a=e.next;
				double temp=min.dist+e.distance;
				if(Double.compare(temp,a.dist)<0){
					vqueue.remove(a);
					a.dist=temp;
					a.path=min;
					vqueue.add(a);		
				}
			}	
		}		
	}
	
	//return the shortest path as an arraylist of vertex with order, given the destination name
	public ArrayList<Vertex> getPath(String desName){
		ArrayList<Vertex> p=new ArrayList<Vertex>();
		Vertex des=vertices.get(desName);
		Vertex c=des;
		if(c.known=false){
			System.out.println("No path found");
			p.add(c);
			return p;
		}
		
		while(c.path!=null){
			p.add(c);
			c=c.path;
		}
		p.add(c);
		return p;
	}
	public double getTotal(String desName){
		Vertex des=vertices.get(desName);
		return des.dist;
	}
	//method to get all the vertices
	public HashMap<String,Vertex> getVertices(){
		return vertices;
	}

}
