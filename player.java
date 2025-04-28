public class player {
	
	static int defaultBombNumber = 100;
	
	private int[] position = new int[2];
	
	private boolean hasKey;
	private boolean alive;
	private int numBombs;
	private boolean bombPlaced;
	private String represent;
	private boolean moved;
	
	public player(int row, int collumn) {
		this.position[0] = row;
		this.position[1] = collumn;
		this.represent = "o";
		this.hasKey = false;
		this.alive = true;
		this.numBombs = defaultBombNumber;
		this.bombPlaced = false;
		this.moved = false;
	}

	public void copyPlayer(player pie){
		this.position = pie.getPosition();
	}
	
	public boolean getMoved() {
		return this.moved;
	}
	
	public void setMoved(boolean move) {
		this.moved = move;
	}
	
	public int[] getPosition() {
		return this.position;
	}
	
	public void setPosition(int row, int col) {
		this.position[0] = row;
		this.position[1] = col;
	}
	
	public void resetBombs(){
		this.numBombs = defaultBombNumber;
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
	
	public int getBombs() {
		return this.numBombs;
	}
	
	public void subBomb() {
		this.numBombs -= 1;
	}
	
	public void setBombs(int num) {
		this.numBombs = num;
	}
	
	public void placeBomb(boolean place) {
		this.bombPlaced = place;
	}

	public boolean isBombPlaced() {
		return this.bombPlaced;
	}
	
	public void moveplayer(int direction, maze map, bomb bomb){

		switch(direction) {
		
		case 0:		if(this.getBombs() > 0 && !this.isBombPlaced()) {
					this.placeBomb(true);
					this.subBomb();
					bomb.setPlaceTurn(true);
				}
				break;
		
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


		public boolean abmoveplayer(int direction, maze map, bomb bomb){

		switch(direction) {
		
		case 0:		if(this.getBombs() > 0 && !this.isBombPlaced()) {
					this.placeBomb(true);
					this.subBomb();
					bomb.setPlaceTurn(true);
					return true;
				}
				else{
					return false;
				}
		
		case 1: 	if(this.position[1] == map.getRowSize() - 1) {
						if(map.getMazePoint(this.position[0], 0) != 1) {
						this.position[1] = 0;
						return true;
						}
						else{
							return false;
						}
					}		
					else if(map.getMazePoint(this.position[0], this.position[1] + 1) != 1) {
						this.position[1] += 1;
						return true;
					}
					else {
						return false;
					}
		
		case 2:		if(this.position[1] == 0) {
						if(map.getMazePoint(this.position[0], map.getRowSize() - 1) != 1) {
						this.position[1] = map.getRowSize() - 1;
						return true;
						}
						else{
							return false;
						}
					}		
					else if(map.getMazePoint(this.position[0], this.position[1] - 1) != 1) {
						this.position[1] -= 1;
						return true;
					}
					else {
						return false;
					}
		
		case 3:		if(this.position[0] == 0) {
						if(map.getMazePoint(map.getColSize() - 1, position[1]) != 1) {
						this.position[0] = map.getColSize() - 1;
						return true;
						}
						else{
							return false;
						}
					}		
					else if(map.getMazePoint(this.position[0] - 1, this.position[1]) != 1) {
						this.position[0] -= 1;
						return true;
					}
					else {
						return false;
					}
		
		case 4: 	if(this.position[0] == map.getColSize() - 1) {
						if(map.getMazePoint(0, position[1]) != 1) {
						this.position[0] = 0;
						return true;
						}
						else{
							return false;
						}
					}		
					else if(map.getMazePoint(this.position[0] + 1, this.position[1]) != 1) {
						this.position[0] += 1;
						return true;
					}
					else {
						return false;
					}
		
		}
		return false;
		
	}

	
}
