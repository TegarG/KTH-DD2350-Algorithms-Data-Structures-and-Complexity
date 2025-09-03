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

    public Parray(int h){
        this.root = createEmptyArray(h);
    }

    public static Parray newarray(){
        int startHeight = 4;

        return new Parray(startHeight);
    }

    public Node createEmptyArray(int h){
        // Base case: if h is zero then leaf node
        if (h == 0) return new Node(0, null, null);

        // Recursive case: create children nodes
        Node left = createEmptyArray(h-1);
        Node right = createEmptyArray(h-1);

        // Return parent node
        return new Node(h, left, right);
    }


    public void set(Parray a, int i, Integer value){
        // Sätt a[i] till value och returnera den nya versionen av arrayen. i är ett ickenegativt heltal och value är ett heltal. Såväl i som value är 32-bitstal, dvs högst 231 − 1.
    }

    public int get(int a, int i){
        // Returnera värdet a[i]. Om a[i] inte har tilldelats ett värde tidigare returneras 0.
        return 0;
    }

    public class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int value, Node left, Node right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

}
