import java.util.Scanner;
import java.util.Stack;


public class Parray{
    public static void main (String[] args){

        Scanner sc = new Scanner(System.in);

        Parray arr = newarray();

        while (true) { 
            String input = sc.nextLine();

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



        // System.out.println("hello world!");

        // Parray arr = newarray();

        // arr = set(arr, 0, 420);
        // arr = set(arr, 1, 42);
        // arr = set(arr, 13, 100);
        // System.out.println(get(arr, 15));
        // arr = set(arr, 15, 69);
        // System.out.println(get(arr, 15));
        // System.out.println(get(arr, 0));
        // arr.unset();
        // System.out.println(get(arr, 15));
        // System.out.println(get(arr, 13));
        // arr.unset();
        // System.out.println(get(arr, 13));

    }

    private Node root;
    private final static int height = 31;
    private int index = 0;
    private static Stack<Node> vStack = new Stack<Node>();;

    public Parray(Node r){
        this.root = r;
    }

    public Parray(int h){
        this.root = createEmptyArray(h);
    }

    public static Parray newarray(){
        vStack.push(null);
        return new Parray(null);
    }

    public Node createEmptyArray(int h){
        // Base case: if h is zero then leaf node
        if (h == 0) return new Node(index++, null, null);

        // Recursive case: create children nodes
        Node left = createEmptyArray(h-1);
        Node right = createEmptyArray(h-1);

        // Return parent node
        return new Node(h, left, right);
    }


    public static Parray set(Parray a, int i, Integer value){
        // Sätt a[i] till value och returnera den nya versionen av arrayen. i är ett ickenegativt heltal och value är ett heltal. Såväl i som value är 32-bitstal, dvs högst 231 − 1.

        // Check if index within range, i >2^31 - 1 or i is negative
        if ((i > ((1 << height) - 1) || i < 0)){
            return null;
        }

        if (value == null){
            return null;
        }

        Node newRoot = set(a.root, i, value, a.height);
        vStack.push(newRoot);
        return new Parray(newRoot);
        // Traversera trädet ->
        // Om höjden är 0, då är vi vid ett löv och lägger in värde
        // Om höjden är inte > 0, skapa ny nod och gå till antingen höger eller vänster
        // Noden som inte ersätts skapar vi en pekare till från den nya noden
    }

    public static Node set(Node current, int i, Integer value, int h){
        // Set leaf to value
        if (h == 0){
            return new Node(value, null, null);
        }
        if (current == null) current = new Node(null, null, null);

        // Check direction on each level
        int direction = i & (1 << (h-1));

        if (direction == 0){
            Node left = set(current.left, i, value, h-1);

            return new Node(null, left, current.right);
        }else{
            Node right = set(current.right, i, value, h-1);
            return new Node(null, current.left, right);
        }
    }

    public static int get(Parray a, int i){
        // Returnera värdet a[i]. Om a[i] inte har tilldelats ett värde tidigare returneras 0.

        // Check if index within range, i >2^31 - 1 or i is negative
        if ((i > ((1 << height) - 1) || i < 0)){
            return 0;
        }

        return get(a.root, i, a.height);
    }

    public static int get(Node n, int i, int h){
        // If requested node does not exist return 0
        if (n == null) return 0;
        // If leaf return value
        if (h == 0) return n.value;

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
        // Unset function should retrieve the previous versions tree root
        // The roots should be stored in a LIFO data structure, either stack or linked list. I believe a linked list will be easier to implement dynamically.
        // The set function should push the new version to the data structure.
        vStack.pop();
        this.root = vStack.peek();
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

}
