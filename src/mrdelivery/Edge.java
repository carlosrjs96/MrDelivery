package mrdelivery;

public class Edge {
    private double weight;
    private Vertex startVertex;
    private Vertex targetVertex;

    private boolean activo;
    private int costo;
    private double km;
    private double minutos;

    public Edge(Vertex startVertex, Vertex targetVertex, boolean activo, int costo, double km, double minutos) {
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
        this.activo = activo;
        this.costo = costo;
        this.km = km;
        this.minutos = minutos;
    }

    public boolean isActivo() {
        return activo;
    }

    public int getCosto() {
        return costo;
    }

    public double getKm() {
        return km;
    }

    public double getMinutos() {
        return minutos;
    }
    public Edge(double weight, Vertex startVertex, Vertex targetVertex) {
        this.weight = weight;
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    public void setTargetVertex(Vertex targetVertex) {
        this.targetVertex = targetVertex;
    }
}
