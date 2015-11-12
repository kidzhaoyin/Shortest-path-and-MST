//Yin Zhao UNI:yz2426
//HW#5 PROGRAMMING#1 Dijkstra's
//DijkstraTest class
//In command line argument, input two files, the first one containing all city pairs and their distance, the second one containing coordinates of all cities

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class DijkstraTest {

	public static void main(String[] args) throws FileNotFoundException {
		

		File aFile = new File(args[0]);
		File bFile = new File(args[1]);
		JFrame frame = new DijkstraFrame(aFile, bFile);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Dijkstra's Shortest Path");
		frame.pack();
		frame.setVisible(true);
	}

}
