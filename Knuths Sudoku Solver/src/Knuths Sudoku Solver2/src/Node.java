public class Node {
	
    public Integer data;
    public Node rowHeadNode;
    public Node rowTailNode;
    public Node colHeadNode;
    public Node colTailNode;
    public Node rightNode;
    public Node leftNode;
    public Node upNode;
    public Node downNode;
    
    public Node(Integer data){
        this.data=data;
    }
    public void setRowHeadNode(Node rowHeadNode) {
        this.rowHeadNode = rowHeadNode;
    }

    public Node getRowHeadNode() {
        return this.rowHeadNode;
    }

    public void setRowTailNode(Node rowTailNode) {
        this.rowTailNode = rowTailNode;
    }

    public void setColHeadNode(Node colHeadNode) {
        this.colHeadNode = colHeadNode;
    }

    public Node getColHeadNode() {
        return this.colHeadNode;
    }

    public Node getRowTailNode() {
        return this.rowTailNode;
    }

    public void setColTailNode(Node colTailNode) {
        this.colTailNode = colTailNode;
    }

    public Node getColTailNode() {
        return this.colTailNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public void setUpNode(Node upNode) {
        this.upNode = upNode;
    }

    public void setDownNode(Node downNode) {
        this.downNode = downNode;
    }

    public Node getRightNode() {
        return this.rightNode;
    }

    public Node getLeftNode() {
        return this.leftNode;
    }

    public Node getUpNode() {
        return this.upNode;
    }

    public Node getDownNode() {
        return this.downNode;
    }

    public int getData() {
        return this.data;
    }

    public void printNode() {
        System.out.print("Value: " + String.valueOf(this.data) + "\n");
        // System.out.print(String.valueOf("Up: " + this.upNode.getData() + "\n"));
        // System.out.print(String.valueOf("Down: " + this.downNode.getData() + "\n"));
        // System.out.print(String.valueOf("Right: " + this.rightNode.getData() + "\n"));
        // System.out.print(String.valueOf("Left: " + this.leftNode.getData() + "\n"));
        // System.out.print(String.valueOf("Column Tail: " + this.colTailNode.getData() + "\n"));
        // System.out.print(String.valueOf("Column Head: " + this.colHeadNode.getData() + "\n"));
        // System.out.print(String.valueOf("Row Tail: " + this.rowTailNode.getData() + "\n"));
        // System.out.print(String.valueOf("Row Head: " + this.rowHeadNode.getData() + "\n"));
        System.out.print("\n");
    }
}