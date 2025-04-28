import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class abprune extends JFrame implements ActionListener {

        /** Displays what is going on */
        private JTextArea outputDisplay;

        //Keeps track of how player should move
        private JLabel move;
        
        private JButton cont;
        
        //private Action moveAction;
           
        
        public abprune() {
                this.buildGUI();
        
                // Make the GUI visible.
                // super means we are calling a method inherited from the super class
                super.setVisible(true);
        }
        
        public Color foreground = Color.LIGHT_GRAY;

        public double gameScore(maze map, player pie, mince mince, meat meat){
              double mincemeatDist =  (Math.sqrt(Math.pow((mince.getPosition()[0] - pie.getPosition()[0]),2) + Math.pow(mince.getPosition()[1] - pie.getPosition()[1], 2))) + (Math.sqrt(Math.pow((meat.getPosition()[0] - pie.getPosition()[0]),2) + Math.pow(meat.getPosition()[1] - pie.getPosition()[1], 2))); //Euclidean distance between mince and player plus euclidean distance between meat and player
              double minceDist = (Math.sqrt(Math.pow((mince.getPosition()[0] - pie.getPosition()[0]),2) + Math.pow(mince.getPosition()[1] - pie.getPosition()[1], 2)));
              double keyDist = Math.abs(pie.getPosition()[0] - map.getKeyPos()[0]) + Math.abs(pie.getPosition()[1] - map.getKeyPos()[1]); //Manhattan distance between meat and key
              if(keyDist == 0){
                      keyDist = 0.001; //Prevents divide by 0
              }
              double doorDist = Math.abs(pie.getPosition()[0] - map.getDoorPos()[0]) + Math.abs(pie.getPosition()[1] - map.getDoorPos()[1]);
              if(doorDist == 0){
                      doorDist = 0.001; //Prevents divie by 0
              }
              double score = 10 * (mincemeatDist / keyDist) - doorDist; //Larger when pie is close to key and far from mince and meat
              if(pie.hasKey()){
                      score = 10 * (mincemeatDist / doorDist) - doorDist;
              }
              //return (int)Math.floor((Math.random() * 100) - 50);
              return score; //Prevents stagnation
        }



        public int ab_player_move(maze main_map, player main_pie, bomb main_bomb, meat main_meat, mince main_mince){
                int return_move = -1;
                double return_val = Integer.MIN_VALUE;
                double cur_move_val;
                maze map = new maze();
                map.copyMaze(main_map);
                player pie = new player(main_pie.getPosition()[0], main_pie.getPosition()[1]);
                mince mince = new mince(main_mince.getPosition()[0], main_mince.getPosition()[1]);
                meat meat = new meat(main_meat.getPosition()[0], main_meat.getPosition()[1]);
                bomb bomb = new bomb();
                for(int move = 1; move <= 4; move++){
                        cur_move_val = ab_min(Integer.MIN_VALUE, Integer.MAX_VALUE, map, pie, bomb, meat, mince, 0, move);
                        if(cur_move_val != 404.404 && cur_move_val >= return_val){
                                return_val = cur_move_val;
                                //System.out.println("\u001B[32m" + return_val);
                                return_move = move;
                        }
                }
                //System.out.println("Player: " + return_val + " , " + return_move);
                return return_move;
        }

        public int ab_mince_move(maze main_map, player main_pie, bomb main_bomb, meat main_meat, mince main_mince){
                int return_move = -1;
                double return_val = Integer.MAX_VALUE;
                double cur_move_val;
                maze map = new maze();
                map.copyMaze(main_map);
                player pie = new player(main_pie.getPosition()[0], main_pie.getPosition()[1]);
                mince mince = new mince(main_mince.getPosition()[0], main_mince.getPosition()[1]);
                meat meat = new meat(main_meat.getPosition()[0], main_meat.getPosition()[1]);
                bomb bomb = new bomb();
                for(int move = 0; move <= 7; move++){
                        cur_move_val = ab_min(Integer.MIN_VALUE, Integer.MAX_VALUE, map, pie, bomb, meat, mince, 1, move);
                        if(cur_move_val != 404.404 && cur_move_val <= return_val){
                                return_val = cur_move_val;
                                //System.out.println("\u001B[31m" + return_val);
                                return_move = move;
                        }
                }
                //System.out.println("Mince: " + return_val + " , " + return_move);
                return return_move;
        }



        public double ab_max(double alpha, double beta, maze map, player pie, bomb bomb, meat meat, mince mince, int depth, int move){
                //System.out.println("\u001B[32m" + depth + ", " + move);
                if(pie.abmoveplayer(move, map, bomb) == false){ //Move player, check if valid
                        return 404.404;
                }
                //(pie.getPosition()[0] == meat.getPosition()[0] && pie.getPosition()[1] == meat.getPosition()[1])
                if(false || (pie.getPosition()[0] == mince.getPosition()[0] && pie.getPosition()[1] == mince.getPosition()[1]) ){
                        return Integer.MIN_VALUE;
                }
                if(pie.getPosition()[0] == map.getKeyPos()[0] && pie.getPosition()[1] == map.getKeyPos()[1]){
                        return Integer.MAX_VALUE;
                }
                if(depth == 12){
                        return gameScore(map, pie, mince, meat);
                }
                if(meat.pieSpotted(map, pie)) {
                        meat.chase(pie, map, 100);
                }
                else {
                        int wandir = meat.state_direction(map, pie, mince);
                        meat.wander(wandir, map, 100);
                }
                double value;
                for(int mince_move = 0; mince_move <=7; mince_move++){
                        value = ab_min(alpha, beta, map, pie, bomb, meat, mince, depth + 1, mince_move);
                        if(value != 404.404) {
                                alpha = Math.max(alpha, value);
                                if(alpha >= beta){
                                        return alpha;
                                }
                        }
                }
                return alpha;
        }

        
        public double ab_min(double alpha, double beta, maze map, player pie, bomb bomb, meat meat, mince mince, int depth, int move){
                //System.out.println("\u001B[31m" + depth + ", " + move);
                if(mince.abmove(move, map) == false){ //Move mince, check if valid
                        return 404.404;
                }
                //(pie.getPosition()[0] == meat.getPosition()[0] && pie.getPosition()[1] == meat.getPosition()[1])
                if( false || (pie.getPosition()[0] == mince.getPosition()[0] && pie.getPosition()[1] == mince.getPosition()[1]) ){
                        return Integer.MIN_VALUE;
                }
                if(pie.getPosition()[0] == map.getKeyPos()[0] && pie.getPosition()[1] == map.getKeyPos()[1]){
                        return Integer.MAX_VALUE;
                }
                if(depth == 12){
                        return gameScore(map, pie, mince, meat);
                }
                if(meat.pieSpotted(map, pie)) {
                        meat.chase(pie, map, 100);
                }
                else {
                        int wandir = meat.state_direction(map, pie, mince);
                        meat.wander(wandir, map, 100);
                }
                double value;
                for(int pie_move = 1; pie_move <=4; pie_move++){
                        value = ab_max(alpha, beta, map, pie, bomb, meat, mince, depth + 1, pie_move);
                        if(value != 404.404){
                                beta = Math.min(beta, value);
                                if(alpha >= beta){
                                        return beta;
                                }
                        }
                }
                return beta;
        } 
        


        /**
        * Creates the items in the GUI and adds them to the window.
        */
        private void buildGUI() {
                super.setSize(400, 430); // size of the window
                super.setTitle("MinceMeat"); // title displayed on the window
                super.setResizable(false);
                super.setLocationRelativeTo(null); //centers window on screen
                super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ends program when window is closed
        
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

                this.outputDisplay.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "s");
                this.outputDisplay.getActionMap().put("s", new moveAction(0));

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
        public class moveAction extends AbstractAction {
	        int dir;
	        moveAction (int direction) {
	        dir = direction;
	        }
	  
        @Override
                public void actionPerformed(ActionEvent e) {
                        move.setText(Integer.toString(dir));
                }
        }


        //Called to update what is shown in display
        public void updateGUI(maze map, player pie, Color color, bomb bomb) {
                String draw = map.printMap(map.getMaze(), pie, bomb, true);
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
	
        abprune gui = new abprune(); //Creates GUI
        
        gui.map = new maze(); //Creates and generates maze / map
        
        int[] piepos; //Stores player location
        
        int[] bombpos; //Stores bomb's location

        int[] meatpos; //Stores location of meat

        int[] mincepos; //Stores location of mince

        Random rand = new Random(); // Creates random number to choose a safe spawn location
        int r1 = rand.nextInt(0, 2); //Chooses which horizontal corridor is selected
        int r2 = rand.nextInt(2, 4); //Chooses which vertical corridor is selected
        player pie = new player(gui.map.getSafeSpawn(r1), gui.map.getSafeSpawn(r2)); //Creates player
        
        bomb bomb = new bomb();
        
        meat meat = new meat(rand.nextInt(0, gui.map.getColSize() - 1), rand.nextInt(0, gui.map.getRowSize() - 1));
        
        int r3 = 1 - r1;
        int r4 = rand.nextInt(2, 4);
        mince mince = new mince(gui.map.getSafeSpawn(r3), gui.map.getSafeSpawn(r4));
        
        meatpos = meat.getPosition();
        gui.map.setMazePoint(meatpos[0], meatpos[1], 3);
        
        piepos = pie.getPosition(); //Stores player starting position
        gui.map.setMazePoint(piepos[0], piepos[1], 2); //Places player on map
        
        mincepos = mince.getPosition();
        gui.map.setMazePoint(mincepos[0], mincepos[1], 4);
        
        
        gui.move.setText("5"); //initializes move label to unused value
        
        
        int delay = 10; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
        	        
                        //Increase time
                        //TODO Change time to use actual time -- maybe already do? Loop repeats every 10ms. Not taking into account execution time. Time would be useful for giving an accurate time report on kill screen.
                        
                        //Calendar now = Calendar.getInstance();
                        //now.get(Calendar.MILLISECOND);
        	        if(pie.alive()) {
        	                gui.time += 1;
        	        }

        	        if(!pie.hasKey()) {
        		        gui.map.setMazePoint(gui.map.kp1(), gui.map.kp2(), 7);
        	        }       

                        //Draw door on map
        	        gui.map.setMazePoint(gui.map.dp1(), gui.map.dp2(), 8);
        	        
                        //Get player input
                	gui.direction = Integer.parseInt(gui.move.getText());
                        
                        if(gui.direction == 5) {
                                pie.setMoved(false);
                        }
                        else {
                                pie.setMoved(true);
                        }

                        //Clear player position on map
        	        gui.map.setMazePoint(piepos[0], piepos[1], 0);
        	        

                        //Pass move command to player
                        /*if(gui.direction != 5){
        	                pie.abmoveplayer(gui.direction, gui.map, bomb);
                                System.out.println(gui.gameScore(gui.map, pie, mince, meat));
                        }*/
             	        
                        pie.moveplayer(gui.ab_player_move(gui.map, pie, bomb, meat, mince), gui.map, bomb);
                        pie.setMoved(true);

                        if(pie.isBombPlaced() && bomb.getPlaceTurn()){
                                bomb.setPlaced(true);
                                bomb.setPosition(piepos[0], piepos[1]);
                                bomb.setPlaceTurn(false);
                                bomb.setFuse(4);
                        }

                        //Set move direction to unused value
                        gui.move.setText("5");

                        gui.map.setMazePoint(piepos[0], piepos[1], 2);

                        if(bomb.getPlaced()) {
                                gui.map.setMazePoint(bomb.getPosition()[0], bomb.getPosition()[1], 6);
                        }
                        
                        if(pie.getMoved()) {
                        bomb.tickFuse();
                        }
                        if(bomb.getFuse() == 0) {
                                bomb.explode(gui.map, pie, meat, mince);
                        }


                        //Clear meat's old position
                        if(meat.getAlive()){
                                gui.map.setMazePoint(meatpos[0], meatpos[1], 0);
                        }


                        if(pie.alive() && pie.getMoved()) {
                                if(meat.pieSpotted(gui.map, pie)) {
                                        meat.chase(pie, gui.map, gui.time);
                                }
                                else {
                                        int wandir = meat.state_direction(gui.map, pie, mince);
                                        meat.wander(wandir, gui.map, gui.time);
                                }
                        }
             
                //Gradual fade as player gets close to meat
                if(meat.getAlive() && Math.sqrt(Math.pow((meatpos[0] - piepos[0]), 2) + Math.pow(meatpos[1] - piepos[1], 2)) <= 5 && gui.time % 20 == 0) {
                        gui.foreground = gui.foreground.darker();
                }
                else if(!meat.getAlive() || Math.sqrt(Math.pow((meatpos[0] - piepos[0]), 2) + Math.pow(meatpos[1] - piepos[1], 2)) > 5) {
                        gui.foreground = Color.LIGHT_GRAY;
                }
                     
                //Draw meat
                if(meat.getAlive()) {
                        gui.map.setMazePoint(meatpos[0], meatpos[1], 3);
                }
                
                //Clear Mince's old position
                if(mince.getAlive()) {
                        gui.map.setMazePoint(mincepos[0], mincepos[1], 0);
                }
                
                //Run Mince's AI
                if(mince.getAlive() && pie.getMoved()) {
                        //mince.hunt(gui.map, pie, gui.time);
                        //int mince_wandir = rand.nextInt(0, 8);
                        mince.abmove(gui.ab_mince_move(gui.map, pie, bomb, meat, mince), gui.map);
                }
                
                //Check for kill condition
                if(pie.alive()) {
                        if(mince.getAlive() && mince.pieAte(pie)) {
                                pie.setAlive(false);
                                gui.killer = "Mince";
                        }
                        else if(meat.getAlive() && meat.pieAte(pie)) {
                                pie.setAlive(false);
                                gui.killer = "Meat";
                        }
                        else {
                                gui.killer = "your own bomb!";
                        }
                }
                     
                //Draw Mince
                if(mince.getAlive()) {
                        gui.map.setMazePoint(mincepos[0], mincepos[1], 4);
                }
                     
                if((piepos[0] == gui.map.dp1() && piepos[1] == gui.map.dp2() && pie.hasKey()) || pie.hasKey()) {
                        gui.map = new maze();
                        pie.setKey(false);
                        gui.level += 1;

                        mince.setAlive(true);
                        meat.setAlive(true);

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
                        gui.updateGUI(gui.map, pie, gui.foreground, bomb);
                }
                else {
                        if(gui.wait >= 0 && false) {
                                gui.deathScreen(gui.level, gui.time, gui.killer);
                                gui.wait -= 1;
                        }
                        else {
                        //gui.cont.setVisible(true);
                    
                        gui.wait = 0;
                        System.out.println(gui. killer + ", " + gui.level);
                    
                        gui.map = new maze();
                        pie.setAlive(true);
                        pie.setKey(false);
                        pie.placeBomb(false);
                        pie.resetBombs();
                        bomb.setPlaced(false);


                        mince.setAlive(true);
                        meat.setAlive(true);

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
    

        @Override
        public void actionPerformed(ActionEvent e) {
        	// TODO Auto-generated method stub
        	
        }
} 
  
