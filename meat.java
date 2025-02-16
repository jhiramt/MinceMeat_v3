
public class meat {
	
	private int[] position = new int[2];
	
	public meat(int row, int collumn) {
		this.position[0] = row;
		this.position[1] = collumn;
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
	
	
	public boolean pieSpotted(maze map, player pie) {
		
		int[] piepos = pie.getPosition();
		boolean ret = false;
		if(this.position[0] == piepos[0]) {
			if(this.position[1] > piepos[1]) {
				for(int i = this.position[1]; i > piepos[1]; i--) {
					if(map.getMazePoint(this.position[0], i) == 1) {
						return false;
					}
				}
			}
			if(this.position[1] < piepos[1]) {
				for(int i = this.position[1]; i < piepos[1]; i++) {
					if(map.getMazePoint(this.position[0], i) == 1) {
						return false;
					}
				}
			}
			ret = true;
		}
		if(this.position[1] == piepos[1]) {
			if(this.position[0] > piepos[0]) {
				for(int i = this.position[0]; i > piepos[0]; i--) {
					if(map.getMazePoint(i, this.position[1]) == 1) {
						return false;
					}
				}
			}
			if(this.position[0] < piepos[0]) {
				for(int i = this.position[0]; i < piepos[0]; i++) {
					if(map.getMazePoint(i, this.position[1]) == 1) {
						return false;
					}
				}
			}
			ret = true;
		}		
		return ret;
	}
	
	
	public void chase(player pie, maze map, int time) {
		int[] piepos = pie.getPosition();
		if(time % 15 == 0) {
			if(piepos[0] < this.position[0]) {
				this.position[0] -= 1;
			}
			if(piepos[0] > this.position[0]) {
				this.position[0] += 1;
			}
			if(piepos[1] < this.position[1]) {
				this.position[1] -= 1;
			}
			if(piepos[1] > this.position[1]) {
				this.position[1] += 1;
			}			
		}
	}
	
	
	
	public void wander(int direction, maze map, int time) {
		
		if(time % 100 == 0) {
		switch(direction) {
		
		case 1: if(this.position[1] == map.getRowSize() - 1) {
					this.position[1] = 0;
					break;
				}
				else {
					this.position[1] += 1;
					break;
				}
		case 2: if(this.position[1] == 0) {
					this.position[1] = map.getRowSize() - 1;
					break;
				}
				else {
					this.position[1] -= 1;
					break;
				}
		case 3: if(this.position[0] == map.getColSize() - 1) {
					this.position[0] = 0;
					break;
				}
				else {
					this.position[0] += 1;
					break;
				}
		case 4: if(this.position[0] == 0) {
					this.position[0] = map.getColSize() - 1;
					break;
				}
				else {
					this.position[0] -= 1;
					break;
				}	
		
		}
		}
		
		
	}
	
	
	
}
