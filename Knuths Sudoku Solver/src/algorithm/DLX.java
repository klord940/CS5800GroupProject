package algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Dancing Links quadruple linked list and Algorithm X.  
 * Process method runs algorithm X. CreateDLXList creates the 
 * linked list.
 * 
 * @author Robert Wilson
 * Created: 09 DECS 2022
 * Class: CS 5800
 */
public class DLX {

	// header for each columnNode
	private ColumnNode header;
	
	// The answer List is used to create the cover for each iteration
	private List<DancingNode> answer;
	
	// The result list is only built if the solution is found
	public List<DancingNode> result;

	/**
	 * Constructor for a given cover matrix.
	 * 
	 * @param cover (int[][]) The cover matrix to input into the DLX
	 */
	public DLX(int[][] cover) {
		header = createDLXList(cover);
	}

	/**
	 * Creates a Dancing Links quad links List.
	 * 
	 * @param grid (int[][]) cover grid to be turned into list
	 * @return headerNode (ColumnNode)
	 */
	private ColumnNode createDLXList(int[][] grid) {
		final int nbColumns = grid[0].length;
		ColumnNode headerNode = new ColumnNode("header");
		List<ColumnNode> columnNodes = new ArrayList<>();

		for (int i = 0; i < nbColumns; i++) {
			ColumnNode n = new ColumnNode(i + "");
			columnNodes.add(n);
			headerNode = (ColumnNode) headerNode.linkRight(n);
		}

		headerNode = headerNode.right.column;

		for (int[] aGrid : grid) {
			DancingNode prev = null;

			for (int j = 0; j < nbColumns; j++) {
				if (aGrid[j] == 1) {
					ColumnNode col = columnNodes.get(j);
					DancingNode newNode = new DancingNode(col);

					if (prev == null) {
						prev = newNode;
					}
					
					col.top.linkDown(newNode);
					prev = prev.linkRight(newNode);
					col.size++;
				}
			}
		}

		headerNode.size = nbColumns;

		return headerNode;
	}

	/**
	 * Algorithm X run through. 
	 * 
	 * @param k (int) the iteration we are on. Starts at zero 
	 */
	private void process(int k) {
		if (header.right == header) {
			// End of Algorithm X
			// Result is copied in a result list
			result = new LinkedList<>(answer);
		} else {
			// we choose column c
		    ColumnNode c = selectColumnNodeHeuristic();
		    c.cover();

		    for (DancingNode r = c.bottom; r != c; r = r.bottom) {
		    	// We add r line to partial solution
		    	answer.add(r);

		    	// We cover columns
		    	for (DancingNode j = r.right; j != r; j = j.right) {
		    		j.column.cover();
		    	}

		    	// recursive call to level k + 1
		    	process(k + 1);

		    	// We go back
		    	r = answer.remove(answer.size() - 1);
		    	c = r.column;

		    	// We uncover columns
		    	for (DancingNode j = r.left; j != r; j = j.left) {
		    		j.column.uncover();
		    	}
		    }

		    c.uncover();
		}
	}

	/**
	 * The selection method for columns inside algorithm X.  
	 * Selecting the minimum size column first.
	 * 
	 * @return (ColumnNode) The column node selected from the method.
	 */
	private ColumnNode selectColumnNodeHeuristic() {
		int min = Integer.MAX_VALUE;
		ColumnNode ret = null;
		for(ColumnNode c = (ColumnNode) header.right; c != header; c = (ColumnNode) c.right) {
			if(c.size < min) {
				min = c.size;
				ret = c;
			}
		}
		return ret;
	}

	/**
	 * Creates the empty answer list, then calls Algorithm X with int 0.
	 * 
	 */
	public void solve() {
		answer = new LinkedList<DancingNode>();
		process(0);
	}

	/**
	 * Diagnostics to have a look at the board state
	 */
	private void printBoard() {
        System.out.println("Board Config: ");
        for(ColumnNode tmp = (ColumnNode) header.right; tmp != header; tmp = (ColumnNode) tmp.right){

            for(DancingNode d = tmp.bottom; d != tmp; d = d.bottom){
                String ret = "";
                ret += d.column.name + " --> ";
                for(DancingNode i = d.right; i != d; i = i.right){
                    ret += i.column.name + " --> ";
                }
                System.out.println(ret);
            }
        }
    }
}
