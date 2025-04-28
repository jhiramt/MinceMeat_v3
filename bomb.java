public class bomb {
        

        private int[] position = new int[2];

        private boolean placed = false;

        private boolean placeTurn = false;

        private boolean exploding = false;
        
        private int fuse = 0;

        private int range = 1;

        public bomb() {
                this.placed = false;
                this.exploding = false;
        }
                
	public int[] getPosition() {
		return this.position;
	}
	public void setPosition(int row, int col) {
		this.position[0] = row;
		this.position[1] = col;
	}
        
        public boolean getPlaced() {
                return this.placed;
        }

        public void setPlaced(boolean place) {
                this.placed = place;
        }


        public boolean getPlaceTurn() {
                return this.placeTurn;
        }

        public void setPlaceTurn(boolean placet) {
                this.placeTurn = placet;
        }


        public int getRange() {
                return this.range;
        }

        
        public boolean getExploding() {
                return this.exploding;
        }

        public void setExploding(boolean explode) {
                this.exploding = explode;
        }

        public void tickFuse() {
                this.fuse = this.fuse - 1;
                this.setExploding(false);
        }

        public void setFuse(int fuse){
                this.fuse = fuse;
        }

        public int getFuse(){
                return this.fuse;
        }



        public void explode(maze map, player pie, meat meat, mince mince){
                this.setExploding(true);
                int[] colCheck = new int[] {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] rowCheck = new int[] {-1, 0, 1, -1, 1, -1, 0, 1};
		int[] check = new int[2];

                for(int i = 0; i < 8; i++) {
			check[0] = this.position[0] + colCheck[i];
			check[1] = this.position[1] + rowCheck[i];
                        if(!(check[0] < 0) && !(check[0] > map.getColSize() - 1) && !(check[1] < 0) && !(check[1] > map.getRowSize() - 1)) {
                        map.setMazePoint(check[0], check[1], 0);
                        if(check[0] == pie.getPosition()[0] && check[1] == pie.getPosition()[1]){
                                pie.setAlive(false);
                        }
                        if(check[0] == mince.getPosition()[0] && check[1] == mince.getPosition()[1]){
                                mince.setAlive(false);
                        }
                        if(check[0] == meat.getPosition()[0] && check[1] == meat.getPosition()[1]){
                                meat.setAlive(false);
                        }
                        
                }
                }

                
                map.setMazePoint(this.position[0], this.position[1], 0);
                pie.placeBomb(false);
                this.placed = false;

        }



        

}
