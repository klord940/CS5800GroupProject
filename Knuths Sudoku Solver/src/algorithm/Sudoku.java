package algorithm;

import java.util.Arrays;
import java.util.List;

/**
 * Main class for setting up the exact cover problem with the constraints for Sudoku.  
 * This class will call the Dancing Links algorithm X runner, DLX.java.
 * 
 * @author Robert Wilson
 * Created: 09 DEC S2022
 * Class: CS 5800
 */
public class Sudoku {

	// Grid size
	private static final int SIZE = 9;
	
	// Box size
	private static final int BOX_SIZE = 3;
	private static final int EMPTY_CELL = 0;
	
	// 4 constraints : cell, line, column, boxes
	private static final int CONSTRAINTS = 5;
	
	// Values for each cells
	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = SIZE;
	
	// Starting index for cover matrix
	private static final int COVER_START_INDEX = 1;

	// Grids for initial and solved
	private int[][] grid;
	private int[][] gridSolved;

	/**
	 * Constructor for sudoku class with int[][] input.  Fills out the grid matrix 
	 * from the input grid.
	 * 
	 * @param grid input board from source with type int.
	 */
	public Sudoku(int[][] grid) {
		this.grid = new int[SIZE][SIZE];

	    for (int i = 0; i < SIZE; i++) {
	    	for (int j = 0; j < SIZE; j++) {
	    		this.grid[i][j] = grid[i][j];
	    	}	    		
	    }	    	
	}

	/**
	 * Constructor for Sudoku class with Integer[][] input.  Fills out the grid matrix
	 * from the input grid.
	 * 
	 * @param board input board from source with type Integer.
	 */
	public Sudoku(Integer[][] board) {
		this.grid = new int[SIZE][SIZE];

	    for (int i = 0; i < SIZE; i++) {
	    	for (int j = 0; j < SIZE; j++) {
	    		if(board[i][j]  == null) {
	    			this.grid[i][j] = 0;
	    		} else {
	    			this.grid[i][j] = board[i][j];
	    		}
	    	}	    		
	    }
	}

	/**
	 * Index in the cover matrix
	 * 
	 * @param row (int) the row in the cover matrix.
	 * @param column (int) the column in the cover matrix
	 * @param num (int) 
	 * @return
	 */
	private int indexInCoverMatrix(int row, int column, int num) {
		return (row - 1) * SIZE * SIZE + (column - 1) * SIZE + (num - 1);
  	}

	/**
	 * Building of an empty cover matrix, using the constraints outlined below for Hyper Sudoku.
	 * 
	 * @return coverMatrix (int[][]) the empty Cover Matrix with the constraints applied.
	 */
	private int[][] createCoverMatrix() {
		int[][] coverMatrix = new int[SIZE * SIZE * MAX_VALUE][SIZE * SIZE * CONSTRAINTS];

	    int header = 0;
	    header = createCellConstraints(coverMatrix, header);
	    header = createRowConstraints(coverMatrix, header);
	    header = createColumnConstraints(coverMatrix, header);
	    header = createBoxConstraints(coverMatrix, header);
	    header = createHyperBoxConstraints(coverMatrix, header);
	    
	    return coverMatrix;
  	}
	
	/**
	 * Helper function to covers the constraints for outside the hyper boxes, 
	 * which is the same as the cell constraints.
	 * 
	 * @param matrix (int[][]) Cover Matrix so far.
	 * @param header (int) the header to start at
	 * @return header (int) This should not be used by the parent constraint
	 */
	private int createHyperBoxConstraints2(int[][] matrix, int header) {		
		for (int row = COVER_START_INDEX; row <= SIZE; row++) {
			for (int column = COVER_START_INDEX; column <= SIZE; column++, header++) {
				// If we are inside the Hyper cubes do not place any constraints
				if(((row >= 2 && row <= 4) && (column >= 2 && column <= 4)) || ((row >= 2 && row <= 4) && (column >= 6 && column <= 8)) || ((row >= 6 && row <= 8) && (column >= 2 && column <= 4)) || ((row >= 6 && row <= 8) && (column >= 6 && column <= 8))) {
					//System.out.println("row: " + row + "\tColumn: " + column + "\tNumber: " + n + "\tHeader: " + header);
					continue;
				}
				for (int n = COVER_START_INDEX; n <= SIZE; n++) {
					//System.out.println("row: " + row + "\tColumn: " + column + "\tNumber: " + n);
					int index = indexInCoverMatrix(row, column, n);
					matrix[index][header] = 1;
				}
			}
		}
	    return header;
	}

