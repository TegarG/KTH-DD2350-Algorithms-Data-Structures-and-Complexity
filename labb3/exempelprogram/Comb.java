/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class Comb {
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
    
    
    int[] writeFlowGraph() {
	int v = x + y + 2, totalEdges = x + y + e, s = 1, t = v;
	
	int[] flowGraph = new int[4 + 3*totalEdges];

    // Lägg in graf komponenter
	flowGraph[0] = v;
	flowGraph[1] = s;
	flowGraph[2] = t;
	flowGraph[3] = totalEdges;

	int index = 4;

	// Kanter från s till X
	for (int i = 1; i <= x; i++){
        flowGraph[index++] = s;
		flowGraph[index++] = i+1;
		flowGraph[index++] = 1;
		//System.out.println(s + " " + (i+1) + " 1");
	}

	// Kanter från X till Y
	for (int i = 0; i < e; i++) {
	    // int a = 1, b = 2, c = 17;
	    // Kant från a till b med kapacitet c
	    // io.println(a + " " + b + " " + c);
		int a = edges[i][0] + 1;
		int b = edges[i][1] + 1;
        flowGraph[index++] = a;
        flowGraph[index++] = b;
        flowGraph[index++] = 1;
		//System.out.println(a + " " + b + " 1");
	}

	// Kanter från Y till t
	for (int i = x + 1; i <= x + y; i++){
        flowGraph[index++] = i+1;
        flowGraph[index++] = t;
        flowGraph[index++] = 1;
		//System.out.println((i+1) + " " + t + " 1");
	}

	// Debugutskrift
	System.err.println("Skickade iväg flödesgrafen");
    return flowGraph;
    }
    
    
    void readMaxFlowSolution(int[] maxFlowGraph) {
	// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
	// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
	// skickade iväg)

    // Hämta graf komponenter
	int v = maxFlowGraph[0];
	int s = maxFlowGraph[1];
	int t = maxFlowGraph[2];
	totflow = maxFlowGraph[3];
	int e = maxFlowGraph[4];
	int foundMatches = 0;

	edgeMatches = new int[totflow][];
    int index = 5;
	
	for (int i = 0; i < e; ++i) {
		//if(foundMatches == totflow) break;
	    // Flöde f från a till b
	    int a = maxFlowGraph[index++];
	    int b = maxFlowGraph[index++];
	    int f = maxFlowGraph[index++];

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
    
    Comb() {
	io = new Kattio(System.in, System.out);
	
	readBipartiteGraph();
	
	int[] flowGraph = writeFlowGraph();

    int[] maxFlowGraph = EdmondsKarps1.MaxFlowSolver(flowGraph);
	
	readMaxFlowSolution(maxFlowGraph);
	
	writeBipMatchSolution();

	// debugutskrift
	System.err.println("Bipred avslutar\n");

	// Kom ihåg att stänga ner Kattio-klassen
	io.close();
    }
    
    public static void main(String args[]) {
	new Comb();
    }
}

