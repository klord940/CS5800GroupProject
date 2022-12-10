
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Sudoku model contains all functionality of the game.
 * 
 * @author Robert Wilson
 * Created: 19 NOV 2022
 * Class: CS5800 
 */
public class Model {
	
	// The Game Board
	private Integer[][] board;
	
	// GameState
	boolean validPuzzle;
	boolean gameOver;
	
	// The size of our board[size][size]
	private final int size = 9;
	
	/**
	 * Constructor creates a new board of size*size, populates it with the initial conditions, and sets the win condition to false.
	 */
	public Model() {
		this.board = new Integer[size][size];
		this.validPuzzle = false;
		this.gameOver = false;
		// Populate with input board
	}
	
	
	/**
	 * Execute a move in the position specified by the given row and column.
	 *
	 * @param r the row of the intended move
	 * @param c the column of the intended move
	 * @throws IllegalArgumentException if the position is invalid
	 * @throws IllegalStateException if the game is over
	 */
	public void move(int r, int c) {
		// If the game is over then you cannot play
		if(!gameOver) {
			// Check it is a valid button to press
			if(r < 0 || r > size || c > size || c < 0) {
				throw new IllegalArgumentException("This move is out of bounds.");
			}
			// Check whether there is a number there or not
			if(this.board[r][c] == null) {
				this.board[r][c] = 1;
				return;
			}
			// If the number is 1 to 8 then increment by one, set to null otherwise.  
			// If the number is an input parameter, then it cannot be changed
			if(this.board[r][c].intValue()>0 && this.board[r][c].intValue()<9) {
				this.board[r][c] += 1;
				return;
			} else if (this.board[r][c].intValue() == 9) {
				this.board[r][c] = null;
				return;
			} throw new IllegalArgumentException("Spot Cannot be Changed.");
		} throw new IllegalStateException("Game is over.");
	}
	
	/**
	 * Returns whether the input is a valid Soduku puzzle
	 * 
	 * @return true if the given puzzle is valid	 
	 */
	public boolean isValidPuzzle() {
		return validPuzzle;
	}
	
	/**
	 * Return whether the game is over. The board must be full, if so then the player has to press the check solution button. 
	 * If these conditions have been met then the method will proceed to check if the given input is a solution.
	 *
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * Return the current Integer mark at a given row and column, or {@code null} if the
	 * position is empty.
	 *
	 * @param r the row
	 * @param c the column
	 * @return the Integer at the given location, or null if it's empty
	 */
	public Integer getMarkAt(int r, int c) {
		return this.board[r][c];
	}
}