import java.util.List;
import java.util.ArrayList;
/**
 * Driver which creates an instance of the Soduku Game controller.
 * 
 * @author Robert Wilson
 * Created: 19 NOV 2022
 * Class: CS5800
 *
 */
public class Driver {

	public static void main(String[] args) {
		Controller s1 = new Controller();

		// int[][] sodukoData = 
		// {{1, 2, 3, 4, 5, 6, 7, 8, 9}, 
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}, 
		// {1, 2, 3, 4, 5, 6, 7, 8, 9},
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}, 
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}, 
		// {1, 2, 3, 4, 5, 6, 7, 8, 9},
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}, 
		// {1, 2, 3, 4, 5, 6, 7, 8, 9},
		// {1, 2, 3, 4, 5, 6, 7, 8, 9}};

		// int i;
		// int j;

		// ArrayList<ArrayList<Node>> rows = new ArrayList<ArrayList<Node>>();

		// for (i = 0; i < 9; i++) {
		// 	ArrayList<Node> nodeList = new ArrayList<Node>();
		// 	for (j = 0; j < 9; j++) {
		// 		Node newNode = new Node(sodukoData[i][j]);
		// 		nodeList.add(newNode);				
		// 	}
		// 	rows.add(nodeList);
		// }

		// for (i = 0; i < 9; i++) {
		// 	ArrayList<Node> arrayList = rows.get(i);
		// 	for (j = 0; j < 9; j++) {
		// 		Node node = arrayList.get(j);
		// 		if (j + 1 < 9) {
		// 			node.setRightNode(arrayList.get(j+1));
		// 		}
		// 		else {
		// 			// this assumes the nodes at the end of the row
		// 			// have a right reference to the beginning of the row
		// 			// not the next row's first column
		// 			node.setRightNode(arrayList.get(0));
		// 		}
		// 		if (j - 1 > 0) {
		// 			node.setLeftNode(arrayList.get(j-1));
		// 		}
		// 		else {
		// 			node.setLeftNode(arrayList.get(8));
		// 		}
		// 		if (i - 1 > 0) {
		// 			ArrayList<Node> upArrayList = rows.get(i-1);
		// 			node.setUpNode(upArrayList.get(j));
		// 		}
		// 		else {
		// 			ArrayList<Node> upArrayList = rows.get(8);
		// 			node.setUpNode(upArrayList.get(j));
		// 		}
		// 		if (i + 1 < 9) {
		// 			ArrayList<Node> downArrayList = rows.get(i+1);
		// 			node.setDownNode(downArrayList.get(j));
		// 		}
		// 		else {
		// 			ArrayList<Node> downArrayList = rows.get(8);
		// 			node.setDownNode(downArrayList.get(j));
		// 		}
		// 		node.setColHeadNode(arrayList.get(0));
		// 		node.setColTailNode(arrayList.get(8));
		// 		ArrayList<Node> headRow = rows.get(0);
		// 		node.setRowHeadNode(headRow.get(0));
		// 		ArrayList<Node> tailRow = rows.get(8);
		// 		node.setRowTailNode(tailRow.get(8));
		// 	}
		// }



		// for (i = 0;i < 9; i++) {
		// 	for (j = 0; j < 9; i++) {
		// 		Node node = nodeList.get(i);
		// 		if (i%8 == 0) {
		// 			node.setLeftNode(nodeList.get(nodeList.get(i+8)));
		// 		}
		// 		else {
		// 			node.setLeftNode(nodeList.get(nodeList.get(i++)));
		// 		}
		// 		if (i%9 == 0) {
		// 			node.setRightNode(nodeList.get(nodeList.get(i-8)));
		// 		}
		// 		else {
		// 			node.setRightNode(nodeList.get(nodeList.get(i--)));
		// 		}
		// 		node.setUpNode()
		// 		node.printNode();
		// 	}
		// }
	}
}
