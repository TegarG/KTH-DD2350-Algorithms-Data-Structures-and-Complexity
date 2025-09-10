import java.util.Scanner;
import java.util.Stack;


public class Parray{
    public static void main (String[] args){

        Scanner sc = new Scanner(System.in);

        Parray arr = newarray();

        while (true) { 
            String input = sc.nextLine();

            if (input == null){
                break;
            }

            String[] inArr = input.split(" ");

            if (inArr.length > 3) {
                System.out.println("Invalid input!");
            }

            switch(inArr[0]){
                case "set":
                    int i1 = Integer.parseInt(inArr[1]);
                    int value = Integer.parseInt(inArr[2]);
                    arr = set(arr, i1, value);
                    break;
                case "get":
                    int i2 = Integer.parseInt(inArr[1]);
                    System.out.println(get(arr, i2));
                    break;
                case "unset":
                    arr.unset();
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private Node root;
    private int height;
    private static Stack<ArrVersion> vStack = new Stack<ArrVersion>();;

    public Parray(Node r, int h){
        this.root = r;
        this.height = h;
    }

    public Parray(int h){
        this.root = createEmptyArray(h);
    }

    public static Parray newarray(){
        Node newRoot = createEmptyArray(4);
        vStack.push(new ArrVersion(newRoot, 4));
        return new Parray(newRoot, 4);
    }

    public static Node createEmptyArray(int h){
        // Base case: if h is zero then leaf node
        if (h == 0) return new Node(null, null, null);

        // Recursive case: create children nodes
        Node left = createEmptyArray(h-1);
        Node right = createEmptyArray(h-1);

        // Return parent node
        return new Node(h, left, right);
    }

    /// Sätt a[i] till value och returnera den nya versionen av arrayen. i är ett ickenegativt heltal och value är ett heltal. Såväl i som value är 32-bitstal, dvs högst 231 − 1.
    public static Parray set(Parray a, int i, Integer value){

        // Dynamically increase binary tree by creating new root and pointing old root as left child node
        while (i > ((1 << a.height) - 1) && a.height < 32){
            Node newRoot = new Node(null, a.root, null);
            a.height++;
            a.root = newRoot;
        }

        // Check if index within range, i >2^31 - 1 or i is negative
        if ((i > ((1 << 31) - 1) || i < 0)){
            return null;
        }

        // If user inputs null value, return null
        if (value == null){
            return null;
        }

        // Create new tree root and push to version stack, return array
        Node newRoot = set(a.root, i, value, a.height);
        vStack.push(new ArrVersion(newRoot, a.height));
        return new Parray(newRoot, a.height);
    }

    public static Node set(Node current, int i, Integer value, int h){
        // If height is 0 -> leaf, set leaf to value
        if (h == 0){
            return new Node(value, null, null);
        }

        // If there is no node -> create placeholder
        if (current == null) current = new Node(null, null, null);

        // Check direction on each level
        int direction = i & (1 << (h-1));

        if (direction == 0){ // Left
            // Create left node recursively, and return newly created current node preserving right child node
            Node left = set(current.left, i, value, h-1);
            return new Node(null, left, current.right);
        }else{
            // Create right node recursively, and return newly created current node preserving left child node
            Node right = set(current.right, i, value, h-1);
            return new Node(null, current.left, right);
        }
    }

    /// Returnera värdet a[i]. Om a[i] inte har tilldelats ett värde tidigare returneras 0.
    public static int get(Parray a, int i){
        // Check if index within range, i >2^31 - 1 or i is negative
        if ((i > ((1 << a.height) - 1) || i < 0)){
            return 0;
        }

        return get(a.root, i, a.height);
    }

    public static int get(Node n, int i, int h){
        // If requested node does not exist return 0
        if (n == null) return 0;

        // If leaf -> return value
        if (h == 0){
            if(n.value == null){// No value
                return 0; 
            } else{
                return n.value;
            }
        }

        // Check direction on each level
        int direction = i & (1 << (h-1));

        // If bit is 0 go left
        if (direction == 0){
            return get(n.left, i, h-1);
        }else{ // If bit is 1 go right
            return get(n.right, i, h-1);
        }
    }

    public void unset(){
        // Always preserve first version
        if (vStack.size() == 1) return;

        vStack.pop();// Pop current version
        ArrVersion previous = vStack.peek(); // Retrieve previous version
        this.root = previous.root;
        this.height = previous.height;
    }

    public static class Node{
        public Integer value;
        public Node left;
        public Node right;

        public Node(Integer value, Node left, Node right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public static class ArrVersion{
        public Node root;
        public int height;

        public ArrVersion(Node r, int h){
            this.root = r;
            this.height = h;
        }
    }

}
