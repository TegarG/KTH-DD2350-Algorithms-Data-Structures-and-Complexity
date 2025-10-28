import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class EdmondsKarps{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt();
        int s = sc.nextInt() - 1;
        int t = sc.nextInt() - 1;
        int E = sc.nextInt();
        
        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = (ArrayList<Edge>[]) new ArrayList[V];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i = 0; i < E; i++){
            int a = sc.nextInt()-1;
            int b = sc.nextInt()-1;
            int c = sc.nextInt();

            // Skapa kanter
            Edge forward = new Edge(a, b, c);
            Edge reverse = new Edge(b, a, 0);
            forward.reverse = reverse;
            reverse.reverse = forward;

            // Lägg in i grafen
            graph[a].add(forward);
            graph[b].add(reverse);
        }

        int maxFlow = edmondsKarps(graph, s, t);

        ArrayList<Edge> posEdges= new ArrayList<>();

        // Output
        System.out.println(V);
        System.err.println((s+1) + " " + (t+1) + " " + maxFlow);
        
        // Samla kanter med positivt flöde
        for (ArrayList<Edge> edges : graph) {
            for (int j = 0; j < edges.size(); j++) {
                Edge e = edges.get(j);
                if (e.flow > 0){
                    posEdges.add(e);
                }
            }
        }

        // Antalet kanter med positivt flöde
        System.out.println(posEdges.size());

        // Skriv ut kanter
        for (Edge edge: posEdges){
            System.out.println((edge.from + 1)+ " " + (edge.to + 1) + " " + edge.flow);
        }

    }

    public static class Edge{
        int from, to, cap, flow;
        Edge reverse;

        public Edge(int from, int to, int cap){
            this.from = from;
            this.to = to;
            this.cap = cap;
            this.flow = 0;
        }

        public int getResidual(){
            return this.cap - this.flow;
        }
    }

    static int edmondsKarps(ArrayList<Edge>[] graph, int s, int t){
        // 1. Hitta en kortaste flödesstig med BFS
        int maxFlow = 0;
        int V = graph.length;
        int i = 0;

        while (true) { 
            Edge[] parent = new Edge[V];
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            queue.add(s);

            while (!queue.isEmpty() && parent[t] == null) { 
                int currentNode = queue.poll();
                for (Edge edge: graph[currentNode]){
                    // If node not traversed and we can push flow
                    // Do not go back to source
                    if (parent[edge.to] == null && edge.to != s && edge.getResidual() > 0){
                        // Add parent edge
                        parent[edge.to] = edge;
                        // Add edge to queue
                        queue.add(edge.to);
                    }
                }
            }

            // No more paths to sink, exit search
            if (parent[t] == null) break;

            // While queue is empty and we have not found t
            // If capacity - flow > 0, traversera nod

            // 2. Hitta bottleneck för stigen
            int bottleneck = Integer.MAX_VALUE;
            for (Edge e = parent[t]; e != null; e = parent[e.from]){
                // Hitta minimala kapaciteten för stigen
                bottleneck = Integer.min(bottleneck, e.getResidual());
            }

            // 3. Augmentera flödesstigen
            for (Edge e = parent[t]; e != null; e = parent[e.from]){
                e.flow += bottleneck;
                e.reverse.flow = -e.flow;
                i++;
            }

            // Uppdatera maximala flödet
            maxFlow += bottleneck;
        }
        return maxFlow;
    }
}