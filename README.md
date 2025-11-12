# Battleship

**Battleship** is a digital recreation of the classic board game, developed as part of the course **TDT4100 – Object-Oriented Programming** at NTNU during Spring 2025.  
This project was my first Java application, built using **JavaFX** for the graphical user interface and structured according to object-oriented principles.

## About the Game

The application allows two players to compete against each other by placing ships on a 10x10 grid and taking turns firing shots at the opponent’s board to sink their ships.  

Gameplay consists of three main phases:
1. **Start Menu** – Players enter their names and can view previous winners.  
2. **Placement Phase** – Each player places five ships of varying lengths on their own board.  
3. **Battle Phase** – Players alternate shooting at the opponent’s grid.  
   - Hits are marked with “X”  
   - Misses are marked with “M”  
   - The game ends when all ships of one player have been sunk.

The winner’s name is saved to a local file (`winners.txt`), which is displayed in the start menu for game history.

## Implementation Details

The game follows core object-oriented design principles:
- **Encapsulation:** Core logic divided across classes such as `Ship`, `Board`, and `Game`.
- **Delegation:** The `Game` class manages turn flow and interaction between players.
- **Interfaces:** The `Shootable` interface defines shared behavior for objects that can be targeted.
- **Exception Handling:** Used to ensure robust file operations when saving and loading winners.
- **Testing:** JUnit 5 tests verify ship placement, shooting logic, and winner file handling.

## Technologies Used
- **Java 23**
- **JavaFX**
- **JUnit 5**

---

