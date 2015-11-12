//Yin Zhao UNI: yz2426
//HW#5 PROGRAMMING#2 Kruskal's
//KruskalTest class
//input name of file containing all cities with x and y coordinates in command line argument
//resulting map will be displayed on GUI window

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFrame;




public class KruskalTest {

	public static void main(String[] args) throws FileNotFoundException {
		
		File in = new File(args[0]);
		Kruskal kr = new Kruskal(in);
		ArrayList<Kruskal.Edge> edges = kr.findPath();
		
		kr.printPath(edges);
		
		JFrame frame = new JFrame();
		frame.setSize(2000, 2000);
		frame.setTitle("Kruskal's minimum spanning tree");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DrawMap map = new DrawMap(in, edges);
		frame.add(map);
		frame.setVisible(true);
	}

}
