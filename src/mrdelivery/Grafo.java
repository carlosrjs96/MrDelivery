package mrdelivery;

import java.util.*;

public class Grafo {

    private List<Vertex> grafo = new ArrayList<>();
    ArrayList<Integer>[] adjList2;

    List<List<Vertex>> paths = new ArrayList<>();

    public List<Vertex> getVertices() {
        return grafo;
    }

    public void setGrafo(List<Vertex> grafo) {
        this.grafo = grafo;
        adjList2 = new ArrayList[grafo.size()];
    }

    public Vertex findVertice(String name) {
        for (Vertex vertex : grafo) {
            if (vertex.getName().equals(name.toUpperCase()))
                return vertex;
        }
        return null;
    }

    public List<List<Vertex>> getPaths() {
        return paths;
    }

    public String verGrafo() {
        StringBuilder grafoView = new StringBuilder();

        for (Vertex vertice : grafo) {
            //grafoView += vertice.getName() + "->(";
            List<Edge> edges = vertice.getEdges();
            if (!edges.isEmpty()) {
                for (Edge edge : edges)
                    grafoView.append(vertice.getName()).append("->(activo: ").append(edge.isActivo()).append(", costo: ")
                            .append(edge.getCosto()).append(", km: ").append(edge.getKm()).append(", minutos: ")
                            .append(edge.getMinutos()).append(")->").append(edge.getTargetVertex().getName()).append("\n");
            } else {
                grafoView.append(vertice.getName());
            }
        }
        return grafoView.toString();
    }

    //Quita los valores visited, previosVertex, minDistance de los vertices del grafo
    void resetGrafo() {
        for (Vertex vertex : grafo) {
            vertex.setMinDistance(Double.MAX_VALUE);
            vertex.setPreviosVertex(null);
            vertex.setVisited(false);
        }
        paths.clear();
    }

    //Dado un vertice source y un vertice destino, retorna todos los caminos posibles
    public void printAllPaths(int s, int d) {

        for (int i = 0; i < grafo.size(); i++) {
            //System.out.println("tamno grafo " +grafo.size());
            adjList2[i] = new ArrayList<>();
        }
        for (Vertex vertex : grafo){
            List<Edge> edges = vertex.getEdges();
            for (Edge edge : edges)
                addEdge(vertex.getId(), edge.getTargetVertex().getId());
        }
        //boolean[] isVisited = new boolean[v];
        boolean[] isVisited = new boolean[grafo.size()];
        //ArrayList<Vertex> isVisited = new ArrayList<>();
        ArrayList<Integer> pathList = new ArrayList<>();

        // add source to path[]
        pathList.add(s);

        // Call recursive utility
        printAllPathsUtil(s, d, isVisited, pathList);
    }

    private void printAllPathsUtil(Integer u, Integer d, boolean [] isVisited, List<Integer> localPathList) {

        // Mark the current node
        isVisited[u] = true;
        //isVisited.add(u);

        if (u.equals(d)) {
            System.out.println(localPathList);
            // if match found then no need to traverse more till depth
            ArrayList<Vertex> list = new ArrayList<>();
            for (Integer integer : localPathList) {
                list.add(findVertice(integer));
            }
            paths.add(list);
            isVisited[u] = false;
            //isVisited.remove(u);
            return;
        }

        // Recur for all the vertices
        // adjacent to current vertex
//        List<Edge> adjList = findVertice(grafo, u).getEdges();
//        for (int i = 0; i < adjList.size(); ++i){
//            if (!isVisited[adjList.get(i).getTargetVertex().getId()]){
//                localPathList.add(i);
//                printAllPathsUtil(adjList.get(i).getTargetVertex().getId(), d, isVisited, localPathList);
//
//                // remove current node
//                // in path[]
//                localPathList.remove(i);
//
//            }
//        }
        for (Integer i : adjList2[u]) {
            if (!isVisited[i]) {
                // store current node
                // in path[]
                localPathList.add(i);
                printAllPathsUtil(i, d, isVisited, localPathList);

                // remove current node
                // in path[]
                localPathList.remove(i);
            }
        }

        // Mark the current node
        isVisited[u] = false;
        //isVisited.remove(u);
    }

    public Vertex findVertice(List<Vertex> list, int id) {
        for (Vertex vertex : list) {
            if (vertex.getId() == id)
                return vertex;
        }
        return null;
    }
    public Vertex findVertice(int id) {
        for (Vertex vertex : grafo) {
            if (vertex.getId() == id)
                return vertex;
        }
        return null;
    }

    public void addEdge(int u, int v) {
        // Add v to u's list.
        adjList2[u].add(v);
    }

