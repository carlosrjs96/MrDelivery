package mrdelivery;

import java.util.Comparator;

public class Nodo implements Comparator <Nodo> {
    public int id;
    public String vertice;
    public int costo;
    public int km;
    public int minutos;

    public Nodo(){ }

    public Nodo(int id, int costo) {
        this.id = id;
        this.costo = costo;
    }

    public Nodo(int id, String vertice, int costo, int km, int minutos) {
        this.id = id;
        this.vertice = vertice;
        this.costo = costo;
        this.km = km;
        this.minutos = minutos;
    }

    @Override
    public int compare(Nodo nodo1, Nodo nodo2){
        return Integer.compare(nodo1.costo, nodo2.costo);
    }
}
