package mrdelivery;
//mrdelivery.Dijkstra's Algorithm using Priority Queue

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class DPQ {
    public int dist[]; // distancias
    private Set<Integer> settled;
    private PriorityQueue<Nodo> pq;
    private int V; //numero de vertices
    public List<List<Nodo>> adj; //adyacencia

    public DPQ(int V) {
        this.V = V;
        dist = new int[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Nodo>(V, new Nodo());
    }

    // Function for mrdelivery.Dijkstra's Algorithm
    public void dijkstra(List<List<Nodo>> adj, Nodo src) {
        this.adj = adj;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // Add source Nodo to the priority queue
        //pq.add(new Nodo(src, 0));
        pq.add(src);

        // Distance to the source is 0
        //dist[src.id] = 0;
        dist[src.id] = 0;
        while (settled.size() != V) {

            // remove the minimum distance Nodo
            // from the priority queue
            int u = pq.remove().id;
            //int u = !pq.isEmpty() ? (pq.remove().id) : 0;

            // adding the Nodo whose distance is
            // finalized
            settled.add(u);

            e_Neighbours(u);
        }
    }

    // Function to process all the neighbours  
    // of the passed Nodo 
    private void e_Neighbours(int u) {
        int edgeDistance = -1;
        int newDistance = -1;

        // All the neighbors of v 
        for (int i = 0; i < adj.get(u).size(); i++) {
            Nodo v = adj.get(u).get(i);

            // If current Nodo hasn't already been processed 
            if (!settled.contains(v.id)) {
                edgeDistance = v.costo;
                newDistance = dist[u] + edgeDistance;

                // If new distance is cheaper in cost 
                if (newDistance < dist[v.id])
                    dist[v.id] = newDistance;

                // Add the current Nodo to the queue 
                pq.add(new Nodo(v.id, v.vertice, dist[v.id], v.km, v.minutos));
            }
        }
    }

}
