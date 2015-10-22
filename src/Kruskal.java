//Yin Zhao UNI:yz2426
//HW#5 PROGRAMMING #2 Kruskal's
//Kruskal's algorithm to compute minimum spanning tree

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Kruskal {

	private static final double INFINITE = 99999999;
	//Vertex class
	//each Vertex has a name, mark known/unknown, distance, previous Vertex and coordinates
	class Vertex{
		public String name;
		public boolean known;
		public double d;
		public Vertex prev;
		int x,y;
		
		public Vertex( String name, int x, int y) {
			this.name = name;
			known = false;
			d = INFINITE;
			prev = null;
			this.x = x;
			this.y = y;
		}
	}
	//Edge class
	//each Edge has two vertices and weight. compare according to weight
	class Edge implements Comparable<Edge> {
		public Vertex v;
		public Vertex u;
		public double weight;
		
		public Edge( Vertex v, Vertex u, double weight) {
			this.v = v;
			this.u = u;
			this.weight = weight;
		}

		public int compareTo( Edge other) {
			return Double.compare(this.weight, other.weight);
		}
	}
	
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private BinaryHeap<Edge> bh;
	//keep track of number of vertices
	private int numV;
	private int x_max,y_max;
	
	//aFile the file containing coordinates of all cities
	public Kruskal(File aFile) throws FileNotFoundException{
		
		Scanner in = new Scanner(aFile);
		
		//put all vertices in the ArrayList
		numV = 0;
		x_max = 0; y_max = 0;
		while( in.hasNextLine()){
			String aLine = in.nextLine();
			String[] info = aLine.split("\\s+");
			String name = info[0];
			
			int xaxis = Integer.parseInt(info[1]);
			int yaxis = Integer.parseInt(info[2]);
			Vertex v = new Vertex(name, xaxis, yaxis);
			
			if( xaxis > x_max )
				x_max = xaxis;
			if( yaxis > y_max )
				y_max = yaxis;
			vertices.add(v);
			numV++;			
		}
		//put all edges to the BinaryHeap
		bh = new BinaryHeap<Edge>( numV * numV / 2 - numV / 2);
		for(int j=0;j<vertices.size()-1;j++){
			for(int k=j+1;k<(vertices.size());k++){
				Vertex u=vertices.get(j),v=vertices.get(k);
				double weight=Math.sqrt(Math.pow(u.x-v.x, 2)+Math.pow(u.y-v.y, 2));
				Edge e=new Edge(u,v,weight);
				bh.insert(e);
				
			}
		}		
	}
	
	//method to construct the minimum spanning tree and put all chosen edges into an ArrayList
	public ArrayList<Edge> findPath() {
		//each single vertex in a set
		DisjSets ds=new DisjSets(numV);
		
		ArrayList<Edge> mst=new ArrayList<Edge>();
		
		while(mst.size()!=numV-1){
			//find the smallest remaining edge
			Edge min=bh.deleteMin();
			
			int p1=ds.find(vertices.indexOf(min.u));
			int p2=ds.find(vertices.indexOf(min.v));
			
			if(p1!=p2){
				mst.add(min);
				ds.union(p1, p2);
			}
		}
		return mst;
	}
	
	//method to get the list of all vertices
	public ArrayList<Vertex> getVertices(){
		return vertices;
	}
	//method to print out path in console window
	public void printPath(ArrayList<Edge> mst){
		System.out.println("Minimum spanning tree city pairs:");
		for(Edge e:mst)
			System.out.println("("+e.u.name+" , "+e.v.name+")");
	}
	
	public int getX_max(){
		return x_max;
	}
	public int getY_max(){
		return y_max;
	}
}
