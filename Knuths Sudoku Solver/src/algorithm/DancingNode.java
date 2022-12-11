package algorithm;

/**
 * These are the regular quadruple linked nodes in the Dancing
 * Links Algorithm X.
 * 
 * @author Robert Wilson
 * Created: 09 DEC 2022
 * Class: CS 5800
 */
public class DancingNode {
	
	// Pointers to nearby nodes
	public DancingNode left, right, top, bottom;
	
	// Column the node is in
	public ColumnNode column;

	/**
	 * Constructor for all nodes, but here for the column nodes really.
	 * 
	 */
	public DancingNode() {
		left = right = top = bottom = this;
	}

	/**
	 * Constructor for all other nodes with the input of which 
	 * column it is in. 
	 * 
	 * @param c (ColumnNode) the columnNode this node belongs to.
	 */
	public DancingNode(ColumnNode c) {
    	this();
    	column = c;
	}

	/**
	 * Link the current node above the input node.
	 * 
	 * @param node (DancingNode) The node to be under the current node.
	 * @return node (DancingNode)  The node to be under the current node.
	 */
	public DancingNode linkDown(DancingNode node) {
		node.bottom = bottom;
		node.bottom.top = node;
		node.top = this;
		bottom = node;
		return node;
	}

	/**
	 * Link the current node to the left of the input node.
	 * 
	 * @param node (DancingNode)  The node to be to the right of the current node.
	 * @return node (DancingNode)  The node to be to the right of the current node.
	 */
	public DancingNode linkRight(DancingNode node) {
		node.right = right;
		node.right.left = node;
		node.left = this;
		right = node;
		return node;
	}

	/**
	 * Change the node to the left of this to point to the node 
	 * to the right of this node.  Also change the node to the 
	 * left to point to the node on the right of this node.
	 * 
	 * Effectively removing this node from the row list.
	 * 
	 */
 	public void removeLeftRight() {
 		left.right = right;
 		right.left = left;
 	}

 	/**
 	 * Reinsert this node in the row by resetting the left 
 	 * and right nodes to point back at it.
 	 * 
 	 */
 	public void reinsertLeftRight() {
 		left.right = this;
 		right.left = this;
 	}

 	/**
 	 * Change the node to the top of this to point to the node
 	 * to the bottom of this node.  Also change the node to the
 	 * bottom of this to point to the node on the top of this node.
 	 * 
 	 * Effectively removing it from the vertical list.
 	 * 
 	 */
 	public void removeTopBottom() {
 		top.bottom = bottom;
 		bottom.top = top;
 	}

 	/**
 	 * Reinsert this node into the column by resetting the top
 	 * and bottom nodes to point at this node again.
 	 * 
 	 */
 	public void reinsertTopBottom() {
 		top.bottom = this;
 		bottom.top = this;
 	}
}