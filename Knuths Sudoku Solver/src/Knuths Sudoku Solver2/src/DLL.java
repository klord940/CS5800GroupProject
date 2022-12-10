public class DLL{

	Node headNode, tailNode = null;
	
	public void addNode(int data) {
		Node newNode = new Node(data);
		
		if(headNode == null) {
			headNode = tailNode = newNode;
			headNode.leftNode = null;
			tailNode.rightNode = null;
		} else {
			tailNode.rightNode = newNode;
			newNode.leftNode = tailNode;
			tailNode = newNode;
			tailNode.rightNode = null;
		}
	}
	
	public void display(){
		Node tempHead = headNode;
		
		if(headNode == null){
			System.out.println("List is empty");
		} else {
			System.out.println("The Nodes in the DLL are: ");
			while(tempHead != null){
				System.out.println(tempHead.data + " ");
				tempHead = tempHead.rightNode;
			}
		}
	}
	
	public static void main(String[ ] args) {
		DLL newList = new DLL();
		newList.addNode(2);
		newList.addNode(3);
		newList.addNode(9);
		newList.addNode(23);
		newList.addNode(1);
		newList.display();
	}
	
	
}
