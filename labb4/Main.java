import java.util.Scanner;
class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        int E = sc.nextInt();
        int m = sc.nextInt();
        int[][] edges = new int[E][2];
        int[] edgeCount = new int[V + 1];
        // Node 0 existerar ej, sätt till oändligheten
        edgeCount[0] = Integer.MAX_VALUE;

        // Samla kanter
        for (int i = 0; i < E; i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            edgeCount[a]++;
            edgeCount[b]++;
            edges[i][0] = a + 2;
            edges[i][1] = b + 2;
        }

        // Räkna alla noder utan kanter (isolerade)
        int isolatedNodes = 0;
        for (int eCount : edgeCount){
            if (eCount == 0) isolatedNodes++;
        }

        // Utskrift
        // Antal roller n, samt p1 och p2
        System.out.println(V + 2);

        // Antal scener s, samt separata scener för p1 och p2 / r1 och r2
        // Varje isolerad nod kommer även få en separat scen 
        // -> Alla roller förekommer minst en gång
        System.out.println(E + 2 + isolatedNodes);

        // Antal skådespelare k, samt p1 och p2
        System.out.println(m + 2);

        // Explicit tilldela r1 och r2 till p1 och p2
        System.out.println("1 1");
        System.out.println("1 2");

        // Resten av rollerna kan spelas av alla roller
        // <-> Kan vi färga grafen med m färger så att ingen gränsar samma?
        for (int i = 0; i < V; i++){
            String assign = m + " ";
            for (int j = 1; j <= m; j++){
                assign += j == m ? (j + 2) : (j + 2) + " ";
            }
            System.out.println(assign );
        }

        // Explicit tilldela r1 och r3 till s1 och r2 och r3 till s2
        // Diva villkoret bryts alltså inte
        System.out.println("2 1 3");
        System.out.println("2 2 3");

        // Låt varje kant (r_a, r_b) vara en scen, där r_a och r_b medverkar
        // Därmed alltid två roller på varje scen
        for (int[] role: edges) {
            System.out.println("2 " + role[0] + " " + role[1]);
        }

        // Skapa scener för isolerade noder, alltså roller utan en scentilldelning
        // Noden kan färgas till vilken färg som helst -> godtycklig självständig partner r1
        for (int i = 1; i <= V; i++){
            if (edgeCount[i] == 0){
                System.out.println("2 1 " + (i+2));
            }
        }

        sc.close();

    }
}