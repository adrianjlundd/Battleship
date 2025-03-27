package battleship;

public interface Shootable {

    boolean hit(int position);
    boolean isSunk();
}
