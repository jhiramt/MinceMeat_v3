import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;

public class GUI extends CloseableFrame implements ActionListener{


  /** Displays what is going on */
  private JTextArea outputDisplay;

  //Keeps track of how player should move
  private JLabel move;
  
  
  private JButton cont;
  
  //private Action moveAction;
     
  
  public GUI() {
    this.buildGUI();

    // Make the GUI visible.
    // super means we are calling a method inherited from the super class
    super.setVisible(true);
  }
  
  public Color foreground = Color.LIGHT_GRAY;
  /**
  * Creates the items in the GUI and adds them to the window.
  */
  private void buildGUI() {
    super.setSize(400, 430); // size of the window
    super.setTitle("MinceMeat"); // title displayed on the window
    super.setResizable(false);
	super.setLocationRelativeTo(null); //centers window on screen

    // Create a content pane to hold the GUI objects using BorderLayout
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());
    // Create a text area to display the output
    this.outputDisplay = new JTextArea(25,25);
    this.outputDisplay.setEditable(false); 
    this.outputDisplay.setVisible(true);
    //Adds keylistener to keep track of keyboard input
    //this.outputDisplay.addKeyListener(this);
    
    this.outputDisplay.setBackground(Color.BLACK);
    this.outputDisplay.setForeground(foreground);
   
    this.move = new JLabel();   
   // The move component shouldn't be shown
    this.move.setVisible(false);
    
    
this.outputDisplay.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "r");
    
    this.outputDisplay.getActionMap().put("r", new moveAction(1));


this.outputDisplay.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "l");
    
    this.outputDisplay.getActionMap().put("l", new moveAction(2));
    
    
    this.outputDisplay.getInputMap().put(KeyStroke.getKeyStroke("UP"), "u");
    
    this.outputDisplay.getActionMap().put("u", new moveAction(3));
    
    
