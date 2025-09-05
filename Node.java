
public class Node {
    public int value; //sum of elements in node
    public Node leftNode, rightNode; //left and right children
    public Node() {}

    public Node(Node l, Node r, int value) {
        this.leftNode = l;
        this.rightNode = r;
        this.value = value;
    }
}