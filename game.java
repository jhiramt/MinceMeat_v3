public class game extends GUI {
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
             
             //Gradual fade as player gets close to meat
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
    
}
  
  