this.outputDisplay.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "d");
    
    this.outputDisplay.getActionMap().put("d", new moveAction(4));
    
    this.outputDisplay.setFocusable(true);
    
    
    this.cont = new JButton("Continue");
    this.cont.setVisible(false);
    this.cont.setContentAreaFilled(false);
    this.cont.setOpaque(false);
    this.cont.setForeground(Color.WHITE);
    
    
    //Adds components to pane
    contentPane.add("North", this.move);
    contentPane.add("Center", this.outputDisplay);
    //contentPane.add("South", this.cont);
    //contentPane.add("South", buttons);
    contentPane.setBackground(Color.BLACK);
    
  }
  
  
  
  
  @SuppressWarnings("serial")
  public class moveAction extends AbstractAction{
	  
	  int dir;
	  moveAction (int direction){
		  dir = direction;
	  }
	  
  @Override
  public void actionPerformed(ActionEvent e) {
	  move.setText(Integer.toString(dir));
  }
  }

  
  
  //Called to update what is shown in display
  public void updateGUI(maze map, player pie, Color color) {
	  
	  String draw = map.printMap(map.getMaze(), pie);
	  this.outputDisplay.setForeground(color);
	  this.outputDisplay.setText(draw);
  }
  
  
  public void deathScreen(int rounds, int time, String killer) {
	  this.outputDisplay.setForeground(Color.RED.brighter());
	  //"        "
	  int minutes = Math.round(time / 6000);
	  //System.out.println(time);
	  int seconds = Math.round((time - (6000 * minutes)) / 100);
	  String death = "\n\n\n                                     ##########\n                                 ##############\n                             ##################\n                             ##################\n                             ##        ######        ##\n                             ##        ######        ##\n                             ##################\n                                 ######    ######\n                                 ######    ######\n                                 ##############\n                                 ###    ###      ###\n                                   ##    ###      ##  \n\n                                    Killed by " + killer +"\n                     You survived " + minutes + " minutes, " + seconds + " seconds.\n                              You completed " + rounds + " levels.";
	  this.outputDisplay.setText(death);
  }
  
  
  
  int time = 0;
  int direction = 5;
  int level = 0;
  maze map;
  int wait = 500;
  String killer = "";
  
  
  public static void main(String[] args) {

	
    GUI gui = new GUI(); //Creates GUI
   
    
    gui.map = new maze(); //Creates and generates maze / map
    
	int[] piepos; //Stores player location
	
	int[] meatpos; 
	
	int[] mincepos;
		
    Random rand = new Random(); // Creates random number to choose a safe spawn location
    int r1 = rand.nextInt(0, 2); //Chooses which horizontal corridor is selected
    int r2 = rand.nextInt(2, 4); //Chooses which vertical corridor is selected
    player pie = new player(gui.map.getSafeSpawn(r1), gui.map.getSafeSpawn(r2)); //Creates player
    
    
    meat meat = new meat(rand.nextInt(0, gui.map.getColSize() - 1), rand.nextInt(0, gui.map.getRowSize() - 1));
    
    int r3 = 1 - r1;
    int r4 = rand.nextInt(2, 4);
    mince mince = new mince(gui.map.getSafeSpawn(r3), gui.map.getSafeSpawn(r4));
    
    //door door = new door(15, 23);
    
    //doorpos = door.getPosition();
    
    
    
    //map.setMazePoint(doorpos[0], doorpos[1], 8);
    
    
    meatpos = meat.getPosition();
    
    gui.map.setMazePoint(meatpos[0], meatpos[1], 3);
    
    piepos = pie.getPosition(); //Stores starting position
    gui.map.setMazePoint(piepos[0], piepos[1], 2); //Places player on map
    
    
    mincepos = mince.getPosition();
    gui.map.setMazePoint(mincepos[0], mincepos[1], 4);
    
    
    //int direction = 5; //initializes direction value to an unused value
    gui.move.setText("5"); //initializes move label to unused value
    
   //gui.doorPos = gui.map.getDoorPos();
   //gui.keyPos = gui.map.getKeyPos();
   
   
   
    int delay = 10; //milliseconds
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	
        	
        	if(pie.alive()) {
        	gui.time += 1;
        	//gui.killer = "";
        	}
        	
        	if(!pie.hasKey()) {
        		gui.map.setMazePoint(gui.map.kp1(), gui.map.kp2(), 7);
        	}
        	
        	
        	gui.map.setMazePoint(gui.map.dp1(), gui.map.dp2(), 8);
            //System.out.println(doorPos[0] + ", " + doorPos[1]);
            
        	
        	gui.direction = Integer.parseInt(gui.move.getText());
        	
        	gui.map.setMazePoint(piepos[0], piepos[1], 0);
        	
        	 pie.movePlayer(gui.direction, gui.map);
             gui.move.setText("5");
             //int[] piepos = pie.getPosition();
             gui.map.setMazePoint(piepos[0], piepos[1], 2);
             
             
             
             //System.out.println(piepos[1] + ", " + keyPos[1]);
             
             gui.map.setMazePoint(meatpos[0], meatpos[1], 0);
             
             if(pie.alive()) {
             if(meat.pieSpotted(gui.map, pie)) {
             	meat.chase(pie, gui.map, gui.time);
             }
             else {
             	int wandir = rand.nextInt(1, 5);
             	meat.wander(wandir, gui.map, gui.time);
             	
             }
             }
             
             if(Math.sqrt(Math.pow((meatpos[0] - piepos[0]), 2) + Math.pow(meatpos[1] - piepos[1], 2)) <= 5 && gui.time % 20 == 0) {
            	 gui.foreground = gui.foreground.darker();
             }
             else if(Math.sqrt(Math.pow((meatpos[0] - piepos[0]), 2) + Math.pow(meatpos[1] - piepos[1], 2)) > 5) {
            	 gui.foreground = Color.LIGHT_GRAY;
             }
             
             
             
             gui.map.setMazePoint(meatpos[0], meatpos[1], 3);
             //System.out.println(Integer.toString(meatpos[0]) + ", " + Integer.toString(meatpos[1]));
             
             
             gui.map.setMazePoint(mincepos[0], mincepos[1], 0);
             
             mince.hunt(gui.map, pie, gui.time);
             
             //int[] mincepos = mince.getPosition();
             if(pie.alive()) {
             if(mincepos[0] == piepos[0] && mincepos[1] == piepos[1]) {
            	 pie.setAlive(false);
            	 gui.killer = "Mince";
             }
             else if(meatpos[0] == piepos[0] && meatpos[1] == piepos[1]) {
            	 pie.setAlive(false);
            	 gui.killer = "Meat";
             }
             }
             
             
             gui.map.setMazePoint(mincepos[0], mincepos[1], 4);
             
             
             
             
             
             
             
             if(piepos[0] == gui.map.dp1() && piepos[1] == gui.map.dp2() && pie.hasKey()) {
            	 gui.map = new maze();
            	 pie.setKey(false);
            	 gui.level += 1;
            	 
            	 int r1 = rand.nextInt(0, 2); //Chooses which horizontal corridor is selected
            	 int r2 = rand.nextInt(2, 4);
            	 //pie.setRep("o");
            	 pie.setPosition(gui.map.getSafeSpawn(r1), gui.map.getSafeSpawn(r2));
            	 r2 = rand.nextInt(2, 4);
            	 r1 = 1 - r1;
            	 mince.setPosition(gui.map.getSafeSpawn(r1), gui.map.getSafeSpawn(r2));
            	 meat.setPosition(rand.nextInt(0, gui.map.getColSize() - 1), rand.nextInt(0, gui.map.getRowSize() - 1));

            	 
             }
            
             
             if(piepos[0] == gui.map.kp1() && piepos[1] == gui.map.kp2() && !pie.hasKey()) {
           		pie.setKey(true);
           		//pie.setRep("õ");
           		gui.foreground = Color.WHITE;
           		
           		//map.setMazePoint(12, 20, 11); map.setMazePoint(12, 21, 12);
           	}
             
            
             if(pie.alive()) {
             gui.updateGUI(gui.map, pie, gui.foreground);
             }
             
             else {
            	 
            	 
            	 if(gui.wait >= 0) {
            	 gui.deathScreen(gui.level, gui.time, gui.killer);
            	 gui.wait -= 1;
            	 }
            	 else {
            	 //gui.cont.setVisible(true);
            	 
            	gui.wait = 500;
            	 
            	 gui.map = new maze();
            	 pie.setAlive(true);
            	 pie.setKey(false);
            	 gui.time = 0;
            	 gui.level = 0;
            	 
            	 int r1 = rand.nextInt(0, 2); //Chooses which horizontal corridor is selected
            	 int r2 = rand.nextInt(2, 4);
            	 pie.setPosition(gui.map.getSafeSpawn(r1), gui.map.getSafeSpawn(r2));
            	 r2 = rand.nextInt(2, 4);
            	 r1 = 1 - r1;
            	 mince.setPosition(gui.map.getSafeSpawn(r1), gui.map.getSafeSpawn(r2));
            	 meat.setPosition(rand.nextInt(0, gui.map.getColSize() - 1), rand.nextInt(0, gui.map.getRowSize() - 1));

            	 
            	 }
            	 
             }
             
        }
    };
    new Timer(delay, taskPerformer).start();
    
    
    } // Ends main gameplay loop
    
 // }
  
  

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}

  
  

  }
  
