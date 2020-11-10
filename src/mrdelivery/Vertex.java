package mrdelivery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Vertex implements Comparable<Vertex> {
    private int id;
    private String name;
    private List<Edge> edges;
    private boolean visited;
    private Vertex previosVertex;
    private double minDistance = Double.MAX_VALUE;

    public Vertex(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }
    public Vertex(String name, int id) {
        this.name = name;
        this.edges = new ArrayList<>();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void addNeighbour(Edge edge) {
        this.edges.add(edge);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Vertex getPreviosVertex() {
        return previosVertex;
    }

    public void setPreviosVertex(Vertex previosVertex) {
        this.previosVertex = previosVertex;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }




    @Override
    public int compareTo(Vertex otherVertex) {
        return Double.compare(this.minDistance, otherVertex.minDistance);
    }

    @Override
    public String toString() {
        return name;
    }

    public Edge finEdge(Vertex a, Vertex b){
        if (!a.getEdges().isEmpty()) {
            for (Edge edge : a.getEdges()) {
                if (edge.getTargetVertex() == b)
                    return edge;
            }
        }
        return null;
    }
}
