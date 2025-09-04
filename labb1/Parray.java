public class Parray{
    public static void main (String[] args){
        System.out.println("hello world!");

        Parray arr = newarray();

        Node n = arr.root;
        int nr = 0;
        while (n.left != null && n.right != null){
            System.out.println(n.value);
            if (nr % 2 == 0){
                n = n.left;
            } else{
                n = n.right;
            }
            nr++;
        }
        System.out.println(n.value);
    }

    private Node root;
    private final int height = 31;

    public Parray(Node r){
        this.root = r;
    }

    public static Parray newarray(){
        return new Parray(null);
    }

    // public Node createEmptyArray(int h){
    //     // Base case: if h is zero then leaf node
    //     if (h == 0) return new Node(0, null, null);

    //     // Recursive case: create children nodes
    //     Node left = createEmptyArray(h-1);
    //     Node right = createEmptyArray(h-1);

    //     // Return parent node
    //     return new Node(h, left, right);
    // }


    public void set(Parray a, int i, Integer value){
        // Sätt a[i] till value och returnera den nya versionen av arrayen. i är ett ickenegativt heltal och value är ett heltal. Såväl i som value är 32-bitstal, dvs högst 231 − 1.

    }

    public static int get(Parray a, int i){
        // Returnera värdet a[i]. Om a[i] inte har tilldelats ett värde tidigare returneras 0.

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
        }else{ // if bit is 1 go right
            return get(n.right, i, h-1);
        }
    }

    public class Node{
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
