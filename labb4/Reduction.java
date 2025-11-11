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
        int n = V;
        System.out.println(n);

        // Scener 
        int s = E; 
        System.out.println(s);

        // Skådespelare p1, p2, ... pk
        int k = M;
        System.out.println(k);

        // Roller 
        // p1 och p2 får varsin roll
        System.out.println("1 1");
        System.out.println("1 2");

        // Resterande roller kan besättas av alla
        for (int i = 2; i < V; i++){
            System.out.print(M);
            for(int j = 1; j <= M; j++){
                System.out.print(" " + j);
            }
            System.out.println();
        }

        // Scener
        // scen 1 spelas av p1 och p3
        System.out.println("2 1 3");
        // scen 2 spelas av p2 och p3 
        System.out.println("2 2 3");

        // Resterande scener medverkas av
        for(int i = 2; i < s; i++){
            System.out.println("2 " + edges[i][0] + " " + edges[i][1]);
        }

        sc.close();
    }
}
