
public class Tree {
    
    static Integer MAXN = 32; //Max number of nodes

    //Version array, stores each version of the tree
    static int[] versionArr = new int[MAXN];

    //Root pointers, each pointing to a version of the tree
    static Node[] versionRoot = new Node[MAXN];

    //Version-0 tree
    static void build(Node n, int l, int r) {
        if (l == r) {
            n.value = 1;
            return;
        }

        int mid = (l + r) / 2;

        n.leftNode = new Node(null, null, 0);
        n.rightNode = new Node(null, null, 1);
        
        build(n.leftNode, l, mid);
        build(n.rightNode, mid + 1, r);

        n.value = n.leftNode.value + n.rightNode.value;
    }

}