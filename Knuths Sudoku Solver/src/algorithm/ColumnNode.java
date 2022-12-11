package algorithm;

/**
 * The column node to the Dancing Links Algorithm X.  These nodes
 * contains the cover and uncover methods for traversing towards the 
 * solution and backtracking as necessary. 
 * 
 * This class extends the Dancing Links Node class, DancingNode. 
 * 
 * @author Robert Wilson
 * Created: 09 DEC 2022
 * Class: CS 5800
 */
public class ColumnNode extends DancingNode {

	// Size of the column
	public int size;
	
	// name of the column
	public String name;

	/**
	 * Constructor for the column nodes with input for the name.
	 * 
	 * @param n (String) the name of the column.
	 */
	public ColumnNode(String n) {
		super();
	    size = 0;
	    name = n;
	    column = this;
	}

	/**
	 * Eliminates nodes as we go through the algorithm in search of
	 * a solution. 
	 * 
	 */
	public void cover() {
		removeLeftRight();

	    for (DancingNode i = bottom; i != this; i = i.bottom) {
	    	for (DancingNode j = i.right; j != i; j = j.right) {
	    		j.removeTopBottom();
	    		j.column.size--;
	    	}
	    }
	}

	/**
	 * Reinstates nodes which were eliminated in the cover process.
	 * 
	 */
	public void uncover() {
		for (DancingNode i = top; i != this; i = i.top) {
			for (DancingNode j = i.left; j != i; j = j.left) {
				j.column.size++;
				j.reinsertTopBottom();
			}
	    }
	    reinsertLeftRight();
	}
}