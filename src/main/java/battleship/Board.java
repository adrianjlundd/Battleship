package battleship;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int Size = 10;
    private final char[][] board; // '0' = water, 'S' = ship, 'X' = hit, 'M' = miss
    private final List<Ship> ships;

    public Board() {
        board = new char[Size][Size];
        ships = new ArrayList<>();
        initializeBoard();
        
    }
    private void initializeBoard() {
        for (int i = 0 ;i < 10 ; i++) {
            for (int ii = 0 ;ii < 10 ; ii++) {
                board[i][ii] = '0';
            }
        }
    }

    public boolean placeShip(Ship ship) {
        if (!isValidPlacement(ship)) {
            return false;
        }
        ships.add(ship);
        markShipOnBoard(ship);
        return true;
    }

    public void markShipOnBoard(Ship ship) {
        int row = ship.getRow();
        int col = ship.getCol();
        for (int i = 0 ; i < ship.getLength() ; i++) {
            if (ship.isHorizontal()) {
                board[row][col + i] = 'S';
            }
            else {
                board[row + i][col] = 'S';
            }
        }

    }

    public boolean isValidPlacement(Ship ship) {
        int row = ship.getRow();
        int col = ship.getCol();
        int length = ship.getLength();
        
        for (int i = 0 ; i < length ; i++) {
            if (ship.isHorizontal()) {
                int coll = col + i;
                if (!(row >= 0 && row < Size && coll >= 0 && coll < Size && board[row][coll] != 'S')) {
                    return false;
                }            

            }
            else {
                int roww = row + i;
                if (!(col >= 0 && col < Size && roww >= 0 && roww < Size && board[roww][col] != 'S')) {
                    return false;
                } 
            }            
        }
        return true;
    }

    public boolean shoot(int row, int col) {
        
        if (row < 0 || row >= Size || col < 0 || col >= Size) {
            return false;
        }
        // Sjekk om cellen allerede er truffet eller markert som bom
        if (board[row][col] == 'X' || board[row][col] == 'M') {
            return false;
        }
        
    
        if (board[row][col] == 'S') {
            board[row][col] = 'X';

            for (Ship ship : ships) {
                if (isPositionOnShip(ship, row, col)) {
                    int segment;
                    if (ship.isHorizontal()) {
                        segment = col - ship.getCol();  // Horisontal avstand fra skipets start
                    } else {
                        segment = row - ship.getRow();  // Vertikal avstand fra skipets start
                    }
                    ship.hit(segment);  // Registrer treff på dette segmentet
                    return true;        // Returner true hvis treff
                }
            }
        }

        board[row][col] = 'M';
        return false;

    }

    public boolean isPositionOnShip(Ship ship, int row, int col) {
        if (ship.isHorizontal()) {
            return (row == ship.getRow() && col >= ship.getCol() && col < (ship.getCol() + ship.getLength()) ); //utenfor vertikalt /horisontral
        }
        else {
            return (col == ship.getCol() && row >= ship.getRow() && row < (ship.getRow() + ship.getLength()) );
        }
    }

    public char[][] getBoard() {
        return board;
    }
    public List<Ship> getShips() {
        return ships;
    }
    public static int getSize() {
        return Size;
    }
}
