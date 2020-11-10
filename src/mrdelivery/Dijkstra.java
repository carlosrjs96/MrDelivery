package mrdelivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    public void computePath(Vertex sourceVertex, String param) {
        sourceVertex.setMinDistance(0);
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceVertex);

        while (!priorityQueue.isEmpty()) {
            Vertex vertex = priorityQueue.poll();

            for (Edge edge : vertex.getEdges()) {
                Vertex v = edge.getTargetVertex();
//                Vertex u = edge.getStartVertex();
                //--------------------------------
                double weight;
                double minDistance;
                if (param.equals("costo")){
                    weight = edge.getCosto();
                    minDistance = vertex.getMinDistance() + weight;
                } else if (param.equals("km")){
                    weight = edge.getKm();
                    minDistance = vertex.getMinDistance() + weight;
                } else {
                    weight = edge.getMinutos();
                    minDistance = vertex.getMinDistance() + weight;
                }
                //---------------------------------
                //double weight = edge.getWeight();
                //double minDistance = vertex.getMinDistance() + weight;

                if (minDistance < v.getMinDistance()) {
                    priorityQueue.remove(vertex);
                    v.setPreviosVertex(vertex);
                    v.setMinDistance(minDistance);
                    priorityQueue.add(v);
                }
            }
        }
    }

    public List<Vertex> getShortestPathTo(Vertex targetVerte) {
        List<Vertex> path = new ArrayList<>();

        for (Vertex vertex = targetVerte; vertex != null; vertex = vertex.getPreviosVertex()) {
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }
}