	/**
	 * Creates the constraints for the Hyper portion of the Hyper Sudoku
	 * 
	 * @param matrix (int[][]) Cover Matrix so far.
	 * @param header (int) the header to start at
	 * @return header (int) The final header at the end of this project...  So far...
	 */
	private int createHyperBoxConstraints(int[][] matrix, int header) {
		// Create the constraints outside of the hyper cubes
		createHyperBoxConstraints2(matrix, header);
		
		// Initialize starter variables
		int count = 0;
		header += 10;
		
		// Iterate through the Hyper cubes
		for(int row = (COVER_START_INDEX + 1); row <= SIZE; row += (BOX_SIZE + 1)) {
			for(int column = (COVER_START_INDEX + 1); column <= SIZE; column += (BOX_SIZE + 1)) {
				for(int n = COVER_START_INDEX; n <= SIZE; n++, header++) {
					for(int rowDelta = 0; rowDelta < BOX_SIZE; rowDelta++) {
						for(int columnDelta = 0; columnDelta < BOX_SIZE; columnDelta++) {
							int index = indexInCoverMatrix(row + rowDelta, column + columnDelta, n);
							matrix[index][header] = 1;
						}
					}
					
					// At the end of a boxes size we need to skip a header
					if(n % BOX_SIZE == 0 ) {
						header++;
						count++;
					}
					
					// At the end of a row we need to add another header to account for starting at column 2 
					if(count == (BOX_SIZE-1)) {
						header++;
						count = 0;
					}
				}
			}
			// Can make this better
			// Skip rows as needed
			if(row == 2) {
				header += SIZE;
			} else {
				header += SIZE-1;
			}
		}
		return header;
	}
	
	/**
	 * Creates the Box constraints which are BOXSIZE x BOXSIZE. 
	 * Only values from MIN_VALUE through MAX_VALUE in the BOX_SIZE once.
	 * 
	 * @param matrix (int[][]) The cover matrix so far.
	 * @param header (int) the head of the matrix so far
	 * @return header (int) the head after running through the constraints
	 */
	private int createBoxConstraints(int[][] matrix, int header) {
		for (int row = COVER_START_INDEX; row <= SIZE; row += BOX_SIZE) {
			for (int column = COVER_START_INDEX; column <= SIZE; column += BOX_SIZE) {
				for (int n = COVER_START_INDEX; n <= SIZE; n++, header++) {
					for (int rowDelta = 0; rowDelta < BOX_SIZE; rowDelta++) {
						for (int columnDelta = 0; columnDelta < BOX_SIZE; columnDelta++) {
							int index = indexInCoverMatrix(row + rowDelta, column + columnDelta, n);
							matrix[index][header] = 1;
						}
					}
				}
			}
		}
		return header;
	}

	/**
	 * Creates the Column constraints in which only one instance of MIN_VALUE 
	 * through SIZE can be in each column. 
	 * 
	 * @param matrix (int[][]) The cover matrix so far.
	 * @param header (int) The header of the matrix so far.
	 * @return header (int)  The header after running through the constraints
	 */
	private int createColumnConstraints(int[][] matrix, int header) {
		for (int column = COVER_START_INDEX; column <= SIZE; column++) {
			for (int n = COVER_START_INDEX; n <= SIZE; n++, header++) {
				for (int row = COVER_START_INDEX; row <= SIZE; row++) {
					int index = indexInCoverMatrix(row, column, n);
					matrix[index][header] = 1;
				}
			}
    	}
	    return header;
	}

	/**
	 * Creates the row constraints in which only one instance of MIN_VALUE
	 * through SIZE can be in each row. 
	 * 
	 * @param matrix (int[][]) The cover matrix so far.
	 * @param header (int)  The header of the matrix so far.
	 * @return header (int)  The header after running through the constraints.
	 */
	private int createRowConstraints(int[][] matrix, int header) {
		for (int row = COVER_START_INDEX; row <= SIZE; row++) {
			for (int n = COVER_START_INDEX; n <= SIZE; n++, header++) {
				for (int column = COVER_START_INDEX; column <= SIZE; column++) {
					int index = indexInCoverMatrix(row, column, n);
					matrix[index][header] = 1;
				}
			}
		}
	    return header;
	}

