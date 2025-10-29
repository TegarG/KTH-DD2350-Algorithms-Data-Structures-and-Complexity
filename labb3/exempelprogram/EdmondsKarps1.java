import java.util.ArrayDeque;
import java.util.ArrayList;

public class EdmondsKarps1{

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
            }

            // Uppdatera maximala flödet
            maxFlow += bottleneck;
        }
        return maxFlow;
    }
    static int[] MaxFlowSolver(int[] flowGraph){
        int V = flowGraph[0];
        int s = flowGraph[1] - 1;
        int t = flowGraph[2] - 1;
        int E = flowGraph[3];

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = (ArrayList<Edge>[]) new ArrayList[V];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 4; i < flowGraph.length; i = i + 3){
            int a = flowGraph[i] - 1;
            int b = flowGraph[i+1] - 1;
            int c = flowGraph[i+2];

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
        
        // Samla kanter med positivt flöde
        for (ArrayList<Edge> edges : graph) {
            for (int j = 0; j < edges.size(); j++) {
                Edge e = edges.get(j);
                if (e.flow > 0){
                    posEdges.add(e);
                }
            }
        }

        int[] maxFlowGraph = new int[5 + (3 * posEdges.size())];

        // Samla värden för max flödesgrafen
        maxFlowGraph[0] = V;
        maxFlowGraph[1] = s+1;
        maxFlowGraph[2] = t+1;
        maxFlowGraph[3] = maxFlow;
        maxFlowGraph[4] = posEdges.size();

        int i = 5;
        // Lägg in kanter med positivt flöde
        for (Edge edge: posEdges){
            maxFlowGraph[i++] = edge.from + 1;
            maxFlowGraph[i++] = edge.to + 1;
            maxFlowGraph[i++] = edge.flow;
        }

        return maxFlowGraph;
    }
}