    public double [][] getAdjMatrix(String param){

        double [][] adjMatrix = new double[grafo.size()][grafo.size()];

        //Matriz de adyacencia con costo como ponderacion
        if (param.equals("costo")) {
            for (Vertex vertice : grafo) {
                for (Edge arista : vertice.getEdges()) {
                    adjMatrix[vertice.getId()][arista.getTargetVertex().getId()] = arista.getCosto();
                }
            }
        } else if (param.equals("km")) { //Matriz de adyacencia con km como ponderacion
            for (Vertex vertice : grafo) {
                for (Edge arista : vertice.getEdges()) {
                    adjMatrix[vertice.getId()][arista.getTargetVertex().getId()] = arista.getKm();
                }
            }
        } else {
            for (Vertex vertice : grafo) { //Matriz de adyacencia con minutos como ponderacion
                for (Edge arista : vertice.getEdges()) {
                    adjMatrix[vertice.getId()][arista.getTargetVertex().getId()] = arista.getMinutos();
                }
            }
        }
        return adjMatrix;
    }

    //BUSCA A UN VERTICE POR NOMBRE
    public Vertex findVerticeByName(String name) {
        for (Vertex vertex : grafo) {
            if (vertex.getName() == name)
                return vertex;
        }
        return null;
    }

    // DFS algorithm
    public String sDFS = "";
    public String DFS(int _vertice) {
        grafo.get(_vertice).setVisited(true);
        System.out.print(_vertice + " ");
        this.sDFS += findVertice(_vertice).getName() + " ";
        Iterator<Edge> ite = grafo.get(_vertice).getEdges().listIterator();
        while (ite.hasNext()) {
            Edge adj = ite.next();
            Vertex targetVertex = adj.getTargetVertex();
            if (!targetVertex.isVisited())
                DFS(targetVertex.getId());
        }
        return sDFS;
    }
    // DFS algorithm result
    public String resultDFS(int _vertice){  //  resultDFS(findVerticeByName("A").getId()); //para ejecutar DFS
        System.out.println("Recorrido en Profundidad ");
        String result = DFS(_vertice);
        resetVectexVisited();
        this.sDFS = "";
        System.out.println(" ");
        return result;
    }

    public void resetVectexVisited(){
        for(Vertex v: grafo){
            v.setVisited(false);
        }
    }

    // BFS algorithm
    public String BFS(int _vertice) {  //  BFS(findVerticeByName("A").getId()); //para ejecutar BFS
        System.out.println("Recorrido en Anchura ");
        resetVectexVisited();
        grafo.get(_vertice).setVisited(true);
        String result = "";
        LinkedList<Integer> queue = new LinkedList();
        queue.add(_vertice);
        while (queue.size() != 0) {
            _vertice = queue.poll();
            System.out.print(_vertice + " ");
            result += findVertice(_vertice).getName() + " ";
            Iterator<Edge> ite = grafo.get(_vertice).getEdges().listIterator();
            while (ite.hasNext()) {
                Edge adj = ite.next();
                Vertex targetVertex = adj.getTargetVertex();
                if (!targetVertex.isVisited()) {
                    targetVertex.setVisited(true);
                    queue.add(targetVertex.getId());
                }
            }
        }
        System.out.println(" ");
        resetVectexVisited();
        return result;
    }

    public String Prim(double G[][],String criterio) {// Prim(getAdjMatrix("costo"),"costo");
        String result = "";
        double INF = 9999999;

        int no_edge = 0; // número de borde y  establece el número de borde en 0

        int V = G.length;

        // crea una matriz para rastrear el vértice seleccionado
        // seleccionado se convertirá en verdadero en caso contrario falso
        boolean[] selected = new boolean[V];

        // establecer inicialmente falsa seleccionada
        Arrays.fill(selected, false);

        // el número de egde en el árbol de expansión mínimo será
        // siempre menor que (V -1), donde V es el número de vértices en
        // grafico

        //elige el vértice 0 y hazlo verdadero
        selected[0] = true;

        //imprimir para bordes y peso
        System.out.println("Arbol de expansion minima ");
        System.out.println("Borde : Peso");
        result+= "Borde : Peso"+"\n";
        while (no_edge < V - 1) {
            // Para cada vértice en el conjunto S, encuentre todos los vértices adyacentes
            // , calcula la distancia desde el vértice seleccionado en el paso 1.
            // si el vértice ya está en el conjunto S, descartarlo en caso contrario
            // elige otro vértice más cercano al vértice seleccionado en el paso 1.

            double min = INF;
            int x = 0; // row number
            int y = 0; // col number

            for (int i = 0; i < V; i++) {
                if (selected[i] == true) {
                    for (int j = 0; j < V; j++) {
                        // not in selected and there is an edge
                        if (!selected[j] && G[i][j] != 0) {
                            if (min > G[i][j]) {
                                min = G[i][j];
                                x = i;
                                y = j;
                            }
                        }
                    }
                }
            }
            System.out.println(x + " - " + y + " :  " + G[x][y]);
            result+= findVertice(x).getName() + " - " + findVertice(y).getName() + " :  " + G[x][y] + " " +criterio+"\n";
            selected[y] = true;
            no_edge++;
        }
        return result;
    }
}
