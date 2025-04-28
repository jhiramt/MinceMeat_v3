public class mince {
	
	private int[] position = new int[2];
	private static final int max = Integer.MAX_VALUE;
	 

	private boolean alive;

	public mince(int row, int col) {
		this.position[0] = row;
		this.position[1] = col;
		this.alive = true;
	}
	
	public boolean getAlive() {
		return this.alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public int[] getPosition() {
		return this.position;
	}
	
	
	public void setPosition(int row, int col) {
		this.position[0] = row;
		this.position[1] = col;
	}


	public boolean pieAte(player pie) {
		int[] piepos = pie.getPosition();
		if(this.position[0] == piepos[0] && this.position[1] == piepos[1]) {
			return true;
		}
		return false;
	}
	
	public boolean abmove(int direction, maze map) {
		int[] colCheck = new int[] {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] rowCheck = new int[] {-1, 0, 1, -1, 1, -1, 0, 1};
		int[] check = new int[2];
		
		check[0] = this.position[0] + colCheck[direction];
		check[1] = this.position[1] + rowCheck[direction];

		if(!(check[0] < 0) && !(check[0] > map.getColSize() - 1) && !(check[1] < 0) && !(check[1] > map.getRowSize() - 1) && map.getMazePoint(check[0], check[1]) != 1) {

			switch(direction) {
			
			case 0: this.position[0] -= 1; this.position[1] -= 1; break;
			
			case 1: this.position[0] -= 1; break;
			
			case 2: this.position[0] -= 1; this.position[1] += 1; break;
			
			case 3: this.position[1] -= 1; break;
			
			case 4: this.position[1] += 1; break;
			
			case 5: this.position[0] += 1; this.position[1] -= 1; break;
			
			case 6: this.position[0] += 1; break;
			
			case 7: this.position[0] += 1; this.position[1] += 1; break;
			
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public void hunt(maze map, player pie, int time) {
		
		int[] piepos = pie.getPosition();
		if(time % 60 == 0 && !(this.position[0] == piepos[0] && this.position[1] == piepos[1])) {
			
			int[] colCheck = new int[] {-1, -1, -1, 0, 0, 1, 1, 1};
			int[] rowCheck = new int[] {-1, 0, 1, -1, 1, -1, 0, 1};
			int[] check = new int[2];
			
			double[] vals = new double[8];
			
			double compare = max - 1;
			int targetIndex = -1;
			
			for(int i = 0; i < 8; i++) {
				check[0] = this.position[0] + colCheck[i];
				check[1] = this.position[1] + rowCheck[i];
				
				if(!(check[0] < 0) && !(check[0] > map.getColSize() - 1) && !(check[1] < 0) && !(check[1] > map.getRowSize() - 1)) {

				if(map.getMazePoint(check[0], check[1]) == 1) {
					vals[i] = max;
				}
				else {
					double dist = Math.sqrt(Math.pow((check[0] - piepos[0]),2) + Math.pow(check[1] - piepos[1], 2));
					vals[i] = dist;					
				}
				}
				else {
					vals[i] = max;
				}
				
				if(vals[i] < compare) {
					targetIndex = i;
					compare = vals[i];
				}
			}
			
			
			switch(targetIndex) {
			
			case 0: this.position[0] -= 1; this.position[1] -= 1; break;
			
			case 1: this.position[0] -= 1; break;
			
			case 2: this.position[0] -= 1; this.position[1] += 1; break;
			
			case 3: this.position[1] -= 1; break;
			
			case 4: this.position[1] += 1; break;
			
			case 5: this.position[0] += 1; this.position[1] -= 1; break;
			
			case 6: this.position[0] += 1; break;
			
			case 7: this.position[0] += 1; this.position[1] += 1; break;
			
			
			}
			
		
		
		}
		
		
	}
	
	
	
	
	
	
	
}
