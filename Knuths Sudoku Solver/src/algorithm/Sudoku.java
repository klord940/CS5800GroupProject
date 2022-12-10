package algorithm;

import java.util.Arrays;
import java.util.List;

public class Sudoku {

	// Grid size
	private static final int SIZE = 9;
	
	// Box size
	private static final int BOX_SIZE = 3;
	private static final int EMPTY_CELL = 0;
	
	// 4 constraints : cell, line, column, boxes
	private static final int CONSTRAINTS = 4;
	
	// Values for each cells
	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = SIZE;
	
	// Starting index for cover matrix
	private static final int COVER_START_INDEX = 1;

	// Grids for initial and solved
	private int[][] grid;
	private int[][] gridSolved;

	
	public Sudoku(int[][] grid) {
		this.grid = new int[SIZE][SIZE];

	    for (int i = 0; i < SIZE; i++) {
	    	for (int j = 0; j < SIZE; j++) {
	    		this.grid[i][j] = grid[i][j];
	    	}	    		
	    }	    	
	}

	// Index in the cover matrix
	private int indexInCoverMatrix(int row, int column, int num) {
		return (row - 1) * SIZE * SIZE + (column - 1) * SIZE + (num - 1);
  	}

	// Building of an empty cover matrix
	private int[][] createCoverMatrix() {
		int[][] coverMatrix = new int[SIZE * SIZE * MAX_VALUE][SIZE * SIZE * CONSTRAINTS];

	    int header = 0;
	    header = createCellConstraints(coverMatrix, header);
	    header = createRowConstraints(coverMatrix, header);
	    header = createColumnConstraints(coverMatrix, header);
	    createBoxConstraints(coverMatrix, header);

	    return coverMatrix;
  	}

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

	// Converting Sudoku grid as a cover matrix
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
	  
	public void solve() {
		int[][] cover = convertInCoverMatrix(grid);
		//printCoverMatrix(cover);
		DLX dlx = new DLX(cover);
		dlx.solve();
		gridSolved = convertDLXListToGrid(dlx.result);
		displaySolution();
	}
	  
	private void displaySolution() {
		// TODO Auto-generated method stub
		for(int[] i : gridSolved) {
			for(int j : i) {
				System.out.println(j);
			}
		}
	}

	private int[][] convertDLXListToGrid(List<DancingNode> answer) {
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

			// we get line and column
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
	
	public static void main(String[] args) {
		
	}
}