	/**
	 * Creates the cell constraints in which only one instance of MIN_VALUE
	 * through SIZE can be in each cell.
	 * 
	 * @param matrix (int[][])  The cover matrix so far.
	 * @param header (int)  The header of the matrix so far.
	 * @return header (int)  The header after running through the constraints.
	 */
	private int createCellConstraints(int[][] matrix, int header) {
		for (int row = COVER_START_INDEX; row <= SIZE; row++) {
			for (int column = COVER_START_INDEX; column <= SIZE; column++, header++) {
				for (int n = COVER_START_INDEX; n <= SIZE; n++) {
					int index = indexInCoverMatrix(row, column, n);
					matrix[index][header] = 1;
				}
			}
		}
	    return header;
	}

	/**
	 * Converting Sudoku grid as a cover matrix, given an input matrix.
	 * 
	 * @param grid (int[][])  Input grid of the original Soduku problem.
	 * @return coverMatrix (int[][])  The cover matrix with the Sudoku
	 * 			 constraints added as well as the inputs from the given problem.
	 */
	private int[][] convertInCoverMatrix(int[][] grid) {
		int[][] coverMatrix = createCoverMatrix();

		// Taking into account the values already entered in Sudoku's grid instance
		for (int row = COVER_START_INDEX; row <= SIZE; row++) {
			for (int column = COVER_START_INDEX; column <= SIZE; column++) {
				int n = grid[row - 1][column - 1];

				if (n != EMPTY_CELL) {
					for (int num = MIN_VALUE; num <= MAX_VALUE; num++) {
						if (num != n) {
							Arrays.fill(coverMatrix[indexInCoverMatrix(row, column, num)], 0);
						}
					}
				}
			}
		}
	    return coverMatrix;
	}

	/**
	 * Converts the Sudoku problem into an Exact cover problem with the proper constraints.
	 * Then it creates a quadruple chained list and calls the DLX solve method, Algorithm X.
	 * Lastly it converts the solution back to a human readable sudoku solution.  If there is 
	 * a solution it returns true and if not then it returns false.  
	 * 
	 * @return solved (boolean)  true if it has a solution and false otherwise.
	 */
	public boolean solve() {
		// Boolean for whether there is a solution or not
		boolean solved = false;
		
		// convert the input grid into a cover matrix
		int[][] cover = convertInCoverMatrix(grid);
		
		// create a quadruple chained double linked list
		DLX dlx = new DLX(cover);
		
		// Use Algorithm X and Dancing Links to solve the problem
		dlx.solve();
		
		// Convert the cover solution back to a Sudoku style grid
		gridSolved = convertDLXListToGrid(dlx.result);
		
		// validate whether a solution has been found and print to console
		solved = validateBoard();
		System.out.println(solved);
		
		// Display both grids, before and after algorithm to the console
		displaySolution();
		
		// Return boolean of whether the problem has been solved
		return solved;
	}

	/**
	 * Displays the original grid and the solution grid to the console terminal.
	 * 
	 */
	private void displaySolution() {
		// Solved grid for display
		for(int[] i : gridSolved) {
			for(int j : i) {
				System.out.print(j);
			}
			System.out.println();
		}
		
		System.out.println("\n");
		
		// Original problem for display
		for(int[] i : grid) {
			for(int j : i) {
				System.out.print(j);
			}
			System.out.println();
		}
	}

	/**
	 * Counts and displays the number of option selections made.
	 * Mainly for debugging.
	 * 
	 * @param matrix (int[][]) Cover Matrix to be processed
	 * @return count (int) The number of 1s in the cover matrix, 
	 * 						indicating an option is selected
	 */
	private int countMatrix(int[][] matrix) {
		int count = 0;
		for(int[] i : matrix) {
			for(int j : i) {
				if(j == 1) count++;
			}
		}
		return count;
	}
	
