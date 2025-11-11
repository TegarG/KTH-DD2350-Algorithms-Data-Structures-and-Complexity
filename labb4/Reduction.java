import java.util.Scanner;

public class Reduction {
    
    //Grafklass med noder och kanter


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        // Första raden är antalet hörn V
        int V = sc.nextInt();

        // Andra raden är antalet kanter E
        int E = sc.nextInt();
        int[][] edges = new int[E][2];

        // Tredje raden är målet (antalet färger eller roller) M
        int M = sc.nextInt();

        // Resterande rader är varje kant (E st) från 1 till V
        // Läs in alla kanter 
        for (int i = 0; i < E; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            edges[i][0] = a;
            edges[i][1] = b;
        }

        // Gör om problemet till rollbesättning
        // Roller r1, r2, ... rn
        int n = V + 3;
        System.out.println(n);

        // Scener 
        int s = E + 2; 
        System.out.println(s);

        // Skådespelare p1, p2, ... pk
        int k = M;
        System.out.println(k);

        // Roller 
        // En för varje nod
        for (int i = 0; i < V; i++){
            System.out.print(M);
            for(int j = 1; j <= M; j++){
                System.out.print(" " + j);
            }
            System.out.println();
        }

        // p1 och p2 får varsin roll
        System.out.println("1 1");
        System.out.println("1 2");
        
        // Hjälp roll för scener med p1 och p2... 
        // ...kan besättas av alla förutom p1 och p2
        if(M > 2){
            System.out.print((M - 2));
            for(int i = 3; i <= M; i++){
                System.out.print(" " + i);
            }
            System.out.println();
        } else {
            System.out.println("1 1");
        }

        // Scener
        // Alla scener medverkas av
        for(int i = 0; i < E; i++){
            System.out.println("2 " + edges[i][0] + " " + edges[i][1]);
        }

        // scen 1 spelas av p1 och p3
        System.out.println("2 " + (V + 1) + " " + (V + 3));
        // scen 2 spelas av p2 och p3 
        System.out.println("2 " + (V + 2) + " " + (V + 3));

        sc.close();
    }
}
