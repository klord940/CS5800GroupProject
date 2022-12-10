package algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DLX {

	private ColumnNode header;
	private List<DancingNode> answer;
	public List<DancingNode> result;

	public DLX(int[][] cover) {
		header = createDLXList(cover);
	}

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

					if (prev == null)
						prev = newNode;

					col.top.linkDown(newNode);
					prev = prev.linkRight(newNode);
					col.size++;
				}
			}
		}

		headerNode.size = nbColumns;

		return headerNode;
	}
	
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

		    	// recursive call to leverl k + 1
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
	
	private ColumnNode selectColumnNodeHeuristic() {
		// TODO Auto-generated method stub
		return null;
	}

	public void solve() {
		// TODO
	}
	
	
}