	/**
	 * Prints the full cover matrix out. mainly for debugging.
	 * 
	 * @param matrix (int[][]) Cover matrix to print out
	 */
	private void printMatrix(int[][] matrix) {
		for(int[] i : matrix) {
			for(int j : i) {
				System.out.print(j);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns the answer grid after the algorithm has been run.  If the 
	 * algorithm does not come up with a solution then return the original grid 
	 * (answer will be null in this case).  
	 * 
	 * @param answer (List<DancingNode>) the answer list from the algorithm
	 * @return result/grid (int[][]) The answer as a 2d grid or the original 
	 * 			grid if answer is null.
	 */
	private int[][] convertDLXListToGrid(List<DancingNode> answer) {
		// If there is no solution then return original grid
		if(answer == null) {
			return this.grid;
		}
		
		// initialize answer grid
		int[][] result = new int[SIZE][SIZE];

		for (DancingNode n : answer) {
			DancingNode rcNode = n;
			int min = Integer.parseInt(rcNode.column.name);

			for (DancingNode tmp = n.right; tmp != n; tmp = tmp.right) {
				int val = Integer.parseInt(tmp.column.name);

				if (val < min) {
					min = val;
					rcNode = tmp;
			  	}
	  		}

			// we get row and column
			int ans1 = Integer.parseInt(rcNode.column.name);
			int ans2 = Integer.parseInt(rcNode.right.column.name);
			int r = ans1 / SIZE;
			int c = ans1 % SIZE;
			
			// and the affected value
			int num = (ans2 % SIZE) + 1;
			
			// we affect that on the result grid
			result[r][c] = num;
		}
		return result;
	}

	/**
	 * Validates whether the solution is the same as the input grid
	 * and returns true if it is not, false otherwise. 
	 * 
	 * @return (bool) true if not the same as the input, false if it is.
	 */
	private boolean validateBoard() {
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(grid[i][j] != gridSolved[i][j] ) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * If this is run as a stand alone then use the trail grid provided.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Trial input grids
		
		int [][] inputGrid1 = {
				{0, 0, 3, 0, 1, 0, 0, 0, 0},
				{4, 1, 5, 0, 0, 0, 0, 9, 0},
				{2, 0, 6, 5, 0, 0, 3, 0, 0},
				{5, 0, 0, 0, 8, 0, 0, 0, 9},
				{0, 7, 0, 9, 0, 0, 0, 3, 2},
				{0, 3, 8, 0, 0, 4, 0, 6, 0},
				{0, 0, 0, 2, 6, 0, 4, 0, 3},
				{0, 0, 0, 3, 0, 0, 0, 0, 8},
				{3, 2, 0, 0, 0, 7, 9, 5, 0}				
		};
		
		
		int [][] inputGrid2 = {
				{0, 0, 0, 0, 0, 0, 3, 0, 0},
				{1, 0, 0, 4, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 1, 0, 5},
				{9, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 2, 6, 0, 0},
				{0, 0, 0, 0, 5, 3, 0, 0, 0},
				{0, 5, 0, 8, 0, 0, 0, 0, 0},
				{0, 0, 0, 9, 0, 0, 0, 7, 0},
				{0, 8, 3, 0, 0, 0, 0, 4, 0}				
		};
		
		int[][] HyperInput = {
				{1, 0, 6, 2, 4, 3, 0, 0, 0},
				{2, 3, 0, 7, 0, 9, 0, 5, 6},
				{7, 0, 9, 5, 0, 6, 2, 4, 0},
				{9, 6, 0, 1, 5, 7, 0, 3, 0},
				{5, 0, 3, 8, 6, 2, 9, 0, 7},
				{8, 7, 0, 3, 0, 4, 0, 0, 5},
				{4, 0, 5, 6, 0, 8, 3, 0, 0},
				{6, 9, 0, 4, 0, 1, 0, 7, 0},
				{3, 1, 7, 9, 2, 5, 4, 6, 8}
		};
		
		int[][] HyperInput_solution = {
				{1, 5, 6, 2, 4, 3, 7, 8, 9},
				{2, 3, 4, 7, 8, 9, 1, 5, 6},
				{7, 8, 9, 5, 1, 6, 2, 4, 3},
				{9, 6, 2, 1, 5, 7, 8, 3, 4},
				{5, 4, 3, 8, 6, 2, 9, 1, 7},
				{8, 7, 1, 3, 9, 4, 6, 2, 5},
				{4, 2, 5, 6, 7, 8, 3, 9, 1},
				{6, 9, 8, 4, 3, 1, 5, 7, 2},
				{3, 1, 7, 9, 2, 5, 4, 6, 8}
		};
		
		
		
		int[][] HyperInput2 = {
				{0, 0, 0, 0, 0, 6, 0, 0, 0},
				{4, 6, 0, 0, 0, 0, 0, 8, 9},
				{0, 0, 5, 0, 1, 0, 6, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 9, 0},
				{1, 0, 0, 0, 3, 0, 8, 0, 0},
				{0, 5, 0, 0, 0, 0, 4, 0, 0},
				{0, 8, 1, 0, 0, 2, 0, 0, 6},
				{0, 0, 0, 0, 9, 1, 0, 7, 0},
				{6, 0, 0, 5, 8, 0, 2, 1, 0}
		};
		
		int[][] input_4 = {
				{0, 1, 0, 0},
				{0, 2, 3, 0},
				{1, 0, 0, 0},
				{0, 0, 2, 0}
		};
		
		// create an instance of itself to solve
		Sudoku s = new Sudoku(HyperInput);
		
		// Solve said instance
		s.solve();
	}
}