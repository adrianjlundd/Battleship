package battleship;

public class Game {
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private Player opponent;

    public Game(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.currentPlayer = player1;
        this.opponent = player2;
    }

    public boolean attack(int row, int col) {
        // Angrip motstanderens brett
        boolean hit = opponent.getBoard().shoot(row, col);
        return hit;
    }

    public void switchTurn() {
        // Bytt tur
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
    }

    public boolean isGameOver() {
        return player1.hasLost() || player2.hasLost();
    }

    public Player getWinner() {
        if (player1.hasLost()) {
            return player2;
        } else if (player2.hasLost()) {
            return player1;
        }
        return null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpponent() {
        return opponent;
    }
}