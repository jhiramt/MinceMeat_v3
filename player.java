public class player {

	private int[] position = new int[2];
	
	private boolean hasKey = false;
	private boolean alive = true;
	private String represent;
	
	public player(int row, int collumn) {
		this.position[0] = row;
		this.position[1] = collumn;
		this.represent = "o";
	}
	
	public int[] getPosition(){
		return this.position;
	}
	
	public void setPosition(int row, int col) {
		this.position[0] = row;
		this.position[1] = col;
	}
	
	public void setKey(boolean has) {
		this.hasKey = has;
		if(has == true) {
			this.represent = "õ";
		}
		else {
			this.represent = "o";
		}
	}
	
	public boolean hasKey() {
		return this.hasKey;
	}
	
	public void setAlive(boolean life) {
		this.alive = life;
	}
	
	public boolean alive() {
		return this.alive;
	}
	
	public String rep() {
		return this.represent;
	}
	
	
	public void movePlayer(int direction, maze map){
				
		switch(direction) {
		
		
		case 1: 	if(this.position[1] == map.getRowSize() - 1) {
						if(map.getMazePoint(this.position[0], 0) != 1) {
						this.position[1] = 0;
						}
						break;
					}		
					else if(map.getMazePoint(this.position[0], this.position[1] + 1) != 1) {
						this.position[1] += 1;
						break;
					}
					else {
						break;
					}
		
		case 2:		if(this.position[1] == 0) {
						if(map.getMazePoint(this.position[0], map.getRowSize() - 1) != 1) {
						this.position[1] = map.getRowSize() - 1;
						}
						break;
					}		
					else if(map.getMazePoint(this.position[0], this.position[1] - 1) != 1) {
						this.position[1] -= 1;
						break;
					}
					else {
						break;
					}
		
		case 3:		if(this.position[0] == 0) {
						if(map.getMazePoint(map.getColSize() - 1, position[1]) != 1) {
						this.position[0] = map.getColSize() - 1;
						}
						break;
					}		
					else if(map.getMazePoint(this.position[0] - 1, this.position[1]) != 1) {
						this.position[0] -= 1;
						break;
					}
					else {
						break;
					}
		
		case 4: 	if(this.position[0] == map.getColSize() - 1) {
						if(map.getMazePoint(0, position[1]) != 1) {
						this.position[0] = 0;
						}
						break;
					}		
					else if(map.getMazePoint(this.position[0] + 1, this.position[1]) != 1) {
						this.position[0] += 1;
						break;
					}
					else {
						break;
					}
		
					}
		
	}

	
}
