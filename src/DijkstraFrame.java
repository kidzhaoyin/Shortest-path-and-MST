//Yin Zhao UNI:yz2426
//HW#5 PROGRAMMING#1 Dijsktra's
//DijkstraFrame class asks user input of source and destination in a GUI window and call DijkstraMap to draw the map

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DijkstraFrame extends JFrame{

	private static final int FRAME_WIDTH=600;
	private static final int FRAME_HEIGHT=400;
	
	private JLabel title;
	private JLabel sourceLabel;
	private JLabel desLabel;
	private JComboBox sourceComb;
	private JComboBox desComb;
	private JButton button;
	private ActionListener listener;
	private DijkstraPath dp;
	private HashMap<String, DijkstraPath.Vertex> vs;
	private DijkstraMap dmap;
	
	
	public DijkstraFrame(final File aFile,final File bFile) throws FileNotFoundException{

		dp=new DijkstraPath(aFile);
		vs=dp.getVertices();
		title=new JLabel("Please choose source and destination");
		add(title,BorderLayout.NORTH);
	
		sourceLabel=new JLabel("Source:");	
		add(sourceLabel,BorderLayout.WEST);
	
		desLabel=new JLabel("Destination:");
		add(desLabel,BorderLayout.EAST);
		
		button=new JButton("Calculate");
		
		//when the button is pressed, DijkstraMap is constructed and paintComponents method is called
		class ChoiceListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				String s=(String) sourceComb.getSelectedItem();
				String d=(String) desComb.getSelectedItem();
				try {
					JFrame fr=new JFrame();
					fr.setSize(2000,2000);
					fr.setTitle("Dijkstra's Shortest Path");
					dmap=new DijkstraMap(aFile,bFile,s,d);
					fr.add(dmap);
					fr.setVisible(true);
					fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
		listener=new ChoiceListener();
		createControlPanel();
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
	}
	//create a control panel and put all components (labels, button, comboBox) onto the panel, add panel in the frame
	public void createControlPanel(){
		JPanel comboPanel=createComboBox();
		JPanel controlPanel=new JPanel();
		controlPanel.setLayout(new GridLayout(2,1));
		controlPanel.add(comboPanel);
		controlPanel.add(button);
		button.addActionListener(listener);
		add(controlPanel,BorderLayout.SOUTH);
		
	}
	
	//method to create ComboBox for choosing source and destination
	public JPanel createComboBox(){
		sourceComb=new JComboBox();
		desComb=new JComboBox();
		for(Entry<String,DijkstraPath.Vertex> v:vs.entrySet()){
			sourceComb.addItem(v.getKey());
			desComb.addItem(v.getKey());
		}
		sourceComb.setEditable(false);
		desComb.setEditable(false);
		
		JPanel panel=new JPanel();
		GridLayout layout=new GridLayout(1,2);
		layout.setHgap(80);
		panel.setLayout(layout);
		panel.add(sourceComb);
		panel.add(desComb);
		return panel;
		
	}
	
}
