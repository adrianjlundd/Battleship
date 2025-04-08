package battleship;

public class Player {
    private final String name;
    private final Board board;

    public Player(String name) {
        this.name = name;
        this.board = new Board();
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public boolean hasLost() {
        for (Ship ship : board.getShips()) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
}