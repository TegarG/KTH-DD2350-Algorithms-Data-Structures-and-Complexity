/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
    Kattio io;

	// Bipartit graf
	int x, y, e;
	int[][] edges;

	// Flödeslösning
	int totflow;
	int[][] edgeMatches;

    
    void readBipartiteGraph() {
	// Läs antal hörn och kanter
	x = io.getInt();
	y = io.getInt();
	e = io.getInt();

	// Deklarera edges arr med e kanter
	edges = new int[e][];
	
	// Läs in kanterna
	for (int i = 0; i < e; ++i) {
	    int a = io.getInt();
	    int b = io.getInt();
		edges[i] = new int[]{a, b};
	}
    }
    
    
    void writeFlowGraph() {
	int v = x + y + 2, totalEdges = x + y + e, s = 1, t = v;
	
	// Skriv ut antal hörn och kanter samt källa och sänka
	io.println(v);
	io.println(s + " " + t);
	io.println(totalEdges);

	// Kanter från s till X
	for (int i = 1; i <= x; i++){
		io.println(s + " " + (i+1) + " 1");
		//System.out.println(s + " " + (i+1) + " 1");
	}

	// Kanter från X till Y
	for (int i = 0; i < e; ++i) {
	    // int a = 1, b = 2, c = 17;
	    // Kant från a till b med kapacitet c
	    // io.println(a + " " + b + " " + c);
		int a = edges[i][0] + 1;
		int b = edges[i][1] + 1;
		io.println(a + " " + b + " 1");
		//System.out.println(a + " " + b + " 1");
	}

	// Kanter från Y till t
	for (int i = x + 1; i <= x + y; i++){
		io.println((i+1) + " " + t + " 1");
		//System.out.println((i+1) + " " + t + " 1");
	}

	// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
	io.flush();
	
	// Debugutskrift
	System.err.println("Skickade iväg flödesgrafen");
    }
    
    
    void readMaxFlowSolution() {
	// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
	// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
	// skickade iväg)
	int v = io.getInt();
	int s = io.getInt();
	int t = io.getInt();
	totflow = io.getInt();
	int e = io.getInt();
	int foundMatches = 0;

	edgeMatches = new int[totflow][];
	
	for (int i = 0; i < e; ++i) {
		//if(foundMatches == totflow) break;
	    // Flöde f från a till b
	    int a = io.getInt();
	    int b = io.getInt();
	    int f = io.getInt();

		if (f == 1 && (a <= x+1 && a > s) && (b > x+1 && b < t)){
			edgeMatches[foundMatches] = new int[]{a-1, b-1};
			foundMatches++;
		}
	}

	// for(int[] edge: edgeMatches){
	// 	System.out.println(edge[0] + " " + edge[1]);
	// }

    }
    
    
    void writeBipMatchSolution() {
	// int x = 17, y = 4711, maxMatch = 0;
	int maxMatch = totflow;
	
	// Skriv ut antal hörn och storleken på matchningen
	io.println(x + " " + y);
	io.println(maxMatch);
	
	for (int i = 0; i < maxMatch; ++i) {
	    int a = edgeMatches[i][0], b = edgeMatches[i][1];
	    // Kant mellan a och b ingår i vår matchningslösning
	    io.println(a + " " + b);
	}
	
    }
    
    BipRed() {
	io = new Kattio(System.in, System.out);
	
	readBipartiteGraph();
	
	writeFlowGraph();
	
	readMaxFlowSolution();
	
	writeBipMatchSolution();

	// debugutskrift
	System.err.println("Bipred avslutar\n");

	// Kom ihåg att stänga ner Kattio-klassen
	io.close();
    }
    
    public static void main(String args[]) {
	new BipRed();
    }
}

