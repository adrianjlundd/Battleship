package battleship;

public class Ship implements Shootable {

    private final int row;
    private final int col;
    private final int length;
    private final boolean isHorizontal;
    private final boolean[] hits;

    public Ship(int row, int col, int length, boolean isHorizontal) {
        this.row = row;
        this.col = col;
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.hits = new boolean[length];
    }

    public boolean isSunk() {
        for (boolean hit : hits) {
            if (hit!=true) {
                return false;
            }
        }
        return true;
    }
    public boolean isHit(int position) {
        return hits[position];
    }
    public boolean hit(int position) {
        if (position >= 0 && position < length) {
            hits[position] = true;
            return true;
        }
        return false;
    }

    public int getCol() {
        return col;
    }
    public int getRow() { 
        return row; }
    
    public int getLength() {
        return length; }
    public boolean isHorizontal() {
        return isHorizontal; }


}
