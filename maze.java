public class maze {

	private static int colsize = 25;	//25
	private static int rowsize = 50;	//50
	private int[] row = new int[rowsize];
	private int[] col = new int[colsize];
	private int[][] labrynth = new int[rowsize][colsize];
	private int[] safeSpawn = new int[4];

	private int[] doorPos = new int[2];
	private int[] keyPos = new int[2];

	public maze() {
		this.row = fill(rowsize);
		this.col = fill(colsize);
		this.labrynth = generateMaze();
	}

	public void copyMaze(maze map) {
		for(int row = 0; row < 25; row++){
			for(int col = 0; col < 50; col++){
				//System.out.println(map.getMazePoint(row, col));
				this.setMazePoint(row, col, map.getMazePoint(row, col));
			}
		}
	}

	public void setMaze(int[][] lab) {
		this.labrynth = lab;
	}

	public int[][] getMaze() {
		return this.labrynth;
	}

	public int getMazePoint(int row, int col) {
		return this.labrynth[row][col];
	}

	public void setMazePoint(int row, int col, int val) {
		this.labrynth[row][col] = val;
	}

	public int[] fill(int size) {
		int[] ret = new int[size];
		for (int i = 0; i < size; i++) {
			ret[i] = (int) ((Math.random() * 10));
		}
		return ret;
	}

	public int getColSize() {
		return colsize;
	}

	public int getRowSize() {
		return rowsize;
	}

	public void setSafeSpawn(int val, int place) {
		this.safeSpawn[place] = val;
	}

	public int[] getSafeSpawn() {
		return this.safeSpawn;
	}

	public int getSafeSpawn(int val) {
		return this.safeSpawn[val];
	}

	public int[] getKeyPos() {
		return this.keyPos;
	}

	public int kp1() {
		return this.keyPos[0];
	}

	public int kp2() {
		return this.keyPos[1];
	}

	public void setKeyPos(int row, int col) {
		this.keyPos[0] = row;
		this.keyPos[1] = col;
	}

	public int[] getDoorPos() {
		return this.doorPos;
	}

	public int dp1() {
		return this.doorPos[0];
	}

	public int dp2() {
		return this.doorPos[1];
	}

	public void setDoorPos(int row, int col) {
		this.doorPos[0] = row;
		this.doorPos[1] = col;
	}

	private int[][] generateMaze() {
		int[][] ret = new int[colsize][rowsize];

		for (int i = 0; i < colsize; i++) {
			for (int j = 0; j < rowsize; j++) {
				if (((this.row[j] + this.col[i]) % 10) >= 7) { //Make 7 for actual game
					ret[i][j] = 1;
				} else {
					ret[i][j] = 0;
					;
				}
			}
		}

		int rowVal = (int) (Math.random() * 5) + 6;
		this.setSafeSpawn(rowVal, 0);
		for (int i = 0; i < rowsize; i++) {
			ret[rowVal][i] = 0;
			ret[rowVal][i] = 0;
		}
		rowVal = (int) (Math.random() * 5) + 15;
		this.setSafeSpawn(rowVal, 1);
		for (int i = 0; i < rowsize; i++) {
			ret[rowVal][i] = 0;
		}
		int colVal = (int) (Math.random() * 5) + 7;
		this.setSafeSpawn(colVal, 2);
		for (int i = 0; i < colsize; i++) {
			ret[i][colVal] = 0;
			ret[i][colVal + 1] = 0;
		}
		colVal = (int) (Math.random() * 5) + 32;
		this.setSafeSpawn(colVal, 3);
		for (int i = 0; i < colsize; i++) {
			ret[i][colVal] = 0;
			ret[i][colVal - 1] = 0;
		}

		// this.dp1()
		this.setDoorPos((int) (Math.random() * (colsize - 2)) + 1, (int) (Math.random() * (rowsize - 2)) + 1);
		ret[this.doorPos[0]][this.doorPos[1]] = 8;
		int[] colClear = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] rowClear = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };
		int[] clear = new int[2];

		for (int i = 0; i < 8; i++) {
			clear[0] = this.doorPos[0] + colClear[i];
			clear[1] = this.doorPos[1] + rowClear[i];
			ret[clear[0]][clear[1]] = 0;
		}

		this.setKeyPos(this.doorPos[0], this.doorPos[1]);
		int[] keypos = this.getKeyPos();

		boolean tru = ((Math.abs(keypos[0] - this.getSafeSpawn(0)) < 3))
				|| (Math.abs(keypos[0] - this.getSafeSpawn(1)) < 3) || (Math.abs(keypos[1] - this.getSafeSpawn(2)) < 3)
				|| (Math.abs(keypos[1] - this.getSafeSpawn(3)) < 3);
		int halt = 10;
		while ((Math.sqrt(Math.pow((keypos[0] - this.doorPos[0]), 2) + Math.pow(keypos[1] - this.doorPos[1], 2)) <= 7)
				|| (tru && halt > 0)) {
			this.setKeyPos((int) (Math.random() * colsize - 1), (int) (Math.random() * rowsize - 1));
			halt -= 1;
			//System.out.println(keypos[0] + ", " + keypos[1]);
		}
		ret[this.keyPos[0]][this.keyPos[1]] = 7;

		return ret;

	}

	public String printMap(int[][] map, player pie, bomb bomb, boolean vis) {

		int[] piepos = pie.getPosition();
		int[] bombpos = bomb.getPosition();
		String ret = "";
		for (int i = 0; i < colsize; i++) {
			for (int j = 0; j < rowsize; j++) {
				// Math.abs(i - piepos[0]) < 3 && Math.abs(j - piepos[1]) < 4
				if ((Math.sqrt(Math.pow((i - piepos[0]), 2) + Math.pow(j - piepos[1], 2)) <= 4.25) || ((Math.sqrt(Math.pow((i - bombpos[0]), 2) + Math.pow(j - bombpos[1], 2)) <= (bomb.getRange() * 1.5)) && bomb.getPlaced())|| vis)  { //4.25 for limited view
					if (map[i][j] == 1) {
						ret += "#"; // █
					} else if (map[i][j] == 2) {
						ret += pie.rep(); // õ
					} else if (map[i][j] == 3) {
						ret += "þ"; // 🜘 þ
					} else if (map[i][j] == 4) {
						ret += "§"; // ☤ 🜑 🜻 §
					} else if (map[i][j] == 7) {
						ret += "~"; // ¶
					} else if (map[i][j] == 8) {
						ret += "[]";
					} else if (map[i][j] == 6) {
						ret += "ò";
					} else if (map[i][j] == 9) {
						ret += "*";
					} else {
						ret += " ."; // ░▒
					}
				} else {
					ret += "  ";
				}

			}
			ret += "\n";
		}
		return ret;

	}

}
